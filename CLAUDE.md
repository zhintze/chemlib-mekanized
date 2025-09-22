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

## ChemLib Content Extraction

### Extraction System
The mod now includes a complete ChemLib content extraction system since no 1.21.1 version exists:

1. **Data Extraction** (`ChemLibDataExtractor`):
   - Parses ChemLib's `elements.json` (118 elements) and `compounds.json` (~100 compounds)
   - Preserves all chemical properties: atomic numbers, abbreviations, colors, effects, matter states
   - Maintains exact structure for seamless integration

2. **Item Registration** (`ChemLibItemRegistry`):
   - `ExtractedElementItem` and `ExtractedCompoundItem` classes
   - Creative tab organization by chemical groups (Elements, Compounds, Metals, Non-Metals, Metalloids)
   - Full tooltip support with chemical properties and Mekanism compatibility indicators

3. **Layered Texture System**:
   - Copied exact ChemLib texture structure: `element_[state]_layer_0.png` + `element_[state]_layer_1.png`
   - Supports all matter states: solid, liquid, gas
   - Base textures (layer_0) + colored overlays (layer_1) for proper chemical coloring

4. **Mekanism Integration** (`MekanismChemLibIntegration`):
   - Maps ChemLib matter states to Mekanism chemical types:
     - Elements: gas → Gas, liquid → Slurry, solid metals → Slurry, solid non-metals → Infusion
     - Compounds: gas → Gas, liquid → Slurry, solid → Infusion
   - Preserves radioactivity and hazardous properties for proper effect handling

5. **Data Generation**:
   - `ChemLibItemModelProvider`: Generates layered item models for all chemicals
   - `ChemLibLanguageProvider`: Creates en_us translations with proper formatting
   - Full automation for 200+ chemical items

## Alchemistry Integration Analysis

### Machine Types and Mekanism Equivalents

Alchemistry has 5 main processing machines that need conversion to Mekanism equivalents:

1. **Dissolver** → **Mekanism Chemical Dissolution Chamber**
   - Input: Items/blocks
   - Output: Probabilistic groups of chemicals
   - Function: Breaks down complex items into constituent elements/compounds
   - Mekanism equivalent: Custom recipes for Chemical Dissolution Chamber

2. **Compactor** → **Mekanism Chemical Infuser/Combiner**
   - Input: Chemical compounds
   - Output: Solid items/blocks
   - Function: Compresses chemicals back into physical forms
   - Mekanism equivalent: Pressurized Reaction Chamber recipes

3. **Combiner** → **Mekanism Chemical Combiner**
   - Input: Multiple chemicals
   - Output: Single compound chemical
   - Function: Synthesis of complex molecules from elements
   - Mekanism equivalent: Chemical Infuser recipes

4. **Atomizer** → **Mekanism Rotary Condensentrator**
   - Input: Liquids/fluids
   - Output: Chemical gases
   - Function: Converts fluids to gaseous chemical forms
   - Mekanism equivalent: Rotary Condensentrator (Decondensentrating mode)

5. **Liquifier** → **Mekanism Rotary Condensentrator**
   - Input: Chemical gases
   - Output: Fluids/liquids
   - Function: Condenses gases into liquid forms
   - Mekanism equivalent: Rotary Condensentrator (Condensentrating mode)

### Recipe Conversion Strategy

**Alchemistry Recipe Structure:**
```json
{
  "type": "alchemistry:dissolver",
  "input": { "ingredient": { "item": "minecraft:diamond" }, "count": 1 },
  "output": {
    "rolls": 1,
    "weighted": false,
    "groups": [
      {
        "probability": 100,
        "results": [
          { "item": "chemlib:carbon", "count": 64 }
        ]
      }
    ]
  }
}
```

**Conversion Requirements:**
1. **Probabilistic → Deterministic**: Alchemistry uses probability groups, Mekanism uses fixed recipes
2. **Multiple outputs → Single output**: Group multiple probable results into separate recipe variants
3. **ChemLib items → Mekanism chemicals**: Map ChemLib items to our extracted Mekanism chemical types

### Implementation Plan for Phase 2

1. **Recipe Scanner**: Parse all Alchemistry recipe JSONs to extract:
   - Input items and quantities
   - Output probability groups and results
   - Machine type associations

2. **Probability Resolver**: Convert probabilistic outputs to deterministic recipes:
   - High probability (>80%) → Primary recipe
   - Medium probability (40-80%) → Secondary recipe variant
   - Low probability (<40%) → Bonus/rare recipe variant

3. **Chemical Mapper**: Map ChemLib references to our extracted chemicals:
   - `"chemlib:carbon"` → `MekanismChemLibIntegration.getElementChemical("carbon")`
   - `"chemlib:water"` → `MekanismChemLibIntegration.getCompoundChemical("water")`

