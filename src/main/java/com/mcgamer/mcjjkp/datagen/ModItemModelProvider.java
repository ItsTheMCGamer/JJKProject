package com.mcgamer.mcjjkp.datagen;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, JJKMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.MODEL_ITEM.get());
        basicItem(ModItems.BLOOD_TIPPED_ARROW_ITEM.get());
    }
}
