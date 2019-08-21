package tk.valoeghese.climatic.api;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.LayerRandomnessSource;
import tk.valoeghese.climatic.ClimaticWorldType;
import tk.valoeghese.climatic.impl.ClimateBiomesImpl;

public final class ClimateBiomes {
	private ClimateBiomes() {
	}

	public static Climate lookup(int id) {
		return ClimateBiomesImpl.lookup(id);
	}

	public static void addBiome(Climate climate, Biome biome, int weight) {
		if (biome != null) {
			ClimateBiomesImpl.addBiomeEntry(climate, biome, weight);
		} else {
			ClimaticWorldType.log.warn("A null biome was attempted to be added!");
		}
	}

	public static Biome get(LayerRandomnessSource rand, Climate climate) {
		return ClimateBiomesImpl.get(rand, climate);
	}
	
	public static void addNeighboursForBiome(Biome base, Biome neighbour, int weight) {
		if (neighbour != null) {
			ClimateBiomesImpl.addNeighbours(base, neighbour, weight);
		} else {
			ClimaticWorldType.log.warn("A null biome was attempted to be added!");
		}
	}
	
	/**
	 * @param island biome to be added as an island
	 * @param chance chance out of 100 to be set
	 */
	public static void addIslandBiome(Biome island, int chance) {
		addIslandBiome(OceanClimate.NONE, island, chance);
	}
	
	/**
	 * @param island biome to be added as an island
	 * @param chance chance out of 100 to be set
	 */
	public static void addIslandBiome(OceanClimate climate, Biome island, int chance) {
		if (island != null) {
			if (climate == OceanClimate.NONE) {
				// Add to all climates, including NONE
				ClimateBiomesImpl.addIsland(OceanClimate.WARM, island, chance);
				ClimateBiomesImpl.addIsland(OceanClimate.LUKEWARM, island, chance);
				ClimateBiomesImpl.addIsland(OceanClimate.COLD, island, chance);
			}
			
			ClimateBiomesImpl.addIsland(climate, island, chance);
		} else {
			ClimaticWorldType.log.warn("A null biome was attempted to be added!");
		}
	}
	
	/**
	 * @param smallEdge the edge to be replaced/made smaller if large shores are turned on
	 * @param correction the biome to be used as the replacement
	 * @param allowedSurroundings biomes that do not obstruct the replacement of the edge. It is recommended to put the edge and main biome here.
	 */
	public static void addSmallEdgeCorrection(Biome smallEdge, Biome correction, Biome...allowedSurroundings) {
		ClimateBiomesImpl.addSmallEdgeCorrection(smallEdge, correction, allowedSurroundings);
	}
}
