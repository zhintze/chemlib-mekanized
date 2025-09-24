# Rotary Condensentrator Recipes Implementation

## Overview
This document tracks the implementation of Rotary Condensentrator recipes for ChemLibMekanized.
The Rotary Condensentrator converts between gas and liquid (fluid) forms of chemicals.

## Recipe Formats

### Condensentrating (Gas → Liquid)
```json
{
  "type": "mekanism:rotary",
  "gas_input": {
    "amount": 1,
    "chemical": "mekanism:water_vapor"
  },
  "fluid_output": {
    "amount": 1,
    "id": "minecraft:water"
  }
}
```

### Decondensentrating (Liquid → Gas)
```json
{
  "type": "mekanism:rotary",
  "fluid_input": {
    "amount": 1,
    "id": "minecraft:water"
  },
  "gas_output": {
    "amount": 1,
    "id": "mekanism:water_vapor"
  }
}
```

### Bidirectional (Both ways in one recipe)
```json
{
  "type": "mekanism:rotary",
  "gas_input": {
    "amount": 1,
    "chemical": "mekanism:water_vapor"
  },
  "fluid_input": {
    "amount": 1,
    "id": "minecraft:water"
  },
  "gas_output": {
    "amount": 1,
    "id": "mekanism:water_vapor"
  },
  "fluid_output": {
    "amount": 1,
    "id": "minecraft:water"
  }
}
```

## Implementation Strategy
1. Create gas ↔ liquid conversions for chemicals with both states
2. Focus on scientifically accurate phase transitions
3. Support mod fluid integration where available
4. Balance conversion ratios for gameplay

## Batch 1: Limited Vanilla Fluid Conversions (✅ Complete - 2 recipes)
*Generated: 2024-12-23*

### Implemented Recipes (2 total - both working):
1. **water_electrolysis**: Water → Hydrogen gas
2. **lava_sulfur_extraction**: Lava → Sulfur Dioxide gas

### Key Implementation Notes:
- Fixed recipe format to use `chemical_output` instead of `gas_output`
- Limited to vanilla fluids due to liquid chemical registration pending
- Full implementation awaits FluidRegistry for liquid forms of gases

## Future Implementation Required
To enable full gas ↔ liquid conversions, we need to:
1. Register liquid forms of chemicals in FluidRegistry
2. Create fluid textures and properties
3. Add bidirectional rotary recipes for each gas/liquid pair

Potential liquid chemicals to register:
- liquid_oxygen, liquid_nitrogen, liquid_hydrogen
- liquid_chlorine, liquid_fluorine
- liquid_ammonia, liquid_methane, liquid_carbon_dioxide
- liquid_noble_gases (He, Ne, Ar, Kr, Xe, Rn)
- liquid_acids (H2SO4, HF, HCl)