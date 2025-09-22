package com.hecookin.chemlibmekanized.integration;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.registry.MekanismChemicalRegistry;

public class ChemicalIntegration {

    public static void registerAllElements() {
        ChemlibMekanized.LOGGER.info("Registering Mekanism chemical wrappers for ChemLib elements");

        // Noble gases (common in Minecraft chemistry)
        MekanismChemicalRegistry.registerElementGas("hydrogen", "B3DFFF", false);
        MekanismChemicalRegistry.registerElementGas("oxygen", "FF8080", false);
        MekanismChemicalRegistry.registerElementGas("nitrogen", "7F7FFF", false);
        MekanismChemicalRegistry.registerElementGas("chlorine", "FFFF00", false);
        MekanismChemicalRegistry.registerElementGas("helium", "FFE4B5", false);

        // Metals (as slurries for processing)
        MekanismChemicalRegistry.registerElementSlurry("iron", "B07156", false);
        MekanismChemicalRegistry.registerElementSlurry("copper", "FF8040", false);
        MekanismChemicalRegistry.registerElementSlurry("gold", "FFD700", false);
        MekanismChemicalRegistry.registerElementSlurry("aluminum", "C0C0C0", false);
        MekanismChemicalRegistry.registerElementSlurry("lead", "8B7D7B", false);

        // Radioactive elements
        MekanismChemicalRegistry.registerElementGas("uranium", "32FF32", true);
        MekanismChemicalRegistry.registerElementSlurry("plutonium", "F54A4A", true);

        // Non-metals as slurries
        MekanismChemicalRegistry.registerElementSlurry("carbon", "2F2F2F", false);
        MekanismChemicalRegistry.registerElementSlurry("silicon", "8B8D7A", false);
        MekanismChemicalRegistry.registerElementSlurry("sulfur", "FFFF00", false);

        ChemlibMekanized.LOGGER.info("Registered {} element chemical wrappers", 15);
    }

    public static void registerAllCompounds() {
        ChemlibMekanized.LOGGER.info("Registering Mekanism chemical wrappers for ChemLib compounds");

        // Common gases
        MekanismChemicalRegistry.registerCompoundGas("water_vapor", "87CEEB");
        MekanismChemicalRegistry.registerCompoundGas("carbon_dioxide", "32C832");
        MekanismChemicalRegistry.registerCompoundGas("methane", "5C5C5C");
        MekanismChemicalRegistry.registerCompoundGas("ammonia", "87CEEB");
        MekanismChemicalRegistry.registerCompoundGas("hydrogen_chloride", "B3FFB3");

        // Organic compounds
        MekanismChemicalRegistry.registerCompoundGas("ethane", "4F4F4F");
        MekanismChemicalRegistry.registerCompoundGas("propane", "FFE4B5");
        MekanismChemicalRegistry.registerCompoundGas("butane", "FFDAB9");

        // Industrial chemicals
        MekanismChemicalRegistry.registerCompoundGas("chlorine_gas", "FFFF7F");
        MekanismChemicalRegistry.registerCompoundGas("sulfur_dioxide", "FFFF80");

        ChemlibMekanized.LOGGER.info("Registered {} compound chemical wrappers", 10);
    }
}