package me.jvav.mcdrconnect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import me.jvav.mcdrconnect.api.IHandler;
import me.jvav.mcdrconnect.api.MCDRConnect;
import me.jvav.mcdrconnect.command.MCDRConnectCommand;
import me.jvav.mcdrconnect.impl.ServerVersionHandler;
import me.jvav.mcdrconnect.impl.completion.CompletionHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.HashMap;
import java.util.Map;

public class TheMod implements ModInitializer {
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
    public static final Map<String, IHandler> HANDLER_MAP = new HashMap<>();

    @Override
    public void onInitialize() {
        MCDRConnect.registerHandler("mcdrconnect.command_completion", new CompletionHandler());
        MCDRConnect.registerHandler("mcdrconnect.server_version", new ServerVersionHandler());
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection selection) {
        new MCDRConnectCommand().register(dispatcher, context, selection);
    }
}
