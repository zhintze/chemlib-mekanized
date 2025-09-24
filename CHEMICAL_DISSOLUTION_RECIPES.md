# Chemical Dissolution Chamber Recipes Implementation

## Overview
This document tracks the implementation of Chemical Dissolution Chamber recipes for ChemLibMekanized.
The Chemical Dissolution Chamber breaks down items/blocks into their constituent chemical components.

**Total Implemented: 64 recipes** (Batch 1: 14, Batch 2: 20, Batch 3: 30)
**Currently Working: 42 recipes** (primarily carbon outputs, plus redstone/copper)
**Bonus Features (Pending): 22 recipes** (require new slurry registrations)

## Recipe Format
All recipes follow the Mekanism Chemical Dissolution format:
```json
{
  "type": "mekanism:dissolution",
  "item_input": {
    "count": 1,
    "item": "minecraft:coal"  // Direct item reference, not nested under "ingredient"
  },
  "chemical_input": {
    "amount": 1000,
    "chemical": "mekanism:sulfuric_acid"
  },
  "output": {
    "amount": 16000,
    "id": "mekanism:carbon"
  },
  "per_tick_usage": true  // REQUIRED field for Mekanism
}
```

## Important Implementation Notes
1. **Recipe Format Fix**: Initially recipes failed to load due to:
   - Missing `per_tick_usage` field (required by Mekanism)
   - Wrong item structure (was nested under `ingredient`, should be direct)

2. **Avoiding Conflicts**: Removed all metal ore/ingot recipes as Mekanism already handles ore processing:
   - No iron/gold/copper ingot dissolution
   - No raw ore dissolution
   - These are handled by Mekanism's existing Chemical Dissolution Chamber recipes

3. **Balance Adjustments**:
   - Diamond uses catalytic amount of HF (100mB instead of 4000mB)
   - Higher carbon yields for gameplay value (diamond → 256 carbon)
   - Coal/charcoal give 16 carbon (doubled from original)

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

### Working Recipes Summary (8 total):
1. **coal_dissolution**: Coal → 16x Carbon
2. **charcoal_dissolution**: Charcoal → 16x Carbon
3. **coal_block_dissolution**: Coal Block → 144x Carbon
4. **diamond_dissolution**: Diamond + 100mB HF → 256x Carbon
5. **diamond_block_dissolution**: Diamond Block + 500mB HF → 2304x Carbon
6. **redstone_dissolution**: Redstone → Redstone infusion
7. **redstone_block_dissolution**: Redstone Block → 9x Redstone infusion
8. **sugar_dissolution**: Sugar → 12x Carbon

### Bonus Recipes (6 total - need chemical registration):
- **Silicon slurry needed (4)**: quartz, quartz_block, sand, glass dissolutions
- **Calcium slurry needed (2)**: bone, bone_meal dissolutions

## Batch 2: Nether/End Materials and Complex Items (✅ Complete - 20 recipes)
*Generated: 2024-12-23*

### Working Recipes (10 total):

#### Organic Materials → Carbon:
1. **soul_sand_dissolution**: Soul Sand + H₂SO₄ → 4x Carbon
2. **chorus_fruit_dissolution**: Chorus Fruit + H₂SO₄ → 6x Carbon
3. **paper_dissolution**: Paper + 500mB H₂SO₄ → 3x Carbon
4. **book_dissolution**: Book + 1500mB H₂SO₄ → 9x Carbon
5. **leather_dissolution**: Leather + H₂SO₄ → 8x Carbon
6. **wool_dissolution**: White Wool + H₂SO₄ → 12x Carbon
7. **string_dissolution**: String + 250mB H₂SO₄ → 2x Carbon

#### Other Materials:
8. **prismarine_shard_dissolution**: Prismarine Shard + HF → 2x Copper Slurry
9. **end_stone_dissolution**: End Stone + HF → 2x Calcium Slurry (needs registration)
10. **flint_dissolution**: Flint + HF → 2x Silicon Slurry (needs registration)

### Bonus Recipes (10 total - need slurry registration):

