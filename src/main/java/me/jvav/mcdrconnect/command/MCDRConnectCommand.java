package me.jvav.mcdrconnect.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.jvav.mcdrconnect.TheMod;
import me.jvav.mcdrconnect.mixin.CommandSourceStackAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.rcon.RconConsoleSource;

import static net.minecraft.commands.Commands.*;

public class MCDRConnectCommand implements ICommand {
    private int handle(CommandContext<CommandSourceStack> ctx) {
        String event = ctx.getArgument("event", String.class);
        String data = ctx.getArgument("data", String.class);
        if (TheMod.HANDLER_MAP.containsKey(event)) {
            ctx.getSource().sendSystemMessage(Component.literal(TheMod.HANDLER_MAP.get(event).handle(ctx.getSource().getServer(), data)));
        }
        return 1;
    }

    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher,
                         CommandBuildContext context,
                         Commands.CommandSelection selection) {
        dispatcher.register(
            literal("mcdrconnectgetdata")
            .requires(stack -> ((CommandSourceStackAccessor) stack).getSource() instanceof RconConsoleSource || FabricLoader.getInstance().isDevelopmentEnvironment())
            .then(
                argument("event", StringArgumentType.word())
                .then(
                    argument("data", StringArgumentType.string())
                    .executes(this::handle)
                )
            )
        );
    }
}
