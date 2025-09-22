package com.hecookin.chemlibmekanized.recipes;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.registry.MekanismChemicalRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SynthesisRecipeConverter {

    private static final Map<String, SynthesisRecipe> SYNTHESIS_RECIPES = new HashMap<>();

    public static class SynthesisRecipe {
        public final List<ChemicalInput> inputs;
        public final Object output; // Can be String (item) or ChemicalReference (chemical)
        public final int outputAmount;

        public SynthesisRecipe(List<ChemicalInput> inputs, Object output, int outputAmount) {
            this.inputs = inputs;
            this.output = output;
            this.outputAmount = outputAmount;
        }
    }

    public static class ChemicalInput {
        public final MekanismChemicalRegistry.ChemicalReference chemical;
        public final int amount;

        public ChemicalInput(MekanismChemicalRegistry.ChemicalReference chemical, int amount) {
            this.chemical = chemical;
            this.amount = amount;
        }
    }

    public static void initializeSynthesisRecipes() {
        ChemlibMekanized.LOGGER.info("Converting Alchemistry synthesis recipes to Mekanism format");

        addWaterSynthesis();
        addBasicCompounds();
        addMetalSynthesis();

        ChemlibMekanized.LOGGER.info("Converted {} synthesis recipes", SYNTHESIS_RECIPES.size());
    }

    private static void addWaterSynthesis() {
        String recipeKey = "water_synthesis";
        SYNTHESIS_RECIPES.put(recipeKey, new SynthesisRecipe(
            List.of(
                new ChemicalInput(ChemicalConversionRecipes.getGasForm("hydrogen"), 2000),
                new ChemicalInput(ChemicalConversionRecipes.getGasForm("oxygen"), 1000)
            ),
            "minecraft:water_bucket",
            1
        ));
    }

    private static void addBasicCompounds() {
        String carbonDioxideKey = "carbon_dioxide_synthesis";
        SYNTHESIS_RECIPES.put(carbonDioxideKey, new SynthesisRecipe(
            List.of(
                new ChemicalInput(ChemicalConversionRecipes.getSlurryForm("carbon"), 1000),
                new ChemicalInput(ChemicalConversionRecipes.getGasForm("oxygen"), 2000)
            ),
            ChemicalConversionRecipes.getGasForm("carbon_dioxide"),
            1000
        ));

        String methaneKey = "methane_synthesis";
        SYNTHESIS_RECIPES.put(methaneKey, new SynthesisRecipe(
            List.of(
                new ChemicalInput(ChemicalConversionRecipes.getSlurryForm("carbon"), 1000),
                new ChemicalInput(ChemicalConversionRecipes.getGasForm("hydrogen"), 4000)
            ),
            ChemicalConversionRecipes.getGasForm("methane"),
            1000
        ));
    }

    private static void addMetalSynthesis() {
        String ironIngotKey = "iron_ingot_synthesis";
        SYNTHESIS_RECIPES.put(ironIngotKey, new SynthesisRecipe(
            List.of(
                new ChemicalInput(ChemicalConversionRecipes.getSlurryForm("iron"), 1000)
            ),
            "minecraft:iron_ingot",
            1
        ));
    }

    public static SynthesisRecipe getSynthesisRecipe(String recipeKey) {
        return SYNTHESIS_RECIPES.get(recipeKey);
    }

    public static Map<String, SynthesisRecipe> getAllRecipes() {
        return new HashMap<>(SYNTHESIS_RECIPES);
    }

    public static SynthesisRecipe findRecipeByOutput(String output) {
        return SYNTHESIS_RECIPES.values().stream()
            .filter(recipe -> recipe.output.equals(output))
            .findFirst()
            .orElse(null);
    }
}