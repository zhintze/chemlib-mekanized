#!/usr/bin/env python3
import json
import os
from pathlib import Path

def remove_invalid_dissolution_recipes():
    """Remove dissolution recipes that output chemicals instead of slurries"""

    base_dir = Path('src/main/resources/data/chemlibmekanized/recipe/dissolution')

    if not base_dir.exists():
        print(f"Directory not found: {base_dir}")
        return

    removed_count = 0
    kept_count = 0

    # Process all dissolution recipes
    for json_file in base_dir.rglob('*.json'):
        try:
            with open(json_file, 'r') as f:
                recipe = json.load(f)

            if recipe.get('type') != 'mekanism:dissolution':
                continue

            should_remove = False

            if 'output' in recipe and 'id' in recipe['output']:
                output_id = recipe['output']['id']

                # Keep only recipes that output slurries
                if 'dirty_' in output_id or 'clean_' in output_id or 'slurry_' in output_id:
                    kept_count += 1
                else:
                    # This outputs a chemical, not a slurry - remove it
                    should_remove = True
                    print(f"Removing: {json_file.name} (outputs {output_id})")

            if should_remove:
                os.remove(json_file)
                removed_count += 1

        except Exception as e:
            print(f"Error processing {json_file}: {e}")

    print(f"\n=== SUMMARY ===")
    print(f"Removed {removed_count} invalid dissolution recipes")
    print(f"Kept {kept_count} valid slurry dissolution recipes")

if __name__ == '__main__':
    remove_invalid_dissolution_recipes()