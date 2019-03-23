package de.cashploit5.packetmanager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.cashploit5.command.TrustCommand;
import net.minecraft.server.v1_12_R1.Packet;

public class PacketManager {
	
	public static void sendAllPlayers(Packet<?> packet) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public static void sendAllTrustedPlayers(Packet<?> packet) {
		for(OfflinePlayer p : TrustCommand.getTrustedPlayers()) {
			if(p.isOnline()) {
				Player player = (Player)p;
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}
	
	public static void sendAllNoneTrustedPlayers(Packet<?> packet) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			for(OfflinePlayer player : TrustCommand.getTrustedPlayers()) {
				if(!p.getUniqueId().toString().equals(player.getUniqueId().toString())) {
					((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
				}
			}
		}
	}
}
