package tk.valoeghese.climatic.impl;

import net.minecraft.world.biome.Biome;

public final class IslandEntry {
	
	public final Biome island;
	public final int chance;
	
	public IslandEntry(Biome island, int chance) {
		this.island = island;
		this.chance = chance;
	}

}
