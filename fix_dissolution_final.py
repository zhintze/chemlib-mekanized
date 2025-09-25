#!/usr/bin/env python3
import json
import os
from pathlib import Path

def fix_dissolution_recipe(file_path):
    """Fix dissolution recipe to use correct amounts without per_tick_usage"""
    try:
        with open(file_path, 'r') as f:
            recipe = json.load(f)

        # Skip if not a dissolution recipe
        if recipe.get('type') != 'mekanism:dissolution':
            return False

        # Skip Mekanism ore dissolution recipes (dirty slurries)
        if 'output' in recipe and 'id' in recipe.get('output', {}):
            output_id = recipe['output']['id']
            if output_id.startswith('mekanism:dirty_'):
                print(f"Skipping Mekanism ore recipe: {file_path.name}")
                return False

        modified = False

        # Remove per_tick_usage if present
        if 'per_tick_usage' in recipe:
            del recipe['per_tick_usage']
            modified = True

        # Fix chemical input amounts
        if 'chemical_input' in recipe and 'chemical' in recipe['chemical_input']:
            chemical = recipe['chemical_input']['chemical']
            current_amount = recipe['chemical_input'].get('amount', 0)

            # Set correct amounts based on acid type
            if chemical == 'mekanism:sulfuric_acid':
                if current_amount != 20:
                    recipe['chemical_input']['amount'] = 20
                    modified = True
                    print(f"Fixed {file_path.name}: sulfuric_acid to 20mb")
            elif chemical in ['chemlibmekanized:nitric_acid',
                            'mekanism:hydrochloric_acid',
                            'chemlibmekanized:hydrochloric_acid',
                            'mekanism:hydrogen_chloride']:
                if current_amount != 10:
                    recipe['chemical_input']['amount'] = 10
                    modified = True
                    print(f"Fixed {file_path.name}: {chemical.split(':')[1]} to 10mb")

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
    skipped_count = 0

    # Process all JSON files in the dissolution directory and subdirectories
    for json_file in base_dir.rglob('*.json'):
        total_count += 1
        result = fix_dissolution_recipe(json_file)
        if result is True:
            fixed_count += 1
        elif 'dirty_' in str(json_file):
            skipped_count += 1

    print(f"\n=== Summary ===")
    print(f"Total files processed: {total_count}")
    print(f"Files fixed: {fixed_count}")
    print(f"Mekanism ore recipes skipped: {skipped_count}")

if __name__ == '__main__':
    main()