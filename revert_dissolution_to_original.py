#!/usr/bin/env python3
import json
import os
from pathlib import Path

def revert_dissolution_recipe(file_path):
    """Revert dissolution recipe to original format with id output"""
    try:
        with open(file_path, 'r') as f:
            recipe = json.load(f)

        # Skip if not a dissolution recipe
        if recipe.get('type') != 'mekanism:dissolution':
            return False

        modified = False

        # Remove per_tick_usage if present
        if 'per_tick_usage' in recipe:
            del recipe['per_tick_usage']
            modified = True

        # Fix chemical input amounts back to integers
        if 'chemical_input' in recipe and 'chemical' in recipe['chemical_input']:
            chemical = recipe['chemical_input']['chemical']
            current_amount = recipe['chemical_input'].get('amount', 0)

            # Set correct amounts based on acid type
            if chemical == 'mekanism:sulfuric_acid':
                if current_amount != 20:
                    recipe['chemical_input']['amount'] = 20
                    modified = True
            elif chemical in ['chemlibmekanized:nitric_acid',
                            'mekanism:hydrochloric_acid',
                            'chemlibmekanized:hydrochloric_acid',
                            'mekanism:hydrogen_chloride']:
                if current_amount != 10:
                    recipe['chemical_input']['amount'] = 10
                    modified = True

        # Revert output format back to using "id" for ALL outputs
        if 'output' in recipe:
            if 'chemical' in recipe['output']:
                # Change back from chemical to id
                chemical_id = recipe['output']['chemical']
                amount = recipe['output'].get('amount', 200)
                recipe['output'] = {
                    "amount": amount,
                    "id": chemical_id  # Change back to "id"
                }
                modified = True
                print(f"Reverted {file_path.name}: changed chemical back to id for {chemical_id}")

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
        if revert_dissolution_recipe(json_file):
            fixed_count += 1

    print(f"\n=== Summary ===")
    print(f"Total files processed: {total_count}")
    print(f"Files reverted: {fixed_count}")
    print(f"Files unchanged: {total_count - fixed_count}")

if __name__ == '__main__':
    main()