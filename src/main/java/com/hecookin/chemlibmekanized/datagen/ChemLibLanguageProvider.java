package com.hecookin.chemlibmekanized.datagen;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import com.hecookin.chemlibmekanized.registry.ChemLibFluidRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.List;

public class ChemLibLanguageProvider extends LanguageProvider {

    public ChemLibLanguageProvider(PackOutput output) {
        super(output, ChemlibMekanized.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ChemlibMekanized.LOGGER.info("Generating translations for ChemLib extracted content");

        addCreativeTabTranslations();
        addElementTranslations();
        addCompoundTranslations();
        addFluidTranslations();
        addChemicalTranslations();

        ChemlibMekanized.LOGGER.info("Completed generating translations");
    }

    private void addCreativeTabTranslations() {
        add("itemGroup.chemlibmekanized.elements", "Elements");
        add("itemGroup.chemlibmekanized.compounds", "Compounds");
        add("itemGroup.chemlibmekanized.metals", "Metals");
        add("itemGroup.chemlibmekanized.non_metals", "Non-Metals");
        add("itemGroup.chemlibmekanized.metalloids", "Metalloids");
    }

    private void addElementTranslations() {
        List<ChemLibDataExtractor.ElementData> elements = ChemLibDataExtractor.extractElements();

        for (ChemLibDataExtractor.ElementData element : elements) {
            String translationKey = "item." + ChemlibMekanized.MODID + "." + element.name;
            String displayName = formatChemicalName(element.name);
            add(translationKey, displayName);

        }
    }

    private void addCompoundTranslations() {
        List<ChemLibDataExtractor.CompoundData> compounds = ChemLibDataExtractor.extractCompounds();

        for (ChemLibDataExtractor.CompoundData compound : compounds) {
            String translationKey = "item." + ChemlibMekanized.MODID + "." + compound.name;
            String displayName = formatChemicalName(compound.name);
            add(translationKey, displayName);

        }
    }

    private void addFluidTranslations() {
        List<ChemLibFluidRegistry.ChemLibFluidEntry> fluids = ChemLibFluidRegistry.getAllFluids();

        for (ChemLibFluidRegistry.ChemLibFluidEntry fluid : fluids) {
            String fluidTypeName = formatChemicalName(fluid.name());
            add("fluid_type." + ChemlibMekanized.MODID + "." + fluid.name(), fluidTypeName);
        }
    }

    private void addChemicalTranslations() {
        // Gas Elements - following Mekanism's chemical.{modid}.{name} pattern
        add("chemical." + ChemlibMekanized.MODID + ".element_hydrogen", "Hydrogen");
        add("chemical." + ChemlibMekanized.MODID + ".element_oxygen", "Oxygen");
        add("chemical." + ChemlibMekanized.MODID + ".element_nitrogen", "Nitrogen");
        add("chemical." + ChemlibMekanized.MODID + ".element_chlorine", "Chlorine");
        add("chemical." + ChemlibMekanized.MODID + ".element_helium", "Helium");
        add("chemical." + ChemlibMekanized.MODID + ".element_uranium", "Uranium");

        // Gas Compounds - following Mekanism's chemical.{modid}.{name} pattern
        add("chemical." + ChemlibMekanized.MODID + ".compound_water_vapor", "Water Vapor");
        add("chemical." + ChemlibMekanized.MODID + ".compound_carbon_dioxide", "Carbon Dioxide");
        add("chemical." + ChemlibMekanized.MODID + ".compound_methane", "Methane");
        add("chemical." + ChemlibMekanized.MODID + ".compound_ammonia", "Ammonia");
        add("chemical." + ChemlibMekanized.MODID + ".compound_hydrogen_chloride", "Hydrogen Chloride");
        add("chemical." + ChemlibMekanized.MODID + ".compound_ethane", "Ethane");
        add("chemical." + ChemlibMekanized.MODID + ".compound_propane", "Propane");
        add("chemical." + ChemlibMekanized.MODID + ".compound_butane", "Butane");
        add("chemical." + ChemlibMekanized.MODID + ".compound_chlorine_gas", "Chlorine Gas");
        add("chemical." + ChemlibMekanized.MODID + ".compound_sulfur_dioxide", "Sulfur Dioxide");
    }

    private String formatChemicalName(String name) {
        String[] words = name.split("_");
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                formatted.append(" ");
            }
            String word = words[i];
            formatted.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase());
        }

        return formatted.toString();
    }
}