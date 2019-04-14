package de.cashploit5.v1_12_2.command;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.cashploit5.Main;
import de.cashploit5.v1_12_2.uuidmanager.UUIDManager;

public class AntiTrustCommand extends Command {

	public static List<String> notTrustedPlayers = Main.getPlugin().getConfig("trusted")
			.getStringList("notTrustedPlayers");
	public static boolean isTrustEnabled = !Main.getPlugin().getConfig("trusted").getBoolean("isTrustDisabled");

	public AntiTrustCommand() {
		super("antitrust");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if (args.length != 0) {
			if (args.length == 1) {
				switch (args[0].toLowerCase()) {
				case "disable":
					isTrustEnabled = true;
					YamlConfiguration cfg = Main.getPlugin().getConfig("trusted");
					cfg.set("isTrustDisabled", false);
					Main.getPlugin().saveConfig(cfg);
					Main.sendMessage(p, "§aAnti-Trust wurde §cdeaktiviert");
					break;
				case "enable":
					isTrustEnabled = false;
					YamlConfiguration cfg1 = Main.getPlugin().getConfig("trusted");
					cfg1.set("isTrustDisabled", true);
					Main.getPlugin().saveConfig(cfg1);
					Main.sendMessage(p, "§aAnti-Trust wurde §caktiviert");
					break;
				case "add":
					Main.sendMessage(p, "§6°antitrust <add,remove> <Player>");
					break;
				case "remove":
					Main.sendMessage(p, "§6°antitrust <add,remove> <Player>");
					break;
				default:
					Main.sendMessage(p, "§6°antitrust <enable,disable,add,remove> [<Player>]");
					break;
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("add")) {
					OfflinePlayer t = UUIDManager.getOfflinePlayer(args[1]);
					if (t == null) {
						Main.sendMessage(p, "§cDieser Spieler war noch nie auf dem Server");
					} else {
						if (!TrustCommand.getTrustedPlayers().contains(t.getUniqueId().toString())) {
							if (notTrustedPlayers.contains(t.getUniqueId().toString())) {
								Main.sendMessage(p, "§cDieser Spieler ist bereits anti-getrusted");
							} else {
								notTrustedPlayers.add(t.getUniqueId().toString());
								YamlConfiguration cfg = Main.getPlugin().getConfig("trusted");
								cfg.set("notTrustedPlayers", notTrustedPlayers);
								Main.getPlugin().saveConfig(cfg);
								Main.sendMessage(p, "§aDu hast Spieler §6" + t.getName() + " §aanti-getrusted");
							}
						} else {
							Main.sendMessage(p,
									"§cDieser Spieler ist getrusted! Ent-Truste ihn um ihn zu anti-trusten!");
						}
					}
				} else if (args[0].equalsIgnoreCase("remove")) {
					OfflinePlayer t = UUIDManager.getOfflinePlayer(args[1]);
					if (t == null) {
						Main.sendMessage(p, "§cDieser Spieler war noch nie auf dem Server");
					} else {
						if (!notTrustedPlayers.contains(t.getUniqueId().toString())) {
							Main.sendMessage(p, "Dieser Spieler ist bereits ent-anti-trusted");
						} else {
							notTrustedPlayers.remove(t.getUniqueId().toString());
							YamlConfiguration cfg = Main.getPlugin().getConfig("trusted");
							cfg.set("notTrustedPlayers", notTrustedPlayers);
							Main.getPlugin().saveConfig(cfg);
							Main.sendMessage(p, "§aDu hast Spieler §6" + t.getName() + " §aent-anti-getrusted");
						}
					}
				} else {
					Main.sendMessage(p, "§6°antitrust <enable,disable,add,remove> [<Player>]");
				}
			}
		} else {
			Main.sendMessage(p, "§6°antitrust <enable,disable,add,remove> [<Player>]");
		}
	}
}
