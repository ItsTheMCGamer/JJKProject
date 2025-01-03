package com.mcgamer.mcjjkp.networking.packets;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.screen.TechniquesScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class S2COpenTechniqueScreen extends AbstractPacket {
    public static final Type<S2COpenTechniqueScreen> TYPE = new Type<>(JJKMod.prefix("open_technique_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2COpenTechniqueScreen> CODEC =
            StreamCodec.ofMember(S2COpenTechniqueScreen::toBytes, S2COpenTechniqueScreen::new);


    public S2COpenTechniqueScreen() {

    }

    //Decoder
    public S2COpenTechniqueScreen(RegistryFriendlyByteBuf buf) {

    }

    //Encoder
    public void toBytes(RegistryFriendlyByteBuf buf) {

    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player) {
        minecraft.setScreen(new TechniquesScreen(Component.literal("Techniques Menu")));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}