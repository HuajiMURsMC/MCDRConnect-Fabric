package me.jvav.mcdrconnect.impl;

import me.jvav.mcdrconnect.TheMod;
import me.jvav.mcdrconnect.api.HandlerAlreadyExistsException;
import me.jvav.mcdrconnect.api.IHandler;

import java.util.regex.Pattern;

public class MCDRConnectImpl {
    private static final Pattern EVENT_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_.]{1,62}[a-zA-Z]$");

    public static void registerHandler(String id, IHandler handler) throws HandlerAlreadyExistsException {
        assert EVENT_PATTERN.matcher(id).matches();
        if (TheMod.HANDLER_MAP.containsKey(id)) {
            throw new HandlerAlreadyExistsException("Handler for querying \"%s\" has already existed".formatted(id));
        }
        TheMod.HANDLER_MAP.put(id, handler);
    }
}
