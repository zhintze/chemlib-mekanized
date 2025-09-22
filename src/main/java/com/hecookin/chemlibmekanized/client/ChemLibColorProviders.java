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
                event.register((stack, tintIndex) -> {
                    int color = elementItem.getColor(stack, tintIndex);
                    ChemlibMekanized.LOGGER.debug("Element {} color for tint index {}: 0x{}",
                                                  elementItem.getChemicalName(), tintIndex, Integer.toHexString(color));
                    return color;
                }, item);
                elementCount++;
            }
        }

        // Register color providers for all compound items
        for (DeferredHolder<Item, Item> itemHolder : ChemLibItemRegistry.COMPOUND_ITEMS.values()) {
            Item item = itemHolder.get();
            if (item instanceof ExtractedCompoundItem compoundItem) {
                event.register((stack, tintIndex) -> {
                    int color = compoundItem.getColor(stack, tintIndex);
                    ChemlibMekanized.LOGGER.debug("Compound {} color for tint index {}: 0x{}",
                                                  compoundItem.getChemicalName(), tintIndex, Integer.toHexString(color));
                    return color;
                }, item);
                compoundCount++;
            }
        }

        ChemlibMekanized.LOGGER.info("Registered color handlers for {} elements and {} compounds", elementCount, compoundCount);
    }
}