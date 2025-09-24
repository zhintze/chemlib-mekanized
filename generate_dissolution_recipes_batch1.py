#!/usr/bin/env python3
"""
Generate Batch 1 of Chemical Dissolution Chamber recipes.
Focus on vanilla Minecraft items being dissolved into chemical components.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/dissolution"

# Batch 1: Common Minecraft Materials
BATCH_1_RECIPES = [
    # === Carbon-based materials ===
    {
        "name": "coal_dissolution",
        "input": ("minecraft:coal", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 16),  # More generous output
        "note": "Coal to carbon"
    },

    {
        "name": "charcoal_dissolution",
        "input": ("minecraft:charcoal", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 16),  # More generous output
        "note": "Charcoal to carbon"
    },

    {
        "name": "coal_block_dissolution",
        "input": ("minecraft:coal_block", 1),
        "gas_input": ("sulfuric_acid", 5),
        "output": ("carbon", 144),  # 9x coal = 9 * 16
        "note": "Coal block to carbon (9x coal)"
    },

    # === Precious materials ===
    {
        "name": "diamond_dissolution",
        "input": ("minecraft:diamond", 1),
        "gas_input": ("hydrofluoric_acid", 0.1),  # Extremely small amount
        "output": ("carbon", 256),  # Much more carbon output
        "note": "Diamond is pure carbon - catalytic HF"
    },

    {
        "name": "diamond_block_dissolution",
        "input": ("minecraft:diamond_block", 1),
        "gas_input": ("hydrofluoric_acid", 0.5),  # Very small amount for block
        "output": ("carbon", 2304),  # 9x diamond = 9 * 256
        "note": "Diamond block (9x diamond) - catalytic HF"
    },


    # === Redstone ===
    {
        "name": "redstone_dissolution",
        "input": ("minecraft:redstone", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("redstone", 1),
        "note": "Redstone dust to infusion"
    },

    {
        "name": "redstone_block_dissolution",
        "input": ("minecraft:redstone_block", 1),
        "gas_input": ("sulfuric_acid", 5),
        "output": ("redstone", 9),
        "note": "Redstone block dissolution"
    },

    # === Quartz ===
    {
        "name": "quartz_dissolution",
        "input": ("minecraft:quartz", 1),
        "gas_input": ("hydrofluoric_acid", 1),
        "output": ("silicon", 1),
        "note": "Quartz (SiO2) to silicon"
    },

    {
        "name": "quartz_block_dissolution",
        "input": ("minecraft:quartz_block", 1),
        "gas_input": ("hydrofluoric_acid", 4),
        "output": ("silicon", 4),
        "note": "Quartz block to silicon"
    },

    # === Sand/Glass (silicon dioxide) ===
    {
        "name": "sand_dissolution",
        "input": ("minecraft:sand", 1),
        "gas_input": ("hydrofluoric_acid", 1),
        "output": ("silicon", 1),
        "note": "Sand (SiO2) to silicon"
    },

    {
        "name": "glass_dissolution",
        "input": ("minecraft:glass", 1),
        "gas_input": ("hydrofluoric_acid", 1),
        "output": ("silicon", 1),
        "note": "Glass to silicon"
    },

    # === Organic materials ===
    {
        "name": "sugar_dissolution",
        "input": ("minecraft:sugar", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 12),
        "note": "Sugar (C12H22O11) decomposition"
    },

    {
        "name": "bone_dissolution",
        "input": ("minecraft:bone", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("calcium", 3),
        "note": "Bone to calcium phosphate"
    },

    {
        "name": "bone_meal_dissolution",
        "input": ("minecraft:bone_meal", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("calcium", 1),
        "note": "Bone meal to calcium"
    }
]

def create_dissolution_recipe(recipe_data):
    """Create a chemical dissolution recipe JSON."""
    recipe = {
        "type": "mekanism:dissolution",
        "item_input": {
            "count": recipe_data["input"][1],
            "item": recipe_data["input"][0]
        },
        "chemical_input": {
            "amount": recipe_data["gas_input"][1] * 1000,
            "chemical": get_chemical_id(recipe_data["gas_input"][0])
        },
        "output": {
            "amount": recipe_data["output"][1] * 1000,
            "id": get_chemical_id(recipe_data["output"][0])
        },
        "per_tick_usage": True
    }
    return recipe

def main():
    """Generate the first batch of dissolution recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    skipped_count = 0

    print("Generating Batch 1: Chemical Dissolution Chamber Recipes")
    print("=" * 60)

    for recipe_data in BATCH_1_RECIPES:
        recipe_name = recipe_data["name"]

        # Check if we're using chemicals that exist
        gas_input = recipe_data["gas_input"][0]
        output = recipe_data["output"][0]

        # These should all exist in Mekanism
        if gas_input not in ["sulfuric_acid", "hydrofluoric_acid"]:
            print(f"  ⚠️  Warning: {recipe_name} uses unregistered gas {gas_input}")

        recipe_json = create_dissolution_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ Created {recipe_name}.json")
        if "note" in recipe_data:
            print(f"     Note: {recipe_data['note']}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")

    print("\n📝 Notes:")
    print("   - Uses sulfuric acid for most dissolutions")
    print("   - Uses hydrofluoric acid for harder materials (diamond, quartz)")
    print("   - Outputs are simplified for gameplay balance")

if __name__ == "__main__":
    main()