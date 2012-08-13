package net.roguedraco.rankuponkills;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import net.roguedraco.player.RDPlayer;

public class Ranks {

	public static Map<Integer, Rank> ranks = new HashMap<Integer, Rank>();

	public static Rank getNextRank(RDPlayer rdp) {
		Set<Integer> keys = ranks.keySet();
		Integer key = 0;
		for(Integer rankKey : keys) {
			Rank rank = getRank(rankKey);
			if(rank.isEligable(rdp.getInt("count.mob"), rdp.getInt("count.player"))) {
				key = rankKey;
			}
		}
		return getRank(key+1);
	}

	public static Rank getRank(Integer key) {
		if (ranks.containsKey(key)) {
			return ranks.get(key);
		} else {
			return ranks.get(0);
		}
	}
	
	public static Rank getRank(RDPlayer rdp) {
		Set<Integer> keys = ranks.keySet();
		Rank rank = getRank(0);
		for(Integer rankKey : keys) {
			Rank rankVal = getRank(rankKey);
			if(rankVal.isEligable(rdp.getInt("count.mob"), rdp.getInt("count.player"))) {
				rank = rankVal;
			}
		}
		return rank;
	}

	public static void loadRanks() {
		ConfigurationSection conf = RankupOnKillsPlugin.getPlugin().getConfig().getConfigurationSection("ranks");
		Iterator<String> rankKeys = conf.getKeys(false).iterator();
		Integer x = 0;
		while (rankKeys.hasNext()) {
			String key = rankKeys.next();

			Boolean	sharedCount = conf.getBoolean(key+".sharedCount",false);
			Boolean	requireBoth = conf.getBoolean(key+".requireBoth",false);
			Integer	pvpCount = conf.getInt(key+".playerCount",0);
			Integer mobCount = conf.getInt(key+".mobCount",0);
			Integer totCount = conf.getInt(key+".totCount",(pvpCount+mobCount));
			
			Rank rank = new Rank(key, sharedCount, requireBoth, pvpCount,
					mobCount, totCount);
			ranks.put(x, rank);
			RankupOnKillsPlugin.debug("Loaded Rank: ID:"+x+", Rank:"+rank.getName()+", Shared: "+sharedCount+", Both: "+requireBoth+", PVP:"+pvpCount+", Mob:"+mobCount+", Tot:"+totCount);
			x++;
		}
	}
}
