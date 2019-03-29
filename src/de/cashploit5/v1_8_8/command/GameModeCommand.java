package de.cashploit5.v1_8_8.command;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.cashploit5.Main;
import de.cashploit5.v1_8_8.uuidmanager.UUIDManager;

public class GameModeCommand extends Command {

	public GameModeCommand() {
		super("gamemode");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if (args.length == 1) {
			String s = args[0];
			switch (s.toLowerCase()) {
			case "1":
			case "c":
			case "cr":
			case "creative":
				p.setGameMode(GameMode.CREATIVE);
				Main.sendMessage(p, "§aDu bist nun §6CREATIVE");
				break;
			case "2":
			case "a":
			case "advenure":
				p.setGameMode(GameMode.ADVENTURE);
				Main.sendMessage(p, "§aDu bist nun §6ADVENTURE");
				break;
			case "3":
			case "sp":
			case "spectator":
				p.setGameMode(GameMode.SPECTATOR);
				Main.sendMessage(p, "§aDu bist nun §6SPECTATOR");
				break;
			case "0":
			case "s":
			case "survival":
				p.setGameMode(GameMode.SURVIVAL);
				Main.sendMessage(p, "§aDu bist nun §6SURVIVAL");
				break;
			default:
				Main.sendMessage(p, "§cDu musst einen gültigen Wert angeben!");
				break;
			}
		} else if (args.length == 2) {
			Player t = UUIDManager.getPlayer(args[1]);
			if (t == null) {
				Main.sendMessage(p, "§cDieser Spieler ist nicht online!");
			} else {
				String s = args[0];
				switch (s.toLowerCase()) {
				case "1":
				case "c":
				case "cr":
				case "creative":
					t.setGameMode(GameMode.CREATIVE);
					Main.sendMessage(p, t.getName() + "§a ist nun §6CREATIVE");
					break;
				case "2":
				case "a":
				case "advenure":
					t.setGameMode(GameMode.ADVENTURE);
					Main.sendMessage(p, t.getName() + "§a ist nun §6ADVENTURE");
					break;
				case "3":
				case "sp":
				case "spectator":
					t.setGameMode(GameMode.SPECTATOR);
					Main.sendMessage(p, t.getName() + "§a ist nun §6SPECTATOR");
					break;
				case "0":
				case "s":
				case "survival":
					t.setGameMode(GameMode.SURVIVAL);
					Main.sendMessage(p, t.getName() + "§a ist nun §6SURVIVAL");
					break;
				default:
					Main.sendMessage(p, "§cDu musst einen gültigen Wert angeben!");
					break;
				}
			}
		} else {
			Main.sendMessage(p, "§6°gamemode <Wert> [<Spieler>]");
		}
	}
}
