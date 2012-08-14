package net.roguedraco.rankuponkills;

import java.util.Comparator;

public class RankComparator implements Comparator<Rank> {

	public int compare(Rank r1, Rank r2) {
		return (r1.getTotalRequiredCount()>r2.getTotalRequiredCount() ? -1 : (r1.getTotalRequiredCount()==r2.getTotalRequiredCount() ? 0 : 1));
	}

}
