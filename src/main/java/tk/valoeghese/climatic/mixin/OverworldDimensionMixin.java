package tk.valoeghese.climatic.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorType;
import tk.valoeghese.climatic.ClimaticWorldType;
import tk.valoeghese.climatic.impl.type.ClimaticBiomeSource;
import tk.valoeghese.climatic.impl.type.ClimaticChunkGenerator;

@Mixin(OverworldDimension.class)
public abstract class OverworldDimensionMixin extends Dimension
{
	public OverworldDimensionMixin(World world, DimensionType type)
	{
		super(world, type);
	}
	
	@Inject(method = "createChunkGenerator", at = @At("RETURN"), cancellable = true)
	public void createChunkGenerator(CallbackInfoReturnable<ChunkGenerator<? extends ChunkGeneratorConfig>> info) {
		LevelGeneratorType type = this.world.getLevelProperties().getGeneratorType();
		ChunkGeneratorType<OverworldChunkGeneratorConfig, ClimaticChunkGenerator> chunkGenType = ClimaticWorldType.CHUNKGEN_TYPE;
		
		if(type == ClimaticWorldType.WORLDTYPE) {
			OverworldChunkGeneratorConfig settings = new OverworldChunkGeneratorConfig() {
				@Override
				public int getBiomeSize() {
					return 5;
				}
			};
			
			info.setReturnValue(
				chunkGenType.create(this.world, new ClimaticBiomeSource(world), settings)
			);
		}
	}

}
