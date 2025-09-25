#!/usr/bin/env python3
"""
Generate complete slurry processing cycle:
1. Chemical washing recipes (dirty slurry -> clean slurry)
2. Chemical crystallizing recipes (clean slurry -> crystal)
3. Register crystal items
"""

import json
import os

# All elements that should have slurry forms
ELEMENTS = [
    "aluminum", "antimony", "arsenic", "barium", "beryllium", "bismuth", "boron",
    "cadmium", "calcium", "cerium", "cesium", "chromium", "cobalt", "copper",
    "dysprosium", "erbium", "europium", "francium", "gadolinium", "gallium",
    "germanium", "gold", "hafnium", "holmium", "indium", "iridium", "iron",
    "lanthanum", "lawrencium", "lead", "lithium", "lutetium", "magnesium",
    "manganese", "molybdenum", "neodymium", "nickel", "niobium", "osmium",
    "palladium", "platinum", "polonium", "potassium", "praseodymium", "promethium",
    "rhenium", "rhodium", "rubidium", "ruthenium", "samarium", "scandium",
    "silver", "sodium", "strontium", "tantalum", "technetium", "terbium",
    "thallium", "thorium", "thulium", "tin", "titanium", "tungsten", "uranium",
    "vanadium", "ytterbium", "yttrium", "zinc", "zirconium"
]

def create_washing_recipe(element):
    """Create a chemical washing recipe for dirty -> clean slurry"""
    return {
        "type": "mekanism:washing",
        "fluid_input": {
            "amount": 5,
            "fluid": "minecraft:water"
        },
        "chemical_input": {
            "amount": 1,
            "chemical": f"chemlibmekanized:dirty_{element}"
        },
        "output": {
            "id": f"chemlibmekanized:clean_{element}"
        }
    }

def create_crystallizing_recipe(element):
    """Create a chemical crystallizing recipe for clean slurry -> crystal"""
    return {
        "type": "mekanism:crystallizing",
        "chemical_input": {
            "amount": 200,
            "chemical": f"chemlibmekanized:clean_{element}"
        },
        "output": {
            "count": 1,
            "id": f"chemlibmekanized:{element}_crystal"
        }
    }

def add_crystal_registration_to_registry():
    """Add crystal item registration to ChemLibItemRegistry.java"""
    registry_path = "src/main/java/com/hecookin/chemlibmekanized/registry/ChemLibItemRegistry.java"

    # Read the file
    with open(registry_path, 'r') as f:
        content = f.read()

    # Check if crystal registration already exists
    if "METAL_CRYSTAL_ITEMS" in content:
        print("Crystal registration already exists in ChemLibItemRegistry")
        return False

    # Find insertion points
    # Add crystal map declaration after other metal maps
    map_insertion = "    public static final Map<String, DeferredHolder<Item, Item>> METAL_PLATE_ITEMS = new HashMap<>();"
    map_addition = "\n    public static final Map<String, DeferredHolder<Item, Item>> METAL_CRYSTAL_ITEMS = new HashMap<>();"

    # Add registration method
    method_insertion = "    public static void registerMetalPlates() {"
    method_addition = """
    public static void registerMetalCrystals() {
        List<ChemLibDataExtractor.ElementData> metals = ChemLibDataExtractor.extractMetals();
        List<ChemLibDataExtractor.ElementData> metalloids = ChemLibDataExtractor.extractMetalloids();

        // Register crystals for all metals
        for (ChemLibDataExtractor.ElementData metal : metals) {
            String crystalName = metal.name + "_crystal";
            DeferredHolder<Item, Item> crystalItem = ITEMS.register(crystalName,
                () -> new MetalCrystalItem(metal));
            METAL_CRYSTAL_ITEMS.put(metal.name, crystalItem);
        }

        // Register crystals for metalloids too
        for (ChemLibDataExtractor.ElementData metalloid : metalloids) {
            String crystalName = metalloid.name + "_crystal";
            DeferredHolder<Item, Item> crystalItem = ITEMS.register(crystalName,
                () -> new MetalCrystalItem(metalloid));
            METAL_CRYSTAL_ITEMS.put(metalloid.name, crystalItem);
        }

        ChemlibMekanized.LOGGER.info("Registered {} metal/metalloid crystals", METAL_CRYSTAL_ITEMS.size());
    }

    """

    # Add import for MetalCrystalItem
    import_insertion = "import com.hecookin.chemlibmekanized.items.MetalPlateItem;"
    import_addition = "\nimport com.hecookin.chemlibmekanized.items.MetalCrystalItem;"

    # Add to initialization
    init_insertion = "        registerMetalPlates();"
    init_addition = "\n        registerMetalCrystals();"

    # Add to logging
    log_insertion = "METAL_NUGGET_ITEMS.size(), METAL_PLATE_ITEMS.size());"
    log_addition = "METAL_NUGGET_ITEMS.size(), METAL_PLATE_ITEMS.size(), METAL_CRYSTAL_ITEMS.size());"
    log_insertion2 = '"{} elements, {} compounds, {} metal ingots, {} nuggets, {} plates"'
    log_addition2 = '"{} elements, {} compounds, {} metal ingots, {} nuggets, {} plates, {} crystals"'

    # Apply changes
    content = content.replace(import_insertion, import_insertion + import_addition)
    content = content.replace(map_insertion, map_insertion + map_addition)
    content = content.replace(method_insertion, method_addition + method_insertion)
    content = content.replace(init_insertion, init_insertion + init_addition)
    content = content.replace(log_insertion2, log_addition2)
    content = content.replace(log_insertion, log_addition)

    # Write back
    with open(registry_path, 'w') as f:
        f.write(content)

    print("Added crystal registration to ChemLibItemRegistry")
    return True

def main():
    # Create the recipe directories
    washing_dir = "src/main/resources/data/chemlibmekanized/recipe/washing"
    crystallizing_dir = "src/main/resources/data/chemlibmekanized/recipe/crystallizing"

    os.makedirs(washing_dir, exist_ok=True)
    os.makedirs(crystallizing_dir, exist_ok=True)

    washing_count = 0
    crystallizing_count = 0

    for element in ELEMENTS:
        # Create washing recipe
        washing_recipe = create_washing_recipe(element)
        washing_filename = f"{washing_dir}/wash_{element}.json"

        with open(washing_filename, 'w') as f:
            json.dump(washing_recipe, f, indent=2)
        washing_count += 1

        # Create crystallizing recipe
        crystallizing_recipe = create_crystallizing_recipe(element)
        crystallizing_filename = f"{crystallizing_dir}/crystallize_{element}.json"

        with open(crystallizing_filename, 'w') as f:
            json.dump(crystallizing_recipe, f, indent=2)
        crystallizing_count += 1

    print(f"\nTotal washing recipes created: {washing_count}")
    print(f"Total crystallizing recipes created: {crystallizing_count}")
    print(f"Elements processed: {len(ELEMENTS)}")

    # Add crystal registration to the registry
    add_crystal_registration_to_registry()

if __name__ == "__main__":
    main()