# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a NeoForge Minecraft mod called "Chemlib_Mekanized" that integrates ChemLib's chemical system with Alchemistry's processing logic and Mekanism's advanced machinery and automation. The mod is built for Minecraft 1.21.1 using NeoForge 21.1.209 and targets Java 21.

## Development Commands

### Building and Running
- `./gradlew build` - Build the mod
- `./gradlew runClient` - Run the mod in a Minecraft client environment
- `./gradlew runServer` - Run the mod in a Minecraft server environment
- `./gradlew runGameTestServer` - Run game tests
- `./gradlew runData` - Generate data files (recipes, loot tables, etc.)

### Dependency Management
- `./gradlew --refresh-dependencies` - Refresh local dependency cache
- `./gradlew clean` - Clean build artifacts

## Integration Architecture

### Chemical System Integration
The mod creates a unified chemistry processing ecosystem by:

1. **Chemical Registry** (`MekanismChemicalRegistry`): Maps ChemLib chemicals to Mekanism format
   - Element gases and slurries for different matter states
   - Compound gases for chemical processing
   - Centralized lookup system for chemical conversions

2. **Chemical Integration** (`ChemicalIntegration`): Bridges ChemLib and Mekanism APIs
   - Registers test chemicals for development (hydrogen, oxygen, carbon, etc.)
   - Handles matter state conversions (solid → slurry, gas → chemical)
   - Preserves chemical properties like radioactivity

3. **Recipe Conversion Systems**:
   - **ChemicalConversionRecipes**: State conversion mappings between solid items, liquids, gases, and slurries
   - **DecompositionRecipeConverter**: Converts Alchemistry dissolution recipes to Mekanism format
   - **SynthesisRecipeConverter**: Converts Alchemistry combination recipes for Mekanism machines

### Processing Flow
1. **Decomposition**: Items → Chemical components (via Chemical Dissolution Chamber)
2. **State Conversion**: Solids ↔ Slurries ↔ Gases (via Rotary Condensentrator, Chemical Crystallizer)
3. **Synthesis**: Chemical components → New compounds/items (via Chemical Infuser, Pressurized Reaction Chamber)

## Project Structure

### Core Architecture
- **Main Mod Class**: `ChemlibMekanized.java` - Entry point with integration initialization
- **Client-Side Class**: `ChemlibMekanizedClient.java` - Client-only functionality
- **Configuration**: `Config.java` - Mod configuration system

### Integration Modules
- **`registry/MekanismChemicalRegistry`** - Chemical registration and lookup
- **`integration/ChemicalIntegration`** - Chemical system bridging
- **`recipes/ChemicalConversionRecipes`** - State conversion mappings
- **`recipes/DecompositionRecipeConverter`** - Alchemistry → Mekanism decomposition
- **`recipes/SynthesisRecipeConverter`** - Alchemistry → Mekanism synthesis

### Dependencies
- **ChemLib**: Chemical foundation (elements, compounds, properties)
- **Alchemistry**: Recipe logic and processing relationships
- **Mekanism**: Processing infrastructure and automation
- **JEI**: Recipe viewing integration

## Development Notes

### Chemical Processing Pipeline
The mod implements a three-stage chemical processing system:
1. **Input Processing**: Convert items to chemical components
2. **Chemical Manipulation**: Transform, combine, or separate chemicals
3. **Output Synthesis**: Convert chemicals back to usable items

### Recipe System Architecture
- Probabilistic outputs supported for realistic chemical reactions
- Multiple input/output recipes for complex synthesis
- State-aware conversions (respects matter states from ChemLib)
- Mekanism machine compatibility for automation

### Code Conventions
- Package structure: `com.hecookin.chemlibmekanized.{module}`
- Chemical IDs follow format: `{modid}:{type}_{chemical_name}`
- Recipe converters use builder pattern for complex recipes
- All integration happens during FMLCommonSetupEvent for proper mod loading order

### Logging
Uses SLF4J logger with detailed initialization logging for troubleshooting integration issues. Key log points:
- Chemical registration progress
- Recipe conversion statistics
- Integration system initialization status