package me.nobaboy.nobaaddons.config

import com.google.gson.JsonObject
import me.nobaboy.nobaaddons.NobaAddons
import me.nobaboy.nobaaddons.config.NobaConfigUtils.renameToBackup
import me.nobaboy.nobaaddons.features.Feature
import me.nobaboy.nobaaddons.utils.ErrorManager
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

object FeatureConfig {
	val FILE: Path = NobaAddons.CONFIG_DIR.resolve("features.json")

	init {
		ClientLifecycleEvents.CLIENT_STOPPING.register { save() }
	}

	private val gson by NobaAddons::GSON
	lateinit var json: JsonObject
		private set

	fun load() {
		if(!FILE.exists()) return

		json = try {
			gson.fromJson(FILE.readText(), JsonObject::class.java)
		} catch(ex: Exception) {
			ErrorManager.logError("Failed to load feature config", ex)
			FILE.renameToBackup()
			return
		}
	}

	fun save() {
		val json = JsonObject()
		for((id, feature) in Feature.features()) {
			json.add(id, feature.save())
		}
		FILE.writeText(gson.toJson(json))
	}
}