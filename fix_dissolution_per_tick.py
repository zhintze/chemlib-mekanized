#!/usr/bin/env python3
import json
import os
from pathlib import Path

def fix_dissolution_recipe(file_path):
    """Remove per_tick_usage from dissolution recipes"""
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
            print(f"Removed per_tick_usage from: {file_path.name}")

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
        if fix_dissolution_recipe(json_file):
            fixed_count += 1

    print(f"\n=== Summary ===")
    print(f"Total files processed: {total_count}")
    print(f"Files fixed: {fixed_count}")
    print(f"Files already correct: {total_count - fixed_count}")

if __name__ == '__main__':
    main()