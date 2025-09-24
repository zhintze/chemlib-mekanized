# Chemical Dissolution Chamber Recipes Implementation

## Overview
This document tracks the implementation of Chemical Dissolution Chamber recipes for ChemLibMekanized.
The Chemical Dissolution Chamber breaks down items/blocks into their constituent chemical components.

## Recipe Format
All recipes follow the Mekanism Chemical Dissolution format:
- Type: `mekanism:dissolution`
- Input: Item with optional count
- Gas Input: Chemical (usually sulfuric acid)
- Output: Chemical (slurry or gas)

## Dissolution Strategy
1. Break down common Minecraft items into chemical components
2. Use scientifically accurate decomposition where possible
3. Balance for gameplay (not always perfectly stoichiometric)
4. Focus on items that would realistically dissolve in acid

## Batch 1: Common Minerals and Ores (✅ Complete - 20 recipes)
*Generated: 2024-12-23*

### Implemented Recipes:

#### Carbon Materials:
1. **coal_dissolution**: Coal → 8 Carbon (infusion)
2. **charcoal_dissolution**: Charcoal → 8 Carbon (infusion)
3. **coal_block_dissolution**: Coal Block → 72 Carbon (infusion)
4. **diamond_dissolution**: Diamond → 64 Carbon (using HF acid)
5. **diamond_block_dissolution**: Diamond Block → 576 Carbon (using HF acid)

#### Metal Ores (outputs dirty slurries):
6. **iron_ingot_dissolution**: Iron Ingot → Dirty Iron Slurry
7. **raw_iron_dissolution**: Raw Iron → 2x Dirty Iron Slurry
8. **gold_ingot_dissolution**: Gold Ingot → Dirty Gold Slurry
9. **raw_gold_dissolution**: Raw Gold → 2x Dirty Gold Slurry
10. **copper_ingot_dissolution**: Copper Ingot → Dirty Copper Slurry
11. **raw_copper_dissolution**: Raw Copper → 3x Dirty Copper Slurry

#### Redstone:
12. **redstone_dissolution**: Redstone → Redstone (infusion)
13. **redstone_block_dissolution**: Redstone Block → 9x Redstone (infusion)

#### Silicon Materials (outputs need registration):
14. **quartz_dissolution**: Quartz → Silicon Slurry (using HF acid)
15. **quartz_block_dissolution**: Quartz Block → 4x Silicon Slurry (using HF acid)
16. **sand_dissolution**: Sand → Silicon Slurry (using HF acid)
17. **glass_dissolution**: Glass → Silicon Slurry (using HF acid)

#### Organic Materials:
18. **sugar_dissolution**: Sugar → 12x Carbon (C₁₂H₂₂O₁₁ decomposition)
19. **bone_dissolution**: Bone → 3x Calcium Slurry (needs registration)
20. **bone_meal_dissolution**: Bone Meal → Calcium Slurry (needs registration)

### Chemicals Used:
- **Inputs**: sulfuric_acid (most recipes), hydrofluoric_acid (hard materials)
- **Outputs from Mekanism**: carbon, redstone, dirty_iron, dirty_gold, dirty_copper
- **Outputs needing registration**: slurry_silicon, slurry_calcium

### Notes:
- Most dissolutions expect 15 out of 20 to work immediately
- Silicon and calcium slurries need to be registered in Java
- Uses realistic acids: H₂SO₄ for normal, HF for silicates/diamonds