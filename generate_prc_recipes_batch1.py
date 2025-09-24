#!/usr/bin/env python3
"""
Generate Batch 1 of Pressurized Reaction Chamber recipes.
Focus on practical industrial chemical reactions using registered chemicals.
"""

import json
import os
from chemical_mappings import get_chemical_id

# Directory for recipes
RECIPE_DIR = "src/main/resources/data/chemlibmekanized/recipe/reaction"

# Batch 1: Basic Industrial Reactions
BATCH_1_RECIPES = [
    # === Water-Based Reactions ===
    {
        "name": "steam_coal_gasification",
        "item_input": ("minecraft:coal", 1),
        "fluid_input": ("water", 1000),  # Use tag, will be converted
        "energy": 2000.0,
        "duration": 200,
        "chemical_output": ("carbon_monoxide", 2000),
        "note": "Coal + H2O → CO + H2 (water-gas shift)"
    },

    {
        "name": "limestone_decomposition",
        "item_input": ("minecraft:calcite", 1),
        "chemical_input": ("sulfuric_acid", 100),
        "energy": 1000.0,
        "duration": 100,
        "item_output": ("minecraft:bone_meal", 1),
        "chemical_output": ("carbon_dioxide", 1000),
        "note": "CaCO3 + H2SO4 → CaSO4 + CO2 + H2O"
    },

    # === Metal Processing ===
    {
        "name": "iron_sulfide_roasting",
        "item_input": ("minecraft:raw_iron", 1),
        "chemical_input": ("oxygen", 2000),
        "energy": 3000.0,
        "duration": 300,
        "item_output": ("minecraft:iron_ingot", 1),
        "chemical_output": ("sulfur_dioxide", 1000),
        "note": "Roasting iron ore with oxygen"
    },

    {
        "name": "copper_oxide_reduction",
        "item_input": ("minecraft:raw_copper", 2),
        "chemical_input": ("hydrogen", 1000),
        "energy": 2000.0,
        "duration": 200,
        "item_output": ("minecraft:copper_ingot", 2),
        "fluid_output": ("water", 500),
        "note": "CuO + H2 → Cu + H2O"
    },

    # === Organic Reactions ===
    {
        "name": "sugar_fermentation",
        "item_input": ("minecraft:sugar", 2),
        "fluid_input": ("water", 500),
        "energy": 500.0,
        "duration": 400,
        "chemical_output": ("ethene", 1000),
        "note": "Sugar fermentation to ethylene"
    },

    {
        "name": "biomass_gasification",
        "item_input": ("minecraft:oak_leaves", 8),
        "chemical_input": ("oxygen", 500),
        "energy": 1000.0,
        "duration": 200,
        "chemical_output": ("methane", 500),
        "item_output": ("minecraft:charcoal", 1),
        "note": "Partial oxidation of biomass"
    },

    # === Ammonia Production ===
    {
        "name": "haber_process",
        "chemical_input": ("nitrogen", 1000),
        "fluid_input": ("water", 100),  # Catalyst simulation
        "energy": 5000.0,
        "duration": 500,
        "chemical_output": ("ammonia", 2000),
        "note": "N2 + 3H2 → 2NH3 (simplified)"
    },

    {
        "name": "urea_synthesis",
        "item_input": ("minecraft:bone_meal", 1),
        "chemical_input": ("ammonia", 1000),
        "energy": 1500.0,
        "duration": 150,
        "item_output": ("minecraft:white_dye", 2),
        "chemical_output": ("carbon_dioxide", 500),
        "note": "Urea synthesis from ammonia"
    },

    # === Acid-Base Reactions ===
    {
        "name": "sulfuric_acid_neutralization",
        "item_input": ("minecraft:iron_ingot", 1),
        "chemical_input": ("sulfuric_acid", 500),
        "energy": 1000.0,
        "duration": 100,
        "item_output": ("minecraft:raw_iron", 1),
        "chemical_output": ("hydrogen", 1000),
        "note": "Fe + H2SO4 → FeSO4 + H2"
    },

    {
        "name": "hydrochloric_acid_limestone",
        "item_input": ("minecraft:diorite", 1),
        "chemical_input": ("hydrogen_chloride", 200),
        "energy": 800.0,
        "duration": 80,
        "item_output": ("minecraft:quartz", 1),
        "chemical_output": ("carbon_dioxide", 500),
        "note": "Acid dissolution of carbonate minerals"
    },

    # === Oxidation Reactions ===
    {
        "name": "sulfur_oxidation",
        "item_input": ("minecraft:gunpowder", 1),
        "chemical_input": ("oxygen", 1500),
        "energy": 2000.0,
        "duration": 150,
        "chemical_output": ("sulfur_dioxide", 1000),
        "item_output": ("minecraft:charcoal", 1),
        "note": "Oxidation of sulfur compounds"
    },

    {
        "name": "phosphorus_oxidation",
        "item_input": ("minecraft:bone_block", 1),
        "chemical_input": ("oxygen", 2000),
        "energy": 2500.0,
        "duration": 250,
        "item_output": ("minecraft:bone_meal", 9),
        "chemical_output": ("carbon_dioxide", 500),
        "note": "Oxidation of organic phosphates"
    },

    # === Reduction Reactions ===
    {
        "name": "carbon_reduction",
        "item_input": ("minecraft:charcoal", 1),
        "chemical_input": ("carbon_dioxide", 1000),
        "energy": 3000.0,
        "duration": 200,
        "chemical_output": ("carbon_monoxide", 2000),
        "note": "C + CO2 → 2CO (Boudouard reaction)"
    },

    {
        "name": "hydrogen_reduction",
        "item_input": ("minecraft:blaze_powder", 1),
        "chemical_input": ("hydrogen", 500),
        "energy": 2000.0,
        "duration": 100,
        "item_output": ("minecraft:glowstone_dust", 2),
        "chemical_output": ("water_vapor", 500),
        "note": "Reduction of oxidized compounds"
    },

    # === Polymerization ===
    {
        "name": "plastic_polymerization",
        "chemical_input": ("ethene", 2000),
        "fluid_input": ("water", 100),  # Catalyst
        "energy": 4000.0,
        "duration": 400,
        "item_output": ("mekanism:hdpe_sheet", 1),
        "note": "Ethylene polymerization to HDPE"
    },

    # === Halogenation ===
    {
        "name": "chlorination_reaction",
        "item_input": ("minecraft:paper", 1),
        "chemical_input": ("chlorine", 100),
        "energy": 500.0,
        "duration": 50,
        "item_output": ("minecraft:white_dye", 1),
        "note": "Bleaching with chlorine"
    },

    {
        "name": "fluorination_reaction",
        "item_input": ("minecraft:glass", 1),
        "chemical_input": ("hydrogen_fluoride", 100),
        "energy": 1000.0,
        "duration": 100,
        "item_output": ("minecraft:glass_pane", 2),
        "note": "Glass etching with HF"
    },

    # === Special Reactions ===
    {
        "name": "nether_star_synthesis",
        "item_input": ("minecraft:diamond", 1),
        "chemical_input": ("xenon", 1000),
        "fluid_input": ("lava", 1000),
        "energy": 10000.0,
        "duration": 1000,
        "item_output": ("minecraft:nether_star", 1),
        "note": "High-energy exotic synthesis"
    },

    {
        "name": "ender_pearl_reaction",
        "item_input": ("minecraft:chorus_fruit", 1),
        "chemical_input": ("radon", 500),
        "energy": 5000.0,
        "duration": 500,
        "item_output": ("minecraft:ender_pearl", 1),
        "note": "Quantum phase shifting"
    },

    {
        "name": "glowstone_synthesis",
        "item_input": ("minecraft:redstone", 2),
        "chemical_input": ("helium", 1000),
        "energy": 3000.0,
        "duration": 300,
        "item_output": ("minecraft:glowstone_dust", 4),
        "note": "Photonic crystal formation"
    }
]

