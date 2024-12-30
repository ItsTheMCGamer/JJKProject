package com.mcgamer.mcjjkp.event;

import com.mcgamer.mcjjkp.JJKMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static com.mcgamer.mcjjkp.util.ModDamageTypes.HAEMORRHAGE;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent
    public static void onUseArrow(PlayerInteractEvent.RightClickItem event) {
        LivingEntity entity = event.getEntity();
        if(event.getItemStack().is(Items.ARROW)) {
            entity.hurt(new DamageSource(entity.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(HAEMORRHAGE)), 4f);
        }
    }
}
