package com.mcgamer.mcjjkp.event;

import com.mcgamer.mcjjkp.Config;
import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.command.TechniquesCommand;
import com.mcgamer.mcjjkp.command.TestCommand;
import com.mcgamer.mcjjkp.components.ModDataComponents;
import com.mcgamer.mcjjkp.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Random;

import static com.mcgamer.mcjjkp.attachments.ModDataAttachments.*;
import static com.mcgamer.mcjjkp.util.ModDamageTypes.HAEMORRHAGE;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent
    public static void onUseArrow(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (event.getItemStack().is(Items.ARROW) && !event.getLevel().isClientSide && player.getData(INNATE_TECHNIQUE)
                .equals("blood_manipulation") && player.getData(ARROW_PRICK_COOLDOWN) >= 10) {

            player.hurt(new DamageSource(player.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(HAEMORRHAGE)), 2f);
            event.getItemStack().consume(1, player);

            ItemStack bloodTippedArrow = ModItems.BLOOD_TIPPED_ARROW_ITEM.toStack();
            bloodTippedArrow.set(ModDataComponents.ARROW_OWNER, player.getName().toString());
            player.addItem(bloodTippedArrow);

            event.getEntity().setData(ARROW_PRICK_COOLDOWN, 0);
            player.setData(BLOOD_DRAWN, player.getData(BLOOD_DRAWN) + 1);

        }
    }
    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        player.setData(COOLDOWN_SLOT_ONE, player.getData(COOLDOWN_SLOT_ONE) + 1);
        player.setData(COOLDOWN_SLOT_TWO, player.getData(COOLDOWN_SLOT_TWO) + 1);
        player.setData(COOLDOWN_SLOT_THREE, player.getData(COOLDOWN_SLOT_THREE) + 1);
        player.setData(COOLDOWN_SLOT_FOUR, player.getData(COOLDOWN_SLOT_FOUR) + 1);

        if(player.hasData(ARROW_PRICK_COOLDOWN)) {
            player.setData(ARROW_PRICK_COOLDOWN, player.getData(ARROW_PRICK_COOLDOWN) + 1);
        }
        if (player.hasData(BLOOD_DRAWN)) {
            applyEffects(player);
        }
        if(player.getData(BLOOD_DRAWN) > 0 && player.getData(ARROW_PRICK_COOLDOWN) >= 2000) {
            player.setData(BLOOD_DRAWN, player.getData(BLOOD_DRAWN) - 1);
            player.setData(ARROW_PRICK_COOLDOWN, 0);
        }
    }
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if(Config.randomAssignTechniques && !event.getEntity().getData(PLAYER_HAS_JOINED)) {
            event.getEntity().setData(PLAYER_HAS_JOINED, true);
            Random random = new Random();
            var randInt = random.nextInt(3);
            event.getEntity().setData(INNATE_TECHNIQUE, "blood_manipulation");
            // assignTechnique(event.getEntity(), randInt);

        }
    }
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        TechniquesCommand.register(event.getDispatcher());
        TestCommand.register(event.getDispatcher());
    }




    private static void applyEffects(Player player) {
        switch (player.getData(BLOOD_DRAWN)) {
            case 4, 5:
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200), player);
                break;
            case 6:
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200), player);
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200), player);
                break;
            case 7:
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 2), player);
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2), player);
                break;
            case 8, 9:
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 3), player);
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 4), player);
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 4), player);
                break;
            case 10, 11:
                player.kill();
        }
    }
    public static void assignTechnique(Player player, Integer technique) {
        switch (technique) {
            case 1, 2:
                player.setData(INNATE_TECHNIQUE, "blood_manipulation");
            case 0:
                System.out.println("hello");
        }

    }
}
