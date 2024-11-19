package me.nobaboy.nobaaddons.features.visuals.itemoverlays.slotinfo.impl

import me.nobaboy.nobaaddons.events.ScreenRenderEvents
import me.nobaboy.nobaaddons.features.visuals.itemoverlays.slotinfo.ISlotInfo
import me.nobaboy.nobaaddons.utils.items.ItemUtils.getSkyBlockItem

object KuudraKeyInfoSlot : ISlotInfo {
	override val enabled: Boolean get() = config.kuudraKey

	override fun handle(event: ScreenRenderEvents.DrawSlot) {
		val item = event.itemStack.getSkyBlockItem() ?: return
		if(!item.id.startsWith("KUUDRA") || !item.id.endsWith("KEY")) return

		val tier = getKeyTier(item.id)
		drawCount(event, tier)
	}

	private fun getKeyTier(id: String): String {
		return when(id) {
			"KUUDRA_TIER_KEY" -> "1"
			"KUUDRA_HOT_TIER_KEY" -> "2"
			"KUUDRA_BURNING_TIER_KEY" -> "3"
			"KUUDRA_FIERY_TIER_KEY" -> "4"
			"KUUDRA_INFERNAL_TIER_KEY" -> "5"
			else -> "?"
		}
	}
}