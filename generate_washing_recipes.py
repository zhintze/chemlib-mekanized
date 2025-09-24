#!/usr/bin/env python3
"""
Generate washing recipes to convert dirty slurries to clean slurries.
This creates the processing chain: Ore → Dirty Slurry → Clean Slurry → Crystals
"""

import json
import os

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/washing"

# All metals/materials that have slurries
SLURRY_METALS = [
    # Common metals
    "aluminum", "titanium", "zinc", "nickel", "silver", "platinum",
    "iron", "copper", "gold",

    # Rare metals
    "tungsten", "chromium", "manganese", "cobalt", "cadmium", "mercury",

    # Precious metals
    "palladium", "rhodium", "iridium", "ruthenium",

    # Radioactive
    "thorium", "uranium",

    # Alkali/Alkaline
    "lithium", "sodium", "potassium", "calcium", "magnesium", "barium", "strontium",

    # Metalloids
    "silicon", "germanium", "antimony", "bismuth", "arsenic", "tellurium", "boron",

    # Lanthanides
    "cerium", "neodymium", "lanthanum", "gadolinium", "europium",

    # Other metals
    "indium", "gallium", "hafnium", "tantalum", "rhenium", "molybdenum",
    "vanadium", "niobium", "beryllium", "zirconium", "scandium", "yttrium",
    "thallium", "tin", "lead", "polonium", "technetium", "osmium",
    "rubidium", "cesium", "francium", "radium",

    # Actinides
    "actinium", "protactinium", "neptunium", "plutonium", "americium",
    "curium", "berkelium", "californium",

    # Special materials
    "diamond", "emerald", "quartz", "lapis", "redstone", "coal",
    "netherite", "obsidian"
]

def create_washing_recipe(metal_name):
    """Create a washing recipe JSON for converting dirty to clean slurry."""
    recipe = {
        "type": "mekanism:washing",
        "chemical_input": {
            "amount": 1,
            "chemical": f"chemlibmekanized:dirty_{metal_name}"
        },
        "fluid_input": {
            "amount": 5,
            "tag": "minecraft:water"
        },
        "output": {
            "amount": 1,
            "id": f"chemlibmekanized:clean_{metal_name}"
        }
    }
    return recipe

def main():
    """Generate washing recipes for all registered slurries."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0

    print("Generating Washing Recipes for Slurries")
    print("=" * 60)

    for metal in SLURRY_METALS:
        recipe_name = f"{metal}_washing"
        recipe_json = create_washing_recipe(metal)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        created_count += 1

        # Print progress every 10 recipes
        if created_count % 10 == 0:
            print(f"  ✅ Created {created_count} washing recipes...")

    print(f"  ✅ Created all {created_count} washing recipes")
    print("=" * 60)
    print(f"Summary: Created {created_count} washing recipes")
    print(f"Processing chain complete: Ore → Dirty Slurry → Clean Slurry → Crystals")

if __name__ == "__main__":
    main()