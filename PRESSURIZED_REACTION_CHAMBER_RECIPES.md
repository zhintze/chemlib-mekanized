# Pressurized Reaction Chamber Recipes Implementation

## Overview
This document tracks the implementation of Pressurized Reaction Chamber (PRC) recipes for ChemLibMekanized.
The PRC is Mekanism's most versatile machine, handling complex reactions with multiple inputs and outputs.

## Recipe Format
PRC recipes support multiple input types and outputs:
```json
{
  "type": "mekanism:reaction",
  "item_input": {
    "count": 1,
    "item": "minecraft:coal"
  },
  "fluid_input": {
    "amount": 1000,
    "id": "minecraft:water"
  },
  "chemical_input": {
    "amount": 1000,
    "chemical": "mekanism:oxygen"
  },
  "energy_required": 100.0,
  "duration": 100,
  "item_output": {
    "id": "minecraft:diamond"
  },
  "chemical_output": {
    "amount": 1000,
    "id": "mekanism:hydrogen"
  }
}
```

## Implementation Strategy
1. Focus on complex multi-input reactions
2. Create practical chemical synthesis chains
3. Use chemicals we've already registered
4. Balance energy and time requirements

## Batch 1: Basic Industrial Reactions (✅ Complete - 20 recipes)
*Generated: 2024-12-23*

### Implemented Recipes (20 total):

#### Water-Based Reactions (2 recipes):
1. **steam_coal_gasification**: Coal + Water → CO + H₂
2. **limestone_decomposition**: Calcite + H₂SO₄ → Bone Meal + CO₂

#### Metal Processing (2 recipes):
3. **iron_sulfide_roasting**: Raw Iron + O₂ → Iron Ingot + SO₂
4. **copper_oxide_reduction**: Raw Copper + H₂ → Copper Ingot + Water

#### Organic Reactions (2 recipes):
5. **sugar_fermentation**: Sugar + Water → Ethene
6. **biomass_gasification**: Oak Leaves + O₂ → Methane + Charcoal

#### Ammonia Production (2 recipes):
7. **haber_process**: N₂ + Water (catalyst) → NH₃
8. **urea_synthesis**: Bone Meal + NH₃ → White Dye + CO₂

#### Acid-Base Reactions (2 recipes):
9. **sulfuric_acid_neutralization**: Iron + H₂SO₄ → Raw Iron + H₂
10. **hydrochloric_acid_limestone**: Diorite + HCl → Quartz + CO₂

#### Oxidation Reactions (2 recipes):
11. **sulfur_oxidation**: Gunpowder + O₂ → SO₂ + Charcoal
12. **phosphorus_oxidation**: Bone Block + O₂ → Bone Meal + CO₂

#### Reduction Reactions (2 recipes):
13. **carbon_reduction**: Charcoal + CO₂ → CO (Boudouard reaction)
14. **hydrogen_reduction**: Blaze Powder + H₂ → Glowstone Dust + Water Vapor

#### Polymerization (1 recipe):
15. **plastic_polymerization**: Ethene + Water → HDPE Sheet

#### Halogenation (2 recipes):
16. **chlorination_reaction**: Paper + Cl₂ → White Dye
17. **fluorination_reaction**: Glass + HF → Glass Panes

#### Special Reactions (3 recipes):
18. **nether_star_synthesis**: Diamond + Xenon + Lava → Nether Star
19. **ender_pearl_reaction**: Chorus Fruit + Radon → Ender Pearl
20. **glowstone_synthesis**: Redstone + Helium → Glowstone Dust

### Key Features:
- Complex multi-input reactions utilizing PRC's versatility
- Balanced energy requirements (500-10000 J)
- Realistic reaction times (50-1000 ticks)
- Mix of practical and exotic reactions

### Important Format Requirements:
- **fluid_input is REQUIRED** - even if minimal (use 1mB water as catalyst placeholder)
- Fluid inputs use `tag` field for vanilla fluids (e.g., `"tag": "minecraft:water"`)
- Fluid outputs use `fluid` field with full ID (e.g., `"fluid": "minecraft:water"`)
- Chemical outputs use `id` field (not `chemical`)