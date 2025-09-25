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