package com.hecookin.chemlibmekanized.extraction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hecookin.chemlibmekanized.ChemlibMekanized;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ChemLibDataExtractor {
    private static final Gson GSON = new Gson();
    private static final String CHEMLIB_PATH = "/home/keroppi/Development/Minecraft/ChemLib/src/main/resources/data/chemlib/";

    public static class ElementData {
        public final String name;
        public final int atomicNumber;
        public final String abbreviation;
        public final String group;
        public final String period;
        public final String matterState;
        public final String metalType;
        public final boolean artificial;
        public final boolean hasItem;
        public final String color;
        public final FluidProperties fluidProperties;
        public final List<EffectData> effects;

        public ElementData(String name, int atomicNumber, String abbreviation, String group,
                          String period, String matterState, String metalType, boolean artificial,
                          boolean hasItem, String color, FluidProperties fluidProperties,
                          List<EffectData> effects) {
            this.name = name;
            this.atomicNumber = atomicNumber;
            this.abbreviation = abbreviation;
            this.group = group;
            this.period = period;
            this.matterState = matterState;
            this.metalType = metalType;
            this.artificial = artificial;
            this.hasItem = hasItem;
            this.color = color;
            this.fluidProperties = fluidProperties;
            this.effects = effects;
        }
    }

    public static class CompoundData {
        public final String name;
        public final String matterState;
        public final boolean hasItem;
        public final String description;
        public final String color;
        public final FluidProperties fluidProperties;
        public final List<ComponentData> components;
        public final List<EffectData> effects;

        public CompoundData(String name, String matterState, boolean hasItem, String description,
                           String color, FluidProperties fluidProperties, List<ComponentData> components,
                           List<EffectData> effects) {
            this.name = name;
            this.matterState = matterState;
            this.hasItem = hasItem;
            this.description = description;
            this.color = color;
            this.fluidProperties = fluidProperties;
            this.components = components;
            this.effects = effects;
        }
    }

    public static class ComponentData {
        public final String name;
        public final int count;

        public ComponentData(String name, int count) {
            this.name = name;
            this.count = count;
        }
    }

    public static class FluidProperties {
        public final int density;
        public final int lightLevel;
        public final int viscosity;
        public final int temperature;
        public final double motionScale;
        public final double fallDistanceModifier;
        public final String pathType;
        public final boolean pushEntity;
        public final boolean canSwim;
        public final boolean canDrown;
        public final boolean canHydrate;
        public final boolean canExtinguish;
        public final boolean supportsBoating;
        public final boolean canConvertToSource;

        public FluidProperties(int density, int lightLevel, int viscosity, int temperature,
                             double motionScale, double fallDistanceModifier, String pathType,
                             boolean pushEntity, boolean canSwim, boolean canDrown, boolean canHydrate,
                             boolean canExtinguish, boolean supportsBoating, boolean canConvertToSource) {
            this.density = density;
            this.lightLevel = lightLevel;
            this.viscosity = viscosity;
            this.temperature = temperature;
            this.motionScale = motionScale;
            this.fallDistanceModifier = fallDistanceModifier;
            this.pathType = pathType;
            this.pushEntity = pushEntity;
            this.canSwim = canSwim;
            this.canDrown = canDrown;
            this.canHydrate = canHydrate;
            this.canExtinguish = canExtinguish;
            this.supportsBoating = supportsBoating;
            this.canConvertToSource = canConvertToSource;
        }
    }

    public static class EffectData {
        public final String location;
        public final int duration;
        public final int amplifier;

        public EffectData(String location, int duration, int amplifier) {
            this.location = location;
            this.duration = duration;
            this.amplifier = amplifier;
        }

        public MobEffectInstance toMobEffectInstance() {
            MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.parse(location));
            return new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect), duration, amplifier);
        }
    }

    public static List<ElementData> extractElements() {
        List<ElementData> elements = new ArrayList<>();
        Path elementsPath = Paths.get(CHEMLIB_PATH + "elements.json");

        try (FileReader reader = new FileReader(elementsPath.toFile())) {
            JsonObject root = GSON.fromJson(reader, JsonObject.class);
            JsonArray elementsArray = root.getAsJsonArray("elements");

            for (JsonElement element : elementsArray) {
                JsonObject elementObj = element.getAsJsonObject();

                String name = elementObj.get("name").getAsString();
                int atomicNumber = elementObj.get("atomic_number").getAsInt();
                String abbreviation = elementObj.get("abbreviation").getAsString();
                String group = elementObj.get("group").getAsString();
                String period = elementObj.get("period").getAsString();
                String matterState = elementObj.get("matter_state").getAsString();
                String metalType = elementObj.get("metal_type").getAsString();
                boolean artificial = elementObj.has("artificial") && elementObj.get("artificial").getAsBoolean();
                boolean hasItem = elementObj.has("has_item") && elementObj.get("has_item").getAsBoolean();
                String color = elementObj.get("color").getAsString();

                FluidProperties fluidProps = null;
                if (elementObj.has("fluid_properties")) {
                    JsonObject fluidObj = elementObj.getAsJsonObject("fluid_properties");
                    fluidProps = new FluidProperties(
                        fluidObj.get("density").getAsInt(),
                        fluidObj.get("light_level").getAsInt(),
                        fluidObj.get("viscosity").getAsInt(),
                        fluidObj.get("temperature").getAsInt(),
                        fluidObj.get("motion_scale").getAsDouble(),
                        fluidObj.get("fall_distance_modifier").getAsDouble(),
                        fluidObj.get("path_type").getAsString(),
                        fluidObj.get("push_entity").getAsBoolean(),
                        fluidObj.get("can_swim").getAsBoolean(),
                        fluidObj.get("can_drown").getAsBoolean(),
                        fluidObj.get("can_hydrate").getAsBoolean(),
                        fluidObj.get("can_extinguish").getAsBoolean(),
                        fluidObj.get("supports_boating").getAsBoolean(),
                        fluidObj.get("can_convert_to_source").getAsBoolean()
                    );
                }

                List<EffectData> effects = new ArrayList<>();
                if (elementObj.has("effect")) {
                    JsonArray effectsArray = elementObj.getAsJsonArray("effect");
                    for (JsonElement effectElement : effectsArray) {
                        JsonObject effectObj = effectElement.getAsJsonObject();
                        effects.add(new EffectData(
                            effectObj.get("location").getAsString(),
                            Integer.parseInt(effectObj.get("duration").getAsString()),
                            effectObj.get("amplifier").getAsInt()
                        ));
                    }
                }

                elements.add(new ElementData(name, atomicNumber, abbreviation, group, period,
                                           matterState, metalType, artificial, hasItem, color,
                                           fluidProps, effects));
            }
        } catch (IOException e) {
            ChemlibMekanized.LOGGER.error("Failed to read elements.json", e);
        }

        return elements;
    }

    public static List<CompoundData> extractCompounds() {
        List<CompoundData> compounds = new ArrayList<>();
        Path compoundsPath = Paths.get(CHEMLIB_PATH + "compounds.json");

        try (FileReader reader = new FileReader(compoundsPath.toFile())) {
            JsonObject root = GSON.fromJson(reader, JsonObject.class);
            JsonArray compoundsArray = root.getAsJsonArray("compounds");

            for (JsonElement compound : compoundsArray) {
                JsonObject compoundObj = compound.getAsJsonObject();

                String name = compoundObj.get("name").getAsString();
                String matterState = compoundObj.get("matter_state").getAsString();
                boolean hasItem = compoundObj.has("has_item") && compoundObj.get("has_item").getAsBoolean();
                boolean hasFluid = compoundObj.has("has_fluid") && compoundObj.get("has_fluid").getAsBoolean();
                String description = compoundObj.has("description") ? compoundObj.get("description").getAsString() : "";
                String color = compoundObj.get("color").getAsString();

                // Skip compounds that already have fluids (like water which uses vanilla)
                if (hasFluid) {
                    continue;
                }

                FluidProperties fluidProps = null;
                if (compoundObj.has("fluid_properties")) {
                    JsonObject fluidObj = compoundObj.getAsJsonObject("fluid_properties");
                    fluidProps = new FluidProperties(
                        fluidObj.get("density").getAsInt(),
                        fluidObj.get("light_level").getAsInt(),
                        fluidObj.get("viscosity").getAsInt(),
                        fluidObj.get("temperature").getAsInt(),
                        fluidObj.get("motion_scale").getAsDouble(),
                        fluidObj.get("fall_distance_modifier").getAsDouble(),
                        fluidObj.get("path_type").getAsString(),
                        fluidObj.get("push_entity").getAsBoolean(),
                        fluidObj.get("can_swim").getAsBoolean(),
                        fluidObj.get("can_drown").getAsBoolean(),
                        fluidObj.get("can_hydrate").getAsBoolean(),
                        fluidObj.get("can_extinguish").getAsBoolean(),
                        fluidObj.get("supports_boating").getAsBoolean(),
                        fluidObj.get("can_convert_to_source").getAsBoolean()
                    );
                }

                List<ComponentData> components = new ArrayList<>();
                if (compoundObj.has("components")) {
                    JsonArray componentsArray = compoundObj.getAsJsonArray("components");
                    for (JsonElement componentElement : componentsArray) {
                        JsonObject componentObj = componentElement.getAsJsonObject();
                        String componentName = componentObj.get("name").getAsString();
                        int count = componentObj.has("count") ? componentObj.get("count").getAsInt() : 1;
                        components.add(new ComponentData(componentName, count));
                    }
                }

                List<EffectData> effects = new ArrayList<>();
                if (compoundObj.has("effect")) {
                    JsonArray effectsArray = compoundObj.getAsJsonArray("effect");
                    for (JsonElement effectElement : effectsArray) {
                        JsonObject effectObj = effectElement.getAsJsonObject();
                        effects.add(new EffectData(
                            effectObj.get("location").getAsString(),
                            Integer.parseInt(effectObj.get("duration").getAsString()),
                            effectObj.get("amplifier").getAsInt()
                        ));
                    }
                }

                compounds.add(new CompoundData(name, matterState, hasItem, description, color,
                                             fluidProps, components, effects));
            }
        } catch (IOException e) {
            ChemlibMekanized.LOGGER.error("Failed to read compounds.json", e);
        }

        return compounds;
    }
}