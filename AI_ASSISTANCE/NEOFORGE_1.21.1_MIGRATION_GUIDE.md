# NeoForge 1.21.1 Migration Guide

This document provides essential information for migrating mods to NeoForge 1.21.1, based on real-world experience with the ChemLibMekanized project.

## Critical Changes from Forge to NeoForge 1.21.1

### 1. Texture Atlas System - **BREAKING CHANGE**

**Problem**: Manual atlas JSON files no longer work in NeoForge 1.21.1
**Old Approach (Forge)**:
```json
// /assets/modid/atlases/items.json
{
  "sources": [
    {
      "type": "directory",
      "source": "items",
      "prefix": "items/"
    }
  ]
}
```

**New Approach (NeoForge 1.21.1)**: Use `SpriteSourceProvider` during datagen

```java
package com.yourmod.datagen;

import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.concurrent.CompletableFuture;

public class YourModSpriteSourceProvider extends SpriteSourceProvider {

    public YourModSpriteSourceProvider(PackOutput output,
                                      CompletableFuture<HolderLookup.Provider> lookupProvider,
                                      ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, "yourmod", existingFileHelper);
    }

    @Override
    protected void gather() {
        // Items in NeoForge 1.21.1 use the BLOCKS atlas, not a separate items atlas
        SourceList blocksAtlas = atlas(BLOCKS_ATLAS);

        // Add your textures to the blocks atlas
        blocksAtlas.addSource(new DirectoryLister("items", "items/"));
    }
}
```

**Register in DataGen**:
```java
@SubscribeEvent
public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput packOutput = generator.getPackOutput();
    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

    generator.addProvider(event.includeClient(),
        new YourModSpriteSourceProvider(packOutput, lookupProvider, existingFileHelper));
}
```

### 2. Important Atlas Facts

- **Items use BLOCKS atlas**: In NeoForge 1.21.1, item textures are loaded into `minecraft:textures/atlas/blocks.png`, not a separate items atlas
- **Atlas generation is mandatory**: Without proper atlas configuration, textures will show as purple/black missing texture pattern
- **DirectoryLister is preferred**: Use `DirectoryLister` to include entire texture directories efficiently

### 3. Package Name Changes

**Forge → NeoForge Package Mappings**:
```java
// Old Forge imports
net.minecraftforge.* → net.neoforged.neoforge.*
net.minecraftforge.fml.* → net.neoforged.fml.*
net.minecraftforge.client.* → net.neoforged.neoforge.client.*
net.minecraftforge.common.data.* → net.neoforged.neoforge.common.data.*
```

### 4. Event System Changes

**Mod Event Bus Registration**:
```java
// NeoForge 1.21.1 - Updated annotation
@EventBusSubscriber(modid = YourMod.MODID, value = Dist.CLIENT)
public class YourModClient {

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Client setup code
    }
}
```

### 5. Data Generation Updates

**Required DataGen Providers**:
```java
public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput packOutput = generator.getPackOutput();
    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

    // Client-side providers
    generator.addProvider(event.includeClient(), new YourModItemModelProvider(packOutput, existingFileHelper));
    generator.addProvider(event.includeClient(), new YourModLanguageProvider(packOutput));
    generator.addProvider(event.includeClient(), new YourModSpriteSourceProvider(packOutput, lookupProvider, existingFileHelper));

    // Server-side providers
    generator.addProvider(event.includeServer(), new YourModRecipeProvider(packOutput, lookupProvider));
    generator.addProvider(event.includeServer(), new YourModLootTableProvider(packOutput, lookupProvider));
}
```

### 6. Configuration System

**Config File Structure**:
```java
// NeoForge 1.21.1 config registration
@EventBusSubscriber(modid = YourMod.MODID)
public class YourModConfig {
    // Config implementation remains largely the same
    // but registration may have slight changes
}
```

### 7. Mixin Compatibility

**Version Warnings**: Expect warnings like:
```
Class version 65 required is higher than the class version supported by the current version of Mixin (JAVA_16 supports class version 60)
```
These are generally safe to ignore as they're compatibility warnings, not errors.

### 8. Common Migration Issues & Solutions

#### Issue: Purple/Black Missing Textures
**Cause**: Atlas configuration not properly set up
**Solution**: Implement `SpriteSourceProvider` as shown above

#### Issue: "Missing texture in model" Errors
**Symptom**:
```
Missing textures in model yourmod:item_name#inventory:
    minecraft:textures/atlas/blocks.png:yourmod:items/texture_name
```
**Solution**:
1. Verify `SpriteSourceProvider` is registered in datagen
2. Run `./gradlew runData` to generate atlas configuration
3. Ensure texture files exist in the correct directory structure

#### Issue: Model Registration Errors
**Cause**: Trying to manually register models with `ModelEvent.RegisterAdditional`
**Solution**: Remove manual registration - template models load automatically when referenced

### 9. Development Workflow

**Recommended Build Process**:
1. `./gradlew clean` - Clean previous builds
2. `./gradlew runData` - Generate data (atlas, models, recipes)
3. `./gradlew build` - Build the mod
4. `./gradlew runClient` - Test in client

### 10. Version Compatibility

**Target Versions for ChemLibMekanized**:
- **Minecraft**: 1.21.1
- **NeoForge**: 21.1.190+
- **Java**: 21
- **Gradle**: 8.14.3

### 11. Debugging Tips

**Useful Log Filters**:
```bash
# Check texture loading
./gradlew runClient 2>&1 | grep -E "(Missing|texture|atlas|Created:)"

# Check mod loading
./gradlew runClient 2>&1 | grep -E "(Loading|MODID|ERROR)"

# Check datagen
./gradlew runData 2>&1 | grep -E "(Gathering|sprite|atlas)"
```

**Key Log Messages to Watch For**:
- `Created: 2048x2048x4 minecraft:textures/atlas/blocks.png-atlas` ✅ Good
- `Missing textures in model` ❌ Atlas issue
- `Gathering sprite sources` ✅ SpriteSourceProvider working

### 12. Best Practices

1. **Always run datagen** after texture/model changes
2. **Use programmatic providers** instead of manual JSON files
3. **Target blocks atlas** for item textures
4. **Test thoroughly** - NeoForge behavior differs from Forge in subtle ways
5. **Keep dependencies updated** - NeoForge ecosystem evolves rapidly

### 13. File Structure

**Required Directory Structure**:
```
src/
├── main/
│   ├── java/.../datagen/
│   │   ├── YourModDataGenerator.java
│   │   ├── YourModSpriteSourceProvider.java
│   │   ├── YourModItemModelProvider.java
│   │   └── YourModLanguageProvider.java
│   └── resources/assets/yourmod/
│       ├── textures/items/
│       │   ├── texture1.png
│       │   └── texture2.png
│       └── models/item/
│           ├── item1.json
│           └── item2.json
└── generated/resources/assets/minecraft/
    └── atlases/
        └── blocks.json  # Generated by SpriteSourceProvider
```

### 14. Performance Considerations

- **Atlas size**: Monitor atlas dimensions in logs - larger atlases use more VRAM
- **Texture organization**: Group related textures in subdirectories for better organization
- **Mip level warnings**: Large textures may be downscaled automatically

This guide is based on the successful migration of ChemLibMekanized from a multi-mod integration project to NeoForge 1.21.1, including the resolution of critical texture atlas loading issues.

## References

- [NeoForge Documentation](https://docs.neoforged.net/)
- [Minecraft Wiki - Resource Packs](https://minecraft.wiki/w/Resource_pack)
- [NeoForge Examples Repository](https://github.com/neoforged/NeoForge/tree/1.21.x/tests)