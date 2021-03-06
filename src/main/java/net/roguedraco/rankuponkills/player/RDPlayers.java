package net.roguedraco.rankuponkills.player;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.roguedraco.rankuponkills.RankupOnKillsPlugin;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RDPlayers {
	
	// Settings for RDPlayers (Use across all of my plugins)
	public static boolean saveData = true;
	
	public static JavaPlugin plugin = null;
	private static Map<String,RDPlayer> players = new HashMap<String,RDPlayer>();
	
	public static void setPlugin(JavaPlugin plugin) {
		RDPlayers.plugin = plugin;
	}

	public static void loadAll() {
		Collection<RDPlayer> col = RDPlayers.players.values();
		for(RDPlayer rdp : col) {
			rdp.load();
		}
	}

	public static void saveAll() {
		Collection<RDPlayer> col = RDPlayers.players.values();
		for(RDPlayer rdp : col) {
			rdp.save();
		}
	}
	
	public static RDPlayer getPlayer(String name) {
		// Check if player is new
		if((RDPlayers.check(name) || saveData == false) && players.containsKey(name)) {
			// Player is existing, load data
			RDPlayer pd = players.get(name);
			return pd;
		}
		else {
			// Player is new, create data
			RDPlayer pd = new RDPlayer(name);
			pd.save();
			players.put(name, pd);
			return pd;
		}
	}
	
	public static boolean check(String name) {
		if(saveData == true) {
			File file = new File("plugins/"+plugin.getName()+"/players/",name+".yml");
			boolean exists = file.exists();
			if (!exists) {
				return false;
			}
			else {
				return true;
			}
		}
		return false;
	}
	
	public static void playerJoin(final PlayerJoinEvent event) {
		
		String name = event.getPlayer().getName();
		// Check if player is new
		addPlayer(name);
		RankupOnKillsPlugin.debug("Added player to RDPlayers: "+name);
		
	}
	
	public static void playerQuit(final PlayerQuitEvent event) {
		String name = event.getPlayer().getName();
		RDPlayers.getPlayer(name).save();
		RDPlayers.players.remove(name);
		RankupOnKillsPlugin.debug("Removed player to RDPlayers: "+name);
	}
	
	public static Collection<RDPlayer> getPlayers() {
		return RDPlayers.players.values();
	}
	
	public static void addPlayer(String name) {
		if(RDPlayers.check(name) == true) {
			// Player is existing, load data
			RDPlayer pd = new RDPlayer(name);
			pd.load();
			pd.save();
			RDPlayers.players.put(name,pd);
		}
		else {
			// Player is new, create data
			RDPlayer pd = new RDPlayer(name);
			pd.load();
			pd.save();
			RDPlayers.players.put(name, pd);
		}
	}
	
	public static Set<RDPlayer> getAllPlayers() {
		Set<RDPlayer> set = new HashSet<RDPlayer>();
		
		File[] files = new File("plugins/RankupOnKills/players").listFiles();
		for (File file : files) {
			String name = file.getName();
			int pos = name.lastIndexOf('.');
			String ext = name.substring(pos + 1);
			if (ext.equalsIgnoreCase("yml")) {
				name = name.replaceAll(".yml", "");
				RDPlayer p = new RDPlayer(name);
				p.load();
				set.add(p);
			}
		}
		
		return set;
	}

}
