package me.nobaboy.nobaaddons.features.chat.alerts

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import me.nobaboy.nobaaddons.config.NobaConfigUtils.boolean
import me.nobaboy.nobaaddons.config.NobaConfigUtils.requires
import me.nobaboy.nobaaddons.events.impl.chat.ChatMessageEvents
import me.nobaboy.nobaaddons.features.Feature
import me.nobaboy.nobaaddons.features.FeatureCategory
import me.nobaboy.nobaaddons.features.chat.alerts.crimsonisle.MythicSeaCreatureAlert
import me.nobaboy.nobaaddons.features.chat.alerts.crimsonisle.VanquisherAlert
import me.nobaboy.nobaaddons.utils.ErrorManager
import me.nobaboy.nobaaddons.utils.StringUtils.cleanFormatting
import me.nobaboy.nobaaddons.utils.tr

object ChatAlerts : Feature(
	id = "chatAlerts",
	name = tr("nobaaddons.feature.chatAlerts", "Chat Alerts"),
	category = FeatureCategory.CHAT,
	enabledByDefault = true,
) {
	private val alerts = arrayOf<ChatAlert>(
		MythicSeaCreatureAlert,
		VanquisherAlert,
	)

	override val configBuilder = config { enabled ->
		alerts.forEach {
			boolean(it.name, it.description, false, it::enabled) requires enabled
		}
	}

	override fun initFeature() {
		ChatMessageEvents.CHAT.register(this::onChatMessage)
	}

	private fun onChatMessage(event: ChatMessageEvents.Chat) {
		if(!enabled) return

		val string = event.message.string.cleanFormatting()
		for(alert in alerts) {
			if(!alert.enabled) continue
			try {
				alert.processMessage(string)
			} catch(ex: Throwable) {
				ErrorManager.logError("${alert::class.simpleName} threw an error while processing a chat message", ex)
			}
		}
	}

	override fun load(data: JsonObject) {
		for(alert in alerts) {
			alert.enabled = data.get(alert.id)
				?.let { it as? JsonPrimitive }
				?.takeIf { it.isBoolean }
				?.asBoolean ?: continue
		}
	}

	override fun save(): JsonObject = JsonObject().apply {
		for(alert in alerts) {
			addProperty(alert.id, alert.enabled)
		}
	}
}