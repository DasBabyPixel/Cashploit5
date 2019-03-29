package de.cashploit5.v1_12_2.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.cashploit5.Main;
import de.cashploit5.v1_12_2.uuidmanager.UUIDManager;

public class PermaDemoCommand extends Command {

	public PermaDemoCommand() {
		super("permademo");
	}

	private HashMap<Player, Integer> schedulers = new HashMap<>();
	private HashMap<Player, Boolean> isRunning = new HashMap<>();

	@Override
	public void onCommand(Player p, Command command, String[] args) {

		if (args.length == 1) {
			Player target = UUIDManager.getPlayer(args[0]);
			if (target == null) {
				Main.sendMessage(p, "§cDieser Spieler ist nicht online!");
			} else {
				if (isRunning.get(target) == null || !isRunning.get(target)) {
					int sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

						@Override
						public void run() {
							DemoCommand.sendDemo(target);
						}
					}, 0, 1);
					schedulers.put(target, sched);
					isRunning.put(target, true);
					Main.sendMessage(p, "§cSpieler §6" + target.getName() + "§c sieht nun die Demo!");
				} else {
					Bukkit.getScheduler().cancelTask(schedulers.get(target));
					isRunning.put(target, false);
					Main.sendMessage(p, "§cSpieler §6" + target.getName() + "§c sieht nun nicht mehr die Demo!");
				}
			}
		} else {
			Main.sendMessage(p, "§6°permademo <Spieler>");
		}
	}
}
