package com.hecookin.chemlibmekanized.client;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.items.ExtractedElementItem;
import com.hecookin.chemlibmekanized.items.ExtractedCompoundItem;
import com.hecookin.chemlibmekanized.registry.ChemLibItemRegistry;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ChemLibColorProviders {

    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        int elementCount = 0;
        int compoundCount = 0;

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

        ChemlibMekanized.LOGGER.info("Registered color handlers for {} elements and {} compounds", elementCount, compoundCount);
    }
}