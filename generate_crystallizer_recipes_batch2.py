#!/usr/bin/env python3
"""
Generate Batch 2 of Chemical Crystallizer recipes.
Focus on more complex chemical conversions and alternative outputs.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/crystallizing"

# Batch 2: Advanced Chemical Conversions
BATCH_2_RECIPES = [
    # === Alternative Carbon Outputs ===
    {
        "name": "carbon_to_ink_sac",
        "chemical_type": "infusion",
        "input": ("carbon", 8),
        "output": "minecraft:ink_sac",
        "note": "Carbon black to ink"
    },

    {
        "name": "carbon_to_gray_dye",
        "chemical_type": "infusion",
        "input": ("carbon", 2),
        "output": "minecraft:gray_dye",
        "note": "Carbon to gray pigment"
    },

    {
        "name": "carbon_to_light_gray_dye",
        "chemical_type": "infusion",
        "input": ("carbon", 1),
        "output": "minecraft:light_gray_dye",
        "note": "Diluted carbon pigment"
    },

    {
        "name": "large_carbon_to_obsidian",
        "chemical_type": "infusion",
        "input": ("carbon", 128),
        "output": "minecraft:obsidian",
        "note": "Vitrified carbon"
    },

    {
        "name": "carbon_to_flint",
        "chemical_type": "infusion",
        "input": ("carbon", 32),
        "output": "minecraft:flint",
        "note": "Compressed carbon to flint"
    },

    # === Compound Gases to Dyes ===
    {
        "name": "carbon_dioxide_to_lime_dye",
        "chemical_type": "gas",
        "input": ("carbon_dioxide", 2),
        "output": "minecraft:lime_dye",
        "note": "CO2 plant growth indicator"
    },

    {
        "name": "nitric_oxide_to_red_dye",
        "chemical_type": "gas",
        "input": ("nitric_oxide", 2),
        "output": "minecraft:red_dye",
        "note": "NO oxidation color"
    },

    {
        "name": "carbon_monoxide_to_light_gray_concrete",
        "chemical_type": "gas",
        "input": ("carbon_monoxide", 8),
        "output": "minecraft:light_gray_concrete_powder",
        "note": "Industrial byproduct"
    },

    # === Noble Gas Alternatives ===

    {
        "name": "radon_to_spider_eye",
        "chemical_type": "gas",
        "input": ("radon", 2),
        "output": "minecraft:spider_eye",
        "note": "Radioactive mutation"
    },

    {
        "name": "xenon_to_sea_lantern",
        "chemical_type": "gas",
        "input": ("xenon", 8),
        "output": "minecraft:sea_lantern",
        "note": "Xenon lamp technology"
    },

    # === Carbon Compounds ===
    {
        "name": "carbon_dioxide_to_slime_ball",
        "chemical_type": "gas",
        "input": ("carbon_dioxide", 16),
        "output": "minecraft:slime_ball",
        "note": "CO2 polymer gel"
    },

    {
        "name": "hydrogen_sulfide_to_yellow_dye",
        "chemical_type": "gas",
        "input": ("hydrogen_sulfide", 1),
        "output": "minecraft:yellow_dye",
        "note": "Sulfur yellow"
    },

    # === Nitrogen Compounds ===
    {
        "name": "nitrogen_to_white_dye",
        "chemical_type": "gas",
        "input": ("nitrogen", 2),
        "output": "minecraft:white_dye",
        "note": "Inert white pigment"
    },

    {
        "name": "ammonia_to_green_dye",
        "chemical_type": "gas",
        "input": ("ammonia", 1),
        "output": "minecraft:green_dye",
        "note": "Plant fertilizer green"
    },

    # === Chlorine/Fluorine Compounds ===
    {
        "name": "chlorine_to_cyan_dye",
        "chemical_type": "gas",
        "input": ("chlorine", 1),
        "output": "minecraft:cyan_dye",
        "note": "Pool water color"
    },

    {
        "name": "fluorine_to_magenta_dye",
        "chemical_type": "gas",
        "input": ("fluorine", 1),
        "output": "minecraft:magenta_dye",
        "note": "Fluorescent color"
    },

    # === Special Conversions ===
    {
        "name": "methane_to_brown_dye",
        "chemical_type": "gas",
        "input": ("methane", 1),
        "output": "minecraft:brown_dye",
        "note": "Natural gas residue"
    },


    {
        "name": "large_nitrogen_to_blue_ice",
        "chemical_type": "gas",
        "input": ("nitrogen", 16),
        "output": "minecraft:blue_ice",
        "note": "Ultra-cold nitrogen"
    }
]

def create_crystallizer_recipe(recipe_data):
    """Create a chemical crystallizer recipe JSON."""
    recipe = {
        "type": "mekanism:crystallizing",
        "chemical_type": recipe_data["chemical_type"],
        "input": {
            "amount": recipe_data["input"][1] * 1000,
            "chemical": get_chemical_id(recipe_data["input"][0])
        },
        "output": {
            "id": recipe_data["output"]
        }
    }

    # Add count if specified
    if "count" in recipe_data:
        recipe["output"]["count"] = recipe_data["count"]

    return recipe

def check_chemical_exists(chemical_name, chemical_type):
    """Check if input chemical is registered."""
    if chemical_type == "infusion":
        if chemical_name in ["carbon", "redstone"]:
            return True, "Mekanism infusion"
    elif chemical_type == "gas":
        # Check our registered gases
        if chemical_name in ["hydrogen", "oxygen", "nitrogen", "chlorine", "fluorine",
                             "xenon", "krypton", "argon", "neon", "helium", "radon",
                             "ammonia", "methane", "hydrogen_sulfide", "carbon_monoxide",
                             "nitric_oxide", "carbon_dioxide"]:
            return True, "Registered gas"

    return False, f"Chemical {chemical_name} may not be registered"

def main():
    """Generate the second batch of crystallizer recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    working_count = 0

    print("Generating Batch 2: Advanced Chemical Crystallizer Recipes")
    print("=" * 60)

    for recipe_data in BATCH_2_RECIPES:
        recipe_name = recipe_data["name"]
        chemical_name = recipe_data["input"][0]
        chemical_type = recipe_data["chemical_type"]

        # Check if chemical exists
        exists, reason = check_chemical_exists(chemical_name, chemical_type)

        recipe_json = create_crystallizer_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        if exists:
            print(f"  ✅ Created {recipe_name}.json")
            working_count += 1
        else:
            print(f"  ⚠️  Created {recipe_name}.json ({reason})")

        if "note" in recipe_data:
            print(f"     Note: {recipe_data['note']}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")
    print(f"  - Should work: {working_count}")

    # List recipes by category
    print("\n📊 Recipe categories:")
    print("  - Alternative carbon outputs: 5")
    print("  - Gas to dye conversions: 10")
    print("  - Noble gas specialties: 2")
    print("  - Other conversions: 1")

if __name__ == "__main__":
    main()