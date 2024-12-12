package me.nobaboy.nobaaddons.events

import me.nobaboy.nobaaddons.events.internal.CancelableEvent
import me.nobaboy.nobaaddons.events.internal.CancelableEventDispatcher
import me.nobaboy.nobaaddons.events.internal.EventDispatcher
import me.nobaboy.nobaaddons.utils.NobaVec
import net.minecraft.sound.SoundCategory
import net.minecraft.util.Identifier

object SoundEvents {
	/**
	 * Event invoked to determine whether a given sound should be allowed to play.
	 */
	@JvmField val ALLOW_SOUND = CancelableEventDispatcher<AllowSound>()

	/**
	 * Event invoked after a sound is canceled.
	 */
	@JvmField val SOUND_CANCELED = EventDispatcher<Sound>()

	/**
	 * Event invoked after a sound is played.
	 */
	@JvmField val SOUND = EventDispatcher<Sound>()

	data class AllowSound(val id: Identifier, val location: NobaVec, val pitch: Float, val volume: Float) : CancelableEvent()
	data class Sound(val id: Identifier, val category: SoundCategory, val location: NobaVec, val pitch: Float, val volume: Float)
}