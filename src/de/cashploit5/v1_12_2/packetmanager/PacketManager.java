package de.cashploit5.v1_12_2.packetmanager;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.cashploit5.v1_12_2.command.TrustCommand;
import net.minecraft.server.v1_12_R1.Packet;

public class PacketManager {

	public static void sendAllPlayers(Packet<?> packet) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public static void sendAllTrustedPlayers(Packet<?> packet) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (TrustCommand.getTrustedPlayers().contains(p.getUniqueId().toString()))
				if (p.isOnline()) {
					Player player = (Player) p;
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
				}
		}
	}

	public static void sendAllNoneTrustedPlayers(Packet<?> packet) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!TrustCommand.getTrustedPlayers().contains(p.getUniqueId().toString())) {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}
}
