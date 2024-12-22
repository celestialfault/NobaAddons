package me.nobaboy.nobaaddons.utils

import net.minecraft.util.function.ValueLists
import java.util.function.IntFunction
import java.util.function.ToIntFunction

object EnumUtils {
	inline fun <reified T : Enum<T>> ordinalMapper(outOfBounds: ValueLists.OutOfBoundsHandling = ValueLists.OutOfBoundsHandling.WRAP): IntFunction<T> {
		return ValueLists.createIdToValueFunction(ToIntFunction<T> { it.ordinal }, enumValues<T>(), outOfBounds)
	}
}