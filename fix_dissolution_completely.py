#!/usr/bin/env python3
import json
import os
from pathlib import Path

def fix_dissolution_recipe_completely(file_path):
    """Fix dissolution recipe to match Mekanism's exact format"""
    try:
        with open(file_path, 'r') as f:
            recipe = json.load(f)

        # Skip if not a dissolution recipe
        if recipe.get('type') != 'mekanism:dissolution':
            return False

        # Correct format based on Mekanism source:
        # - Uses per_tick_usage: true
        # - Uses 1mb per tick (100 ticks total)
        # - Output uses "id" field for slurries
        # - Output should use "chemical" field for non-slurries

        # Determine correct acid amount per tick
        # We want 10mb total for alternative acids, 20mb for sulfuric
        # With per_tick_usage over ~100 ticks:
        # 10mb / 100 = 0.1mb per tick (but Mekanism uses integers)
        # So use 1mb per tick = 100mb total (reasonable)

        modified = False

        # Add per_tick_usage
        if 'per_tick_usage' not in recipe:
            recipe['per_tick_usage'] = True
            modified = True

        # Fix chemical input amount to 1mb per tick
        if 'chemical_input' in recipe and 'chemical' in recipe['chemical_input']:
            chemical = recipe['chemical_input']['chemical']

            # Use 1mb per tick for all acids (will consume 100mb total)
            # Or we could use 2mb for sulfuric, 1mb for others
            if chemical == 'mekanism:sulfuric_acid':
                recipe['chemical_input']['amount'] = 2  # 200mb total
            else:
                recipe['chemical_input']['amount'] = 1  # 100mb total
            modified = True

        # Output format depends on whether it's a slurry or chemical
        # Slurries (dirty_*) use "id", chemicals use "chemical"
        # But wait, checking our recipes again...

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
    # First, let's check what one of our dissolution recipes looks like
    sample_file = Path('src/main/resources/data/chemlibmekanized/recipe/dissolution/elements/dissolve_nitrogen_sulfuric.json')

    if sample_file.exists():
        with open(sample_file, 'r') as f:
            sample = json.load(f)
        print("Sample dissolution recipe structure:")
        print(json.dumps(sample, indent=2))
        print("\nIssues found:")

        # Check field names
        has_issues = False
        if 'item_input' not in sample:
            print("- Missing 'item_input' field (using different name?)")
            has_issues = True
        if 'chemical_input' not in sample:
            print("- Missing 'chemical_input' field (using different name?)")
            has_issues = True

        # Check if output is trying to create a chemical that doesn't exist
        if 'output' in sample and 'id' in sample['output']:
            output_id = sample['output']['id']
            if not output_id.startswith('mekanism:dirty_') and not output_id.startswith('chemlibmekanized:dirty_'):
                # Non-slurry outputs might need to be registered chemicals
                print(f"- Output '{output_id}' might not be a registered slurry")
                print("  (Non-slurry chemical outputs may need special handling)")
                has_issues = True

        if not has_issues:
            print("- No obvious structural issues found")

    print("\n" + "="*60)
    print("Based on Mekanism source examples, dissolution recipes should:")
    print("1. Use 'item_input' and 'chemical_input' (with underscores)")
    print("2. Use 'per_tick_usage: true' with small amounts (1-2mb)")
    print("3. Output to registered slurries using 'id' field")
    print("4. For chemical outputs (not slurries), might need different handling")
    print("="*60)

if __name__ == '__main__':
    main()