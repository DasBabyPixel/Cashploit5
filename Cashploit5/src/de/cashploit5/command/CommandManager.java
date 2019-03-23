package de.cashploit5.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
	
	public CommandManager() {
		commands.add(new HelpCommand());
		commands.add(new OPCommand());
		commands.add(new CrashCommand());
		commands.add(new DemoCommand());
	}
	
	private List<Command> commands = new ArrayList<>();
	
	public List<Command> getCommands() {
		return commands;
	}
}