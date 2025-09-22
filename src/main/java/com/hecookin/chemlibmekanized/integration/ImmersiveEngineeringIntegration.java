package com.hecookin.chemlibmekanized.integration;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.registry.MekanismChemicalRegistry;

import java.util.HashMap;
import java.util.Map;

public class ImmersiveEngineeringIntegration {

    private static final Map<String, ChemicalProcessingRecipe> CHEMICAL_PROCESSING_RECIPES = new HashMap<>();

    public static class ChemicalProcessingRecipe {
        public final String inputFluid;
        public final MekanismChemicalRegistry.ChemicalReference outputChemical;
        public final int processingTime;
        public final int energyRequired;

        public ChemicalProcessingRecipe(String inputFluid, MekanismChemicalRegistry.ChemicalReference outputChemical, int processingTime, int energyRequired) {
            this.inputFluid = inputFluid;
            this.outputChemical = outputChemical;
            this.processingTime = processingTime;
            this.energyRequired = energyRequired;
        }
    }

    public static void initializeIntegration() {
        ChemlibMekanized.LOGGER.info("Initializing ImmersiveEngineering chemical processing integration");

        // Refinery processes for converting IE fluids to Mekanism chemicals
        addRefineryToChemicalRecipe("immersiveengineering:crude_oil",
            MekanismChemicalRegistry.getCompoundGas("methane"), 100, 1000);

        addRefineryToChemicalRecipe("immersiveengineering:diesel",
            MekanismChemicalRegistry.getCompoundGas("butane"), 80, 800);

        addRefineryToChemicalRecipe("immersiveengineering:gasoline",
            MekanismChemicalRegistry.getCompoundGas("ethane"), 60, 600);

        // Chemical Thrower processes for applying Mekanism chemicals
        addChemicalThrowerRecipe(MekanismChemicalRegistry.getElementGas("chlorine"));
        addChemicalThrowerRecipe(MekanismChemicalRegistry.getCompoundGas("ammonia"));

        // Arc Furnace electrolysis for element extraction
        addArcFurnaceElectrolysis("minecraft:iron_ore",
            MekanismChemicalRegistry.getElementSlurry("iron"), 200, 2000);

        addArcFurnaceElectrolysis("minecraft:copper_ore",
            MekanismChemicalRegistry.getElementSlurry("copper"), 200, 2000);

        addArcFurnaceElectrolysis("minecraft:gold_ore",
            MekanismChemicalRegistry.getElementSlurry("gold"), 300, 3000);

        ChemlibMekanized.LOGGER.info("Registered {} ImmersiveEngineering chemical processing recipes",
            CHEMICAL_PROCESSING_RECIPES.size());
    }

    private static void addRefineryToChemicalRecipe(String inputFluid, MekanismChemicalRegistry.ChemicalReference outputChemical, int time, int energy) {
        if (outputChemical != null) {
            String recipeKey = "refinery_" + inputFluid.replace(":", "_");
            CHEMICAL_PROCESSING_RECIPES.put(recipeKey,
                new ChemicalProcessingRecipe(inputFluid, outputChemical, time, energy));
        }
    }

    private static void addChemicalThrowerRecipe(MekanismChemicalRegistry.ChemicalReference chemical) {
        if (chemical != null) {
            String recipeKey = "chemical_thrower_" + chemical.id.getPath();
            CHEMICAL_PROCESSING_RECIPES.put(recipeKey,
                new ChemicalProcessingRecipe(chemical.id.toString(), chemical, 20, 100));
        }
    }

    private static void addArcFurnaceElectrolysis(String inputOre, MekanismChemicalRegistry.ChemicalReference outputSlurry, int time, int energy) {
        if (outputSlurry != null) {
            String recipeKey = "arc_furnace_" + inputOre.replace(":", "_");
            CHEMICAL_PROCESSING_RECIPES.put(recipeKey,
                new ChemicalProcessingRecipe(inputOre, outputSlurry, time, energy));
        }
    }

    public static ChemicalProcessingRecipe getProcessingRecipe(String recipeKey) {
        return CHEMICAL_PROCESSING_RECIPES.get(recipeKey);
    }

    public static Map<String, ChemicalProcessingRecipe> getAllRecipes() {
        return new HashMap<>(CHEMICAL_PROCESSING_RECIPES);
    }

    public static boolean hasChemicalThrowerSupport(MekanismChemicalRegistry.ChemicalReference chemical) {
        return chemical != null && CHEMICAL_PROCESSING_RECIPES.containsKey("chemical_thrower_" + chemical.id.getPath());
    }
}