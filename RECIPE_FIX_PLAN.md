# Recipe System Fix Plan

## Issues Identified

### 1. Duplicate HCl Dissolution Recipes
- **Problem**: ALL 86 elements have both `_hcl.json` and `_hydrochloric.json` files
- **Cause**: Using two different chemical IDs for the same acid:
  - `mekanism:hydrogen_chloride` (gas form)
  - `mekanism:hydrochloric_acid` (liquid form)
- **Fix**: Remove one set (recommend keeping `_hcl.json` with hydrogen_chloride)

### 2. Missing Sulfuric Acid Dissolution
- **Problem**: 66 regular elements missing sulfuric dissolution recipes
- **Affected**: All elements except super-heavy (104+) and technetium
- **Fix**: Generate missing sulfuric acid recipes for these 66 elements

### 3. Seaborgium Washing Recipe Not Working
- **Status**: Recipe file exists and looks correct
- **Slurry Registration**: Confirmed registered in ChemlibMekanizedChemicals enum
- **Possible Issues**:
  1. Case sensitivity in chemical IDs
  2. Registration timing issue
  3. JEI integration problem

### 4. Chemical Registration Verification Needed
- Need to verify at runtime that slurries are actually being registered
- Check if there's a mod load order issue

## Fix Implementation Steps

### Step 1: Remove Duplicate HCl Recipes
```bash
# Remove all _hydrochloric.json files, keep _hcl.json
rm src/main/resources/data/chemlibmekanized/recipe/dissolution/element_*_dissolution_hydrochloric.json
rm src/main/resources/data/chemlibmekanized/recipe/dissolution/metals/element_*_to_slurry_hydrochloric.json
```

### Step 2: Generate Missing Sulfuric Recipes
Create sulfuric acid dissolution for these 66 elements:
- aluminum, antimony, arsenic, barium, beryllium, bismuth, boron, cadmium, calcium
- cerium, cesium, chromium, cobalt, copper, dysprosium, erbium, europium
- gadolinium, gallium, germanium, gold, hafnium, holmium, indium, iridium, iron
- lanthanum, lead, lithium, lutetium, magnesium, manganese, molybdenum
- neodymium, nickel, niobium, osmium, palladium, platinum, polonium, potassium
- praseodymium, promethium, rhenium, rhodium, rubidium, ruthenium, samarium
- scandium, silver, sodium, strontium, tantalum, terbium, thallium, thorium
- thulium, tin, titanium, tungsten, uranium, vanadium, ytterbium, yttrium, zinc, zirconium

### Step 3: Debug Seaborgium Issue
Add debug logging to verify:
1. Slurry registration at startup
2. Recipe loading confirmation
3. Chemical ID matching

### Step 4: Verify All Chemicals Are Registered
Check that all elements in recipes have corresponding registered chemicals

## Files to Check
- `/src/main/java/com/hecookin/chemlibmekanized/registry/ChemlibMekanizedChemicals.java`
- `/src/main/resources/data/chemlibmekanized/recipe/dissolution/`
- `/src/main/resources/data/chemlibmekanized/recipe/washing/`

## Testing Plan
1. Remove duplicate HCl recipes
2. Add missing sulfuric recipes
3. Build and run client
4. Check JEI for:
   - Seaborgium washing recipe visibility
   - Correct number of dissolution recipes per element
5. Test in-game processing of seaborgium