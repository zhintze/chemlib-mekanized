#!/usr/bin/env python3
"""
Extract element colors from ChemLib data and update slurry colors to match.
"""

import json
import re

# Path to ChemLib element data
CHEMLIB_ELEMENTS_PATH = "/home/keroppi/Development/Minecraft/ChemLib/src/main/resources/data/chemlib/elements.json"

# Elements that we have slurries for (excluding Mekanism duplicates)
SLURRY_ELEMENTS = [
    "aluminum", "titanium", "zinc", "nickel", "silver", "platinum",
    "tungsten", "chromium", "manganese", "cobalt", "cadmium", "mercury",
    "palladium", "rhodium", "iridium", "ruthenium", "thorium",
    "lithium", "sodium", "potassium", "calcium", "magnesium", "barium", "strontium",
    "silicon", "germanium", "antimony", "bismuth",
    "cerium", "neodymium", "lanthanum", "gadolinium", "europium",
    "indium", "gallium", "hafnium", "tantalum", "rhenium", "molybdenum",
    "vanadium", "niobium", "beryllium", "zirconium", "scandium", "yttrium",
    "thallium", "polonium", "technetium", "rubidium", "cesium", "francium", "radium",
    "arsenic", "tellurium", "boron", "astatine",
    "actinium", "protactinium", "neptunium", "plutonium",
    "americium", "curium", "berkelium", "californium"
]

def extract_element_colors():
    """Extract element colors from ChemLib data."""
    with open(CHEMLIB_ELEMENTS_PATH, 'r') as f:
        data = json.load(f)

    elements = data['elements']  # Elements are in an "elements" array

    color_map = {}
    for element in elements:
        name = element['name']
        if name in SLURRY_ELEMENTS:
            # ChemLib colors are hex strings without the 0x prefix
            color_str = element.get('color', 'FFFFFF')
            # Convert to integer with alpha channel
            color = int(color_str, 16) | 0xFF000000
            color_map[name] = f"0x{color:08X}"

    return color_map

def update_slurries_java(color_map):
    """Update ChemLibSlurries.java with correct colors."""
    file_path = "src/main/java/com/hecookin/chemlibmekanized/registry/ChemLibSlurries.java"

    with open(file_path, 'r') as f:
        content = f.read()

    # Update each element's color in the enum
    for element_name, new_color in color_map.items():
        # Handle special cases
        if element_name == "aluminum":
            # Match both ALUMINUM and aluminum in quotes
            patterns = [
                (r'ALUMINUM\("aluminum", 0x[0-9A-Fa-f]+\)', f'ALUMINUM("aluminum", {new_color})'),
                (r'ALUMINUM_ALT\("aluminium", 0x[0-9A-Fa-f]+\)', f'ALUMINUM_ALT("aluminium", {new_color})')
            ]
        elif element_name == "tungsten":
            patterns = [
                (r'TUNGSTEN\("tungsten", 0x[0-9A-Fa-f]+\)', f'TUNGSTEN("tungsten", {new_color})'),
                (r'TUNGSTEN_ALT\("wolfram", 0x[0-9A-Fa-f]+\)', f'TUNGSTEN_ALT("wolfram", {new_color})')
            ]
        else:
            # Standard pattern
            enum_name = element_name.upper()
            patterns = [(f'{enum_name}\\("{element_name}", 0x[0-9A-Fa-f]+\\)',
                        f'{enum_name}("{element_name}", {new_color})')]

        for pattern, replacement in patterns:
            content = re.sub(pattern, replacement, content, flags=re.IGNORECASE)

    with open(file_path, 'w') as f:
        f.write(content)

    print(f"Updated {len(color_map)} element colors in ChemLibSlurries.java")

def main():
    print("Extracting element colors from ChemLib...")
    color_map = extract_element_colors()

    print(f"Found colors for {len(color_map)} elements")

    # Show some examples
    print("\nSample colors:")
    for element in ["aluminum", "titanium", "zinc", "nickel", "silver"][:5]:
        if element in color_map:
            print(f"  {element}: {color_map[element]}")

    print("\nUpdating ChemLibSlurries.java...")
    update_slurries_java(color_map)

    print("\n✅ Slurry colors updated to match ChemLib elements")

if __name__ == "__main__":
    main()