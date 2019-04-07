package de.cashploit5.v1_8_8.plugin;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.cashploit5.Main;
import de.cashploit5.console.console;
import de.cashploit5.v1_8_8.packetmanager.PacketManager;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class ScoreboardManager implements Listener, CommandExecutor {

	public ScoreboardManager(Main main) {
		setUp();
		Bukkit.getPluginManager().registerEvents(this, main);
		main.getCommand("discord").setExecutor(this);
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {

			@Override
			public void run() {
				for (Player all : Bukkit.getOnlinePlayers()) {
					registerPlayer(all);
				}

			}
		}, 4);
	}

	private static String teamSpeakName = "";
	private static String discordName = "";
	private static String scoreboardName = "";

	private static String defaultRang = "";
	private static String defaultPrefix = "";

	private static String ownerRang = "";
	private static String ownerPrefix = "";

	public static Economy eco;
	private static List<String> range = new ArrayList<>();
	private static HashMap<String, String> prefixFromRang = new HashMap<>();
	private static HashMap<Player, String> prefixFromPlayer = new HashMap<>();
	private static HashMap<Player, Scoreboard> sbmap = new HashMap<>();
	private static HashMap<Player, Boolean> isRegistered = new HashMap<>();

	public static void setUp() {
		loadFromConfig();
		RegisteredServiceProvider<Economy> rsp = Main.getPlugin().getServer().getServicesManager()
				.getRegistration(Economy.class);
		eco = rsp.getProvider();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					registerPlayer(all);
					setPrefix(all);
				}
			}
		}, 20L);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				for(Player all : Bukkit.getOnlinePlayers()) {
					
					setSidebar(all);
					setTab();
					
				}
			}
		}, 0L, 10*20L);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String name = ScoreboardManager.sbmap.get(p).getEntryTeam(p.getName()).getPrefix();

		String arrmsg[] = e.getMessage().split(" ");
		
		for (int i = 0; i < arrmsg.length; i++) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (arrmsg[i].equalsIgnoreCase(all.getName())) {
					arrmsg[i] = "§b@" + all.getName() + "§7";
					boolean pling = Main.getPlugin().getConfig().getBoolean("togglepling." + all.getName());
					if (pling)
						all.playSound(all.getLocation(), Sound.BLOCK_NOTE_PLING, 3.0f, 1.0f);
				}
			}
		}
		
		String newmsg;
		
		newmsg = name + p.getName() + "Â§8 Â»Â§7";
		
		
		for (int i = 0; i < arrmsg.length; i++) {
			newmsg = newmsg + " " + arrmsg[i];

		}
		
		if(p.hasPermission("chat.color")) {
			newmsg = ChatColor.translateAlternateColorCodes('&', newmsg);
		}
		if(p.hasPermission("prefix.everone")) {
			newmsg = newmsg.replaceAll("everyone", "@everyone");
			newmsg = newmsg.replaceAll("@everyone", "§b@everyone§7");
			newmsg = newmsg.replaceAll("@§b@everyone§7", "§b@everyone§7");
			if(newmsg.contains("§b@everyone§7")) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					boolean pling = Main.getPlugin().getConfig().getBoolean("togglepling." + all.getName());
					if(pling) {
						all.playSound(all.getLocation(), Sound.BLOCK_NOTE_PLING, 3.0f, 1.0f);
					}
					
				}
			}
			
		}
		
		
		
		newmsg = newmsg.replaceAll("%", "%%");

		e.setFormat(newmsg);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (isRegistered.get(p) == null) {
			registerPlayer(p);
			isRegistered.put(p, true);
		}
		setPrefix(p);
		setSidebar(p);
	}

	public static void setPrefix(Player p) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			for (String rang : range) {
				String Rang = rang;
				rang = rang.substring(1);
				rang = rang.substring(1);
				if (all.isOp()) {
					sbmap.get(p).getTeam(ownerRang).addEntry(all.getName());
					String ownerRang = ScoreboardManager.ownerPrefix;
					ownerRang = ownerRang.substring(1);
					ownerRang = ownerRang.substring(1);
					ownerRang = ownerRang.substring(0, ownerRang.length()-3);
					prefixFromPlayer.put(p, ownerRang);
				} else if (all.hasPermission("prefix." + rang.toLowerCase())) {
					sbmap.get(p).getTeam(Rang).addEntry(all.getName());
					String Rang2 = prefixFromRang.get(Rang);
					Rang2 = Rang2.substring(1);
					Rang2 = Rang2.substring(1);
					Rang2 = Rang2.substring(0, Rang2.length()-3);
					prefixFromPlayer.put(p, Rang2);
				} else {
					sbmap.get(p).getTeam(defaultRang).addEntry(all.getName());
					String defaultPrefix = ScoreboardManager.defaultPrefix;
					defaultPrefix = defaultPrefix.substring(1);
					defaultPrefix = defaultPrefix.substring(1);
					defaultPrefix = defaultPrefix.substring(0, defaultPrefix.length()-3);
					prefixFromPlayer.put(p, defaultPrefix);
				}
			}
		}
	}

	public static void registerPlayer(Player p) {
		if (!eco.hasAccount(p))
			eco.createPlayerAccount(p);
		sbmap.put(p, Bukkit.getScoreboardManager().getNewScoreboard());
		Scoreboard sb = sbmap.get(p);
		Objective ob = sb.getObjective("sidebar");
		if (ob == null)
			ob = sb.registerNewObjective("sidebar", "dummy");
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		ob.setDisplayName(scoreboardName);

		for (String rang : range) {
			String prefix = prefixFromRang.get(rang);
			try {
				sb.registerNewTeam(rang);
			} catch (Exception e) {
			}
			sb.getTeam(rang).setPrefix(prefix);
		}
		
		try {
			sb.registerNewTeam(defaultRang);
		} catch (Exception e) {
		}
		sb.getTeam(defaultRang).setPrefix(defaultPrefix);
		try {
			sb.registerNewTeam(ownerRang);
		} catch (Exception e) {
		}
		sb.getTeam(ownerRang).setPrefix(ownerPrefix);
		sbmap.put(p, sb);
		p.setScoreboard(sb);
	}

	public static void setSidebar(Player p) {
		Scoreboard sb = sbmap.get(p);
		if(sb == null) {sb = Bukkit.getScoreboardManager().getNewScoreboard(); sbmap.put(p, sb);}
		Objective obj = sb.getObjective("sidebar");
		if(obj == null) obj = sb.registerNewObjective("sidebar", "dummy");
		Team money = sb.getTeam("money");
		Team online = sb.getTeam("onplayers");
		if(money == null) money = sb.registerNewTeam("money");
		if(online == null) online = sb.registerNewTeam("onplayers");
		online.addEntry("§a§7");
		money.addEntry("§b§7");
		
		DecimalFormat format = new DecimalFormat("#############.##");
		String onlines = Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getServer().getMaxPlayers();
		String bal = format.format(eco.getBalance(p));
		
		money.setSuffix(bal);
		online.setSuffix(onlines);
		money.setPrefix("  ");
		online.setPrefix("  ");
		
		obj.getScore("   ").setScore(6);
		obj.getScore("§7» §c§lGeld").setScore(5);
		obj.getScore("§b§7").setScore(4);
		obj.getScore("  ").setScore(3);
		obj.getScore("§7» §c§lOnline").setScore(2);
		obj.getScore("§a§7").setScore(1);
		obj.getScore(" ").setScore(0);
	}
	
	public static void setTab() {
		FileConfiguration cfg = Main.getPlugin().getConfig();

		String Header = cfg.getString("tablist.header");
		String Footer = cfg.getString("tablist.footer");

		Header = ChatColor.translateAlternateColorCodes('&', Header);
		Footer = ChatColor.translateAlternateColorCodes('&', Footer);
		
		
		if (Header == null) {
			Header = " ";
			cfg.set("tablist.header", "");
			Main.getPlugin().saveConfig();
		}
		if (Footer == null) {
			Footer = " ";
			cfg.set("tablist.footer", "");
			Main.getPlugin().saveConfig();
		}

		IChatBaseComponent tabHeader = ChatSerializer.a("{\"text\":\"" + Header + "\"}");
		IChatBaseComponent tabFooter = ChatSerializer.a("{\"text\":\"" + Footer + "\"}");

		PacketPlayOutPlayerListHeaderFooter tabPacket = new PacketPlayOutPlayerListHeaderFooter();
		
		try {
			Field HeaderField = tabPacket.getClass().getDeclaredField("a");
			HeaderField.setAccessible(true);
			HeaderField.set(tabPacket, tabHeader);
			Field FooterField = tabPacket.getClass().getDeclaredField("b");
			FooterField.setAccessible(true);
			FooterField.set(tabPacket, tabFooter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PacketManager.sendAllPlayers(tabPacket);
		}

	}
	
	public static void loadFromConfig() {
		Main.getPlugin().saveDefaultConfig();
		FileConfiguration cfg = Main.getPlugin().getConfig();
		teamSpeakName = cfg.getString("tabulator.ts");
		discordName = cfg.getString("tabulator.discord");
		scoreboardName = cfg.getString("scoreboard.name");
		defaultRang = cfg.getString("prefix.default");
		ownerRang = cfg.getString("prefix.owner");
		if (defaultRang == null)
			defaultRang = "";
		if (teamSpeakName == null)
			teamSpeakName = "";
		if (discordName == null)
			discordName = "";
		if (scoreboardName == null)
			scoreboardName = "";
		if (ownerRang == null)
			ownerRang = "";

		scoreboardName = ChatColor.translateAlternateColorCodes('&', scoreboardName);
		discordName = ChatColor.translateAlternateColorCodes('&', discordName);
		teamSpeakName = ChatColor.translateAlternateColorCodes('&', teamSpeakName);
		defaultRang = ChatColor.translateAlternateColorCodes('&', defaultRang);
		ownerRang = ChatColor.translateAlternateColorCodes('&', ownerRang);
		String oldD = defaultRang;
		if (ownerRang.contains(";")) {
			String[] de = ownerRang.split(";");
			if (de.length == 2) {
				ownerRang = de[0];
				if (de[1].contains(":")) {
					String[] de2 = de[1].split(":");
					if (de2.length == 2 && de2[0].equals("Prefix")) {
						ownerPrefix = de2[1];
					} else {
						console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
								+ "hat bei §6prefix.owner §c\"§6x;Prefix:y§c\" erwartet, aber nicht gefunden!");
					}
				} else {
					console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
							+ "hat bei §6prefix.owner §c\"§6x;Prefix:y§c\" erwartet, aber nicht gefunden!");
				}
			} else {
				console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
						+ "hat bei §6prefix.owner §c\"§6x;Prefix:y§c\" erwartet, aber nicht gefunden!");
			}
		} else {
			console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
					+ "hat bei §6prefix.owner §c\"§6x;Prefix:y§c\" erwartet, aber nicht gefunden!");
		}

		if (defaultRang.contains(";")) {
			String[] de = defaultRang.split(";");
			if (de.length == 2) {
				defaultRang = de[0];
				if (de[1].contains(":")) {
					String[] de2 = de[1].split(":");
					if (de2.length == 2 && de2[0].equals("Prefix")) {
						defaultPrefix = de2[1];
					} else {
						console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
								+ "hat bei §6prefix.default §c\"§6x;Prefix:y§c\" erwartet, aber nicht gefunden!");
					}
				} else {
					console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
							+ "hat bei §6prefix.default §c\"§6x;Prefix:y§c\" erwartet, aber nicht gefunden!");
				}
			} else {
				console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
						+ "hat bei §6prefix.default §c\"§6x;Prefix:y§c\" erwartet, aber nicht gefunden!");
			}
		} else {
			console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
					+ "hat bei §6prefix.default §c\"§6x;Prefix:y§c\" erwartet, aber nicht gefunden!");
		}
		if (defaultRang.equals(oldD)) {
			defaultRang = "99Spieler";
		}

		range = cfg.getStringList("prefix.raenge");
		List<String> Range = new ArrayList<>();
		for (String rang : range) {
			rang = ChatColor.translateAlternateColorCodes('&', rang);
			if (rang.contains(";")) {
				String[] args1 = rang.split(";");
				String Rang = args1[0];
				if (args1.length == 2 && args1[1].contains(":")) {
					String[] args2 = args1[1].split(":");
					if (args2.length == 2 && args2[0].equals("Prefix")) {
						String Prefix = args2[1];
						Range.add(Rang);
						prefixFromRang.put(Rang, Prefix);
					} else {
						console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
								+ "hat \"x;Prefix:y§c\" erwartet, aber nicht gefunden!");
					}
				} else {
					if (args1.length != 2) {
						console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
								+ "hat \"§6x;y§c\" erwartet, aber nicht gefunden!");
					} else {
						console.send("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n§c"
								+ "hat \"§6x;y:z§c\" erwartet, aber nicht gefunden!");
					}
				}
			} else {
				Bukkit.getConsoleSender().sendMessage("§8[§ePrefixPlugin§8]§c SyntaxFehler in der Config: \n"
						+ "§chat \"§6;§c\" erwartet, aber nicht gefunden!");
			}
		}
		range.clear();
		range.addAll(Range);
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("command.discord")));
		return false;
	}

}
