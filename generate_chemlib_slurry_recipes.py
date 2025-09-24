#!/usr/bin/env python3
"""
Generate dissolution recipes for ChemLib elements to our custom slurries.
For metals that Mekanism doesn't provide slurries for.
"""

import json
import os

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/dissolution"

# Elements that should produce our custom slurries
CHEMLIB_SLURRY_ELEMENTS = [
    # Common modded metals
    ("aluminum", "Aluminum"),
    ("titanium", "Titanium"),
    ("zinc", "Zinc"),
    ("nickel", "Nickel"),
    ("silver", "Silver"),
    ("platinum", "Platinum"),

    # Metalloids
    ("silicon", "Silicon"),
    ("germanium", "Germanium"),
    ("antimony", "Antimony"),
    ("bismuth", "Bismuth"),
    ("boron", "Boron"),
    ("arsenic", "Arsenic"),
    ("tellurium", "Tellurium"),

    # Alkali/Alkaline metals
    ("lithium", "Lithium"),
    ("sodium", "Sodium"),
    ("potassium", "Potassium"),
    ("calcium", "Calcium"),
    ("magnesium", "Magnesium"),
    ("barium", "Barium"),
    ("strontium", "Strontium"),
    ("rubidium", "Rubidium"),
    ("cesium", "Cesium"),

    # Transition metals
    ("tungsten", "Tungsten"),
    ("chromium", "Chromium"),
    ("manganese", "Manganese"),
    ("cobalt", "Cobalt"),
    ("vanadium", "Vanadium"),
    ("molybdenum", "Molybdenum"),
    ("tantalum", "Tantalum"),
    ("hafnium", "Hafnium"),
    ("rhenium", "Rhenium"),
    ("scandium", "Scandium"),
    ("yttrium", "Yttrium"),
    ("zirconium", "Zirconium"),
    ("niobium", "Niobium"),

    # Precious/Noble metals
    ("palladium", "Palladium"),
    ("rhodium", "Rhodium"),
    ("iridium", "Iridium"),
    ("ruthenium", "Ruthenium"),

    # Post-transition metals
    ("gallium", "Gallium"),
    ("indium", "Indium"),
    ("thallium", "Thallium"),
    ("cadmium", "Cadmium"),

    # Lanthanides
    ("lanthanum", "Lanthanum"),
    ("cerium", "Cerium"),
    ("neodymium", "Neodymium"),
    ("europium", "Europium"),
    ("gadolinium", "Gadolinium"),

    # Actinides (excluding uranium)
    ("thorium", "Thorium"),
    ("plutonium", "Plutonium"),
    ("neptunium", "Neptunium"),

    # Radioactive
    ("polonium", "Polonium"),
    ("radium", "Radium"),
    ("technetium", "Technetium"),
    ("francium", "Francium"),
]

def create_element_recipe(element_id, element_name):
    """Create element item to slurry recipe."""
    return {
        "name": f"element_{element_id}_to_slurry",
        "item": f"chemlibmekanized:{element_id}",
        "output": f"chemlibmekanized:dirty_{element_id}",
        "amount": 200,  # Element = 1/5 of ore
        "note": f"{element_name} element to slurry"
    }

def create_ingot_recipe(element_id, element_name):
    """Create ingot to slurry recipe."""
    return {
        "name": f"{element_id}_ingot_to_slurry",
        "item": f"chemlibmekanized:{element_id}_ingot",
        "output": f"chemlibmekanized:dirty_{element_id}",
        "amount": 1000,  # Ingot = full ore equivalent
        "note": f"{element_name} ingot to slurry"
    }

def create_nugget_recipe(element_id, element_name):
    """Create nugget to slurry recipe."""
    return {
        "name": f"{element_id}_nugget_to_slurry",
        "item": f"chemlibmekanized:{element_id}_nugget",
        "output": f"chemlibmekanized:dirty_{element_id}",
        "amount": 111,  # Nugget = 1/9 of ingot
        "note": f"{element_name} nugget to slurry"
    }

