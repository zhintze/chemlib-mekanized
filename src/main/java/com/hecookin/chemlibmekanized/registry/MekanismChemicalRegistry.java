package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

public class MekanismChemicalRegistry {

    private static final Map<String, String> ELEMENT_GASES = new HashMap<>();
    private static final Map<String, String> COMPOUND_GASES = new HashMap<>();
    private static final Map<String, String> ELEMENT_SLURRIES = new HashMap<>();

    public static String getElementGas(String elementName) {
        return ELEMENT_GASES.get(elementName);
    }

    public static String getCompoundGas(String compoundName) {
        return COMPOUND_GASES.get(compoundName);
    }

    public static String getElementSlurry(String elementName) {
        return ELEMENT_SLURRIES.get(elementName);
    }

    public static void registerElementGas(String elementName, String gasId) {
        ELEMENT_GASES.put(elementName, gasId);
    }

    public static void registerCompoundGas(String compoundName, String gasId) {
        COMPOUND_GASES.put(compoundName, gasId);
    }

    public static void registerElementSlurry(String elementName, String slurryId) {
        ELEMENT_SLURRIES.put(elementName, slurryId);
    }

    public static void init() {
        ChemlibMekanized.LOGGER.info("Mekanism Chemical Registry initialized");
    }
}