package com.hecookin.chemlibmekanized.integration;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import com.hecookin.chemlibmekanized.registry.ChemLibItemRegistry;
import com.hecookin.chemlibmekanized.registry.MekanismChemicalRegistry;
import com.hecookin.chemlibmekanized.items.ExtractedElementItem;
import com.hecookin.chemlibmekanized.items.ExtractedCompoundItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;
import java.util.HashMap;

public class MekanismChemLibIntegration {

    private static final Map<String, MekanismChemicalRegistry.ChemicalReference> ELEMENT_CHEMICAL_MAP = new HashMap<>();
    private static final Map<String, MekanismChemicalRegistry.ChemicalReference> COMPOUND_CHEMICAL_MAP = new HashMap<>();

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
                MekanismChemicalRegistry.ChemicalReference chemicalRef = createMekanismChemical(data);

                if (chemicalRef != null) {
                    ELEMENT_CHEMICAL_MAP.put(elementName, chemicalRef);
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
                MekanismChemicalRegistry.ChemicalReference chemicalRef = createMekanismChemical(data);

                if (chemicalRef != null) {
                    COMPOUND_CHEMICAL_MAP.put(compoundName, chemicalRef);
                }
            }
        }
    }

    private static MekanismChemicalRegistry.ChemicalReference createMekanismChemical(ChemLibDataExtractor.ElementData data) {
        String matterState = data.matterState;
        String color = data.color;
        boolean isRadioactive = data.effects != null && !data.effects.isEmpty() &&
                               data.effects.stream().anyMatch(effect ->
                                   "minecraft:weakness".equals(effect.location) ||
                                   "minecraft:glowing".equals(effect.location));

        return switch (matterState) {
            case "gas" -> MekanismChemicalRegistry.registerElementGas(data.name, color, isRadioactive);
            case "liquid" -> MekanismChemicalRegistry.registerElementSlurry(data.name, color, isRadioactive);
            case "solid" -> {
                if ("metal".equals(data.metalType)) {
                    yield MekanismChemicalRegistry.registerElementSlurry(data.name, color, isRadioactive);
                } else {
                    yield MekanismChemicalRegistry.registerElementInfusion(data.name, color, isRadioactive);
                }
            }
            default -> null;
        };
    }

    private static MekanismChemicalRegistry.ChemicalReference createMekanismChemical(ChemLibDataExtractor.CompoundData data) {
        String matterState = data.matterState;
        String color = data.color;
        boolean isHazardous = data.effects != null && !data.effects.isEmpty();

        return switch (matterState) {
            case "gas" -> MekanismChemicalRegistry.registerCompoundGas(data.name, color, isHazardous);
            case "liquid" -> MekanismChemicalRegistry.registerCompoundSlurry(data.name, color, isHazardous);
            case "solid" -> MekanismChemicalRegistry.registerCompoundInfusion(data.name, color, isHazardous);
            default -> null;
        };
    }

    public static MekanismChemicalRegistry.ChemicalReference getElementChemical(String elementName) {
        return ELEMENT_CHEMICAL_MAP.get(elementName);
    }

    public static MekanismChemicalRegistry.ChemicalReference getCompoundChemical(String compoundName) {
        return COMPOUND_CHEMICAL_MAP.get(compoundName);
    }

    public static boolean isElementMapped(String elementName) {
        return ELEMENT_CHEMICAL_MAP.containsKey(elementName);
    }

    public static boolean isCompoundMapped(String compoundName) {
        return COMPOUND_CHEMICAL_MAP.containsKey(compoundName);
    }

    public static Map<String, MekanismChemicalRegistry.ChemicalReference> getAllElementMappings() {
        return new HashMap<>(ELEMENT_CHEMICAL_MAP);
    }

    public static Map<String, MekanismChemicalRegistry.ChemicalReference> getAllCompoundMappings() {
        return new HashMap<>(COMPOUND_CHEMICAL_MAP);
    }
}