package com.hecookin.chemlibmekanized.util;

import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to generate all chemical item model files
 * based on extracted ChemLib data with proper template references.
 */
public class ModelGenerator {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String MODELS_DIR = "src/main/resources/assets/chemlibmekanized/models/item/";

    public static void main(String[] args) {
        System.out.println("Generating chemical item models...");

        try {
            generateElementModels();
            generateCompoundModels();
            System.out.println("Model generation completed successfully!");
        } catch (Exception e) {
            System.err.println("Error generating models: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void generateElementModels() throws IOException {
        List<ChemLibDataExtractor.ElementData> elements = ChemLibDataExtractor.extractElements();

        for (ChemLibDataExtractor.ElementData element : elements) {
            String templateName = getElementTemplate(element.matterState);
            generateModelFile(element.name, templateName);
            System.out.println("Generated model for element: " + element.name + " (" + element.abbreviation + ") -> " + templateName);
        }
    }

    private static void generateCompoundModels() throws IOException {
        List<ChemLibDataExtractor.CompoundData> compounds = ChemLibDataExtractor.extractCompounds();

        for (ChemLibDataExtractor.CompoundData compound : compounds) {
            String templateName = getCompoundTemplate(compound);
            generateModelFile(compound.name, templateName);
            System.out.println("Generated model for compound: " + compound.name + " -> " + templateName);
        }
    }

    private static String getElementTemplate(String matterState) {
        return switch (matterState.toLowerCase()) {
            case "solid" -> "element_solid_model";
            case "liquid" -> "element_liquid_model";
            case "gas" -> "element_gas_model";
            default -> "element_solid_model"; // fallback
        };
    }

    private static String getCompoundTemplate(ChemLibDataExtractor.CompoundData compound) {
        String matterState = compound.matterState.toLowerCase();

        // Special logic for dust compounds (same as renderer logic)
        if ("solid".equals(matterState)) {
            String name = compound.name.toLowerCase();
            if (name.contains("oxide") || name.contains("sulfate") ||
                name.contains("chromate") || name.contains("purple") ||
                name.contains("iodide")) {
                return "compound_dust_model";
            }
        }

        return switch (matterState) {
            case "solid" -> "compound_solid_model";
            case "liquid" -> "compound_liquid_model";
            case "gas" -> "compound_gas_model";
            default -> "compound_solid_model"; // fallback
        };
    }

    private static void generateModelFile(String itemName, String templateName) throws IOException {
        Map<String, String> model = new HashMap<>();
        model.put("parent", "chemlibmekanized:item/" + templateName);

        File modelsDir = new File(MODELS_DIR);
        if (!modelsDir.exists()) {
            modelsDir.mkdirs();
        }

        File modelFile = new File(modelsDir, itemName + ".json");
        try (FileWriter writer = new FileWriter(modelFile)) {
            GSON.toJson(model, writer);
        }
    }
}