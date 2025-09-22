package com.hecookin.chemlibmekanized.integration;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.registry.MekanismChemicalRegistry;

public class ChemicalIntegration {

    public static void registerAllElements() {
        ChemlibMekanized.LOGGER.info("Registering Mekanism chemical wrappers for ChemLib elements");

        registerTestElement("hydrogen", "gas");
        registerTestElement("oxygen", "gas");
        registerTestElement("carbon", "solid");
        registerTestElement("nitrogen", "gas");
        registerTestElement("iron", "solid");

        ChemlibMekanized.LOGGER.info("Registered element chemical wrappers");
    }

    public static void registerAllCompounds() {
        ChemlibMekanized.LOGGER.info("Registering Mekanism chemical wrappers for ChemLib compounds");

        registerTestCompound("water", "liquid");
        registerTestCompound("carbon_dioxide", "gas");
        registerTestCompound("methane", "gas");
        registerTestCompound("ammonia", "gas");

        ChemlibMekanized.LOGGER.info("Registered compound chemical wrappers");
    }

    private static void registerTestElement(String elementName, String matterState) {
        String registryName = "element_" + elementName;

        if ("gas".equals(matterState) || "liquid".equals(matterState)) {
            String gasId = ChemlibMekanized.MODID + ":" + registryName;
            MekanismChemicalRegistry.registerElementGas(elementName, gasId);
        }

        if ("solid".equals(matterState)) {
            String slurryId = ChemlibMekanized.MODID + ":" + registryName + "_slurry";
            MekanismChemicalRegistry.registerElementSlurry(elementName, slurryId);
        }
    }

    private static void registerTestCompound(String compoundName, String matterState) {
        String registryName = "compound_" + compoundName;

        if ("gas".equals(matterState) || "liquid".equals(matterState)) {
            String gasId = ChemlibMekanized.MODID + ":" + registryName;
            MekanismChemicalRegistry.registerCompoundGas(compoundName, gasId);
        }
    }
}