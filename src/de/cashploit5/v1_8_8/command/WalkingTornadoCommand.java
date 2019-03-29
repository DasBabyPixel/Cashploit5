package de.cashploit5.v1_8_8.command;

import org.bukkit.entity.Player;

import de.cashploit5.Main;
import de.cashploit5.v1_8_8.tornado.Tornado;
import de.cashploit5.v1_8_8.uuidmanager.UUIDManager;

public class WalkingTornadoCommand extends Command {
	
	public WalkingTornadoCommand() {
		super("walkingtornado");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if(args.length == 1) {
			Player t = UUIDManager.getPlayer(args[0]);
			if(t == null) {
				Main.sendMessage(p, "§cDieser Spieler ist nicht online!");
			} else {
				Tornado tornado = new Tornado(t.getLocation(), (short)10);
				tornado.spawn();
			}
		} else {
			Main.sendMessage(p, "§6°walkingtornado <Spieler>");
		}
	}
	
}
