package com.mcgamer.mcjjkp.gui.overlays;

import com.mcgamer.mcjjkp.item.ModItems;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniqueRegistry;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniques;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class TechniqueItemOverlay implements LayeredDraw.Layer {
    public static TechniqueItemOverlay instance = new TechniqueItemOverlay();

    protected static final ResourceLocation WIDGETS_LOCATION = ResourceLocation
            .withDefaultNamespace("textures/gui/widgets.png");
    protected static final ResourceLocation HOTBAR_SLOT_LOCATION = ResourceLocation
            .withDefaultNamespace("textures/gui/sprites/hud/hotbar_slot.png");
    protected static final ResourceLocation HOTBAR_SELECTION_LOCATION = ResourceLocation
            .withDefaultNamespace("textures/gui/sprites/hud/hotbar_selection.png");

    private static final Map<String, ItemStack> TECHNIQUE_ITEMS = new HashMap<>();

    private static String currentTechnique = null;
    private static ItemStack currentTechniqueWeapon = ItemStack.EMPTY;
    private static int lastSelectedSlot = -1;
    private static boolean isTechniqueSlotSelected = false;

    static {
        TECHNIQUE_ITEMS.put("Blood Edge", new ItemStack(ModItems.BLOOD_EDGE_ITEM.get()));
    }

    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().options.hideGui || Minecraft.getInstance().player == null || Minecraft.getInstance().player.isSpectator()) {
            return;
        }

        Player player = Minecraft.getInstance().player;

        // Check if player switched hotbar slots or went to offhand
        checkForSlotChange(player);

        // Only render if we have an active technique
        if (currentTechnique == null || !TECHNIQUE_ITEMS.containsKey(currentTechnique)) {
            return;
        }

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        // Position the slot to the left of the main hotbar
        // Standard hotbar starts at screenWidth/2 - 91, so we place our slot at screenWidth/2 - 91 - 29 (slot width + gap)
        int slotX = screenWidth / 2 - 91 - 32; // 29 for slot width + 3 for gap
        int slotY = screenHeight - 22;

        // Render the hotbar slot background
        guiGraphics.blit(HOTBAR_SLOT_LOCATION, slotX, slotY, 0, 0, 22, 22, 22, 22);

        // Get the technique item
        ItemStack techniqueItem = TECHNIQUE_ITEMS.get(currentTechnique);
        if (!techniqueItem.isEmpty()) {
            // Render the item in the slot
            guiGraphics.renderItem(techniqueItem, slotX + 3, slotY + 3);
            guiGraphics.renderItemDecorations(Minecraft.getInstance().font, techniqueItem, slotX + 3, slotY + 3);
        }

        // Optional: Add a subtle glow or highlight to indicate this is a special slot
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.setColor(1.0F, 0.8F, 0.8F, 0.3F); // Slight red tint
        guiGraphics.blit(HOTBAR_SLOT_LOCATION, slotX, slotY, 0, 0, 22, 22, 22, 22);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F); // Reset color
        RenderSystem.disableBlend();
    }

    private void checkForSlotChange(Player player) {
        int currentSelectedSlot = player.getInventory().selected;

        // If player changed hotbar slot, hide the technique slot
        if (lastSelectedSlot != -1 && currentSelectedSlot != lastSelectedSlot) {
            hideTechniqueSlot();
        }

        lastSelectedSlot = currentSelectedSlot;
    }

    /**
     * Call this method when a technique is activated to show the technique slot
     * @param techniqueName The name of the technique (must match a key in TECHNIQUE_ITEMS)
     */
    public static void showTechniqueSlot(String techniqueName) {
        if (TECHNIQUE_ITEMS.containsKey(techniqueName)) {
            currentTechnique = techniqueName;
            lastSelectedSlot = Minecraft.getInstance().player != null ?
                    Minecraft.getInstance().player.getInventory().selected : -1;
        }
    }

    /**
     * Call this method to hide the technique slot
     */
    public static void hideTechniqueSlot() {
        ExtensionTechniqueRegistry.getTechniqueByName(currentTechnique).get().deactivate(Minecraft.getInstance().player);

        currentTechnique = null;
        lastSelectedSlot = -1;
    }

    /**
     * Check if a technique slot is currently active
     */
    public static boolean isTechniqueSlotActive() {
        return currentTechnique != null;
    }

    /**
     * Get the current technique name
     */
    public static String getCurrentTechnique() {
        return currentTechnique;
    }

    /**
     * Get the item for the current technique
     */
    public static ItemStack getCurrentTechniqueItem() {
        if (currentTechnique != null && TECHNIQUE_ITEMS.containsKey(currentTechnique)) {
            return TECHNIQUE_ITEMS.get(currentTechnique);
        }
        return ItemStack.EMPTY;
    }

    /**
     * Register a new technique item mapping
     * These items are purely visual and exist ONLY in this special slot
     */
    public static void registerTechniqueItem(String techniqueName, ItemStack item) {
        TECHNIQUE_ITEMS.put(techniqueName, item);
    }
}