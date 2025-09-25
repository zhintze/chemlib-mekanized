# ChemLibMekanized Recipe System Knowledge

## Critical Mekanism Recipe Requirements

### Field Naming Convention
**IMPORTANT**: All Mekanism recipe JSON fields MUST use snake_case (underscores), NOT camelCase!

This was discovered through painful debugging when recipes silently failed with camelCase field names.

### Chemical Dissolution Chamber

**Required Format**:
```json
{
  "type": "mekanism:dissolution",
  "item_input": {
    "count": 1,
    "item": "chemlibmekanized:scandium"
  },
  "chemical_input": {
    "amount": 10,
    "chemical": "mekanism:hydrochloric_acid"
  },
  "output": {
    "amount": 200,
    "id": "chemlibmekanized:dirty_scandium"
  },
  "per_tick_usage": false
}
```

**Critical Fields**:
- `per_tick_usage` is REQUIRED (not optional as documentation might suggest)
- Use 10mb for HCl/HCl gas, 20mb for H₂SO₄, 100mb for water
- Output should be 200mb of dirty slurry (maintaining 9:1 element ratio)

### Chemical Washing (Washer)

**Format**:
```json
{
  "type": "mekanism:washing",
  "fluid_input": {
    "amount": 5,
    "fluid": "minecraft:water"
  },
  "chemical_input": {
    "amount": 1,
    "chemical": "chemlibmekanized:dirty_scandium"
  },
  "output": {
    "id": "chemlibmekanized:clean_scandium"
  }
}
```

### Chemical Crystallizing

**Format**:
```json
{
  "type": "mekanism:crystallizing",
  "chemical_input": {
    "amount": 200,
    "chemical": "chemlibmekanized:clean_scandium"
  },
  "output": {
    "count": 1,
    "id": "chemlibmekanized:scandium_crystal"
  }
}
```

### Rotary Condensentrator

**Condensentrating (Gas → Fluid)**:
```json
{
  "type": "mekanism:rotary",
  "gas_input": {
    "amount": 1,
    "chemical": "chemlibmekanized:nitrogen"
  },
  "fluid_output": {
    "amount": 1,
    "id": "chemlibmekanized:liquid_nitrogen"
  }
}
```

**Decondensentrating (Fluid → Gas)**:
```json
{
  "type": "mekanism:rotary",
  "fluid_input": {
    "amount": 1,
    "id": "chemlibmekanized:liquid_nitrogen"
  },
  "gas_output": {
    "amount": 1,
    "id": "chemlibmekanized:nitrogen"
  }
}
```

### Pressurized Reaction Chamber (PRC)

**Format for Complex Reactions**:
```json
{
  "type": "mekanism:reaction",
  "item_input": {
    "count": 1,
    "tag": "c:ingots/iron"
  },
  "fluid_input": {
    "amount": 100,
    "fluid": "minecraft:water"
  },
  "chemical_input": {
    "amount": 100,
    "chemical": "mekanism:oxygen"
  },
  "duration": 100,
  "item_output": {
    "count": 1,
    "id": "minecraft:iron_block"
  },
  "chemical_output": {
    "amount": 100,
    "id": "mekanism:hydrogen"
  }
}
```

## Common Pitfalls and Solutions

### 1. Silent Recipe Failures
**Symptom**: Recipes don't show in JEI, no error messages

**Common Causes**:
- Using camelCase field names instead of snake_case
- Missing required fields (especially `per_tick_usage`)
- Incorrect chemical references (typos in chemical IDs)

### 2. Chemical Reference Formats

**Correct**:
- Mekanism chemicals: `"mekanism:oxygen"`
- Our chemicals: `"chemlibmekanized:dirty_scandium"`

**Incorrect**:
- Missing namespace: `"oxygen"` ❌
- Wrong namespace: `"chemlib:oxygen"` ❌

### 3. Amount Field Guidelines

**Slurries**:
- Dirty/Clean slurry: Usually 200mb per crystal
- Washing ratio: 1mb dirty → 1mb clean (with 5mb water)

**Dissolution**:
- HCl (both acid and gas): 10mb
- Sulfuric acid: 20mb
- Water: 100mb

**Gas/Fluid Conversion**:
- 1:1 ratio (1mb gas = 1mb fluid)

## Processing Chain Balance

### Standard Metal Processing (9:1 ratio maintained)
1. **9 Element items** → Compactor → 1 Element Block
2. **1 Element item** → Dissolution → 200mb Dirty Slurry
3. **200mb Dirty Slurry** → Washing → 200mb Clean Slurry
4. **200mb Clean Slurry** → Crystallizing → 1 Crystal
5. **1 Crystal** → Smelting → 1 Ingot
6. **9 Nuggets** → Crafting → 1 Ingot

This maintains the ChemLib balance where 81 element items (9×9) = 1 block = 9 ingots

## Recipe Generation Scripts

### Key Python Scripts Created

1. **generate_element_dissolution_recipes.py**
   - Generates 4 dissolution recipes per element
   - Uses different acids (HCl, HCl gas, H₂SO₄, H₂O)
   - Excludes Mekanism duplicates

2. **generate_slurry_processing_recipes.py**
   - Creates washing recipes (dirty → clean)
   - Creates crystallizing recipes (clean → crystal)
   - Updates item registrations

3. **generate_rotary_recipes.py**
   - Creates gas ↔ fluid conversion recipes
   - Handles noble gases and vaporizable compounds

## Testing Checklist

When adding new elements/chemicals:

1. ✅ Register chemical in `ChemlibMekanizedChemicals.java`
2. ✅ Add to `ChemLibMetal` enum if it's a metal/metalloid
3. ✅ Generate dissolution recipes (4 per element)
4. ✅ Generate washing recipe
5. ✅ Generate crystallizing recipe
6. ✅ Add translations in `ChemLibLanguageProvider`:
   - Slurries array
   - Crystals array
7. ✅ Register crystal item in `ChemLibItemRegistry`
8. ✅ Add to creative tabs
9. ✅ Register color provider
10. ✅ Run `./gradlew runData` to generate lang files
11. ✅ Test in JEI that full chain appears

## Mekanism Machine Processing Rates

Default processing times (can be upgraded):
- **Dissolution Chamber**: 100 ticks (5 seconds)
- **Chemical Washer**: 10 ticks (0.5 seconds)
- **Chemical Crystallizer**: 200 ticks (10 seconds)
- **Rotary Condensentrator**: 1 tick (instant)
- **Pressurized Reaction Chamber**: Variable (set in recipe)