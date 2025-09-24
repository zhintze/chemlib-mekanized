#!/usr/bin/env python3
"""
Generate Batch 1 of Rotary Condensentrator recipes.
Limited to conversions with existing fluids since we haven't registered liquid forms yet.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/rotary"

# Batch 1: Limited conversions with existing fluids
# Most gas liquefaction requires registering new fluids first
BATCH_1_RECIPES = [
    # === Vanilla Fluid → Gas Conversions ===
    {
        "name": "water_electrolysis",
        "fluid_input": "minecraft:water",
        "gas_output": ("hydrogen", 1000),  # Water → Hydrogen (simplified)
        "note": "Water electrolysis for hydrogen"
    },

    {
        "name": "lava_sulfur_extraction",
        "fluid_input": "minecraft:lava",
        "gas_output": ("sulfur_dioxide", 500),  # Lava contains sulfur
        "note": "Extract volcanic gases from lava"
    },

    # Note: Most recipes require liquid chemical registration
    # Future additions would include:
    # - liquid_oxygen ↔ oxygen gas
    # - liquid_nitrogen ↔ nitrogen gas
    # - liquid_hydrogen ↔ hydrogen gas
    # - liquid_chlorine ↔ chlorine gas
    # - liquid_ammonia ↔ ammonia gas
    # - And many more...
]

def create_rotary_recipe(recipe_data):
    """Create a rotary condensentrator recipe JSON."""
    recipe = {
        "type": "mekanism:rotary"
    }

    if "fluid_input" in recipe_data:
        # Fluid to gas (decondensentrating)
        recipe["fluid_input"] = {
            "amount": 1000,  # 1 bucket
            "id": recipe_data["fluid_input"]
        }
        recipe["chemical_output"] = {
            "amount": recipe_data["gas_output"][1],
            "id": get_chemical_id(recipe_data["gas_output"][0])
        }
    elif "gas_input" in recipe_data:
        # Gas to fluid (condensentrating)
        recipe["chemical_input"] = {
            "amount": recipe_data["gas_input"][1],
            "chemical": get_chemical_id(recipe_data["gas_input"][0])
        }
        recipe["fluid_output"] = {
            "amount": 1000,  # 1 bucket
            "id": recipe_data["fluid_output"]
        }

    return recipe

def main():
    """Generate the first batch of rotary condensentrator recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0

    print("Generating Batch 1: Rotary Condensentrator Recipes (Limited)")
    print("=" * 60)
    print("⚠️  Note: Most rotary recipes require liquid chemical registration")
    print("  Currently only implementing conversions with existing fluids")
    print()

    for recipe_data in BATCH_1_RECIPES:
        recipe_name = recipe_data["name"]

        recipe_json = create_rotary_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ Created {recipe_name}.json")
        if "note" in recipe_data:
            print(f"     Note: {recipe_data['note']}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")

    print("\n📝 Future Implementation Required:")
    print("  To enable gas ↔ liquid conversions, we need to:")
    print("  1. Register liquid forms of chemicals in FluidRegistry")
    print("  2. Create fluid textures and properties")
    print("  3. Add bidirectional rotary recipes for each gas/liquid pair")
    print("\n  Potential liquid chemicals:")
    print("  - liquid_oxygen, liquid_nitrogen, liquid_hydrogen")
    print("  - liquid_chlorine, liquid_fluorine")
    print("  - liquid_ammonia, liquid_methane, liquid_carbon_dioxide")
    print("  - liquid_noble_gases (He, Ne, Ar, Kr, Xe, Rn)")
    print("  - liquid_acids (H2SO4, HF, HCl)")

if __name__ == "__main__":
    main()