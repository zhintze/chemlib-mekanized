#!/usr/bin/env python3
"""
Generate washing recipes for ChemLib slurries (dirty → clean conversion).
Uses Mekanism's Chemical Washer to clean dirty slurries.
"""

import json
import os

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/washing"

# All our custom slurries (excluding Mekanism duplicates)
CHEMLIB_SLURRIES = [
    # Common metals
    "aluminum", "titanium", "zinc", "nickel", "silver", "platinum",

    # Rare metals
    "tungsten", "chromium", "manganese", "cobalt", "cadmium", "mercury",

    # Precious metals
    "palladium", "rhodium", "iridium", "ruthenium",

    # Radioactive
    "thorium",

    # Alkali/Alkaline metals
    "lithium", "sodium", "potassium", "calcium", "magnesium", "barium", "strontium",

    # Metalloids
    "silicon", "germanium", "antimony", "bismuth",

    # Lanthanides
    "cerium", "neodymium", "lanthanum", "gadolinium", "europium",

    # Other metals
    "indium", "gallium", "hafnium", "tantalum", "rhenium", "molybdenum",
    "vanadium", "niobium", "beryllium", "zirconium", "scandium", "yttrium",
    "thallium", "polonium", "technetium", "rubidium", "cesium", "francium", "radium",

    # Additional metalloids
    "arsenic", "tellurium", "boron", "astatine",

    # Alternative spellings
    "aluminium", "gallium_arsenide",

    # Actinides
    "actinium", "protactinium", "neptunium", "plutonium",
    "americium", "curium", "berkelium", "californium",

    # Alternative names
    "wolfram",

    # Special materials
    "quartz", "lapis", "coal", "netherite_scrap", "emerald"
]

def create_washing_recipe(slurry_name):
    """Create a washing recipe for dirty → clean slurry conversion."""
    recipe = {
        "type": "mekanism:washing",
        "fluid_input": {
            "amount": 5,  # Standard Mekanism water amount
            "tag": "minecraft:water"
        },
        "chemical_input": {
            "amount": 1,
            "chemical": f"chemlibmekanized:dirty_{slurry_name}"
        },
        "output": {
            "amount": 1,
            "id": f"chemlibmekanized:clean_{slurry_name}"
        }
    }
    return recipe

def main():
    """Generate all slurry washing recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0

    print("Generating ChemLib Slurry Washing Recipes")
    print("=" * 60)
    print(f"Creating {len(CHEMLIB_SLURRIES)} washing recipes...")
    print()

    # Show first 10 for feedback
    for i, slurry_name in enumerate(CHEMLIB_SLURRIES[:10]):
        recipe_json = create_washing_recipe(slurry_name)
        file_path = os.path.join(RECIPE_DIR, f"{slurry_name}_slurry_washing.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ {slurry_name.title()} slurry washing")
        created_count += 1

    # Generate the rest quietly
    for slurry_name in CHEMLIB_SLURRIES[10:]:
        recipe_json = create_washing_recipe(slurry_name)
        file_path = os.path.join(RECIPE_DIR, f"{slurry_name}_slurry_washing.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        created_count += 1

    if len(CHEMLIB_SLURRIES) > 10:
        print(f"  ... and {len(CHEMLIB_SLURRIES) - 10} more")

    print()
    print("=" * 60)
    print(f"✨ Summary: Created {created_count} washing recipes")
    print()
    print("📋 Recipe format:")
    print("  • Input: 1mB dirty slurry + 5mB water")
    print("  • Output: 1mB clean slurry")
    print("  • Machine: Chemical Washer")
    print()
    print("⚖️ Matches Mekanism's standard washing ratios")

if __name__ == "__main__":
    main()