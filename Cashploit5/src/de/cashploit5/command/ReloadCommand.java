package de.cashploit5.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReloadCommand extends Command {

	public ReloadCommand() {
		super("reload");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		Bukkit.reload();
	}
}
