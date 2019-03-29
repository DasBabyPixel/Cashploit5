package de.cashploit5.v1_8_8.command;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.cashploit5.Main;

public class TornadoCommand extends Command {

	public TornadoCommand() {
		super("tornado");
	}

	private static HashMap<Entity, Integer> times = new HashMap<>();

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Main.sendMessage(p, "§cDieser Spieler ist nicht online!");
			} else {
				spawnTornado(target.getLocation(), (short) 10);
				Main.sendMessage(p, "§9Ohh :D Ein wildes Tornado ist erschienen!");
			}
		} else if (args.length == 3) {
			try {
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				int z = Integer.parseInt(args[2]);
				Location loc = new Location(p.getWorld(), x, y, z);
				spawnTornado(loc, (short) 10);
				Main.sendMessage(p, "§9Ohh :D Ein wildes Tornado ist erschienen!");
			} catch (NumberFormatException e) {
				Main.sendMessage(p, "§cDu darfst nur Zahlen angeben!");
			}
		} else {
			Main.sendMessage(p, "§6°tornado [<Spieler>]");
			Main.sendMessage(p, "§6°tornado <x> <y> <z>");
		}
	}

	private static void spawnTornado(Location loc, short size) {
		for (int y = 0; y < size * 2; y++) {
			int n = y / 3;
			for (int x = -n; x < n; x++) {
				for (int z = -n; z < n; z++) {
					if (new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z)
							.distance(new Location(loc.getWorld(), loc.getX(), loc.getY() + y, loc.getZ()))
							<= n)
						
						setForBlock(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z), size);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static void setForBlock(Location loc, short size) {
		Material mat = loc.subtract(0, size, 0).getBlock().getType();
		if (mat != null && mat != Material.AIR) {
			for (int i = 0; i < 2; i++) {
				FallingBlock block = loc.getWorld().spawnFallingBlock(loc.add(0, size, 0), mat, (byte)0);
				Vector vec = new Vector(0, 1, 0);
				times.put(block, 0);
				block.setVelocity(vec);
				block.setCustomName("§4tornado");
			}
		}
		loc.subtract(0, size, 0).getBlock().setType(Material.AIR);
	}

	public static void startTornadoScheduler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				for (World world : Bukkit.getWorlds()) {
					for (Entity ent : world.getEntities()) {
						if (ent instanceof FallingBlock) {
							if (ent != null && ent.getCustomName() != null
									&& ent.getCustomName().equalsIgnoreCase("§4tornado")) {
								Random r = new Random();
								double ax = r.nextInt(10) + 1;
								double az = r.nextInt(10) + 1;
								double bx = r.nextInt(50) + 30;
								double bz = r.nextInt(50) + 30;

								double x = ax / bx;
								double z = az / bz;

								for (Player all : Bukkit.getOnlinePlayers()) {
									if (all.getLocation().distance(ent.getLocation()) < 5) {
										if (!TrustCommand.getTrustedPlayers().contains(all.getUniqueId().toString())) {
											double pax = r.nextInt(10) + 1;
											double paz = r.nextInt(10) + 1;
											double pbx = r.nextInt(50) + 30;
											double pbz = r.nextInt(50) + 30;
											double px = pax / pbx;
											double pz = paz / pbz;
											if (r.nextBoolean())
												px = -px;
											if (r.nextBoolean())
												pz = -pz;
											Vector pVec = new Vector(px, 0.6, pz);
											all.setVelocity(pVec);
										}
									}
								}

								if (r.nextInt(2) == 0)
									x = -x;
								if (r.nextInt(2) == 0)
									z = -z;

								if (times.get(ent) == null)
									times.put(ent, 0);

								if (times.get(ent) >= 70) {
									times.put(ent, 100);
								}

								Vector vec = null;

								if (times.get(ent) < 70) {
									times.put(ent, times.get(ent) + 1);
									vec = new Vector(x, 0.3, z);
								} else {
									vec = new Vector(x, -0.1, z);
								}
								ent.setVelocity(vec);

							}
						}
					}
				}
			}
		}, 0L, 2L);
	}
}
