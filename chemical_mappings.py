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
    "refined_obsidian": "mekanism:refined_obsidian",
    "bio": "mekanism:bio",
    "fungi": "mekanism:fungi",

    # Mekanism slurries (for metals)
    "iron": "mekanism:dirty_iron",
    "gold": "mekanism:dirty_gold",
    "copper": "mekanism:dirty_copper",
    "tin": "mekanism:dirty_tin",
    "lead": "mekanism:dirty_lead",
    "uranium": "mekanism:dirty_uranium",
    "osmium": "mekanism:dirty_osmium",

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
    "nitrogen": "chemlibmekanized:element_nitrogen",

    # Elements as slurries for dissolution outputs
    "silicon": "chemlibmekanized:slurry_silicon",
    "calcium": "chemlibmekanized:slurry_calcium"
}

# New compounds created by our recipes (will need to be registered)
NEW_COMPOUNDS = {
    # Noble gas compounds
    "xenon_difluoride": "chemlibmekanized:compound_xenon_difluoride",
    "krypton_difluoride": "chemlibmekanized:compound_krypton_difluoride",
    "radon_difluoride": "chemlibmekanized:compound_radon_difluoride",

    # Organic compounds
    "hydrogen_cyanide": "chemlibmekanized:compound_hydrogen_cyanide",
    "formaldehyde": "chemlibmekanized:compound_formaldehyde",
    "vinyl_chloride": "chemlibmekanized:compound_vinyl_chloride",
    "ethylene_oxide": "chemlibmekanized:compound_ethylene_oxide",
    "benzene": "chemlibmekanized:compound_benzene",
    "toluene": "chemlibmekanized:compound_toluene",

    # Chlorine/fluorine compounds
    "chlorine_monoxide": "chemlibmekanized:compound_chlorine_monoxide",
    "chlorine_dioxide": "chemlibmekanized:compound_chlorine_dioxide",
    "phosgene": "chemlibmekanized:compound_phosgene",
    "chlorine_trifluoride": "chemlibmekanized:compound_chlorine_trifluoride",

    # Nitrogen oxides
    "nitrous_oxide": "chemlibmekanized:compound_nitrous_oxide",
    "nitrogen_trioxide": "chemlibmekanized:compound_nitrogen_trioxide",
    "dinitrogen_tetroxide": "chemlibmekanized:compound_dinitrogen_tetroxide",

    # Others
    "hydrogen_peroxide": "chemlibmekanized:compound_hydrogen_peroxide",
    "ozone": "chemlibmekanized:compound_ozone",
    "carbon_oxysulfide": "chemlibmekanized:compound_carbon_oxysulfide"
}

# Our registered slurries (excluding Mekanism duplicates: iron, gold, copper, tin, lead, uranium, osmium)
CHEMLIB_SLURRIES = {
    # Metals not in Mekanism
    "aluminum": "chemlibmekanized:dirty_aluminum",
    "titanium": "chemlibmekanized:dirty_titanium",
    "zinc": "chemlibmekanized:dirty_zinc",
    "nickel": "chemlibmekanized:dirty_nickel",
    "silver": "chemlibmekanized:dirty_silver",
    "platinum": "chemlibmekanized:dirty_platinum",

    # Metalloids and special elements
    "silicon": "chemlibmekanized:dirty_silicon",
    "calcium": "chemlibmekanized:dirty_calcium",
    "sodium": "chemlibmekanized:dirty_sodium",
    "germanium": "chemlibmekanized:dirty_germanium",
    "antimony": "chemlibmekanized:dirty_antimony",
    "bismuth": "chemlibmekanized:dirty_bismuth",
    "boron": "chemlibmekanized:dirty_boron",
    "arsenic": "chemlibmekanized:dirty_arsenic",
    "tellurium": "chemlibmekanized:dirty_tellurium",

    # Special materials (not metals)
    "emerald": "chemlibmekanized:dirty_emerald",
    "quartz": "chemlibmekanized:dirty_quartz",
    "lapis": "chemlibmekanized:dirty_lapis",
    "coal_slurry": "chemlibmekanized:dirty_coal",
    "netherite_scrap": "chemlibmekanized:dirty_netherite_scrap",

    # Additional metals
    "beryllium": "chemlibmekanized:dirty_beryllium",
    "magnesium": "chemlibmekanized:dirty_magnesium",
    "scandium": "chemlibmekanized:dirty_scandium",
    "vanadium": "chemlibmekanized:dirty_vanadium",
    "chromium": "chemlibmekanized:dirty_chromium",
    "manganese": "chemlibmekanized:dirty_manganese",
    "cobalt": "chemlibmekanized:dirty_cobalt",
    "gallium": "chemlibmekanized:dirty_gallium",
    "rubidium": "chemlibmekanized:dirty_rubidium",
    "strontium": "chemlibmekanized:dirty_strontium",
    "yttrium": "chemlibmekanized:dirty_yttrium",
    "zirconium": "chemlibmekanized:dirty_zirconium",
    "niobium": "chemlibmekanized:dirty_niobium",
    "molybdenum": "chemlibmekanized:dirty_molybdenum",
    "technetium": "chemlibmekanized:dirty_technetium",
    "ruthenium": "chemlibmekanized:dirty_ruthenium",
    "rhodium": "chemlibmekanized:dirty_rhodium",
    "palladium": "chemlibmekanized:dirty_palladium",
    "cadmium": "chemlibmekanized:dirty_cadmium",
    "indium": "chemlibmekanized:dirty_indium",
    "cesium": "chemlibmekanized:dirty_cesium",
    "barium": "chemlibmekanized:dirty_barium",
    "lanthanum": "chemlibmekanized:dirty_lanthanum",
    "cerium": "chemlibmekanized:dirty_cerium",
    "neodymium": "chemlibmekanized:dirty_neodymium",
    "europium": "chemlibmekanized:dirty_europium",
    "gadolinium": "chemlibmekanized:dirty_gadolinium",
    "hafnium": "chemlibmekanized:dirty_hafnium",
    "tantalum": "chemlibmekanized:dirty_tantalum",
    "tungsten": "chemlibmekanized:dirty_tungsten",
    "rhenium": "chemlibmekanized:dirty_rhenium",
    "iridium": "chemlibmekanized:dirty_iridium",
    "mercury": "chemlibmekanized:dirty_mercury",
    "thallium": "chemlibmekanized:dirty_thallium",
    "bismuth": "chemlibmekanized:dirty_bismuth",
    "polonium_slurry": "chemlibmekanized:dirty_polonium",
    "thorium": "chemlibmekanized:dirty_thorium",
}

def get_chemical_id(name):
    """Get the proper chemical ID, preferring Mekanism where available."""
    # Check Mekanism first
    if name.lower() in MEKANISM_CHEMICAL_MAPPINGS:
        return MEKANISM_CHEMICAL_MAPPINGS[name.lower()]
    # Check our slurries
    elif name.lower() in CHEMLIB_SLURRIES:
        return CHEMLIB_SLURRIES[name.lower()]
    # Then check our chemicals
    elif name.lower() in CHEMLIB_CHEMICALS:
        return CHEMLIB_CHEMICALS[name.lower()]
    # Check new compounds from our recipes
    elif name.lower() in NEW_COMPOUNDS:
        return NEW_COMPOUNDS[name.lower()]
    # Default to our namespace if not found
    else:
        return f"chemlibmekanized:compound_{name.lower()}"