package com.mcgamer.mcjjkp.item;

import com.mcgamer.mcjjkp.util.ModTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class ModToolTiers {
    public static final Tier BLOOD = new SimpleTier(Tags.Blocks.NEEDS_NETHERITE_TOOL, 1000, 3.0F,
            6.0F, 0, () -> Ingredient.of(Items.AIR));


}
