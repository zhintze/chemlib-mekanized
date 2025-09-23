# NeoForge 1.21.1 Troubleshooting Guide

## Common Issues and Solutions

### 1. Purple/Black Missing Texture Pattern

**Symptoms**:
- All mod items show purple/black checkered pattern
- Console shows "Missing textures in model" warnings

**Diagnosis**:
```bash
# Check for missing texture errors
./gradlew runClient 2>&1 | grep -E "Missing texture"
```

**Solutions**:
1. **Implement SpriteSourceProvider** (most common cause)
2. **Run datagen**: `./gradlew runData`
3. **Verify texture files exist** in correct directory
4. **Check atlas configuration** is generated properly

### 2. Model Registration Errors

**Symptoms**:
```
IllegalArgumentException: Model Location: yourmod:model_name#inventory is missing
```

**Cause**: Attempting manual model registration in NeoForge 1.21.1

**Solution**: Remove manual registration code:
```java
// ❌ DON'T DO THIS in NeoForge 1.21.1
@SubscribeEvent
static void onModelRegister(ModelEvent.RegisterAdditional event) {
    event.register(new ModelResourceLocation(...)); // Remove this
}
```

Template models load automatically when referenced by individual item models.

### 3. Datagen Failures

**Symptoms**:
- `./gradlew runData` fails
- Generated files not created

**Common Causes & Solutions**:

**Missing Dependencies**:
```java
// Ensure all required parameters are provided
generator.addProvider(event.includeClient(),
    new YourSpriteSourceProvider(packOutput, lookupProvider, existingFileHelper));
```

**Wrong Event Registration**:
```java
// Correct registration
@EventBusSubscriber(modid = YourMod.MODID)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        // Implementation
    }
}
```

### 4. Color Tinting Not Working

**Symptoms**:
- Items show textures but wrong colors
- All items appear the same color

**Solutions**:

**Register Color Handlers**:
```java
@SubscribeEvent
static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
    for (RegistryObject<Item> item : YOUR_ITEMS.getEntries()) {
        if (item.get() instanceof YourColoredItem coloredItem) {
            event.register(coloredItem::getColor, item.get());
        }
    }
}
```

**Implement getColor Method**:
```java
@Override
public int getColor(ItemStack stack, int tintIndex) {
    // Layer 0 = colored, Layer 1+ = white (-1)
    return tintIndex > 0 ? -1 : yourItemColor;
}
```

### 5. Build Errors

**Gradle Version Issues**:
```bash
# Update gradle wrapper
./gradlew wrapper --gradle-version=8.14.3
```

**Java Version Mismatch**:
```bash
# Verify Java 21
java -version
# Should show: openjdk version "21.x.x"
```

**Dependencies Not Resolving**:
```gradle
// In build.gradle - ensure repositories are correct
repositories {
    maven { name = 'NeoForged'; url = 'https://maven.neoforged.net/releases' }
    maven { name = 'Mojang'; url = 'https://libraries.minecraft.net/' }
    mavenCentral()
}
```

### 6. Client Startup Issues

**Long Startup Times**:
- Normal for development environment
- First run generates caches (~2-3 minutes)
- Subsequent runs should be faster (~30-60 seconds)

**Memory Issues**:
```bash
# Increase JVM memory in gradle.properties
org.gradle.jvmargs=-Xmx4G -XX:MaxMetaspaceSize=1G
```

**Port Conflicts**:
- Default debug port: 5005
- Change if needed: `-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5006`

### 7. Mixin Warnings

**Expected Warnings** (safe to ignore):
```
Class version 65 required is higher than the class version supported by the current version of Mixin
```

**Actual Mixin Errors**:
- Check mixin syntax and target methods
- Verify mixin configuration in `mods.toml`

### 8. Resource Loading Issues

**Assets Not Loading**:
1. **Check directory structure**:
   ```
   src/main/resources/assets/yourmod/
   ├── textures/
   ├── models/
   ├── lang/
   └── ...
   ```

2. **Verify mod ID consistency** in all files

