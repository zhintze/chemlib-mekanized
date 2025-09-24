#!/usr/bin/env python3
"""
Generate scientifically accurate gas combination recipes for Chemical Infuser.
"""

import json
import os
from chemical_mappings import get_chemical_id

RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/chemical_infusing"

# Scientifically accurate gas combinations
ACCURATE_RECIPES = [
    # Steam Reforming of Methane (industrial hydrogen production)
    {
        "name": "steam_reforming",
        "left": ("methane", 1),
        "right": ("steam", 1),  # H2O vapor at high temp
        "output": ("carbon_monoxide", 1),
        "output_amount": 1,
        "byproduct": ("hydrogen", 3)  # Would need multiple outputs
    },

    # Water Gas Shift Reaction
    {
        "name": "water_gas_shift",
        "left": ("carbon_monoxide", 1),
        "right": ("water_vapor", 1),
        "output": ("carbon_dioxide", 1),
        "output_amount": 1,
        "byproduct": ("hydrogen", 1)
    },

    # Partial Oxidation of Methane
    {
        "name": "methane_partial_oxidation",
        "left": ("methane", 2),
        "right": ("oxygen", 1),
        "output": ("carbon_monoxide", 2)
    },

    # Dry Reforming of Methane
    {
        "name": "dry_reforming",
        "left": ("methane", 1),
        "right": ("carbon_dioxide", 1),
        "output": ("carbon_monoxide", 2)
    },

    # Ostwald Process Step 1 (Ammonia Oxidation)
    {
        "name": "ammonia_oxidation",
        "left": ("ammonia", 4),
        "right": ("oxygen", 5),
        "output": ("nitric_oxide", 4)
    },

    # Hydrogen Sulfide Oxidation
    {
        "name": "hydrogen_sulfide_oxidation",
        "left": ("hydrogen_sulfide", 2),
        "right": ("oxygen", 3),
        "output": ("sulfur_dioxide", 2)
    },

    # Claus Process (H2S + SO2 -> Sulfur)
    # Note: We'd need elemental sulfur as output
    {
        "name": "claus_process",
        "left": ("hydrogen_sulfide", 2),
        "right": ("sulfur_dioxide", 1),
        "output": ("water_vapor", 2)  # Plus sulfur solid
    },

    # Complete Combustion of Methane
    {
        "name": "methane_combustion",
        "left": ("methane", 1),
        "right": ("oxygen", 2),
        "output": ("carbon_dioxide", 1),
        "output_amount": 1,
        "byproduct": ("water_vapor", 2)
    },

    # Hydrogen Peroxide Formation (simplified)
    {
        "name": "hydrogen_peroxide_formation",
        "left": ("hydrogen", 1),
        "right": ("oxygen", 1),
        "output": ("hydrogen_peroxide", 1)  # If we have it
    }
]

def create_simple_recipe(recipe_data):
    """Create a chemical infuser recipe JSON for single-output reactions."""
    recipe = {
        "type": "mekanism:chemical_infusing",
        "left_input": {
            "amount": recipe_data["left"][1] * 1000,
            "chemical": get_chemical_id(recipe_data["left"][0])
        },
        "right_input": {
            "amount": recipe_data["right"][1] * 1000,
            "chemical": get_chemical_id(recipe_data["right"][0])
        },
        "output": {
            "amount": recipe_data.get("output_amount", recipe_data["output"][1]) * 1000,
            "id": get_chemical_id(recipe_data["output"][0] if isinstance(recipe_data["output"], tuple) else recipe_data["output"])
        }
    }
    return recipe

def main():
    """Generate scientifically accurate replacement recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    # Only create recipes that work with single output
    # Skip reactions that need multiple outputs
    single_output_recipes = [
        {
            "name": "methane_partial_oxidation",
            "left": ("methane", 2),
            "right": ("oxygen", 1),
            "output": ("carbon_monoxide", 2)
        },
        {
            "name": "dry_reforming",
            "left": ("methane", 1),
            "right": ("carbon_dioxide", 1),
            "output": ("carbon_monoxide", 2)
        },
        {
            "name": "ammonia_oxidation",
            "left": ("ammonia", 4),
            "right": ("oxygen", 5),
            "output": ("nitric_oxide", 4)
        },
        {
            "name": "hydrogen_sulfide_oxidation",
            "left": ("hydrogen_sulfide", 2),
            "right": ("oxygen", 3),
            "output": ("sulfur_dioxide", 2)
        }
    ]

    print("Generating Scientifically Accurate Gas Combination Recipes")
    print("=" * 60)

    for recipe_data in single_output_recipes:
        recipe_json = create_simple_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_data['name']}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ Created {recipe_data['name']}.json")
        print(f"     {recipe_data['left'][1]}{recipe_data['left'][0]} + {recipe_data['right'][1]}{recipe_data['right'][0]} -> {recipe_data['output'][1]}{recipe_data['output'][0]}")

    print("\n⚠️  Note: Many real reactions produce multiple products.")
    print("   Chemical Infuser only supports single output.")
    print("   Consider using Pressurized Reaction Chamber for multi-output reactions.")

if __name__ == "__main__":
    main()