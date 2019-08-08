package tk.valoeghese.climatic.impl.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.layer.CrossSamplingLayer;
import net.minecraft.world.biome.layer.LayerRandomnessSource;

public enum EaseOceanEdgesLayer implements CrossSamplingLayer, OceanIds {
	INSTANCE;

	private static final int SAVANNA_ID = Registry.BIOME.getRawId(Biomes.SAVANNA);

	@Override
	public int sample(LayerRandomnessSource rand, int border1, int border2, int border3, int border4, int centre) {
		if (isOcean(centre)) {
			if (!isOcean(border1) || !isOcean(border2) || !isOcean(border3) || !isOcean(border4)) {
				if (isShallowOcean(centre)) {
					return OCEAN;
				} else {
					return rand.nextInt(3) == 0 ? OCEAN : DEEP_OCEAN;
				}
			}
			} else if (isOcean(border1) || isOcean(border2) || isOcean(border3) || isOcean(border4)) {
				if (Registry.BIOME.get(centre).getCategory() == Biome.Category.DESERT) {
					return SAVANNA_ID;
				}
			}

			return centre;
		}

	}
