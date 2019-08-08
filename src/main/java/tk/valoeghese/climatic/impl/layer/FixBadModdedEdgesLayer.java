package tk.valoeghese.climatic.impl.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.CrossSamplingLayer;
import net.minecraft.world.biome.layer.LayerRandomnessSource;
import tk.valoeghese.climatic.impl.ClimateBiomesImpl;

public enum FixBadModdedEdgesLayer implements CrossSamplingLayer, OceanIds {
	INSTANCE;

	@Override
	public int sample(LayerRandomnessSource rand, int border1, int border2, int border3, int border4, int centre) {
		Biome b = Registry.BIOME.get(centre);
		if (ClimateBiomesImpl.canCorrectEdges(b)) {
			return ClimateBiomesImpl.correctEdges(b, Registry.BIOME.get(border1), Registry.BIOME.get(border2), Registry.BIOME.get(border3), Registry.BIOME.get(border4), centre);
		} else {
			return centre;
		}
	}
}