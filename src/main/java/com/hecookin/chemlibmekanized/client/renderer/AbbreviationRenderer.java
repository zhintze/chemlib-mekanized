package com.hecookin.chemlibmekanized.client.renderer;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.Config;
import com.hecookin.chemlibmekanized.api.Chemical;
import com.hecookin.chemlibmekanized.api.ChemicalType;
import com.hecookin.chemlibmekanized.api.MatterState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Supplier;

public class AbbreviationRenderer extends BlockEntityWithoutLevelRenderer {

    public static final Supplier<BlockEntityWithoutLevelRenderer> INSTANCE = () -> new AbbreviationRenderer();

    public static final IClientItemExtensions RENDERER = new IClientItemExtensions() {
        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return INSTANCE.get();
        }
    };

    public AbbreviationRenderer() {
        super(null, null);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack,
                           MultiBufferSource buffer, int light, int overlay) {

        if (!(stack.getItem() instanceof Chemical chemical)) {
            return;
        }


        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = getModelForItem(stack, chemical);


        if (model != null) {
            poseStack.pushPose();

            // Apply context-specific transformations
            applyContextTransformations(poseStack, context);

            // Render the base model
            itemRenderer.render(stack, context, false, poseStack, buffer, light, overlay, model);

            // Render abbreviation overlay if configured
            renderAbbreviation(chemical, poseStack, context, buffer, light);

            poseStack.popPose();
        } else {
            ChemlibMekanized.LOGGER.warn("AbbreviationRenderer: No model found for chemical {}", chemical.getChemicalName());
        }
    }

    private BakedModel getModelForItem(ItemStack stack, Chemical chemical) {
        Minecraft minecraft = Minecraft.getInstance();

        // Get the item's registry name for debugging
        ResourceLocation itemId = minecraft.level != null ?
            minecraft.level.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ITEM)
                .getKey(stack.getItem()) : null;


        // Build template model name based on chemical type and matter state
        String templateName = getTemplateModelName(chemical);


        // Get the template model directly from model manager
        ModelResourceLocation templateLocation = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, templateName), "inventory");

        BakedModel model = minecraft.getModelManager().getModel(templateLocation);


        return model;
    }

    private String getTemplateModelName(Chemical chemical) {
        // Determine template based on chemical type and matter state
        String prefix = chemical.getChemicalType() == ChemicalType.ELEMENT ? "element" : "compound";
        String suffix = switch (chemical.getMatterState()) {
            case SOLID -> "solid";
            case LIQUID -> "liquid";
            case GAS -> "gas";
        };

        // Handle special compound dust state (used by some compounds)
        if (chemical.getChemicalType() == ChemicalType.COMPOUND) {
            // Some compounds use "dust" instead of "solid" - could be improved with better logic
            String chemicalName = chemical.getChemicalName().toLowerCase();
            if (chemicalName.contains("oxide") || chemicalName.contains("sulfate") ||
                chemicalName.contains("chromate") || chemicalName.contains("purple") ||
                chemicalName.contains("iodide")) {
                suffix = "dust";
            }
        }

        return prefix + "_" + suffix + "_model";
    }

    private void applyContextTransformations(PoseStack poseStack, ItemDisplayContext context) {
        switch (context) {
            case GUI:
                // No additional transformations needed for GUI
                break;
            // Note: ITEM_FRAME may be named differently in NeoForge 1.21.1
            // Commenting out for now
            // case ITEM_FRAME:
            //     // Small scaling for item frames
            //     poseStack.scale(0.5f, 0.5f, 0.5f);
            //     break;
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
                // First person hand positioning
                poseStack.translate(0.0, 0.0, 0.0);
                break;
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
                // Third person hand scaling
                poseStack.scale(0.375f, 0.375f, 0.375f);
                poseStack.translate(0.0, 0.25, 0.0);
                break;
            case GROUND:
                // Ground item rendering
                poseStack.scale(0.25f, 0.25f, 0.25f);
                break;
            case FIXED:
                // Item stands and displays
                poseStack.scale(0.5f, 0.5f, 0.5f);
                break;
            default:
                break;
        }
    }

    private void renderAbbreviation(Chemical chemical, PoseStack poseStack, ItemDisplayContext context,
                                  MultiBufferSource buffer, int light) {
        // Check configuration before rendering abbreviations
        if (!Config.RENDER_ABBREVIATIONS.get()) {
            return;
        }

        String abbreviation = chemical.getAbbreviation();
        if (abbreviation == null || abbreviation.isEmpty()) {
            return;
        }

        // Context-specific abbreviation rendering
        switch (context) {
            case GUI:
                renderGuiAbbreviation(abbreviation, poseStack, buffer, light);
                break;
            // Note: ITEM_FRAME may be named differently in NeoForge 1.21.1
            // case ITEM_FRAME:
            //     renderItemFrameAbbreviation(abbreviation, poseStack, buffer, light);
            //     break;
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
                renderHandAbbreviation(abbreviation, poseStack, buffer, light, context);
                break;
            case GROUND:
                renderGroundAbbreviation(abbreviation, poseStack, buffer, light);
                break;
            case FIXED:
                renderFixedAbbreviation(abbreviation, poseStack, buffer, light);
                break;
            default:
                break;
        }
    }

    private void renderGuiAbbreviation(String abbreviation, PoseStack poseStack,
                                     MultiBufferSource buffer, int light) {
        // Render abbreviation overlay for GUI context (inventory, creative menu)
        Minecraft minecraft = Minecraft.getInstance();
        poseStack.pushPose();

        // Position text in bottom-right corner of item
        poseStack.translate(0.75, 0.75, 0.1);
        poseStack.scale(0.5f, 0.5f, 1.0f);

        // Render text with shadow for readability
        minecraft.font.drawInBatch(abbreviation, 0, 0, 0xFFFFFF, true,
                                  poseStack.last().pose(), buffer,
                                  net.minecraft.client.gui.Font.DisplayMode.NORMAL,
                                  0, light);

        poseStack.popPose();
    }

    private void renderItemFrameAbbreviation(String abbreviation, PoseStack poseStack,
                                           MultiBufferSource buffer, int light) {
        // Render abbreviation for item frame context
        Minecraft minecraft = Minecraft.getInstance();
        poseStack.pushPose();

        poseStack.translate(0.5, 0.5, 0.1);
        poseStack.scale(0.3f, 0.3f, 1.0f);

        minecraft.font.drawInBatch(abbreviation, 0, 0, 0xFFFFFF, true,
                                  poseStack.last().pose(), buffer,
                                  net.minecraft.client.gui.Font.DisplayMode.NORMAL,
                                  0, light);

        poseStack.popPose();
    }

    private void renderHandAbbreviation(String abbreviation, PoseStack poseStack,
                                      MultiBufferSource buffer, int light, ItemDisplayContext context) {
        // Render abbreviation for hand-held contexts
        Minecraft minecraft = Minecraft.getInstance();
        poseStack.pushPose();

        // Adjust positioning based on hand
        if (context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND ||
            context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
            poseStack.translate(-0.2, 0.2, 0.1);
        } else {
            poseStack.translate(0.2, 0.2, 0.1);
        }

        poseStack.scale(0.2f, 0.2f, 1.0f);

        minecraft.font.drawInBatch(abbreviation, 0, 0, 0xFFFFFF, true,
                                  poseStack.last().pose(), buffer,
                                  net.minecraft.client.gui.Font.DisplayMode.NORMAL,
                                  0, light);

        poseStack.popPose();
    }

    private void renderGroundAbbreviation(String abbreviation, PoseStack poseStack,
                                        MultiBufferSource buffer, int light) {
        // Render abbreviation for ground item context
        Minecraft minecraft = Minecraft.getInstance();
        poseStack.pushPose();

        poseStack.translate(0.0, 1.2, 0.0);
        poseStack.scale(0.1f, 0.1f, 1.0f);
        poseStack.mulPose(minecraft.getEntityRenderDispatcher().cameraOrientation());

        minecraft.font.drawInBatch(abbreviation, 0, 0, 0xFFFFFF, true,
                                  poseStack.last().pose(), buffer,
                                  net.minecraft.client.gui.Font.DisplayMode.NORMAL,
                                  0, light);

        poseStack.popPose();
    }

    private void renderFixedAbbreviation(String abbreviation, PoseStack poseStack,
                                       MultiBufferSource buffer, int light) {
        // Render abbreviation for fixed display context (item stands)
        Minecraft minecraft = Minecraft.getInstance();
        poseStack.pushPose();

        poseStack.translate(0.5, 0.5, 0.1);
        poseStack.scale(0.25f, 0.25f, 1.0f);

        minecraft.font.drawInBatch(abbreviation, 0, 0, 0xFFFFFF, true,
                                  poseStack.last().pose(), buffer,
                                  net.minecraft.client.gui.Font.DisplayMode.NORMAL,
                                  0, light);

        poseStack.popPose();
    }
}