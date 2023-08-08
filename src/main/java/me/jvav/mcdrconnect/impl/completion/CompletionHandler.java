package me.jvav.mcdrconnect.impl.completion;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import me.jvav.mcdrconnect.TheMod;
import me.jvav.mcdrconnect.api.IHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CompletionHandler implements IHandler {
    @Override
    public @NotNull String handle(MinecraftServer server, String data) {
        CompletionData completionData = TheMod.GSON.fromJson(data, CompletionData.class);
        StringReader stringReader = new StringReader(completionData.input);
        if (stringReader.canRead() && stringReader.peek() == '/') {
            stringReader.skip();
        }
        ParseResults<CommandSourceStack> results = server.getCommands().getDispatcher().parse(stringReader, server.createCommandSourceStack());
        CompletableFuture<Suggestions> suggestionsFuture = server.getCommands().getDispatcher().getCompletionSuggestions(results, completionData.cursor);
        Suggestions suggestions = suggestionsFuture.join();

        Map<String, String> completions = new HashMap<>();

        for (Suggestion suggestion : suggestions.getList()) {
            String suggestedCommand = suggestion.getText();
            if (suggestedCommand.isEmpty()) {
                continue;
            }
            completions.put(suggestedCommand, completionData.input.substring(0, suggestion.getRange().getStart()));
        }
        return TheMod.GSON.toJson(completions);
    }
}
