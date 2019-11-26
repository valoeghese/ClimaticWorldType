//package tk.valoeghese.climatic.compat;
//
//import com.terraformersmc.traverse.biome.TraverseBiomes;
//
//import net.minecraft.world.biome.Biomes;
//import tk.valoeghese.climatic.ClimaticWorldType;
//import tk.valoeghese.climatic.api.Climate;
//import tk.valoeghese.climatic.api.ClimateBiomes;
//import tk.valoeghese.climatic.api.OceanClimate;
//
//public final class TraverseCompat {
//	private TraverseCompat() {
//	}
//
//	public static void load() {
//		ClimaticWorldType.log.info("Climatic World Type has detected that Traverse is installed! Loading Compatibility...");
//		
//		// Arid Highland
//		ClimateBiomes.addBiome(Climate.TROPICAL_SAVANNAH, TraverseBiomes.ARID_HIGHLANDS, 10);
//
//		// Autumnal Woods
//		ClimateBiomes.addBiome(Climate.COOL_TEMPERATE, TraverseBiomes.AUTUMNAL_WOODS, 10);
//		ClimateBiomes.addBiome(Climate.WARM_TEMPERATE, TraverseBiomes.AUTUMNAL_WOODS, 6);
//
//		// Cliffs
//		ClimateBiomes.addBiome(Climate.MARITIME, TraverseBiomes.CLIFFS, 5);
//		ClimateBiomes.addBiome(Climate.BOREAL, TraverseBiomes.CLIFFS, 5);
//		ClimateBiomes.addBiome(Climate.SNOWY, TraverseBiomes.CLIFFS, 3);
//
//		// Coniferous Forest
//		ClimateBiomes.addBiome(Climate.MARITIME, TraverseBiomes.CONIFEROUS_FOREST, 10);
//		ClimateBiomes.addBiome(Climate.BOREAL, TraverseBiomes.CONIFEROUS_FOREST, 3);
//
//		// Desert Shrubland
//		ClimateBiomes.addBiome(Climate.TROPICAL_SAVANNAH, TraverseBiomes.DESERT_SHRUBLAND, 5);
//		ClimateBiomes.addBiome(Climate.TEMPERATE_DESERT, TraverseBiomes.DESERT_SHRUBLAND, 10);
//
//		// High Coniferous Forest
//		ClimateBiomes.addNeighboursForBiome(TraverseBiomes.CONIFEROUS_FOREST, TraverseBiomes.HIGH_CONIFEROUS_FOREST, 5);
//
//		// Lush Swamp
//		ClimateBiomes.addBiome(Climate.TROPICAL_RAINFOREST, TraverseBiomes.LUSH_SWAMP, 5);
//		ClimateBiomes.addNeighboursForBiome(Biomes.SWAMP, TraverseBiomes.LUSH_SWAMP, 5);
//
//		// Meadow
//		ClimateBiomes.addBiome(Climate.COOL_TEMPERATE, TraverseBiomes.MEADOW, 5);
//		ClimateBiomes.addBiome(Climate.WARM_TEMPERATE, TraverseBiomes.MEADOW, 5);
//		ClimateBiomes.addNeighboursForBiome(TraverseBiomes.MEADOW, Biomes.PLAINS, 5);
//
//		// Mini Jungle
//		ClimateBiomes.addBiome(Climate.HUMID_SUBTROPICAL, TraverseBiomes.MINI_JUNGLE, 5);
//		ClimateBiomes.addNeighboursForBiome(Biomes.JUNGLE, TraverseBiomes.MINI_JUNGLE, 5);
//
//		// Plains Plateau
//		ClimateBiomes.addBiome(Climate.TROPICAL_SAVANNAH, TraverseBiomes.PLAINS_PLATEAU, 1);
//		ClimateBiomes.addBiome(Climate.TROPICAL_STEPPE, TraverseBiomes.PLAINS_PLATEAU, 5);
//		ClimateBiomes.addBiome(Climate.WARM_TEMPERATE, TraverseBiomes.PLAINS_PLATEAU, 5);
//		ClimateBiomes.addSmallEdgeCorrection(TraverseBiomes.ROCKY_EDGE, TraverseBiomes.PLAINS_PLATEAU, TraverseBiomes.ROCKY_EDGE, TraverseBiomes.PLAINS_PLATEAU);
//
//		// Rolling Hills
//		ClimateBiomes.addBiome(Climate.COOL_TEMPERATE, TraverseBiomes.ROLLING_HILLS, 5);
//
//		// Snowy Coniferous Forest
//		ClimateBiomes.addBiome(Climate.SNOWY, TraverseBiomes.SNOWY_CONIFEROUS_FOREST, 5);
//
//		// Snowy High Coniferous Forest
//		ClimateBiomes.addNeighboursForBiome(TraverseBiomes.SNOWY_CONIFEROUS_FOREST, TraverseBiomes.SNOWY_HIGH_CONIFEROUS_FOREST, 5);
//		
//		// Wooded Island
//		ClimateBiomes.addIslandBiome(OceanClimate.LUKEWARM, TraverseBiomes.WOODED_ISLAND, 10);
//		
//		// Woodlands
//		ClimateBiomes.addBiome(Climate.TROPICAL_STEPPE, TraverseBiomes.WOODLANDS, 3);
//		ClimateBiomes.addBiome(Climate.COOL_TEMPERATE, TraverseBiomes.WOODLANDS, 3);
//		ClimateBiomes.addBiome(Climate.MEDITERRANEAN, TraverseBiomes.WOODLANDS, 10);
//	}
//}
