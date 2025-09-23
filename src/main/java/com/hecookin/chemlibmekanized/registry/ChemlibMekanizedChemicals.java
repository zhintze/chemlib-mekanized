package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry for all ChemLib-Mekanized chemicals using proper Mekanism Chemical objects.
 * Replaces the old ChemicalReference system with actual Mekanism integration.
 */
public class ChemlibMekanizedChemicals {

    private ChemlibMekanizedChemicals() {
    }

    public static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(ChemlibMekanized.MODID);

    // Color mapping for ChemLib chemical colors
    private static final Map<String, Integer> CHEMLIB_COLORS = new HashMap<>();

    // Initialize ChemLib color mapping
    static {
        loadChemLibColors();
    }

    // Gas Elements - Using ChemLib colors dynamically
    public static final DeferredChemical<Chemical> HYDROGEN_GAS = CHEMICALS.register("element_hydrogen", getChemLibColor("hydrogen", 0xB3DFFF));
    public static final DeferredChemical<Chemical> OXYGEN_GAS = CHEMICALS.register("element_oxygen", getChemLibColor("oxygen", 0xFF8080));
    public static final DeferredChemical<Chemical> NITROGEN_GAS = CHEMICALS.register("element_nitrogen", getChemLibColor("nitrogen", 0x7F7FFF));
    public static final DeferredChemical<Chemical> CHLORINE_GAS = CHEMICALS.register("element_chlorine", getChemLibColor("chlorine", 0xFFFF00));
    public static final DeferredChemical<Chemical> FLUORINE_GAS = CHEMICALS.register("element_fluorine", getChemLibColor("fluorine", 0xFFFFAA));
    public static final DeferredChemical<Chemical> HELIUM_GAS = CHEMICALS.register("element_helium", getChemLibColor("helium", 0xFFE4B5));
    public static final DeferredChemical<Chemical> NEON_GAS = CHEMICALS.register("element_neon", getChemLibColor("neon", 0xFFB6C1));
    public static final DeferredChemical<Chemical> ARGON_GAS = CHEMICALS.register("element_argon", getChemLibColor("argon", 0x80E0FF));
    public static final DeferredChemical<Chemical> KRYPTON_GAS = CHEMICALS.register("element_krypton", getChemLibColor("krypton", 0xC0FFB6));
    public static final DeferredChemical<Chemical> XENON_GAS = CHEMICALS.register("element_xenon", getChemLibColor("xenon", 0xB3B3FF));
    public static final DeferredChemical<Chemical> RADON_GAS = CHEMICALS.register("element_radon", getChemLibColor("radon", 0xFF6347));
    public static final DeferredChemical<Chemical> URANIUM_GAS = CHEMICALS.register("element_uranium", getChemLibColor("uranium", 0x32FF32));

    // Gas Compounds - Using ChemLib colors dynamically
    public static final DeferredChemical<Chemical> CARBON_DIOXIDE = CHEMICALS.register("compound_carbon_dioxide", getChemLibColor("carbon_dioxide", 0x32C832));
    public static final DeferredChemical<Chemical> METHANE = CHEMICALS.register("compound_methane", getChemLibColor("methane", 0x5C5C5C));
    public static final DeferredChemical<Chemical> AMMONIA = CHEMICALS.register("compound_ammonia", getChemLibColor("ammonia", 0x87CEEB));
    public static final DeferredChemical<Chemical> HYDROGEN_CHLORIDE_GAS = CHEMICALS.register("compound_hydrogen_chloride", getChemLibColor("hydrogen_chloride", 0xB3FFB3));
    public static final DeferredChemical<Chemical> ETHANE = CHEMICALS.register("compound_ethane", getChemLibColor("ethane", 0x4F4F4F));
    public static final DeferredChemical<Chemical> PROPANE = CHEMICALS.register("compound_propane", getChemLibColor("propane", 0xFFE4B5));
    public static final DeferredChemical<Chemical> BUTANE = CHEMICALS.register("compound_butane", getChemLibColor("butane", 0xFFDAB9));
    public static final DeferredChemical<Chemical> ACETYLENE = CHEMICALS.register("compound_acetylene", getChemLibColor("acetylene", 0xFFFFCC));
    public static final DeferredChemical<Chemical> ETHYLENE = CHEMICALS.register("compound_ethylene", getChemLibColor("ethylene", 0xE6E6FA));
    public static final DeferredChemical<Chemical> HYDROGEN_SULFIDE = CHEMICALS.register("compound_hydrogen_sulfide", getChemLibColor("hydrogen_sulfide", 0xFFFF99));
    public static final DeferredChemical<Chemical> NITROGEN_DIOXIDE = CHEMICALS.register("compound_nitrogen_dioxide", getChemLibColor("nitrogen_dioxide", 0xFF4500));
    public static final DeferredChemical<Chemical> CARBON_MONOXIDE = CHEMICALS.register("compound_carbon_monoxide", getChemLibColor("carbon_monoxide", 0x808080));
    public static final DeferredChemical<Chemical> NITRIC_OXIDE = CHEMICALS.register("compound_nitric_oxide", getChemLibColor("nitric_oxide", 0xDDA0DD));
    public static final DeferredChemical<Chemical> SULFUR_DIOXIDE_GAS = CHEMICALS.register("compound_sulfur_dioxide", getChemLibColor("sulfur_dioxide", 0xFFFF80));

