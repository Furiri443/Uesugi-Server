package com.emu.tqqserver.command;

import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.game.present.PresentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddPayJewelCommand implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(AddPayJewelCommand.class);
    private final UserService userService = new UserService();
    private final PresentService presentService = new PresentService();

    @Override
    public String getName() {
        return "addpaycoin";
    }

    @Override
    public String getDescription() {
        return "Adds pay coins to a user. Usage: /addpaycoin <amount> [userId]";
    }

    @Override
    public void execute(long executorId, String[] args) {
        if (args.length < 2) {
            log.warn("Usage: /addpaycoin <amount> [userId]");
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
                log.warn("Please specify a target userId when executing from Console. Usage: /addpaycoin <amount> <userId>");
                return;
            }

            if (userService.findById(targetUserId) == null) {
                log.warn("ID không tồn tại: " + targetUserId);
                return;
            }

            presentService.addPresent(targetUserId, 5, 0, amount, "Quà từ lệnh /addpaycoin");
            log.info("Sent {} pay coins to present box of user {}", amount, targetUserId);
        } catch (NumberFormatException e) {
            log.warn("Amount and userId must be numbers. Usage: /addpaycoin <amount> [userId]");
        } catch (Exception e) {
            log.error("Failed to execute add_paycoin", e);
        }
    }
}
