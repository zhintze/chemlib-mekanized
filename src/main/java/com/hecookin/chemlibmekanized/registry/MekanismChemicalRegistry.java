package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class MekanismChemicalRegistry {

    private static final Map<String, ChemicalReference> ELEMENT_GASES = new HashMap<>();
    private static final Map<String, ChemicalReference> COMPOUND_GASES = new HashMap<>();
    private static final Map<String, ChemicalReference> ELEMENT_SLURRIES = new HashMap<>();

    public static class ChemicalReference {
        public final ResourceLocation id;
        public final String colorHex;
        public final boolean radioactive;

        public ChemicalReference(ResourceLocation id, String colorHex, boolean radioactive) {
            this.id = id;
            this.colorHex = colorHex;
            this.radioactive = radioactive;
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

    public static void registerElementGas(String elementName, String colorHex, boolean radioactive) {
        String registryName = "element_" + elementName;
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, registryName);
        ELEMENT_GASES.put(elementName, new ChemicalReference(id, colorHex, radioactive));
    }

    public static void registerCompoundGas(String compoundName, String colorHex) {
        String registryName = "compound_" + compoundName;
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, registryName);
        COMPOUND_GASES.put(compoundName, new ChemicalReference(id, colorHex, false));
    }

    public static void registerElementSlurry(String elementName, String colorHex, boolean radioactive) {
        String registryName = "element_" + elementName + "_slurry";
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, registryName);
        ELEMENT_SLURRIES.put(elementName, new ChemicalReference(id, colorHex, radioactive));
    }

    public static void init() {
        ChemlibMekanized.LOGGER.info("Mekanism Chemical Registry initialized (compatibility mode)");
    }
}