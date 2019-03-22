package de.cashploit5.command;

import org.bukkit.entity.Player;

public abstract class Command {
	
	public abstract void onCommand(Player p, Command command, String[] args);
	
	private String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
