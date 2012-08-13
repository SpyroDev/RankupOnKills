package net.roguedraco.rankuponkills.commands;

import net.roguedraco.lang.Lang;
import net.roguedraco.player.RDPlayer;
import net.roguedraco.player.RDPlayers;
import net.roguedraco.rankuponkills.Rank;
import net.roguedraco.rankuponkills.Ranks;
import net.roguedraco.rankuponkills.RankupOnKillsPlugin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;

public class GeneralCommands {
	@Command(aliases = { "rankstat" }, usage = "", flags = "", desc = "Shows rank statistics.", help = "Shows how far away you are from your next rank.", min = 0, max = 1)
	@CommandPermissions("rankuponkills.check")
	public static void rankstat(CommandContext args, CommandSender sender)
			throws CommandException {
		RDPlayer rdp = RDPlayers.getPlayer(sender.getName());		
		if(args.argsLength() > 0) {
			if(RankupOnKillsPlugin.permission.playerHas((Player) sender, "rankuponkills.checkOther")) {
				rdp = RDPlayers.getPlayer(args.getString(0));
			}
		}
		runRankStat((Player)sender,rdp);
		
	}
	
	public static void runRankStat(Player player, RDPlayer target) {
		Rank nextRank = Ranks.getNextRank(target);
		int mobCount = target.getInt("count.mob");
		int pvpCount = target.getInt("count.player");

		int mobRemainder = nextRank.getRequiredMobCount() - mobCount;
		int pvpRemainder = nextRank.getRequiredPlayerCount() - pvpCount;

		if (mobRemainder < 0) {
			mobRemainder = 0;
		}
		if (pvpRemainder < 0) {
			pvpRemainder = 0;
		}

		if (nextRank.isSharedCount()) {
			if (nextRank.getTotalRequiredCount() > (mobCount + pvpCount)) {
				player.sendMessage(Lang.get("rankstat.shared").replaceAll("%M",""+mobRemainder).replaceAll("%P",""+pvpRemainder).replaceAll("%S", ""+(mobRemainder+pvpRemainder)).replaceAll("%R", nextRank.getName()));
			} else {
				player.sendMessage(Lang.get("rankstat.highestRank").replaceAll("%M",""+mobRemainder).replaceAll("%P",""+pvpRemainder).replaceAll("%R", nextRank.getName()));
			}
		} else if (nextRank.isRequireBoth()) {
			if (nextRank.getRequiredMobCount() > mobCount) {
				if (nextRank.getRequiredPlayerCount() > pvpCount) {
					player.sendMessage(Lang.get("rankstat.playerAndMob").replaceAll("%M",""+mobRemainder).replaceAll("%P",""+pvpRemainder).replaceAll("%R", nextRank.getName()));
				} else {
					player.sendMessage(Lang.get("rankstat.mobOnly").replaceAll("%M",""+mobRemainder).replaceAll("%P",""+pvpRemainder).replaceAll("%R", nextRank.getName()));
				}
			} else {
				if (nextRank.getRequiredPlayerCount() > pvpCount) {
					player.sendMessage(Lang.get("rankstat.playerOnly").replaceAll("%M",""+mobRemainder).replaceAll("%P",""+pvpRemainder).replaceAll("%R", nextRank.getName()));
				} else {
					player.sendMessage(Lang.get("rankstat.highestRank").replaceAll("%M",""+mobRemainder).replaceAll("%P",""+pvpRemainder).replaceAll("%R", nextRank.getName()));
				}
			}
		} else {
			if (nextRank.getRequiredMobCount() > mobCount) {
				if (nextRank.getRequiredPlayerCount() > pvpCount) {
					player.sendMessage(Lang.get("rankstat.playerOrMob").replaceAll("%M",""+mobRemainder).replaceAll("%P",""+pvpRemainder).replaceAll("%R", nextRank.getName()));
				} else {
					player.sendMessage(Lang.get("rankstat.mobOnly").replaceAll("%M",""+mobRemainder).replaceAll("%P",""+pvpRemainder).replaceAll("%R", nextRank.getName()));
				}
			} else {
				if (nextRank.getRequiredPlayerCount() > pvpCount) {
					player.sendMessage(Lang.get("rankstat.playerOnly").replaceAll("%M",""+mobRemainder).replaceAll("%P",""+pvpRemainder).replaceAll("%R", nextRank.getName()));
				} else {
					player.sendMessage(Lang.get("rankstat.highestRank").replaceAll("%M",""+mobRemainder).replaceAll("%P",""+pvpRemainder).replaceAll("%R", nextRank.getName()));
				}
			}
		}
	}
}
