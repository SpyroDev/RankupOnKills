package net.roguedraco.rankuponkills;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.roguedraco.rankuponkills.Metrics.Graph;
import net.roguedraco.rankuponkills.player.RDPlayer;
import net.roguedraco.rankuponkills.player.RDPlayers;

public class Stats {

	private static Map<String, Integer> weaponStatValues = new HashMap<String, Integer>();
	private static Map<String, Integer> mobStatValues = new HashMap<String, Integer>();

	public static void weaponStats(Graph weapons) {
		Set<String> weaponNames = weaponStatValues.keySet();
		for (final String weapon : weaponNames) {
			weapons.addPlotter(new Metrics.Plotter(weapon) {

				@Override
				public int getValue() {
					return Stats.getWeaponValue(weapon);
				}

			});
		}
	}

	public static void mobStats(Graph mobs) {

		Set<String> mobNames = mobStatValues.keySet();
		for (final String mob : mobNames) {
			mobs.addPlotter(new Metrics.Plotter(mob) {

				@Override
				public int getValue() {
					return Stats.getMobValue(mob);
				}

			});
		}

	}

	public static int getMobValue(String key) {
		if (mobStatValues.containsKey(key)) {
			return mobStatValues.get(key);
		}
		return 0;
	}
	
	public static int getWeaponValue(String key) {
		if (weaponStatValues.containsKey(key)) {
			return weaponStatValues.get(key);
		}
		return 0;
	}
	
	public static void incrimentWeapon(String weapon) {
		int val = getWeaponValue(weapon);
		weaponStatValues.put(weapon, (val+1));
	}
	
	public static void incrimentMob(String mob) {
		int val = getMobValue(mob);
		mobStatValues.put(mob, (val+1));
	}

	public void calculateMobStats() {
		Set<RDPlayer> players = RDPlayers.getAllPlayers();
		for(RDPlayer player : players) {
			player.getInt("count.mob");
		}
	}
}
