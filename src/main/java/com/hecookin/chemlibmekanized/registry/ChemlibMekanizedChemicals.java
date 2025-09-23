package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;
import net.minecraft.resources.ResourceLocation;

/**
 * Registry for all ChemLib-Mekanized chemicals using proper Mekanism Chemical objects.
 * Replaces the old ChemicalReference system with actual Mekanism integration.
 */
public class ChemlibMekanizedChemicals {

    private ChemlibMekanizedChemicals() {
    }

    public static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(ChemlibMekanized.MODID);

    // Gas Elements - Using Mekanism's default liquid texture with color tinting
    public static final DeferredChemical<Chemical> HYDROGEN_GAS = CHEMICALS.register("element_hydrogen", 0xB3DFFF);
    public static final DeferredChemical<Chemical> OXYGEN_GAS = CHEMICALS.register("element_oxygen", 0xFF8080);
    public static final DeferredChemical<Chemical> NITROGEN_GAS = CHEMICALS.register("element_nitrogen", 0x7F7FFF);
    public static final DeferredChemical<Chemical> CHLORINE_GAS = CHEMICALS.register("element_chlorine", 0xFFFF00);
    public static final DeferredChemical<Chemical> HELIUM_GAS = CHEMICALS.register("element_helium", 0xFFE4B5);
    public static final DeferredChemical<Chemical> URANIUM_GAS = CHEMICALS.register("element_uranium", 0x32FF32);

    // Gas Compounds - Using Mekanism's default liquid texture with color tinting
    public static final DeferredChemical<Chemical> WATER_VAPOR = CHEMICALS.register("compound_water_vapor", 0x87CEEB);
    public static final DeferredChemical<Chemical> CARBON_DIOXIDE = CHEMICALS.register("compound_carbon_dioxide", 0x32C832);
    public static final DeferredChemical<Chemical> METHANE = CHEMICALS.register("compound_methane", 0x5C5C5C);
    public static final DeferredChemical<Chemical> AMMONIA = CHEMICALS.register("compound_ammonia", 0x87CEEB);
    public static final DeferredChemical<Chemical> HYDROGEN_CHLORIDE_GAS = CHEMICALS.register("compound_hydrogen_chloride", 0xB3FFB3);
    public static final DeferredChemical<Chemical> ETHANE = CHEMICALS.register("compound_ethane", 0x4F4F4F);
    public static final DeferredChemical<Chemical> PROPANE = CHEMICALS.register("compound_propane", 0xFFE4B5);
    public static final DeferredChemical<Chemical> BUTANE = CHEMICALS.register("compound_butane", 0xFFDAB9);
    public static final DeferredChemical<Chemical> CHLORINE_COMPOUND_GAS = CHEMICALS.register("compound_chlorine_gas", 0xFFFF7F);
    public static final DeferredChemical<Chemical> SULFUR_DIOXIDE_GAS = CHEMICALS.register("compound_sulfur_dioxide", 0xFFFF80);

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
            case "helium" -> HELIUM_GAS.get();
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
            case "water_vapor" -> WATER_VAPOR.get();
            case "carbon_dioxide" -> CARBON_DIOXIDE.get();
            case "methane" -> METHANE.get();
            case "ammonia" -> AMMONIA.get();
            case "hydrogen_chloride" -> HYDROGEN_CHLORIDE_GAS.get();
            case "ethane" -> ETHANE.get();
            case "propane" -> PROPANE.get();
            case "butane" -> BUTANE.get();
            case "chlorine_gas" -> CHLORINE_COMPOUND_GAS.get();
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
     * Register all chemicals during mod initialization.
     * Called during RegisterEvent<Chemical>.
     */
    public static void init() {
        ChemlibMekanized.LOGGER.info("Initializing {} gas chemicals for ChemLib integration",
            CHEMICALS.getEntries().size());
    }
}