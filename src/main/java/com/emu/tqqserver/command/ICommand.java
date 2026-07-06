package com.emu.tqqserver.command;

public interface ICommand {
    String getName();
    String getDescription();
    /**
     * Executes the command.
     * @param executorId 0 if executed from Console (Server), or the actual userId if executed from game chat.
     * @param args the command arguments
     */
    void execute(long executorId, String[] args);
}
