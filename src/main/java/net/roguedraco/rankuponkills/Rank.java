package net.roguedraco.rankuponkills;

import net.roguedraco.player.RDPlayer;

public class Rank {

	public String name;
	public boolean sharedCount = false;
	public boolean requireBoth = false;
	public int requiredPlayerCount;
	public int requiredMobCount;
	public int totalRequiredCount;
	public String next;

	public Rank(String name, boolean sharedCount, boolean requireBoth, int requiredPlayerCount,
			int requiredMobCount, int totalRequiredCount) {
		this.name = name;
		this.sharedCount = sharedCount;
		this.requireBoth = requireBoth;
		this.requiredPlayerCount = requiredPlayerCount;
		this.requiredMobCount = requiredMobCount;
		this.totalRequiredCount = totalRequiredCount;
	}
	
	public Rank(String name, boolean sharedCount, boolean requireBoth, int requiredPlayerCount,
			int requiredMobCount, int totalRequiredCount, String next) {
		this.name = name;
		this.sharedCount = sharedCount;
		this.requireBoth = requireBoth;
		this.requiredPlayerCount = requiredPlayerCount;
		this.requiredMobCount = requiredMobCount;
		this.totalRequiredCount = totalRequiredCount;
		this.next = next;
	}

	public boolean isEligable(int mobCount, int playerCount) {
		if (sharedCount == true) {
			if ((mobCount + playerCount) >= totalRequiredCount) {
				return true;
			}
		} else {
			if (requireBoth) {
				if(mobCount >= requiredMobCount && playerCount >= requiredPlayerCount) {
					return true;
				}
			} else {
				if (mobCount >= requiredMobCount) {
					return true;
				}
				if (playerCount >= requiredPlayerCount) {
					return true;
				}
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public int getRequiredPlayerCount() {
		return requiredPlayerCount;
	}

	public int getRequiredMobCount() {
		return requiredMobCount;
	}

	public int getTotalRequiredCount() {
		return totalRequiredCount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRequiredPlayerCount(int requiredPlayerCount) {
		this.requiredPlayerCount = requiredPlayerCount;
	}

	public void setRequiredMobCount(int requiredMobCount) {
		this.requiredMobCount = requiredMobCount;
	}

	public boolean isSharedCount() {
		return sharedCount;
	}

	public boolean isRequireBoth() {
		return requireBoth;
	}
	
	public String getNext(RDPlayer rdp) {
		if(next != "" && next != null) {
			return next;
		}
		else {
			Rank rank = Ranks.getNextRankAlt(rdp);
			return rank.getName();
		}
	}
}
