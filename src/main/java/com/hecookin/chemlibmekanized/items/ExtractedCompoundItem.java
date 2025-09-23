package com.hecookin.chemlibmekanized.items;

import com.hecookin.chemlibmekanized.api.Chemical;
import com.hecookin.chemlibmekanized.api.ChemicalType;
import com.hecookin.chemlibmekanized.api.MatterState;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.List;

public class ExtractedCompoundItem extends Item implements Chemical {
    private final ChemLibDataExtractor.CompoundData compoundData;

    public ExtractedCompoundItem(ChemLibDataExtractor.CompoundData compoundData) {
        super(new Item.Properties().stacksTo(64));
        this.compoundData = compoundData;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);

        if (compoundData.description != null && !compoundData.description.isEmpty()) {
            String[] words = compoundData.description.split(" ");
            StringBuilder line = new StringBuilder();
            for (String word : words) {
                if (line.length() + word.length() + 1 > 40) {
                    tooltipComponents.add(Component.literal(line.toString()).withStyle(ChatFormatting.GRAY));
                    line = new StringBuilder(word);
                } else {
                    if (line.length() > 0) line.append(" ");
                    line.append(word);
                }
            }
            if (line.length() > 0) {
                tooltipComponents.add(Component.literal(line.toString()).withStyle(ChatFormatting.GRAY));
            }
        }

        tooltipComponents.add(Component.literal("Matter State: " + compoundData.matterState.toUpperCase())
                                      .withStyle(ChatFormatting.YELLOW));

        if (compoundData.components != null && !compoundData.components.isEmpty()) {
            tooltipComponents.add(Component.literal("Components:").withStyle(ChatFormatting.AQUA));
            for (ChemLibDataExtractor.ComponentData component : compoundData.components) {
                String componentText = component.count > 1 ?
                    String.format("• %s x%d", component.name, component.count) :
                    String.format("• %s", component.name);
                tooltipComponents.add(Component.literal(componentText).withStyle(ChatFormatting.DARK_AQUA));
            }
        }

        if (compoundData.effects != null && !compoundData.effects.isEmpty()) {
            tooltipComponents.add(Component.literal("Effects:").withStyle(ChatFormatting.LIGHT_PURPLE));
            for (ChemLibDataExtractor.EffectData effect : compoundData.effects) {
                tooltipComponents.add(Component.literal("• " + effect.location)
                                              .withStyle(ChatFormatting.DARK_PURPLE));
            }
        }
    }

    @Override
    public int getColor() {
        try {
            return Integer.parseInt(compoundData.color, 16) | 0xFF000000;
        } catch (NumberFormatException e) {
            return 0xFFFFFFFF;
        }
    }

    public int getColor(ItemStack itemStack, int tintIndex) {
        return tintIndex > 0 ? -1 : getColor();
    }

    @Override
    public String getChemicalName() {
        return compoundData.name;
    }

    @Override
    public String getAbbreviation() {
        return compoundData.name;
    }

    @Override
    public MatterState getMatterState() {
        return switch(compoundData.matterState.toLowerCase()) {
            case "solid" -> MatterState.SOLID;
            case "liquid" -> MatterState.LIQUID;
            case "gas" -> MatterState.GAS;
            default -> MatterState.SOLID;
        };
    }

    @Override
    public ChemicalType getChemicalType() {
        return ChemicalType.COMPOUND;
    }

    public String getMatterStateString() {
        return compoundData.matterState;
    }

    public String getChemicalDescription() {
        return compoundData.description != null ? compoundData.description : "";
    }

    public List<MobEffectInstance> getEffects() {
        return compoundData.effects != null ?
               compoundData.effects.stream().map(ChemLibDataExtractor.EffectData::toMobEffectInstance).toList() :
               List.of();
    }

    public List<ChemLibDataExtractor.ComponentData> getComponents() {
        return compoundData.components != null ? compoundData.components : List.of();
    }

    public ChemLibDataExtractor.CompoundData getCompoundData() {
        return compoundData;
    }

}