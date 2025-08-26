package com.mcgamer.mcjjkp.networking.packets;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public class S2CFlowingRedScaleActive extends AbstractPacket {
    public static final Type<S2CFlowingRedScaleActive> TYPE = new Type<>(JJKMod
            .prefix("sync_flowing_red_scale_active"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CFlowingRedScaleActive> CODEC =
            StreamCodec.ofMember(S2CFlowingRedScaleActive::toBytes, S2CFlowingRedScaleActive::new);
    private final boolean flowingRedScaleActive;


    public S2CFlowingRedScaleActive(boolean flowingRedScaleActive) {
        this.flowingRedScaleActive = flowingRedScaleActive;
    }

    //Decoder
    public S2CFlowingRedScaleActive(RegistryFriendlyByteBuf buf) {
        this.flowingRedScaleActive = buf.readBoolean();

    }

    //Encoder
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(flowingRedScaleActive);

    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        player.setData(ModDataAttachments.FLOWING_RED_SCALE_ACTIVE, flowingRedScaleActive);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}