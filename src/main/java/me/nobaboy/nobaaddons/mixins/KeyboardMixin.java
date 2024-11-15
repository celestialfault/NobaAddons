package me.nobaboy.nobaaddons.mixins;

import me.nobaboy.nobaaddons.features.keybinds.KeyBindListener;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
	private @Shadow @Final MinecraftClient client;

	@Inject(method = "onKey", at = @At("TAIL"))
	public void nobaaddons$onKeyPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
		if(window != client.getWindow().getHandle() || client.currentScreen != null) {
			return;
		}
		KeyBindListener.onPress(key);
	}
}