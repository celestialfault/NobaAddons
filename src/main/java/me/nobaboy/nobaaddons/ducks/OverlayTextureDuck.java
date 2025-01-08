package me.nobaboy.nobaaddons.ducks;

import org.jetbrains.annotations.Nullable;

import java.awt.*;

public interface OverlayTextureDuck {
	// color can't be a NobaColor instance as we use Color#getAlpha() here
	void nobaaddons$setColor(@Nullable Color color);
}
