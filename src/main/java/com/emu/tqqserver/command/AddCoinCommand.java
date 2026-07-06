package com.emu.tqqserver.command;

import com.emu.tqqserver.game.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddCoinCommand implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(AddCoinCommand.class);
    private final UserService userService = new UserService();

    @Override
    public String getName() {
        return "addcoin";
    }

    @Override
    public String getDescription() {
        return "Adds coins to a user. Usage: /addcoin <amount> [userId]";
    }

    @Override
    public void execute(long executorId, String[] args) {
        if (args.length < 2) {
            log.warn("Usage: /addcoin <amount> [userId]");
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
                log.warn("Please specify a target userId when executing from Console. Usage: /addcoin <amount> <userId>");
                return;
            }

            if (userService.findById(targetUserId) == null) {
                log.warn("ID không tồn tại: " + targetUserId);
                return;
            }

            userService.addCoin(targetUserId, amount);
            log.info("Added {} coins to user {}", amount, targetUserId);
        } catch (NumberFormatException e) {
            log.warn("Amount and userId must be numbers. Usage: /addcoin <amount> [userId]");
        } catch (Exception e) {
            log.error("Failed to execute add_coin", e);
        }
    }
}
