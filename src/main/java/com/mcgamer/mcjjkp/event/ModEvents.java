package com.mcgamer.mcjjkp.event;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.UUID;

import static com.mcgamer.mcjjkp.JJKMod.cooldownMap;
import static com.mcgamer.mcjjkp.util.ModDamageTypes.HAEMORRHAGE;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent
    public static void onUseArrow(PlayerInteractEvent.RightClickItem event) {
        Player entity = event.getEntity();
        if (event.getItemStack().is(Items.ARROW) && !event.getLevel().isClientSide) {
            System.out.println("abc");
            UUID playerId = entity.getUUID();
            // Check cooldown
            if (cooldownMap.containsKey(playerId)) {
                System.out.println("ab");
                long lastUseTime = cooldownMap.get(playerId);
                long currentTime = event.getLevel().getGameTime();
                long cooldownDuration = 10;
                System.out.println(currentTime-lastUseTime);
                if (currentTime - lastUseTime < cooldownDuration) {
                    System.out.println("a");
                } else {
                    if (event.getItemStack().is(Items.ARROW) && !event.getLevel().isClientSide) {
                        entity.hurt(new DamageSource(entity.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                                .getHolderOrThrow(HAEMORRHAGE)), 4f);
                        event.getItemStack().consume(1, entity);
                        entity.addItem(ModItems.BLOOD_TIPPED_ARROW_ITEM.toStack());
                        cooldownMap.put(event.getEntity().getUUID(), event.getLevel().getGameTime());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide) {
            for(Player player : event.getLevel().players()) {
                cooldownMap.put(player.getUUID(), event.getLevel().getGameTime());
            }
        }
    }
}
