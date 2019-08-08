package tk.valoeghese.climatic.impl.layer;

import net.minecraft.world.biome.layer.LayerSampleContext;
import net.minecraft.world.biome.layer.LayerSampler;
import net.minecraft.world.biome.layer.ParentedLayer;
import tk.valoeghese.climatic.ClimaticWorldType;
import tk.valoeghese.climatic.impl.ClimateBiomesImpl;
import tk.valoeghese.climatic.util.OpenSimplexNoise;

public class AddTemperatureLayer implements ParentedLayer {
	
	public AddTemperatureLayer(long seed) {
		noise = new OpenSimplexNoise(seed);
		scale = ClimaticWorldType.config.temperature_zone_size;
	}
	
	private final OpenSimplexNoise noise;
	private final double scale;
	
	@Override
	public int sample(LayerSampleContext<?> rand, LayerSampler sampler, int x, int z) {
		double noiseSample = noise.eval((double) x / scale, (double) z / scale);
		return ClimateBiomesImpl.getTemperature(noiseSample);
	}

	@Override
	public int transformX(int x) {
		return x;
	}

	@Override
	public int transformZ(int z) {
		return z;
	}
}
