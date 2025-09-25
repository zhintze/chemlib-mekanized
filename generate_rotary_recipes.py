#!/usr/bin/env python3
"""
Generate rotary condensentrator recipes for gas to fluid conversion
"""

import json
import os

# Define all gas to fluid conversions
GAS_FLUIDS = [
    # Element gases
    ("nitrogen", "nitrogen"),
    ("fluorine", "fluorine"),

    # Noble gases
    ("helium", "helium"),
    ("neon", "neon"),
    ("argon", "argon"),
    ("krypton", "krypton"),
    ("xenon", "xenon"),
    ("radon", "radon"),

    # Compound gases
    ("carbon_dioxide", "carbon_dioxide"),
    ("ethylene", "ethylene"),
    ("ammonium", "ammonium"),
    ("methane", "methane"),
    ("ethane", "ethane"),
    ("propane", "propane"),
    ("butane", "butane"),
    ("nitrogen_dioxide", "nitrogen_dioxide"),
    ("ammonia", "ammonia"),
    ("hydrogen_sulfide", "hydrogen_sulfide"),
    ("acetylene", "acetylene"),
    ("carbon_monoxide", "carbon_monoxide"),
    ("nitric_oxide", "nitric_oxide"),

    # Vaporizable liquid compounds
    ("ethanol", "ethanol"),
    ("propan_1_ol", "propan_1_ol"),
    ("propan_2_ol", "propan_2_ol"),
    ("pentane", "pentane"),
    ("hexane", "hexane"),
    ("heptane", "heptane"),
    ("acetic_acid", "acetic_acid"),
    ("carbon_disulfide", "carbon_disulfide"),
]

def create_rotary_recipe(gas_name, fluid_name):
    """Create a rotary condensentrator recipe for gas <-> fluid conversion"""
    return {
        "type": "mekanism:rotary",
        "chemical_input": {
            "amount": 1,
            "chemical": f"chemlibmekanized:{gas_name}"
        },
        "chemical_output": {
            "amount": 1,
            "id": f"chemlibmekanized:{gas_name}"
        },
        "fluid_input": {
            "amount": 1,
            "fluid": f"chemlibmekanized:liquid_{fluid_name}"
        },
        "fluid_output": {
            "amount": 1,
            "id": f"chemlibmekanized:liquid_{fluid_name}"
        }
    }

def main():
    # Create the rotary recipes directory
    output_dir = "src/main/resources/data/chemlibmekanized/recipe/rotary"
    os.makedirs(output_dir, exist_ok=True)

    created_count = 0

    for gas_name, fluid_name in GAS_FLUIDS:
        # Create the recipe
        recipe = create_rotary_recipe(gas_name, fluid_name)

        # Write the recipe file
        filename = f"{output_dir}/{gas_name}.json"
        with open(filename, 'w') as f:
            json.dump(recipe, f, indent=2)

        print(f"Created rotary recipe: {gas_name}.json")
        created_count += 1

    print(f"\nTotal rotary recipes created: {created_count}")
    print(f"Location: {output_dir}/")

if __name__ == "__main__":
    main()