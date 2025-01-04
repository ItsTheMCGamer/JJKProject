package com.mcgamer.mcjjkp.networking.packets;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniques;
import com.mcgamer.mcjjkp.techniques.blood_manipulation.FlowingRedScale;
import com.mcgamer.mcjjkp.util.GoreCoreByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class C2SUseTechnique extends AbstractPacket {
    public static final Type<C2SUseTechnique> TYPE = new Type<>(JJKMod.prefix("use_technique"));
    public static final StreamCodec<RegistryFriendlyByteBuf, C2SUseTechnique> CODEC =
            StreamCodec.ofMember(C2SUseTechnique::toBytes, C2SUseTechnique::new);
    private ExtensionTechnique extensionTechnique;


    public C2SUseTechnique(ExtensionTechnique technique) {
        this.extensionTechnique = technique;
    }

    //Decoder
    public C2SUseTechnique(RegistryFriendlyByteBuf buf) {
        String extensionTechniqueName = GoreCoreByteBufUtil.readString(buf);
        extensionTechnique = ExtensionTechniques.get(extensionTechniqueName);
        if (extensionTechnique == null) {
            throw new NullPointerException("Server sent invalid ability over network: ID " + extensionTechniqueName);
        }

    }

    //Encoder
    public void toBytes(RegistryFriendlyByteBuf buf) {
        GoreCoreByteBufUtil.writeString(buf, extensionTechnique.getName());
    }

    public ExtensionTechnique getExtensionTechnique() {
        return extensionTechnique;
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player) {
        this.getExtensionTechnique().execute();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}