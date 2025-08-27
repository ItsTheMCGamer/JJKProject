package com.mcgamer.mcjjkp.networking.packets;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniqueRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public class S2CToggleTechnique extends AbstractPacket {
    public static final Type<S2CToggleTechnique> TYPE = new Type<>(JJKMod.prefix("sync_technique_state"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CToggleTechnique> CODEC = StreamCodec.ofMember(S2CToggleTechnique::toBytes, S2CToggleTechnique::new);

    private final boolean isActive;
    private final String techniqueName;

    public S2CToggleTechnique(boolean isActive, String techniqueName) {
        this.isActive = isActive;
        this.techniqueName = techniqueName;
    }

    public S2CToggleTechnique(RegistryFriendlyByteBuf buf) {
        this.isActive = buf.readBoolean();
        this.techniqueName = buf.readUtf();
    }

    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(isActive);
        buf.writeUtf(techniqueName);
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        ExtensionTechniqueRegistry.getTechniqueByName(techniqueName)
                .ifPresent(technique -> technique.setActive(isActive));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}