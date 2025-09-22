package com.hecookin.chemlibmekanized.recipes;

import com.hecookin.chemlibmekanized.ChemlibMekanized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecompositionRecipeConverter {

    private static final Map<String, DecompositionRecipe> DECOMPOSITION_RECIPES = new HashMap<>();

    public static class DecompositionRecipe {
        public final String inputItem;
        public final List<ChemicalOutput> outputs;

        public DecompositionRecipe(String inputItem, List<ChemicalOutput> outputs) {
            this.inputItem = inputItem;
            this.outputs = outputs;
        }
    }

    public static class ChemicalOutput {
        public final String chemical;
        public final int amount;
        public final double probability;

        public ChemicalOutput(String chemical, int amount, double probability) {
            this.chemical = chemical;
            this.amount = amount;
            this.probability = probability;
        }
    }

    public static void initializeDecompositionRecipes() {
        ChemlibMekanized.LOGGER.info("Converting Alchemistry decomposition recipes to Mekanism format");

        addWaterDecomposition();
        addOrganicCompounds();
        addMetalDecomposition();

        ChemlibMekanized.LOGGER.info("Converted {} decomposition recipes", DECOMPOSITION_RECIPES.size());
    }

    private static void addWaterDecomposition() {
        DECOMPOSITION_RECIPES.put("minecraft:water_bucket", new DecompositionRecipe(
            "minecraft:water_bucket",
            List.of(
                new ChemicalOutput(ChemicalConversionRecipes.getGasForm("hydrogen"), 2000, 1.0),
                new ChemicalOutput(ChemicalConversionRecipes.getGasForm("oxygen"), 1000, 1.0)
            )
        ));
    }

    private static void addOrganicCompounds() {
        DECOMPOSITION_RECIPES.put("minecraft:coal", new DecompositionRecipe(
            "minecraft:coal",
            List.of(
                new ChemicalOutput(ChemicalConversionRecipes.getSlurryForm("carbon"), 1000, 1.0)
            )
        ));

        DECOMPOSITION_RECIPES.put("minecraft:diamond", new DecompositionRecipe(
            "minecraft:diamond",
            List.of(
                new ChemicalOutput(ChemicalConversionRecipes.getSlurryForm("carbon"), 2000, 1.0)
            )
        ));
    }

    private static void addMetalDecomposition() {
        DECOMPOSITION_RECIPES.put("minecraft:iron_ingot", new DecompositionRecipe(
            "minecraft:iron_ingot",
            List.of(
                new ChemicalOutput(ChemicalConversionRecipes.getSlurryForm("iron"), 1000, 1.0)
            )
        ));

        DECOMPOSITION_RECIPES.put("minecraft:iron_ore", new DecompositionRecipe(
            "minecraft:iron_ore",
            List.of(
                new ChemicalOutput(ChemicalConversionRecipes.getSlurryForm("iron"), 1500, 0.8),
                new ChemicalOutput(ChemicalConversionRecipes.getSlurryForm("carbon"), 200, 0.3)
            )
        ));
    }

    public static DecompositionRecipe getDecompositionRecipe(String inputItem) {
        return DECOMPOSITION_RECIPES.get(inputItem);
    }

    public static Map<String, DecompositionRecipe> getAllRecipes() {
        return new HashMap<>(DECOMPOSITION_RECIPES);
    }
}