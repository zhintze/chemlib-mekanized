package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import com.hecookin.chemlibmekanized.items.ExtractedElementItem;
import com.hecookin.chemlibmekanized.items.ExtractedCompoundItem;
import com.hecookin.chemlibmekanized.items.MetalIngotItem;
import com.hecookin.chemlibmekanized.items.MetalNuggetItem;
import com.hecookin.chemlibmekanized.items.MetalPlateItem;
import com.hecookin.chemlibmekanized.items.MetalCrystalItem;
import com.hecookin.chemlibmekanized.items.PeriodicTableItem;
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
    public static final Map<String, DeferredHolder<Item, Item>> METAL_INGOT_ITEMS = new HashMap<>();
    public static final Map<String, DeferredHolder<Item, Item>> METAL_NUGGET_ITEMS = new HashMap<>();
    public static final Map<String, DeferredHolder<Item, Item>> METAL_PLATE_ITEMS = new HashMap<>();
    public static final Map<String, DeferredHolder<Item, Item>> METAL_CRYSTAL_ITEMS = new HashMap<>();

    // Special items
    public static final DeferredHolder<Item, Item> PERIODIC_TABLE = ITEMS.register("periodic_table", PeriodicTableItem::new);

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
                // Add metal element items
                ELEMENT_ITEMS.values().forEach(item -> {
                    if (item != null && item.get() != null) {
                        Item itemInstance = item.get();
                        if (itemInstance instanceof ExtractedElementItem elementItem &&
                            "metal".equals(elementItem.getMetalType())) {
                            output.accept(itemInstance);
                        }
                    }
                });

                // Add metal ingot, nugget, and plate items (metals only)
                METAL_INGOT_ITEMS.entrySet().forEach(entry -> {
                    String metalName = entry.getKey();
                    // Check if this is a metal (not metalloid)
                    ELEMENT_ITEMS.values().forEach(elementItem -> {
                        if (elementItem != null && elementItem.get() != null) {
                            Item elementInstance = elementItem.get();
                            if (elementInstance instanceof ExtractedElementItem elementData &&
                                elementData.getElementData().name.equals(metalName) &&
                                "metal".equals(elementData.getMetalType())) {

                                // Add ingot
                                if (entry.getValue() != null && entry.getValue().get() != null) {
                                    output.accept(entry.getValue().get());
                                }

                                // Add nugget
                                var nugget = METAL_NUGGET_ITEMS.get(metalName);
                                if (nugget != null && nugget.get() != null) {
                                    output.accept(nugget.get());
                                }

                                // Add plate
                                var plate = METAL_PLATE_ITEMS.get(metalName);
                                if (plate != null && plate.get() != null) {
                                    output.accept(plate.get());
                                }

                                // Add crystal
                                var crystal = METAL_CRYSTAL_ITEMS.get(metalName);
                                if (crystal != null && crystal.get() != null) {
                                    output.accept(crystal.get());
                                }
                            }
                        }
                    });
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
                // Add metalloid element items
                ELEMENT_ITEMS.values().forEach(item -> {
                    if (item != null && item.get() != null) {
                        Item itemInstance = item.get();
                        if (itemInstance instanceof ExtractedElementItem elementItem &&
                            "metalloid".equals(elementItem.getMetalType())) {
                            output.accept(itemInstance);
                        }
                    }
                });

                // Add metalloid ingot, nugget, and plate items
                METAL_INGOT_ITEMS.entrySet().forEach(entry -> {
                    String metalName = entry.getKey();
                    // Check if this is a metalloid
                    ELEMENT_ITEMS.values().forEach(elementItem -> {
                        if (elementItem != null && elementItem.get() != null) {
                            Item elementInstance = elementItem.get();
                            if (elementInstance instanceof ExtractedElementItem elementData &&
                                elementData.getElementData().name.equals(metalName) &&
                                "metalloid".equals(elementData.getMetalType())) {

                                // Add ingot
                                if (entry.getValue() != null && entry.getValue().get() != null) {
                                    output.accept(entry.getValue().get());
                                }

                                // Add nugget
                                var nugget = METAL_NUGGET_ITEMS.get(metalName);
                                if (nugget != null && nugget.get() != null) {
                                    output.accept(nugget.get());
                                }

                                // Add plate
                                var plate = METAL_PLATE_ITEMS.get(metalName);
                                if (plate != null && plate.get() != null) {
                                    output.accept(plate.get());
                                }

                                // Add crystal
                                var crystal = METAL_CRYSTAL_ITEMS.get(metalName);
                                if (crystal != null && crystal.get() != null) {
                                    output.accept(crystal.get());
                                }
                            }
                        }
                    });
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

    public static void registerMetalIngots() {
        List<ChemLibDataExtractor.ElementData> metals = ChemLibDataExtractor.extractMetals();

        for (ChemLibDataExtractor.ElementData metal : metals) {
            String ingotName = metal.name + "_ingot";
            DeferredHolder<Item, Item> ingotItem = ITEMS.register(ingotName,
                () -> new MetalIngotItem(metal));
            METAL_INGOT_ITEMS.put(metal.name, ingotItem);
        }

        ChemlibMekanized.LOGGER.info("Registered {} metal ingots", METAL_INGOT_ITEMS.size());
    }

    public static void registerMetalNuggets() {
        List<ChemLibDataExtractor.ElementData> metals = ChemLibDataExtractor.extractMetals();

        for (ChemLibDataExtractor.ElementData metal : metals) {
            String nuggetName = metal.name + "_nugget";
            DeferredHolder<Item, Item> nuggetItem = ITEMS.register(nuggetName,
                () -> new MetalNuggetItem(metal));
            METAL_NUGGET_ITEMS.put(metal.name, nuggetItem);
        }

        ChemlibMekanized.LOGGER.info("Registered {} metal nuggets", METAL_NUGGET_ITEMS.size());
    }


    public static void registerMetalCrystals() {
        // extractMetals() already includes both metals AND metalloids
        List<ChemLibDataExtractor.ElementData> metalsAndMetalloids = ChemLibDataExtractor.extractMetals();

        // Register crystals for all metals and metalloids (no need to separate)
        for (ChemLibDataExtractor.ElementData element : metalsAndMetalloids) {
            String crystalName = element.name + "_crystal";
            DeferredHolder<Item, Item> crystalItem = ITEMS.register(crystalName,
                () -> new MetalCrystalItem(element));
            METAL_CRYSTAL_ITEMS.put(element.name, crystalItem);
        }

        ChemlibMekanized.LOGGER.info("Registered {} metal/metalloid crystals", METAL_CRYSTAL_ITEMS.size());
    }

        public static void registerMetalPlates() {
        List<ChemLibDataExtractor.ElementData> metals = ChemLibDataExtractor.extractMetals();

        for (ChemLibDataExtractor.ElementData metal : metals) {
            String plateName = metal.name + "_plate";
            DeferredHolder<Item, Item> plateItem = ITEMS.register(plateName,
                () -> new MetalPlateItem(metal));
            METAL_PLATE_ITEMS.put(metal.name, plateItem);
        }

        ChemlibMekanized.LOGGER.info("Registered {} metal plates", METAL_PLATE_ITEMS.size());
    }

    public static void initializeRegistries() {
        ChemlibMekanized.LOGGER.info("Initializing ChemLib content extraction and registration");
        registerElements();
        registerCompounds();
        registerMetalIngots();
        registerMetalNuggets();
        registerMetalPlates();
        registerMetalCrystals();
        ChemlibMekanized.LOGGER.info("ChemLib content extraction completed - {} elements, {} compounds, {} metal ingots, {} nuggets, {} plates",
                                   ELEMENT_ITEMS.size(), COMPOUND_ITEMS.size(), METAL_INGOT_ITEMS.size(),
                                   METAL_NUGGET_ITEMS.size(), METAL_PLATE_ITEMS.size(), METAL_CRYSTAL_ITEMS.size());
    }
}