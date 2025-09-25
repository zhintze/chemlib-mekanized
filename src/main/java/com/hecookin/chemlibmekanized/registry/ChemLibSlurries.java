package com.hecookin.chemlibmekanized.registry;

import mekanism.api.chemical.Chemical;
import mekanism.common.registration.impl.SlurryRegistryObject;

/**
 * Utility class for accessing ChemLib metal slurries.
 * This is now a facade that delegates to the main ChemlibMekanizedChemicals registry.
 * Maintained for backward compatibility with existing code.
 */
public class ChemLibSlurries {

    // Private constructor to prevent instantiation
    private ChemLibSlurries() {
    }

    /**
     * Get a dirty slurry by metal name.
     * Delegates to the main chemical registry.
     */
    public static Chemical getDirtySlurry(String metalName) {
        return ChemlibMekanizedChemicals.getDirtySlurry(metalName);
    }

    /**
     * Get a clean slurry by metal name.
     * Delegates to the main chemical registry.
     */
    public static Chemical getCleanSlurry(String metalName) {
        return ChemlibMekanizedChemicals.getCleanSlurry(metalName);
    }

    /**
     * Get the full slurry registry object.
     * Delegates to the main chemical registry.
     */
    public static SlurryRegistryObject<Chemical, Chemical> getSlurryPair(String metalName) {
        return ChemlibMekanizedChemicals.getSlurryPair(metalName);
    }

    /**
     * Check if a metal has slurries registered.
     */
    public static boolean hasSlurry(String metalName) {
        return ChemlibMekanizedChemicals.getSlurryPair(metalName) != null;
    }
}