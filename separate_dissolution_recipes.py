#!/usr/bin/env python3
import json
import os
import shutil
from pathlib import Path

def analyze_and_separate_dissolution_recipes():
    """Separate dissolution recipes into valid (slurry) and invalid (chemical) ones"""

    base_dir = Path('src/main/resources/data/chemlibmekanized/recipe/dissolution')

    if not base_dir.exists():
        print(f"Directory not found: {base_dir}")
        return

    valid_slurry = []
    invalid_chemical = []
    other_outputs = []

    # Analyze all dissolution recipes
    for json_file in base_dir.rglob('*.json'):
        try:
            with open(json_file, 'r') as f:
                recipe = json.load(f)

            if recipe.get('type') != 'mekanism:dissolution':
                continue

            if 'output' in recipe and 'id' in recipe['output']:
                output_id = recipe['output']['id']

                if 'dirty_' in output_id or 'clean_' in output_id:
                    valid_slurry.append(str(json_file))
                elif 'slurry_' in output_id:
                    # These might be custom slurries
                    valid_slurry.append(str(json_file))
                elif output_id.startswith('chemlibmekanized:') or output_id.startswith('mekanism:'):
                    # These are chemicals, not slurries - invalid for dissolution
                    invalid_chemical.append((str(json_file), output_id))
                else:
                    other_outputs.append((str(json_file), output_id))

        except Exception as e:
            print(f"Error reading {json_file}: {e}")

    print("=== ANALYSIS RESULTS ===\n")
    print(f"Valid slurry dissolution recipes: {len(valid_slurry)}")
    print(f"Invalid chemical dissolution recipes: {len(invalid_chemical)}")
    print(f"Other outputs: {len(other_outputs)}")

    if invalid_chemical:
        print("\n=== INVALID CHEMICAL OUTPUTS (Cannot use dissolution) ===")
        # Group by chemical type
        chemicals = {}
        for file_path, output_id in invalid_chemical:
            if output_id not in chemicals:
                chemicals[output_id] = []
            chemicals[output_id].append(file_path)

        for chemical, files in sorted(chemicals.items()):
            print(f"\n{chemical}: {len(files)} recipes")
            if len(files) <= 3:
                for f in files:
                    print(f"  - {Path(f).name}")

    print("\n=== RECOMMENDATION ===")
    print("The Chemical Dissolution Chamber can ONLY output slurries (dirty_* or clean_*).")
    print("Recipes trying to output regular chemicals should be:")
    print("1. Removed (if they're for converting items to chemicals)")
    print("2. Converted to Chemical Oxidizer recipes (for item -> gas conversion)")
    print("3. Or use a different machine entirely")

    return invalid_chemical

if __name__ == '__main__':
    invalid = analyze_and_separate_dissolution_recipes()