package de.cashploit5.console;

import org.bukkit.Bukkit;

public class console {
	
	public static void send(Object obj) {
		Bukkit.getConsoleSender().sendMessage(obj.toString());
	}
	
}
