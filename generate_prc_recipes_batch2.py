#!/usr/bin/env python3
"""
Generate Batch 2 of Pressurized Reaction Chamber recipes.
Focus on advanced chemical synthesis and material processing.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/reaction"

# Batch 2: Advanced Chemical Synthesis
BATCH_2_RECIPES = [
    # === Advanced Metallurgy ===
    {
        "name": "steel_production",
        "item_input": ("minecraft:iron_ingot", 1),
        "chemical_input": ("carbon_monoxide", 100),
        "fluid_input": ("water", 100),
        "energy": 4000.0,
        "duration": 400,
        "item_output": ("mekanism:ingot_steel", 1),
        "note": "Iron + CO → Steel"
    },

    {
        "name": "gold_purification",
        "item_input": ("minecraft:raw_gold", 2),
        "chemical_input": ("chlorine", 500),
        "fluid_input": ("water", 200),
        "energy": 3000.0,
        "duration": 300,
        "item_output": ("minecraft:gold_ingot", 3),
        "note": "Gold chlorination process"
    },

    # === Gem Enhancement ===
    {
        "name": "diamond_synthesis",
        "item_input": ("minecraft:coal_block", 1),
        "chemical_input": ("oxygen", 100),
        "fluid_input": ("lava", 1000),
        "energy": 20000.0,
        "duration": 2000,
        "item_output": ("minecraft:diamond", 1),
        "note": "Ultra-high pressure synthesis"
    },

    {
        "name": "emerald_growth",
        "item_input": ("minecraft:prismarine_shard", 4),
        "chemical_input": ("xenon", 200),
        "fluid_input": ("water", 500),
        "energy": 8000.0,
        "duration": 800,
        "item_output": ("minecraft:emerald", 1),
        "note": "Hydrothermal emerald growth"
    },

    # === Explosive Synthesis ===
    {
        "name": "tnt_production",
        "item_input": ("minecraft:sand", 4),
        "chemical_input": ("nitrogen_dioxide", 1000),
        "fluid_input": ("water", 100),
        "energy": 2000.0,
        "duration": 200,
        "item_output": ("minecraft:tnt", 1),
        "note": "Nitration of toluene substitute"
    },

    {
        "name": "firework_star_creation",
        "item_input": ("minecraft:gunpowder", 1),
        "chemical_input": ("sulfur_dioxide", 200),
        "fluid_input": ("water", 50),
        "energy": 1000.0,
        "duration": 100,
        "item_output": ("minecraft:firework_star", 1),
        "note": "Pyrotechnic composition"
    },

    # === Biological Synthesis ===
    {
        "name": "slime_ball_synthesis",
        "item_input": ("minecraft:sugar", 2),
        "chemical_input": ("methane", 500),
        "fluid_input": ("water", 200),
        "energy": 1500.0,
        "duration": 150,
        "item_output": ("minecraft:slime_ball", 1),
        "note": "Polymer gel formation"
    },

    {
        "name": "leather_tanning",
        "item_input": ("minecraft:rabbit_hide", 4),
        "chemical_input": ("sulfur_dioxide", 100),
        "fluid_input": ("water", 100),
        "energy": 800.0,
        "duration": 80,
        "item_output": ("minecraft:leather", 1),
        "note": "Chrome tanning process"
    },

    {
        "name": "bone_meal_enrichment",
        "item_input": ("minecraft:bone", 1),
        "chemical_input": ("nitrogen", 200),
        "fluid_input": ("water", 100),
        "energy": 500.0,
        "duration": 50,
        "item_output": ("minecraft:bone_meal", 4),
        "note": "Phosphate enrichment"
    },

    # === Fuel Processing ===
    {
        "name": "coal_liquefaction",
        "item_input": ("minecraft:coal", 1),
        "chemical_input": ("hydrogen", 2000),
        "fluid_input": ("water", 500),
        "energy": 3000.0,
        "duration": 300,
        "fluid_output": ("lava", 100),
        "chemical_output": ("methane", 500),
        "note": "Fischer-Tropsch process"
    },

    {
        "name": "blaze_powder_synthesis",
        "item_input": ("minecraft:magma_cream", 1),
        "chemical_input": ("sulfur_trioxide", 500),
        "fluid_input": ("lava", 100),
        "energy": 2500.0,
        "duration": 250,
        "item_output": ("minecraft:blaze_powder", 2),
        "note": "Blaze essence extraction"
    },

    # === Enchanting Materials ===
    {
        "name": "lapis_enrichment",
        "item_input": ("minecraft:lapis_lazuli", 1),
        "chemical_input": ("nitrogen", 100),
        "fluid_input": ("water", 100),
        "energy": 1500.0,
        "duration": 150,
        "item_output": ("minecraft:lapis_block", 1),
        "note": "Lapis concentration"
    },

    {
        "name": "experience_bottle_synthesis",
        "item_input": ("minecraft:glass_bottle", 1),
        "chemical_input": ("xenon", 100),
        "fluid_input": ("water", 100),
        "energy": 5000.0,
        "duration": 500,
        "item_output": ("minecraft:experience_bottle", 1),
        "note": "XP essence capture"
    },

    # === Construction Materials ===
    {
        "name": "obsidian_formation",
        "item_input": ("minecraft:magma_block", 1),
        "chemical_input": ("water_vapor", 1000),
        "fluid_input": ("water", 1000),
        "energy": 4000.0,
        "duration": 400,
        "item_output": ("minecraft:obsidian", 1),
        "note": "Rapid cooling process"
    },

    {
        "name": "prismarine_synthesis",
        "item_input": ("minecraft:prismarine_shard", 4),
        "chemical_input": ("chlorine", 200),
        "fluid_input": ("water", 500),
        "energy": 2000.0,
        "duration": 200,
        "item_output": ("minecraft:prismarine", 1),
        "note": "Marine crystal formation"
    },

    {
        "name": "end_stone_creation",
        "item_input": ("minecraft:chorus_fruit", 1),
        "chemical_input": ("radon", 200),
        "fluid_input": ("water", 100),
        "energy": 3000.0,
        "duration": 300,
        "item_output": ("minecraft:end_stone", 1),
        "note": "Dimensional stone synthesis"
    },

    # === Redstone Technology ===
    {
        "name": "redstone_block_compression",
        "item_input": ("minecraft:redstone", 9),
        "chemical_input": ("nitrogen", 100),
        "fluid_input": ("water", 10),
        "energy": 1000.0,
        "duration": 100,
        "item_output": ("minecraft:redstone_block", 1),
        "note": "High-pressure compression"
    },

    {
        "name": "comparator_assembly",
        "item_input": ("minecraft:quartz", 1),
        "chemical_input": ("redstone", 3000),
        "fluid_input": ("water", 100),
        "energy": 2000.0,
        "duration": 200,
        "item_output": ("minecraft:comparator", 1),
        "note": "Redstone circuit assembly"
    },

    # === Special Items ===
    {
        "name": "totem_restoration",
        "item_input": ("minecraft:gold_block", 1),
        "chemical_input": ("helium", 1000),
        "fluid_input": ("water", 500),
        "energy": 15000.0,
        "duration": 1500,
        "item_output": ("minecraft:totem_of_undying", 1),
        "note": "Life force infusion"
    }
]

def create_prc_recipe(recipe_data):
    """Create a Pressurized Reaction Chamber recipe JSON."""
    recipe = {
        "type": "mekanism:reaction"
    }

    # Add item input if present
    if "item_input" in recipe_data:
        recipe["item_input"] = {
            "count": recipe_data["item_input"][1],
            "item": recipe_data["item_input"][0]
        }

    # Add fluid input - REQUIRED even if minimal
    if "fluid_input" in recipe_data:
        recipe["fluid_input"] = {
            "amount": recipe_data["fluid_input"][1],
            "tag": f"minecraft:{recipe_data['fluid_input'][0]}"  # Use tag for vanilla fluids
        }
    else:
        # PRC requires fluid_input
        recipe["fluid_input"] = {
            "amount": 1,
            "tag": "minecraft:water"
        }

    # Add chemical input if present
    if "chemical_input" in recipe_data:
        recipe["chemical_input"] = {
            "amount": recipe_data["chemical_input"][1],
            "chemical": get_chemical_id(recipe_data["chemical_input"][0])
        }

    # Add energy and duration (required)
    recipe["energy_required"] = recipe_data["energy"]
    recipe["duration"] = recipe_data["duration"]

    # Add item output if present
    if "item_output" in recipe_data:
        recipe["item_output"] = {
            "id": recipe_data["item_output"][0]
        }
        if recipe_data["item_output"][1] > 1:
            recipe["item_output"]["count"] = recipe_data["item_output"][1]

    # Add fluid output if present
    if "fluid_output" in recipe_data:
        recipe["fluid_output"] = {
            "amount": recipe_data["fluid_output"][1],
            "fluid": f"minecraft:{recipe_data['fluid_output'][0]}"
        }

    # Add chemical output if present
    if "chemical_output" in recipe_data:
        recipe["chemical_output"] = {
            "amount": recipe_data["chemical_output"][1],
            "id": get_chemical_id(recipe_data["chemical_output"][0])
        }

    return recipe

def main():
    """Generate the second batch of PRC recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0

    print("Generating Batch 2: Pressurized Reaction Chamber Recipes")
    print("=" * 60)

    for recipe_data in BATCH_2_RECIPES:
        recipe_name = recipe_data["name"]

        recipe_json = create_prc_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ Created {recipe_name}.json")
        if "note" in recipe_data:
            print(f"     Note: {recipe_data['note']}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")

    print("\n📊 Recipe categories:")
    print("  - Advanced metallurgy: 2")
    print("  - Gem enhancement: 2")
    print("  - Explosive synthesis: 2")
    print("  - Biological synthesis: 3")
    print("  - Fuel processing: 2")
    print("  - Enchanting materials: 2")
    print("  - Construction materials: 3")
    print("  - Redstone technology: 2")
    print("  - Special items: 1")

if __name__ == "__main__":
    main()