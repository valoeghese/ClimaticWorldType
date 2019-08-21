package tk.valoeghese.climatic.impl.layer;

import java.util.function.LongFunction;

import com.google.common.collect.ImmutableList;

import net.minecraft.world.biome.layer.AddBambooJungleLayer;
import net.minecraft.world.biome.layer.AddDeepOceanLayer;
import net.minecraft.world.biome.layer.AddEdgeBiomesLayer;
import net.minecraft.world.biome.layer.AddHillsLayer;
import net.minecraft.world.biome.layer.AddIslandLayer;
import net.minecraft.world.biome.layer.AddMushroomIslandLayer;
import net.minecraft.world.biome.layer.AddRiversLayer;
import net.minecraft.world.biome.layer.AddSunflowerPlainsLayer;
import net.minecraft.world.biome.layer.BiomeLayerSampler;
import net.minecraft.world.biome.layer.CachingLayerContext;
import net.minecraft.world.biome.layer.CachingLayerSampler;
import net.minecraft.world.biome.layer.CellScaleLayer;
import net.minecraft.world.biome.layer.ContinentLayer;
import net.minecraft.world.biome.layer.EaseBiomeEdgeLayer;
import net.minecraft.world.biome.layer.IncreaseEdgeCurvatureLayer;
import net.minecraft.world.biome.layer.LayerFactory;
import net.minecraft.world.biome.layer.LayerSampleContext;
import net.minecraft.world.biome.layer.LayerSampler;
import net.minecraft.world.biome.layer.NoiseToRiverLayer;
import net.minecraft.world.biome.layer.ParentedLayer;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SimpleLandNoiseLayer;
import net.minecraft.world.biome.layer.SmoothenShorelineLayer;
import net.minecraft.world.level.LevelGeneratorType;
import tk.valoeghese.climatic.ClimaticWorldType;

