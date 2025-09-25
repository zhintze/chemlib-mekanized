#!/usr/bin/env python3
import json
import os
from pathlib import Path

def fix_dissolution_output(file_path):
    """Fix dissolution recipe output format"""
    try:
        with open(file_path, 'r') as f:
            recipe = json.load(f)

        # Skip if not a dissolution recipe
        if recipe.get('type') != 'mekanism:dissolution':
            return False

        modified = False

        # Fix output format
        if 'output' in recipe and 'id' in recipe['output']:
            output_id = recipe['output']['id']

            # Check if this is a slurry (dirty_*) or a chemical
            if output_id.startswith('mekanism:dirty_') or output_id.startswith('chemlibmekanized:dirty_'):
                # Slurries use "id" field - this is correct
                pass
            else:
                # Chemical outputs should use "chemical" field instead of "id"
                amount = recipe['output'].get('amount', 200)
                recipe['output'] = {
                    "amount": amount,
                    "chemical": output_id  # Change "id" to "chemical"
                }
                modified = True
                print(f"Fixed {file_path.name}: changed id to chemical for {output_id}")

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
    skipped_slurry = 0

    # Process all JSON files in the dissolution directory and subdirectories
    for json_file in base_dir.rglob('*.json'):
        total_count += 1
        result = fix_dissolution_output(json_file)
        if result is True:
            fixed_count += 1

    print(f"\n=== Summary ===")
    print(f"Total files processed: {total_count}")
    print(f"Files fixed: {fixed_count}")
    print(f"Files already correct: {total_count - fixed_count}")

if __name__ == '__main__':
    main()