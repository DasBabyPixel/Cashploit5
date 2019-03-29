package de.cashploit5.v1_12_2.command;

import org.bukkit.entity.Player;

import de.cashploit5.Main;
import de.cashploit5.v1_12_2.tornado.Tornado;

public class DeleteTornadosCommand extends Command {

	public DeleteTornadosCommand() {
		super("deletetornados");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		for(Tornado tornado : Tornado.tornados) tornado.delete();
		Tornado.tornados.clear();
		Main.sendMessage(p, "§aAlle Tornados wurde gecleart!");
	}
}
