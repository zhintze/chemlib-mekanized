#!/usr/bin/env python3
"""
Generate Batch 2 of Chemical Dissolution Chamber recipes.
Focus on Nether/End materials and complex organic items.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/dissolution"

# Batch 2: Nether, End, and Complex Items
BATCH_2_RECIPES = [
    # === Nether Materials ===
    {
        "name": "netherrack_dissolution",
        "input": ("minecraft:netherrack", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("sulfur", 2),  # Netherrack contains sulfur
        "note": "Netherrack to sulfur"
    },

    {
        "name": "soul_sand_dissolution",
        "input": ("minecraft:soul_sand", 1),
        "gas_input": ("sulfuric_acid", 2),
        "output": ("carbon", 4),  # Souls = organic carbon
        "note": "Soul sand to carbon (organic remains)"
    },

    {
        "name": "glowstone_dust_dissolution",
        "input": ("minecraft:glowstone_dust", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("phosphorus", 2),  # Phosphorescent material
        "note": "Glowstone to phosphorus"
    },

    {
        "name": "glowstone_dissolution",
        "input": ("minecraft:glowstone", 1),
        "gas_input": ("sulfuric_acid", 4),
        "output": ("phosphorus", 8),  # 4x dust
        "note": "Glowstone block to phosphorus"
    },

    {
        "name": "blaze_powder_dissolution",
        "input": ("minecraft:blaze_powder", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("sulfur", 3),  # Blaze = sulfur + fire
        "note": "Blaze powder to sulfur"
    },

    {
        "name": "magma_cream_dissolution",
        "input": ("minecraft:magma_cream", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("sulfur", 2),  # Magma + slime
        "note": "Magma cream to sulfur"
    },

    # === End Materials ===
    {
        "name": "end_stone_dissolution",
        "input": ("minecraft:end_stone", 1),
        "gas_input": ("hydrofluoric_acid", 1),
        "output": ("calcium", 2),  # Alien limestone
        "note": "End stone to calcium"
    },

    {
        "name": "chorus_fruit_dissolution",
        "input": ("minecraft:chorus_fruit", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 6),  # Organic alien fruit
        "note": "Chorus fruit to carbon"
    },

    {
        "name": "ender_pearl_dissolution",
        "input": ("minecraft:ender_pearl", 1),
        "gas_input": ("hydrofluoric_acid", 2),
        "output": ("beryllium", 4),  # Exotic material
        "note": "Ender pearl to beryllium"
    },

    # === Organic Items ===
    {
        "name": "paper_dissolution",
        "input": ("minecraft:paper", 1),
        "gas_input": ("sulfuric_acid", 0.5),
        "output": ("carbon", 3),  # Cellulose
        "note": "Paper to carbon"
    },

    {
        "name": "book_dissolution",
        "input": ("minecraft:book", 1),
        "gas_input": ("sulfuric_acid", 1.5),
        "output": ("carbon", 9),  # 3 paper + leather
        "note": "Book to carbon"
    },

    {
        "name": "leather_dissolution",
        "input": ("minecraft:leather", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 8),  # Protein/organic
        "note": "Leather to carbon"
    },

    {
        "name": "wool_dissolution",
        "input": ("minecraft:white_wool", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 12),  # Protein fibers
        "note": "Wool to carbon"
    },

    {
        "name": "string_dissolution",
        "input": ("minecraft:string", 1),
        "gas_input": ("sulfuric_acid", 0.25),
        "output": ("carbon", 2),  # Spider silk protein
        "note": "String to carbon"
    },

    # === Clay and Ceramics ===
    {
        "name": "clay_ball_dissolution",
        "input": ("minecraft:clay_ball", 1),
        "gas_input": ("hydrofluoric_acid", 0.5),
        "output": ("aluminum", 1),  # Aluminum silicate
        "note": "Clay to aluminum"
    },

    {
        "name": "clay_dissolution",
        "input": ("minecraft:clay", 1),
        "gas_input": ("hydrofluoric_acid", 2),
        "output": ("aluminum", 4),  # 4x clay ball
        "note": "Clay block to aluminum"
    },

    {
        "name": "brick_dissolution",
        "input": ("minecraft:brick", 1),
        "gas_input": ("hydrofluoric_acid", 1),
        "output": ("aluminum", 1),  # Fired clay
        "note": "Brick to aluminum"
    },

    # === Miscellaneous ===
    {
        "name": "gunpowder_dissolution",
        "input": ("minecraft:gunpowder", 1),
        "gas_input": ("water_vapor", 1),  # Different solvent
        "output": ("sulfur", 2),  # Sulfur + carbon + saltpeter
        "note": "Gunpowder to sulfur"
    },

    {
        "name": "flint_dissolution",
        "input": ("minecraft:flint", 1),
        "gas_input": ("hydrofluoric_acid", 1),
        "output": ("silicon", 2),  # Chert/silica
        "note": "Flint to silicon"
    },

    {
        "name": "prismarine_shard_dissolution",
        "input": ("minecraft:prismarine_shard", 1),
        "gas_input": ("hydrofluoric_acid", 1),
        "output": ("copper", 2),  # Blue-green color suggests copper
        "note": "Prismarine to copper"
    }
]

def create_dissolution_recipe(recipe_data):
    """Create a chemical dissolution recipe JSON."""
    # Convert gas_input amount to millibuckets (multiply by 1000)
    # But keep small amounts reasonable
    gas_amount = recipe_data["gas_input"][1]
    if gas_amount < 1:
        gas_amount = int(gas_amount * 1000)  # Convert fractions to mB
    else:
        gas_amount = int(gas_amount * 1000)

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

def check_chemical_exists(chemical_name):
    """Check if output chemical will work."""
    # These need to be registered as slurries or infusions
    needs_registration = [
        "sulfur", "phosphorus", "beryllium", "aluminum"
    ]

    if chemical_name in needs_registration:
        return False, f"Needs slurry_{chemical_name} registration"

    # These should work with existing mappings
    working = ["carbon", "silicon", "calcium", "copper"]
    if chemical_name in working:
        return True, "Should work with existing mappings"

    return False, "Unknown chemical"

def main():
    """Generate the second batch of dissolution recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    pending_count = 0
    working_recipes = []
    pending_recipes = []

    print("Generating Batch 2: Nether/End Materials and Complex Items")
    print("=" * 60)

    for recipe_data in BATCH_2_RECIPES:
        recipe_name = recipe_data["name"]
        output_chemical = recipe_data["output"][0]

        works, reason = check_chemical_exists(output_chemical)

        recipe_json = create_dissolution_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        if works:
            print(f"  ✅ Created {recipe_name}.json (working)")
            working_recipes.append(recipe_name)
        else:
            print(f"  ⚠️  Created {recipe_name}.json (pending: {reason})")
            pending_recipes.append((recipe_name, reason))
            pending_count += 1

        if "note" in recipe_data:
            print(f"     Note: {recipe_data['note']}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")
    print(f"  - Working immediately: {len(working_recipes)}")
    print(f"  - Pending registration: {pending_count}")

    if working_recipes:
        print("\n✅ Working recipes:")
        for r in working_recipes:
            print(f"  - {r}")

    if pending_recipes:
        print("\n⚠️  Pending recipes (need chemical registration):")
        for r, reason in pending_recipes:
            print(f"  - {r}: {reason}")

if __name__ == "__main__":
    main()