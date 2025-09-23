package com.hecookin.chemlibmekanized.datagen;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ChemlibMekanized.MODID)
public class ChemLibDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ChemlibMekanized.LOGGER.info("Starting ChemLib data generation");

        generator.addProvider(event.includeClient(), new ChemLibItemModelProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeClient(), new ChemLibLanguageProvider(packOutput));

        generator.addProvider(event.includeClient(), new ChemLibSpriteSourceProvider(packOutput, lookupProvider, existingFileHelper));

        ChemlibMekanized.LOGGER.info("ChemLib data generation providers registered");
    }
}