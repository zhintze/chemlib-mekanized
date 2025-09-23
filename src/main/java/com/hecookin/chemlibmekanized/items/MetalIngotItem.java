package com.hecookin.chemlibmekanized.items;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Metal ingot item generated from ChemLib metallic elements.
 * Uses the ingot template texture with element-specific color tinting.
 */
public class MetalIngotItem extends Item {
    private final ChemLibDataExtractor.ElementData elementData;

    public MetalIngotItem(ChemLibDataExtractor.ElementData elementData) {
        super(new Item.Properties().stacksTo(64));
        this.elementData = elementData;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);

        // Element information
        tooltipComponents.add(Component.literal(String.format("%s Ingot (%s)",
                                formatElementName(elementData.name), elementData.abbreviation))
                                      .withStyle(ChatFormatting.AQUA));

        // Atomic number
        tooltipComponents.add(Component.literal("Atomic Number: " + elementData.atomicNumber)
                                      .withStyle(ChatFormatting.GRAY));

        // Metal classification
        tooltipComponents.add(Component.literal("Metal Type: " + elementData.metalType.toUpperCase())
                                      .withStyle(ChatFormatting.GREEN));

        // Periodic table group
        tooltipComponents.add(Component.literal("Group: " + elementData.group + " | Period: " + elementData.period)
                                      .withStyle(ChatFormatting.YELLOW));
    }

    /**
     * Get the element data for this metal ingot.
     */
    public ChemLibDataExtractor.ElementData getElementData() {
        return elementData;
    }

    /**
     * Get the element color for tinting.
     */
    public int getColor() {
        try {
            String colorStr = elementData.color;

            // Special handling for promethium to make it more cyan
            if ("promethium".equals(elementData.name)) {
                return 0xFF4AAFAA; // Cyan-tinted color for promethium
            }

            // Handle invalid color formats (like promethium's "62af0a7")
            if (colorStr.length() == 7) {
                // Truncate to 6 characters if 7 characters long
                colorStr = colorStr.substring(0, 6);
            } else if (colorStr.length() != 6) {
                // Use a default color for other invalid lengths
                return 0xFF888888; // Default gray instead of white for visibility
            }

            int color = (int) Long.parseLong(colorStr, 16);
            color |= 0xFF000000; // Add full alpha
            return color;
        } catch (NumberFormatException e) {
            ChemlibMekanized.LOGGER.warn("Invalid color format for element {}: {}", elementData.name, elementData.color);
            return 0xFF888888; // Default gray instead of white for visibility
        }
    }

    /**
     * Get the color for the color provider system.
     */
    public int getColor(ItemStack itemStack, int tintIndex) {
        return tintIndex > 0 ? -1 : getColor();
    }

    /**
     * Format element name for display (Title Case).
     */
    private String formatElementName(String name) {
        String[] words = name.split("_");
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                formatted.append(" ");
            }
            String word = words[i];
            formatted.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase());
        }

        return formatted.toString();
    }
}