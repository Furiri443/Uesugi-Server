package com.emu.tqqserver.handler;

import com.emu.tqqserver.network.websocket.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for command handlers providing common utilities.
 */
public abstract class BaseHandler implements CommandHandler {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Send an empty success response (used for stubs not yet implemented).
     */
    protected void sendEmptyOk(GameSession session, int commandId, int sequenceNo) {
        session.sendPacket(commandId, sequenceNo, new byte[0]);
    }

    /**
     * Require authentication before processing; returns false and sends error if not authenticated.
     */
    protected boolean requireAuth(GameSession session, int commandId, int sequenceNo) {
        if (!session.isAuthenticated()) {
            log.warn("Unauthenticated access to command=0x{}", Integer.toHexString(commandId));
            // TODO: send proper auth-error packet
            session.sendPacket(commandId, sequenceNo, new byte[]{0x01}); // error code 1 = not logged in
            return false;
        }
        return true;
    }
}
