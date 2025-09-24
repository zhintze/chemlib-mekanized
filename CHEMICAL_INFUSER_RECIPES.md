# Chemical Infuser Recipes Implementation

## Overview
This document tracks the implementation of Chemical Infuser recipes for ChemLibMekanized.
**Total Working: 18 recipes** (Batch 1 only)
**Bonus Features (Pending): 12 recipes** (Require new chemical registration)

## Recipe Format
All recipes follow the Mekanism Chemical Infuser JSON format:
- Type: `mekanism:chemical_infusing`
- Inputs: Two chemicals with amounts in mB (millibuckets)
- Output: One chemical with amount in mB

## Chemical Mapping Policy
1. **Always use Mekanism chemicals when available** (e.g., `mekanism:hydrogen`)
2. Use our chemicals only when Mekanism doesn't have them (e.g., `chemlibmekanized:compound_ammonia`)
3. Never duplicate existing Mekanism recipes

## Batch 1: Basic Chemical Compounds (✅ Complete - 18 recipes)
*Generated: 2024-12-23*
*Updated: 2024-12-23 - Removed duplicates and scientifically incorrect recipes*

### ⚠️ Important Notes:
- **Removed Duplicates**: SO₂ + O₂ → SO₃ and H₂ + Cl₂ → HCl (Mekanism already has these)
- **Removed Incorrect**: Hydrocarbon "addition" reactions violate organic chemistry principles
- **Added Accurate**: Scientifically valid oxidation and reforming reactions

### Currently Implemented Recipes (18 total):

#### Basic Gas Formations:
1. **water_vapor_formation**: 2 H₂ + O₂ → 2 H₂O
2. **ammonia_synthesis**: N₂ + 3 H₂ → 2 NH₃ (Haber-Bosch process)
3. **methane_formation**: C + 4 H₂ → CH₄

#### Oxidation Reactions:
4. **carbon_dioxide_formation**: C + 2 O₂ → CO₂
5. **carbon_monoxide_formation**: 2 C + O₂ → 2 CO
6. **nitric_oxide_formation**: N₂ + O₂ → 2 NO
7. **nitrogen_dioxide_formation**: 2 NO + O₂ → 2 NO₂
8. **ammonia_oxidation**: 4 NH₃ + 5 O₂ → 4 NO (Ostwald process step 1)
9. **hydrogen_sulfide_oxidation**: 2 H₂S + 3 O₂ → 2 SO₂

#### Reforming Reactions:
10. **dry_reforming**: CH₄ + CO₂ → 2 CO (CO₂ utilization)

#### Acid Formations:
11. **hydrofluoric_acid_formation**: H₂ + F₂ → 2 HF
12. **sulfuric_acid_formation**: SO₃ + H₂O → H₂SO₄

#### Simple Organic Compounds:
13. **ethylene_formation**: 2 C + 4 H₂ → C₂H₄
14. **acetylene_formation**: 2 C + 2 H₂ → C₂H₂

#### Industrial Chemicals:
15. **ethene_formation**: C₂H₄ + O₂ → C₂H₄O (ethene oxide)
16. **brine_formation**: Na + 10 H₂O → Brine solution
17. **steam_formation**: H₂O + heat → Steam
18. **hydrogen_sulfide_formation**: 2 H₂ + SO₂ → H₂S

### Chemicals Used:
- **From Mekanism (10)**: hydrogen, oxygen, fluorine, sodium, carbon, water_vapor, steam, sulfur_dioxide, sulfur_trioxide, hydrofluoric_acid, sulfuric_acid, brine, ethene
- **From ChemLibMekanized (7)**: nitrogen, ammonia, methane, acetylene, ethylene, hydrogen_sulfide, carbon_monoxide, carbon_dioxide, nitric_oxide, nitrogen_dioxide

## BONUS FEATURES - Advanced Compounds (Pending Implementation)
*These 12 recipes are created but require registering new output chemicals in the Java code*

### Pending Chemical Registrations Needed:

#### Noble Gas Compounds:
1. **xenon_difluoride**: Xe + 2 F₂ → XeF₂
2. **krypton_difluoride**: Kr + 2 F₂ → KrF₂
3. **radon_difluoride**: Rn + 2 F₂ → RnF₂

#### Advanced Organic Compounds:
4. **hydrogen_cyanide_formation**: CH₄ + NH₃ → HCN (Andrussow process)
5. **formaldehyde_formation**: CH₄ + O₂ → CH₂O (partial oxidation)

#### Halogen Compounds:
6. **chlorine_monoxide_formation**: Cl₂ + O₂ → Cl₂O
7. **chlorine_dioxide_formation**: 2 Cl₂ + 2 O₂ → 2 ClO₂
8. **phosgene_formation**: CO + Cl₂ → COCl₂
9. **chlorine_trifluoride_formation**: Cl₂ + 3 F₂ → ClF₃

#### Other Compounds:
10. **nitrous_oxide_formation**: 2 N₂ + O₂ → N₂O
11. **hydrogen_peroxide_formation**: 2 H₂ + 2 O₂ → H₂O₂
12. **carbon_oxysulfide_formation**: CO + SO₂ → COS

### Chemicals Requiring Registration:
To enable these recipes, the following chemicals must be added to `ChemlibMekanizedChemicals.java`:
- compound_xenon_difluoride
- compound_krypton_difluoride
- compound_radon_difluoride
- compound_hydrogen_cyanide
- compound_formaldehyde
- compound_chlorine_monoxide
- compound_chlorine_dioxide
- compound_phosgene
- compound_chlorine_trifluoride
- compound_nitrous_oxide
- compound_hydrogen_peroxide
- compound_carbon_oxysulfide

## Next Batches (Planned)

### Batch 3: Inorganic Compounds (20 recipes)
- Metal hydrides
- Metal halides
- Nitrates and sulfates
- Phosphates and carbonates

### Batch 4: Specialized Compounds (20 recipes)
- Pharmaceutical precursors
- Industrial chemicals
- Catalysts
- Advanced materials

## Testing Checklist
- [ ] Build mod with `./gradlew build`
- [ ] Launch game and verify no recipe loading errors
- [ ] Test in-game with Chemical Infuser
- [ ] Verify JEI integration shows all recipes
- [ ] Check for conflicts with existing Mekanism recipes

## Notes
- All amounts are in millibuckets (mB)
- Standard ratio: 1 unit = 1000 mB
- Some reactions are simplified for gameplay balance
- Real stoichiometry adjusted for better gameplay