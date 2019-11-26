package tk.valoeghese.climatic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorType;
import tk.valoeghese.climatic.api.Climate;
import tk.valoeghese.climatic.api.ClimateBiomes;
//import tk.valoeghese.climatic.compat.TerrestriaCompat;
//import tk.valoeghese.climatic.compat.TraverseCompat;
import tk.valoeghese.climatic.config.ClimaticConfig;
import tk.valoeghese.climatic.impl.ClimaticWorldTypeHolder;
import tk.valoeghese.climatic.impl.type.ClimaticChunkGenerator;
import tk.valoeghese.climatic.impl.type.ClimaticChunkGeneratorType;

public class ClimaticWorldType implements ModInitializer {
	
	public static ChunkGeneratorType<OverworldChunkGeneratorConfig, ClimaticChunkGenerator> CHUNKGEN_TYPE = new ClimaticChunkGeneratorType().getChunkGeneratorType(OverworldChunkGeneratorConfig::new);

	public static ClimaticConfig config = null; // if it cannot load the config we will get an NPE

	public static final Logger log = LogManager.getLogger("ClimaticWorldType");
	
	// load and initialise me on client pls
	static LevelGeneratorType loadMeOnClientPls;
	
	@Override
	public void onInitialize() {
		initConfig();
		
		loadMeOnClientPls = ClimaticWorldTypeHolder.WORLDTYPE;

		Registry.register(Registry.CHUNK_GENERATOR_TYPE, from("climatic"), CHUNKGEN_TYPE);

		boolean traverseLoaded = FabricLoader.getInstance().isModLoaded("traverse");
		boolean terrestriaLoaded = FabricLoader.getInstance().isModLoaded("terrestria");

		addDefaultBiomes(traverseLoaded, terrestriaLoaded);

//		if (traverseLoaded) {
//			TraverseCompat.load();
//		}
//		if (terrestriaLoaded) {
//			TerrestriaCompat.load();
//		}
	}

