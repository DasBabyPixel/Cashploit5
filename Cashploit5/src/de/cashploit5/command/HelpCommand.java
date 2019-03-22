package de.cashploit5.command;

import org.bukkit.entity.Player;

import de.cashploit5.Main;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help");
	}
	
	@Override
	public void onCommand(Player p, Command command, String[] args) {
		p.sendMessage("§c*********************");
		for(Command c : Main.getPlugin().getCommandManager().getCommands()) {
			p.sendMessage("§6" + c.getName());
		}
		p.sendMessage("§c*********************");
	}
}
