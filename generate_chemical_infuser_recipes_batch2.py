#!/usr/bin/env python3
"""
Generate Batch 2 of Chemical Infuser recipes - Advanced Compounds.
Focus on scientifically accurate reactions using only available chemicals.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/chemical_infusing"

# Batch 2: Advanced Compounds using only available chemicals
BATCH_2_RECIPES = [
    # === Noble Gas Compounds (using available noble gases) ===
    {
        "name": "xenon_difluoride",
        "left": ("xenon", 1),
        "right": ("fluorine", 2),
        "output": ("xenon_difluoride", 1),
        "note": "XeF2 - Requires UV light or electrical discharge"
    },

    {
        "name": "krypton_difluoride",
        "left": ("krypton", 1),
        "right": ("fluorine", 2),
        "output": ("krypton_difluoride", 1),
        "note": "KrF2 - Only stable at very low temperatures"
    },

    {
        "name": "radon_difluoride",
        "left": ("radon", 1),
        "right": ("fluorine", 2),
        "output": ("radon_difluoride", 1),
        "note": "RnF2 - Only stable radon compound"
    },

    # === Advanced Organic Reactions ===
    {
        "name": "hydrogen_cyanide_formation",
        "left": ("methane", 1),
        "right": ("ammonia", 1),
        "output": ("hydrogen_cyanide", 1),
        "note": "HCN - Andrussow process (simplified)"
    },

    {
        "name": "formaldehyde_formation",
        "left": ("methane", 1),
        "right": ("oxygen", 1),
        "output": ("formaldehyde", 1),
        "note": "CH2O - Partial oxidation of methane"
    },

    # === Chlorine/Fluorine Compounds ===
    {
        "name": "chlorine_monoxide_formation",
        "left": ("chlorine", 1),
        "right": ("oxygen", 1),
        "output": ("chlorine_monoxide", 1),
        "note": "Cl2O - Dichlorine monoxide"
    },

    {
        "name": "chlorine_dioxide_formation",
        "left": ("chlorine", 2),
        "right": ("oxygen", 2),
        "output": ("chlorine_dioxide", 2),
        "note": "ClO2 - Powerful oxidizer"
    },

    {
        "name": "phosgene_formation",
        "left": ("carbon_monoxide", 1),
        "right": ("chlorine", 1),
        "output": ("phosgene", 1),
        "note": "COCl2 - Industrial chemical (very toxic!)"
    },

    {
        "name": "chlorine_trifluoride_formation",
        "left": ("chlorine", 1),
        "right": ("fluorine", 3),
        "output": ("chlorine_trifluoride", 1),
        "note": "ClF3 - Extremely reactive oxidizer"
    },

    # === Nitrogen Oxides ===
    {
        "name": "nitrous_oxide_formation",
        "left": ("nitrogen", 2),
        "right": ("oxygen", 1),
        "output": ("nitrous_oxide", 1),
        "note": "N2O - Laughing gas"
    },

    {
        "name": "nitrogen_trioxide_formation",
        "left": ("nitric_oxide", 1),
        "right": ("nitrogen_dioxide", 1),
        "output": ("nitrogen_trioxide", 1),
        "note": "N2O3 - Dinitrogen trioxide"
    },

    {
        "name": "dinitrogen_tetroxide_formation",
        "left": ("nitrogen_dioxide", 2),
        "right": ("nitrogen_dioxide", 1),  # Dimerization requires pressure/cooling
        "output": ("dinitrogen_tetroxide", 1),
        "note": "N2O4 - Rocket oxidizer (2NO2 → N2O4)"
    },

    # === Hydrogen Compounds ===
    {
        "name": "hydrogen_peroxide_formation",
        "left": ("hydrogen", 2),
        "right": ("oxygen", 2),
        "output": ("hydrogen_peroxide", 1),
        "note": "H2O2 - Oxidizer and disinfectant"
    },

    {
        "name": "ozone_formation",
        "left": ("oxygen", 3),
        "right": ("oxygen", 1),  # Catalyst/energy
        "output": ("ozone", 2),
        "note": "3O2 -> 2O3 with electrical discharge"
    },

    # === Sulfur-Carbon Compounds (using carbon infusion) ===
    {
        "name": "carbon_oxysulfide_formation",
        "left": ("carbon_monoxide", 1),
        "right": ("sulfur_dioxide", 1),
        "output": ("carbon_oxysulfide", 1),
        "note": "COS - Most abundant sulfur compound in atmosphere"
    },

    # === Ethene/Alkene Reactions ===
    {
        "name": "vinyl_chloride_formation",
        "left": ("ethylene", 1),
        "right": ("hydrogen_chloride", 1),
        "output": ("vinyl_chloride", 1),
        "note": "C2H3Cl - PVC precursor"
    },

    {
        "name": "ethylene_oxide_formation",
        "left": ("ethene", 1),  # Using Mekanism's ethene
        "right": ("oxygen", 1),
        "output": ("ethylene_oxide", 1),
        "note": "C2H4O - Important industrial chemical"
    },

    # === More Complex Hydrocarbons ===
    {
        "name": "benzene_formation",
        "left": ("acetylene", 3),
        "right": ("carbon", 1),  # Catalyst
        "output": ("benzene", 1),
        "note": "C6H6 - Aromatic ring formation"
    },

    {
        "name": "toluene_formation",
        "left": ("benzene", 1),
        "right": ("methane", 1),
        "output": ("toluene", 1),
        "note": "C7H8 - Methylbenzene"
    }
]

def check_chemical_exists(chemical_name):
    """Check if we have this chemical registered."""
    # List of chemicals we know exist in our system
    known_chemicals = [
        # From Mekanism
        "hydrogen", "oxygen", "chlorine", "fluorine", "carbon", "carbon_monoxide",
        "sulfur_dioxide", "water_vapor", "steam",
        # From our registry
        "nitrogen", "ammonia", "methane", "carbon_dioxide", "nitric_oxide",
        "xenon", "krypton", "radon", "helium", "neon", "argon",
        # New chemicals we'd need to register
        "phosphorus", "silicon", "boron", "sulfur", "nickel", "arsenic",
        "germanium", "selenium", "tellurium"
    ]
    return chemical_name.lower() in known_chemicals

def create_chemical_infuser_recipe(recipe_data):
    """Create a chemical infuser recipe JSON."""
    # Handle special case where right input has 0 amount (catalyst)
    right_amount = recipe_data["right"][1]
    if right_amount == 0:
        right_amount = 1  # Minimum amount for catalyst reactions

    recipe = {
        "type": "mekanism:chemical_infusing",
        "left_input": {
            "amount": recipe_data["left"][1] * 1000,
            "chemical": get_chemical_id(recipe_data["left"][0])
        },
        "right_input": {
            "amount": right_amount * 1000,
            "chemical": get_chemical_id(recipe_data["right"][0])
        },
        "output": {
            "amount": recipe_data["output"][1] * 1000,
            "id": get_chemical_id(recipe_data["output"][0])
        }
    }
    return recipe

def main():
    """Generate the second batch of chemical infuser recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    skipped_count = 0
    missing_chemicals = set()

    print("Generating Batch 2: Advanced Chemical Compounds")
    print("=" * 60)

    # Filter recipes to only those we can actually implement
    implementable_recipes = []

    for recipe_data in BATCH_2_RECIPES:
        # Check if input chemicals exist
        missing = []
        for chemical, _ in [recipe_data["left"], recipe_data["right"]]:
            if not check_chemical_exists(chemical):
                missing.append(chemical)
                missing_chemicals.add(chemical)

        if not missing:
            # For now, assume output chemical will be registered
            implementable_recipes.append(recipe_data)
        else:
            print(f"  ⏭️  Skipping {recipe_data['name']} - missing: {', '.join(missing)}")
            skipped_count += 1

    # Generate the implementable recipes
    for recipe_data in implementable_recipes:
        recipe_name = recipe_data["name"]

        # Skip ozone for now as it needs special handling
        if recipe_name == "ozone_formation":
            print(f"  ⏭️  Skipping {recipe_name} - needs special catalyst handling")
            skipped_count += 1
            continue

        recipe_json = create_chemical_infuser_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ Created {recipe_name}.json")
        if "note" in recipe_data:
            print(f"     Note: {recipe_data['note']}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes, Skipped {skipped_count}")

    if missing_chemicals:
        print(f"\n⚠️  Chemicals needed to be registered for full implementation:")
        for chemical in sorted(missing_chemicals):
            print(f"  • {chemical}")

    print("\n📝 Note: Many of these reactions require specific conditions:")
    print("   - High temperature/pressure")
    print("   - Catalysts")
    print("   - UV light or electrical discharge")
    print("   These are simplified for gameplay purposes.")

if __name__ == "__main__":
    main()