package com.emu.tqqserver.command;

import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.network.websocket.GameSession;
import com.emu.tqqserver.proto.pkg_prealtime.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestErrorCommand implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(TestErrorCommand.class);

    @Override
    public String getName() {
        return "testerror";
    }

    @Override
    public String getDescription() {
        return "Test pushing an error to client";
    }

    @Override
    public void execute(long executorId, String[] args) {
        GameSession session = com.emu.tqqserver.network.websocket.GameWebSocketHandler.getSessionByUserId(executorId);
        if (session != null) {
            Error error = Error.newBuilder().setCode(10401).setMsg("Force reload").build();
            session.sendPush(1, error.toByteArray()); // OpCode 1 = Error
            log.info("Pushed Error 10401 to client");
        }
    }
}
