package com.hecookin.chemlibmekanized.items;

import com.hecookin.chemlibmekanized.api.Chemical;
import com.hecookin.chemlibmekanized.api.ChemicalType;
import com.hecookin.chemlibmekanized.api.MatterState;
import com.hecookin.chemlibmekanized.client.renderer.AbbreviationRenderer;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ExtractedElementItem extends Item implements Chemical {
    private final ChemLibDataExtractor.ElementData elementData;

    public ExtractedElementItem(ChemLibDataExtractor.ElementData elementData) {
        super(new Item.Properties().stacksTo(64));
        this.elementData = elementData;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);

        tooltipComponents.add(Component.literal(String.format("%s (%d)", elementData.abbreviation, elementData.atomicNumber))
                                      .withStyle(ChatFormatting.AQUA));

        tooltipComponents.add(Component.literal(getGroupName()).withStyle(ChatFormatting.GRAY));

        tooltipComponents.add(Component.literal("Matter State: " + elementData.matterState.toUpperCase())
                                      .withStyle(ChatFormatting.YELLOW));

        tooltipComponents.add(Component.literal("Metal Type: " + elementData.metalType.toUpperCase())
                                      .withStyle(ChatFormatting.GREEN));

        if (elementData.artificial) {
            tooltipComponents.add(Component.literal("Artificial Element").withStyle(ChatFormatting.RED));
        }

        if (elementData.effects != null && !elementData.effects.isEmpty()) {
            tooltipComponents.add(Component.literal("Effects:").withStyle(ChatFormatting.LIGHT_PURPLE));
            for (ChemLibDataExtractor.EffectData effect : elementData.effects) {
                tooltipComponents.add(Component.literal("• " + effect.location)
                                              .withStyle(ChatFormatting.DARK_PURPLE));
            }
        }

        tooltipComponents.add(Component.literal("Mekanism Compatible").withStyle(ChatFormatting.GOLD));
    }

    public String getGroupName() {
        return switch(elementData.atomicNumber) {
            case 1, 6, 7, 8, 15, 16, 34 -> "Reactive Non-Metals";
            case 3, 11, 19, 37, 55, 87 -> "Alkali Metals";
            case 4, 12, 20, 38, 56, 88 -> "Alkaline Earth Metals";
            case 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 72, 73, 74, 75, 76, 77, 78, 79, 80, 104, 105, 106, 107, 108 -> "Transition Metals";
            case 13, 31, 49, 50, 81, 82, 83, 84 -> "Post-Transition Metals";
            case 109, 110, 111, 112, 113, 114, 115, 116, 117, 118 -> "Unknown Properties";
            case 5, 14, 32, 33, 51, 52 -> "Metalloids";
            case 9, 17, 35, 53, 85 -> "Halogens";
            case 2, 10, 18, 36, 54, 86 -> "Noble Gasses";
            case 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71 -> "Lanthanides";
            case 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103 -> "Actinides";
            default -> "";
        };
    }

    @Override
    public int getColor() {
        try {
            return Integer.parseInt(elementData.color, 16) | 0xFF000000;
        } catch (NumberFormatException e) {
            return 0xFFFFFFFF;
        }
    }

    public int getColor(ItemStack itemStack, int tintIndex) {
        return tintIndex > 0 ? -1 : getColor();
    }

    @Override
    public String getChemicalName() {
        return elementData.name;
    }

    @Override
    public String getAbbreviation() {
        return elementData.abbreviation;
    }

    @Override
    public MatterState getMatterState() {
        return switch(elementData.matterState.toLowerCase()) {
            case "solid" -> MatterState.SOLID;
            case "liquid" -> MatterState.LIQUID;
            case "gas" -> MatterState.GAS;
            default -> MatterState.SOLID;
        };
    }

    @Override
    public ChemicalType getChemicalType() {
        return ChemicalType.ELEMENT;
    }

    public int getAtomicNumber() {
        return elementData.atomicNumber;
    }

    public String getMatterStateString() {
        return elementData.matterState;
    }

    public String getMetalType() {
        return elementData.metalType;
    }

    public boolean isArtificial() {
        return elementData.artificial;
    }

    public List<MobEffectInstance> getEffects() {
        return elementData.effects != null ?
               elementData.effects.stream().map(ChemLibDataExtractor.EffectData::toMobEffectInstance).toList() :
               List.of();
    }

    public ChemLibDataExtractor.ElementData getElementData() {
        return elementData;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(AbbreviationRenderer.RENDERER);
    }
}