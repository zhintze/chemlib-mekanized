#!/usr/bin/env python3
"""
Generate Mekanism Chemical Crystallizer recipes for metals and metalloids.
1800mB clean slurry → 1 crystal (maintaining 9 elements = 1 nugget balance)
"""

import json
import os

# All metals/metalloids that should have crystallizer recipes
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

def create_crystallizer_recipe(metal):
    """
    Create a Chemical Crystallizer recipe.
    16200mB clean slurry → 1 crystal (81x element = 1 ingot balance)
    """
    return {
        "type": "mekanism:crystallizing",
        "input": {
            "amount": 16200,  # 16200mB = 81 elements worth (200mB each) = 1 ingot
            "chemical": f"chemlibmekanized:clean_{metal}"
        },
        "output": {
            "count": 1,
            "id": f"chemlibmekanized:{metal}_crystal"
        }
    }

def main():
    # Create output directory
    output_dir = "src/main/resources/data/chemlibmekanized/recipe/crystallizing"
    os.makedirs(output_dir, exist_ok=True)

    # Generate crystallizer recipes
    for metal in METALS:
        recipe = create_crystallizer_recipe(metal)

        # Write recipe file
        output_path = os.path.join(output_dir, f"{metal}_crystal.json")
        with open(output_path, 'w') as f:
            json.dump(recipe, f, indent=2)

    print(f"Generated {len(METALS)} crystallizer recipes")
    print(f"Recipe balance: 16200mB clean slurry → 1 crystal → 1 ingot")
    print("(Maintains 81 elements = 1 ingot ratio)")

if __name__ == "__main__":
    main()