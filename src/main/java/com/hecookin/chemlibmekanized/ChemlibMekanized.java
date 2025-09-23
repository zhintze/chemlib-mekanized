package com.hecookin.chemlibmekanized;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.hecookin.chemlibmekanized.registry.ChemlibMekanizedChemicals;
import com.hecookin.chemlibmekanized.registry.ChemLibItemRegistry;
import com.hecookin.chemlibmekanized.registry.ChemLibFluidRegistry;
import com.hecookin.chemlibmekanized.integration.MekanismChemLibIntegration;
import com.hecookin.chemlibmekanized.integration.ImmersiveEngineeringIntegration;

import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(ChemlibMekanized.MODID)
public class ChemlibMekanized {
    public static final String MODID = "chemlibmekanized";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChemlibMekanized(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerChemicals);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        LOGGER.info("ChemLibMekanized mod initialization started");

        // Register Mekanism chemicals
        ChemlibMekanizedChemicals.CHEMICALS.register(modEventBus);

        ChemLibItemRegistry.ITEMS.register(modEventBus);
        ChemLibItemRegistry.CREATIVE_TABS.register(modEventBus);
        ChemLibItemRegistry.initializeRegistries();

        // Register standard NeoForge fluid system (no buckets)
        ChemLibFluidRegistry.FLUIDS.register(modEventBus);
        ChemLibFluidRegistry.FLUID_TYPES.register(modEventBus);
        ChemLibFluidRegistry.BLOCKS.register(modEventBus);
        ChemLibFluidRegistry.registerAll();
    }

    private void registerChemicals(RegisterEvent event) {
        if (event.getRegistryKey().equals(MekanismAPI.CHEMICAL_REGISTRY_NAME)) {
            LOGGER.info("Registering Mekanism chemicals for ChemLib integration");
            ChemlibMekanizedChemicals.init();
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("ChemLibMekanized common setup starting");
        event.enqueueWork(() -> {
            LOGGER.info("Initializing extracted ChemLib content with Mekanism integration");

            MekanismChemLibIntegration.initializeChemicalMappings();
            ImmersiveEngineeringIntegration.initializeIntegration();

            LOGGER.info("Mekanism gas chemical integration completed successfully");
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("ChemLibMekanized server starting - chemical processing integration ready");
    }
}
