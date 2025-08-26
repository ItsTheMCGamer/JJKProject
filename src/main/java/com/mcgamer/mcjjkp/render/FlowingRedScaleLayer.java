package com.mcgamer.mcjjkp.render;

import com.mcgamer.mcjjkp.JJKMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.concurrent.Flow;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class FlowingRedScaleLayer {
    public static final ResourceLocation NORMAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "textures/entity/flowing_red_scale_layer.png");
    public static final ResourceLocation STACK_TEXTURE = ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "textures/entity/flowing_red_scale_stacked_layer.png");
    private static final int COLOR = RenderHelper.colorf(.8f, .8f, .8f);

    public static class Vanilla extends RenderLayer<Player, HumanoidModel<Player>> {
        public static ModelLayerLocation ENERGY_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(JJKMod.MOD_ID, "energy_layer"), "main");
        private final HumanoidModel<Player> model;
        private final ResourceLocation TEXTURE;
        private final Predicate<LivingEntity> shouldRender;

        public Vanilla(RenderLayerParent pRenderer, ResourceLocation texture, Predicate<LivingEntity> shouldRender) {
            super(pRenderer);
            this.model = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ENERGY_LAYER));
            this.TEXTURE = texture;
            this.shouldRender = shouldRender;
        }

        public Vanilla(RenderLayerParent pRenderer, ResourceLocation texture, Holder<MobEffect> shouldRenderFlag) {
            this(pRenderer, texture, living -> living.hasEffect(shouldRenderFlag));
        }

        public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, Player pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if (FlowingRedScaleLayer.shouldRender(pLivingEntity, shouldRender)) {
                float f = (float) pLivingEntity.tickCount + pPartialTicks;
                HumanoidModel<Player> entitymodel = this.model();
                VertexConsumer vertexconsumer = pBuffer.getBuffer(FlowingRedScaleLayer.getRenderType(TEXTURE, f));
                this.getParentModel().copyPropertiesTo(entitymodel);
                entitymodel.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, COLOR);
            }
        }

        protected HumanoidModel<Player> model() {
            return model;
        }

        protected boolean shouldRender(Player entity) {
            return true;
        }
    }

    private static RenderType getRenderType(ResourceLocation texture, float f) {
        return RenderType.energySwirl(texture, f * 0.02F % 1.0F, f * 0.01F % 1.0F);
    }

    private static boolean shouldRender(LivingEntity entity, Predicate<LivingEntity> shouldRenderFlag) {
        return shouldRenderFlag.test(entity);
    }
}