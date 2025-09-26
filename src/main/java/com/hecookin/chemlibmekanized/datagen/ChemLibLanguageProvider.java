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
        addMetalItemTranslations();
        addCrystalTranslations();
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

    private void addMetalItemTranslations() {
        List<ChemLibDataExtractor.ElementData> metals = ChemLibDataExtractor.extractMetals();

        for (ChemLibDataExtractor.ElementData metal : metals) {
            String elementName = formatChemicalName(metal.name);

            // Ingot translations
            String ingotKey = "item." + ChemlibMekanized.MODID + "." + metal.name + "_ingot";
            add(ingotKey, elementName + " Ingot");

            // Nugget translations
            String nuggetKey = "item." + ChemlibMekanized.MODID + "." + metal.name + "_nugget";
            add(nuggetKey, elementName + " Nugget");

            // Plate translations
            String plateKey = "item." + ChemlibMekanized.MODID + "." + metal.name + "_plate";
            add(plateKey, elementName + " Plate");
        }
    }

    private void addCrystalTranslations() {
        // All metals/metalloids that have crystals
        String[][] crystals = {
            // Common metals
            {"aluminum", "Aluminum"}, {"titanium", "Titanium"}, {"zinc", "Zinc"},
            {"nickel", "Nickel"}, {"silver", "Silver"}, {"platinum", "Platinum"},
            {"copper", "Copper"}, {"iron", "Iron"}, {"gold", "Gold"}, {"lead", "Lead"}, {"tin", "Tin"},
            {"osmium", "Osmium"}, {"uranium", "Uranium"},
            // Rare metals
            {"tungsten", "Tungsten"}, {"chromium", "Chromium"}, {"manganese", "Manganese"},
            {"cobalt", "Cobalt"}, {"cadmium", "Cadmium"}, {"mercury", "Mercury"},
            // Precious metals
            {"palladium", "Palladium"}, {"rhodium", "Rhodium"}, {"iridium", "Iridium"}, {"ruthenium", "Ruthenium"},
            // Radioactive
            {"thorium", "Thorium"}, {"polonium", "Polonium"},
            // Alkali/Alkaline
            {"lithium", "Lithium"}, {"sodium", "Sodium"}, {"potassium", "Potassium"},
            {"calcium", "Calcium"}, {"magnesium", "Magnesium"}, {"barium", "Barium"},
            {"strontium", "Strontium"}, {"rubidium", "Rubidium"}, {"cesium", "Cesium"},
            {"francium", "Francium"}, {"radium", "Radium"},
            // Metalloids
            {"silicon", "Silicon"}, {"germanium", "Germanium"}, {"antimony", "Antimony"},
            {"bismuth", "Bismuth"}, {"boron", "Boron"}, {"arsenic", "Arsenic"}, {"tellurium", "Tellurium"},
            // Lanthanides (rare earths)
            {"cerium", "Cerium"}, {"neodymium", "Neodymium"}, {"lanthanum", "Lanthanum"},
            {"gadolinium", "Gadolinium"}, {"europium", "Europium"}, {"samarium", "Samarium"},
            {"terbium", "Terbium"}, {"dysprosium", "Dysprosium"}, {"holmium", "Holmium"},
            {"erbium", "Erbium"}, {"thulium", "Thulium"}, {"ytterbium", "Ytterbium"},
            {"lutetium", "Lutetium"}, {"praseodymium", "Praseodymium"}, {"promethium", "Promethium"},
            // Other metals
            {"indium", "Indium"}, {"gallium", "Gallium"}, {"hafnium", "Hafnium"},
            {"tantalum", "Tantalum"}, {"rhenium", "Rhenium"}, {"molybdenum", "Molybdenum"},
            {"vanadium", "Vanadium"}, {"niobium", "Niobium"}, {"beryllium", "Beryllium"},
            {"zirconium", "Zirconium"}, {"scandium", "Scandium"}, {"yttrium", "Yttrium"},
            {"thallium", "Thallium"}, {"technetium", "Technetium"},
            // Actinides
            {"actinium", "Actinium"}, {"protactinium", "Protactinium"}, {"neptunium", "Neptunium"},
            {"plutonium", "Plutonium"}, {"americium", "Americium"}, {"curium", "Curium"},
            {"berkelium", "Berkelium"}, {"californium", "Californium"},
            // Super-heavy elements
            {"lawrencium", "Lawrencium"}, {"einsteinium", "Einsteinium"}, {"fermium", "Fermium"},
            {"mendelevium", "Mendelevium"}, {"nobelium", "Nobelium"}, {"rutherfordium", "Rutherfordium"},
            {"dubnium", "Dubnium"}, {"seaborgium", "Seaborgium"}, {"bohrium", "Bohrium"},
            {"hassium", "Hassium"}, {"meitnerium", "Meitnerium"}, {"darmstadtium", "Darmstadtium"},
            {"roentgenium", "Roentgenium"}, {"copernicium", "Copernicium"}, {"nihonium", "Nihonium"},
            {"flerovium", "Flerovium"}, {"moscovium", "Moscovium"}, {"livermorium", "Livermorium"}
        };

        for (String[] crystal : crystals) {
            String id = crystal[0];
            String name = crystal[1];
            add("item." + ChemlibMekanized.MODID + "." + id + "_crystal", name + " Crystal");
        }
    }

    private void addFluidTranslations() {
        // Add all fluids (including gas fluids now that they're in getAllFluids())
        List<ChemLibFluidRegistry.ChemLibFluidEntry> fluids = ChemLibFluidRegistry.getAllFluids();
        for (ChemLibFluidRegistry.ChemLibFluidEntry fluid : fluids) {
            String fluidTypeName = formatChemicalName(fluid.name());
            // Add fluid type translation
            add("fluid_type." + ChemlibMekanized.MODID + "." + fluid.name(), fluidTypeName);
            // Add block translation for fluid blocks
            add("block." + ChemlibMekanized.MODID + "." + fluid.name() + "_block", fluidTypeName);
        }
    }


    private void addSlurryTranslations() {
        // Define all our slurry elements with proper display names
        String[][] slurries = {
            // Common metals
            {"aluminum", "Aluminum"}, {"titanium", "Titanium"}, {"zinc", "Zinc"},
            {"nickel", "Nickel"}, {"silver", "Silver"}, {"platinum", "Platinum"},
            // Rare metals
            {"tungsten", "Tungsten"}, {"chromium", "Chromium"}, {"manganese", "Manganese"},
            {"cobalt", "Cobalt"}, {"cadmium", "Cadmium"}, {"mercury", "Mercury"},
            // Precious metals
            {"palladium", "Palladium"}, {"rhodium", "Rhodium"}, {"iridium", "Iridium"}, {"ruthenium", "Ruthenium"},
            // Radioactive
            {"thorium", "Thorium"},
            // Alkali/Alkaline
            {"lithium", "Lithium"}, {"sodium", "Sodium"}, {"potassium", "Potassium"},
            {"calcium", "Calcium"}, {"magnesium", "Magnesium"}, {"barium", "Barium"}, {"strontium", "Strontium"},
            // Metalloids
            {"silicon", "Silicon"}, {"germanium", "Germanium"}, {"antimony", "Antimony"}, {"bismuth", "Bismuth"},
            // Lanthanides
            {"cerium", "Cerium"}, {"neodymium", "Neodymium"}, {"lanthanum", "Lanthanum"},
            {"gadolinium", "Gadolinium"}, {"europium", "Europium"}, {"samarium", "Samarium"},
            {"terbium", "Terbium"}, {"dysprosium", "Dysprosium"}, {"holmium", "Holmium"},
            {"erbium", "Erbium"}, {"thulium", "Thulium"}, {"ytterbium", "Ytterbium"},
            {"lutetium", "Lutetium"}, {"praseodymium", "Praseodymium"}, {"promethium", "Promethium"},
            // Other metals
            {"indium", "Indium"}, {"gallium", "Gallium"}, {"hafnium", "Hafnium"},
            {"tantalum", "Tantalum"}, {"rhenium", "Rhenium"}, {"molybdenum", "Molybdenum"},
            {"vanadium", "Vanadium"}, {"niobium", "Niobium"}, {"beryllium", "Beryllium"},
            {"zirconium", "Zirconium"}, {"scandium", "Scandium"}, {"yttrium", "Yttrium"},
            {"thallium", "Thallium"}, {"polonium", "Polonium"}, {"technetium", "Technetium"},
            {"rubidium", "Rubidium"}, {"cesium", "Cesium"}, {"francium", "Francium"}, {"radium", "Radium"},
            // Metalloids
            {"arsenic", "Arsenic"}, {"tellurium", "Tellurium"}, {"boron", "Boron"}, {"astatine", "Astatine"},
            // Alternative spellings
            {"aluminium", "Aluminium"}, {"gallium_arsenide", "Gallium Arsenide"},
            // Actinides
            {"actinium", "Actinium"}, {"protactinium", "Protactinium"}, {"neptunium", "Neptunium"},
            {"plutonium", "Plutonium"}, {"americium", "Americium"}, {"curium", "Curium"},
            {"berkelium", "Berkelium"}, {"californium", "Californium"},
            // Super-heavy elements
            {"lawrencium", "Lawrencium"}, {"einsteinium", "Einsteinium"}, {"fermium", "Fermium"},
            {"mendelevium", "Mendelevium"}, {"nobelium", "Nobelium"}, {"rutherfordium", "Rutherfordium"},
            {"dubnium", "Dubnium"}, {"seaborgium", "Seaborgium"}, {"bohrium", "Bohrium"},
            {"hassium", "Hassium"}, {"meitnerium", "Meitnerium"}, {"darmstadtium", "Darmstadtium"},
            {"roentgenium", "Roentgenium"}, {"copernicium", "Copernicium"}, {"nihonium", "Nihonium"},
            {"flerovium", "Flerovium"}, {"moscovium", "Moscovium"}, {"livermorium", "Livermorium"},
            // Alternative names
            {"wolfram", "Wolfram"},
            // Special materials
            {"quartz", "Quartz"}, {"lapis", "Lapis"}, {"coal", "Coal"},
            {"netherite_scrap", "Netherite Scrap"}, {"emerald", "Emerald"}
        };

        // Add translations for each slurry
        for (String[] slurry : slurries) {
            String id = slurry[0];
            String name = slurry[1];
            add("chemical." + ChemlibMekanized.MODID + ".dirty_" + id, "Dirty " + name + " Slurry");
            add("chemical." + ChemlibMekanized.MODID + ".clean_" + id, "Clean " + name + " Slurry");
        }
    }

    private void addChemicalTranslations() {
        // Special items
        add("item.chemlibmekanized.periodic_table", "Periodic Table");
        add("item.chemlibmekanized.periodic_table.tooltip", "Right-click to view the periodic table of elements");
        add("screen.chemlibmekanized.periodic_table", "Periodic Table of Elements");

        // Add gas chemical translations for vaporized compounds
        add("chemical." + ChemlibMekanized.MODID + ".ethanol", "Ethanol");
        add("chemical." + ChemlibMekanized.MODID + ".propan_1_ol", "Propan-1-ol");
        add("chemical." + ChemlibMekanized.MODID + ".propan_2_ol", "Propan-2-ol");
        add("chemical." + ChemlibMekanized.MODID + ".pentane", "Pentane");
        add("chemical." + ChemlibMekanized.MODID + ".hexane", "Hexane");
        add("chemical." + ChemlibMekanized.MODID + ".heptane", "Heptane");
        add("chemical." + ChemlibMekanized.MODID + ".acetic_acid", "Acetic Acid");
        add("chemical." + ChemlibMekanized.MODID + ".carbon_disulfide", "Carbon Disulfide");
        // Add slurry translations
        addSlurryTranslations();

        // Gas Elements - following Mekanism's chemical.{modid}.{name} pattern
        // Note: Registration doesn't use element_ prefix
        add("chemical." + ChemlibMekanized.MODID + ".hydrogen", "Hydrogen");
        add("chemical." + ChemlibMekanized.MODID + ".oxygen", "Oxygen");
        add("chemical." + ChemlibMekanized.MODID + ".nitrogen", "Nitrogen");
        add("chemical." + ChemlibMekanized.MODID + ".chlorine", "Chlorine");
        add("chemical." + ChemlibMekanized.MODID + ".fluorine", "Fluorine");
        add("chemical." + ChemlibMekanized.MODID + ".helium", "Helium");
        add("chemical." + ChemlibMekanized.MODID + ".neon", "Neon");
        add("chemical." + ChemlibMekanized.MODID + ".argon", "Argon");
        add("chemical." + ChemlibMekanized.MODID + ".krypton", "Krypton");
        add("chemical." + ChemlibMekanized.MODID + ".xenon", "Xenon");
        add("chemical." + ChemlibMekanized.MODID + ".radon", "Radon");
        add("chemical." + ChemlibMekanized.MODID + ".uranium", "Uranium");

        // Gas Compounds - following Mekanism's chemical.{modid}.{name} pattern
        // Note: Registration doesn't use compound_ prefix
        add("chemical." + ChemlibMekanized.MODID + ".carbon_dioxide", "Carbon Dioxide");
        add("chemical." + ChemlibMekanized.MODID + ".methane", "Methane");
        add("chemical." + ChemlibMekanized.MODID + ".ammonia", "Ammonia");
        add("chemical." + ChemlibMekanized.MODID + ".hydrogen_chloride", "Hydrogen Chloride");
        add("chemical." + ChemlibMekanized.MODID + ".ethane", "Ethane");
        add("chemical." + ChemlibMekanized.MODID + ".propane", "Propane");
        add("chemical." + ChemlibMekanized.MODID + ".butane", "Butane");
        add("chemical." + ChemlibMekanized.MODID + ".acetylene", "Acetylene");
        add("chemical." + ChemlibMekanized.MODID + ".ethylene", "Ethylene");
        add("chemical." + ChemlibMekanized.MODID + ".hydrogen_sulfide", "Hydrogen Sulfide");
        add("chemical." + ChemlibMekanized.MODID + ".nitrogen_dioxide", "Nitrogen Dioxide");
        add("chemical." + ChemlibMekanized.MODID + ".carbon_monoxide", "Carbon Monoxide");
        add("chemical." + ChemlibMekanized.MODID + ".nitric_oxide", "Nitric Oxide");
        add("chemical." + ChemlibMekanized.MODID + ".sulfur_dioxide", "Sulfur Dioxide");
        add("chemical." + ChemlibMekanized.MODID + ".ammonium", "Ammonium");

        // Acid chemicals for PRC processing
        add("chemical." + ChemlibMekanized.MODID + ".nitric_acid", "Nitric Acid");
        add("chemical." + ChemlibMekanized.MODID + ".hydrochloric_acid", "Hydrochloric Acid");
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