    /**
     * Get element gas chemical by name.
     *
     * @param elementName The element name (e.g., "hydrogen", "oxygen")
     * @return The Chemical object, or null if not found
     */
    public static Chemical getElementGas(String elementName) {
        return switch (elementName.toLowerCase()) {
            case "hydrogen" -> HYDROGEN_GAS.get();
            case "oxygen" -> OXYGEN_GAS.get();
            case "nitrogen" -> NITROGEN_GAS.get();
            case "chlorine" -> CHLORINE_GAS.get();
            case "fluorine" -> FLUORINE_GAS.get();
            case "helium" -> HELIUM_GAS.get();
            case "neon" -> NEON_GAS.get();
            case "argon" -> ARGON_GAS.get();
            case "krypton" -> KRYPTON_GAS.get();
            case "xenon" -> XENON_GAS.get();
            case "radon" -> RADON_GAS.get();
            case "uranium" -> URANIUM_GAS.get();
            default -> null;
        };
    }

    /**
     * Get compound gas chemical by name.
     *
     * @param compoundName The compound name (e.g., "water_vapor", "carbon_dioxide")
     * @return The Chemical object, or null if not found
     */
    public static Chemical getCompoundGas(String compoundName) {
        return switch (compoundName.toLowerCase()) {
            case "carbon_dioxide" -> CARBON_DIOXIDE.get();
            case "methane" -> METHANE.get();
            case "ammonia" -> AMMONIA.get();
            case "hydrogen_chloride" -> HYDROGEN_CHLORIDE_GAS.get();
            case "ethane" -> ETHANE.get();
            case "propane" -> PROPANE.get();
            case "butane" -> BUTANE.get();
            case "acetylene" -> ACETYLENE.get();
            case "ethylene" -> ETHYLENE.get();
            case "hydrogen_sulfide" -> HYDROGEN_SULFIDE.get();
            case "nitrogen_dioxide" -> NITROGEN_DIOXIDE.get();
            case "carbon_monoxide" -> CARBON_MONOXIDE.get();
            case "nitric_oxide" -> NITRIC_OXIDE.get();
            case "sulfur_dioxide" -> SULFUR_DIOXIDE_GAS.get();
            default -> null;
        };
    }

    /**
     * Check if an element has a gas form registered.
     *
     * @param elementName The element name
     * @return true if gas form exists
     */
    public static boolean hasElementGas(String elementName) {
        return getElementGas(elementName) != null;
    }

    /**
     * Check if a compound has a gas form registered.
     *
     * @param compoundName The compound name
     * @return true if gas form exists
     */
    public static boolean hasCompoundGas(String compoundName) {
        return getCompoundGas(compoundName) != null;
    }

    /**
     * Load ChemLib colors from extracted data.
     */
    private static void loadChemLibColors() {
        // Load element colors
        List<ChemLibDataExtractor.ElementData> elements = ChemLibDataExtractor.extractElements();
        for (ChemLibDataExtractor.ElementData element : elements) {
            if (element.color != null && !element.color.isEmpty()) {
                try {
                    int color = (int) Long.parseLong(element.color, 16);
                    if (element.color.length() == 6) {
                        color |= 0xFF000000; // Add full alpha if not specified
                    }
                    CHEMLIB_COLORS.put(element.name, color);
                } catch (NumberFormatException e) {
                    ChemlibMekanized.LOGGER.warn("Invalid color format for element {}: {}", element.name, element.color);
                }
            }
        }

        // Load compound colors
        List<ChemLibDataExtractor.CompoundData> compounds = ChemLibDataExtractor.extractCompounds();
        for (ChemLibDataExtractor.CompoundData compound : compounds) {
            if (compound.color != null && !compound.color.isEmpty()) {
                try {
                    int color = (int) Long.parseLong(compound.color, 16);
                    if (compound.color.length() == 6) {
                        color |= 0xFF000000; // Add full alpha if not specified
                    }
                    CHEMLIB_COLORS.put(compound.name, color);
                } catch (NumberFormatException e) {
                    ChemlibMekanized.LOGGER.warn("Invalid color format for compound {}: {}", compound.name, compound.color);
                }
            }
        }

        ChemlibMekanized.LOGGER.info("Loaded {} ChemLib colors for chemical consistency", CHEMLIB_COLORS.size());
    }

    /**
     * Get ChemLib color for a chemical, with fallback.
     *
     * @param name The chemical name
     * @param fallback Fallback color if not found in ChemLib
     * @return The color as integer
     */
    private static int getChemLibColor(String name, int fallback) {
        Integer color = CHEMLIB_COLORS.get(name);
        if (color != null) {
            ChemlibMekanized.LOGGER.debug("Using ChemLib color for {}: 0x{}", name, Integer.toHexString(color));
            return color;
        } else {
            ChemlibMekanized.LOGGER.debug("No ChemLib color found for {}, using fallback: 0x{}", name, Integer.toHexString(fallback));
            return fallback;
        }
    }

    /**
     * Get the ChemLib color for a chemical (public method for fluid registry).
     *
     * @param name The chemical name
     * @return The color as integer, or null if not found
     */
    public static Integer getChemLibColorForFluid(String name) {
        return CHEMLIB_COLORS.get(name);
    }

    /**
     * Register all chemicals during mod initialization.
     * Called during RegisterEvent<Chemical>.
     */
    public static void init() {
        ChemlibMekanized.LOGGER.info("Initializing {} gas chemicals for ChemLib integration",
            CHEMICALS.getEntries().size());
    }
}