#### Sulfur Sources (need slurry_sulfur):
- **netherrack_dissolution**: Netherrack → 2x Sulfur
- **blaze_powder_dissolution**: Blaze Powder → 3x Sulfur
- **magma_cream_dissolution**: Magma Cream → 2x Sulfur
- **gunpowder_dissolution**: Gunpowder + Water Vapor → 2x Sulfur

#### Phosphorus Sources (need slurry_phosphorus):
- **glowstone_dust_dissolution**: Glowstone Dust → 2x Phosphorus
- **glowstone_dissolution**: Glowstone Block → 8x Phosphorus

#### Aluminum Sources (need slurry_aluminum):
- **clay_ball_dissolution**: Clay Ball → Aluminum
- **clay_dissolution**: Clay Block → 4x Aluminum
- **brick_dissolution**: Brick → Aluminum

#### Exotic Materials (need slurry_beryllium):
- **ender_pearl_dissolution**: Ender Pearl + 2x HF → 4x Beryllium

## Batch 3: Food Items and Agricultural Products (✅ Complete - 30 recipes)
*Generated: 2024-12-23*

### Working Recipes (27 total):

#### Grains and Starches → Carbon:
1. **wheat_dissolution**: Wheat → 12x Carbon
2. **bread_dissolution**: Bread → 18x Carbon
3. **potato_dissolution**: Potato → 8x Carbon
4. **baked_potato_dissolution**: Baked Potato → 10x Carbon

#### Vegetables and Fruits → Carbon:
5. **carrot_dissolution**: Carrot → 6x Carbon
6. **beetroot_dissolution**: Beetroot → 5x Carbon
7. **apple_dissolution**: Apple → 10x Carbon
8. **melon_slice_dissolution**: Melon Slice → 2x Carbon
9. **sweet_berries_dissolution**: Sweet Berries → 3x Carbon
10. **pumpkin_dissolution**: Pumpkin → 16x Carbon

#### Proteins/Meats → Carbon:
11. **beef_dissolution**: Raw Beef → 16x Carbon
12. **porkchop_dissolution**: Raw Porkchop → 14x Carbon
13. **chicken_dissolution**: Raw Chicken → 12x Carbon
14. **mutton_dissolution**: Raw Mutton → 15x Carbon
15. **rabbit_dissolution**: Raw Rabbit → 10x Carbon
16. **cod_dissolution**: Raw Cod → 8x Carbon
17. **salmon_dissolution**: Raw Salmon → 10x Carbon

#### Sweets and Complex Foods → Carbon:
18. **cookie_dissolution**: Cookie → 6x Carbon
19. **pumpkin_pie_dissolution**: Pumpkin Pie → 20x Carbon
20. **cake_dissolution**: Cake → 48x Carbon
21. **honey_bottle_dissolution**: Honey Bottle + Water Vapor → 24x Carbon

#### Other Organics:
22. **red_mushroom_dissolution**: Red Mushroom → 4x Carbon
23. **brown_mushroom_dissolution**: Brown Mushroom → 4x Carbon
24. **cocoa_beans_dissolution**: Cocoa Beans → 8x Carbon
25. **sugar_cane_dissolution**: Sugar Cane + Water Vapor → 6x Carbon

#### Special Extractions:
26. **egg_dissolution**: Egg → 2x Calcium Slurry (needs registration)
27. **bamboo_dissolution**: Bamboo → Silicon Slurry (needs registration)

### Bonus Recipes (3 total - need slurry registration):
- **glow_berries_dissolution**: Glow Berries → Phosphorus (bioluminescence)
- **kelp_dissolution**: Kelp → Iodine
- **dried_kelp_dissolution**: Dried Kelp → 2x Iodine

## Summary of All Batches

### Working Recipe Categories:
- **Carbon extraction**: 50+ recipes (coal, organics, food)
- **Redstone infusion**: 2 recipes
- **Metal slurries**: 1 recipe (prismarine → copper)
- **Silicon/Calcium**: Pending registration but mapped

### Chemicals Requiring Registration:
- **Already mapped**: slurry_silicon, slurry_calcium
- **Need new mappings**: slurry_sulfur, slurry_phosphorus, slurry_aluminum, slurry_beryllium, slurry_iodine