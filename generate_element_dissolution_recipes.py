#!/usr/bin/env python3
"""
Generate dissolution recipes for ChemLib element items to Mekanism slurries.
Matches Mekanism's ore processing balance.
"""

import json
import os

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/dissolution"

# Elements that should produce Mekanism slurries (not our own)
MEKANISM_SLURRY_ELEMENTS = [
    {
        "element": "iron",
        "input_item": "chemlibmekanized:iron",
        "output": "mekanism:dirty_iron",
        "amount": 200,  # Element item = 1/5 of ore block
        "note": "Iron element to Mekanism slurry"
    },
    {
        "element": "gold",
        "input_item": "chemlibmekanized:gold",
        "output": "mekanism:dirty_gold",
        "amount": 200,
        "note": "Gold element to Mekanism slurry"
    },
    {
        "element": "copper",
        "input_item": "chemlibmekanized:copper",
        "output": "mekanism:dirty_copper",
        "amount": 200,
        "note": "Copper element to Mekanism slurry"
    },
    {
        "element": "tin",
        "input_item": "chemlibmekanized:tin",
        "output": "mekanism:dirty_tin",
        "amount": 200,
        "note": "Tin element to Mekanism slurry"
    },
    {
        "element": "lead",
        "input_item": "chemlibmekanized:lead",
        "output": "mekanism:dirty_lead",
        "amount": 200,
        "note": "Lead element to Mekanism slurry"
    },
    {
        "element": "uranium",
        "input_item": "chemlibmekanized:uranium",
        "output": "mekanism:dirty_uranium",
        "amount": 200,
        "note": "Uranium element to Mekanism slurry"
    },
    {
        "element": "osmium",
        "input_item": "chemlibmekanized:osmium",
        "output": "mekanism:dirty_osmium",
        "amount": 200,
        "note": "Osmium element to Mekanism slurry"
    }
]

# Also create recipes for ingots to slurries (more efficient)
INGOT_TO_SLURRY_RECIPES = [
    {
        "element": "iron_ingot",
        "input_item": "chemlibmekanized:iron_ingot",
        "output": "mekanism:dirty_iron",
        "amount": 1000,  # Ingot = full ore equivalent
        "note": "Iron ingot to Mekanism slurry"
    },
    {
        "element": "gold_ingot",
        "input_item": "chemlibmekanized:gold_ingot",
        "output": "mekanism:dirty_gold",
        "amount": 1000,
        "note": "Gold ingot to Mekanism slurry"
    },
    {
        "element": "copper_ingot",
        "input_item": "chemlibmekanized:copper_ingot",
        "output": "mekanism:dirty_copper",
        "amount": 1000,
        "note": "Copper ingot to Mekanism slurry"
    },
    {
        "element": "tin_ingot",
        "input_item": "chemlibmekanized:tin_ingot",
        "output": "mekanism:dirty_tin",
        "amount": 1000,
        "note": "Tin ingot to Mekanism slurry"
    },
    {
        "element": "lead_ingot",
        "input_item": "chemlibmekanized:lead_ingot",
        "output": "mekanism:dirty_lead",
        "amount": 1000,
        "note": "Lead ingot to Mekanism slurry"
    },
    {
        "element": "uranium_ingot",
        "input_item": "chemlibmekanized:uranium_ingot",
        "output": "mekanism:dirty_uranium",
        "amount": 1000,
        "note": "Uranium ingot to Mekanism slurry"
    },
    {
        "element": "osmium_ingot",
        "input_item": "chemlibmekanized:osmium_ingot",
        "output": "mekanism:dirty_osmium",
        "amount": 1000,
        "note": "Osmium ingot to Mekanism slurry"
    }
]

