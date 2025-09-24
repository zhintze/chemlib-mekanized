# Chemical Infuser Recipe Analysis

## Issue Summary
**Problem**: Only 22 recipes appear in-game, but we have 30 recipe JSON files.
**Root Cause**: 12 recipes use OUTPUT chemicals that are not registered in `ChemlibMekanizedChemicals.java`

## Recipe Breakdown

### Total Recipe Files: 30
- **Batch 1**: 18 recipes
- **Batch 2**: 12 recipes

### Recipe Status Analysis

#### ✅ Working Recipes (18 total)
These recipes should display correctly because all chemicals are registered:

**From Batch 1 (all 18 work):**
1. water_vapor_formation
2. ammonia_synthesis
3. methane_formation
4. carbon_dioxide_formation
5. carbon_monoxide_formation
6. nitric_oxide_formation
7. nitrogen_dioxide_formation
8. ammonia_oxidation
9. hydrogen_sulfide_oxidation
10. dry_reforming
11. hydrofluoric_acid_formation
12. sulfuric_acid_formation
13. ethylene_formation
14. acetylene_formation
15. ethene_formation
16. brine_formation
17. steam_formation
18. hydrogen_sulfide_formation

**From Batch 2 (0 work):**
- None, all Batch 2 recipes use unregistered output chemicals

#### ❌ Broken Recipes (12 total)
These recipes won't load because their OUTPUT chemicals don't exist:

**All from Batch 2:**
1. carbon_oxysulfide_formation → `compound_carbon_oxysulfide` (NOT REGISTERED)
2. chlorine_dioxide_formation → `compound_chlorine_dioxide` (NOT REGISTERED)
3. chlorine_monoxide_formation → `compound_chlorine_monoxide` (NOT REGISTERED)
4. chlorine_trifluoride_formation → `compound_chlorine_trifluoride` (NOT REGISTERED)
5. formaldehyde_formation → `compound_formaldehyde` (NOT REGISTERED)
6. hydrogen_cyanide_formation → `compound_hydrogen_cyanide` (NOT REGISTERED)
7. hydrogen_peroxide_formation → `compound_hydrogen_peroxide` (NOT REGISTERED)
8. krypton_difluoride → `compound_krypton_difluoride` (NOT REGISTERED)
9. nitrous_oxide_formation → `compound_nitrous_oxide` (NOT REGISTERED)
10. phosgene_formation → `compound_phosgene` (NOT REGISTERED)
11. radon_difluoride → `compound_radon_difluoride` (NOT REGISTERED)
12. xenon_difluoride → `compound_xenon_difluoride` (NOT REGISTERED)

## Why This Happened

1. **Batch 1 Success**: All Batch 1 recipes use chemicals that either:
   - Come from Mekanism (hydrogen, oxygen, chlorine, etc.)
   - Are registered in our `ChemlibMekanizedChemicals.java` (nitrogen, ammonia, methane, etc.)

2. **Batch 2 Failure**: Batch 2 introduced NEW compound outputs that were never registered:
   - We added them to `chemical_mappings.py` in the `NEW_COMPOUNDS` dict
   - But we never registered them in the Java code
   - Mekanism can't create recipes with non-existent output chemicals

## Discrepancy Explanation
- You see **22 recipes** in JEI (but really only 18 from our mod work)
- The extra 4 might be from Mekanism's own recipes or partial loading
- We have **30 files** but only **18 functional recipes**

## Solution Required
To fix this, we need to register all 12 missing chemicals in `ChemlibMekanizedChemicals.java`:
- compound_carbon_oxysulfide
- compound_chlorine_dioxide
- compound_chlorine_monoxide
- compound_chlorine_trifluoride
- compound_formaldehyde
- compound_hydrogen_cyanide
- compound_hydrogen_peroxide
- compound_krypton_difluoride
- compound_nitrous_oxide
- compound_phosgene
- compound_radon_difluoride
- compound_xenon_difluoride

Once these are registered, all 30 recipes should appear in-game.