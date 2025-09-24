#!/usr/bin/env python3
"""
Generate item models for metal crystals using layered textures.
Uses base + overlay for color tinting like Mekanism.
"""

import json
import os

# All metals/metalloids that have crystals
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

def create_crystal_model(metal):
    """
    Create a layered crystal model with base and overlay.
    Layer 0 (base) gets tinted with the metal color.
    Layer 1 (overlay) stays white for highlights.
    """
    return {
        "parent": "minecraft:item/generated",
        "textures": {
            "layer0": "chemlibmekanized:item/crystal_base",
            "layer1": "chemlibmekanized:item/crystal_overlay"
        }
    }

def main():
    # Create output directory
    output_dir = "src/main/resources/assets/chemlibmekanized/models/item"
    os.makedirs(output_dir, exist_ok=True)

    # Generate crystal models
    for metal in METALS:
        model = create_crystal_model(metal)

        # Write model file
        output_path = os.path.join(output_dir, f"{metal}_crystal.json")
        with open(output_path, 'w') as f:
            json.dump(model, f, indent=2)

    print(f"Generated {len(METALS)} crystal item models")
    print("Using layered texture system: crystal_base (tinted) + crystal_overlay (white)")

if __name__ == "__main__":
    main()