package com.mcgamer.mcjjkp.command;

import com.mcgamer.mcjjkp.networking.ModMessages;
import com.mcgamer.mcjjkp.networking.packets.S2COpenTechniqueScreen;
import com.mcgamer.mcjjkp.screen.TechniquesScreen;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class TechniquesCommand {

    public TechniquesCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("techniques").requires(player -> player.hasPermission(2))
                .then((Commands.literal("menu")
                        .executes(TechniquesCommand::openTechniquesMenu))));


    }

    public static int openTechniquesMenu(CommandContext<CommandSourceStack> commandContext) {
        ModMessages.sendToPlayerClient(new S2COpenTechniqueScreen(),
                (ServerPlayer)commandContext.getSource().getEntity());

        return 0;
    }
}
