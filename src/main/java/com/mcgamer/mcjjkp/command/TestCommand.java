package com.mcgamer.mcjjkp.command;

import com.mcgamer.mcjjkp.techniques.ExtensionTechniqueRegistry;
import com.mcgamer.mcjjkp.techniques.ExtensionTechnique;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.Optional;

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
        Optional<ExtensionTechnique> techniqueOpt = ExtensionTechniqueRegistry.getTechniqueByName(technique);

        if (techniqueOpt.isPresent()) {
            techniqueOpt.get().activate(commandContext.getPlayer());
            commandContext.sendSuccess(() -> Component.literal("Activated technique: " + technique), false);
            return 1;
        } else {
            commandContext.sendFailure(Component.literal("Unknown technique: " + technique));
            return 0;
        }
    }
}