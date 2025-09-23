package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import com.hecookin.chemlibmekanized.items.ExtractedElementItem;
import com.hecookin.chemlibmekanized.items.ExtractedCompoundItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ChemLibItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ChemlibMekanized.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChemlibMekanized.MODID);

    public static final Map<String, DeferredHolder<Item, Item>> ELEMENT_ITEMS = new HashMap<>();
    public static final Map<String, DeferredHolder<Item, Item>> COMPOUND_ITEMS = new HashMap<>();

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ELEMENTS_TAB = CREATIVE_TABS.register("elements",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.chemlibmekanized.elements"))
            .icon(() -> {
                var hydrogenItem = ELEMENT_ITEMS.get("hydrogen");
                return hydrogenItem != null ? new ItemStack(hydrogenItem.get()) : ItemStack.EMPTY;
            })
            .displayItems((parameters, output) -> {
                ELEMENT_ITEMS.values().forEach(item -> {
                    if (item != null && item.get() != null) {
                        output.accept(item.get());
                    }
                });
            })
            .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COMPOUNDS_TAB = CREATIVE_TABS.register("compounds",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.chemlibmekanized.compounds"))
            .icon(() -> {
                var co2Item = COMPOUND_ITEMS.get("carbon_dioxide");
                return co2Item != null ? new ItemStack(co2Item.get()) : ItemStack.EMPTY;
            })
            .displayItems((parameters, output) -> {
                COMPOUND_ITEMS.values().forEach(item -> {
                    if (item != null && item.get() != null) {
                        output.accept(item.get());
                    }
                });
            })
            .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> METALS_TAB = CREATIVE_TABS.register("metals",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.chemlibmekanized.metals"))
            .icon(() -> {
                var ironItem = ELEMENT_ITEMS.get("iron");
                return ironItem != null ? new ItemStack(ironItem.get()) : ItemStack.EMPTY;
            })
            .displayItems((parameters, output) -> {
                ELEMENT_ITEMS.values().forEach(item -> {
                    if (item != null && item.get() != null) {
                        Item itemInstance = item.get();
                        if (itemInstance instanceof ExtractedElementItem elementItem &&
                            "metal".equals(elementItem.getMetalType())) {
                            output.accept(itemInstance);
                        }
                    }
                });
            })
            .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NON_METALS_TAB = CREATIVE_TABS.register("non_metals",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.chemlibmekanized.non_metals"))
            .icon(() -> {
                var oxygenItem = ELEMENT_ITEMS.get("oxygen");
                return oxygenItem != null ? new ItemStack(oxygenItem.get()) : ItemStack.EMPTY;
            })
            .displayItems((parameters, output) -> {
                ELEMENT_ITEMS.values().forEach(item -> {
                    if (item != null && item.get() != null) {
                        Item itemInstance = item.get();
                        if (itemInstance instanceof ExtractedElementItem elementItem &&
                            "nonmetal".equals(elementItem.getMetalType())) {
                            output.accept(itemInstance);
                        }
                    }
                });
            })
            .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> METALLOIDS_TAB = CREATIVE_TABS.register("metalloids",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.chemlibmekanized.metalloids"))
            .icon(() -> {
                var siliconItem = ELEMENT_ITEMS.get("silicon");
                return siliconItem != null ? new ItemStack(siliconItem.get()) : ItemStack.EMPTY;
            })
            .displayItems((parameters, output) -> {
                ELEMENT_ITEMS.values().forEach(item -> {
                    if (item != null && item.get() != null) {
                        Item itemInstance = item.get();
                        if (itemInstance instanceof ExtractedElementItem elementItem &&
                            "metalloid".equals(elementItem.getMetalType())) {
                            output.accept(itemInstance);
                        }
                    }
                });
            })
            .build()
    );

    public static void registerElements() {
        List<ChemLibDataExtractor.ElementData> elements = ChemLibDataExtractor.extractElements();

        for (ChemLibDataExtractor.ElementData element : elements) {
            DeferredHolder<Item, Item> elementItem = ITEMS.register(element.name,
                () -> new ExtractedElementItem(element));
            ELEMENT_ITEMS.put(element.name, elementItem);

        }
    }

    public static void registerCompounds() {
        List<ChemLibDataExtractor.CompoundData> compounds = ChemLibDataExtractor.extractCompounds();

        for (ChemLibDataExtractor.CompoundData compound : compounds) {
            DeferredHolder<Item, Item> compoundItem = ITEMS.register(compound.name,
                () -> new ExtractedCompoundItem(compound));
            COMPOUND_ITEMS.put(compound.name, compoundItem);

        }
    }

    public static void initializeRegistries() {
        ChemlibMekanized.LOGGER.info("Initializing ChemLib content extraction and registration");
        registerElements();
        registerCompounds();
        ChemlibMekanized.LOGGER.info("ChemLib content extraction completed - {} elements, {} compounds",
                                   ELEMENT_ITEMS.size(), COMPOUND_ITEMS.size());
    }
}