# Nugget recipes (least efficient)
NUGGET_TO_SLURRY_RECIPES = [
    {
        "element": "iron_nugget",
        "input_item": "chemlibmekanized:iron_nugget",
        "output": "mekanism:dirty_iron",
        "amount": 111,  # 1/9 of ingot
        "note": "Iron nugget to Mekanism slurry"
    },
    {
        "element": "gold_nugget",
        "input_item": "chemlibmekanized:gold_nugget",
        "output": "mekanism:dirty_gold",
        "amount": 111,
        "note": "Gold nugget to Mekanism slurry"
    },
    {
        "element": "copper_nugget",
        "input_item": "chemlibmekanized:copper_nugget",
        "output": "mekanism:dirty_copper",
        "amount": 111,
        "note": "Copper nugget to Mekanism slurry"
    },
    {
        "element": "tin_nugget",
        "input_item": "chemlibmekanized:tin_nugget",
        "output": "mekanism:dirty_tin",
        "amount": 111,
        "note": "Tin nugget to Mekanism slurry"
    },
    {
        "element": "lead_nugget",
        "input_item": "chemlibmekanized:lead_nugget",
        "output": "mekanism:dirty_lead",
        "amount": 111,
        "note": "Lead nugget to Mekanism slurry"
    },
    {
        "element": "uranium_nugget",
        "input_item": "chemlibmekanized:uranium_nugget",
        "output": "mekanism:dirty_uranium",
        "amount": 111,
        "note": "Uranium nugget to Mekanism slurry"
    },
    {
        "element": "osmium_nugget",
        "input_item": "chemlibmekanized:osmium_nugget",
        "output": "mekanism:dirty_osmium",
        "amount": 111,
        "note": "Osmium nugget to Mekanism slurry"
    }
]

def create_dissolution_recipe(element_data):
    """Create a dissolution chamber recipe JSON."""
    recipe = {
        "type": "mekanism:dissolution",
        "item_input": {
            "count": 1,
            "item": element_data["input_item"]
        },
        "chemical_input": {
            "amount": 100,  # Standard amount of acid
            "chemical": "mekanism:sulfuric_acid"
        },
        "output": {
            "amount": element_data["amount"],
            "id": element_data["output"]
        },
        "per_tick_usage": True
    }
    return recipe

def main():
    """Generate dissolution recipes for ChemLib elements to Mekanism slurries."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0

    print("Generating ChemLib Element → Mekanism Slurry Recipes")
    print("=" * 60)

    # Generate element recipes
    print("\n📦 Element Item Recipes (200mB output):")
    for element_data in MEKANISM_SLURRY_ELEMENTS:
        recipe_name = f"element_{element_data['element']}_to_slurry"

        recipe_json = create_dissolution_recipe(element_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ Created {recipe_name}.json")
        if "note" in element_data:
            print(f"     Note: {element_data['note']}")
        created_count += 1

    # Generate ingot recipes
    print("\n🔧 Ingot Recipes (1000mB output):")
    for element_data in INGOT_TO_SLURRY_RECIPES:
        recipe_name = f"{element_data['element']}_to_slurry"

        recipe_json = create_dissolution_recipe(element_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ Created {recipe_name}.json")
        if "note" in element_data:
            print(f"     Note: {element_data['note']}")
        created_count += 1

    # Generate nugget recipes
    print("\n🔸 Nugget Recipes (111mB output):")
    for element_data in NUGGET_TO_SLURRY_RECIPES:
        recipe_name = f"{element_data['element']}_to_slurry"

        recipe_json = create_dissolution_recipe(element_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ Created {recipe_name}.json")
        if "note" in element_data:
            print(f"     Note: {element_data['note']}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")

    print("\n📊 Output ratios (matching Mekanism balance):")
    print("  - Element item: 200mB (1/5 of ore)")
    print("  - Ingot: 1000mB (same as ore block)")
    print("  - Nugget: 111mB (1/9 of ingot)")
    print("\n⚖️ This maintains Mekanism's ore processing balance while")
    print("  allowing ChemLib items to integrate into the slurry system.")

if __name__ == "__main__":
    main()