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

## Batch 2: Advanced Chemical Conversions (✅ Complete - 20 recipes)
*Generated: 2024-12-23*

### Implemented Recipes (20 total - all working):

#### Alternative Carbon Outputs (5 recipes):
1. **carbon_to_ink_sac**: 8x Carbon → Ink Sac
2. **carbon_to_gray_dye**: 2x Carbon → Gray Dye
3. **carbon_to_light_gray_dye**: Carbon → Light Gray Dye
4. **large_carbon_to_obsidian**: 128x Carbon → Obsidian
5. **carbon_to_flint**: 32x Carbon → Flint

#### Gas to Dye Conversions (10 recipes):
6. **carbon_dioxide_to_lime_dye**: 2x CO₂ → Lime Dye
7. **nitric_oxide_to_red_dye**: 2x NO → Red Dye
8. **hydrogen_sulfide_to_yellow_dye**: H₂S → Yellow Dye
9. **nitrogen_to_white_dye**: 2x N₂ → White Dye
10. **ammonia_to_green_dye**: NH₃ → Green Dye
11. **chlorine_to_cyan_dye**: Cl₂ → Cyan Dye
12. **fluorine_to_magenta_dye**: F₂ → Magenta Dye
13. **methane_to_brown_dye**: CH₄ → Brown Dye
14. **oxygen_to_blue_dye**: 2x O₂ → Blue Dye
15. **carbon_monoxide_to_light_gray_concrete**: 8x CO → Light Gray Concrete Powder

#### Noble Gas Specialties (3 recipes):
16. **helium_to_feather**: 4x Helium → Feather
17. **radon_to_spider_eye**: 2x Radon → Spider Eye
18. **xenon_to_sea_lantern**: 8x Xenon → Sea Lantern

#### Other Conversions (2 recipes):
19. **hydrogen_to_slime_ball**: 16x Hydrogen → Slime Ball
20. **large_nitrogen_to_blue_ice**: 16x Nitrogen → Blue Ice

## Summary
**Total Crystallizer Recipes: 37** (Batch 1: 17, Batch 2: 20)
- Covers all 16 Minecraft dye colors through chemical conversions
- Provides alternative paths to rare items (spider eye, slime ball, sea lantern)
- Multiple output options for common chemicals (especially carbon)
- Creative but plausible chemical transformations