def create_prc_recipe(recipe_data):
    """Create a Pressurized Reaction Chamber recipe JSON."""
    recipe = {
        "type": "mekanism:reaction"
    }

    # Add item input if present
    if "item_input" in recipe_data:
        recipe["item_input"] = {
            "count": recipe_data["item_input"][1],
            "item": recipe_data["item_input"][0]
        }

    # Add fluid input - REQUIRED even if minimal
    if "fluid_input" in recipe_data:
        recipe["fluid_input"] = {
            "amount": recipe_data["fluid_input"][1],
            "tag": f"minecraft:{recipe_data['fluid_input'][0]}"  # Use tag for vanilla fluids
        }
    else:
        # PRC requires fluid_input, use minimal water as catalyst placeholder
        recipe["fluid_input"] = {
            "amount": 1,
            "tag": "minecraft:water"  # Use tag for minimal water
        }

    # Add chemical input if present
    if "chemical_input" in recipe_data:
        recipe["chemical_input"] = {
            "amount": recipe_data["chemical_input"][1],
            "chemical": get_chemical_id(recipe_data["chemical_input"][0])
        }

    # Add energy and duration (required)
    recipe["energy_required"] = recipe_data["energy"]
    recipe["duration"] = recipe_data["duration"]

    # Add item output if present
    if "item_output" in recipe_data:
        recipe["item_output"] = {
            "id": recipe_data["item_output"][0]
        }
        if recipe_data["item_output"][1] > 1:
            recipe["item_output"]["count"] = recipe_data["item_output"][1]

    # Add fluid output if present
    if "fluid_output" in recipe_data:
        recipe["fluid_output"] = {
            "amount": recipe_data["fluid_output"][1],
            "fluid": f"minecraft:{recipe_data['fluid_output'][0]}"  # Add minecraft: prefix
        }

    # Add chemical output if present
    if "chemical_output" in recipe_data:
        recipe["chemical_output"] = {
            "amount": recipe_data["chemical_output"][1],
            "id": get_chemical_id(recipe_data["chemical_output"][0])
        }

    return recipe

def main():
    """Generate the first batch of PRC recipes."""
    os.makedirs(RECIPE_DIR, exist_ok=True)

    created_count = 0

    print("Generating Batch 1: Pressurized Reaction Chamber Recipes")
    print("=" * 60)

    for recipe_data in BATCH_1_RECIPES:
        recipe_name = recipe_data["name"]

        recipe_json = create_prc_recipe(recipe_data)
        file_path = os.path.join(RECIPE_DIR, f"{recipe_name}.json")

        with open(file_path, 'w') as f:
            json.dump(recipe_json, f, indent=2)

        print(f"  ✅ Created {recipe_name}.json")
        if "note" in recipe_data:
            print(f"     Note: {recipe_data['note']}")
        created_count += 1

    print("=" * 60)
    print(f"Summary: Created {created_count} recipes")

    print("\n📊 Recipe categories:")
    print("  - Water-based reactions: 2")
    print("  - Metal processing: 2")
    print("  - Organic reactions: 2")
    print("  - Ammonia production: 2")
    print("  - Acid-base reactions: 2")
    print("  - Oxidation reactions: 2")
    print("  - Reduction reactions: 2")
    print("  - Polymerization: 1")
    print("  - Halogenation: 2")
    print("  - Special reactions: 3")

if __name__ == "__main__":
    main()