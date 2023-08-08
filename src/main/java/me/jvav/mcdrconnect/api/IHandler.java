package me.jvav.mcdrconnect.api;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public interface IHandler {
    @NotNull
    String handle(MinecraftServer server, String data);
}
