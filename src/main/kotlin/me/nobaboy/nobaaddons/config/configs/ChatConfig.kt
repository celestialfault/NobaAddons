package me.nobaboy.nobaaddons.config.configs

import dev.isxander.yacl3.config.v2.api.SerialEntry
import me.nobaboy.nobaaddons.features.chat.filter.ChatFilterOption

class ChatConfig {
	@SerialEntry
	val filter: Filter = Filter()

	@SerialEntry
	val alerts: Alerts = Alerts()

	class Filter {
		@SerialEntry
		var hideTipMessages: Boolean = false

		@SerialEntry
		var hideProfileInfo: Boolean = false

		@SerialEntry
		var blessingMessage: ChatFilterOption = ChatFilterOption.SHOWN

		@SerialEntry
		var healerOrbMessage: ChatFilterOption = ChatFilterOption.SHOWN

		@SerialEntry
		var pickupObtainMessage: Boolean = false

		@SerialEntry
		var allow5050ItemMessage: Boolean = false
	}

	class Alerts {
		@SerialEntry
		var mythicSeaCreatureSpawn: Boolean = false

		@SerialEntry
		var vanquisherSpawn: Boolean = false
	}
}