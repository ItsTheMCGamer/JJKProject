package com.mcgamer.mcjjkp.networking.packets;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniques;
import com.mcgamer.mcjjkp.util.GoreCoreByteBufUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class C2SRemoveModifiers extends AbstractPacket {
    public static final Type<C2SRemoveModifiers> TYPE = new Type<>(JJKMod.prefix("remove_modifiers"));
    public static final StreamCodec<RegistryFriendlyByteBuf, C2SRemoveModifiers> CODEC =
            StreamCodec.ofMember(C2SRemoveModifiers::toBytes, C2SRemoveModifiers::new);
    private ResourceLocation id;
    private Holder<Attribute> attribute;


    public C2SRemoveModifiers(ResourceLocation id, Holder<Attribute> attribute) {
        this.id = id;
        this.attribute = attribute;
    }

    //Decoder
    public C2SRemoveModifiers(RegistryFriendlyByteBuf buf) {

    }

    //Encoder
    public void toBytes(RegistryFriendlyByteBuf buf) {

    }

    public ResourceLocation getId() {
        return id;
    }
    public Holder<Attribute> getAttribute() {
        return attribute;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        player.getAttribute(getAttribute()).removeModifier(id);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}