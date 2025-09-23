package com.hecookin.chemlibmekanized.integration;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import com.hecookin.chemlibmekanized.registry.ChemLibItemRegistry;
import com.hecookin.chemlibmekanized.registry.ChemlibMekanizedChemicals;
import com.hecookin.chemlibmekanized.items.ExtractedElementItem;
import com.hecookin.chemlibmekanized.items.ExtractedCompoundItem;
import mekanism.api.chemical.Chemical;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;
import java.util.HashMap;

public class MekanismChemLibIntegration {

    private static final Map<String, Chemical> ELEMENT_CHEMICAL_MAP = new HashMap<>();
    private static final Map<String, Chemical> COMPOUND_CHEMICAL_MAP = new HashMap<>();

    public static void initializeChemicalMappings() {
        ChemlibMekanized.LOGGER.info("Initializing ChemLib to Mekanism chemical mappings");

        mapElementsToMekanismChemicals();
        mapCompoundsToMekanismChemicals();

        ChemlibMekanized.LOGGER.info("Chemical mappings completed - {} elements, {} compounds mapped",
                                   ELEMENT_CHEMICAL_MAP.size(), COMPOUND_CHEMICAL_MAP.size());
    }

    private static void mapElementsToMekanismChemicals() {
        for (Map.Entry<String, DeferredHolder<Item, Item>> entry : ChemLibItemRegistry.ELEMENT_ITEMS.entrySet()) {
            String elementName = entry.getKey();
            Item item = entry.getValue().get();

            if (item instanceof ExtractedElementItem elementItem) {
                ChemLibDataExtractor.ElementData data = elementItem.getElementData();
                Chemical chemical = getMekanismChemicalForElement(data);

                if (chemical != null) {
                    ELEMENT_CHEMICAL_MAP.put(elementName, chemical);
                }
            }
        }
    }

    private static void mapCompoundsToMekanismChemicals() {
        for (Map.Entry<String, DeferredHolder<Item, Item>> entry : ChemLibItemRegistry.COMPOUND_ITEMS.entrySet()) {
            String compoundName = entry.getKey();
            Item item = entry.getValue().get();

            if (item instanceof ExtractedCompoundItem compoundItem) {
                ChemLibDataExtractor.CompoundData data = compoundItem.getCompoundData();
                Chemical chemical = getMekanismChemicalForCompound(data);

                if (chemical != null) {
                    COMPOUND_CHEMICAL_MAP.put(compoundName, chemical);
                }
            }
        }
    }

    private static Chemical getMekanismChemicalForElement(ChemLibDataExtractor.ElementData data) {
        String matterState = data.matterState;

        // Only map gas elements to our registered chemicals
        if ("gas".equals(matterState)) {
            return ChemlibMekanizedChemicals.getElementGas(data.name);
        }
        return null;
    }

    private static Chemical getMekanismChemicalForCompound(ChemLibDataExtractor.CompoundData data) {
        String matterState = data.matterState;

        // Only map gas compounds to our registered chemicals
        if ("gas".equals(matterState)) {
            return ChemlibMekanizedChemicals.getCompoundGas(data.name);
        }
        return null;
    }

    public static Chemical getElementChemical(String elementName) {
        return ELEMENT_CHEMICAL_MAP.get(elementName);
    }

    public static Chemical getCompoundChemical(String compoundName) {
        return COMPOUND_CHEMICAL_MAP.get(compoundName);
    }

    public static boolean isElementMapped(String elementName) {
        return ELEMENT_CHEMICAL_MAP.containsKey(elementName);
    }

    public static boolean isCompoundMapped(String compoundName) {
        return COMPOUND_CHEMICAL_MAP.containsKey(compoundName);
    }

    public static Map<String, Chemical> getAllElementMappings() {
        return new HashMap<>(ELEMENT_CHEMICAL_MAP);
    }

    public static Map<String, Chemical> getAllCompoundMappings() {
        return new HashMap<>(COMPOUND_CHEMICAL_MAP);
    }
}