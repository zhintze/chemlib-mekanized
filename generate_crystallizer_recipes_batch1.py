#!/usr/bin/env python3
"""
Generate Batch 1 of Chemical Crystallizer recipes.
Focus on converting chemicals back into useful solid items.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/crystallizing"

# Batch 1: Basic Chemical to Item Conversions
BATCH_1_RECIPES = [
    # === Carbon Infusion to Items ===
    {
        "name": "carbon_to_coal",
        "chemical_type": "infusion",
        "input": ("carbon", 16),  # Match our dissolution output
        "output": "minecraft:coal",
        "note": "Carbon back to coal"
    },

    {
        "name": "carbon_to_charcoal",
        "chemical_type": "infusion",
        "input": ("carbon", 16),  # Alternative output
        "output": "minecraft:charcoal",
        "note": "Carbon to charcoal (alternative)"
    },

    {
        "name": "carbon_to_black_dye",
        "chemical_type": "infusion",
        "input": ("carbon", 4),  # Small amount for dye
        "output": "minecraft:black_dye",
        "note": "Carbon black pigment"
    },

    {
        "name": "large_carbon_to_diamond",
        "chemical_type": "infusion",
        "input": ("carbon", 256),  # Match diamond dissolution
        "output": "minecraft:diamond",
        "note": "Compressed carbon to diamond"
    },

    {
        "name": "carbon_to_coal_block",
        "chemical_type": "infusion",
        "input": ("carbon", 144),  # Match coal block dissolution
        "output": "minecraft:coal_block",
        "note": "Large carbon to coal block"
    },

    # === Redstone Infusion ===
    {
        "name": "redstone_infusion_to_dust",
        "chemical_type": "infusion",
        "input": ("redstone", 1),
        "output": "minecraft:redstone",
        "note": "Redstone infusion to dust"
    },

    {
        "name": "redstone_infusion_to_block",
        "chemical_type": "infusion",
        "input": ("redstone", 9),
        "output": "minecraft:redstone_block",
        "note": "Redstone infusion to block"
    },

    # === Metal Slurries (using Mekanism's slurries) ===
    {
        "name": "dirty_iron_to_raw_iron",
        "chemical_type": "slurry",
        "input": ("dirty_iron", 2),  # 2000mB for 1 raw ore
        "output": "minecraft:raw_iron",
        "note": "Iron slurry to raw iron"
    },

    {
        "name": "dirty_gold_to_raw_gold",
        "chemical_type": "slurry",
        "input": ("dirty_gold", 2),
        "output": "minecraft:raw_gold",
        "note": "Gold slurry to raw gold"
    },

    {
        "name": "dirty_copper_to_raw_copper",
        "chemical_type": "slurry",
        "input": ("dirty_copper", 3),  # Match dissolution ratio
        "output": "minecraft:raw_copper",
        "note": "Copper slurry to raw copper"
    },

    # === Noble Gas Compounds ===
    {
        "name": "xenon_to_glowstone_dust",
        "chemical_type": "gas",
        "input": ("xenon", 1),
        "output": "minecraft:glowstone_dust",
        "note": "Xenon gas luminescence"
    },

    {
        "name": "krypton_to_prismarine_crystals",
        "chemical_type": "gas",
        "input": ("krypton", 2),
        "output": "minecraft:prismarine_crystals",
        "note": "Krypton crystallization"
    },

    {
        "name": "neon_to_orange_dye",
        "chemical_type": "gas",
        "input": ("neon", 1),
        "output": "minecraft:orange_dye",
        "note": "Neon color extraction"
    },

    {
        "name": "argon_to_purple_dye",
        "chemical_type": "gas",
        "input": ("argon", 1),
        "output": "minecraft:purple_dye",
        "note": "Argon color extraction"
    },

    # === Compound Gases ===
    {
        "name": "ammonia_to_bone_meal",
        "chemical_type": "gas",
        "input": ("ammonia", 2),
        "output": "minecraft:bone_meal",
        "note": "Ammonia as fertilizer"
    },

    {
        "name": "hydrogen_sulfide_to_gunpowder",
        "chemical_type": "gas",
        "input": ("hydrogen_sulfide", 4),
        "output": "minecraft:gunpowder",
        "note": "Sulfur compound to gunpowder"
    },

    # === Basic Elements ===
    {
        "name": "nitrogen_to_packed_ice",
        "chemical_type": "gas",
        "input": ("nitrogen", 8),
        "output": "minecraft:packed_ice",
        "note": "Liquid nitrogen freezing"
    }
]

def create_crystallizer_recipe(recipe_data):
    """Create a chemical crystallizer recipe JSON."""
    recipe = {
        "type": "mekanism:crystallizing",
        "chemical_type": recipe_data["chemical_type"],
        "input": {
            "amount": recipe_data["input"][1] * 1000,  # Convert to mB
            "chemical": get_chemical_id(recipe_data["input"][0])
        },
        "output": {
            "id": recipe_data["output"]  # Changed from "item" to "id"
        }
    }

    # Add count if specified
    if "count" in recipe_data:
        recipe["output"]["count"] = recipe_data["count"]

    return recipe

def check_chemical_exists(chemical_name, chemical_type):
    """Check if input chemical is registered."""
    if chemical_type == "infusion":
        # These infusions exist in Mekanism
        if chemical_name in ["carbon", "redstone", "diamond", "gold", "tin", "bio", "fungi"]:
            return True, "Mekanism infusion"
    elif chemical_type == "slurry":
        # These slurries exist in Mekanism
        if chemical_name in ["dirty_iron", "dirty_gold", "dirty_copper", "dirty_tin", "dirty_lead", "dirty_osmium", "dirty_uranium"]:
            return True, "Mekanism slurry"
    elif chemical_type == "gas":
        # Check our registered gases
        if chemical_name in ["hydrogen", "oxygen", "nitrogen", "chlorine", "fluorine",
                             "xenon", "krypton", "argon", "neon", "helium", "radon",
                             "ammonia", "methane", "hydrogen_sulfide", "carbon_monoxide",
                             "nitric_oxide", "carbon_dioxide"]:
            return True, "Registered gas"

    return False, f"Chemical {chemical_name} may not be registered"

def main():
    """Generate the first batch of crystallizer recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0
    working_count = 0
    issues = []

    print("Generating Batch 1: Chemical Crystallizer Recipes")
    print("=" * 60)

    for recipe_data in BATCH_1_RECIPES:
        recipe_name = recipe_data["name"]
        chemical_name = recipe_data["input"][0]
        chemical_type = recipe_data["chemical_type"]

        # Check if chemical exists
        exists, reason = check_chemical_exists(chemical_name, chemical_type)

        recipe_json = create_crystallizer_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        if exists:
            print(f"  ✅ Created {recipe_name}.json")
            working_count += 1
        else:
            print(f"  ⚠️  Created {recipe_name}.json ({reason})")
            issues.append((recipe_name, reason))

        if "note" in recipe_data:
            print(f"     Note: {recipe_data['note']}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")
    print(f"  - Should work: {working_count}")
    print(f"  - May have issues: {len(issues)}")

    if issues:
        print("\n⚠️  Potential issues:")
        for name, reason in issues:
            print(f"  - {name}: {reason}")

if __name__ == "__main__":
    main()