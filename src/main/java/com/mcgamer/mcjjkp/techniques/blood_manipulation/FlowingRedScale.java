package com.mcgamer.mcjjkp.techniques.blood_manipulation;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.UUID;

import static com.mcgamer.mcjjkp.attachments.ModDataAttachments.COOLDOWN_SLOT_ONE;

public class FlowingRedScale extends ExtensionTechnique {
    public static int cooldownTimer = 15;

    public FlowingRedScale() {
        super(BloodManipulationTechnique.ID, "flowing_red_scale");
    }

    @Override
    public void execute() {
        Player player = Minecraft.getInstance().player;

        if(player.getData(ModDataAttachments.COOLDOWN_SLOT_ONE) >= getCooldown()) {
            player.setData(COOLDOWN_SLOT_ONE, 0);

            //AttributeModifier strength_modifier = new AttributeModifier(ResourceLocation
            //        .fromNamespaceAndPath(JJKMod.MOD_ID, "attribute.damage_boost"),
            //        1.2 + Math.floor((double) player.getData(ModDataAttachments.STRENGTH_XP) / 1000) * 0.05,
            //        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            //AttributeModifier speed_modifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID,
            //        "attribute.damage_boost"), 1.2 + Math.floor((double) player
            //        .getData(ModDataAttachments.SPEED_XP) / 1000) * 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            //AttributeModifier health_modifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(JJKMod
            //        .MOD_ID, "attribute.damage_boost"), 1.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            AttributeModifier strength_modifier = new AttributeModifier(ResourceLocation
                    .fromNamespaceAndPath(JJKMod.MOD_ID, "flowing_red_scale.strength_boost"),
                    0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            AttributeModifier speed_modifier = new AttributeModifier(ResourceLocation
                    .fromNamespaceAndPath(JJKMod.MOD_ID, "flowing_red_scale.speed_boost"),
                    0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            AttributeModifier health_modifier = new AttributeModifier(ResourceLocation
                    .fromNamespaceAndPath(JJKMod.MOD_ID, "flowing_red_scale.health_boost"),
                    0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

            player.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(strength_modifier);
            player.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(speed_modifier);
            player.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(health_modifier);

        }
    }

    public static int getCooldown() {
        return 10;
    }
}
