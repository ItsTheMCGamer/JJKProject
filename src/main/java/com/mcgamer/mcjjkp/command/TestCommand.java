package com.mcgamer.mcjjkp.command;

import com.mcgamer.mcjjkp.networking.ModMessages;
import com.mcgamer.mcjjkp.networking.packets.C2SUseTechnique;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import com.mcgamer.mcjjkp.techniques.ExtensionTechniques;
import com.mcgamer.mcjjkp.techniques.blood_manipulation.FlowingRedScale;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.sun.jdi.connect.Connector;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class TestCommand {

    public TestCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("testa").requires(player -> {
            return player.hasPermission(2);
                }).then(Commands.argument("technique", StringArgumentType.string())
                .executes(b -> test((CommandSourceStack) b.getSource(), StringArgumentType.getString(b,
                        "technique")))));


    }

    public static int test(CommandSourceStack commandContext, String technique) {
        ModMessages.sendToServer(new C2SUseTechnique(ExtensionTechniques.get(technique)));
        System.out.println("Flowing Red Scale Active");

        return 0;
    }
}
