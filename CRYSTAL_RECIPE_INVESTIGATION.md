# Crystal Recipe Investigation Report
**Date**: September 25, 2024
**Issue**: Seaborgium and Ytterbium crystal recipes not showing in JEI despite fixes

## Critical Background Reading
Before proceeding with this investigation, **PLEASE REVIEW** these essential documents in the `_AI_ASSISTANCE/` folder:
- `MEKANISM_RECIPE_KNOWLEDGE.md` - Core recipe system understanding
- `Mekanism_Recipe_Generation_Guide.md` - Recipe format specifications
- `CHEMLIBMEKANIZED_LESSONS_LEARNED.md` - Previous issues and solutions
- `MEKANISM_PRC_RECIPE_GUIDE.md` - Pressurized Reaction Chamber specifics
- `NEOFORGE_MEKANISM_INTEGRATION.md` - Integration architecture

## Problem Statement
Ytterbium and Seaborgium (along with other super-heavy elements) have issues in their processing chain:
1. ✅ Elements exist and show in JEI
2. ✅ Dissolution recipes work (element → dirty slurry)
3. ✅ Slurry names display correctly (after fixing translation arrays)
4. ✅ Crystal items exist and appear with correct textures
5. ✅ **Crystal recipes FIXED - incorrect field name issue resolved**

## What We've Fixed So Far

### 1. Translation Keys (FIXED)
**File**: `ChemLibLanguageProvider.java`
- Added missing elements to `slurries` array (lines 206-210, 227-232)
- Added same elements to `crystals` array (lines 117-127)
- Elements added: ytterbium, lutetium, lawrencium, einsteinium, seaborgium, etc.

### 2. Crystal Item Registration (FIXED)
**File**: `ChemLibItemRegistry.java`
- Fixed duplicate registration issues with HashSet
- Crystals now properly added to creative tabs
- Color providers correctly registered

### 3. Crystal Model Generation (FIXED)
**File**: `ChemLibItemModelProvider.java`
- Added `generateMetalCrystalModels()` method
- Fixed texture path: `items/crystal` → `item/crystal_base`
- Generated 76 crystal models via data generation

### 4. Recipe Amounts (FIXED)
**Files**: All `src/main/resources/data/chemlibmekanized/recipe/crystallizing/*.json`
- Changed amount from 200mB to 1800mB for all 98 recipes
- This aligns with balance: 9 elements = 1800mB = 1 crystal = 1 ingot

### 5. Recipe Field Names (FIXED - THIS WAS THE ISSUE!)
**Files**: All `src/main/resources/data/chemlibmekanized/recipe/crystallizing/*.json`
- Changed field name from `"chemical_input"` to `"input"`
- Mekanism crystallizer recipes use `"input"` not `"chemical_input"`
- Discovered by examining Mekanism's own lithium crystallizer recipe

## Current Recipe Structure

### Washing Recipe (wash_ytterbium.json) - APPEARS TO WORK
```json
{
  "type": "mekanism:washing",
  "fluid_input": {
    "amount": 5,
    "fluid": "minecraft:water"
  },
  "chemical_input": {
    "amount": 1,
    "chemical": "chemlibmekanized:dirty_ytterbium"
  },
  "output": {
    "id": "chemlibmekanized:clean_ytterbium"
  }
}
```

### Crystallizing Recipe (crystallize_ytterbium.json) - FIXED!
```json
{
  "type": "mekanism:crystallizing",
  "input": {  // <-- FIXED: Was "chemical_input", should be "input"
    "amount": 1800,
    "chemical": "chemlibmekanized:clean_ytterbium"
  },
  "output": {
    "count": 1,
    "id": "chemlibmekanized:ytterbium_crystal"
  }
}
```

## Potential Issues to Investigate

### 1. Recipe Format Discrepancy
The crystallizing recipe format might be wrong. Need to check:
- Is `chemical_input` the correct field name?
- Should it be `slurry_input` instead?
- Is the output format correct (`id` vs `item`)?

### 2. Chemical Type Missing
Some Mekanism recipes require a `chemicalType` field:
```json
"chemicalType": "slurry"
```

### 3. Registration Order
- Are slurries being registered before recipes are loaded?
- Is there a timing issue with chemical registration?

### 4. JEI Integration
- Is JEI properly detecting our chemical types?
- Are there missing JEI plugin registrations?

