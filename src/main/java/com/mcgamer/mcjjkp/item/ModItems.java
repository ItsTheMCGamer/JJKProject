package com.mcgamer.mcjjkp.item;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.item.custom.BloodTippedArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(JJKMod.MOD_ID);

    public static final DeferredItem<Item> BLOOD_TIPPED_ARROW_ITEM = ITEMS.register("blood_tipped_arrow_item",
            () -> new BloodTippedArrowItem(new Item.Properties().stacksTo(48)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
