#!/usr/bin/env python3
import json
import os
from pathlib import Path

def add_per_tick_usage_false(file_path):
    """Add per_tick_usage: false to dissolution recipes"""
    try:
        with open(file_path, 'r') as f:
            recipe = json.load(f)

        # Skip if not a dissolution recipe
        if recipe.get('type') != 'mekanism:dissolution':
            return False

        modified = False

        # Add per_tick_usage: false if not present
        if 'per_tick_usage' not in recipe:
            recipe['per_tick_usage'] = False  # FALSE, not true - one-time consumption
            modified = True

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
        if add_per_tick_usage_false(json_file):
            fixed_count += 1

    print(f"=== Summary ===")
    print(f"Added per_tick_usage: false to {fixed_count} recipes")
    print(f"Total files processed: {total_count}")

if __name__ == '__main__':
    main()