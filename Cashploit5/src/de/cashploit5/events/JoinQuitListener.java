package de.cashploit5.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.cashploit5.Main;
import de.cashploit5.command.VanishCommand;

public class JoinQuitListener implements Listener {

	public JoinQuitListener(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (VanishCommand.list.contains(e.getPlayer().getUniqueId().toString())) {
			e.setQuitMessage(null);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (VanishCommand.list.contains(e.getPlayer().getUniqueId().toString())) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.hidePlayer(e.getPlayer());
			}
			e.setJoinMessage(null);
		}
	}

}
