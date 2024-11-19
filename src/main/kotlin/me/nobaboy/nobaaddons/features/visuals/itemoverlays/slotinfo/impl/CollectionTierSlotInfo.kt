package me.nobaboy.nobaaddons.features.visuals.itemoverlays.slotinfo.impl

import me.nobaboy.nobaaddons.events.ScreenRenderEvents
import me.nobaboy.nobaaddons.features.visuals.itemoverlays.slotinfo.ISlotInfo
import me.nobaboy.nobaaddons.utils.InventoryUtils
import me.nobaboy.nobaaddons.utils.NumberUtils.tryRomanToArabic
import me.nobaboy.nobaaddons.utils.items.ItemUtils.lore
import me.nobaboy.nobaaddons.utils.items.ItemUtils.stringLines

object CollectionTierSlotInfo : ISlotInfo {
	override val enabled: Boolean get() = config.collectionTier

	override fun handle(event: ScreenRenderEvents.DrawSlot) {
		if(InventoryUtils.openInventoryName()?.endsWith(" Collections") != true) return

		val itemStack = event.itemStack
		val lore = itemStack.lore.stringLines
		if(!lore.any { it == "Click to view!" }) return

		val tier = if(lore.any { it.startsWith("Progress to") }) {
			itemStack.name.string.split(" ").lastOrNull()?.tryRomanToArabic()?.toString() ?: "0"
		} else {
			"§a✔"
		}

		drawCount(event, tier)
	}
}