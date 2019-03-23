package de.cashploit5;

import de.cashploit5.command.CommandManager;
import de.cashploit5.command.tab.CrashTabCompleter;
import de.cashploit5.events.AntiBANListener;
import de.cashploit5.events.ChatListener;
import de.cashploit5.events.JoinQuitListener;
import de.cashploit5.events.tab.TabListener;

public class Cashploit extends Main {
	
	private static Cashploit plugin;
	
	public void onEnable() {
		plugin = this;
		registerConfig("trusted");
		new ChatListener(this);
		new AntiBANListener(this);
		new JoinQuitListener(this);
		new TabListener(this);
		
		setCommandManager(new CommandManager());
		
		registerTabCompleters();
		
	}
	
	public static Cashploit getPlugin() {
		return plugin;
	}
	
	private void registerTabCompleters() {
		getCashploitCommand("crash").setTabCompleter(new CrashTabCompleter());
	}
}
