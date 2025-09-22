package com.hecookin.chemlibmekanized;

import com.hecookin.chemlibmekanized.client.ChemLibColorProviders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

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
    static void onModelRegister(ModelEvent.RegisterAdditional event) {
        ChemlibMekanized.LOGGER.info("Registering ChemLib template models");

        // Register all template models (NeoForge 1.21.1 requires "standalone" variant for side-loaded models)
        event.register(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "element_solid_model"), "standalone"));
        event.register(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "element_liquid_model"), "standalone"));
        event.register(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "element_gas_model"), "standalone"));
        event.register(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "compound_solid_model"), "standalone"));
        event.register(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "compound_liquid_model"), "standalone"));
        event.register(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "compound_gas_model"), "standalone"));
        event.register(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "compound_dust_model"), "standalone"));

        ChemlibMekanized.LOGGER.info("ChemLib template models registered: 7 models");
    }
}
