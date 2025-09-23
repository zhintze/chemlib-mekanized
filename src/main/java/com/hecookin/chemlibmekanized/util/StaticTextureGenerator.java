package com.hecookin.chemlibmekanized.util;

import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Utility to generate static texture files for each chemical item
 * by combining layer textures into individual files
 */
public class StaticTextureGenerator {

    private static final String TEXTURES_DIR = "src/main/resources/assets/chemlibmekanized/textures/items/";
    private static final String OUTPUT_DIR = "src/main/resources/assets/chemlibmekanized/textures/items/chemicals/";

    public static void main(String[] args) {
        System.out.println("Generating static texture files for all chemicals...");

        try {
            // Create output directory
            File outputDir = new File(OUTPUT_DIR);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            generateElementTextures();
            generateCompoundTextures();
            System.out.println("Static texture generation completed successfully!");
        } catch (Exception e) {
            System.err.println("Error generating static textures: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void generateElementTextures() throws IOException {
        List<ChemLibDataExtractor.ElementData> elements = ChemLibDataExtractor.extractElements();

        for (ChemLibDataExtractor.ElementData element : elements) {
            String templateName = getElementTemplate(element.matterState);
            generateStaticTexture(element.name, templateName);
            System.out.println("Generated texture for element: " + element.name + " (" + element.abbreviation + ") -> " + templateName);
        }
    }

    private static void generateCompoundTextures() throws IOException {
        List<ChemLibDataExtractor.CompoundData> compounds = ChemLibDataExtractor.extractCompounds();

        for (ChemLibDataExtractor.CompoundData compound : compounds) {
            String templateName = getCompoundTemplate(compound);
            generateStaticTexture(compound.name, templateName);
            System.out.println("Generated texture for compound: " + compound.name + " -> " + templateName);
        }
    }

    private static String getElementTemplate(String matterState) {
        return switch (matterState.toLowerCase()) {
            case "solid" -> "element_solid";
            case "liquid" -> "element_liquid";
            case "gas" -> "element_gas";
            default -> "element_solid"; // fallback
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
                return "compound_dust";
            }
        }

        return switch (matterState) {
            case "solid" -> "compound_solid";
            case "liquid" -> "compound_liquid";
            case "gas" -> "compound_gas";
            default -> "compound_solid"; // fallback
        };
    }

    private static void generateStaticTexture(String itemName, String templateName) throws IOException {
        // Load layer textures
        String layer0Path = TEXTURES_DIR + templateName + "_layer_0.png";
        String layer1Path = TEXTURES_DIR + templateName + "_layer_1.png";

        File layer0File = new File(layer0Path);
        File layer1File = new File(layer1Path);

        if (!layer0File.exists()) {
            System.err.println("Warning: Layer 0 texture not found: " + layer0Path);
            return;
        }

        BufferedImage layer0 = ImageIO.read(layer0File);
        BufferedImage combinedImage = new BufferedImage(layer0.getWidth(), layer0.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = combinedImage.createGraphics();
        g2d.setComposite(AlphaComposite.Src);

        // Draw layer 0 (base)
        g2d.drawImage(layer0, 0, 0, null);

        // Draw layer 1 (overlay) if it exists
        if (layer1File.exists()) {
            BufferedImage layer1 = ImageIO.read(layer1File);
            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.drawImage(layer1, 0, 0, null);
        }

        g2d.dispose();

        // Save combined texture
        String outputPath = OUTPUT_DIR + itemName + ".png";
        File outputFile = new File(outputPath);
        ImageIO.write(combinedImage, "PNG", outputFile);
    }
}