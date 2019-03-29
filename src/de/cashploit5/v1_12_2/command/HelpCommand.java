package de.cashploit5.v1_12_2.command;

import org.bukkit.entity.Player;

import de.cashploit5.Main;


public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help");
	}
	
	

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		p.sendMessage("§c*********************");
		for(Command c : Main.getPlugin().getCommandManager_v1_12_2().getCommands()) {
			p.sendMessage("§6°" + c.getName());
		}
		p.sendMessage("§c*********************");
	}
}
