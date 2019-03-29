package de.cashploit5.v1_12_2.command;

import org.bukkit.entity.Player;

import de.cashploit5.v1_12_2.events.tab.TabCompleter;
import de.cashploit5.v1_12_2.events.tab.TabManager;

public abstract class Command {
	
	public abstract void onCommand(Player p, Command command, String[] args);
	
	private String name;
	
	public Command(String name) {
		this.name = name;
	}
	
	
	public void setTabCompleter(TabCompleter tabCompleter) {
		TabManager.getTabCompleters().put(this, tabCompleter);
	}
	
	public String getName() {
		return this.name;
	}
}
