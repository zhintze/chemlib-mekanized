#!/usr/bin/env python3
"""
Generate the first batch of Chemical Infuser recipes (20 basic compounds).
These are the most fundamental chemical reactions.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/chemical_infusing"

# Batch 1: Basic Compound Formation (20 recipes)
BATCH_1_RECIPES = [
    # 1. Water vapor formation (H2 + O -> H2O) - Skip if Mekanism has this
    {
        "name": "water_vapor_formation",
        "left": ("hydrogen", 2),
        "right": ("oxygen", 1),
        "output": ("water_vapor", 1)
    },

    # 2. Hydrogen chloride formation (H + Cl -> HCl)
    {
        "name": "hydrogen_chloride_formation",
        "left": ("hydrogen", 1),
        "right": ("chlorine", 1),
        "output": ("hydrogen_chloride", 1)
    },

    # 3. Ammonia synthesis (N + 3H -> NH3)
    {
        "name": "ammonia_synthesis",
        "left": ("nitrogen", 1),
        "right": ("hydrogen", 3),
        "output": ("ammonia", 2)
    },

    # 4. Methane formation (C + 2H2 -> CH4)
    {
        "name": "methane_formation",
        "left": ("carbon", 1),
        "right": ("hydrogen", 4),
        "output": ("methane", 1)
    },

    # 5. Carbon dioxide formation (C + O2 -> CO2)
    {
        "name": "carbon_dioxide_formation",
        "left": ("carbon", 1),
        "right": ("oxygen", 2),
        "output": ("carbon_dioxide", 1)
    },

    # 6. Sulfur dioxide formation (S + O2 -> SO2)
    {
        "name": "sulfur_dioxide_formation",
        "left": ("sulfur_dioxide", 1),  # Using SO2 as source since we don't have pure S
        "right": ("oxygen", 1),
        "output": ("sulfur_trioxide", 1)  # SO2 + O -> SO3
    },

    # 7. Hydrogen sulfide formation (H2 + S -> H2S)
    {
        "name": "hydrogen_sulfide_formation",
        "left": ("hydrogen", 2),
        "right": ("sulfur_dioxide", 1),
        "output": ("hydrogen_sulfide", 1)
    },

    # 8. Ethane formation (2C + 3H2 -> C2H6)
    {
        "name": "ethane_formation",
        "left": ("methane", 2),
        "right": ("hydrogen", 2),
        "output": ("ethane", 1)
    },

    # 9. Propane formation (C3H8)
    {
        "name": "propane_formation",
        "left": ("ethane", 1),
        "right": ("methane", 1),
        "output": ("propane", 1)
    },

    # 10. Butane formation (C4H10)
    {
        "name": "butane_formation",
        "left": ("propane", 1),
        "right": ("methane", 1),
        "output": ("butane", 1)
    },

    # 11. Carbon monoxide formation (C + O -> CO)
    {
        "name": "carbon_monoxide_formation",
        "left": ("carbon", 2),
        "right": ("oxygen", 1),
        "output": ("carbon_monoxide", 2)
    },

    # 12. Nitric oxide formation (N + O -> NO)
    {
        "name": "nitric_oxide_formation",
        "left": ("nitrogen", 1),
        "right": ("oxygen", 1),
        "output": ("nitric_oxide", 2)
    },

    # 13. Nitrogen dioxide formation (NO + O -> NO2)
    {
        "name": "nitrogen_dioxide_formation",
        "left": ("nitric_oxide", 2),
        "right": ("oxygen", 1),
        "output": ("nitrogen_dioxide", 2)
    },

    # 14. Ethylene formation (C2H4)
    {
        "name": "ethylene_formation",
        "left": ("carbon", 2),
        "right": ("hydrogen", 4),
        "output": ("ethylene", 1)
    },

    # 15. Acetylene formation (C2H2)
    {
        "name": "acetylene_formation",
        "left": ("carbon", 2),
        "right": ("hydrogen", 2),
        "output": ("acetylene", 1)
    },

    # 16. Hydrofluoric acid formation (H + F -> HF)
    {
        "name": "hydrofluoric_acid_formation",
        "left": ("hydrogen", 1),
        "right": ("fluorine", 1),
        "output": ("hydrofluoric_acid", 1)
    },

    # 17. Sulfuric acid formation (SO3 + H2O -> H2SO4)
    {
        "name": "sulfuric_acid_formation",
        "left": ("sulfur_trioxide", 1),
        "right": ("water_vapor", 1),
        "output": ("sulfuric_acid", 1)
    },

    # 18. Ethene formation (Mekanism specific)
    {
        "name": "ethene_formation",
        "left": ("ethylene", 1),
        "right": ("oxygen", 1),
        "output": ("ethene", 1)
    },

    # 19. Brine formation (NaCl + H2O)
    {
        "name": "brine_formation",
        "left": ("sodium", 1),
        "right": ("water_vapor", 10),
        "output": ("brine", 15)
    },

    # 20. Steam formation (H2O + heat, represented as water_vapor + oxygen)
    {
        "name": "steam_formation",
        "left": ("water_vapor", 1),
        "right": ("oxygen", 1),
        "output": ("steam", 2)
    }
]

def create_chemical_infuser_recipe(recipe_data):
    """Create a chemical infuser recipe JSON."""
    recipe = {
        "type": "mekanism:chemical_infusing",
        "left_input": {
            "amount": recipe_data["left"][1] * 1000,  # Convert to mB
            "chemical": get_chemical_id(recipe_data["left"][0])
        },
        "right_input": {
            "amount": recipe_data["right"][1] * 1000,  # Convert to mB
            "chemical": get_chemical_id(recipe_data["right"][0])
        },
        "output": {
            "amount": recipe_data["output"][1] * 1000,  # Convert to mB
            "id": get_chemical_id(recipe_data["output"][0])
        }
    }
    return recipe

def should_skip_recipe(recipe_name):
    """Check if we should skip a recipe because Mekanism already has it."""
    # List of recipes that Mekanism already provides
    mekanism_existing_recipes = [
        # Add known Mekanism recipes here as we discover them
    ]
    return recipe_name in mekanism_existing_recipes

def main():
    """Generate the first batch of chemical infuser recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    skipped_count = 0

    print("Generating Batch 1: Basic Chemical Compounds (20 recipes)")
    print("=" * 60)

    for recipe_data in BATCH_1_RECIPES:
        recipe_name = recipe_data["name"]

        if should_skip_recipe(recipe_name):
            print(f"  ⏭️  Skipping {recipe_name} (exists in Mekanism)")
            skipped_count += 1
            continue

        recipe_json = create_chemical_infuser_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ Created {recipe_name}.json")
        print(f"     {recipe_data['left'][1]}{recipe_data['left'][0]} + {recipe_data['right'][1]}{recipe_data['right'][0]} -> {recipe_data['output'][1]}{recipe_data['output'][0]}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes, Skipped {skipped_count} duplicates")
    print(f"Location: {RECIPE_DIR}/")

    # List any chemicals that need to be registered
    all_chemicals = set()
    for recipe in BATCH_1_RECIPES:
        all_chemicals.add(recipe["left"][0])
        all_chemicals.add(recipe["right"][0])
        all_chemicals.add(recipe["output"][0])

    print("\nChemicals used in this batch:")
    for chemical in sorted(all_chemicals):
        chem_id = get_chemical_id(chemical)
        if "mekanism:" in chem_id:
            print(f"  • {chemical} -> {chem_id} (Mekanism)")
        else:
            print(f"  • {chemical} -> {chem_id} (Ours)")

if __name__ == "__main__":
    main()