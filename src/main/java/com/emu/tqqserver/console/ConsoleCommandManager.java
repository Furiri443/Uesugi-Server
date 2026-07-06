package com.emu.tqqserver.console;

import com.emu.tqqserver.game.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ConsoleCommandManager {
    private static final Logger log = LoggerFactory.getLogger(ConsoleCommandManager.class);
    private static final UserService userService = new UserService();

    public static void startConsoleThread() {
        Thread thread = new Thread(() -> {
            try (Scanner scanner = new Scanner(System.in)) {
                log.info("Console command listener started. Format: /(command) <amount> <userId>");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.isEmpty()) continue;
                    
                    if (line.startsWith("/")) {
                        handleCommand(line);
                    } else {
                        log.warn("Invalid command format. Use: /(command) <amount> <userId>");
                    }
                }
            } catch (Exception e) {
                log.error("Console thread error", e);
            }
        });
        thread.setDaemon(true);
        thread.setName("ConsoleCommandThread");
        thread.start();
    }

    private static void handleCommand(String line) {
        com.emu.tqqserver.command.CommandManager.getInstance().executeCommand(0, line);
    }
}
