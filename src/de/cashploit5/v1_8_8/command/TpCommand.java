package de.cashploit5.v1_8_8.command;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.cashploit5.Main;
import de.cashploit5.v1_8_8.uuidmanager.UUIDManager;

public class TpCommand extends Command {

	public TpCommand() {
		super("tp");
	}

	@Override
	public void onCommand(Player p, Command command, String[] args) {
		if(args.length == 1) {
			Player target = UUIDManager.getPlayer(args[0]);
			if(target == null) {
				Main.sendMessage(p, "§cDieser Spieler ist nicht online!");
			} else {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5*20, 1, true, false), true);
				p.teleport(target.getLocation().add(0, 3, 0));
			}
		} else {
			Main.sendMessage(p, "§6°tp <Spieler>");
		}
	}
}