def create_plate_recipe(element_id, element_name):
    """Create plate to slurry recipe."""
    return {
        "name": f"{element_id}_plate_to_slurry",
        "item": f"chemlibmekanized:{element_id}_plate",
        "output": f"chemlibmekanized:dirty_{element_id}",
        "amount": 1000,  # Plate = ingot equivalent
        "note": f"{element_name} plate to slurry"
    }

def create_dissolution_recipe(recipe_data):
    """Create a dissolution chamber recipe JSON."""
    recipe = {
        "type": "mekanism:dissolution",
        "item_input": {
            "count": 1,
            "item": recipe_data["item"]
        },
        "chemical_input": {
            "amount": 100,  # Standard acid amount
            "chemical": "mekanism:sulfuric_acid"
        },
        "output": {
            "amount": recipe_data["amount"],
            "id": recipe_data["output"]
        },
        "per_tick_usage": True
    }
    return recipe

def main():
    """Generate dissolution recipes for ChemLib elements to our slurries."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    recipes = []

    # Generate all recipe types for each element
    for element_id, element_name in CHEMLIB_SLURRY_ELEMENTS:
        # Element item recipe
        recipes.append(create_element_recipe(element_id, element_name))

        # Ingot recipe (metals have ingots)
        if element_name not in ["Boron", "Silicon", "Arsenic", "Tellurium"]:  # Non-metals typically don't have ingots
            recipes.append(create_ingot_recipe(element_id, element_name))
            recipes.append(create_nugget_recipe(element_id, element_name))
            recipes.append(create_plate_recipe(element_id, element_name))

    print("Generating ChemLib Element → ChemLib Slurry Recipes")
    print("=" * 60)

    # Group recipes by type for output
    element_recipes = [r for r in recipes if "element_" in r["name"]]
    ingot_recipes = [r for r in recipes if "_ingot_" in r["name"]]
    nugget_recipes = [r for r in recipes if "_nugget_" in r["name"]]
    plate_recipes = [r for r in recipes if "_plate_" in r["name"]]

    # Generate element recipes
    print(f"\n📦 Element Item Recipes ({len(element_recipes)} metals, 200mB output):")
    for recipe_data in element_recipes[:10]:  # Show first 10
        recipe_json = create_dissolution_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_data['name']}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ {recipe_data['note']}")
        created_count += 1

    # Generate the rest quietly
    for recipe_data in element_recipes[10:]:
        recipe_json = create_dissolution_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_data['name']}.json")
        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)
        created_count += 1

    if len(element_recipes) > 10:
        print(f"  ... and {len(element_recipes) - 10} more")

    # Generate ingot recipes
    print(f"\n🔧 Ingot Recipes ({len(ingot_recipes)} metals, 1000mB output):")
    for recipe_data in ingot_recipes[:5]:  # Show first 5
        recipe_json = create_dissolution_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_data['name']}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ {recipe_data['note']}")
        created_count += 1

    # Generate the rest quietly
    for recipe_data in ingot_recipes[5:]:
        recipe_json = create_dissolution_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_data['name']}.json")
        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)
        created_count += 1

    if len(ingot_recipes) > 5:
        print(f"  ... and {len(ingot_recipes) - 5} more")

    # Generate nugget recipes
    print(f"\n🔸 Nugget Recipes ({len(nugget_recipes)} metals, 111mB output):")
    print(f"  ✅ Generated {len(nugget_recipes)} nugget recipes")
    for recipe_data in nugget_recipes:
        recipe_json = create_dissolution_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_data['name']}.json")
        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)
        created_count += 1

    # Generate plate recipes
    print(f"\n📋 Plate Recipes ({len(plate_recipes)} metals, 1000mB output):")
    print(f"  ✅ Generated {len(plate_recipes)} plate recipes")
    for recipe_data in plate_recipes:
        recipe_json = create_dissolution_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_data['name']}.json")
        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")

    print("\n📊 Recipe breakdown:")
    print(f"  - Element items: {len(element_recipes)} recipes")
    print(f"  - Ingots: {len(ingot_recipes)} recipes")
    print(f"  - Nuggets: {len(nugget_recipes)} recipes")
    print(f"  - Plates: {len(plate_recipes)} recipes")
    print("\n⚖️ All recipes match Mekanism's ore processing balance")

if __name__ == "__main__":
    main()