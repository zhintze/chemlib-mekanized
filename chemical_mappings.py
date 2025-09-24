#!/usr/bin/env python3
"""
Chemical mapping system to always prefer Mekanism chemicals where they exist.
"""

# Mekanism chemicals that we should use instead of our own
MEKANISM_CHEMICAL_MAPPINGS = {
    # Mekanism gases we should use
    "hydrogen": "mekanism:hydrogen",
    "oxygen": "mekanism:oxygen",
    "chlorine": "mekanism:chlorine",
    "fluorine": "mekanism:fluorine",
    "sodium": "mekanism:sodium",
    "lithium": "mekanism:lithium",

    # Mekanism compounds
    "water_vapor": "mekanism:water_vapor",
    "steam": "mekanism:steam",
    "sulfur_dioxide": "mekanism:sulfur_dioxide",
    "sulfur_trioxide": "mekanism:sulfur_trioxide",
    "hydrogen_chloride": "mekanism:hydrogen_chloride",
    "sulfuric_acid": "mekanism:sulfuric_acid",
    "hydrofluoric_acid": "mekanism:hydrofluoric_acid",

    # Mekanism infusion types
    "carbon": "mekanism:carbon",
    "redstone": "mekanism:redstone",
    "diamond": "mekanism:diamond",
    "gold": "mekanism:gold",
    "tin": "mekanism:tin",
    "refined_obsidian": "mekanism:refined_obsidian",
    "bio": "mekanism:bio",
    "fungi": "mekanism:fungi",

    # Special Mekanism chemicals
    "brine": "mekanism:brine",
    "ethene": "mekanism:ethene",
    "uranium_oxide": "mekanism:uranium_oxide",
    "uranium_hexafluoride": "mekanism:uranium_hexafluoride",
    "plutonium": "mekanism:plutonium",
    "polonium": "mekanism:polonium",
    "antimatter": "mekanism:antimatter",
    "nuclear_waste": "mekanism:nuclear_waste",
    "spent_nuclear_waste": "mekanism:spent_nuclear_waste",
    "fissile_fuel": "mekanism:fissile_fuel"
}

# Our chemicals that don't exist in Mekanism
CHEMLIB_CHEMICALS = {
    # Noble gases (except those in Mekanism)
    "helium": "chemlibmekanized:element_helium",
    "neon": "chemlibmekanized:element_neon",
    "argon": "chemlibmekanized:element_argon",
    "krypton": "chemlibmekanized:element_krypton",
    "xenon": "chemlibmekanized:element_xenon",
    "radon": "chemlibmekanized:element_radon",

    # Our unique compounds
    "ammonia": "chemlibmekanized:compound_ammonia",
    "methane": "chemlibmekanized:compound_methane",
    "ethane": "chemlibmekanized:compound_ethane",
    "propane": "chemlibmekanized:compound_propane",
    "butane": "chemlibmekanized:compound_butane",
    "acetylene": "chemlibmekanized:compound_acetylene",
    "ethylene": "chemlibmekanized:compound_ethylene",
    "hydrogen_sulfide": "chemlibmekanized:compound_hydrogen_sulfide",
    "nitrogen_dioxide": "chemlibmekanized:compound_nitrogen_dioxide",
    "carbon_monoxide": "chemlibmekanized:compound_carbon_monoxide",
    "nitric_oxide": "chemlibmekanized:compound_nitric_oxide",
    "carbon_dioxide": "chemlibmekanized:compound_carbon_dioxide",

    # Use our nitrogen since Mekanism doesn't have it as a separate gas
    "nitrogen": "chemlibmekanized:element_nitrogen"
}

def get_chemical_id(name):
    """Get the proper chemical ID, preferring Mekanism where available."""
    # Check Mekanism first
    if name.lower() in MEKANISM_CHEMICAL_MAPPINGS:
        return MEKANISM_CHEMICAL_MAPPINGS[name.lower()]
    # Then check our chemicals
    elif name.lower() in CHEMLIB_CHEMICALS:
        return CHEMLIB_CHEMICALS[name.lower()]
    # Default to our namespace if not found
    else:
        return f"chemlibmekanized:{name.lower()}"