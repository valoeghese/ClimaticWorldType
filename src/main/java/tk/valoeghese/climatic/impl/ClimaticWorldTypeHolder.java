package tk.valoeghese.climatic.impl;

import net.minecraft.world.level.LevelGeneratorType;
import tk.valoeghese.climatic.impl.type.WorldtypeProvider;

public class ClimaticWorldTypeHolder {
	public static LevelGeneratorType WORLDTYPE = WorldtypeProvider.getWorldType();
}