4. **Recipe Generators**: Create Mekanism recipe builders for each machine type:
   - `DissolverToMekanismRecipeConverter`
   - `CompactorToMekanismRecipeConverter`
   - `CombinerToMekanismRecipeConverter`
   - `AtomizerToMekanismRecipeConverter`
   - `LiquifierToMekanismRecipeConverter`

### Key Considerations

- **Balancing**: Alchemistry recipes may need rebalancing for Mekanism's processing rates
- **Energy Costs**: Convert Alchemistry FE costs to Mekanism Joules
- **Chemical States**: Ensure proper mapping between ChemLib matter states and Mekanism chemical types
- **Mod Dependencies**: Handle conditional recipes based on mod presence (Thermal, etc.)

### Next Phase Tasks

1. Parse all Alchemistry recipe files (~200+ recipes)
2. Create probability resolution algorithms
3. Generate Mekanism recipe equivalents
4. Test chemical processing chains end-to-end
5. Balance energy costs and processing times

## Current Status: Texture System Implementation

### Problem Analysis (September 2024)

**Issue**: All chemical items showing purple/black checkered "missing texture" pattern in-game.

**Root Cause**: After deep investigation comparing with ChemLib's system, discovered that while we have correctly implemented:
- ✅ ChemLib data extraction (118 elements + 100+ compounds)
- ✅ Template model system with proper parent references
- ✅ Layer texture files (18 PNG files identical to ChemLib)
- ✅ Color tinting system (`tintIndex > 0 ? -1 : getColor()`)
- ✅ ItemColorHandler registration

The missing texture pattern indicates our texture files aren't being found by Minecraft's resource loading system, not a color tinting issue.

### ChemLib's Texture System Analysis

**ChemLib Architecture (Confirmed)**:
- Uses only **18 base layer PNG files** for **710+ chemical items**
- Layer 0: Base shape (gets colored by tint)
- Layer 1: Details/highlights (stays white)
- Template models: `element_solid_model.json`, etc.
- Individual items inherit from templates: `"parent": "chemlib:item/element_gas_model"`

**Layer Textures (All Present)**:
```
element_solid_layer_0.png + element_solid_layer_1.png
element_liquid_layer_0.png + element_liquid_layer_1.png
element_gas_layer_0.png + element_gas_layer_1.png
compound_solid_layer_0.png + compound_solid_layer_1.png
compound_liquid_layer_0.png + compound_liquid_layer_1.png
compound_gas_layer_0.png + compound_gas_layer_1.png
compound_dust_layer_0.png + compound_dust_layer_1.png
bucket_layer_0.png + bucket_layer_1.png + gas_bucket_layer_0.png + gas_bucket_layer_1.png
```

### Phase 1 Implementation - Elegant Layered System ⭐

**Phase 1A: Base Layer Textures** ✅ COMPLETED
- Verified all 18 layer PNG files are identical to ChemLib's
- Files properly located in `/assets/chemlibmekanized/textures/items/`

**Phase 1B: Template Model System** ✅ COMPLETED
- Created 7 template models: `element_solid_model.json`, `element_liquid_model.json`, etc.
- Template models reference layer textures: `"layer0": "chemlibmekanized:items/element_solid_layer_0"`
- 296 individual chemical models inherit from templates: `"parent": "chemlibmekanized:item/element_gas_model"`

**Phase 1C: Color System** ✅ IMPLEMENTED
- `ExtractedElementItem.getColor()` matches ChemLib exactly
- `ChemLibColorProviders` registers handlers for all items
- Proper tint index handling for layer 0 coloring

**Current Issue**: Despite correct implementation, textures still not loading (purple/black pattern).

### Next Debugging Steps

**Phase 2A: Debug Resource Loading**
- Add debug logging to track texture path resolution
- Verify template model registration in `ModelEvent.RegisterAdditional`
- Check if ResourceLocation paths are resolving correctly

**Phase 2B: Alternative Solutions**
- Compare with working ChemLib installation side-by-side
- Test minimal reproduction (single element with direct texture reference)
- Consider if NeoForge 1.21.1 has different texture loading requirements

**Phase 2C: Fallback Approach**
- If layered system continues failing, switch to individual PNG approach
- Export all chemical textures from working ChemLib installation
- Create 296 individual texture files as backup solution

### Development Status

- **ChemLib Content**: ✅ 100% extracted and registered
- **Mekanism Integration**: ✅ Chemical mapping system complete
- **Texture System**: 🔄 Implementation complete, debugging required
- **Alchemistry Recipes**: ⏳ Pending texture resolution

The elegant layered system is theoretically correct but needs debugging to resolve resource loading issues.