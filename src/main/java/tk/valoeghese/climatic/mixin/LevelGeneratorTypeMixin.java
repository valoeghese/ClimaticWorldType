package tk.valoeghese.climatic.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.LevelGeneratorType;
import tk.valoeghese.climatic.impl.ClimaticWorldTypeHolder;

@Mixin(LevelGeneratorType.class)
public class LevelGeneratorTypeMixin {
	
	@Inject(at = @At("HEAD"), method = "getTypeFromName", cancellable = true)
	private static void getTypeFromName(String name, CallbackInfoReturnable<LevelGeneratorType> info) {
		if (name.equalsIgnoreCase("climatic")) {
			info.setReturnValue(ClimaticWorldTypeHolder.WORLDTYPE);
		}
	}
}
