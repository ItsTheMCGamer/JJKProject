package com.mcgamer.mcjjkp.networking;

import com.mcgamer.mcjjkp.JJKMod;
import com.mcgamer.mcjjkp.networking.packets.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static org.apache.http.params.CoreProtocolPNames.PROTOCOL_VERSION;
import static oshi.jna.platform.mac.SystemB.INSTANCE;

@EventBusSubscriber(modid = JJKMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModMessages {

    @SubscribeEvent
    private static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar reg = event.registrar("1");
        reg.playToClient(S2COpenTechniqueScreen.TYPE, S2COpenTechniqueScreen.CODEC, ModMessages::handle);
        reg.playToClient(S2CToggleTechnique.TYPE, S2CToggleTechnique.CODEC, ModMessages::handle);
        reg.playToClient(S2CTechniqueCooldown.TYPE, S2CTechniqueCooldown.CODEC, ModMessages::handle);
        reg.playToClient(S2CSyncCursedEnergy.TYPE, S2CSyncCursedEnergy.CODEC, ModMessages::handle);
    }

    private static <T extends AbstractPacket> void handle(T message, IPayloadContext ctx) {
        if (ctx.flow().getReceptionSide() == LogicalSide.SERVER) {
            handleServer(message, ctx);
        } else {
            ClientMessageHandler.handleClient(message, ctx);
        }
    }

    private static <T extends AbstractPacket> void handleServer(T message, IPayloadContext ctx) {
        MinecraftServer server = ctx.player().getServer();
        message.onServerReceived(server, (ServerPlayer) ctx.player());
    }

    private static class ClientMessageHandler {

        public static <T extends AbstractPacket> void handleClient(T message, IPayloadContext ctx) {
            Minecraft minecraft = Minecraft.getInstance();
            message.onClientReceived(minecraft, minecraft.player);
        }
    }

    public static void sendToNearbyClient(Level world, BlockPos pos, CustomPacketPayload toSend) {
        if (world instanceof ServerLevel ws) {
            ws.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).stream()
                    .filter(p -> p.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                    .forEach(p -> ModMessages.sendToPlayerClient(toSend, p));
        }
    }

    public static void sendToNearbyClient(Level world, Entity e, CustomPacketPayload toSend) {
        sendToNearbyClient(world, e.blockPosition(), toSend);
    }

    public static void sendToPlayerClient(CustomPacketPayload msg, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, msg);
    }

    public static void sendToServer(CustomPacketPayload msg) {
        PacketDistributor.sendToServer(msg);
    }

}
