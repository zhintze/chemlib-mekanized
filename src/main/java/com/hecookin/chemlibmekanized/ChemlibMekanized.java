package com.hecookin.chemlibmekanized;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.hecookin.chemlibmekanized.registry.MekanismChemicalRegistry;
import com.hecookin.chemlibmekanized.registry.ChemLibItemRegistry;
import com.hecookin.chemlibmekanized.registry.ChemLibFluidRegistry;
import com.hecookin.chemlibmekanized.integration.ChemicalIntegration;
import com.hecookin.chemlibmekanized.integration.ImmersiveEngineeringIntegration;
import com.hecookin.chemlibmekanized.integration.MekanismChemLibIntegration;
import com.hecookin.chemlibmekanized.recipes.ChemicalConversionRecipes;
import com.hecookin.chemlibmekanized.recipes.DecompositionRecipeConverter;
import com.hecookin.chemlibmekanized.recipes.SynthesisRecipeConverter;

import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(ChemlibMekanized.MODID)
public class ChemlibMekanized {
    public static final String MODID = "chemlibmekanized";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChemlibMekanized(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        LOGGER.info("ChemLibMekanized mod initialization started");

        ChemLibItemRegistry.ITEMS.register(modEventBus);
        ChemLibItemRegistry.CREATIVE_TABS.register(modEventBus);
        ChemLibItemRegistry.initializeRegistries();

        // Register standard NeoForge fluid system (no buckets)
        ChemLibFluidRegistry.FLUIDS.register(modEventBus);
        ChemLibFluidRegistry.FLUID_TYPES.register(modEventBus);
        ChemLibFluidRegistry.BLOCKS.register(modEventBus);
        ChemLibFluidRegistry.registerAll();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("ChemLibMekanized common setup starting");
        event.enqueueWork(() -> {
            LOGGER.info("Initializing extracted ChemLib content with Mekanism integration");

            MekanismChemicalRegistry.init();
            MekanismChemLibIntegration.initializeChemicalMappings();

            LOGGER.info("Initializing legacy chemical integration for compatibility");
            ChemicalIntegration.registerAllElements();
            ChemicalIntegration.registerAllCompounds();

            LOGGER.info("Initializing recipe conversion systems");
            ChemicalConversionRecipes.initializeConversions();
            DecompositionRecipeConverter.initializeDecompositionRecipes();
            SynthesisRecipeConverter.initializeSynthesisRecipes();

            LOGGER.info("Initializing mod integrations");
            ImmersiveEngineeringIntegration.initializeIntegration();
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("ChemLibMekanized server starting - chemical processing integration ready");
    }
}