public class ClimaticBiomeLayers {

	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long initSeed, ParentedLayer layerToStack, LayerFactory<T> factory, int count, LongFunction<C> randomSource) {
		LayerFactory<T> returns = factory;

		for(int int_2 = 0; int_2 < count; ++int_2) {
			returns = layerToStack.create((LayerSampleContext<T>)randomSource.apply(initSeed + (long)int_2), returns);
		}

		return returns;
	}
	
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> buildContinent(LongFunction<C> random) {
		LayerFactory<T> continentFactory = ContinentLayer.INSTANCE.create(random.apply(1L));
		continentFactory = ScaleLayer.field_16198.create(random.apply(2000L), continentFactory);

		continentFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create(random.apply(1L), continentFactory);
		continentFactory = ScaleLayer.NORMAL.create(random.apply(2001L), continentFactory);

		continentFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create(random.apply(2L), continentFactory);
		continentFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create(random.apply(50L), continentFactory);
		continentFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create(random.apply(70L), continentFactory);
		continentFactory = AddIslandLayer.INSTANCE.create(random.apply(2L), continentFactory);

		continentFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create(random.apply(4L), continentFactory);
		continentFactory = ScaleLayer.NORMAL.create(random.apply(2002L), continentFactory);
		continentFactory = ScaleLayer.NORMAL.create(random.apply(2003L), continentFactory);
		
		return continentFactory;
	}
	
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> buildClimate(long seed, LayerFactory<T> mainLandFactory, LongFunction<C> random) {
		LayerFactory<T> humidityFactory = new AddHumidityLayer(seed - 11L).create(random.apply(2L), mainLandFactory);
		LayerFactory<T> temperatureFactory = new AddTemperatureLayer(seed + 11L).create(random.apply(3L), mainLandFactory);
		
		LayerFactory<T> climateFactory = ClimateLayer.INSTANCE.create(random.apply(3L), temperatureFactory, humidityFactory);

		climateFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create(random.apply(4L), climateFactory);
		climateFactory = AddMushroomIslandLayer.INSTANCE.create(random.apply(5L), climateFactory);
		climateFactory = AddDeepOceanLayer.INSTANCE.create(random.apply(4L), climateFactory);
		climateFactory = AddModdedIslandLayer.INSTANCE.create(random.apply(6L), climateFactory);
		
		return climateFactory;
	}
	
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> buildBiomes(LayerFactory<T> climateFactory, LongFunction<C> random) {
		LayerFactory<T> biomeFactory = SetClimaticBiomeLayer.INSTANCE.create(random.apply(200L), climateFactory);
		biomeFactory = ScaleLayer.NORMAL.create(random.apply(1000L), biomeFactory);
		biomeFactory = AddNeighboursLayer.INSTANCE.create(random.apply(201L), biomeFactory);
		biomeFactory = AddBambooJungleLayer.INSTANCE.create(random.apply(1001L), biomeFactory);
		biomeFactory = ScaleLayer.NORMAL.create(random.apply(1000L), biomeFactory);
		
		return biomeFactory;
	}
	
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> ImmutableList<LayerFactory<T>> build(long seed, LongFunction<C> random) {
		LayerFactory<T> mainLandFactory = buildContinent(random);
		LayerFactory<T> climateFactory = buildClimate(seed, mainLandFactory, random);
		
		// Biomes
		LayerFactory<T> initBiomeFactory = buildBiomes(climateFactory, random);

		LayerFactory<T> biomeFactory = EaseOceanEdgesLayer.INSTANCE.create(random.apply(1000L), initBiomeFactory);
		biomeFactory = EaseBiomeEdgeLayer.INSTANCE.create(random.apply(1000L), biomeFactory);
		
		LayerFactory<T> noiseFactory = SimpleLandNoiseLayer.INSTANCE.create(random.apply(100L), climateFactory);

		noiseFactory = stack(1000L, ScaleLayer.NORMAL, noiseFactory, 2, random);

		biomeFactory = AddHillsLayer.INSTANCE.create(random.apply(1000L), biomeFactory, noiseFactory);
		biomeFactory = AddSunflowerPlainsLayer.INSTANCE.create(random.apply(1001L), biomeFactory);
		
		// Config
		int biomeSize = ClimaticWorldType.config.biome_size;
		int riverSize = ClimaticWorldType.config.river_size;
		boolean largeShores = ClimaticWorldType.config.large_shores;
		
		// Rivers
		LayerFactory<T> riverFactory = stack(1000L, ScaleLayer.NORMAL, noiseFactory, riverSize, random);
		riverFactory = NoiseToRiverLayer.INSTANCE.create(random.apply(1L), riverFactory);
		
		// Size Loop
		for(int i = 0; i < biomeSize; ++i) {
			// scale biome factory
			biomeFactory = ScaleLayer.NORMAL.create(random.apply((long)(1000 + i)), biomeFactory);
			
			// shore/small edge
			if (i == 0) {
				biomeFactory = IncreaseEdgeCurvatureLayer.INSTANCE.create(random.apply(3L), biomeFactory);
			}
			
			if (largeShores || biomeSize <= 1) {
				if (i == 0) {
					biomeFactory = AddEdgeBiomesLayer.INSTANCE.create(random.apply(1000L), biomeFactory);
				}
			} else {
				if (i == 1) {
					biomeFactory = AddEdgeBiomesLayer.INSTANCE.create(random.apply(1000L), biomeFactory);
				}
			}
			
			if (i == 1 && largeShores) {
				biomeFactory = FixBadModdedEdgesLayer.INSTANCE.create(random.apply(1001L), biomeFactory);
			}
		}
		
		biomeFactory = SmoothenShorelineLayer.INSTANCE.create(random.apply(1000L), biomeFactory);
		riverFactory = SmoothenShorelineLayer.INSTANCE.create(random.apply(1000L), riverFactory);
		
		LayerFactory<T> mixBiomeFactory = AddRiversLayer.INSTANCE.create(random.apply(100L), biomeFactory, riverFactory);
		LayerFactory<T> voronoiFactory = CellScaleLayer.INSTANCE.create(random.apply(10L), mixBiomeFactory);

		return ImmutableList.of(mixBiomeFactory, voronoiFactory, mixBiomeFactory);
	}

	public static BiomeLayerSampler[] create(long seed, LevelGeneratorType worldType) {
		ImmutableList<LayerFactory<CachingLayerSampler>> immutableList_1 = build(seed, (salt) -> {
			return new CachingLayerContext(25, seed, salt);
		});
		BiomeLayerSampler noiseSampler = new BiomeLayerSampler((LayerFactory<CachingLayerSampler>)immutableList_1.get(0));
		BiomeLayerSampler biomeSampler = new BiomeLayerSampler((LayerFactory<CachingLayerSampler>)immutableList_1.get(1));
		BiomeLayerSampler unusedSampler = new BiomeLayerSampler((LayerFactory<CachingLayerSampler>)immutableList_1.get(2));
		return new BiomeLayerSampler[]{noiseSampler, biomeSampler, unusedSampler};
	}
}
