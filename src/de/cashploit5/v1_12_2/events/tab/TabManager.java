package de.cashploit5.v1_12_2.events.tab;

import java.util.HashMap;

import de.cashploit5.v1_12_2.command.Command;

public class TabManager {
	
	private static HashMap<Command, TabCompleter> tabMap = new HashMap<>();
	
	public static HashMap<Command, TabCompleter> getTabCompleters() {
		return tabMap;
	}
}
