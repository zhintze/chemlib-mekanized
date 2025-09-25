package com.hecookin.chemlibmekanized.client;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.items.ExtractedElementItem;
import com.hecookin.chemlibmekanized.items.ExtractedCompoundItem;
import com.hecookin.chemlibmekanized.items.MetalIngotItem;
import com.hecookin.chemlibmekanized.items.MetalNuggetItem;
import com.hecookin.chemlibmekanized.items.MetalPlateItem;
import com.hecookin.chemlibmekanized.items.MetalCrystalItem;
import com.hecookin.chemlibmekanized.registry.ChemLibItemRegistry;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ChemLibColorProviders {

    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        int elementCount = 0;
        int compoundCount = 0;
        int metalIngotCount = 0;
        int metalNuggetCount = 0;
        int metalPlateCount = 0;
        int metalCrystalCount = 0;

        // Register color providers for all element items
        for (DeferredHolder<Item, Item> itemHolder : ChemLibItemRegistry.ELEMENT_ITEMS.values()) {
            Item item = itemHolder.get();
            if (item instanceof ExtractedElementItem elementItem) {
                event.register(elementItem::getColor, item);
                elementCount++;
            }
        }

        // Register color providers for all compound items
        for (DeferredHolder<Item, Item> itemHolder : ChemLibItemRegistry.COMPOUND_ITEMS.values()) {
            Item item = itemHolder.get();
            if (item instanceof ExtractedCompoundItem compoundItem) {
                event.register(compoundItem::getColor, item);
                compoundCount++;
            }
        }

        // Register color providers for all metal ingot items
        for (DeferredHolder<Item, Item> itemHolder : ChemLibItemRegistry.METAL_INGOT_ITEMS.values()) {
            Item item = itemHolder.get();
            if (item instanceof MetalIngotItem metalIngotItem) {
                event.register(metalIngotItem::getColor, item);
                metalIngotCount++;
            }
        }

        // Register color providers for all metal nugget items
        for (DeferredHolder<Item, Item> itemHolder : ChemLibItemRegistry.METAL_NUGGET_ITEMS.values()) {
            Item item = itemHolder.get();
            if (item instanceof MetalNuggetItem metalNuggetItem) {
                event.register(metalNuggetItem::getColor, item);
                metalNuggetCount++;
            }
        }

        // Register color providers for all metal plate items
        for (DeferredHolder<Item, Item> itemHolder : ChemLibItemRegistry.METAL_PLATE_ITEMS.values()) {
            Item item = itemHolder.get();
            if (item instanceof MetalPlateItem metalPlateItem) {
                event.register(metalPlateItem::getColor, item);
                metalPlateCount++;
            }
        }

        // Register color providers for all crystal items
        for (DeferredHolder<Item, Item> itemHolder : ChemLibItemRegistry.METAL_CRYSTAL_ITEMS.values()) {
            Item item = itemHolder.get();
            if (item instanceof MetalCrystalItem metalCrystalItem) {
                event.register(metalCrystalItem::getColor, item);
                metalCrystalCount++;
            }
        }

        ChemlibMekanized.LOGGER.info("Registered color handlers for {} elements, {} compounds, {} metal ingots, {} nuggets, {} plates, {} crystals",
                                   elementCount, compoundCount, metalIngotCount, metalNuggetCount, metalPlateCount, metalCrystalCount);
    }
}