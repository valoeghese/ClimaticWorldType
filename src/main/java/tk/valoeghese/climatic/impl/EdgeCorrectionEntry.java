package tk.valoeghese.climatic.impl;

import net.minecraft.world.biome.Biome;

public final class EdgeCorrectionEntry {
	public final Biome correction;
	public final Biome[] allowedBiomes;

	public EdgeCorrectionEntry(Biome correction, Biome[] allowedBiomes) {
		this.correction = correction;
		this.allowedBiomes = allowedBiomes;
	}

	public boolean shouldGenerate(Biome... borders) {
		for (Biome c : borders) {
			boolean result = false;
			for (Biome b : allowedBiomes) {
				if (c == b) {
					result = true;
					break;
				}
			}
			if (result == false) {
				return false;
			}
		}

		return true;
	}
}
