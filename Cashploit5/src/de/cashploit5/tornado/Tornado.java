package de.cashploit5.tornado;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import de.cashploit5.Main;
import de.cashploit5.command.TrustCommand;

public class Tornado {

	public static List<Tornado> tornados = new ArrayList<>();

	private Location spawnLoc;
	private Location currentLoc;
	private List<FallingBlock> blocks = new ArrayList<>();
	private List<FallingBlock> sessionBlocks = new ArrayList<>();
	private ArmorStand heart;
	private Vector direction;
	private int hight;
	private int schedulerID = -1;
	private int Y = 0;
	private int griefingID = -1;
	private int fallingHeartID = -1;
	private int throwingID = -1;

	public Tornado(Location spawnLoc, short hight) {
		direction = randomDirection();
		this.spawnLoc = spawnLoc;
		this.currentLoc = spawnLoc;
		this.hight = hight;
		this.heart = spawnLoc.getWorld().spawn(spawnLoc, ArmorStand.class);
		this.Y = spawnLoc.getBlockY() - hight/2;
		heart.setVisible(false);
		heart.setCustomName("§4WalkingTornado");
		heart.setCustomNameVisible(true);
		tornados.add(this);
	}

	private Vector randomDirection() {
		Random r = new Random();
		double x = ((double) (r.nextInt(20) + 1)) / (double) 100;
		double z = ((double) (r.nextInt(20) + 1)) / (double) 100;
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
									spawnLoc.getZ())) <= n) {

						setForBlock(new Location(spawnLoc.getWorld(), spawnLoc.getX() + x, spawnLoc.getY() + y,
								spawnLoc.getZ() + z), hight);
						blocks.addAll(sessionBlocks);
						sessionBlocks.clear();
					}
				}
			}
		}
		startMoving();
		startGriefing();
		startThrowing();
		startFallingHeart();
	}

	private void setForBlock(Location loc, int hight) {
		Material mat = loc.subtract(0, hight, 0).getBlock().getType();
		if (mat != null && mat != Material.AIR) {

			FallingBlock block = loc.getWorld().spawnFallingBlock(loc.add(0, 3, 0), new MaterialData(mat));
			block.setCustomName("§4walkingtornado");
			block.setGravity(false);
			sessionBlocks.add(block);

			loc.subtract(0, hight, 0).getBlock().setType(Material.AIR);
		}
	}

	public void startFallingHeart() {
		fallingHeartID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				
				spawnForBlocksInRegion(heart.getLocation());
				
			}
		}, 0l, 1l);
	}
	
	public void stopFallingHeart() {
		if(fallingHeartID != -1) {
			Bukkit.getScheduler().cancelTask(fallingHeartID);
			fallingHeartID = -1;
		}
	}
	
	public void startMoving() {
		schedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (new Random().nextInt(50) == 0) {
					direction = randomDirection();
				}
				heart.setVelocity(direction.add(new Vector(0, 0.1, 0)));
				Location l = heart.getLocation();
				l.setY(Y);
				heart.teleport(l);
				for (FallingBlock b : blocks) {
					b.setTicksLived(1);
				}
			}
		}, 0L, 2L);
	}

	public void startGriefing() {
		griefingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (blocks != null) {
					for (FallingBlock b : blocks) {
						spawnForBlocksInRegion(b.getLocation());
					}
					blocks.addAll(sessionBlocks);
					sessionBlocks.clear();
				}
			}
		}, 0L, 2L);

	}

	private void spawnForBlocksInRegion(Location b) {
		Location l2 = b;
		Location l = l2;
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 10; y++) {
				for (int z = -1; z < 2; z++) {
					Location loc = new Location(l.getWorld(), x + l.getX(), y + l.getY(), z + l.getZ());
					loc.getBlock().breakNaturally();
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
		for(FallingBlock b : blocks) b.remove();
		heart.remove();
		stopFallingHeart();
		stopGriefing();
		stopMoving();
		stopThrowing();
	}

	public void startThrowing() {

		throwingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				Location h = heart.getLocation();
				
				for (FallingBlock b : blocks) {
					Location l = b.getLocation();
					
					Vector vec = randomDirection();
					
					
					int dist = 0;
					int d = 0;
					
					if(l.getY()-Y >= 0 && l.getY()-Y < 3) {
						dist += 10;
						d += 1;
					}
					if(l.getY()-Y >= 3 && l.getY()-Y < 6) {
						dist += 20;
						d += 2;
					}
					if(l.getY()-Y >= 6 && l.getY()-Y < 9) {
						dist += 30;
						d += 3;
					}
					if(l.getY()-Y >= 9 && l.getY()-Y < 12) {
						dist += 40;
						d += 4;
					}
					if(l.getY()-Y >= 12) {
						dist += 50;
						d += 5;
					}
					
					double oldY = l.getY();
					
					l.setY(Y);
					h.setY(Y);
					
					if(new Random().nextInt(dist) == 0 || h.distance(l) > d) {
						h.setY(oldY);
						double ax = h.getX();
						double az = h.getZ();
						double bx = l.getX();
						double bz = l.getZ();
						double x = ax - bx;
						double z = az - bz;
						vec = new Vector(x, 0, z).normalize();
						vec.multiply(0.5);
						b.setVelocity(vec);
					} else {
						b.setVelocity(vec);
					}
					
					for(Player all : Bukkit.getOnlinePlayers()) {
						if(!TrustCommand.getTrustedPlayers().contains(all.getUniqueId().toString())) {
							if(all.getLocation().distance(b.getLocation()) < 4) {
							Location ha = b.getLocation();
							Location al = all.getLocation();
							ha.setY(Y + (hight/3)*2);
							double ax = ha.getX();
							double ay = ha.getY();
							double az = ha.getZ();
							
							double bx = al.getX();
							double by = al.getY();
							double bz = al.getZ();
							
							double x = ax - bx;
							double y = ay - by;
							double z = az - bz;
							
							Vector aVec = new Vector(x, y, z).normalize();
							aVec.multiply(0.5);
							all.damage(0.5);
							all.setVelocity(aVec);
							}
						}
					}
				}
			}
		}, 10L, 1L);

	}

	public void stopThrowing() {
		if (throwingID != -1) {
			Bukkit.getScheduler().cancelTask(throwingID);
			throwingID = -1;
		}
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
