package tk.valoeghese.climatic.api;

import tk.valoeghese.climatic.impl.layer.OceanIds;

public enum OceanClimate implements OceanIds {
	WARM(WARM_OCEAN, DEEP_WARM_OCEAN),
	LUKEWARM(LUKEWARM_OCEAN, DEEP_LUKEWARM_OCEAN),
	COLD(COLD_OCEAN, DEEP_COLD_OCEAN),
	/**
	* Note: at the time of adding modded islands, NONE is not a climate, so is treated as "all"
	*/
	NONE(OCEAN, DEEP_OCEAN);
	
	private OceanClimate(int biomeId, int deepBiomeId) {
		normal = biomeId;
		deep = deepBiomeId;
	}
	
	public static OceanClimate getClimate(int biomeId) {
		if (biomeId == WARM_OCEAN || biomeId == DEEP_WARM_OCEAN) {
			return WARM;
		} else if (biomeId == LUKEWARM_OCEAN || biomeId == DEEP_LUKEWARM_OCEAN) {
			return LUKEWARM;
		} else if (biomeId == COLD_OCEAN || biomeId == DEEP_COLD_OCEAN) {
			return COLD;
		} else {
			return NONE;
		}
	}
	
	private final int normal;
	private final int deep;
	
	public int getOcean() {
		return normal;
	}
	
	public int getDeepOcean() {
		return deep;
	}
}