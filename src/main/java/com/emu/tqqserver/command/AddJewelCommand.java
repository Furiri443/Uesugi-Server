package com.emu.tqqserver.command;

import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.game.present.PresentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddJewelCommand implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(AddJewelCommand.class);
    private final UserService userService = new UserService();
    private final PresentService presentService = new PresentService();

    @Override
    public String getName() {
        return "addjewel";
    }

    @Override
    public String getDescription() {
        return "Adds jewels to a user. Usage: /addjewel <amount> [userId]";
    }

    @Override
    public void execute(long executorId, String[] args) {
        if (args.length < 2) {
            log.warn("Usage: /addjewel <amount> [userId]");
            return;
        }

        try {
            int amount = Integer.parseInt(args[1]);
            long targetUserId = executorId;

            // If a specific target is provided, or if console (0) is executing
            if (args.length >= 3) {
                targetUserId = Long.parseLong(args[2]);
            }

            if (targetUserId == 0) {
                log.warn("Please specify a target userId when executing from Console. Usage: /addjewel <amount> <userId>");
                return;
            }

            if (userService.findById(targetUserId) == null) {
                log.warn("ID không tồn tại: " + targetUserId);
                return;
            }

            presentService.addPresent(targetUserId, 2, 0, amount, "Quà từ lệnh /addjewel");
            log.info("Sent {} jewels to present box of user {}", amount, targetUserId);
        } catch (NumberFormatException e) {
            log.warn("Amount and userId must be numbers. Usage: /addjewel <amount> [userId]");
        } catch (Exception e) {
            log.error("Failed to execute add_jewel", e);
        }
    }
}
