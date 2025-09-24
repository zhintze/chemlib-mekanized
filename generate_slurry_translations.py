#!/usr/bin/env python3
"""
Generate translation entries for all ChemLib slurries.
"""

import json

# All our custom slurries (excluding Mekanism duplicates)
CHEMLIB_SLURRIES = [
    # Common metals
    ("aluminum", "Aluminum"),
    ("titanium", "Titanium"),
    ("zinc", "Zinc"),
    ("nickel", "Nickel"),
    ("silver", "Silver"),
    ("platinum", "Platinum"),

    # Rare metals
    ("tungsten", "Tungsten"),
    ("chromium", "Chromium"),
    ("manganese", "Manganese"),
    ("cobalt", "Cobalt"),
    ("cadmium", "Cadmium"),
    ("mercury", "Mercury"),

    # Precious metals
    ("palladium", "Palladium"),
    ("rhodium", "Rhodium"),
    ("iridium", "Iridium"),
    ("ruthenium", "Ruthenium"),

    # Radioactive
    ("thorium", "Thorium"),

    # Alkali/Alkaline metals
    ("lithium", "Lithium"),
    ("sodium", "Sodium"),
    ("potassium", "Potassium"),
    ("calcium", "Calcium"),
    ("magnesium", "Magnesium"),
    ("barium", "Barium"),
    ("strontium", "Strontium"),

    # Metalloids
    ("silicon", "Silicon"),
    ("germanium", "Germanium"),
    ("antimony", "Antimony"),
    ("bismuth", "Bismuth"),

    # Lanthanides
    ("cerium", "Cerium"),
    ("neodymium", "Neodymium"),
    ("lanthanum", "Lanthanum"),
    ("gadolinium", "Gadolinium"),
    ("europium", "Europium"),

    # Other metals
    ("indium", "Indium"),
    ("gallium", "Gallium"),
    ("hafnium", "Hafnium"),
    ("tantalum", "Tantalum"),
    ("rhenium", "Rhenium"),
    ("molybdenum", "Molybdenum"),
    ("vanadium", "Vanadium"),
    ("niobium", "Niobium"),
    ("beryllium", "Beryllium"),
    ("zirconium", "Zirconium"),
    ("scandium", "Scandium"),
    ("yttrium", "Yttrium"),
    ("thallium", "Thallium"),
    ("polonium", "Polonium"),
    ("technetium", "Technetium"),
    ("rubidium", "Rubidium"),
    ("cesium", "Cesium"),
    ("francium", "Francium"),
    ("radium", "Radium"),

    # Additional metalloids
    ("arsenic", "Arsenic"),
    ("tellurium", "Tellurium"),
    ("boron", "Boron"),
    ("astatine", "Astatine"),

    # Alternative spellings
    ("aluminium", "Aluminium"),
    ("gallium_arsenide", "Gallium Arsenide"),

    # Actinides
    ("actinium", "Actinium"),
    ("protactinium", "Protactinium"),
    ("neptunium", "Neptunium"),
    ("plutonium", "Plutonium"),
    ("americium", "Americium"),
    ("curium", "Curium"),
    ("berkelium", "Berkelium"),
    ("californium", "Californium"),

    # Alternative names
    ("wolfram", "Wolfram"),

    # Special materials
    ("quartz", "Quartz"),
    ("lapis", "Lapis"),
    ("coal", "Coal"),
    ("netherite_scrap", "Netherite Scrap"),
    ("emerald", "Emerald")
]

def main():
    """Generate translation entries for slurries."""

    translations = {}

    # Generate translations for each slurry
    for slurry_id, slurry_name in CHEMLIB_SLURRIES:
        # Dirty slurry
        dirty_key = f"chemical.chemlibmekanized.dirty_{slurry_id}"
        translations[dirty_key] = f"Dirty {slurry_name} Slurry"

        # Clean slurry
        clean_key = f"chemical.chemlibmekanized.clean_{slurry_id}"
        translations[clean_key] = f"Clean {slurry_name} Slurry"

    # Read existing language file
    lang_file = "src/main/resources/assets/chemlibmekanized/lang/en_us.json"
    with open(lang_file, 'r') as f:
        existing = json.load(f)

    # Add our translations
    existing.update(translations)

    # Sort keys for better organization
    sorted_translations = dict(sorted(existing.items()))

    # Write back
    with open(lang_file, 'w') as f:
        json.dump(sorted_translations, f, indent=2, ensure_ascii=False)

    print(f"Added {len(translations)} slurry translations")
    print(f"Total translations: {len(sorted_translations)}")

if __name__ == "__main__":
    main()