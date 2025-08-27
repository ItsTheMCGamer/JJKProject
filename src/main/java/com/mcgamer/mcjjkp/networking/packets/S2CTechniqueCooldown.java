package com.mcgamer.mcjjkp.networking.packets;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniqueRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public class S2CTechniqueCooldown extends AbstractPacket {
    public static final Type<S2CTechniqueCooldown> TYPE = new Type<>(JJKMod.prefix("sync_technique_cooldown"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CTechniqueCooldown> CODEC =
            StreamCodec.ofMember(S2CTechniqueCooldown::toBytes, S2CTechniqueCooldown::new);
    private final String techniqueName;
    private final long lastUsed;

    public S2CTechniqueCooldown(String techniqueName, long lastUsed) {
        this.techniqueName = techniqueName;
        this.lastUsed = lastUsed;
    }

    public S2CTechniqueCooldown(RegistryFriendlyByteBuf buf) {
        this.techniqueName = buf.readUtf();
        this.lastUsed = buf.readLong();
    }

    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeUtf(techniqueName);
        buf.writeLong(lastUsed);
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        ExtensionTechniqueRegistry.getTechniqueByName(techniqueName)
                .ifPresent(technique -> technique.setLastUsed(lastUsed));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}