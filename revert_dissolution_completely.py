#!/usr/bin/env python3
import json
import os
from pathlib import Path

def revert_dissolution_to_working_state(file_path):
    """Revert dissolution recipes to the state before we added per_tick_usage"""
    try:
        with open(file_path, 'r') as f:
            recipe = json.load(f)

        # Skip if not a dissolution recipe
        if recipe.get('type') != 'mekanism:dissolution':
            return False

        modified = False

        # Remove per_tick_usage - this was added when things broke
        if 'per_tick_usage' in recipe:
            del recipe['per_tick_usage']
            modified = True
            print(f"Removed per_tick_usage from: {file_path.name}")

        # Ensure amounts are back to 10mb/20mb (not decimals or small values)
        if 'chemical_input' in recipe and 'chemical' in recipe['chemical_input']:
            chemical = recipe['chemical_input']['chemical']
            current_amount = recipe['chemical_input'].get('amount', 0)

            # Reset to intended amounts
            if chemical == 'mekanism:sulfuric_acid':
                if current_amount != 20:
                    recipe['chemical_input']['amount'] = 20
                    modified = True
                    print(f"Reset {file_path.name}: sulfuric_acid to 20mb")
            elif chemical in ['chemlibmekanized:nitric_acid',
                            'mekanism:hydrochloric_acid',
                            'chemlibmekanized:hydrochloric_acid',
                            'mekanism:hydrogen_chloride']:
                if current_amount != 10:
                    recipe['chemical_input']['amount'] = 10
                    modified = True
                    print(f"Reset {file_path.name}: {chemical.split(':')[1]} to 10mb")

        if modified:
            with open(file_path, 'w') as f:
                json.dump(recipe, f, indent=2)
                f.write('\n')
            return True

        return False
    except Exception as e:
        print(f"Error processing {file_path}: {e}")
        return False

def main():
    # Base directory for dissolution recipes
    base_dir = Path('src/main/resources/data/chemlibmekanized/recipe/dissolution')

    if not base_dir.exists():
        print(f"Directory not found: {base_dir}")
        return

    fixed_count = 0
    total_count = 0

    # Process all JSON files in the dissolution directory and subdirectories
    for json_file in base_dir.rglob('*.json'):
        total_count += 1
        if revert_dissolution_to_working_state(json_file):
            fixed_count += 1

    print(f"\n=== Summary ===")
    print(f"Total files processed: {total_count}")
    print(f"Files reverted: {fixed_count}")
    print(f"Files unchanged: {total_count - fixed_count}")

if __name__ == '__main__':
    main()