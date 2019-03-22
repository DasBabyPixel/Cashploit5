package de.cashploit5.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
	
	public CommandManager() {
	}
	
	private List<Command> commands = new ArrayList<>();
	
	public List<Command> getCommands() {
		return commands;
	}
}
