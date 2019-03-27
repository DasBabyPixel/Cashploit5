package de.cashploit5.command;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishCommand extends Command {

	public VanishCommand() {
		super("vanish");
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> list = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public void onCommand(Player p, Command command, String[] args) {
		if (list.contains(p.getUniqueId().toString())) {
			list.remove(p.getUniqueId().toString());
			p.sendMessage("§cDu bist nun nicht mehr unsichtbar!");
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.showPlayer(p);
			}
		} else {
			list.add(p.getUniqueId().toString());
			p.sendMessage("§aDu bist nun unsichtbar!");
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.hidePlayer(p);
			}
		}
	}

}
