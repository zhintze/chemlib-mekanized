package com.hecookin.chemlibmekanized;

import com.hecookin.chemlibmekanized.client.ChemLibColorProviders;
import com.hecookin.chemlibmekanized.registry.ChemLibFluidRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = ChemlibMekanized.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = ChemlibMekanized.MODID, value = Dist.CLIENT)
public class ChemlibMekanizedClient {
    public ChemlibMekanizedClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        ChemlibMekanized.LOGGER.info("HELLO FROM CLIENT SETUP");
        ChemlibMekanized.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        ChemlibMekanized.LOGGER.info("Registering ChemLib item color providers");
        ChemLibColorProviders.registerItemColors(event);
        ChemlibMekanized.LOGGER.info("ChemLib item color providers registered");
    }

    @SubscribeEvent
    static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        ChemlibMekanized.LOGGER.info("Registering ChemLib fluid extensions");
        registerFluidExtensions(event);
        ChemlibMekanized.LOGGER.info("ChemLib fluid extensions registered");
    }

    /**
     * Registers client fluid type extensions for ChemLib fluids
     * Following ImmersiveEngineering's proven approach
     */
    private static void registerFluidExtensions(RegisterClientExtensionsEvent event) {
        // Register extensions for all our ChemLib fluids
        for (ChemLibFluidRegistry.ChemLibFluidEntry fluidEntry : ChemLibFluidRegistry.getAllFluids()) {
            event.registerFluidType(
                new IClientFluidTypeExtensions() {
                    @NotNull
                    @Override
                    public ResourceLocation getStillTexture() {
                        return fluidEntry.stillTexture();
                    }

                    @NotNull
                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return fluidEntry.flowingTexture();
                    }

                    @Override
                    public int getTintColor() {
                        return fluidEntry.color();
                    }
                },
                fluidEntry.fluidType().value()
            );

            ChemlibMekanized.LOGGER.debug("Registered client extension for fluid: {}", fluidEntry.name());
        }
    }

    // Model registration not needed for fluids - texture paths are handled by client extensions

}
