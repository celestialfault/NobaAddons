package me.nobaboy.nobaaddons.features

import kotlinx.serialization.Serializable
import me.nobaboy.nobaaddons.utils.serializers.VersionKSerializer
import net.fabricmc.loader.api.Version

// TODO make this work in a way that makes sense

@Serializable
data class DisabledFeatures(val features: Map<String, DisabledFeature>)

@Serializable
data class DisabledFeature(
	val untilVersion: @Serializable(with = VersionKSerializer::class) Version?
)
