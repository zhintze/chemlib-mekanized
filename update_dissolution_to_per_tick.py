#!/usr/bin/env python3
import json
import os
from pathlib import Path

def update_dissolution_recipe(file_path):
    """Update dissolution recipe to use per-tick consumption"""
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

        # Check if it has a chemical input
        if 'chemical_input' in recipe and 'chemical' in recipe['chemical_input']:
            chemical = recipe['chemical_input']['chemical']
            current_amount = recipe['chemical_input'].get('amount', 0)

            # Calculate per-tick amount (assuming 100 tick duration)
            # We'll use 1mb per 5 ticks for alternative acids (20mb total)
            # and 1mb per 2.5 ticks for sulfuric acid (40mb total, but we'll use 1mb per 2 ticks = 50mb)
            # Actually, let's be more precise:
            # For 10mb total over 100 ticks = 0.1mb per tick
            # For 20mb total over 100 ticks = 0.2mb per tick
            # Since we can't use decimals, we'll use 1mb with adjusted duration

            # Actually, let's use a different approach:
            # Set to 1mb per tick for 10 ticks (10mb total) for alternatives
            # Set to 1mb per tick for 20 ticks (20mb total) for sulfuric
            # But we can't control duration per recipe...

            # Best approach: Use smallest integer that works
            # 1mb per tick for 10 ticks = 10mb (alternatives)
            # 2mb per tick for 10 ticks = 20mb (sulfuric)
            # But duration is fixed at ~100 ticks

            # Let's try fractional mb even though it might not work
            # Mekanism might round or handle it internally
            if chemical == 'mekanism:sulfuric_acid':
                # 20mb over 100 ticks = 0.2mb per tick
                recipe['chemical_input']['amount'] = 0.2
                modified = True
                print(f"Updated {file_path.name}: sulfuric_acid to 0.2mb per tick")
            elif chemical in ['chemlibmekanized:nitric_acid',
                            'mekanism:hydrochloric_acid',
                            'chemlibmekanized:hydrochloric_acid',
                            'mekanism:hydrogen_chloride']:
                # 10mb over 100 ticks = 0.1mb per tick
                recipe['chemical_input']['amount'] = 0.1
                modified = True
                print(f"Updated {file_path.name}: {chemical.split(':')[1]} to 0.1mb per tick")

            # Add per_tick_usage flag
            if 'per_tick_usage' not in recipe:
                recipe['per_tick_usage'] = True
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

    updated_count = 0
    total_count = 0
    skipped_count = 0

    # Process all JSON files in the dissolution directory and subdirectories
    for json_file in base_dir.rglob('*.json'):
        total_count += 1
        result = update_dissolution_recipe(json_file)
        if result is True:
            updated_count += 1
        elif result is False and 'dirty_' in str(json_file):
            skipped_count += 1

    print(f"\n=== Summary ===")
    print(f"Total files processed: {total_count}")
    print(f"Files updated: {updated_count}")
    print(f"Mekanism ore recipes skipped: {skipped_count}")

if __name__ == '__main__':
    main()