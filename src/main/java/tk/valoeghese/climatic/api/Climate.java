package tk.valoeghese.climatic.api;

import tk.valoeghese.climatic.impl.ClimateBiomesImpl;

public enum Climate {
	
	TROPICAL_DESERT(1),
	TROPICAL_SAVANNAH(2),
	TROPICAL_STEPPE(3),
	TROPICAL_RAINFOREST(4),
	
	TEMPERATE_DESERT(5),
	WARM_TEMPERATE(6),
	MARITIME(7),
	MEDITERRANEAN(8),
	HUMID_SUBTROPICAL(9),
	
	COOL_TEMPERATE(10),
	BOREAL(11),
	SNOWY(12),
	ICE_CAP(13);
	
	private Climate(int value) {
		this.value = value;
		ClimateBiomesImpl.setInLookup(value, this);
	}
	
	private final int value;
	
	public int value() {
		return value;
	}

	public static Climate fromValue(int climate) {
		return ClimateBiomesImpl.lookup(climate);
	}
}
