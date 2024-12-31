package com.mcgamer.mcjjkp.event;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.mcgamer.mcjjkp.attachments.ModDataAttachments.BLOOD_DRAWN;
import static com.mcgamer.mcjjkp.attachments.ModDataAttachments.COOLDOWN;
import static com.mcgamer.mcjjkp.util.ModDamageTypes.HAEMORRHAGE;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent
    public static void onUseArrow(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if ((event.getEntity().getData(COOLDOWN) >= 10 || event.getEntity().getData(COOLDOWN) == -1)
                && event.getItemStack().is(Items.ARROW) && !event.getLevel().isClientSide) {
            player.hurt(new DamageSource(player.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(HAEMORRHAGE)), 4f);
            event.getItemStack().consume(1, player);
            player.addItem(ModItems.BLOOD_TIPPED_ARROW_ITEM.toStack());
            event.getEntity().setData(COOLDOWN, 0);
            player.setData(BLOOD_DRAWN, player.getData(BLOOD_DRAWN) + 1);
        }
    }
    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if(player.hasData(COOLDOWN)) {
            player.setData(COOLDOWN, player.getData(COOLDOWN) + 1);
        }
        if (player.hasData(BLOOD_DRAWN)) {
            applyEffects(player);
        }
        if(player.getData(BLOOD_DRAWN) > 0 && player.getData(COOLDOWN) >= 600) {
            player.setData(BLOOD_DRAWN, player.getData(BLOOD_DRAWN) - 1);
            player.setData(COOLDOWN, 0);
        }
    }

    private static void applyEffects(Player player) {
        switch (player.getData(BLOOD_DRAWN)) {
            case 4, 5:
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300), player);
                break;
            case 6:
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 10), player);
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10), player);
                break;
            case 7:
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 10, 2), player);
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 2), player);
                break;
            case 8, 9:
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 10, 3), player);
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 3), player);
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 10, 3), player);
                break;
            case 10, 11:
                player.kill();
        }
    }
}
