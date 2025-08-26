package com.mcgamer.mcjjkp.client;

import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class CursedEnergyHudOverlay implements LayeredDraw.Layer {

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        int x = guiGraphics.guiWidth() / 2;
        int y = guiGraphics.guiHeight();
        Player player = Minecraft.getInstance().player;
        guiGraphics.drawStringWithBackdrop(Minecraft.getInstance().font,
                Component.literal(player.getData(ModDataAttachments.CURSED_ENERGY_AVAILABLE) + "/" +
                        player.getData(ModDataAttachments.CURSED_ENERGY_MAX)), 156 * x / 120, 61 * y / 64,
                0, Color.cyan.hashCode());
    }
}
