# ChemLibMekanized Troubleshooting Guide

## Common Issues and Solutions

### 1. Slurry Names Showing as Translation Keys

**Symptom**: Slurries display as `chemical.chemlibmekanized.dirty_ytterbium` instead of proper names

**Root Cause**: Missing entries in the slurry translations array in `ChemLibLanguageProvider.java`

**Solution**:
- Ensure ALL elements are included in BOTH the `slurries` and `crystals` arrays in `ChemLibLanguageProvider.java`
- The slurries array at lines 189-238 must match the crystals array at lines 77-127
- Run `./gradlew runData` after updating to regenerate translations

### 2. Crystal Items Not Appearing in JEI

**Symptom**: Crystal items exist but don't show up in JEI or creative tabs

**Root Cause**: Crystals not added to creative tab display items

**Solution**:
- In `ChemLibItemRegistry.java`, ensure crystals are added to creative tabs alongside ingots/nuggets/plates:
```java
// Add crystal
var crystal = METAL_CRYSTAL_ITEMS.get(metalName);
if (crystal != null && crystal.get() != null) {
    output.accept(crystal.get());
}
```

### 3. Duplicate Item Registration

**Symptom**: Some items appear twice, others missing, inconsistent behavior

**Root Cause**: Multiple registration systems competing (e.g., MetalCrystalRegistry vs ChemLibItemRegistry)

**Solution**:
- Use only ONE registration system per item type
- Remove duplicate registrations from main mod class
- Prefer dynamic registration from ChemLib data over hardcoded lists

### 4. Recipe Field Name Issues

**Critical Discovery**: Mekanism recipe JSON fields MUST use underscores, not camelCase!

**Correct**:
- `item_input` ✓
- `chemical_input` ✓
- `per_tick_usage` ✓

**Incorrect**:
- `itemInput` ✗
- `chemicalInput` ✗
- `perTickUsage` ✗

### 5. Required Recipe Fields

**Chemical Dissolution Chamber**: The `per_tick_usage` field is REQUIRED (not optional)
```json
{
  "type": "mekanism:dissolution",
  "item_input": {"count": 1, "item": "chemlibmekanized:ytterbium"},
  "chemical_input": {"amount": 10, "chemical": "mekanism:hydrochloric_acid"},
  "output": {"amount": 200, "id": "chemlibmekanized:dirty_ytterbium"},
  "per_tick_usage": false  // REQUIRED FIELD
}
```

### 6. Item Color/Texture Issues

**Symptom**: Items appear white/uncolored despite having color data

**Root Cause**: Missing color provider registration

**Solution**:
- Ensure all colored items are registered in `ChemLibColorProviders.java`
- Use the correct item registry (e.g., `ChemLibItemRegistry.METAL_CRYSTAL_ITEMS` not `MetalCrystalRegistry.CRYSTAL_ITEMS`)

### 7. Missing Super-Heavy Elements

**Issue**: Elements with atomic numbers > 92 not working properly

**Root Cause**:
- ChemLib data extraction forced `hasItem = false` for artificial elements
- Missing enum entries in `ChemLibMetal` enum

**Solution**:
- Force `hasItem = true` in `ChemLibDataExtractor.extractElements()`
- Add all super-heavy elements to `ChemLibMetal` enum in `ChemlibMekanizedChemicals.java`

## Key Patterns to Remember

### Translation Arrays Must Match
The following arrays in `ChemLibLanguageProvider.java` must have matching entries:
- `slurries` array (for slurry translations)
- `crystals` array (for crystal item translations)

If an element is in one, it must be in both!

### Registration Order Matters
1. Chemicals (slurries) must be registered first
2. Items (crystals) registered second
3. Creative tabs registered third
4. Color providers registered during client setup

### Chemical Processing Chain
For each metal/metalloid element:
1. Element item → Dissolution → Dirty Slurry (4 recipes with different acids)
2. Dirty Slurry → Washing → Clean Slurry
3. Clean Slurry → Crystallizing → Crystal
4. Crystal → Smelting → Ingot

All four stages must have:
- Registered items/chemicals
- Proper translations
- Valid recipes
- Creative tab entries (for items)

## Debugging Tips

### Check Registration Counts
Look for log messages like:
```
Registered 92 metal/metalloid crystals
Registered color handlers for 118 elements, 100 compounds, 92 metal ingots, 92 nuggets, 92 plates, 92 crystals
```

Numbers should match across related items.

### Verify Recipe Files
Dissolution recipes: `/data/chemlibmekanized/recipe/dissolution/`
Washing recipes: `/data/chemlibmekanized/recipe/washing/`
Crystallizing recipes: `/data/chemlibmekanized/recipe/crystallizing/`

Each element should have:
- 4 dissolution recipes (hydrochloric, hydrogen_chloride, sulfuric, water)
- 1 washing recipe
- 1 crystallizing recipe

### Use JEI for Testing
JEI is the best way to verify:
- Items exist and are registered
- Recipes are loaded correctly
- Processing chains are complete
- Translations are working