## Processing Chain Balance (CORRECTED)
**Critical**: We discovered crystals produce INGOTS, not nuggets!
- 1 element = 200mB chemical/slurry
- 9 elements = 1800mB slurry = 1 crystal = 1 INGOT
- This is much more efficient than direct crafting (9:1 vs 81:1)

## Files to Check

### Recipe Files
- `/data/chemlibmekanized/recipe/crystallizing/crystallize_ytterbium.json`
- `/data/chemlibmekanized/recipe/crystallizing/crystallize_seaborgium.json`
- `/data/chemlibmekanized/recipe/washing/wash_ytterbium.json`
- `/data/chemlibmekanized/recipe/washing/wash_seaborgium.json`

### Registration Files
- `ChemLibSlurries.java` - Slurry chemical registration
- `MetalCrystalRegistry.java` - Crystal item registration
- `ChemLibItemRegistry.java` - Item and creative tab setup

### Data Generation
- `ChemLibLanguageProvider.java` - Translation keys
- `ChemLibItemModelProvider.java` - Model generation

## Next Steps

1. **Compare with working Mekanism crystallizer recipes**
   - Find examples in Mekanism's own data files
   - Check exact field names and structure

2. **Verify chemical registration**
   - Ensure clean slurries are properly registered as chemicals
   - Check if they're recognized by Mekanism's recipe system

3. **Test with minimal example**
   - Create a simple test recipe with known working chemicals
   - Gradually modify to match our structure

4. **Check logs for recipe loading errors**
   - Look for warnings about invalid recipe formats
   - Check for missing chemical references

## Known Working Elements
For comparison, these elements have fully functional chains:
- Iron (uses Mekanism's native slurries)
- Gold (uses Mekanism's native slurries)
- Copper (uses Mekanism's native slurries)

## Elements with Issues
All custom slurries, particularly:
- Ytterbium
- Seaborgium
- Lawrencium
- Other lanthanides and actinides

## Command Reference
```bash
# Run client to test
./gradlew runClient

# Generate data (models, lang)
./gradlew runData

# Check recipe files
ls src/main/resources/data/chemlibmekanized/recipe/crystallizing/*ytterbium*
ls src/main/resources/data/chemlibmekanized/recipe/washing/*ytterbium*

# Search for recipe patterns
grep -r "mekanism:crystallizing" src/main/resources/data/
```

## Critical Knowledge Points

1. **Field Names**: Mekanism uses snake_case (e.g., `chemical_input`, not `chemicalInput`)
2. **Required Fields**: Some fields like `per_tick_usage` are mandatory for certain recipe types
3. **Chemical vs Slurry**: Need to understand when to use generic "chemical" vs specific "slurry"
4. **Amount Units**: Slurries measured in mB (millibuckets)
5. **Translation Keys**: Format is `chemical.{modid}.{chemical_name}`

## Hypothesis
The most likely issue is that crystallizing recipes need a different format or additional fields when working with custom slurries. The recipe might need:
- A `chemicalType: "slurry"` field
- Different input field name (`slurry_input` instead of `chemical_input`)
- Additional metadata about the chemical type

## SOLUTION FOUND! 🎉

The issue was simple but critical: **Mekanism crystallizer recipes use `"input"` not `"chemical_input"`**

### The Fix Applied:
1. Changed all 98 crystallizing recipes from:
   ```json
   "chemical_input": { ... }
   ```
   to:
   ```json
   "input": { ... }
   ```

2. Also ensured all recipes use 1800mB (not 200mB) for proper balance

### Working Recipe Format:
```json
{
  "type": "mekanism:crystallizing",
  "input": {
    "amount": 1800,
    "chemical": "chemlibmekanized:clean_ytterbium"
  },
  "output": {
    "count": 1,
    "id": "chemlibmekanized:ytterbium_crystal"
  }
}
```

### Lessons Learned:
1. **Always check Mekanism's own recipes** for correct field names
2. **Field naming inconsistency** - Some recipes use `chemical_input`, others use `input`
3. **Documentation is critical** - This issue took significant debugging that could have been avoided

## Action Items (COMPLETED)
- [x] Find and analyze working Mekanism crystallizer recipes
- [x] ~~Test with `chemicalType` field added~~ (not needed)
- [x] Try `input` instead of `chemical_input` (THIS WAS IT!)
- [x] ~~Check Mekanism source for crystallizer recipe schema~~ (found via jar extraction)
- [x] Verify slurry registration is complete
- [x] Test with a known working format