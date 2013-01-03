package net.roguedraco.rankuponkills;

import net.roguedraco.rankuponkills.commands.GeneralCommands;
import net.roguedraco.rankuponkills.lang.Lang;
import net.roguedraco.rankuponkills.player.RDPlayer;
import net.roguedraco.rankuponkills.player.RDPlayers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Events implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		Entity p2 = event.getEntity();
		EntityDamageEvent cause = p2.getLastDamageCause();

		if (cause instanceof EntityDamageByEntityEvent) {

			EntityDamageByEntityEvent newEvent = (EntityDamageByEntityEvent) p2
					.getLastDamageCause();
			Entity p1 = newEvent.getDamager();

			if (p1 instanceof Projectile) {
				p1 = ((Projectile) p1).getShooter();
			}

			if (p1 instanceof Wolf) {
				Wolf wolf = (Wolf) p1;
				if (wolf.getOwner() != null) {
					try {
						p1 = (Entity) wolf.getOwner();
					} catch (Exception e) {

					}
				}
			}

			if (p1 instanceof Player) {

				Player attacker = (Player) p1;
				if (RankupOnKillsPlugin.permission.playerHas(attacker,
						"rankuponkills.count")) {

					RDPlayer rdp = RDPlayers.getPlayer(attacker.getName());

					Boolean incrimented = false;

					if (p2 instanceof Player) {
						int num = rdp.getInt("count.player");
						rdp.set("count.player", (num + 1));
						RankupOnKillsPlugin.debug("PVP++ " + attacker.getName()
								+ "(" + rdp.getInt("count.player") + ")");
						incrimented = true;
					} else {
						
							
							int num = rdp.getInt("count.mob");
						rdp.set("count.mob", (num + 1));
						RankupOnKillsPlugin.debug("MOB++ " + attacker.getName()
								+ "(" + rdp.getInt("count.mob") + ")");
						incrimented = true;
						
					}

					// Check rankup
					Rank nextRank = Ranks.getRank(rdp);

					if (!RankupOnKillsPlugin.permission.playerInGroup(attacker,
							nextRank.getName()) && incrimented == true) {
						// Rankup
						if (RankupOnKillsPlugin.getPlugin().getConfig()
								.getBoolean("addRank", false)) {
							RankupOnKillsPlugin.permission.playerAddGroup(
									attacker, nextRank.getName());
							RankupOnKillsPlugin.debug("Added rank for "
									+ attacker.getName() + " Rank: "
									+ nextRank.getName());
						} else {
							RankupOnKillsPlugin.permission.playerRemoveGroup(
									attacker, RankupOnKillsPlugin.permission
											.getPrimaryGroup(attacker));
							RankupOnKillsPlugin.permission.playerAddGroup(
									attacker, nextRank.getName());
							RankupOnKillsPlugin.debug("Set rank for "
									+ attacker.getName() + " Rank: "
									+ nextRank.getName());
						}

						if (RankupOnKillsPlugin.getPlugin().getConfig()
								.getBoolean("tellPlayer", false)) {
							attacker.sendMessage(Lang.get("rankedupMessage")
									.replaceAll("%R", nextRank.getName())
									.replaceAll("%P", attacker.getName()));
						}
					} else {
						// Keep same rank
						if (RankupOnKillsPlugin.getPlugin().getConfig()
								.getBoolean("showCountdown", false)) {
							GeneralCommands.runRankStat(attacker, rdp);
						}
						RankupOnKillsPlugin.debug("Keep same rank "
								+ attacker.getName());
					}
				}
			}
		}
	}
}
