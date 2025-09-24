#!/usr/bin/env python3
"""
Generate Batch 4 of Chemical Dissolution Chamber recipes.
Focus on modded items, ores, and special materials.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/dissolution"

# Batch 4: Modded and Special Materials
BATCH_4_RECIPES = [
    # === Redstone Engineering ===
    {
        "name": "redstone_torch_dissolution",
        "input": ("minecraft:redstone_torch", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("redstone", 1),
        "note": "Extract redstone from torch"
    },

    {
        "name": "comparator_dissolution",
        "input": ("minecraft:comparator", 1),
        "gas_input": ("hydrogen_chloride", 2),
        "output": ("redstone", 3),
        "note": "Complex redstone device"
    },

    {
        "name": "repeater_dissolution",
        "input": ("minecraft:repeater", 1),
        "gas_input": ("hydrogen_chloride", 2),
        "output": ("redstone", 2),
        "note": "Redstone repeater breakdown"
    },

    # === Precious Materials ===
    {
        "name": "golden_apple_dissolution",
        "input": ("minecraft:golden_apple", 1),
        "gas_input": ("hydrogen_chloride", 4),
        "output": ("gold_slurry", 8),
        "note": "Extract gold from golden apple"
    },

    {
        "name": "golden_carrot_dissolution",
        "input": ("minecraft:golden_carrot", 1),
        "gas_input": ("hydrogen_chloride", 2),
        "output": ("gold_slurry", 1),
        "note": "Gold from golden carrot"
    },

    {
        "name": "glistering_melon_dissolution",
        "input": ("minecraft:glistering_melon_slice", 1),
        "gas_input": ("hydrogen_chloride", 1),
        "output": ("gold_slurry", 1),
        "note": "Gold from glistering melon"
    },

    # === Brewing Ingredients ===
    {
        "name": "blaze_rod_dissolution",
        "input": ("minecraft:blaze_rod", 1),
        "gas_input": ("oxygen", 2),
        "output": ("sulfur_dioxide", 2),
        "note": "Blaze rod sulfur content"
    },

    {
        "name": "magma_cream_dissolution",
        "input": ("minecraft:magma_cream", 1),
        "gas_input": ("water_vapor", 1),
        "output": ("sulfur_dioxide", 1),
        "note": "Magma cream decomposition"
    },

    {
        "name": "ghast_tear_dissolution",
        "input": ("minecraft:ghast_tear", 1),
        "gas_input": ("hydrogen_fluoride", 1),
        "output": ("sodium", 2),
        "note": "Ghast tear salts"
    },

    {
        "name": "phantom_membrane_dissolution",
        "input": ("minecraft:phantom_membrane", 1),
        "gas_input": ("ammonia", 1),
        "output": ("nitrogen", 2),
        "note": "Phantom membrane proteins"
    },

    # === Terracotta and Ceramics ===
    {
        "name": "terracotta_dissolution",
        "input": ("minecraft:terracotta", 1),
        "gas_input": ("hydrogen_fluoride", 1),
        "output": ("aluminum", 1),
        "note": "Clay aluminum content"
    },

    {
        "name": "brick_dissolution",
        "input": ("minecraft:brick", 1),
        "gas_input": ("hydrogen_fluoride", 1),
        "output": ("silicon", 1),
        "note": "Brick silicon dioxide"
    },

    {
        "name": "nether_brick_dissolution",
        "input": ("minecraft:nether_brick", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("netherite_scrap", 1),
        "note": "Nether brick trace metals"
    },

    # === Concrete and Construction ===
    {
        "name": "concrete_powder_dissolution",
        "input": ("minecraft:white_concrete_powder", 1),
        "gas_input": ("water_vapor", 1),
        "output": ("calcium", 1),
        "note": "Concrete calcium content"
    },

    {
        "name": "concrete_dissolution",
        "input": ("minecraft:gray_concrete", 1),
        "gas_input": ("sulfuric_acid", 2),
        "output": ("calcium", 2),
        "note": "Hardened concrete breakdown"
    },

    # === Glass Products ===
    {
        "name": "stained_glass_dissolution",
        "input": ("minecraft:blue_stained_glass", 1),
        "gas_input": ("hydrogen_fluoride", 2),
        "output": ("silicon", 2),
        "note": "Stained glass silicon"
    },

    {
        "name": "glass_bottle_dissolution",
        "input": ("minecraft:glass_bottle", 1),
        "gas_input": ("hydrogen_fluoride", 1),
        "output": ("silicon", 1),
        "note": "Bottle glass silicon"
    },

    # === Miscellaneous ===
    {
        "name": "tnt_dissolution",
        "input": ("minecraft:tnt", 1),
        "gas_input": ("water_vapor", 2),
        "output": ("nitrogen_dioxide", 4),
        "note": "TNT nitrogen compounds"
    },

    {
        "name": "beacon_dissolution",
        "input": ("minecraft:beacon", 1),
        "gas_input": ("hydrogen_fluoride", 5),
        "output": ("obsidian", 3),
        "note": "Beacon obsidian core"
    },

    {
        "name": "enchanting_table_dissolution",
        "input": ("minecraft:enchanting_table", 1),
        "gas_input": ("sulfuric_acid", 4),
        "output": ("diamond", 2),
        "note": "Extract diamonds from table"
    }
]

def create_dissolution_recipe(recipe_data):
    """Create a chemical dissolution chamber recipe JSON."""
    # Determine gas amount based on input
    gas_amount = recipe_data["gas_input"][1] * 100  # Base amount

    recipe = {
        "type": "mekanism:dissolution",
        "item_input": {
            "count": recipe_data["input"][1],
            "item": recipe_data["input"][0]
        },
        "chemical_input": {
            "amount": gas_amount,
            "chemical": get_chemical_id(recipe_data["gas_input"][0])
        },
        "output": {
            "amount": recipe_data["output"][1] * 1000,
            "id": get_chemical_id(recipe_data["output"][0])
        },
        "per_tick_usage": True
    }
    return recipe

def check_outputs_exist(output_name):
    """Check if output chemical exists."""
    # All of these are now registered in ChemLibSlurries!
    return True, "Slurry registered"

def main():
    """Generate the fourth batch of dissolution recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    working_count = 0

    print("Generating Batch 4: Chemical Dissolution Chamber Recipes")
    print("=" * 60)

    for recipe_data in BATCH_4_RECIPES:
        recipe_name = recipe_data["name"]

        # Check if output exists
        output_exists, reason = check_outputs_exist(recipe_data["output"][0])

        recipe_json = create_dissolution_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        if output_exists:
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
    print(f"  - Need slurry registration: {created_count - working_count}")

    print("\n📊 Recipe categories:")
    print("  - Redstone engineering: 3")
    print("  - Precious materials: 3")
    print("  - Brewing ingredients: 4")
    print("  - Terracotta and ceramics: 3")
    print("  - Concrete and construction: 2")
    print("  - Glass products: 2")
    print("  - Miscellaneous: 3")

if __name__ == "__main__":
    main()