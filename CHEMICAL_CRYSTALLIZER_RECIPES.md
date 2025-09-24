# Chemical Crystallizer Recipes Implementation

## Overview
This document tracks the implementation of Chemical Crystallizer recipes for ChemLibMekanized.
The Chemical Crystallizer converts chemicals (slurries, gases, infusions) into solid items.

## Recipe Format
All recipes follow the Mekanism Chemical Crystallizer format:
```json
{
  "type": "mekanism:crystallizing",
  "chemical_type": "gas",  // or "slurry" or "infusion"
  "input": {
    "amount": 100,
    "chemical": "mekanism:lithium"
  },
  "output": {
    "item": "mekanism:dust_lithium"
  }
}
```

## Implementation Strategy
1. Convert chemical outputs back to useful items
2. Create processing chains with dissolution chamber
3. Focus on chemicals we've already created
4. Balance amounts for gameplay value

## Batch 1: Basic Chemical to Item Conversions (✅ Complete - 17 recipes)
*Generated: 2024-12-23*
*Updated: 2024-12-23 - Removed 3 unrealistic recipes, changed argon output*

### Implemented Recipes (17 total - all working):

#### Carbon Infusion → Items (5 recipes):
1. **carbon_to_coal**: 16x Carbon → Coal (matches dissolution)
2. **carbon_to_charcoal**: 16x Carbon → Charcoal (alternative)
3. **carbon_to_black_dye**: 4x Carbon → Black Dye
4. **large_carbon_to_diamond**: 256x Carbon → Diamond (matches dissolution)
5. **carbon_to_coal_block**: 144x Carbon → Coal Block

#### Redstone Infusion → Items (2 recipes):
6. **redstone_infusion_to_dust**: Redstone infusion → Redstone dust
7. **redstone_infusion_to_block**: 9x Redstone infusion → Redstone Block

#### Metal Slurries → Raw Ores (3 recipes):
8. **dirty_iron_to_raw_iron**: 2x Dirty Iron → Raw Iron
9. **dirty_gold_to_raw_gold**: 2x Dirty Gold → Raw Gold
10. **dirty_copper_to_raw_copper**: 3x Dirty Copper → Raw Copper

#### Noble Gases → Special Items (4 recipes):
11. **xenon_to_glowstone_dust**: Xenon → Glowstone Dust
12. **krypton_to_prismarine_crystals**: 2x Krypton → Prismarine Crystals
13. **neon_to_orange_dye**: Neon → Orange Dye
14. **argon_to_purple_dye**: Argon → Purple Dye

#### Compound Gases → Useful Items (2 recipes):
15. **ammonia_to_bone_meal**: 2x Ammonia → Bone Meal (fertilizer)
16. **hydrogen_sulfide_to_gunpowder**: 4x H₂S → Gunpowder

#### Basic Elements → Items (1 recipe):
17. **nitrogen_to_packed_ice**: 8x Nitrogen → Packed Ice

### Key Features:
- Creates complete processing loops with dissolution chamber
- Provides multiple output options for carbon (coal, charcoal, dye, diamond)
- Noble gases produce decorative/special items
- Practical uses for chemical compounds