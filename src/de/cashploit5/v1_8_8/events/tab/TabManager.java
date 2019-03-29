package de.cashploit5.v1_8_8.events.tab;

import java.util.HashMap;

import de.cashploit5.v1_8_8.command.Command;

public class TabManager {

	private static HashMap<Command, TabCompleter> tabMap = new HashMap<>();
	
	public static HashMap<Command, TabCompleter> getTabCompleters() {
		return tabMap;
	}

}
