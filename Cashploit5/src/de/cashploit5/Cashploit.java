package de.cashploit5;

import de.cashploit5.events.ChatListener;

public class Cashploit extends Main {
	
	private static Cashploit plugin;
	
	public void onEnable() {
		plugin = this;
		new ChatListener(this);
	}
	
	
	public static Cashploit getPlugin() {
		return plugin;
	}
}
