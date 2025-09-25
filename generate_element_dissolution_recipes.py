#!/usr/bin/env python3
"""
Generate 4 dissolution recipes per element using different acids
"""

import json
import os

# All elements that should have slurry forms
ELEMENTS = [
    "aluminum", "antimony", "arsenic", "barium", "beryllium", "bismuth", "boron",
    "cadmium", "calcium", "cerium", "cesium", "chromium", "cobalt", "copper",
    "dysprosium", "erbium", "europium", "francium", "gadolinium", "gallium",
    "germanium", "gold", "hafnium", "holmium", "indium", "iridium", "iron",
    "lanthanum", "lawrencium", "lead", "lithium", "lutetium", "magnesium",
    "manganese", "molybdenum", "neodymium", "nickel", "niobium", "osmium",
    "palladium", "platinum", "polonium", "potassium", "praseodymium", "promethium",
    "rhenium", "rhodium", "rubidium", "ruthenium", "samarium", "scandium",
    "silver", "sodium", "strontium", "tantalum", "technetium", "terbium",
    "thallium", "thorium", "thulium", "tin", "titanium", "tungsten", "uranium",
    "vanadium", "ytterbium", "yttrium", "zinc", "zirconium"
]

# The 4 acids used in dissolution
ACIDS = [
    ("sulfuric_acid", 20),      # Sulfuric acid uses 20mB
    ("hydrochloric_acid", 10),  # All others use 10mB
    ("nitric_acid", 10),
    ("hydrogen_chloride", 10)
]

def create_dissolution_recipe(element, acid_name, acid_amount):
    """Create a dissolution recipe for an element with a specific acid"""
    return {
        "type": "mekanism:dissolution",
        "item_input": {
            "count": 1,
            "item": f"chemlibmekanized:{element}"
        },
        "chemical_input": {
            "amount": acid_amount,
            "chemical": f"mekanism:{acid_name}"
        },
        "output": {
            "amount": 200,
            "id": f"chemlibmekanized:dirty_{element}"
        },
        "per_tick_usage": False
    }

def main():
    # Create the dissolution recipes directory
    output_dir = "src/main/resources/data/chemlibmekanized/recipe/dissolution"
    os.makedirs(output_dir, exist_ok=True)

    # First, remove all existing element dissolution recipes
    existing_files = os.listdir(output_dir)
    removed_count = 0
    for filename in existing_files:
        if filename.startswith("element_") and "_to_slurry" in filename:
            os.remove(os.path.join(output_dir, filename))
            removed_count += 1

    print(f"Removed {removed_count} old element dissolution recipes")

    created_count = 0

    for element in ELEMENTS:
        for acid_name, acid_amount in ACIDS:
            # Create the recipe
            recipe = create_dissolution_recipe(element, acid_name, acid_amount)

            # Generate filename based on acid
            acid_suffix = acid_name.replace("_acid", "").replace("hydrogen_chloride", "hcl")
            filename = f"{output_dir}/element_{element}_dissolution_{acid_suffix}.json"

            # Write the recipe file
            with open(filename, 'w') as f:
                json.dump(recipe, f, indent=2)

            created_count += 1

    print(f"\nTotal dissolution recipes created: {created_count}")
    print(f"Elements processed: {len(ELEMENTS)}")
    print(f"Recipes per element: {len(ACIDS)}")
    print(f"Location: {output_dir}/")

    # Verify all elements have 4 recipes
    print("\nVerifying recipe counts...")
    for element in ELEMENTS[:5]:  # Check first 5 as sample
        count = len([f for f in os.listdir(output_dir)
                    if f.startswith(f"element_{element}_dissolution_")])
        print(f"  {element}: {count} recipes")

if __name__ == "__main__":
    main()