	private void initConfig() {
		Gson gson = new Gson();
		
		// first make sure config folder exists
		new File("./config/").mkdir();
		
		File loc = new File("./config/climaticworldtype.json");
		try {
			if (loc.createNewFile()) {
				log.info("Creating config for climatic world type");

				try (FileWriter writer = new FileWriter(loc)) {
					writer.write("{\n" + 
							"  \"humidity_zone_size\": 11.0,\n" + 
							"  \"temperature_zone_size\": 10.0,\n" + 
							"  \n" + 
							"  \"biome_size\": 4,\n" + 
							"  \"river_size\": 5,\n" + 
							"  \"large_shores\": true\n" + 
							"}");
				} catch (FileNotFoundException e) {
					throw new RuntimeException("Unhandled FileNotFoundException in generating config!");
				}
			}
			
			log.info("Loading climatic world type config file");
			try (FileReader reader = new FileReader(loc)) {
				config = gson.fromJson(reader, ClimaticConfig.class);
			} catch (FileNotFoundException e) {
				throw new RuntimeException("Unhandled FileNotFoundException in reading config!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unhandled IOException in config handling!");
		}
	}

	// 10 average weight; 5 average "uncommon" weight
	private void addDefaultBiomes(boolean traverseLoaded, boolean terrestriaLoaded) {
		// Boreal
		ClimateBiomes.addBiome(Climate.BOREAL, Biomes.TAIGA, 7); // 7 + 2 + 1 = 10
		ClimateBiomes.addBiome(Climate.BOREAL, Biomes.GIANT_TREE_TAIGA, 2);
		ClimateBiomes.addBiome(Climate.BOREAL, Biomes.GIANT_SPRUCE_TAIGA, 1);

		// Cool Temperate
		ClimateBiomes.addBiome(Climate.COOL_TEMPERATE, Biomes.BIRCH_FOREST, 7); // 7 + 3 = 10
		ClimateBiomes.addBiome(Climate.COOL_TEMPERATE, Biomes.TALL_BIRCH_FOREST, 3);

		// Humid Subtropical
		ClimateBiomes.addBiome(Climate.HUMID_SUBTROPICAL, Biomes.DARK_FOREST, 10);
		ClimateBiomes.addBiome(Climate.HUMID_SUBTROPICAL, Biomes.SWAMP, 10);

		// Ice Cap
		ClimateBiomes.addBiome(Climate.ICE_CAP, Biomes.SNOWY_TUNDRA, 7); // 7 + 3 = 10
		ClimateBiomes.addBiome(Climate.SNOWY, Biomes.ICE_SPIKES, 3);

		// Maritime
		ClimateBiomes.addBiome(Climate.MARITIME, Biomes.PLAINS, 10);
		ClimateBiomes.addBiome(Climate.MARITIME, Biomes.SWAMP, 10);
		ClimateBiomes.addBiome(Climate.MARITIME, Biomes.FOREST, 7); // 7 + 3 = 10
		ClimateBiomes.addBiome(Climate.MARITIME, Biomes.FLOWER_FOREST, 3);

		// Mediterranean
		ClimateBiomes.addBiome(Climate.MEDITERRANEAN, Biomes.FOREST, 10);
		ClimateBiomes.addBiome(Climate.MEDITERRANEAN, Biomes.MOUNTAINS, 6); // 6 + 3 + 1 = 10
		ClimateBiomes.addBiome(Climate.MEDITERRANEAN, Biomes.GRAVELLY_MOUNTAINS, 3);
		ClimateBiomes.addBiome(Climate.MEDITERRANEAN, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, 1);

		// Snowy
		ClimateBiomes.addBiome(Climate.SNOWY, Biomes.SNOWY_TUNDRA, 10);
		ClimateBiomes.addBiome(Climate.SNOWY, Biomes.SNOWY_TAIGA, 7); // 7 + 3 = 10
		ClimateBiomes.addBiome(Climate.SNOWY, Biomes.SNOWY_TAIGA_MOUNTAINS, 3);

		// Temperate Desert
		ClimateBiomes.addBiome(Climate.TEMPERATE_DESERT, Biomes.DESERT_LAKES, 10);
		ClimateBiomes.addBiome(Climate.TEMPERATE_DESERT, Biomes.BADLANDS_PLATEAU, 3);
		ClimateBiomes.addBiome(Climate.TEMPERATE_DESERT, Biomes.BADLANDS, 2); // 3 + 2 = 5

		// Tropical Desert
		ClimateBiomes.addBiome(Climate.TROPICAL_DESERT, Biomes.DESERT, 7); // 7 + 3 = 10
		ClimateBiomes.addBiome(Climate.TROPICAL_DESERT, Biomes.DESERT_LAKES, 3);

		// Tropical Rainforest
		ClimateBiomes.addBiome(Climate.TROPICAL_RAINFOREST, Biomes.JUNGLE, 7); // 7 + 3 = 10
		ClimateBiomes.addBiome(Climate.TROPICAL_RAINFOREST, Biomes.MODIFIED_JUNGLE, 3);
		ClimateBiomes.addBiome(Climate.TROPICAL_RAINFOREST, Biomes.BAMBOO_JUNGLE, 10);

		// Tropical Savannah
		ClimateBiomes.addBiome(Climate.TROPICAL_SAVANNAH, Biomes.SAVANNA, 7); // 7 + 3 = 10
		ClimateBiomes.addBiome(Climate.TROPICAL_SAVANNAH, Biomes.SHATTERED_SAVANNA, 3);

		// Tropical Steppe
		ClimateBiomes.addBiome(Climate.TROPICAL_STEPPE, Biomes.PLAINS, 10); // when terrestria releases steppe this will become a placeholder

		// Warm Temperate
		ClimateBiomes.addBiome(Climate.WARM_TEMPERATE, Biomes.PLAINS, 10);
		ClimateBiomes.addBiome(Climate.WARM_TEMPERATE, Biomes.FOREST, 10);
		ClimateBiomes.addBiome(Climate.WARM_TEMPERATE, Biomes.DARK_FOREST, 5);

		// Placeholders for other mods "fulfiling" the requirement
		if (!traverseLoaded) {
			ClimateBiomes.addBiome(Climate.COOL_TEMPERATE, Biomes.PLAINS, 5); // else meadow
		}

		// Neighbours
		ClimateBiomes.addNeighboursForBiome(Biomes.TAIGA, Biomes.TAIGA_MOUNTAINS, 5);
		ClimateBiomes.addNeighboursForBiome(Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_MOUNTAINS, 5);
		ClimateBiomes.addNeighboursForBiome(Biomes.ICE_SPIKES, Biomes.SNOWY_TUNDRA, 5);

		ClimateBiomes.addNeighboursForBiome(Biomes.BADLANDS, Biomes.ERODED_BADLANDS, 5);
		ClimateBiomes.addNeighboursForBiome(Biomes.BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU, 3); // 3 + 2 = 5
		ClimateBiomes.addNeighboursForBiome(Biomes.BADLANDS_PLATEAU, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, 2);

		ClimateBiomes.addNeighboursForBiome(Biomes.BAMBOO_JUNGLE, Biomes.JUNGLE, 5);

		ClimateBiomes.addNeighboursForBiome(Biomes.DESERT, Biomes.DESERT_LAKES, 3);
		ClimateBiomes.addNeighboursForBiome(Biomes.DESERT, Biomes.BADLANDS, 2);
	}

	public static final Identifier from(String id)
	{
		return new Identifier("cwt", id);
	}
}
