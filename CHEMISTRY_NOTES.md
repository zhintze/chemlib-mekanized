# Chemistry Notes for ChemLibMekanized

## Important Chemistry Principles

### Why the Original Hydrocarbon Recipes Were Wrong:
- **CH₄ + H₂ ≠ C₂H₆** - You can't just "add" methane and hydrogen to make ethane
- **C₂H₆ + CH₄ ≠ C₃H₈** - Alkanes don't combine by simple addition
- **C₃H₈ + CH₄ ≠ C₄H₁₀** - This violates basic organic chemistry

### How Hydrocarbons Are Actually Made:
1. **From synthesis gas (CO + H₂)** - Fischer-Tropsch process
2. **Catalytic cracking** - Breaking larger molecules into smaller ones
3. **Polymerization** - Building chains through radical or ionic mechanisms
4. **Steam cracking** - High-temperature decomposition

## Scientifically Accurate Gas Combinations We Can Implement

### ✅ Currently Correct:
- N₂ + 3H₂ → 2NH₃ (Haber-Bosch process)
- 2NO + O₂ → 2NO₂ (Nitrogen dioxide formation)
- SO₃ + H₂O → H₂SO₄ (Sulfuric acid formation)

### ✅ New Accurate Recipes Added:
- 2CH₄ + O₂ → 2CO + 4H₂ (Partial oxidation)
- CH₄ + CO₂ → 2CO + 2H₂ (Dry reforming)
- 4NH₃ + 5O₂ → 4NO + 6H₂O (Ostwald process)
- 2H₂S + 3O₂ → 2SO₂ + 2H₂O (H₂S oxidation)

### 🚫 Removed (Duplicates/Incorrect):
- SO₂ + O₂ → SO₃ (Mekanism already has this)
- H₂ + Cl₂ → HCl (Mekanism already has this)
- All incorrect hydrocarbon "addition" reactions

## Reactions Needing Multiple Outputs
These are real but need Pressurized Reaction Chamber (supports multiple outputs):

### Steam Reforming:
- CH₄ + H₂O → CO + 3H₂

### Water Gas Shift:
- CO + H₂O → CO₂ + H₂

### Complete Combustion:
- CH₄ + 2O₂ → CO₂ + 2H₂O

### Claus Process:
- 2H₂S + SO₂ → 3S + 2H₂O

## Future Considerations

### Missing Gases We Could Add:
1. **Ozone (O₃)** - 3O₂ → 2O₃ (with electrical discharge)
2. **Hydrogen Peroxide (H₂O₂)** - H₂ + O₂ → H₂O₂
3. **Nitrous Oxide (N₂O)** - 2NO + H₂ → N₂O + H₂O
4. **Phosphine (PH₃)** - P + 3H₂ → PH₃
5. **Silane (SiH₄)** - Si + 2H₂ → SiH₄

### Noble Gas Compounds (Exotic but Real):
- Xe + F₂ → XeF₂ (Xenon difluoride)
- Xe + 2F₂ → XeF₄ (Xenon tetrafluoride)
- Kr + F₂ → KrF₂ (Krypton difluoride)

### Industrial Processes to Consider:
1. **Wacker Process**: C₂H₄ + PdCl₂ + H₂O → CH₃CHO (acetaldehyde)
2. **Oxo Process**: Alkene + CO + H₂ → Aldehyde
3. **Monsanto Process**: CH₃OH + CO → CH₃COOH (acetic acid)

## Gameplay vs Realism Balance
While scientific accuracy is important, we need to consider:
- Player understanding and intuition
- Recipe complexity and crafting chains
- Balance with existing Mekanism systems
- Fun factor and progression

## Recommended Next Steps:
1. Implement multi-output reactions in Pressurized Reaction Chamber
2. Add catalyst items for reactions that require them
3. Consider temperature/pressure requirements
4. Add more complex organic synthesis chains