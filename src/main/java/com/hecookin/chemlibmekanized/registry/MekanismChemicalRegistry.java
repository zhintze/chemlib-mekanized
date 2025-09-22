package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class MekanismChemicalRegistry {

    private static final Map<String, ChemicalReference> ELEMENT_GASES = new HashMap<>();
    private static final Map<String, ChemicalReference> COMPOUND_GASES = new HashMap<>();
    private static final Map<String, ChemicalReference> ELEMENT_SLURRIES = new HashMap<>();
    private static final Map<String, ChemicalReference> COMPOUND_SLURRIES = new HashMap<>();
    private static final Map<String, ChemicalReference> ELEMENT_INFUSIONS = new HashMap<>();
    private static final Map<String, ChemicalReference> COMPOUND_INFUSIONS = new HashMap<>();

    public static class ChemicalReference {
        public final ResourceLocation id;
        public final String colorHex;
        public final boolean hazardous;

        public ChemicalReference(ResourceLocation id, String colorHex, boolean hazardous) {
            this.id = id;
            this.colorHex = colorHex;
            this.hazardous = hazardous;
        }
    }

    public static ChemicalReference getElementGas(String elementName) {
        return ELEMENT_GASES.get(elementName);
    }

    public static ChemicalReference getCompoundGas(String compoundName) {
        return COMPOUND_GASES.get(compoundName);
    }

    public static ChemicalReference getElementSlurry(String elementName) {
        return ELEMENT_SLURRIES.get(elementName);
    }

    public static ChemicalReference getCompoundSlurry(String compoundName) {
        return COMPOUND_SLURRIES.get(compoundName);
    }

    public static ChemicalReference getElementInfusion(String elementName) {
        return ELEMENT_INFUSIONS.get(elementName);
    }

    public static ChemicalReference getCompoundInfusion(String compoundName) {
        return COMPOUND_INFUSIONS.get(compoundName);
    }

    public static ChemicalReference registerElementGas(String elementName, String colorHex, boolean hazardous) {
        String registryName = "element_" + elementName;
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, registryName);
        ChemicalReference ref = new ChemicalReference(id, colorHex, hazardous);
        ELEMENT_GASES.put(elementName, ref);
        return ref;
    }

    public static ChemicalReference registerCompoundGas(String compoundName, String colorHex, boolean hazardous) {
        String registryName = "compound_" + compoundName;
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, registryName);
        ChemicalReference ref = new ChemicalReference(id, colorHex, hazardous);
        COMPOUND_GASES.put(compoundName, ref);
        return ref;
    }

    public static ChemicalReference registerElementSlurry(String elementName, String colorHex, boolean hazardous) {
        String registryName = "element_" + elementName + "_slurry";
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, registryName);
        ChemicalReference ref = new ChemicalReference(id, colorHex, hazardous);
        ELEMENT_SLURRIES.put(elementName, ref);
        return ref;
    }

    public static ChemicalReference registerCompoundSlurry(String compoundName, String colorHex, boolean hazardous) {
        String registryName = "compound_" + compoundName + "_slurry";
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, registryName);
        ChemicalReference ref = new ChemicalReference(id, colorHex, hazardous);
        COMPOUND_SLURRIES.put(compoundName, ref);
        return ref;
    }

    public static ChemicalReference registerElementInfusion(String elementName, String colorHex, boolean hazardous) {
        String registryName = "element_" + elementName + "_infusion";
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, registryName);
        ChemicalReference ref = new ChemicalReference(id, colorHex, hazardous);
        ELEMENT_INFUSIONS.put(elementName, ref);
        return ref;
    }

    public static ChemicalReference registerCompoundInfusion(String compoundName, String colorHex, boolean hazardous) {
        String registryName = "compound_" + compoundName + "_infusion";
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, registryName);
        ChemicalReference ref = new ChemicalReference(id, colorHex, hazardous);
        COMPOUND_INFUSIONS.put(compoundName, ref);
        return ref;
    }

    public static void init() {
        ChemlibMekanized.LOGGER.info("Mekanism Chemical Registry initialized (compatibility mode)");
    }
}