package com.hecookin.chemlibmekanized.datagen;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import com.hecookin.chemlibmekanized.registry.ChemLibItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Map;

public class ChemLibItemModelProvider extends ItemModelProvider {

    public ChemLibItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ChemlibMekanized.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ChemlibMekanized.LOGGER.info("Generating item models for ChemLib extracted content");

        generateElementModels();
        generateCompoundModels();
        generateMetalIngotModels();
        generateMetalNuggetModels();
        generateMetalPlateModels();

        ChemlibMekanized.LOGGER.info("Completed generating item models");
    }

    private void generateElementModels() {
        List<ChemLibDataExtractor.ElementData> elements = ChemLibDataExtractor.extractElements();

        for (ChemLibDataExtractor.ElementData element : elements) {
            DeferredHolder<Item, Item> itemHolder = ChemLibItemRegistry.ELEMENT_ITEMS.get(element.name);
            if (itemHolder != null) {
                generateLayeredElementModel(element, itemHolder.get());
            }
        }
    }

    private void generateCompoundModels() {
        List<ChemLibDataExtractor.CompoundData> compounds = ChemLibDataExtractor.extractCompounds();

        for (ChemLibDataExtractor.CompoundData compound : compounds) {
            DeferredHolder<Item, Item> itemHolder = ChemLibItemRegistry.COMPOUND_ITEMS.get(compound.name);
            if (itemHolder != null) {
                generateLayeredCompoundModel(compound, itemHolder.get());
            }
        }
    }

    private void generateLayeredElementModel(ChemLibDataExtractor.ElementData element, Item item) {
        String matterState = element.matterState;
        String parentModel = "chemlibmekanized:item/element_" + matterState + "_model";

        ItemModelBuilder model = withExistingParent(element.name, parentModel);

    }

    private void generateLayeredCompoundModel(ChemLibDataExtractor.CompoundData compound, Item item) {
        String matterState = compound.matterState;
        String parentModel;

        if ("solid".equals(matterState) && compound.hasItem) {
            parentModel = "chemlibmekanized:item/compound_dust_model";
        } else {
            parentModel = "chemlibmekanized:item/compound_" + matterState + "_model";
        }

        ItemModelBuilder model = withExistingParent(compound.name, parentModel);

    }

    private void generateMetalIngotModels() {
        for (Map.Entry<String, DeferredHolder<Item, Item>> entry : ChemLibItemRegistry.METAL_INGOT_ITEMS.entrySet()) {
            String metalName = entry.getKey();
            String ingotName = metalName + "_ingot";

            // Generate simple textured model using the ingot template
            singleTexture(ingotName,
                         ResourceLocation.fromNamespaceAndPath("minecraft", "item/generated"),
                         "layer0",
                         modLoc("items/ingot"));
        }
    }

    private void generateMetalNuggetModels() {
        for (Map.Entry<String, DeferredHolder<Item, Item>> entry : ChemLibItemRegistry.METAL_NUGGET_ITEMS.entrySet()) {
            String metalName = entry.getKey();
            String nuggetName = metalName + "_nugget";

            // Generate simple textured model using the nugget template
            singleTexture(nuggetName,
                         ResourceLocation.fromNamespaceAndPath("minecraft", "item/generated"),
                         "layer0",
                         modLoc("items/nugget"));
        }
    }

    private void generateMetalPlateModels() {
        for (Map.Entry<String, DeferredHolder<Item, Item>> entry : ChemLibItemRegistry.METAL_PLATE_ITEMS.entrySet()) {
            String metalName = entry.getKey();
            String plateName = metalName + "_plate";

            // Generate simple textured model using the plate template
            singleTexture(plateName,
                         ResourceLocation.fromNamespaceAndPath("minecraft", "item/generated"),
                         "layer0",
                         modLoc("items/plate"));
        }
    }

    public ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, path);
    }
}