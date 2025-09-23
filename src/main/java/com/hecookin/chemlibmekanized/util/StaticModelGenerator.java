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
 * Utility class to generate static model files for each chemical item
 * using standard item/generated models with direct texture references
 */
public class StaticModelGenerator {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String MODELS_DIR = "src/main/resources/assets/chemlibmekanized/models/item/";

    public static void main(String[] args) {
        System.out.println("Generating static chemical item models...");

        try {
            generateElementModels();
            generateCompoundModels();
            System.out.println("Static model generation completed successfully!");
        } catch (Exception e) {
            System.err.println("Error generating static models: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void generateElementModels() throws IOException {
        List<ChemLibDataExtractor.ElementData> elements = ChemLibDataExtractor.extractElements();

        for (ChemLibDataExtractor.ElementData element : elements) {
            generateStaticModelFile(element.name);
            System.out.println("Generated static model for element: " + element.name + " (" + element.abbreviation + ")");
        }
    }

    private static void generateCompoundModels() throws IOException {
        List<ChemLibDataExtractor.CompoundData> compounds = ChemLibDataExtractor.extractCompounds();

        for (ChemLibDataExtractor.CompoundData compound : compounds) {
            generateStaticModelFile(compound.name);
            System.out.println("Generated static model for compound: " + compound.name);
        }
    }

    private static void generateStaticModelFile(String itemName) throws IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("parent", "item/generated");

        Map<String, String> textures = new HashMap<>();
        textures.put("layer0", "chemlibmekanized:item/chemicals/" + itemName);
        model.put("textures", textures);

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