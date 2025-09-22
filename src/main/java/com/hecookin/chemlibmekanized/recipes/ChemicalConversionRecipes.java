package com.hecookin.chemlibmekanized.recipes;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.registry.MekanismChemicalRegistry;

import java.util.HashMap;
import java.util.Map;

public class ChemicalConversionRecipes {

    private static final Map<String, StateConversion> ELEMENT_CONVERSIONS = new HashMap<>();
    private static final Map<String, StateConversion> COMPOUND_CONVERSIONS = new HashMap<>();

    public static class StateConversion {
        public final String solidForm;
        public final String liquidForm;
        public final MekanismChemicalRegistry.ChemicalReference gasForm;
        public final MekanismChemicalRegistry.ChemicalReference slurryForm;

        public StateConversion(String solidForm, String liquidForm, MekanismChemicalRegistry.ChemicalReference gasForm, MekanismChemicalRegistry.ChemicalReference slurryForm) {
            this.solidForm = solidForm;
            this.liquidForm = liquidForm;
            this.gasForm = gasForm;
            this.slurryForm = slurryForm;
        }
    }

    public static void initializeConversions() {
        ChemlibMekanized.LOGGER.info("Initializing chemical state conversion recipes");

        initializeElementConversions();
        initializeCompoundConversions();

        ChemlibMekanized.LOGGER.info("Chemical state conversion recipes initialized");
    }

    private static void initializeElementConversions() {
        // Gas elements
        ELEMENT_CONVERSIONS.put("hydrogen", new StateConversion(
            "minecraft:air",  // Placeholder for hydrogen item
            null,
            MekanismChemicalRegistry.getElementGas("hydrogen"),
            null
        ));

        ELEMENT_CONVERSIONS.put("oxygen", new StateConversion(
            "minecraft:air",  // Placeholder for oxygen item
            null,
            MekanismChemicalRegistry.getElementGas("oxygen"),
            null
        ));

        ELEMENT_CONVERSIONS.put("nitrogen", new StateConversion(
            "minecraft:air",  // Placeholder for nitrogen item
            null,
            MekanismChemicalRegistry.getElementGas("nitrogen"),
            null
        ));

        // Solid elements (as slurries)
        ELEMENT_CONVERSIONS.put("carbon", new StateConversion(
            "minecraft:coal",
            null,
            null,
            MekanismChemicalRegistry.getElementSlurry("carbon")
        ));

        ELEMENT_CONVERSIONS.put("iron", new StateConversion(
            "minecraft:iron_ingot",
            null,
            null,
            MekanismChemicalRegistry.getElementSlurry("iron")
        ));

        ELEMENT_CONVERSIONS.put("copper", new StateConversion(
            "minecraft:copper_ingot",
            null,
            null,
            MekanismChemicalRegistry.getElementSlurry("copper")
        ));

        ELEMENT_CONVERSIONS.put("gold", new StateConversion(
            "minecraft:gold_ingot",
            null,
            null,
            MekanismChemicalRegistry.getElementSlurry("gold")
        ));
    }

    private static void initializeCompoundConversions() {
        COMPOUND_CONVERSIONS.put("water", new StateConversion(
            "minecraft:ice",
            "minecraft:water_bucket",
            MekanismChemicalRegistry.getCompoundGas("water_vapor"),
            null
        ));

        COMPOUND_CONVERSIONS.put("carbon_dioxide", new StateConversion(
            null,
            null,
            MekanismChemicalRegistry.getCompoundGas("carbon_dioxide"),
            null
        ));

        COMPOUND_CONVERSIONS.put("methane", new StateConversion(
            null,
            null,
            MekanismChemicalRegistry.getCompoundGas("methane"),
            null
        ));

        COMPOUND_CONVERSIONS.put("ammonia", new StateConversion(
            null,
            null,
            MekanismChemicalRegistry.getCompoundGas("ammonia"),
            null
        ));
    }

    public static StateConversion getElementConversion(String elementName) {
        return ELEMENT_CONVERSIONS.get(elementName);
    }

    public static StateConversion getCompoundConversion(String compoundName) {
        return COMPOUND_CONVERSIONS.get(compoundName);
    }

    public static MekanismChemicalRegistry.ChemicalReference getGasForm(String chemicalName) {
        StateConversion elementConv = getElementConversion(chemicalName);
        if (elementConv != null && elementConv.gasForm != null) {
            return elementConv.gasForm;
        }

        StateConversion compoundConv = getCompoundConversion(chemicalName);
        if (compoundConv != null && compoundConv.gasForm != null) {
            return compoundConv.gasForm;
        }

        return null;
    }

    public static MekanismChemicalRegistry.ChemicalReference getSlurryForm(String chemicalName) {
        StateConversion elementConv = getElementConversion(chemicalName);
        if (elementConv != null && elementConv.slurryForm != null) {
            return elementConv.slurryForm;
        }

        return null;
    }
}