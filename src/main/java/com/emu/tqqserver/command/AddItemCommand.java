package com.emu.tqqserver.command;

import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.game.present.PresentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddItemCommand implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(AddItemCommand.class);
    private final UserService userService = new UserService();
    private final PresentService presentService = new PresentService();

    @Override
    public String getName() {
        return "add_item";
    }

    @Override
    public String getDescription() {
        return "Adds items to a user. Usage: /add_item <itemId> <amount> [userId]";
    }

    @Override
    public void execute(long executorId, String[] args) {
        if (args.length < 3) {
            log.warn("Usage: /add_item <itemId> <amount> [userId]");
            return;
        }

        try {
            int itemId = Integer.parseInt(args[1]);
            int amount = Integer.parseInt(args[2]);
            long targetUserId = executorId;

            // If a specific target is provided, or if console (0) is executing
            if (args.length >= 4) {
                targetUserId = Long.parseLong(args[3]);
            }

            if (targetUserId == 0) {
                log.warn("Please specify a target userId when executing from Console. Usage: /add_item <itemId> <amount> <userId>");
                return;
            }

            if (userService.findById(targetUserId) == null) {
                log.warn("ID không tồn tại: " + targetUserId);
                return;
            }

            presentService.addPresent(targetUserId, 4, itemId, amount, "Quà từ lệnh /add_item");
            log.info("Sent {} items (id: {}) to present box of user {}", amount, itemId, targetUserId);
        } catch (NumberFormatException e) {
            log.warn("itemId, amount and userId must be numbers. Usage: /add_item <itemId> <amount> [userId]");
        } catch (Exception e) {
            log.error("Failed to execute add_item", e);
        }
    }
}
