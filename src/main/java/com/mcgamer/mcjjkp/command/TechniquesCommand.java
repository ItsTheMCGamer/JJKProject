package com.mcgamer.mcjjkp.command;

import com.mcgamer.mcjjkp.networking.ModMessages;
import com.mcgamer.mcjjkp.networking.packets.S2COpenTechniqueScreen;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

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
