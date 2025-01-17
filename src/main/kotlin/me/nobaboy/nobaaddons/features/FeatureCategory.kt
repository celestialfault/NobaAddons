package me.nobaboy.nobaaddons.features

import me.nobaboy.nobaaddons.utils.tr
import net.minecraft.text.Text

enum class FeatureCategory(val displayName: Text, val order: Int) {
	GENERAL(tr("nobaaddons.feature.category.general", "General"), Int.MIN_VALUE),

	UI_AND_VISUAL(tr("nobaaddons.feature.category.uiAndVisual", "UI & Visuals"), 0),
	INVENTORY(tr("nobaaddons.feature.category.inventory", "Inventory"), 100),
	EVENTS(tr("nobaaddons.feature.category.events", "Events"), 200),
	FISHING(tr("nobaaddons.feature.category.fishing", "Fishing"), 300),
	MINING(tr("nobaaddons.feature.category.mining", "Mining"), 400),
	DUNGEONS(tr("nobaaddons.feature.category.dungeons", "Dungeons"), 500),
	CHAT(tr("nobaaddons.feature.category.chat", "Chat"), 600),
	QOL(tr("nobaaddons.feature.category.qol", "QOL"), 700),
	RIFT(tr("nobaaddons.feature.category.rift", "Rift"), 800),

	API(tr("nobaaddons.feature.category.api", "API"), Int.MAX_VALUE),
	;

	companion object {
		val CATEGORIES by lazy { entries.sortedBy(FeatureCategory::order) }
	}
}