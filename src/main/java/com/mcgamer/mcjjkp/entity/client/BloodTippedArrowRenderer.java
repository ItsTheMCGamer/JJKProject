package com.mcgamer.mcjjkp.entity.client;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.entity.custom.BloodTippedArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BloodTippedArrowRenderer extends ArrowRenderer<BloodTippedArrowEntity> {
    public static final ResourceLocation BLOOD_TIPPED_ARROW_LOCATION = ResourceLocation
            .fromNamespaceAndPath(JJKMod.MOD_ID, "textures/entity/projectiles/blood_tipped_arrow.png");

    public BloodTippedArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BloodTippedArrowEntity entity) {
        return BLOOD_TIPPED_ARROW_LOCATION;
    }
}
