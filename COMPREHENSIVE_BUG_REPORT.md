# ChemLibMekanized Comprehensive Bug Report
**Date**: September 25, 2024
**Version**: Current development branch

## Executive Summary
Systematic audit of ChemLibMekanized recipe system revealed multiple critical issues affecting gameplay and mod completeness. Most issues are configuration/data problems rather than code bugs.

## Critical Issues (Game-Breaking)

### 1. Invisible Liquid Gas Textures
**Severity**: CRITICAL
**Impact**: 35 gas fluids are completely invisible in-game
**Root Cause**:
- Gas fluids missing from client registration in `ChemLibFluidRegistry.getAllFluids()`
- Base fluid textures are transparent instead of visible

**Fix Required**:
```java
// In ChemLibFluidRegistry.java line 306
all.addAll(GAS_FLUIDS);  // ADD THIS LINE
```
Plus: Replace transparent fluid textures with visible base textures

**Affected Items**: nitrogen, fluorine, helium, neon, argon, all gas compounds

### 2. Missing Crystal Item Registrations
**Severity**: HIGH
**Impact**: 35 crystallizing recipes produce non-existent items
**Details**: MetalCrystalRegistry has hardcoded list missing 35 metals

**Missing Crystals**:
- Super-heavy elements: bohrium, copernicium, darmstadtium, dubnium, hassium, meitnerium, roentgenium, rutherfordium, seaborgium, flerovium, moscovium, nihonium, livermorium
- Rare earths: dysprosium, einsteinium, erbium, fermium, holmium, lawrencium, lutetium, mendelevium, nobelium, praseodymium, promethium, samarium, terbium, thulium, ytterbium
- Base metals: copper, gold, iron, lead, tin, uranium, osmium

**Fix**: Add missing metals to `CRYSTAL_METALS` array in MetalCrystalRegistry.java

## Major Issues

### 3. Duplicate Dissolution Recipes
**Severity**: MEDIUM
**Impact**: 66 elements have duplicate sulfuric acid dissolution recipes
**Details**:
- Each element has BOTH recipes in two locations:
  - Root folder: `element_*_dissolution_sulfuric.json`
  - Metals folder: `element_*_to_slurry_sulfuric.json`
- These are identical recipes with same inputs/outputs
- HCl and hydrochloric are INTENDED as separate recipe types (different acids)

**Current State**: 4 acid types (HCl, hydrochloric, nitric, sulfuric) but sulfuric has duplicates
**Fix**: Remove duplicate sulfuric recipes (choose one location/naming convention)

### 4. Missing Dissolution Recipes
**Severity**: MEDIUM
**Impact**: 12 metals have broken processing chains
**Affected Elements**: actinium, americium, berkelium, californium, curium, francium, mercury, neptunium, plutonium, protactinium, radium, technetium

**Result**: Complete downstream processing exists but inaccessible

### 5. Missing Washing Recipes
**Severity**: MEDIUM
**Impact**: 29 elements cannot be processed from dirty to clean slurry
**Affected**: All super-heavy elements and several lanthanides

**Missing Recipes For**:
- ASTATINE, BOHRIUM, COPERNICIUM, DARMSTADTIUM, DUBNIUM, DYSPROSIUM, EINSTEINIUM, ERBIUM, FERMIUM, FLEROVIUM, HASSIUM, HOLMIUM, LAWRENCIUM, LIVERMORIUM, LUTETIUM, MEITNERIUM, MENDELEVIUM, MOSCOVIUM, NIHONIUM, NOBELIUM, ROENTGENIUM, RUTHERFORDIUM, SEABORGIUM, THULIUM, YTTERBIUM, PRASEODYMIUM, PROMETHIUM, SAMARIUM, TERBIUM

## Minor Issues

### 6. Orphaned Items (No Recipes)
**Severity**: LOW
**Impact**: Hundreds of registered items with no gameplay purpose

**Missing Basic Recipes**:
- ALL metal nuggets need basic conversion recipes:
  - 9 nuggets → 1 ingot (crafting table)
  - 1 ingot → 9 nuggets (crafting table)

**Future Integration (Create Mod)**:
- ALL metal plates (pending Create mod integration for pressing recipes)

**Dead-end Items**:
- Most crystal items (outputs with no uses)
- Many compound items (intermediate products with no progression)

### 7. Missing PRC Recipes for Rare Metals
**Severity**: LOW
**Impact**: 21 rare elements cannot be obtained from vanilla items

**Missing PRC Recipes**:
- 13 super-heavy elements (Z ≥ 104)
- 7 late actinides (Z 97-103)
- 1 natural rare metal (Osmium)

**Note**: Good coverage for naturally occurring rare metals

### 8. Recipe Format Inconsistencies
**Severity**: LOW
**Impact**: Potential future compatibility issues

**Issues Found**:
- Washing recipes use both "tag" and "fluid" for water input
- Some recipes missing standard fields
- Inconsistent naming conventions

## Statistics Summary

| Category | Total | Working | Broken | Coverage |
|----------|-------|---------|--------|----------|
| Custom Elements | 92 | - | - | - |
| Dissolution Recipes | 79 | 79 | 66 | 100% (66 have sulfuric duplicates) |
| Washing Recipes | 92 | 63 | 29 | 68% |
| Crystallizing Recipes | 98 | 63 | 35 | 64% (missing items) |
| Processing Chains | 63 | 51 | 12 | 81% |
| PRC Recipes | 75 | 54 | 21 | 72% |
| Nugget Recipes | ~90 | 0 | ~90 | 0% (need basic conversions) |
| Plate Recipes | ~90 | - | - | N/A (Create mod future) |

## Recommended Priority Fixes

### Immediate (Game-Breaking)
1. Fix gas fluid client registration (1 line code change)
2. Add missing crystal registrations (35 metals)
3. Create visible fluid base textures

### High Priority
4. Remove duplicate sulfuric acid dissolution recipes (66 elements)
5. Add missing dissolution recipes (12 metals)
6. Add missing washing recipes (29 elements)

### Medium Priority
7. Add nugget conversion recipes (9 nuggets ↔ 1 ingot)

### Low Priority
8. Add PRC recipes for super-heavy elements
9. Standardize recipe formats
10. Add uses for dead-end items

### Future Features (Not Bugs)
11. Create mod integration for plate pressing recipes

## Files Requiring Changes

### Code Files
- `/src/main/java/com/hecookin/chemlibmekanized/registry/ChemLibFluidRegistry.java` (line 306)
- `/src/main/java/com/hecookin/chemlibmekanized/registry/MetalCrystalRegistry.java` (CRYSTAL_METALS array)

### Texture Files
- `/src/main/resources/assets/chemlibmekanized/textures/block/fluid/liquid_still.png`
- `/src/main/resources/assets/chemlibmekanized/textures/block/fluid/liquid_flow.png`

### Recipe Directories
- `/src/main/resources/data/chemlibmekanized/recipe/dissolution/`
- `/src/main/resources/data/chemlibmekanized/recipe/washing/`
- `/src/main/resources/data/chemlibmekanized/recipe/reaction/`

## Conclusion
Most issues are data/configuration problems that can be fixed by adding missing recipes and registrations. Only one actual code bug found (gas fluid registration). The mod framework is solid but needs comprehensive recipe coverage to be fully playable.