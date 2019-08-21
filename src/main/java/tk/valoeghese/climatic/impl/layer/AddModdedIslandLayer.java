package tk.valoeghese.climatic.impl.layer;

import net.minecraft.world.biome.layer.CrossSamplingLayer;
import net.minecraft.world.biome.layer.LayerRandomnessSource;
import tk.valoeghese.climatic.api.OceanClimate;
import tk.valoeghese.climatic.impl.ClimateBiomesImpl;

public enum AddModdedIslandLayer implements CrossSamplingLayer, OceanIds {
	INSTANCE;

	@Override
	public int sample(LayerRandomnessSource rand, int border1, int border2, int border3, int border4, int centre) {
		if (isDeepOcean(border1) && isDeepOcean(border2) && isDeepOcean(border3) && isDeepOcean(border4)) {
			OceanClimate climate = OceanClimate.getClimate(centre);
			return ClimateBiomesImpl.populateIsland(climate, rand, centre);
		}

		return centre;
	}

}
