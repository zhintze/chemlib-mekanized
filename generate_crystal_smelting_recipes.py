#!/usr/bin/env python3
"""
Generate smelting recipes for metal crystals → ingots.
1 crystal → 1 ingot
"""

import json
import os

# All metals/metalloids that have crystals and nuggets
METALS = [
    # Common metals
    "aluminum", "titanium", "zinc", "nickel", "silver", "platinum",
    # Rare metals
    "tungsten", "chromium", "manganese", "cobalt", "cadmium", "mercury",
    # Precious metals
    "palladium", "rhodium", "iridium", "ruthenium",
    # Radioactive
    "thorium", "polonium",
    # Alkali/Alkaline
    "lithium", "sodium", "potassium", "calcium", "magnesium", "barium",
    "strontium", "rubidium", "cesium", "francium", "radium",
    # Metalloids
    "silicon", "germanium", "antimony", "bismuth", "boron", "arsenic", "tellurium",
    # Lanthanides
    "cerium", "neodymium", "lanthanum", "gadolinium", "europium",
    # Other metals
    "indium", "gallium", "hafnium", "tantalum", "rhenium", "molybdenum",
    "vanadium", "niobium", "beryllium", "zirconium", "scandium", "yttrium",
    "thallium", "technetium",
    # Actinides
    "actinium", "protactinium", "neptunium", "plutonium", "americium",
    "curium", "berkelium", "californium"
]

def create_smelting_recipe(metal):
    """
    Create a furnace smelting recipe.
    1 crystal → 1 ingot
    """
    return {
        "type": "minecraft:smelting",
        "category": "misc",
        "cookingtime": 200,
        "experience": 0.7,
        "ingredient": {
            "item": f"chemlibmekanized:{metal}_crystal"
        },
        "result": {
            "count": 1,
            "id": f"chemlibmekanized:{metal}_ingot"
        }
    }

def create_blasting_recipe(metal):
    """
    Create a blast furnace recipe (faster).
    1 crystal → 1 ingot
    """
    return {
        "type": "minecraft:blasting",
        "category": "misc",
        "cookingtime": 100,
        "experience": 0.7,
        "ingredient": {
            "item": f"chemlibmekanized:{metal}_crystal"
        },
        "result": {
            "count": 1,
            "id": f"chemlibmekanized:{metal}_ingot"
        }
    }

def main():
    # Create output directories
    smelting_dir = "src/main/resources/data/chemlibmekanized/recipe/smelting"
    blasting_dir = "src/main/resources/data/chemlibmekanized/recipe/blasting"
    os.makedirs(smelting_dir, exist_ok=True)
    os.makedirs(blasting_dir, exist_ok=True)

    # Generate smelting and blasting recipes
    for metal in METALS:
        # Smelting recipe
        smelting_recipe = create_smelting_recipe(metal)
        smelting_path = os.path.join(smelting_dir, f"{metal}_crystal_smelting.json")
        with open(smelting_path, 'w') as f:
            json.dump(smelting_recipe, f, indent=2)

        # Blasting recipe
        blasting_recipe = create_blasting_recipe(metal)
        blasting_path = os.path.join(blasting_dir, f"{metal}_crystal_blasting.json")
        with open(blasting_path, 'w') as f:
            json.dump(blasting_recipe, f, indent=2)

    print(f"Generated {len(METALS)} smelting recipes")
    print(f"Generated {len(METALS)} blasting recipes")
    print("Recipe: 1 crystal → 1 ingot")

if __name__ == "__main__":
    main()