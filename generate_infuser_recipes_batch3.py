#!/usr/bin/env python3
"""
Generate Batch 3 of Chemical Infuser recipes.
Focus on acid-base reactions and noble gas compounds using registered chemicals.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/chemical_infusing"

# Batch 3: Acid-Base and Noble Gas Reactions
BATCH_3_RECIPES = [
    # === Acid Anhydrides ===
    {
        "name": "sulfur_trioxide_hydration",
        "left_input": ("sulfur_trioxide", 1),
        "right_input": ("water_vapor", 1),
        "output": ("sulfuric_acid", 2),
        "note": "SO3 + H2O → H2SO4"
    },

    {
        "name": "nitrogen_dioxide_hydration",
        "left_input": ("nitrogen_dioxide", 2),
        "right_input": ("water_vapor", 1),
        "output": ("nitric_acid", 2),
        "note": "2NO2 + H2O → HNO3 + HNO2"
    },

    # === More Nitrogen Compounds ===
    {
        "name": "nitrogen_oxygen_mix",
        "left_input": ("nitrogen", 2),
        "right_input": ("oxygen", 1),
        "output": ("nitric_oxide", 2),
        "note": "N2 + O2 → 2NO (high temp)"
    },

    {
        "name": "nitrous_oxide_formation",
        "left_input": ("nitrogen", 2),
        "right_input": ("oxygen", 1),
        "output": ("nitrous_oxide", 2),
        "note": "2N2 + O2 → 2N2O"
    },

    # === Hydrogen Compounds ===
    {
        "name": "hydrogen_peroxide_formation",
        "left_input": ("hydrogen", 1),
        "right_input": ("oxygen", 2),
        "output": ("hydrogen_peroxide", 1),
        "note": "H2 + O2 → H2O2"
    },

    {
        "name": "hydrazine_formation",
        "left_input": ("ammonia", 2),
        "right_input": ("hydrogen_peroxide", 1),
        "output": ("hydrazine", 1),
        "note": "2NH3 + H2O2 → N2H4 + 2H2O"
    },

    # === Carbon-Sulfur Compounds ===
    {
        "name": "carbon_disulfide_formation",
        "left_input": ("carbon_monoxide", 1),
        "right_input": ("sulfur_dioxide", 2),
        "output": ("carbon_disulfide", 1),
        "note": "CO + 2SO2 → CS2 + 2O2"
    },

    {
        "name": "carbonyl_sulfide_formation",
        "left_input": ("carbon_monoxide", 1),
        "right_input": ("hydrogen_sulfide", 1),
        "output": ("carbonyl_sulfide", 1),
        "note": "CO + H2S → COS + H2"
    },

    # === Ozone Chemistry ===
    {
        "name": "ozone_formation",
        "left_input": ("oxygen", 3),
        "right_input": ("oxygen", 1),  # UV catalyst simulation
        "output": ("ozone", 2),
        "note": "3O2 → 2O3"
    },

    # === Noble Gas Fluorides ===
    {
        "name": "xenon_difluoride",
        "left_input": ("xenon", 1),
        "right_input": ("fluorine", 2),
        "output": ("xenon_difluoride", 1),
        "note": "Xe + 2F2 → XeF2"
    },

    {
        "name": "xenon_tetrafluoride",
        "left_input": ("xenon", 1),
        "right_input": ("fluorine", 4),
        "output": ("xenon_tetrafluoride", 1),
        "note": "Xe + 4F2 → XeF4"
    },

    {
        "name": "krypton_difluoride",
        "left_input": ("krypton", 1),
        "right_input": ("fluorine", 2),
        "output": ("krypton_difluoride", 1),
        "note": "Kr + 2F2 → KrF2"
    },

    # === Phosphorus Compounds ===
    {
        "name": "phosphine_formation",
        "left_input": ("hydrogen", 3),
        "right_input": ("phosphorus", 1),
        "output": ("phosphine", 1),
        "note": "3H2 + 2P → 2PH3 (simplified)"
    },

    {
        "name": "phosphorus_trichloride",
        "left_input": ("phosphorus", 1),
        "right_input": ("chlorine", 3),
        "output": ("phosphorus_trichloride", 1),
        "note": "P + 3Cl2 → PCl3"
    },

    # === Boron Compounds ===
    {
        "name": "borane_formation",
        "left_input": ("boron", 1),
        "right_input": ("hydrogen", 3),
        "output": ("borane", 1),
        "note": "B + 3H2 → BH3"
    },

    {
        "name": "boron_trifluoride",
        "left_input": ("boron", 1),
        "right_input": ("fluorine", 3),
        "output": ("boron_trifluoride", 1),
        "note": "B + 3F2 → BF3"
    },

    # === Silicon Compounds ===
    {
        "name": "silicon_tetrafluoride",
        "left_input": ("silicon", 1),
        "right_input": ("fluorine", 4),
        "output": ("silicon_tetrafluoride", 1),
        "note": "Si + 4F2 → SiF4"
    },

    {
        "name": "silicon_tetrachloride",
        "left_input": ("silicon", 1),
        "right_input": ("chlorine", 4),
        "output": ("silicon_tetrachloride", 1),
        "note": "Si + 4Cl2 → SiCl4"
    },

    # === Mixed Halogens ===
    {
        "name": "chlorine_trifluoride",
        "left_input": ("chlorine", 1),
        "right_input": ("fluorine", 3),
        "output": ("chlorine_trifluoride", 1),
        "note": "Cl2 + 3F2 → 2ClF3"
    },

    {
        "name": "iodine_pentafluoride",
        "left_input": ("iodine", 1),
        "right_input": ("fluorine", 5),
        "output": ("iodine_pentafluoride", 1),
        "note": "I2 + 5F2 → 2IF5"
    }
]

def create_infuser_recipe(recipe_data):
    """Create a chemical infuser recipe JSON."""
    recipe = {
        "type": "mekanism:chemical_infusing",
        "left_input": {
            "amount": recipe_data["left_input"][1] * 1000,
            "chemical": get_chemical_id(recipe_data["left_input"][0])
        },
        "right_input": {
            "amount": recipe_data["right_input"][1] * 1000,
            "chemical": get_chemical_id(recipe_data["right_input"][0])
        },
        "output": {
            "amount": recipe_data["output"][1] * 1000,
            "id": get_chemical_id(recipe_data["output"][0])
        }
    }
    return recipe

def check_chemical_exists(chemical_name):
    """Check if chemical is registered."""
    # Check our registered gases
    registered = [
        # Basic elements
        "hydrogen", "oxygen", "nitrogen", "chlorine", "fluorine",
        "xenon", "krypton", "argon", "neon", "helium", "radon",
        # Common compounds
        "water_vapor", "ammonia", "methane", "hydrogen_sulfide",
        "carbon_monoxide", "carbon_dioxide", "sulfur_dioxide",
        "sulfur_trioxide", "nitric_oxide", "nitrogen_dioxide",
        # Acids
        "hydrogen_chloride", "hydrogen_fluoride", "sulfuric_acid", "nitric_acid",
        # Hydrocarbons
        "ethene"
    ]

    # Chemicals that need registration
    pending = [
        "nitrous_oxide", "hydrogen_peroxide", "hydrazine", "ozone",
        "carbon_disulfide", "carbonyl_sulfide",
        "xenon_difluoride", "xenon_tetrafluoride", "krypton_difluoride",
        "phosphine", "phosphorus_trichloride", "phosphorus",
        "borane", "boron_trifluoride", "boron",
        "silicon_tetrafluoride", "silicon_tetrachloride", "silicon",
        "chlorine_trifluoride", "iodine_pentafluoride", "iodine"
    ]

    if chemical_name in registered:
        return True, "Registered"
    elif chemical_name in pending:
        return False, "Pending registration"
    else:
        return False, "Unknown chemical"

def main():
    """Generate the third batch of infuser recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    working_count = 0
    pending_count = 0

    print("Generating Batch 3: Chemical Infuser Recipes")
    print("=" * 60)

    for recipe_data in BATCH_3_RECIPES:
        recipe_name = recipe_data["name"]

        # Check if all chemicals exist
        left_exists, _ = check_chemical_exists(recipe_data["left_input"][0])
        right_exists, _ = check_chemical_exists(recipe_data["right_input"][0])
        output_exists, _ = check_chemical_exists(recipe_data["output"][0])

        all_exist = left_exists and right_exists and output_exists

        recipe_json = create_infuser_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        if all_exist:
            print(f"  ✅ Created {recipe_name}.json")
            working_count += 1
        else:
            print(f"  ⚠️  Created {recipe_name}.json (pending chemicals)")
            pending_count += 1

        if "note" in recipe_data:
            print(f"     Note: {recipe_data['note']}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")
    print(f"  - Should work immediately: {working_count}")
    print(f"  - Pending chemical registration: {pending_count}")

    print("\n📊 Recipe categories:")
    print("  - Acid anhydrides: 2")
    print("  - Nitrogen compounds: 2")
    print("  - Hydrogen compounds: 2")
    print("  - Carbon-sulfur compounds: 2")
    print("  - Ozone chemistry: 1")
    print("  - Noble gas fluorides: 3")
    print("  - Phosphorus compounds: 2")
    print("  - Boron compounds: 2")
    print("  - Silicon compounds: 2")
    print("  - Mixed halogens: 2")

if __name__ == "__main__":
    main()