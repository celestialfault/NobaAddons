package me.nobaboy.nobaaddons.utils

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * This code was taken and modified under LGPL-2.1 license
 * @link https://github.com/hannibal002/SkyHanni
 * @author SkyHanni
 */
@JvmInline
value class Timestamp(private val millis: Long) : Comparable<Timestamp> {
	operator fun unaryMinus() = Timestamp(-millis)

	operator fun plus(duration: Duration) = Timestamp(millis + duration.inWholeMilliseconds)
	operator fun plus(milliseconds: Long) = Timestamp(millis + milliseconds)
	operator fun plus(other: Timestamp) = (millis + other.millis).milliseconds

	operator fun minus(duration: Duration) = Timestamp(millis - duration.inWholeMinutes)
	operator fun minus(milliseconds: Long) = Timestamp(millis - milliseconds)
	operator fun minus(other: Timestamp) = (millis - other.millis).milliseconds

	fun elapsedSince() = now() - this
	fun elapsedSeconds() = elapsedSince().inWholeSeconds
	fun elapsedMinutes() = elapsedSince().inWholeMinutes

	fun timeRemaining() = -elapsedSince()

	fun isPast(): Boolean = timeRemaining().isNegative()
	fun isFuture(): Boolean = timeRemaining().isPositive()
	fun isDistantPast(): Boolean = millis == 0L

	override fun compareTo(other: Timestamp): Int = millis.compareTo(other.millis)

	fun toMillis(): Long = millis

	companion object {
		fun now() = Timestamp(System.currentTimeMillis())
		fun distantPast() = Timestamp(0)
		fun distantFuture() = Timestamp(Long.MAX_VALUE)

		fun Duration.fromNow() = now() + this

		fun Long.asTimeStamp() = Timestamp(this)
	}
}