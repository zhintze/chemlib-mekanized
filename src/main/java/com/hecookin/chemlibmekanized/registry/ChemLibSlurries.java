package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;
import mekanism.common.registration.impl.SlurryRegistryObject;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ChemLib metal slurries for Mekanism processing.
 * Each metal gets a dirty and clean slurry variant.
 */
public class ChemLibSlurries {

    public static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(ChemlibMekanized.MODID);

    // Map to hold all our metal slurries
    public static final Map<String, SlurryRegistryObject<Chemical, Chemical>> METAL_SLURRIES = new LinkedHashMap<>();

    // Define metals with their colors (from ChemLib's element colors)
    public enum ChemLibMetal {
        // Common metals (excluding those in Mekanism: iron, gold, copper, tin, lead, uranium, osmium)
        ALUMINUM("aluminum", 0xFF999999),
        TITANIUM("titanium", 0xFF778899),
        ZINC("zinc", 0xFF7D7D7D),
        NICKEL("nickel", 0xFF727472),
        SILVER("silver", 0xFFC0C0C0),
        PLATINUM("platinum", 0xFFE5E5E5),

        // Rare metals
        TUNGSTEN("tungsten", 0xFF606060),
        CHROMIUM("chromium", 0xFF8B8B8B),
        MANGANESE("manganese", 0xFF9C7AC7),
        COBALT("cobalt", 0xFF3333BB),
        CADMIUM("cadmium", 0xFFFF7E00),
        MERCURY("mercury", 0xFFB8B8D0),

        // Precious metals (beyond vanilla gold)
        PALLADIUM("palladium", 0xFFEDE7E0),
        RHODIUM("rhodium", 0xFFC0C0C0),
        IRIDIUM("iridium", 0xFFF0F0F0),
        RUTHENIUM("ruthenium", 0xFFE5E5E5),

        // Radioactive metals (excluding uranium which Mekanism has)
        THORIUM("thorium", 0xFF3D3D3D),

        // Alkali/Alkaline metals
        LITHIUM("lithium", 0xFFCCCCCC),
        SODIUM("sodium", 0xFFFFCC99),
        POTASSIUM("potassium", 0xFFB3B3CC),
        CALCIUM("calcium", 0xFFDDDDDD),
        MAGNESIUM("magnesium", 0xFF8F8F8F),
        BARIUM("barium", 0xFF707070),
        STRONTIUM("strontium", 0xFFFFFF99),

        // Metalloids that act like metals
        SILICON("silicon", 0xFF888888),
        GERMANIUM("germanium", 0xFF666666),
        ANTIMONY("antimony", 0xFF666666),
        BISMUTH("bismuth", 0xFF9E9E9E),

        // Lanthanides (Rare Earth)
        CERIUM("cerium", 0xFFFFFF99),
        NEODYMIUM("neodymium", 0xFFCCCCCC),
        LANTHANUM("lanthanum", 0xFFE6E6E6),
        GADOLINIUM("gadolinium", 0xFFDDDDDD),
        EUROPIUM("europium", 0xFFE6E6E6),

        // Other useful metals
        INDIUM("indium", 0xFFA0A0A0),
        GALLIUM("gallium", 0xFFC0C0C0),
        HAFNIUM("hafnium", 0xFFE6E6E6),
        TANTALUM("tantalum", 0xFF999999),
        RHENIUM("rhenium", 0xFF7F7F7F),
        MOLYBDENUM("molybdenum", 0xFF5C5C5C),
        VANADIUM("vanadium", 0xFF808080),
        NIOBIUM("niobium", 0xFF737373),
        BERYLLIUM("beryllium", 0xFF969696),
        ZIRCONIUM("zirconium", 0xFFC0C0C0),
        SCANDIUM("scandium", 0xFFE6E6E6),
        YTTRIUM("yttrium", 0xFF94FFFF),

        // Additional metals (excluding tin, lead, osmium which Mekanism has)
        THALLIUM("thallium", 0xFFA6544D),
        POLONIUM("polonium", 0xFF673E3E),
        TECHNETIUM("technetium", 0xFF878787),
        RUBIDIUM("rubidium", 0xFF702EB0),
        CESIUM("cesium", 0xFF57178F),
        FRANCIUM("francium", 0xFF420A0A),
        RADIUM("radium", 0xFF007D00),

        // Additional metalloids
        ARSENIC("arsenic", 0xFF3B3B3B),
        TELLURIUM("tellurium", 0xFFD47A00),
        BORON("boron", 0xFFFFB5B5),
        ASTATINE("astatine", 0xFF662233),

        // Post-transition metals
        ALUMINUM_ALT("aluminium", 0xFF999999),  // Alternative spelling
        GALLIUM_ARSENIDE("gallium_arsenide", 0xFF8F8F8F),  // Compound but useful

        // Actinides (besides Thorium and Uranium)
        ACTINIUM("actinium", 0xFF70ABFA),
        PROTACTINIUM("protactinium", 0xFFA78E4E),
        NEPTUNIUM("neptunium", 0xFF0080FF),
        PLUTONIUM("plutonium", 0xFF006BFF),
        AMERICIUM("americium", 0xFF545CF2),
        CURIUM("curium", 0xFF785CE3),
        BERKELIUM("berkelium", 0xFF8A4FBE),
        CALIFORNIUM("californium", 0xFFA136A5),

        // Additional transition metals
        TUNGSTEN_ALT("wolfram", 0xFF606060);  // Alternative name for tungsten

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

    static {
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
        // Register dirty slurry
        DeferredChemical<Chemical> dirtySlurry = CHEMICALS.register("dirty_" + name, () ->
            new Chemical(ChemicalBuilder.builder(ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "slurry/dirty"))
                .tint(tint))
        );

        // Register clean slurry
        DeferredChemical<Chemical> cleanSlurry = CHEMICALS.register("clean_" + name, () ->
            new Chemical(ChemicalBuilder.builder(ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "slurry/clean"))
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