package tk.valoeghese.climatic.impl.type;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.feature.StructureFeature;
import tk.valoeghese.climatic.impl.ClimateBiomesImpl;
import tk.valoeghese.climatic.impl.ClimaticWorldTypeHolder;
import tk.valoeghese.climatic.impl.layer.ClimaticBiomeLayers;

public final class ClimaticBiomeSource extends BiomeSource {
	private final long worldSeed;

	private final BiomeLayerSampler biomeSampler;
	private final BiomeLayerSampler noiseSampler;

	public ClimaticBiomeSource(IWorld world) {
		worldSeed = world.getSeed();

		BiomeLayerSampler[] biomeLayerSamplers_1 = ClimaticBiomeLayers.create(world.getSeed(), ClimaticWorldTypeHolder.WORLDTYPE);

		this.noiseSampler = biomeLayerSamplers_1[0];
		this.biomeSampler = biomeLayerSamplers_1[1];
	}

	public long getWorldSeed() {
		return worldSeed;
	}

	@Override
	public Biome getBiome(int x, int z) {
		return biomeSampler.sample(x, z);
	}

	@Override
	public Biome getBiomeForNoiseGen(int x, int z) {
		return noiseSampler.sample(x, z);
	}

	@Override
	public Biome[] sampleBiomes(int x, int z, int xSize, int zSize, boolean var5) {
		return biomeSampler.sample(x, z, xSize, zSize);
	}

	@Override
	public Set<Biome> getBiomesInArea(int x, int z, int range)
	{
		int int_4 = x - range >> 2;;
		int int_5 = z - range >> 2;
		int int_6 = x + range >> 2;
		int int_7 = z + range >> 2;
		int int_8 = int_6 - int_4 + 1;
		int int_9 = int_7 - int_5 + 1;

		return Sets.newHashSet(noiseSampler.sample(int_4, int_5, int_8, int_9));
	}

	@Override
	public BlockPos locateBiome(int x, int z, int range, List<Biome> biomes, Random random) {
		int i = x - range >> 2;
		int j = z - range >> 2;
		int k = x + range >> 2;
		int l = z + range >> 2;
		int i1 = k - i + 1;
		int j1 = l - j + 1;

		BlockPos blockpos = null;
		int k1 = 0;

		Biome[] biomesInArea = this.noiseSampler.sample(i, j, i1, j1);

		for (int l1 = 0; l1 < i1 * j1; ++l1) {
			int i2 = i + l1 % i1 << 2;
			int j2 = j + l1 / i1 << 2;
			Biome biome = biomesInArea[l1];

			if (biomes.contains(biome) && (blockpos == null || random.nextInt(k1 + 1) == 0)) {
				blockpos = new BlockPos(i2, 0, j2);
				++k1;
			}
		}

		return blockpos;
	}

	@Override
	public boolean hasStructureFeature(StructureFeature<?> feature) {
		return this.structureFeatures.computeIfAbsent(feature, (structureFeature_1x) -> {
			Biome[] var2 = ClimateBiomesImpl.biomes();
			int var3 = var2.length;

			for(int var4 = 0; var4 < var3; ++var4) {
				Biome biome_1 = var2[var4];
				if (biome_1.hasStructureFeature(structureFeature_1x)) {
					return true;
				}
			}

			return false;
		});
	}

	@Override
	public Set<BlockState> getTopMaterials() {
		if (this.topMaterials.isEmpty()) {
			Biome[] var1 = ClimateBiomesImpl.biomes();
			int var2 = var1.length;

			for(int var3 = 0; var3 < var2; ++var3) {
				Biome biome = var1[var3];
				if (biome != null) {
					this.topMaterials.add(biome.getSurfaceConfig().getTopMaterial());
				}
			}
		}

		return this.topMaterials;
	}
}
