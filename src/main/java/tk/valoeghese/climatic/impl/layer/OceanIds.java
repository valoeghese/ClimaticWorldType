package tk.valoeghese.climatic.impl.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;

public interface OceanIds {
	int OCEAN = Registry.BIOME.getRawId(Biomes.OCEAN);
	int WARM_OCEAN = Registry.BIOME.getRawId(Biomes.WARM_OCEAN);
	int LUKEWARM_OCEAN = Registry.BIOME.getRawId(Biomes.LUKEWARM_OCEAN);
	int COLD_OCEAN = Registry.BIOME.getRawId(Biomes.COLD_OCEAN);
	int FROZEN_OCEAN = Registry.BIOME.getRawId(Biomes.FROZEN_OCEAN);
	int DEEP_WARM_OCEAN = Registry.BIOME.getRawId(Biomes.DEEP_WARM_OCEAN);
	int DEEP_LUKEWARM_OCEAN = Registry.BIOME.getRawId(Biomes.DEEP_LUKEWARM_OCEAN);
	int DEEP_OCEAN = Registry.BIOME.getRawId(Biomes.DEEP_OCEAN);
	int DEEP_COLD_OCEAN = Registry.BIOME.getRawId(Biomes.DEEP_COLD_OCEAN);
	int DEEP_FROZEN_OCEAN = Registry.BIOME.getRawId(Biomes.DEEP_FROZEN_OCEAN);

	default public boolean isOcean(int int_1) {
		return int_1 == WARM_OCEAN || int_1 == LUKEWARM_OCEAN || int_1 == OCEAN || int_1 == COLD_OCEAN || int_1 == FROZEN_OCEAN || int_1 == DEEP_WARM_OCEAN || int_1 == DEEP_LUKEWARM_OCEAN || int_1 == DEEP_OCEAN || int_1 == DEEP_COLD_OCEAN || int_1 == DEEP_FROZEN_OCEAN;
	}
	
	default public boolean isShallowOcean(int int_1) {
		return int_1 == WARM_OCEAN || int_1 == LUKEWARM_OCEAN || int_1 == OCEAN || int_1 == COLD_OCEAN || int_1 == FROZEN_OCEAN;
	}
	
	default public boolean isDeepOcean(int int_1) {
		return int_1 == DEEP_WARM_OCEAN || int_1 == DEEP_LUKEWARM_OCEAN || int_1 == DEEP_OCEAN || int_1 == DEEP_COLD_OCEAN || int_1 == DEEP_FROZEN_OCEAN;
	}
}
