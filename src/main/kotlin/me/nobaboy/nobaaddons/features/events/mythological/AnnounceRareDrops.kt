package me.nobaboy.nobaaddons.features.events.mythological

import me.nobaboy.nobaaddons.events.InventoryEvents
import me.nobaboy.nobaaddons.utils.TextUtils.buildText
import me.nobaboy.nobaaddons.utils.TimedSet
import me.nobaboy.nobaaddons.utils.chat.ChatUtils
import me.nobaboy.nobaaddons.utils.items.ItemUtils.getSkyBlockItem
import me.nobaboy.nobaaddons.utils.sound.SoundUtils
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import kotlin.time.Duration.Companion.minutes

object AnnounceRareDrops {
	private val uuidCache = TimedSet<String>(1.minutes)

	private val rareDrops = listOf(
		"ANTIQUE_REMEDIES",
		"CROCHET_TIGER_PLUSHIE",
		"DWARF_TURTLE_SHELMET",
		"MINOS_RELIC"
	)

	fun init() {
		InventoryEvents.SLOT_UPDATE.register(this::onSlotUpdate)
	}

	private fun onSlotUpdate(event: InventoryEvents.SlotUpdate) {
		val itemStack = event.itemStack
		val item = itemStack.getSkyBlockItem() ?: return
		if(item.id !in rareDrops) return

		val uuid = item.uuid ?: return
		if(uuid in uuidCache) return
		uuidCache.add(uuid)

		val text = buildText {
			append(Text.literal("RARE DROP! ").formatted(Formatting.GOLD, Formatting.BOLD))
			append(itemStack.name)
		}

		ChatUtils.addMessage(text, prefix = false)
		SoundUtils.rareDropSound.play()
	}
}