#!/usr/bin/env python3
"""
Generate Batch 3 of Chemical Dissolution Chamber recipes.
Focus on food items for organic chemistry extraction.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/dissolution"

# Batch 3: Food Items and Agricultural Products
BATCH_3_RECIPES = [
    # === Grains and Starches ===
    {
        "name": "wheat_dissolution",
        "input": ("minecraft:wheat", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 12),  # Starch/carbohydrates
        "note": "Wheat to carbon (starch)"
    },

    {
        "name": "bread_dissolution",
        "input": ("minecraft:bread", 1),
        "gas_input": ("sulfuric_acid", 2),
        "output": ("carbon", 18),  # Baked wheat
        "note": "Bread to carbon"
    },

    {
        "name": "potato_dissolution",
        "input": ("minecraft:potato", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 8),  # Starch
        "note": "Potato to carbon (starch)"
    },

    {
        "name": "baked_potato_dissolution",
        "input": ("minecraft:baked_potato", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 10),  # Cooked starch
        "note": "Baked potato to carbon"
    },

    {
        "name": "carrot_dissolution",
        "input": ("minecraft:carrot", 1),
        "gas_input": ("sulfuric_acid", 0.5),
        "output": ("carbon", 6),  # Sugars and fiber
        "note": "Carrot to carbon"
    },

    {
        "name": "beetroot_dissolution",
        "input": ("minecraft:beetroot", 1),
        "gas_input": ("sulfuric_acid", 0.5),
        "output": ("carbon", 5),  # Sugar beet
        "note": "Beetroot to carbon (sugars)"
    },

    # === Fruits ===
    {
        "name": "apple_dissolution",
        "input": ("minecraft:apple", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 10),  # Fructose and fiber
        "note": "Apple to carbon (fructose)"
    },

    {
        "name": "melon_slice_dissolution",
        "input": ("minecraft:melon_slice", 1),
        "gas_input": ("sulfuric_acid", 0.25),
        "output": ("carbon", 2),  # Mostly water, some sugar
        "note": "Melon to carbon"
    },

    {
        "name": "sweet_berries_dissolution",
        "input": ("minecraft:sweet_berries", 1),
        "gas_input": ("sulfuric_acid", 0.25),
        "output": ("carbon", 3),  # Small fruit sugars
        "note": "Sweet berries to carbon"
    },

    {
        "name": "glow_berries_dissolution",
        "input": ("minecraft:glow_berries", 1),
        "gas_input": ("sulfuric_acid", 0.5),
        "output": ("phosphorus", 1),  # Bioluminescent compound
        "note": "Glow berries to phosphorus"
    },

    # === Proteins/Meats ===
    {
        "name": "beef_dissolution",
        "input": ("minecraft:beef", 1),
        "gas_input": ("sulfuric_acid", 2),
        "output": ("carbon", 16),  # Protein
        "note": "Raw beef to carbon (protein)"
    },

    {
        "name": "porkchop_dissolution",
        "input": ("minecraft:porkchop", 1),
        "gas_input": ("sulfuric_acid", 2),
        "output": ("carbon", 14),  # Protein
        "note": "Raw pork to carbon (protein)"
    },

    {
        "name": "chicken_dissolution",
        "input": ("minecraft:chicken", 1),
        "gas_input": ("sulfuric_acid", 1.5),
        "output": ("carbon", 12),  # Lean protein
        "note": "Raw chicken to carbon (protein)"
    },

    {
        "name": "mutton_dissolution",
        "input": ("minecraft:mutton", 1),
        "gas_input": ("sulfuric_acid", 2),
        "output": ("carbon", 15),  # Protein
        "note": "Raw mutton to carbon (protein)"
    },

    {
        "name": "rabbit_dissolution",
        "input": ("minecraft:rabbit", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 10),  # Lean protein
        "note": "Raw rabbit to carbon (protein)"
    },

    {
        "name": "cod_dissolution",
        "input": ("minecraft:cod", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("carbon", 8),  # Fish protein
        "note": "Raw cod to carbon"
    },

    {
        "name": "salmon_dissolution",
        "input": ("minecraft:salmon", 1),
        "gas_input": ("sulfuric_acid", 1.5),
        "output": ("carbon", 10),  # Fatty fish
        "note": "Raw salmon to carbon"
    },

    # === Dairy and Eggs ===
    {
        "name": "egg_dissolution",
        "input": ("minecraft:egg", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("calcium", 2),  # Shell calcium + protein
        "note": "Egg to calcium (shell)"
    },

    # === Sugars and Sweets ===
    {
        "name": "cookie_dissolution",
        "input": ("minecraft:cookie", 1),
        "gas_input": ("sulfuric_acid", 0.5),
        "output": ("carbon", 6),  # Wheat + cocoa
        "note": "Cookie to carbon"
    },

    {
        "name": "pumpkin_pie_dissolution",
        "input": ("minecraft:pumpkin_pie", 1),
        "gas_input": ("sulfuric_acid", 2),
        "output": ("carbon", 20),  # Complex recipe
        "note": "Pumpkin pie to carbon"
    },

    {
        "name": "cake_dissolution",
        "input": ("minecraft:cake", 1),
        "gas_input": ("sulfuric_acid", 5),
        "output": ("carbon", 48),  # Wheat + sugar + egg + milk
        "note": "Cake to carbon (complex)"
    },

    {
        "name": "honey_bottle_dissolution",
        "input": ("minecraft:honey_bottle", 1),
        "gas_input": ("water_vapor", 1),  # Different solvent
        "output": ("carbon", 24),  # Pure sugar
        "note": "Honey to carbon (glucose/fructose)"
    },

    # === Vegetables ===
    {
        "name": "pumpkin_dissolution",
        "input": ("minecraft:pumpkin", 1),
        "gas_input": ("sulfuric_acid", 2),
        "output": ("carbon", 16),  # Large vegetable
        "note": "Pumpkin to carbon"
    },

    {
        "name": "kelp_dissolution",
        "input": ("minecraft:kelp", 1),
        "gas_input": ("sulfuric_acid", 0.5),
        "output": ("iodine", 1),  # Seaweed contains iodine
        "note": "Kelp to iodine"
    },

    {
        "name": "dried_kelp_dissolution",
        "input": ("minecraft:dried_kelp", 1),
        "gas_input": ("sulfuric_acid", 0.25),
        "output": ("iodine", 2),  # Concentrated
        "note": "Dried kelp to iodine"
    },

    # === Mushrooms ===
    {
        "name": "red_mushroom_dissolution",
        "input": ("minecraft:red_mushroom", 1),
        "gas_input": ("sulfuric_acid", 0.5),
        "output": ("carbon", 4),  # Fungal matter
        "note": "Red mushroom to carbon"
    },

    {
        "name": "brown_mushroom_dissolution",
        "input": ("minecraft:brown_mushroom", 1),
        "gas_input": ("sulfuric_acid", 0.5),
        "output": ("carbon", 4),  # Fungal matter
        "note": "Brown mushroom to carbon"
    },

    # === Other Foods ===
    {
        "name": "cocoa_beans_dissolution",
        "input": ("minecraft:cocoa_beans", 1),
        "gas_input": ("sulfuric_acid", 0.5),
        "output": ("carbon", 8),  # Alkaloids and oils
        "note": "Cocoa to carbon"
    },

    {
        "name": "sugar_cane_dissolution",
        "input": ("minecraft:sugar_cane", 1),
        "gas_input": ("water_vapor", 0.5),
        "output": ("carbon", 6),  # Sucrose
        "note": "Sugar cane to carbon"
    },

    {
        "name": "bamboo_dissolution",
        "input": ("minecraft:bamboo", 1),
        "gas_input": ("sulfuric_acid", 1),
        "output": ("silicon", 1),  # Bamboo is high in silica
        "note": "Bamboo to silicon"
    }
]

def create_dissolution_recipe(recipe_data):
    """Create a chemical dissolution recipe JSON."""
    # Convert gas_input amount to millibuckets
    gas_amount = recipe_data["gas_input"][1]
    if gas_amount < 1:
        gas_amount = int(gas_amount * 1000)
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
    # These need to be registered
    needs_registration = [
        "phosphorus", "iodine"
    ]

    if chemical_name in needs_registration:
        return False, f"Needs slurry_{chemical_name} registration"

    # These should work
    working = ["carbon", "silicon", "calcium"]
    if chemical_name in working:
        return True, "Should work with existing mappings"

    return True, "Assuming it works"

def main():
    """Generate the third batch of dissolution recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    pending_count = 0
    working_recipes = []
    pending_recipes = []

    print("Generating Batch 3: Food Items and Agricultural Products")
    print("=" * 60)

    for recipe_data in BATCH_3_RECIPES:
        recipe_name = recipe_data["name"]
        output_chemical = recipe_data["output"][0]

        works, reason = check_chemical_exists(output_chemical)

        recipe_json = create_dissolution_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        if works:
            print(f"  ✅ Created {recipe_name}.json")
            working_recipes.append(recipe_name)
        else:
            print(f"  ⚠️  Created {recipe_name}.json (pending: {reason})")
            pending_recipes.append((recipe_name, reason))
            pending_count += 1

        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")
    print(f"  - Working immediately: {len(working_recipes)}")
    print(f"  - Pending registration: {pending_count}")

    # Group recipes by output type
    carbon_recipes = [r for r in working_recipes if "mushroom" in r or "meat" in r or "potato" in r or
                      "wheat" in r or "bread" in r or "carrot" in r or "apple" in r or
                      "berries" in r or "beef" in r or "pork" in r or "chicken" in r or
                      "mutton" in r or "rabbit" in r or "cod" in r or "salmon" in r or
                      "cookie" in r or "pie" in r or "cake" in r or "honey" in r or
                      "pumpkin" in r or "cocoa" in r or "sugar_cane" in r or "beetroot" in r or
                      "melon" in r]

    print(f"\n📊 Recipe breakdown:")
    print(f"  - Carbon outputs: {len(carbon_recipes)}")
    print(f"  - Other outputs: {len(working_recipes) - len(carbon_recipes)}")

    if pending_recipes:
        print(f"\n⚠️  Chemicals needing registration:")
        unique_chems = set()
        for _, reason in pending_recipes:
            if "slurry_" in reason:
                chem = reason.split("slurry_")[1].split()[0]
                unique_chems.add(chem)
        for chem in unique_chems:
            print(f"  - slurry_{chem}")

if __name__ == "__main__":
    main()