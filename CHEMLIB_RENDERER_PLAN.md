# ChemLib Custom Renderer Implementation Plan
## Complete Port to NeoForge 1.21.1

### **Project Goal**
Implement ChemLib's complete custom rendering system with full abbreviation support, configurable display, and all rendering contexts for seamless Mekanism integration.

---

## **Implementation Architecture**

### **Phase 1: Core Infrastructure**

#### **1.1 Chemical Interface System**
```java
// com.hecookin.chemlibmekanized.api.Chemical.java
public interface Chemical {
    String getAbbreviation();
    int getColor();
    String getChemicalName();
    MatterState getMatterState();
    ChemicalType getChemicalType(); // ELEMENT, COMPOUND
}
```

#### **1.2 Matter State and Type Enums**
```java
// com.hecookin.chemlibmekanized.api.MatterState.java
public enum MatterState {
    SOLID, LIQUID, GAS
}

// com.hecookin.chemlibmekanized.api.ChemicalType.java
public enum ChemicalType {
    ELEMENT, COMPOUND
}

// com.hecookin.chemlibmekanized.api.ItemType.java
public enum ItemType {
    DUST, NUGGET, INGOT, PLATE // For future chemical items
}
```

### **Phase 2: Custom Renderer System**

#### **2.1 AbbreviationRenderer Core**
```java
// com.hecookin.chemlibmekanized.client.renderer.AbbreviationRenderer.java
public class AbbreviationRenderer extends BlockEntityWithoutLevelRenderer {

    // Static instance for IClientItemExtensions
    public static final Supplier<BlockEntityWithoutLevelRenderer> INSTANCE;
    public static final IClientItemExtensions RENDERER;

    // Model selection logic
    private ModelResourceLocation getModelForItem(ItemStack stack);

    // Context-specific rendering with transformations
    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context,
                           PoseStack poseStack, MultiBufferSource buffer,
                           int light, int overlay);

    // Abbreviation text overlay rendering
    private void renderAbbreviation(Chemical chemical, PoseStack poseStack,
                                  ItemDisplayContext context);
}
```

#### **2.2 Model Selection Logic**
Dynamic model resolution based on item properties:
- **Elements**: `element_{matterstate}_model.json`
- **Compounds**: `compound_{matterstate}_model.json`
- **Chemical Items**: `chemical_{itemtype}_model.json`

#### **2.3 Rendering Context Support**
- **GUI**: Inventory, creative menu with flat lighting
- **ITEM_FRAME**: Wall-mounted display
- **FIRST_PERSON_LEFT/RIGHT_HAND**: Hand-held rendering
- **THIRD_PERSON_LEFT/RIGHT_HAND**: Third-person perspective
- **GROUND**: Dropped item rendering
- **FIXED**: Item stands and displays

### **Phase 3: Model Template System**

#### **3.1 Base Model Templates**
Create all ChemLib model templates with layered textures:

```json
// element_solid_model.json
{
  "parent": "minecraft:item/generated",
  "textures": {
    "layer0": "chemlibmekanized:items/element_solid_layer_0",
    "layer1": "chemlibmekanized:items/element_solid_layer_1"
  }
}
```

**Complete Template Set**:
- `element_solid_model.json`, `element_liquid_model.json`, `element_gas_model.json`
- `compound_solid_model.json`, `compound_liquid_model.json`, `compound_gas_model.json`, `compound_dust_model.json`
- `chemical_dust_model.json`, `chemical_nugget_model.json`, `chemical_ingot_model.json`, `chemical_plate_model.json`

#### **3.2 Individual Item Models**
All chemical items reference `builtin_entity.json`:
```json
{
  "parent": "chemlibmekanized:item/builtin_entity"
}
```

#### **3.3 Model Registration**
Register all template models in `ModelEvent.RegisterAdditional`:
```java
event.register(new ModelResourceLocation(
    ResourceLocation.fromNamespaceAndPath(MODID, "element_solid_model"), "standalone"));
```

### **Phase 4: Item Integration**

#### **4.1 Chemical Interface Implementation**
Update existing item classes:
```java
// ExtractedElementItem.java
public class ExtractedElementItem extends Item implements Chemical {

    @Override
    public String getAbbreviation() {
        return elementData.abbreviation;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(AbbreviationRenderer.RENDERER);
    }

    // ... other Chemical interface methods
}
```

#### **4.2 Color System Integration**
Maintain existing color handler registration with updated interface:
```java
// ChemLibColorProviders.java
event.register((stack, tintIndex) -> {
    if (stack.getItem() instanceof Chemical chemical) {
        return tintIndex > 0 ? -1 : chemical.getColor();
    }
    return -1;
}, item);
```

### **Phase 5: Configuration System**

