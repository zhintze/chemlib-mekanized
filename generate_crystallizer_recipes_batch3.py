#!/usr/bin/env python3
"""
Generate Batch 3 of Chemical Crystallizer recipes.
Focus on more practical item outputs and chemical conversions.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/crystallizing"

# Batch 3: Practical Chemical Conversions (reduced after removals)
BATCH_3_RECIPES = [
    # === Building Materials from Chemicals ===

    {
        "name": "calcium_to_bone_block",
        "chemical_type": "infusion",
        "input": ("carbon", 32),
        "output": "minecraft:bone_block",
        "note": "Calcium phosphate synthesis"
    },

    # === Food and Agriculture ===

    {
        "name": "carbon_dioxide_to_sugar",
        "chemical_type": "gas",
        "input": ("carbon_dioxide", 3),
        "output": "minecraft:sugar",
        "note": "Photosynthesis simulation"
    },

    {
        "name": "ammonia_to_wheat_seeds",
        "chemical_type": "gas",
        "input": ("ammonia", 1),
        "output": "minecraft:wheat_seeds",
        "note": "Ammonia fertilizer"
    },

    # === Mineral Processing ===
    {
        "name": "sulfur_dioxide_to_sulfur",
        "chemical_type": "gas",
        "input": ("sulfur_dioxide", 1),
        "output": "minecraft:glowstone_dust",
        "note": "Sulfur crystallization"
    },

    {
        "name": "carbon_to_netherrack",
        "chemical_type": "infusion",
        "input": ("carbon", 8),
        "output": "minecraft:netherrack",
        "note": "Volcanic rock formation"
    },

    {
        "name": "nitrogen_to_soul_sand",
        "chemical_type": "gas",
        "input": ("nitrogen", 4),
        "output": "minecraft:soul_sand",
        "note": "Soul energy crystallization"
    },

    # === Precious Gems ===

    {
        "name": "carbon_to_lapis",
        "chemical_type": "infusion",
        "input": ("carbon", 12),
        "output": "minecraft:lapis_lazuli",
        "count": 4,
        "note": "Lazurite formation"
    },


    # === Mob Drops ===
    {
        "name": "radon_to_ender_pearl",
        "chemical_type": "gas",
        "input": ("radon", 4),
        "output": "minecraft:ender_pearl",
        "note": "Quantum crystallization"
    },

    {
        "name": "sulfur_trioxide_to_blaze_powder",
        "chemical_type": "gas",
        "input": ("sulfur_trioxide", 2),
        "output": "minecraft:blaze_powder",
        "note": "Blaze essence formation"
    },

    {
        "name": "hydrogen_sulfide_to_rotten_flesh",
        "chemical_type": "gas",
        "input": ("hydrogen_sulfide", 2),
        "output": "minecraft:rotten_flesh",
        "note": "Decay simulation"
    },

    # === Nether Materials ===
    {
        "name": "sulfur_dioxide_to_magma_block",
        "chemical_type": "gas",
        "input": ("sulfur_dioxide", 4),
        "output": "minecraft:magma_block",
        "note": "Volcanic block formation"
    },

    {
        "name": "carbon_monoxide_to_blackstone",
        "chemical_type": "gas",
        "input": ("carbon_monoxide", 4),
        "output": "minecraft:blackstone",
        "note": "Volcanic rock crystallization"
    },

    # === Ocean Materials ===
    {
        "name": "chlorine_to_sea_pickle",
        "chemical_type": "gas",
        "input": ("chlorine", 2),
        "output": "minecraft:sea_pickle",
        "note": "Marine organism synthesis"
    },

    {
        "name": "krypton_to_heart_of_sea",
        "chemical_type": "gas",
        "input": ("krypton", 16),
        "output": "minecraft:heart_of_the_sea",
        "note": "Deep ocean crystallization"
    },

    {
        "name": "argon_to_nautilus_shell",
        "chemical_type": "gas",
        "input": ("argon", 8),
        "output": "minecraft:nautilus_shell",
        "note": "Shell formation"
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
                             "nitric_oxide", "carbon_dioxide", "sulfur_dioxide", "sulfur_trioxide"]:
            return True, "Registered gas"

    return False, f"Chemical {chemical_name} may not be registered"

def main():
    """Generate the third batch of crystallizer recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    working_count = 0

    print("Generating Batch 3: Chemical Crystallizer Recipes")
    print("=" * 60)

    for recipe_data in BATCH_3_RECIPES:
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

    print("\n📊 Recipe categories:")
    print("  - Building materials: 1")
    print("  - Food and agriculture: 2")
    print("  - Mineral processing: 3")
    print("  - Precious gems: 1")
    print("  - Mob drops: 3")
    print("  - Nether materials: 2")
    print("  - Ocean materials: 2")

if __name__ == "__main__":
    main()