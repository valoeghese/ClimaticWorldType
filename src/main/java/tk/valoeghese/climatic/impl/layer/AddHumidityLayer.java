package tk.valoeghese.climatic.impl.layer;

import net.minecraft.world.biome.layer.LayerSampleContext;
import net.minecraft.world.biome.layer.LayerSampler;
import net.minecraft.world.biome.layer.ParentedLayer;
import tk.valoeghese.climatic.ClimaticWorldType;
import tk.valoeghese.climatic.impl.ClimateBiomesImpl;
import tk.valoeghese.climatic.util.OpenSimplexNoise;

public class AddHumidityLayer implements ParentedLayer {
	
	public AddHumidityLayer(long seed) {
		noise = new OpenSimplexNoise(seed);
		scale = ClimaticWorldType.config.humidity_zone_size;
	}
	
	private final OpenSimplexNoise noise;
	private final double scale;
	
	@Override
	public int sample(LayerSampleContext<?> rand, LayerSampler sampler, int x, int z) {
		int prevValue = sampler.sample(x, z);
		double noiseSample = noise.eval((double) x / scale, (double) z / scale);
		return prevValue == 0 ? 0 : ClimateBiomesImpl.getHumidity(noiseSample);
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
