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


    /**
     * Registers all liquid chemicals as standard NeoForge fluids
     */
    public static void registerAll() {
        LOGGER.info("Starting ChemLib fluid registration...");

        try {
            registerLiquidElements();
            registerLiquidCompounds();

            LOGGER.info("Registered {} element fluids and {} compound fluids",
                       ELEMENT_FLUIDS.size(), COMPOUND_FLUIDS.size());
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