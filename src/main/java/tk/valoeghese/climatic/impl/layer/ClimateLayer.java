package tk.valoeghese.climatic.impl.layer;

import static tk.valoeghese.climatic.api.Climate.BOREAL;
import static tk.valoeghese.climatic.api.Climate.COOL_TEMPERATE;
import static tk.valoeghese.climatic.api.Climate.HUMID_SUBTROPICAL;
import static tk.valoeghese.climatic.api.Climate.ICE_CAP;
import static tk.valoeghese.climatic.api.Climate.MEDITERRANEAN;
import static tk.valoeghese.climatic.api.Climate.SNOWY;
import static tk.valoeghese.climatic.api.Climate.TEMPERATE_DESERT;
import static tk.valoeghese.climatic.api.Climate.TROPICAL_DESERT;
import static tk.valoeghese.climatic.api.Climate.TROPICAL_RAINFOREST;
import static tk.valoeghese.climatic.api.Climate.TROPICAL_SAVANNAH;
import static tk.valoeghese.climatic.api.Climate.TROPICAL_STEPPE;
import static tk.valoeghese.climatic.api.Climate.WARM_TEMPERATE;
import static tk.valoeghese.climatic.api.Climate.MARITIME;

import net.minecraft.world.biome.layer.LayerRandomnessSource;
import net.minecraft.world.biome.layer.LayerSampler;
import net.minecraft.world.biome.layer.MergingLayer;
import net.minecraft.world.biome.layer.NorthWestCoordinateTransformer;
import tk.valoeghese.climatic.api.Climate;

public enum ClimateLayer implements MergingLayer, NorthWestCoordinateTransformer, OceanIds {

	INSTANCE;

	@Override
	public int sample(LayerRandomnessSource rand, LayerSampler temperature, LayerSampler humidity, int x, int z) {
		int temp = temperature.sample(x, z); // temperature is actually inverse to usual (0 = hot, 7 = cold)
		int humid = humidity.sample(x, z); // 0 = ocean

		if (humid == 0) {
			if (temp < 2) {
				return WARM_OCEAN;
			} else if (temp < 6) {
				return LUKEWARM_OCEAN;
			} else {
				return COLD_OCEAN;
			}
		} else {
			int result = CLIMATE_MAPPINGS[temp][humid - 1].value();
			return (result == WARM_TEMPERATE.value() || result == MEDITERRANEAN.value()) ? addOceanic(rand, humidity, result, x, z) : result;
		}
	}

	private int addOceanic(LayerRandomnessSource rand, LayerSampler humidity, int result, int x, int z) {
		if (humidity.sample(x + 1, z) == 0 || humidity.sample(x - 1, z) == 0 || humidity.sample(x, z + 1) == 0 || humidity.sample(x, z - 1) == 0) {
			return rand.nextInt(3) == 0 ? MARITIME.value() : result;
		} else {
			return result;
		}
	}

	private static final Climate[][] CLIMATE_MAPPINGS = new Climate[][]
			{
		{TROPICAL_DESERT,   TROPICAL_DESERT,   TROPICAL_SAVANNAH, TROPICAL_SAVANNAH, TROPICAL_SAVANNAH, TROPICAL_STEPPE, TROPICAL_RAINFOREST, TROPICAL_RAINFOREST},
		{TROPICAL_SAVANNAH, TROPICAL_SAVANNAH, TROPICAL_SAVANNAH, TROPICAL_SAVANNAH, TROPICAL_SAVANNAH, TROPICAL_STEPPE, HUMID_SUBTROPICAL,   TROPICAL_RAINFOREST},
		{TEMPERATE_DESERT,  MEDITERRANEAN,     TROPICAL_STEPPE,   TROPICAL_STEPPE,   TROPICAL_STEPPE,   WARM_TEMPERATE,  HUMID_SUBTROPICAL,   HUMID_SUBTROPICAL},
		{MEDITERRANEAN,     MEDITERRANEAN,     WARM_TEMPERATE,    WARM_TEMPERATE,    WARM_TEMPERATE,    WARM_TEMPERATE,  WARM_TEMPERATE,      WARM_TEMPERATE},
		{MEDITERRANEAN,     MEDITERRANEAN,     COOL_TEMPERATE,    COOL_TEMPERATE,    WARM_TEMPERATE,    COOL_TEMPERATE,  COOL_TEMPERATE,      COOL_TEMPERATE},
		{BOREAL,            BOREAL,            BOREAL,            COOL_TEMPERATE,    COOL_TEMPERATE,    COOL_TEMPERATE,  COOL_TEMPERATE,      COOL_TEMPERATE},
		{BOREAL,            BOREAL,            BOREAL,            BOREAL,            BOREAL,            SNOWY,           SNOWY,               SNOWY},
		{ICE_CAP,           ICE_CAP,           ICE_CAP,           ICE_CAP,           ICE_CAP,           ICE_CAP,         SNOWY,               SNOWY}
			};

}
