package com.hecookin.chemlibmekanized.registry;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry for ChemLib chemical fluids using standard NeoForge fluid system
 * Simplified approach that avoids circular dependencies
 * Handles all liquid-state elements and compounds extracted from ChemLib
 */
public class ChemLibFluidRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChemLibFluidRegistry.class);

    // Standard NeoForge fluid registration (no buckets)
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, ChemlibMekanized.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, ChemlibMekanized.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, ChemlibMekanized.MODID);

    // Collections to store registered fluids
    private static final List<ChemLibFluidEntry> ELEMENT_FLUIDS = new ArrayList<>();
    private static final List<ChemLibFluidEntry> COMPOUND_FLUIDS = new ArrayList<>();
    private static final List<ChemLibFluidEntry> GAS_FLUIDS = new ArrayList<>();


    /**
     * Registers all liquid chemicals as standard NeoForge fluids
     */
    public static void registerAll() {
        LOGGER.info("Starting ChemLib fluid registration...");

        try {
            registerLiquidElements();
            registerLiquidCompounds();
            registerGasFluids();

            LOGGER.info("Registered {} element fluids, {} compound fluids, and {} gas fluids",
                       ELEMENT_FLUIDS.size(), COMPOUND_FLUIDS.size(), GAS_FLUIDS.size());
        } catch (Exception e) {
            LOGGER.error("Failed to register ChemLib fluids", e);
        }
    }

    /**
     * Registers all liquid-state elements as fluids
     */
    private static void registerLiquidElements() {
        List<ChemLibDataExtractor.ElementData> elements = ChemLibDataExtractor.extractElements();
        LOGGER.info("Found {} total elements, checking for liquid elements with fluid properties", elements.size());

        for (ChemLibDataExtractor.ElementData element : elements) {
            if ("liquid".equals(element.matterState)) {
                // Skip bromine and mercury as they're handled as slurries in ChemlibMekanizedChemicals
                if ("bromine".equals(element.name) || "mercury".equals(element.name)) {
                    LOGGER.info("Skipping liquid element {} - handled as slurry", element.name);
                    continue;
                }
                LOGGER.info("Found liquid element: {} (has fluid properties: {})", element.name, element.fluidProperties != null);
                if (element.fluidProperties != null) {
                    registerElementFluid(element);
                }
            }
        }
    }

    /**
     * Registers all liquid-state compounds as fluids
     */
    private static void registerLiquidCompounds() {
        List<ChemLibDataExtractor.CompoundData> compounds = ChemLibDataExtractor.extractCompounds();
        LOGGER.info("Found {} total compounds, checking for liquid compounds with fluid properties", compounds.size());

        for (ChemLibDataExtractor.CompoundData compound : compounds) {
            if ("liquid".equals(compound.matterState)) {
                LOGGER.info("Found liquid compound: {} (has fluid properties: {})", compound.name, compound.fluidProperties != null);
                if (compound.fluidProperties != null) {
                    registerCompoundFluid(compound);
                }
            }
        }
    }

    /**
     * Registers a single element as a standard NeoForge fluid
     */
    private static void registerElementFluid(ChemLibDataExtractor.ElementData element) {
        try {
            ChemLibFluidEntry fluidEntry = createSimpleFluidEntry(
                element.name,
                ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "block/fluid/liquid_still"),
                ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "block/fluid/liquid_flow"),
                Math.round(element.fluidProperties.density),
                Math.round(element.fluidProperties.viscosity),
                Math.round(element.fluidProperties.temperature),
                element.fluidProperties.lightLevel,
                element.color
            );

            ELEMENT_FLUIDS.add(fluidEntry);
            LOGGER.debug("Registered element fluid: {}", element.name);

        } catch (Exception e) {
            LOGGER.error("Failed to register element fluid: {}", element.name, e);
        }
    }

    /**
     * Registers a single compound as a standard NeoForge fluid
     */
    private static void registerCompoundFluid(ChemLibDataExtractor.CompoundData compound) {
        try {
            ChemLibFluidEntry fluidEntry = createSimpleFluidEntry(
                compound.name,
                ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "block/fluid/liquid_still"),
                ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "block/fluid/liquid_flow"),
                Math.round(compound.fluidProperties.density),
                Math.round(compound.fluidProperties.viscosity),
                Math.round(compound.fluidProperties.temperature),
                compound.fluidProperties.lightLevel,
                compound.color
            );

            COMPOUND_FLUIDS.add(fluidEntry);
            LOGGER.debug("Registered compound fluid: {}", compound.name);

        } catch (Exception e) {
            LOGGER.error("Failed to register compound fluid: {}", compound.name, e);
        }
    }

    /**
     * Registers fluid forms of gas chemicals for rotary condensentrator conversion
     */
    private static void registerGasFluids() {
        LOGGER.info("Registering fluid forms for gas chemicals...");

        // Element gas fluids
        registerGasFluid("nitrogen", "e1b4b8", 808, 1000, 77);  // Liquid nitrogen properties
        registerGasFluid("fluorine", "b4d14b", 1505, 250, 85);  // Liquid fluorine properties

        // Noble gases
        registerGasFluid("helium", "ffdcc", 125, 20, 4);  // Liquid helium properties
        registerGasFluid("neon", "f4bc27", 1207, 30, 27);  // Liquid neon properties
        registerGasFluid("argon", "6711a3", 1395, 250, 87);  // Liquid argon properties
        registerGasFluid("krypton", "bce2ef", 2413, 350, 120);  // Liquid krypton properties
        registerGasFluid("xenon", "2861a7", 3057, 500, 165);  // Liquid xenon properties
        registerGasFluid("radon", "5fd16a", 4400, 700, 211);  // Liquid radon properties

        // Compound gas fluids
        registerGasFluid("carbon_dioxide", "32c832", 1101, 70, 217);  // Liquid CO2 properties
        registerGasFluid("ethylene", "c6c79f", 567, 150, 169);
        registerGasFluid("ammonium", "b4fafa", 682, 250, 240);
        registerGasFluid("methane", "c81eb4", 424, 110, 112);
        registerGasFluid("ethane", "c81e32", 544, 120, 185);
        registerGasFluid("propane", "641e32", 581, 130, 231);
        registerGasFluid("butane", "6f96b4", 601, 140, 273);
        registerGasFluid("nitrogen_dioxide", "b94407", 1448, 400, 294);
        registerGasFluid("ammonia", "3c78fa", 682, 250, 240);
        registerGasFluid("hydrogen_sulfide", "f0dc1e", 949, 200, 213);
        registerGasFluid("acetylene", "96e61e", 378, 100, 189);
        registerGasFluid("carbon_monoxide", "454653", 789, 170, 82);
        registerGasFluid("nitric_oxide", "4178c8", 1340, 220, 121);

        // Vaporizable liquid compounds
        registerGasFluid("ethanol", "d2fa96", 789, 1074, 351);  // Liquid ethanol properties
        registerGasFluid("propan_1_ol", "ffa500", 803, 1943, 370);  // Liquid propan-1-ol
        registerGasFluid("propan_2_ol", "808080", 785, 2040, 355);  // Liquid propan-2-ol (isopropanol)
        registerGasFluid("pentane", "6f9655", 626, 224, 309);  // Liquid pentane
        registerGasFluid("hexane", "6fcd32", 659, 294, 342);  // Liquid hexane
        registerGasFluid("heptane", "d9d900", 684, 387, 371);  // Liquid heptane
        registerGasFluid("acetic_acid", "c8f064", 1049, 1155, 391);  // Liquid acetic acid
        registerGasFluid("carbon_disulfide", "c878c8", 1263, 352, 319);  // Liquid carbon disulfide
    }

    /**
     * Helper method to register a gas fluid
     */
    private static void registerGasFluid(String name, String hexColor, int density, int viscosity, int temperature) {
        try {
            ChemLibFluidEntry fluidEntry = createSimpleFluidEntry(
                "liquid_" + name,  // Prefix with liquid_ to distinguish from gas form
                ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "block/fluid/liquid_still"),
                ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "block/fluid/liquid_flow"),
                density,
                viscosity,
                temperature,
                0,  // No light for most liquefied gases
                hexColor
            );

            GAS_FLUIDS.add(fluidEntry);
            LOGGER.debug("Registered gas fluid: liquid_{}", name);

        } catch (Exception e) {
            LOGGER.error("Failed to register gas fluid: liquid_{}", name, e);
        }
    }

    /**
     * Creates a fluid entry using static registration (no circular dependencies)
     */
    private static ChemLibFluidEntry createSimpleFluidEntry(
            String name,
            ResourceLocation stillTexture,
            ResourceLocation flowingTexture,
            int density,
            int viscosity,
            int temperature,
            int lightLevel,
            String hexColor
    ) {
        // Parse hex color with alpha channel
        int colorInt = (int) Long.parseLong(hexColor, 16);
        if (hexColor.length() == 6) {
            colorInt |= 0xFF000000; // Add full alpha if not specified
        }

        // Register FluidType with chemical properties
        DeferredHolder<FluidType, FluidType> fluidType = FLUID_TYPES.register(name, () ->
            new FluidType(FluidType.Properties.create()
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .density(density)
                .viscosity(viscosity)
                .temperature(temperature)
                .lightLevel(lightLevel)
            )
        );

        // Use mutable pattern to avoid circular dependency
        final DeferredHolder<Fluid, BaseFlowingFluid>[] sourceFluidRef = new DeferredHolder[1];
        final DeferredHolder<Fluid, BaseFlowingFluid>[] flowingFluidRef = new DeferredHolder[1];
        final DeferredHolder<Block, LiquidBlock>[] blockRef = new DeferredHolder[1];

        // Register block
        DeferredHolder<Block, LiquidBlock> block = BLOCKS.register(name + "_block", () ->
            new LiquidBlock(sourceFluidRef[0].get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER))
        );

        // No bucket items - fluids work without them

        blockRef[0] = block;

        DeferredHolder<Fluid, BaseFlowingFluid> sourceFluid = FLUIDS.register(name,
            () -> new BaseFlowingFluid.Source(new BaseFlowingFluid.Properties(fluidType,
                () -> sourceFluidRef[0].get(), () -> flowingFluidRef[0].get())
                .block(() -> block.get()))
        );

        DeferredHolder<Fluid, BaseFlowingFluid> flowingFluid = FLUIDS.register(name + "_flowing",
            () -> new BaseFlowingFluid.Flowing(new BaseFlowingFluid.Properties(fluidType,
                () -> sourceFluidRef[0].get(), () -> flowingFluidRef[0].get())
                .block(() -> block.get()))
        );

        sourceFluidRef[0] = sourceFluid;
        flowingFluidRef[0] = flowingFluid;

        return new ChemLibFluidEntry(
            name, stillTexture, flowingTexture, colorInt,
            fluidType, sourceFluid, flowingFluid, block, null
        );
    }

    /**
     * Gets all registered element fluids
     */
    public static List<ChemLibFluidEntry> getElementFluids() {
        return new ArrayList<>(ELEMENT_FLUIDS);
    }

    /**
     * Gets all registered compound fluids
     */
    public static List<ChemLibFluidEntry> getCompoundFluids() {
        return new ArrayList<>(COMPOUND_FLUIDS);
    }

    /**
     * Gets all registered fluids
     */
    public static List<ChemLibFluidEntry> getAllFluids() {
        List<ChemLibFluidEntry> all = new ArrayList<>();
        all.addAll(ELEMENT_FLUIDS);
        all.addAll(COMPOUND_FLUIDS);
        return all;
    }

    /**
     * Gets the total number of registered fluids
     */
    public static int getTotalFluidCount() {
        return ELEMENT_FLUIDS.size() + COMPOUND_FLUIDS.size();
    }

    /**
     * Fluid entry record to hold all fluid-related objects
     */
    public record ChemLibFluidEntry(
        String name,
        ResourceLocation stillTexture,
        ResourceLocation flowingTexture,
        int color,
        DeferredHolder<FluidType, FluidType> fluidType,
        DeferredHolder<Fluid, BaseFlowingFluid> sourceFluid,
        DeferredHolder<Fluid, BaseFlowingFluid> flowingFluid,
        DeferredHolder<Block, LiquidBlock> block,
        DeferredHolder<Item, BucketItem> bucket  // Now null - no buckets
    ) {}
}