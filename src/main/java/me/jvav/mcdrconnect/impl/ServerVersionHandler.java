package me.jvav.mcdrconnect.impl;

import me.jvav.mcdrconnect.TheMod;
import me.jvav.mcdrconnect.api.IHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ServerVersionHandler implements IHandler {
    @Override
    public @NotNull String handle(MinecraftServer server, String data) {
        Map<String, String> map = new HashMap<>();
        map.put("version", SharedConstants.getCurrentVersion().getId());
        map.put("semver", FabricLoader.getInstance().getModContainer("minecraft").orElseThrow().getMetadata().getVersion().getFriendlyString());
        return TheMod.GSON.toJson(map);
    }
}
