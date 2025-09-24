package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.items.MetalCrystalItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registry for metal and metalloid crystal items.
 * Crystals are produced from clean slurries in the Chemical Crystallizer.
 */
@EventBusSubscriber(modid = ChemlibMekanized.MODID, bus = EventBusSubscriber.Bus.MOD)
public class MetalCrystalRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            net.minecraft.core.registries.BuiltInRegistries.ITEM,
            ChemlibMekanized.MODID
    );

    // Map to hold all crystal items
    public static final Map<String, DeferredHolder<Item, MetalCrystalItem>> CRYSTAL_ITEMS = new LinkedHashMap<>();

    // Separate metalloids for creative tab organization
    public static final String[] METALLOIDS = {
        "silicon", "germanium", "antimony", "bismuth", "boron", "arsenic", "tellurium", "polonium"
    };

    // Define all metals/metalloids that have crystals (matching our slurries)
    public static final String[][] CRYSTAL_METALS = {
        // Common metals
        {"aluminum", "Aluminum", "D9D9D9"},
        {"titanium", "Titanium", "6375FF"},
        {"zinc", "Zinc", "A0997D"},
        {"nickel", "Nickel", "62764C"},
        {"silver", "Silver", "C1C7C7"},
        {"platinum", "Platinum", "D2D3D3"},

        // Rare metals
        {"tungsten", "Tungsten", "2B313E"},
        {"chromium", "Chromium", "481A5D"},
        {"manganese", "Manganese", "C7A385"},
        {"cobalt", "Cobalt", "6666FF"},
        {"cadmium", "Cadmium", "9A2B34"},
        {"mercury", "Mercury", "9D9F9F"},

        // Precious metals
        {"palladium", "Palladium", "704158"},
        {"rhodium", "Rhodium", "827569"},
        {"iridium", "Iridium", "2F5EEF"},
        {"ruthenium", "Ruthenium", "715F63"},

        // Radioactive
        {"thorium", "Thorium", "5F4533"},
        {"polonium", "Polonium", "29443F"},

        // Alkali/Alkaline
        {"lithium", "Lithium", "8F243E"},
        {"sodium", "Sodium", "D3C683"},
        {"potassium", "Potassium", "C6985F"},
        {"calcium", "Calcium", "DBD2C7"},
        {"magnesium", "Magnesium", "EDADEC"},
        {"barium", "Barium", "C7BCA8"},
        {"strontium", "Strontium", "536998"},
        {"rubidium", "Rubidium", "EE88FD"},
        {"cesium", "Cesium", "CBAC2A"},
        {"francium", "Francium", "9A410C"},
        {"radium", "Radium", "3C9C6F"},

        // Metalloids
        {"silicon", "Silicon", "ADB279"},
        {"germanium", "Germanium", "CCD4C0"},
        {"antimony", "Antimony", "855770"},
        {"bismuth", "Bismuth", "3D9185"},
        {"boron", "Boron", "3E4A60"},
        {"arsenic", "Arsenic", "8C3F31"},
        {"tellurium", "Tellurium", "275B1A"},

        // Lanthanides
        {"cerium", "Cerium", "662C5E"},
        {"neodymium", "Neodymium", "8C696B"},
        {"lanthanum", "Lanthanum", "BA9160"},
        {"gadolinium", "Gadolinium", "915536"},
        {"europium", "Europium", "CE81B3"},

        // Other metals
        {"indium", "Indium", "86847F"},
        {"gallium", "Gallium", "A1A8C1"},
        {"hafnium", "Hafnium", "243267"},
        {"tantalum", "Tantalum", "A81E20"},
        {"rhenium", "Rhenium", "1B1D1B"},
        {"molybdenum", "Molybdenum", "6D7E9A"},
        {"vanadium", "Vanadium", "647689"},
        {"niobium", "Niobium", "734D54"},
        {"beryllium", "Beryllium", "21A17E"},
        {"zirconium", "Zirconium", "D3BB8F"},
        {"scandium", "Scandium", "FCFF63"},
        {"yttrium", "Yttrium", "B366C4"},
        {"thallium", "Thallium", "785445"},
        {"technetium", "Technetium", "758489"},

        // Actinides
        {"actinium", "Actinium", "7781F9"},
        {"protactinium", "Protactinium", "3E8863"},
        {"neptunium", "Neptunium", "657065"},
        {"plutonium", "Plutonium", "DB6E22"},
        {"americium", "Americium", "AB8040"},
        {"curium", "Curium", "920133"},
        {"berkelium", "Berkelium", "B48B41"},
        {"californium", "Californium", "B47148"}
    };

    static {
        // Register all crystal items
        for (String[] metal : CRYSTAL_METALS) {
            String id = metal[0];
            String name = metal[1];
            String colorHex = metal[2];

            int color = Integer.parseInt(colorHex, 16) | 0xFF000000;

            DeferredHolder<Item, MetalCrystalItem> crystalItem = ITEMS.register(
                id + "_crystal",
                () -> new MetalCrystalItem(id, name, color)
            );

            CRYSTAL_ITEMS.put(id, crystalItem);
        }

        ChemlibMekanized.LOGGER.info("Registered {} metal crystal items", CRYSTAL_ITEMS.size());
    }

    /**
     * Get a crystal item by metal name
     */
    public static MetalCrystalItem getCrystal(String metalName) {
        DeferredHolder<Item, MetalCrystalItem> holder = CRYSTAL_ITEMS.get(metalName.toLowerCase());
        return holder != null ? holder.get() : null;
    }

    /**
     * Add crystal items to the appropriate creative tabs
     */
    @SubscribeEvent
    public static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ChemLibItemRegistry.METALS_TAB.get()) {
            // Add metal crystal items to the metals tab
            CRYSTAL_ITEMS.forEach((name, crystalHolder) -> {
                if (crystalHolder != null && crystalHolder.get() != null && !isMetalloid(name)) {
                    event.accept(crystalHolder.get());
                }
            });
        } else if (event.getTab() == ChemLibItemRegistry.METALLOIDS_TAB.get()) {
            // Add metalloid crystal items to the metalloids tab
            CRYSTAL_ITEMS.forEach((name, crystalHolder) -> {
                if (crystalHolder != null && crystalHolder.get() != null && isMetalloid(name)) {
                    event.accept(crystalHolder.get());
                }
            });
        }
    }

    private static boolean isMetalloid(String name) {
        for (String metalloid : METALLOIDS) {
            if (metalloid.equals(name)) {
                return true;
            }
        }
        return false;
    }
}