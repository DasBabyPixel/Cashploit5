package de.cashploit5.command;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.cashploit5.uuidmanager.UUIDManager;
import net.minecraft.server.v1_12_R1.PacketPlayOutGameStateChange;

public class DemoCommand extends Command {

	public DemoCommand() {
		super("demo");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if(args.length==1) {
			Player target = UUIDManager.getPlayer(args[0]);
			if(target!=null) {
				sendDemo(target);
				
	
				
				p.sendMessage("§aDemo Screen wurde gesendet!");
			}else {
				p.sendMessage("§cDieser Spieler ist nicht online!");
			}
		}else {
			p.sendMessage("§c°demo <Player>");
		}
	}
	public static void sendDemo(Player player) {
		   PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(5, (float)0);
		   ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
}
