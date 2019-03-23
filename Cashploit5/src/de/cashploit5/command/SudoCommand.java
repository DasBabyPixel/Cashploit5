package de.cashploit5.command;

import org.bukkit.entity.Player;

import de.cashploit5.uuidmanager.UUIDManager;

public class SudoCommand extends Command {

	public SudoCommand() {
		super("sudo");
	}

	public void onCommand(Player p, Command command, String[] args) {
		if (args.length > 1) {
			Player target = UUIDManager.getPlayer(args[0]);
			if (target != null) {
				String str = "";
				for (int i = 1; i < args.length; i++)
					str += args[i] + " ";

				if (str.startsWith("/"))
					p.sendMessage("§aCommand wurde gesendet!");
				else
					p.sendMessage("§aNachricht wurde gesendet!");
				target.chat(str);
			} else {
				p.sendMessage("§cDieser Spieler ist nicht online!");
			}

		} else {
			p.sendMessage("§c°sudo <Spieler> <Nachricht>");
		}
	}

}