#### **5.1 Global Abbreviation Toggle**
```java
// Config.java additions
public static final ModConfigSpec.BooleanValue RENDER_ABBREVIATIONS = BUILDER
    .comment("Whether to render chemical abbreviations on items")
    .define("renderAbbreviations", true);
```

#### **5.2 Configuration Integration**
Renderer checks config before displaying abbreviations:
```java
if (Config.RENDER_ABBREVIATIONS.get() && chemical != null) {
    renderAbbreviation(chemical, poseStack, context);
}
```

### **Phase 6: NeoForge 1.21.1 Compatibility**

#### **6.1 API Updates**
- `Vector3f` → `Axis` for rotations
- `ForgeHooksClient` → `ClientHooks`
- `IClientItemExtensions` package path updates
- Event bus subscription updates

#### **6.2 Model Resource Location**
- Use `"standalone"` variant for template model registration
- Proper `ResourceLocation.fromNamespaceAndPath()` usage

---

## **Mekanism Integration Preparation**

### **Chemical Wrapper System**
The Chemical interface enables seamless Mekanism integration:

```java
// Future: MekanismChemicalWrapper.java
public class MekanismChemicalWrapper {
    public static Gas createGas(Chemical chemical) {
        // Convert Chemical → Mekanism Gas
    }

    public static Slurry createSlurry(Chemical chemical) {
        // Convert Chemical → Mekanism Slurry
    }

    public static Pigment createPigment(Chemical chemical) {
        // Convert Chemical → Mekanism Pigment
    }
}
```

---

## **Implementation Phases**

### **Phase 1: Foundation (Day 1)**
1. Create Chemical interface and enums
2. Implement AbbreviationRenderer core structure
3. Update ExtractedElementItem and ExtractedCompoundItem

### **Phase 2: Rendering (Day 1-2)**
1. Implement all rendering contexts with transformations
2. Add abbreviation text overlay system
3. Create model selection logic

### **Phase 3: Models (Day 2)**
1. Create all model template files
2. Update individual item models to use builtin_entity
3. Register template models in client events

### **Phase 4: Configuration (Day 2)**
1. Add abbreviation toggle to config
2. Integrate config checks in renderer
3. Test configuration functionality

### **Phase 5: Testing & Polish (Day 3)**
1. Test all rendering contexts
2. Verify color tinting works correctly
3. Ensure abbreviations display properly
4. Performance optimization

---

## **File Structure**

```
src/main/java/com/hecookin/chemlibmekanized/
├── api/
│   ├── Chemical.java
│   ├── MatterState.java
│   ├── ChemicalType.java
│   └── ItemType.java
├── client/
│   └── renderer/
│       └── AbbreviationRenderer.java
├── items/
│   ├── ExtractedElementItem.java (updated)
│   └── ExtractedCompoundItem.java (updated)
└── Config.java (updated)

src/main/resources/assets/chemlibmekanized/models/item/
├── builtin_entity.json
├── element_solid_model.json
├── element_liquid_model.json
├── element_gas_model.json
├── compound_solid_model.json
├── compound_liquid_model.json
├── compound_gas_model.json
├── compound_dust_model.json
├── chemical_dust_model.json
├── chemical_nugget_model.json
├── chemical_ingot_model.json
├── chemical_plate_model.json
└── [all individual chemical models updated]
```

---

## **Success Criteria**

### **Functional Requirements**
- ✅ All 296 chemical items display proper textures
- ✅ Abbreviations appear on items in all contexts
- ✅ Color tinting works correctly with layered textures
- ✅ Configuration toggle controls abbreviation display
- ✅ Performance comparable to ChemLib

### **Technical Requirements**
- ✅ NeoForge 1.21.1 API compatibility
- ✅ Clean separation of concerns (api, client, items)
- ✅ Mekanism integration preparation through Chemical interface
- ✅ Maintainable and extensible code structure

### **User Experience**
- ✅ Identical visual experience to ChemLib
- ✅ Smooth rendering in all contexts
- ✅ Clear chemical identification through abbreviations
- ✅ Configurable display preferences

---

## **Risk Mitigation**

### **API Compatibility Risks**
- **Risk**: NeoForge 1.21.1 API differences
- **Mitigation**: Research exact API equivalents, create compatibility wrappers

### **Performance Risks**
- **Risk**: Custom renderer performance impact
- **Mitigation**: Profile rendering, optimize frequently called methods

### **Integration Risks**
- **Risk**: Complex interaction with existing systems
- **Mitigation**: Maintain existing interfaces, add new functionality incrementally

---

This plan provides a complete roadmap for implementing ChemLib's custom renderer system with full fidelity while preparing for seamless Mekanism integration in future phases.