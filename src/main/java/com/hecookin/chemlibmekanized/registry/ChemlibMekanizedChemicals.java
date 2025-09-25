package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;
import mekanism.common.registration.impl.SlurryRegistryObject;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

    // Map to hold all our metal slurries
    public static final Map<String, SlurryRegistryObject<Chemical, Chemical>> METAL_SLURRIES = new LinkedHashMap<>();

    // Define metals with their colors (from ChemLib's element colors)
    public enum ChemLibMetal {
        // Common metals (excluding those in Mekanism: iron, gold, copper, tin, lead, uranium, osmium)
        ALUMINUM("aluminum", 0xFFD9D9D9),
        TITANIUM("titanium", 0xFF6375FF),
        ZINC("zinc", 0xFFA0997D),
        NICKEL("nickel", 0xFF62764C),
        SILVER("silver", 0xFFC1C7C7),
        PLATINUM("platinum", 0xFFD2D3D3),

        // Rare metals
        TUNGSTEN("tungsten", 0xFF2B313E),
        CHROMIUM("chromium", 0xFF481A5D),
        MANGANESE("manganese", 0xFFC7A385),
        COBALT("cobalt", 0xFF6666FF),
        CADMIUM("cadmium", 0xFF9A2B34),
        MERCURY("mercury", 0xFF9D9F9F),

        // Precious metals (beyond vanilla gold)
        PALLADIUM("palladium", 0xFF704158),
        RHODIUM("rhodium", 0xFF827569),
        IRIDIUM("iridium", 0xFF2F5EEF),
        RUTHENIUM("ruthenium", 0xFF715F63),

        // Radioactive metals (excluding uranium which Mekanism has)
        THORIUM("thorium", 0xFF5F4533),

        // Alkali/Alkaline metals
        LITHIUM("lithium", 0xFF8F243E),
        SODIUM("sodium", 0xFFD3C683),
        POTASSIUM("potassium", 0xFFC6985F),
        CALCIUM("calcium", 0xFFDBD2C7),
        MAGNESIUM("magnesium", 0xFFEDADEC),
        BARIUM("barium", 0xFFC7BCA8),
        STRONTIUM("strontium", 0xFF536998),

        // Metalloids that act like metals
        SILICON("silicon", 0xFFADB279),
        GERMANIUM("germanium", 0xFFCCD4C0),
        ANTIMONY("antimony", 0xFF855770),
        BISMUTH("bismuth", 0xFF3D9185),

        // Lanthanides (Rare Earth)
        CERIUM("cerium", 0xFF662C5E),
        NEODYMIUM("neodymium", 0xFF8C696B),
        LANTHANUM("lanthanum", 0xFFBA9160),
        GADOLINIUM("gadolinium", 0xFF915536),
        EUROPIUM("europium", 0xFFCE81B3),

        // Other useful metals
        INDIUM("indium", 0xFF86847F),
        GALLIUM("gallium", 0xFFA1A8C1),
        HAFNIUM("hafnium", 0xFF243267),
        TANTALUM("tantalum", 0xFFA81E20),
        RHENIUM("rhenium", 0xFF1B1D1B),
        MOLYBDENUM("molybdenum", 0xFF6D7E9A),
        VANADIUM("vanadium", 0xFF647689),
        NIOBIUM("niobium", 0xFF734D54),
        BERYLLIUM("beryllium", 0xFF21A17E),
        ZIRCONIUM("zirconium", 0xFFD3BB8F),
        SCANDIUM("scandium", 0xFFFCFF63),
        YTTRIUM("yttrium", 0xFFB366C4),

        // Additional metals (excluding tin, lead, osmium which Mekanism has)
        THALLIUM("thallium", 0xFF785445),
        POLONIUM("polonium", 0xFF29443F),
        TECHNETIUM("technetium", 0xFF758489),
        RUBIDIUM("rubidium", 0xFFEE88FD),
        CESIUM("cesium", 0xFFCBAC2A),
        FRANCIUM("francium", 0xFF9A410C),
        RADIUM("radium", 0xFF3C9C6F),

        // Additional metalloids
        ARSENIC("arsenic", 0xFF8C3F31),
        TELLURIUM("tellurium", 0xFF275B1A),
        BORON("boron", 0xFF3E4A60),
        ASTATINE("astatine", 0xFF9FC413),

        // Post-transition metals
        ALUMINUM_ALT("aluminium", 0xFFD9D9D9),  // Alternative spelling
        GALLIUM_ARSENIDE("gallium_arsenide", 0xFF8F8F8F),  // Compound but useful

        // Actinides (besides Thorium and Uranium)
        ACTINIUM("actinium", 0xFF7781F9),
        PROTACTINIUM("protactinium", 0xFF3E8863),
        NEPTUNIUM("neptunium", 0xFF657065),
        PLUTONIUM("plutonium", 0xFFDB6E22),
        AMERICIUM("americium", 0xFFAB8040),
        CURIUM("curium", 0xFF920133),
        BERKELIUM("berkelium", 0xFFB48B41),
        CALIFORNIUM("californium", 0xFFB47148),

        // Additional transition metals
        TUNGSTEN_ALT("wolfram", 0xFF2B313E);  // Alternative name for tungsten

        private final String name;
        private final int tint;

        ChemLibMetal(String name, int tint) {
            this.name = name;
            this.tint = tint;
        }

        public String getName() {
            return name;
        }

        public int getTint() {
            return tint;
        }
    }

    // Initialize ChemLib color mapping and slurry registrations
    static {
        loadChemLibColors();
        registerAllSlurries();
    }

    // Gas Elements
    // Use Mekanism's hydrogen instead
    // public static final DeferredChemical<Chemical> HYDROGEN = CHEMICALS.register("hydrogen", getChemLibColor("hydrogen", 0xB3DFFF));
    public static final DeferredChemical<Chemical> HELIUM = CHEMICALS.register("helium", getChemLibColor("helium", 0xFFFDCC));
    // public static final DeferredChemical<Chemical> NITROGEN = CHEMICALS.register("nitrogen", getChemLibColor("nitrogen", 0xE1B4B8));
    // Use Mekanism's oxygen instead
    // public static final DeferredChemical<Chemical> OXYGEN = CHEMICALS.register("oxygen", getChemLibColor("oxygen", 0x84CFFE));
    // public static final DeferredChemical<Chemical> FLUORINE = CHEMICALS.register("fluorine", getChemLibColor("fluorine", 0xB4D14B));
    public static final DeferredChemical<Chemical> NEON = CHEMICALS.register("neon", getChemLibColor("neon", 0xF4BC27));
    // public static final DeferredChemical<Chemical> CHLORINE = CHEMICALS.register("chlorine", getChemLibColor("chlorine", 0x8dd41c));
    public static final DeferredChemical<Chemical> ARGON = CHEMICALS.register("argon", getChemLibColor("argon", 0x6711A3));
    public static final DeferredChemical<Chemical> KRYPTON = CHEMICALS.register("krypton", getChemLibColor("krypton", 0xbce2ef));
    public static final DeferredChemical<Chemical> XENON = CHEMICALS.register("xenon", getChemLibColor("xenon", 0x2861a7));
    // public static final DeferredChemical<Chemical> RADON = CHEMICALS.register("radon", getChemLibColor("radon", 0x5fd16a));

    // Liquid Elements (as slurries)
    public static final DeferredChemical<Chemical> BROMINE_SLURRY = CHEMICALS.register("bromine_slurry", getChemLibColor("bromine", 0xa61601));
    // Mercury is registered as a fluid in ChemLibFluidRegistry and as a metal slurry in ChemLibSlurries
    // public static final DeferredChemical<Chemical> MERCURY_SLURRY = CHEMICALS.register("mercury_slurry", getChemLibColor("mercury", 0x9d9f9f));

    // Acid Chemicals for PRC Processing (as fluids, not gas chemicals)
    public static final DeferredChemical<Chemical> NITRIC_ACID = CHEMICALS.register("nitric_acid", getChemLibColor("nitric_acid", 0x427bd6));
    public static final DeferredChemical<Chemical> HYDROCHLORIC_ACID = CHEMICALS.register("hydrochloric_acid", getChemLibColor("hydrochloric_acid", 0x0000ff));

    // Gas Compounds
    // public static final DeferredChemical<Chemical> CARBON_DIOXIDE = CHEMICALS.register("carbon_dioxide", getChemLibColor("carbon_dioxide", 0x32c832));
    public static final DeferredChemical<Chemical> ETHYLENE = CHEMICALS.register("ethylene", getChemLibColor("ethylene", 0xc6c79f));
    public static final DeferredChemical<Chemical> AMMONIUM = CHEMICALS.register("ammonium", getChemLibColor("ammonium", 0xb4fafa));
    // public static final DeferredChemical<Chemical> METHANE = CHEMICALS.register("methane", getChemLibColor("methane", 0xc81eb4));
    public static final DeferredChemical<Chemical> ETHANE = CHEMICALS.register("ethane", getChemLibColor("ethane", 0xc81e32));
    public static final DeferredChemical<Chemical> PROPANE = CHEMICALS.register("propane", getChemLibColor("propane", 0x641e32));
    public static final DeferredChemical<Chemical> BUTANE = CHEMICALS.register("butane", getChemLibColor("butane", 0x6f96b4));
    // public static final DeferredChemical<Chemical> SULFUR_DIOXIDE = CHEMICALS.register("sulfur_dioxide", getChemLibColor("sulfur_dioxide", 0x9b9b32));
    // public static final DeferredChemical<Chemical> NITROGEN_DIOXIDE = CHEMICALS.register("nitrogen_dioxide", getChemLibColor("nitrogen_dioxide", 0xb94407));
    // public static final DeferredChemical<Chemical> AMMONIA = CHEMICALS.register("ammonia", getChemLibColor("ammonia", 0x3c78fa));
    public static final DeferredChemical<Chemical> HYDROGEN_SULFIDE = CHEMICALS.register("hydrogen_sulfide", getChemLibColor("hydrogen_sulfide", 0xf0dc1e));
    public static final DeferredChemical<Chemical> ACETYLENE = CHEMICALS.register("acetylene", getChemLibColor("acetylene", 0x96e61e));
    // public static final DeferredChemical<Chemical> CARBON_MONOXIDE = CHEMICALS.register("carbon_monoxide", getChemLibColor("carbon_monoxide", 0x454653));
    // public static final DeferredChemical<Chemical> NITRIC_OXIDE = CHEMICALS.register("nitric_oxide", getChemLibColor("nitric_oxide", 0x4178c8));

    // Liquid Compounds - These are registered as fluids in ChemLibFluidRegistry, not as slurries
    // Commenting out to avoid conflicts with fluid registration
    // Water should use Mekanism's water_vapor instead
    // public static final DeferredChemical<Chemical> WATER_SLURRY = CHEMICALS.register("water_slurry", getChemLibColor("water", 0x115ec0));
    // public static final DeferredChemical<Chemical> ETHANOL_SLURRY = CHEMICALS.register("ethanol_slurry", getChemLibColor("ethanol", 0xd2fa96));
    // public static final DeferredChemical<Chemical> PROPAN_1_OL_SLURRY = CHEMICALS.register("propan_1_ol_slurry", getChemLibColor("propan-1-ol", 0xffa500));
    // public static final DeferredChemical<Chemical> PROPAN_2_OL_SLURRY = CHEMICALS.register("propan_2_ol_slurry", getChemLibColor("propan-2-ol", 0x808080));
    // public static final DeferredChemical<Chemical> PENTANE_SLURRY = CHEMICALS.register("pentane_slurry", getChemLibColor("pentane", 0x6f9655));
    // public static final DeferredChemical<Chemical> HEXANE_SLURRY = CHEMICALS.register("hexane_slurry", getChemLibColor("hexane", 0x6fcd32));
    // public static final DeferredChemical<Chemical> HEPTANE_SLURRY = CHEMICALS.register("heptane_slurry", getChemLibColor("heptane", 0xd9d900));
    // public static final DeferredChemical<Chemical> EPINEPHRINE_SLURRY = CHEMICALS.register("epinephrine_slurry", getChemLibColor("epinephrine", 0xe6a078));
    // public static final DeferredChemical<Chemical> SULFURIC_ACID_SLURRY = CHEMICALS.register("sulfuric_acid_slurry", getChemLibColor("sulfuric_acid", 0x96a00a));
    // public static final DeferredChemical<Chemical> ACETIC_ACID_SLURRY = CHEMICALS.register("acetic_acid_slurry", getChemLibColor("acetic_acid", 0xc8f064));
    // public static final DeferredChemical<Chemical> CARBON_DISULFIDE_SLURRY = CHEMICALS.register("carbon_disulfide_slurry", getChemLibColor("carbon_disulfide", 0xc878c8));
    // public static final DeferredChemical<Chemical> SULFUR_TRIOXIDE_SLURRY = CHEMICALS.register("sulfur_trioxide_slurry", getChemLibColor("sulfur_trioxide", 0x82a032));

    /**
     * Get element chemical by name and matter state.
     */
    public static Chemical getElementChemical(String elementName, String matterState) {
        if ("gas".equals(matterState)) {
            return switch (elementName.toLowerCase()) {
                // Use Mekanism's hydrogen
                // case "hydrogen" -> HYDROGEN.get();
                case "helium" -> HELIUM.get();
                case "nitrogen" -> NITROGEN.get();
                // Use Mekanism's oxygen
                // case "oxygen" -> OXYGEN.get();
                case "fluorine" -> FLUORINE.get();
                case "neon" -> NEON.get();
                case "chlorine" -> CHLORINE.get();
                case "argon" -> ARGON.get();
                case "krypton" -> KRYPTON.get();
                case "xenon" -> XENON.get();
                case "radon" -> RADON.get();
                default -> null;
            };
        } else if ("liquid".equals(matterState)) {
            return switch (elementName.toLowerCase()) {
                case "bromine" -> BROMINE_SLURRY.get();
                // Mercury is registered as a fluid, not a slurry
                // case "mercury" -> MERCURY_SLURRY.get();
                default -> null;
            };
        }
        return null;
    }

    /**
     * Get compound chemical by name and matter state.
     */
    public static Chemical getCompoundChemical(String compoundName, String matterState) {
        if ("gas".equals(matterState)) {
            return switch (compoundName.toLowerCase()) {
                case "carbon_dioxide" -> CARBON_DIOXIDE.get();
                case "ethylene" -> ETHYLENE.get();
                case "ammonium" -> AMMONIUM.get();
                case "methane" -> METHANE.get();
                case "ethane" -> ETHANE.get();
                case "propane" -> PROPANE.get();
                case "butane" -> BUTANE.get();
                case "sulfur_dioxide" -> SULFUR_DIOXIDE.get();
                case "nitrogen_dioxide" -> NITROGEN_DIOXIDE.get();
                case "ammonia" -> AMMONIA.get();
                case "hydrogen_sulfide" -> HYDROGEN_SULFIDE.get();
                case "acetylene" -> ACETYLENE.get();
                case "carbon_monoxide" -> CARBON_MONOXIDE.get();
                case "nitric_oxide" -> NITRIC_OXIDE.get();
                default -> null;
            };
        } else if ("liquid".equals(matterState)) {
            return switch (compoundName.toLowerCase()) {
                // Liquid compounds are registered as fluids in ChemLibFluidRegistry, not as slurries
                // Water should use Mekanism's water_vapor
                // case "water" -> WATER_SLURRY.get();
                // case "ethanol" -> ETHANOL_SLURRY.get();
                // case "propan-1-ol" -> PROPAN_1_OL_SLURRY.get();
                // case "propan-2-ol" -> PROPAN_2_OL_SLURRY.get();
                // case "pentane" -> PENTANE_SLURRY.get();
                // case "hexane" -> HEXANE_SLURRY.get();
                // case "heptane" -> HEPTANE_SLURRY.get();
                // case "epinephrine" -> EPINEPHRINE_SLURRY.get();
                case "hydrochloric_acid" -> HYDROCHLORIC_ACID.get();  // Special case - acids as chemicals
                // case "sulfuric_acid" -> SULFURIC_ACID_SLURRY.get();
                case "nitric_acid" -> NITRIC_ACID.get();  // Special case - acids as chemicals
                // case "acetic_acid" -> ACETIC_ACID_SLURRY.get();
                // case "carbon_disulfide" -> CARBON_DISULFIDE_SLURRY.get();
                // case "sulfur_trioxide" -> SULFUR_TRIOXIDE_SLURRY.get();
                default -> null;
            };
        }
        return null;
    }

    /**
     * Legacy method for backwards compatibility.
     */
    public static Chemical getElementGas(String elementName) {
        return getElementChemical(elementName, "gas");
    }

    /**
     * Legacy method for backwards compatibility.
     */
    public static Chemical getCompoundGas(String compoundName) {
        return getCompoundChemical(compoundName, "gas");
    }

    /**
     * Check if an element has a gas form registered.
     */
    public static boolean hasElementGas(String elementName) {
        return getElementChemical(elementName, "gas") != null;
    }

    /**
     * Check if a compound has a gas form registered.
     */
    public static boolean hasCompoundGas(String compoundName) {
        return getCompoundChemical(compoundName, "gas") != null;
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

    /**
     * Register all slurries during static initialization.
     */
    private static void registerAllSlurries() {
        // Register all metal slurries
        for (ChemLibMetal metal : ChemLibMetal.values()) {
            METAL_SLURRIES.put(metal.getName(), registerSlurry(metal.getName(), metal.getTint()));
        }

        // Also register special slurries for non-metal materials
        METAL_SLURRIES.put("quartz", registerSlurry("quartz", 0xFFE6D2D2));
        METAL_SLURRIES.put("lapis", registerSlurry("lapis", 0xFF345EC3));
        METAL_SLURRIES.put("coal", registerSlurry("coal", 0xFF202020));
        METAL_SLURRIES.put("netherite_scrap", registerSlurry("netherite_scrap", 0xFF6D4C47));
        METAL_SLURRIES.put("emerald", registerSlurry("emerald", 0xFF2EB82E));
        // Note: Diamond, Redstone, and Refined Obsidian are handled by Mekanism as infusion types
    }

    /**
     * Register a slurry pair (dirty and clean) for a material
     */
    private static SlurryRegistryObject<Chemical, Chemical> registerSlurry(String name, int tint) {
        // Register dirty slurry using Mekanism's dirty slurry texture
        DeferredChemical<Chemical> dirtySlurry = CHEMICALS.register("dirty_" + name, () ->
            new Chemical(ChemicalBuilder.dirtySlurry()
                .tint(tint))
        );

        // Register clean slurry using Mekanism's clean slurry texture
        DeferredChemical<Chemical> cleanSlurry = CHEMICALS.register("clean_" + name, () ->
            new Chemical(ChemicalBuilder.cleanSlurry()
                .tint(tint))
        );

        return new SlurryRegistryObject<>(dirtySlurry, cleanSlurry);
    }

    /**
     * Get a dirty slurry by metal name
     */
    public static Chemical getDirtySlurry(String metalName) {
        SlurryRegistryObject<Chemical, Chemical> slurry = METAL_SLURRIES.get(metalName.toLowerCase());
        if (slurry != null) {
            return slurry.get();  // Primary is dirty
        }
        return null;
    }

    /**
     * Get a clean slurry by metal name
     */
    public static Chemical getCleanSlurry(String metalName) {
        SlurryRegistryObject<Chemical, Chemical> slurry = METAL_SLURRIES.get(metalName.toLowerCase());
        if (slurry != null) {
            return slurry.getSecondary();  // Secondary is clean
        }
        return null;
    }

    /**
     * Get the full slurry registry object
     */
    public static SlurryRegistryObject<Chemical, Chemical> getSlurryPair(String metalName) {
        return METAL_SLURRIES.get(metalName.toLowerCase());
    }
}