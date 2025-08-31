package com.mcgamer.mcjjkp.networking.packets;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.attachments.ModDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public class S2CSyncCursedEnergy extends AbstractPacket {
    public static final Type<S2CSyncCursedEnergy> TYPE = new Type<>(JJKMod.prefix("sync_cursed_energy"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSyncCursedEnergy> CODEC =
            StreamCodec.ofMember(S2CSyncCursedEnergy::toBytes, S2CSyncCursedEnergy::new);
    private final int energy;


    public S2CSyncCursedEnergy(int energy) {
        this.energy = energy;
    }

    //Decoder
    public S2CSyncCursedEnergy(RegistryFriendlyByteBuf buf) {
        this.energy = buf.readInt();

    }

    //Encoder
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeInt(energy);

    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        player.setData(ModDataAttachments.CURSED_ENERGY_AVAILABLE, energy);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}