package tk.valoeghese.climatic.impl.type;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;

public class ClimaticChunkGenerator extends OverworldChunkGenerator {

	public ClimaticChunkGenerator(IWorld iWorld_1, BiomeSource biomeSource_1, OverworldChunkGeneratorConfig overworldChunkGeneratorConfig_1) {
		super(iWorld_1, biomeSource_1, overworldChunkGeneratorConfig_1);
	}
}