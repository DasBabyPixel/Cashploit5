package de.cashploit5.tornado;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import de.cashploit5.Main;

public class Tornado {

	private Location spawnLoc;
	private Location currentLoc;
	private List<FallingBlock> blocks = new ArrayList<>();
	private Vector direction;
	private int hight;
	private int schedulerID = -1;
	private int griefingID = -1;

	public Tornado(Location spawnLoc, short hight) {
		direction = randomDirection();
		this.spawnLoc = spawnLoc;
		this.currentLoc = spawnLoc;
		this.hight = hight;
	}

	private Vector randomDirection() {
		Random r = new Random();
		double x = ((double) (r.nextInt(10) + 1)) / (double) 50;
		double z = ((double) (r.nextInt(10) + 1)) / (double) 50;
		if (r.nextBoolean())
			x = -x;
		if (r.nextBoolean())
			z = -z;
		return new Vector(x, 0, z);
	}

	public void spawn() {
		for (int y = 0; y < hight * 2; y++) {
			int n = y / 3;
			for (int x = -n; x < n; x++) {
				for (int z = -n; z < n; z++) {
					if (new Location(spawnLoc.getWorld(), spawnLoc.getX() + x, spawnLoc.getY() + y, spawnLoc.getZ() + z)
							.distance(new Location(spawnLoc.getWorld(), spawnLoc.getX(), spawnLoc.getY() + y,
									spawnLoc.getZ())) <= n)

						setForBlock(new Location(spawnLoc.getWorld(), spawnLoc.getX() + x, spawnLoc.getY() + y,
								spawnLoc.getZ() + z), hight);
				}
			}
		}
		startMoving();
		startGriefing();
	}

	private void setForBlock(Location loc, int hight) {
		Material mat = loc.subtract(0, hight, 0).getBlock().getType();
		if (mat != null && mat != Material.AIR) {
			for (int i = 0; i < 2; i++) {
				FallingBlock block = loc.getWorld().spawnFallingBlock(loc.add(0, 1, 0), new MaterialData(mat));
				Vector vec = new Vector(0, 0.5, 0);
				block.setVelocity(vec);
				block.setCustomName("§4walkingtornado");
				block.setGravity(false);
				blocks.add(block);
			}
			loc.subtract(0, hight, 0).getBlock().setType(Material.AIR);
		}
	}

	public void startMoving() {
		schedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				direction = randomDirection();
				for (FallingBlock b : blocks) {
					b.setTicksLived(1);
					b.setVelocity(direction);
				}
			}
		}, 0L, 10L);
	}

	public void startGriefing() {
		griefingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (blocks != null)
					for (FallingBlock b : blocks) {
						spawnForBlocksInRegion(b);
					}
			}
		}, 0L, 2L);

	}
	
	private void spawnForBlocksInRegion(FallingBlock b) {
		Location l2 = b.getLocation();
		Location l = l2;
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				for (int z = -1; z < 2; z++) {
					Location loc = new Location(l.getWorld(), x + l.getX(), y + l.getY(), z + l.getZ());
					setForBlock(loc, hight);
				}
			}
		}
	}

	public void stopGriefing() {
		if (griefingID != -1) {
			Bukkit.getScheduler().cancelTask(griefingID);
			griefingID = -1;
		}
	}

	public void stopMoving() {
		if (schedulerID != -1) {
			Bukkit.getScheduler().cancelTask(schedulerID);
			schedulerID = -1;
		}
	}

	public void delete() {

	}

	public int getHight() {
		return this.hight;
	}

	public Location getSpawnLoc() {
		return this.spawnLoc;
	}

	public Location getCurrentLoc() {
		return this.currentLoc;
	}
}
