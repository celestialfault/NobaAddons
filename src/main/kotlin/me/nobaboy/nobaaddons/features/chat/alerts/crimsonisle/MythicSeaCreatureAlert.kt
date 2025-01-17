package me.nobaboy.nobaaddons.features.chat.alerts.crimsonisle

import me.nobaboy.nobaaddons.api.skyblock.SkyBlockAPI
import me.nobaboy.nobaaddons.api.skyblock.SkyBlockAPI.inIsland
import me.nobaboy.nobaaddons.core.SkyBlockIsland
import me.nobaboy.nobaaddons.core.fishing.SeaCreature
import me.nobaboy.nobaaddons.features.chat.alerts.ChatAlert
import me.nobaboy.nobaaddons.utils.LocationUtils
import me.nobaboy.nobaaddons.utils.StringUtils
import me.nobaboy.nobaaddons.utils.chat.ChatUtils
import me.nobaboy.nobaaddons.utils.tr

object MythicSeaCreatureAlert : ChatAlert(
	id = "mythicSeaCreature",
	name = tr("nobaaddons.feature.chat.alerts.mythicSeaCreatureAlert", "Mythic Sea Creature"),
) {
	override fun processMessage(message: String) {
		if(!SkyBlockIsland.CRIMSON_ISLE.inIsland()) return

		val seaCreature = SeaCreature.getBySpawnMessage(message) ?: return
		if(seaCreature.id != "THUNDER" && seaCreature.id != "LORD_JAWBUS") return

		val location = LocationUtils.playerCoords()
		val randomString = StringUtils.randomAlphanumeric()
		ChatUtils.sendChatAsPlayer("$location | ${seaCreature.displayName} at [ ${SkyBlockAPI.prefixedZone} ] @$randomString")
	}
}
