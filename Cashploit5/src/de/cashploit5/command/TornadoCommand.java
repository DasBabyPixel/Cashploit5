package de.cashploit5.command;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import de.cashploit5.Main;

public class TornadoCommand extends Command {

	public TornadoCommand() {
		super("tornado");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Main.sendMessage(p, "§cDieser Spieler ist nicht online!");
			} else {
				spawnTornado(target.getLocation(), (short) 1);
				Main.sendMessage(p, "§9Ohh :D Ein wildes Tornado ist erschienen!");
			}
		} else if (args.length == 3) {
			try {
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				int z = Integer.parseInt(args[2]);
				Location loc = new Location(p.getWorld(), x, y, z);
				spawnTornado(loc, (short) 1);
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
		int session = 0, x = 1, z = 1;
		for(int y = 0; y < size*2; y++) {
			for(int ax = 0; ax < x; ax++) {
				for(int az = 0; az < z; az++) {
					Location l = loc;
					l.setX(loc.getX() + (ax-x/2));
					l.setZ(loc.getZ() + (az-z/2));
					setForBlock(l, size);
					for(Player all : Bukkit.getOnlinePlayers()) {all.teleport(l);all.sendMessage("X:" + x);}
				}
			}
			if(session == 3) {session = 0; x++; z++;}
			session++;
		}
	}

	private static void setForBlock(Location loc, short size) {
		int s = size * 10;
		Material mat = loc.subtract(0, s / 2, 0).getBlock().getType();
		if (mat != null && mat != Material.AIR) {
			FallingBlock block = loc.getWorld().spawnFallingBlock(loc.add(0, s / 2, 0), new MaterialData(mat));
			Random r = new Random();
			Vector vec = new Vector(
					r.nextInt(2)==0 ? r.nextInt(10) / (r.nextInt(40) + 1) : -r.nextInt(10) / (r.nextInt(40) + 1)
							, 2, 
					r.nextInt(2)==0 ? r.nextInt(10) / (r.nextInt(40) + 1) : -r.nextInt(10) / (r.nextInt(40) + 1));
			block.setVelocity(vec);
		}
		loc.subtract(0, s / 2, 0).getBlock().setType(Material.AIR);
	}
}
