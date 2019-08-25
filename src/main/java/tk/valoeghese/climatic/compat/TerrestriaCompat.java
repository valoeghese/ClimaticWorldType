package tk.valoeghese.climatic.compat;

import com.terraformersmc.terrestria.init.TerrestriaBiomes;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.registry.Registry;
import tk.valoeghese.climatic.ClimaticWorldType;
import tk.valoeghese.climatic.api.Climate;
import tk.valoeghese.climatic.api.ClimateBiomes;
import tk.valoeghese.climatic.api.OceanClimate;
import tk.valoeghese.climatic.impl.ClimateBiomesImpl;

public final class TerrestriaCompat {
	private TerrestriaCompat() {
	}

	public static void load() {
		ClimaticWorldType.log.info("Climatic World Type has detected that Terrestria is installed! Loading Compatibility...");

		if (TerrestriaBiomes.SAKURA_FOREST != null) {
			initTerrestria();

		} else {
			RegistryEntryAddedCallback.event(Registry.BIOME).register((rawId, id, biome) -> {
				if (id.toString().equals("terrestria:volcanic_island_beach")) {
					initTerrestria();
				}
			});
		}
	}

	private static void initTerrestria() {
		// Caldera
		ClimateBiomes.addBiome(Climate.HUMID_SUBTROPICAL, TerrestriaBiomes.CALDERA_RIDGE, 5);
		ClimateBiomes.addBiome(Climate.COOL_TEMPERATE, TerrestriaBiomes.CALDERA_RIDGE, 5);
		ClimateBiomes.addSmallEdgeCorrection(TerrestriaBiomes.CALDERA_BEACH, TerrestriaBiomes.CALDERA_RIDGE, TerrestriaBiomes.CALDERA);
		
		ClimateBiomesImpl.trackBiomeIfAbsent(TerrestriaBiomes.CALDERA);
		ClimateBiomesImpl.trackBiomeIfAbsent(TerrestriaBiomes.CALDERA_FOOTHILLS);
		
		// Cypress Forest
		ClimateBiomes.addBiome(Climate.MEDITERRANEAN, TerrestriaBiomes.CYPRESS_FOREST, 10);

		// Cypress Swamp
		ClimateBiomes.addBiome(Climate.HUMID_SUBTROPICAL, TerrestriaBiomes.CYPRESS_SWAMP, 10);

		// Dense Woodlands
		ClimateBiomes.addBiome(Climate.TROPICAL_STEPPE, TerrestriaBiomes.DENSE_WOODLANDS, 3);

		// Hemlock Rainforest
		ClimateBiomes.addBiome(Climate.COOL_TEMPERATE, TerrestriaBiomes.HEMLOCK_RAINFOREST, 10);

		// Japanese Maple Forest
		ClimateBiomes.addBiome(Climate.WARM_TEMPERATE, TerrestriaBiomes.JAPANESE_MAPLE_FOREST, 10);

		// Redwood Forest and Lush Redwood Forest
		ClimateBiomes.addBiome(Climate.MARITIME, TerrestriaBiomes.REDWOOD_FOREST, 10);
		ClimateBiomes.addNeighboursForBiome(TerrestriaBiomes.REDWOOD_FOREST, TerrestriaBiomes.LUSH_REDWOOD_FOREST, 5);

		// Rainbow Eucalyptus Rainforest
		ClimateBiomes.addBiome(Climate.TROPICAL_RAINFOREST, TerrestriaBiomes.RAINBOW_RAINFOREST, 5);
		ClimateBiomes.addNeighboursForBiome(TerrestriaBiomes.RAINBOW_RAINFOREST, TerrestriaBiomes.RAINBOW_RAINFOREST_MOUNTAINS, 5);

		// Sakura Forest
		ClimateBiomes.addBiome(Climate.MARITIME, TerrestriaBiomes.SAKURA_FOREST, 5);
		ClimateBiomes.addBiome(Climate.COOL_TEMPERATE, TerrestriaBiomes.SAKURA_FOREST, 10);

		// Snowy Hemlock Rainforest
		ClimateBiomes.addBiome(Climate.SNOWY, TerrestriaBiomes.SNOWY_HEMLOCK_FOREST, 10);

		// Volcanic Island
		ClimateBiomes.addIslandBiome(OceanClimate.WARM, TerrestriaBiomes.VOLCANIC_ISLAND_SHORE, 14);
		ClimateBiomes.addIslandBiome(OceanClimate.LUKEWARM, TerrestriaBiomes.VOLCANIC_ISLAND_SHORE, 5);
		
		ClimateBiomesImpl.trackBiomeIfAbsent(TerrestriaBiomes.VOLCANIC_ISLAND);
	}
}
