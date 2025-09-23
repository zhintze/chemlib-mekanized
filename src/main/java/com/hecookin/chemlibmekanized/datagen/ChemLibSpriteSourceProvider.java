package com.hecookin.chemlibmekanized.datagen;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.concurrent.CompletableFuture;

public class ChemLibSpriteSourceProvider extends SpriteSourceProvider {

    public ChemLibSpriteSourceProvider(PackOutput output,
                                      CompletableFuture<HolderLookup.Provider> lookupProvider,
                                      ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ChemlibMekanized.MODID, existingFileHelper);
    }

    @Override
    protected void gather() {
        ChemlibMekanized.LOGGER.info("Gathering sprite sources for ChemLib textures");

        // Add our custom layer textures to the blocks atlas
        // Items in NeoForge 1.21.1 actually use the blocks atlas for textures
        SourceList blocksAtlas = atlas(BLOCKS_ATLAS);

        // Add all our layer textures to the blocks atlas
        blocksAtlas.addSource(new DirectoryLister("items", "items/"));

        ChemlibMekanized.LOGGER.info("Added ChemLib texture directory to blocks atlas");
    }
}