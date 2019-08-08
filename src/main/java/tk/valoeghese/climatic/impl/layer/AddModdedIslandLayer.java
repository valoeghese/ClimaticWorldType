package tk.valoeghese.climatic.impl.layer;

import net.minecraft.world.biome.layer.CrossSamplingLayer;
import net.minecraft.world.biome.layer.LayerRandomnessSource;
import tk.valoeghese.climatic.impl.ClimateBiomesImpl;

public enum AddModdedIslandLayer implements CrossSamplingLayer, OceanIds {
	INSTANCE;

	@Override
	public int sample(LayerRandomnessSource rand, int border1, int border2, int border3, int border4, int centre) {
		if (isOcean(border1) && isOcean(border2) && isOcean(border3) && isOcean(border4)) {
			return ClimateBiomesImpl.populateIsland(rand, centre);
		}

		return centre;
	}

}
