package com.emu.tqqserver.command;

import com.emu.tqqserver.game.present.PresentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GiveCommand implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(GiveCommand.class);
    private final PresentService presentService = new PresentService();

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getDescription() {
        return "Give any present item: /give <category> <target_id> <quantity> [userId]";
    }

    @Override
    public void execute(long executorId, String[] args) {
        if (args.length < 4) {
            log.warn("Usage: /give <category> <target_id> <quantity> [userId]");
            return;
        }

        try {
            int category = Integer.parseInt(args[1]);
            int targetId = Integer.parseInt(args[2]);
            int quantity = Integer.parseInt(args[3]);
            long targetUser = executorId;
            if (args.length > 4) {
                targetUser = Long.parseLong(args[4]);
            }

            presentService.addPresent(targetUser, category, targetId, quantity, "Admin Give");
            log.info("Gave category={} targetId={} quantity={} to user {}", category, targetId, quantity, targetUser);
        } catch (NumberFormatException e) {
            log.warn("Invalid numbers provided. Usage: /give <category> <target_id> <quantity> [userId]");
        } catch (Exception e) {
            log.error("Error executing /give", e);
        }
    }
}
