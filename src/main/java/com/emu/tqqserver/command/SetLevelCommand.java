package com.emu.tqqserver.command;

import com.emu.tqqserver.game.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetLevelCommand implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(SetLevelCommand.class);
    private final UserService userService = new UserService();

    @Override
    public String getName() {
        return "setlevel";
    }

    @Override
    public String getDescription() {
        return "Set level for a user. Usage: /setlevel <level> [userId]";
    }

    @Override
    public void execute(long executorId, String[] args) {
        if (args.length < 2) {
            log.warn("Usage: /setlevel <level> [userId]");
            return;
        }

        try {
            int level = Integer.parseInt(args[1]);
            long targetUserId = executorId;

            // If a specific target is provided, or if console (0) is executing
            if (args.length >= 3) {
                targetUserId = Long.parseLong(args[2]);
            }

            if (targetUserId == 0) {
                log.warn("Please specify a target userId when executing from Console. Usage: /setlevel <level> <userId>");
                return;
            }

            if (userService.findById(targetUserId) == null) {
                log.warn("ID không tồn tại: " + targetUserId);
                return;
            }

            userService.setLevel(targetUserId, level);
            log.info("Set level to {} for user {}", level, targetUserId);

            // Force reload for this user if they are online
            com.emu.tqqserver.network.websocket.GameSession session = com.emu.tqqserver.network.websocket.GameWebSocketHandler.getSessionByUserId(targetUserId);
            if (session != null) {
                com.emu.tqqserver.proto.pkg_prealtime.Error error = com.emu.tqqserver.proto.pkg_prealtime.Error.newBuilder()
                    .setCode(10401)
                    .setMsg("Level đã được thay đổi. Đang tải lại dữ liệu...")
                    .build();
                session.sendPush(1, error.toByteArray());
            }
        } catch (NumberFormatException e) {
            log.warn("Level and userId must be numbers. Usage: /setlevel <level> [userId]");
        } catch (Exception e) {
            log.error("Failed to execute setlevel", e);
        }
    }
}
