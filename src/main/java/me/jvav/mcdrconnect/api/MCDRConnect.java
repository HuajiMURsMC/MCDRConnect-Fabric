package me.jvav.mcdrconnect.api;

import me.jvav.mcdrconnect.impl.MCDRConnectImpl;

public class MCDRConnect {
    public static void registerHandler(String id, IHandler handler) throws HandlerAlreadyExistsException {
        MCDRConnectImpl.registerHandler(id, handler);
    }
}
