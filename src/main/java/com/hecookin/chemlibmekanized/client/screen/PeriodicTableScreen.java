package com.hecookin.chemlibmekanized.client.screen;

import com.hecookin.chemlibmekanized.ChemlibMekanized;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor;
import com.hecookin.chemlibmekanized.extraction.ChemLibDataExtractor.ElementData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PeriodicTableScreen extends Screen {

    private static final ResourceLocation PERIODIC_TABLE = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "textures/gui/periodic_table.png");
    private static final ResourceLocation ELEMENT_TOOLTIP_BASE = ResourceLocation.fromNamespaceAndPath(ChemlibMekanized.MODID, "textures/gui/elements/");

    private static final int TABLE_WIDTH = 500;
    private static final int TABLE_HEIGHT = 254;
    private static final int TEXTURE_WIDTH = 2000;
    private static final int TEXTURE_HEIGHT = 1016;

    private static final double ELEMENT_WIDTH = 27.75;
    private static final double ELEMENT_HEIGHT = 26.9;
    private static final int TOOLTIP_SIZE = 40;

    private final List<ElementData> elements;
    private ElementData hoveredElement = null;

    public PeriodicTableScreen() {
        super(Component.translatable("screen.chemlibmekanized.periodic_table"));
        this.elements = ChemLibDataExtractor.extractElements();
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // Render dark background
        this.renderBackground(graphics, mouseX, mouseY, partialTick);

        // Calculate position to center the table
        int x = (this.width - TABLE_WIDTH) / 2;
        int y = (this.height - TABLE_HEIGHT) / 2;

        // Draw the periodic table background
        graphics.blit(PERIODIC_TABLE, x, y, 0, 0, TABLE_WIDTH, TABLE_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        // Detect which element is being hovered
        hoveredElement = getHoveredElement(mouseX - x, mouseY - y);

        // Render element tooltip if hovering
        if (hoveredElement != null) {
            renderElementTooltip(graphics, mouseX, mouseY);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private ElementData getHoveredElement(int relativeX, int relativeY) {
        // Check if mouse is within table bounds
        if (relativeX < 0 || relativeX > TABLE_WIDTH || relativeY < 0 || relativeY > TABLE_HEIGHT) {
            return null;
        }

        // Calculate grid position
        int gridX = (int)(relativeX / ELEMENT_WIDTH);
        int gridY = (int)(relativeY / ELEMENT_HEIGHT);

        // Handle main periodic table area
        if (gridY <= 6) { // Periods 1-7 main area
            for (ElementData element : elements) {
                int period = Integer.parseInt(element.period);
                int group = Integer.parseInt(element.group);

                // Special handling for lanthanides and actinides
                if ((period == 6 || period == 7) && group == 3 && element.atomicNumber > 57) {
                    continue; // These are shown in the bottom rows
                }

                // Calculate expected position
                int expectedX = group - 1;
                int expectedY = period - 1;

                if (gridX == expectedX && gridY == expectedY) {
                    return element;
                }
            }
        }

        // Handle lanthanides (row 8)
        if (gridY == 7) {
            int lanthanideIndex = 57 + gridX; // La starts at 57
            if (lanthanideIndex <= 71) { // Lu is 71
                return findElementByAtomicNumber(lanthanideIndex);
            }
        }

        // Handle actinides (row 9)
        if (gridY == 8) {
            int actinideIndex = 89 + gridX; // Ac starts at 89
            if (actinideIndex <= 103) { // Lr is 103
                return findElementByAtomicNumber(actinideIndex);
            }
        }

        return null;
    }

    private ElementData findElementByAtomicNumber(int atomicNumber) {
        return elements.stream()
            .filter(e -> e.atomicNumber == atomicNumber)
            .findFirst()
            .orElse(null);
    }

    private void renderElementTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        if (hoveredElement == null) return;

        // Create tooltip text
        List<Component> tooltip = new ArrayList<>();
        int elementColor = hoveredElement.color != null && !hoveredElement.color.isEmpty() ?
            Integer.parseUnsignedInt(hoveredElement.color.replace("#", ""), 16) : 0xFFFFFF;
        tooltip.add(Component.literal(hoveredElement.name)
            .withStyle(style -> style.withColor(elementColor)));
        tooltip.add(Component.literal("Symbol: " + hoveredElement.abbreviation));
        tooltip.add(Component.literal("Atomic Number: " + hoveredElement.atomicNumber));
        tooltip.add(Component.literal("Group: " + hoveredElement.group + ", Period: " + hoveredElement.period));

        // Add Mekanism integration info
        String matterState = hoveredElement.matterState;
        if (matterState != null) {
            tooltip.add(Component.literal("Matter State: " + matterState));
        }

        // Check if we have this element registered as a chemical
        if (hoveredElement.hasItem) {
            tooltip.add(Component.literal("Processing Available").withStyle(style -> style.withColor(0x00FF00)));
        }

        // Render the tooltip
        graphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // Helper method to get group number from position
    private int getGroupFromPosition(int x) {
        // Groups 1-18 mapping to x positions
        if (x == 0) return 1;
        if (x == 17) return 18;
        if (x >= 1 && x <= 11) return x + 1;
        if (x >= 12 && x <= 16) return x + 1;
        return -1;
    }

    // Helper method to get period from y position
    private int getPeriodFromPosition(int y) {
        if (y >= 0 && y <= 6) return y + 1;
        return -1;
    }
}