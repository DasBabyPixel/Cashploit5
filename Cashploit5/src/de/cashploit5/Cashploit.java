package de.cashploit5;

import de.cashploit5.command.CommandManager;
import de.cashploit5.events.AntiBANListener;
import de.cashploit5.events.ChatListener;
import de.cashploit5.events.JoinQuitListener;

public class Cashploit extends Main {
	
	private static Cashploit plugin;
	
	public void onEnable() {
		plugin = this;
		new ChatListener(this);
		new AntiBANListener(this);
		new JoinQuitListener(this);
		
		setCommandManager(new CommandManager());
		
	}
	
	public static Cashploit getPlugin() {
		return plugin;
	}
}
