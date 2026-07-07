package com.emu.tqqserver.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Logger log = LoggerFactory.getLogger(CommandManager.class);
    private static final CommandManager INSTANCE = new CommandManager();

    private final Map<String, ICommand> commands = new HashMap<>();

    private CommandManager() {
        // Register default commands
        registerCommand(new AddCoinCommand());
        registerCommand(new AddJewelCommand());
        registerCommand(new AddPayJewelCommand());
        registerCommand(new AddItemCommand());
        registerCommand(new GiveAllCommand());
        registerCommand(new SetLevelCommand());
        registerCommand(new GiveCommand());
    }

    public static CommandManager getInstance() {
        return INSTANCE;
    }

    public void registerCommand(ICommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    public String executeCommand(long executorId, String commandLine) {
        String[] parts = commandLine.trim().split("\\s+");
        if (parts.length == 0) return "Empty command.";

        String cmdName = parts[0].toLowerCase();
        if (cmdName.startsWith("/")) {
            cmdName = cmdName.substring(1);
        }

        ICommand command = commands.get(cmdName);
        if (command != null) {
            try {
                command.execute(executorId, parts);
                return "Command /" + cmdName + " executed successfully.";
            } catch (Exception e) {
                log.error("Error executing command: {}", cmdName, e);
                return "Error: " + e.getMessage();
            }
        } else {
            String avail = String.join(", ", commands.keySet());
            log.warn("Unknown command: {}. Available: {}", cmdName, avail);
            return "Unknown command. Available: " + avail;
        }
    }
}
