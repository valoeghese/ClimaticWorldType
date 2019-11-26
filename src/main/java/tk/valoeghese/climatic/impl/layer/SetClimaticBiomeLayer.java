package tk.valoeghese.climatic.impl.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.LayerRandomnessSource;
import net.minecraft.world.biome.layer.SouthEastSamplingLayer;
import tk.valoeghese.climatic.api.Climate;
import tk.valoeghese.climatic.api.ClimateBiomes;

public enum SetClimaticBiomeLayer implements SouthEastSamplingLayer, OceanIds {
	INSTANCE;

	@Override
	public int sample(LayerRandomnessSource rand, int prev) {
		if (prev < 14 && prev != 0) {
			return Registry.BIOME.getRawId(ClimateBiomes.get(rand, Climate.fromValue(prev)));
		} else {
			return (prev == COLD_OCEAN && rand.nextInt(3) == 0) ? FROZEN_OCEAN : prev; // ocean
		}
	}


}
