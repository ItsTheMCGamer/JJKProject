package com.mcgamer.mcjjkp.item;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.item.custom.BlackFlashItem;
import com.mcgamer.mcjjkp.item.custom.BloodPackItem;
import com.mcgamer.mcjjkp.item.custom.BloodTippedArrowItem;
import com.mcgamer.mcjjkp.item.custom.EmptyBloodPackItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(JJKMod.MOD_ID);

    /**
     * Blood Manipulation
     */
    public static final DeferredItem<Item> BLOOD_TIPPED_ARROW_ITEM = ITEMS.register("blood_tipped_arrow",
            () -> new BloodTippedArrowItem(new Item.Properties().stacksTo(48)));
    public static final DeferredItem<Item> BLOOD_EDGE_ITEM = ITEMS.register("blood_edge",
            () -> new SwordItem(Tiers.NETHERITE,
                    new Item.Properties()
                            .stacksTo(1)
                            .setNoRepair()
                            .attributes(SwordItem.createAttributes(ModToolTiers.BLOOD, 3, -2.4F))));
    public static final DeferredItem<Item> EMPTY_BLOOD_PACK_ITEM = ITEMS.register("empty_blood_pack",
            () -> new EmptyBloodPackItem(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> BLOOD_PACK_ITEM = ITEMS.register("blood_pack",
            () -> new BloodPackItem(new Item.Properties().stacksTo(16)));

    /**
     * 10 Shadows Technique
     */
    public static final DeferredItem<Item> SHADOW_BLADE_ITEM = ITEMS.register("shadow_blade",
            () -> new SwordItem(Tiers.NETHERITE,
                    new Item.Properties()
                            .stacksTo(1)
                            .setNoRepair()
                            .attributes(SwordItem.createAttributes(ModToolTiers.SHADOW, 3, -2.4F))));

    /**
     * General
     */
    public static final DeferredItem<Item> BLACK_FLASH_ITEM = ITEMS.register("black_flash",
            () -> new BlackFlashItem(new Item.Properties().stacksTo(1)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