3. **Case sensitivity**: Linux/Mac are case-sensitive, Windows is not

### 9. JEI Integration Problems

**Items Not Showing in JEI**:
```java
// Ensure creative tab registration
public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
    DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

public static final RegistryObject<CreativeModeTab> YOUR_TAB = CREATIVE_MODE_TABS.register("your_tab",
    () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup.yourmod.tab"))
        .icon(() -> YOUR_ITEM.get().getDefaultInstance())
        .displayItems((parameters, output) -> {
            // Add items to creative tab
            YOUR_ITEMS.getEntries().forEach(item -> output.accept(item.get()));
        })
        .build());
```

### 10. Mekanism Integration Issues

**Chemical Registration Failures**:
```java
// Ensure proper event timing
@SubscribeEvent
public static void registerChemicals(FMLCommonSetupEvent event) {
    event.enqueueWork(() -> {
        // Chemical registration code here
        // Must be in enqueueWork for thread safety
    });
}
```

**Chemical Not Found Errors**:
- Verify chemical IDs match exactly
- Check that Mekanism is loaded before your mod

### 11. Development Workflow Issues

**Changes Not Reflected**:
1. **For texture changes**: Run `./gradlew runData` then restart client
2. **For code changes**: Restart client (hot reload limited in NeoForge)
3. **For recipe changes**: Run `./gradlew runData` and reload world

**Clean Build When Needed**:
```bash
./gradlew clean
./gradlew runData
./gradlew build
./gradlew runClient
```

### 12. Performance Issues

**High Memory Usage**:
- Large texture atlases consume VRAM
- Monitor atlas sizes in logs
- Optimize texture organization

**Slow Startup**:
- First run generates caches
- Large numbers of items increase load time
- Consider lazy initialization for complex systems

## Debug Commands

### Useful Log Filters
```bash
# General mod loading
./gradlew runClient 2>&1 | grep -E "(ERROR|WARN|Loading.*yourmod)"

# Texture-specific
./gradlew runClient 2>&1 | grep -E "(texture|atlas|Missing)"

# Datagen
./gradlew runData 2>&1 | grep -E "(Gathering|Generated|ERROR)"

# Chemical registration
./gradlew runClient 2>&1 | grep -E "(chemical|mekanism|Registration)"
```

### File Verification
```bash
# Check texture files
find src/main/resources/assets/*/textures/ -name "*.png" | head -10

# Check model files
find src/main/resources/assets/*/models/ -name "*.json" | head -10

# Check generated files
find src/generated/resources/ -type f | head -10
```

### Atlas Debugging
```bash
# Check atlas generation
ls -la src/generated/resources/assets/minecraft/atlases/

# Verify atlas content
cat src/generated/resources/assets/minecraft/atlases/blocks.json
```

## Environment Verification

### Prerequisites Checklist
- [ ] Java 21 installed and active
- [ ] Gradle 8.14.3 or compatible
- [ ] NeoForge 21.1.190+
- [ ] Minecraft 1.21.1
- [ ] IDE configured for Java 21
- [ ] Git configured (for version control)

### Development Setup
```bash
# Verify environment
java -version
./gradlew --version
git --version

# Initialize project
./gradlew clean
./gradlew runData
./gradlew build

# Test setup
./gradlew runClient --info
```

## Getting Help

### Log Information to Provide
1. **Full error stack trace**
2. **Gradle version**: `./gradlew --version`
3. **Java version**: `java -version`
4. **NeoForge version** from build.gradle
5. **Relevant code snippets**
6. **File structure** around the problem area

### Common Resources
- [NeoForge Documentation](https://docs.neoforged.net/)
- [NeoForge Discord](https://discord.neoforged.net/)
- [Minecraft Development Kit](https://github.com/MinecraftForge/MDK)
- [ChemLibMekanized Examples](https://github.com/zhintze/chemlib-mekanized)

This troubleshooting guide is based on real-world development experience with ChemLibMekanized and covers the most common issues encountered during NeoForge 1.21.1 mod development.