package com.hecookin.chemlibmekanized.items;

import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Crystal form of metals and metalloids from chemical crystallization.
 * Part of the Mekanism-style ore processing chain.
 * 16200mB clean slurry = 1 crystal = 1 ingot (maintaining the 81 elements → 1 ingot balance)
 */
public class MetalCrystalItem extends Item {
    private final String metalName;
    private final String displayName;
    private final int color;

    public MetalCrystalItem(String metalName, String displayName, int color) {
        super(new Properties().stacksTo(64));
        this.metalName = metalName;
        this.displayName = displayName;
        this.color = color;
    }

    public MetalCrystalItem(ChemLibDataExtractor.ElementData elementData) {
        super(new Properties().stacksTo(64));
        this.metalName = elementData.name;
        this.displayName = formatName(elementData.name);
        // Parse color from hex string
        int tempColor;
        try {
            tempColor = Integer.parseInt(elementData.color, 16) | 0xFF000000;
        } catch (NumberFormatException e) {
            tempColor = 0xFFFFFFFF;
        }
        this.color = tempColor;
    }

    private String formatName(String name) {
        // Capitalize first letter
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        // Add tooltip showing this is a crystallized form
        tooltipComponents.add(Component.literal("Crystallized " + displayName)
                .withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Smelt to obtain ingot")
                .withStyle(ChatFormatting.DARK_GRAY));
    }

    public String getMetalName() {
        return metalName;
    }

    public int getColor() {
        return color;
    }

    // For item color handler
    public int getColor(ItemStack itemStack, int tintIndex) {
        return tintIndex > 0 ? -1 : color;
    }
}