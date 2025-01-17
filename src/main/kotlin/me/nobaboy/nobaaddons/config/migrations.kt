// This file intentionally violates this style rule to make the migration application order unquestionably clear.
@file:Suppress("FunctionName")

package me.nobaboy.nobaaddons.config

import com.google.gson.JsonObject
import dev.celestialfault.celestialconfig.migrations.Migrations

/*
 * Migrations MUST be added at the end of this block, otherwise they will NOT run. Migrations that have already been
 * applied are skipped, so new changes must be added as separate migrations. Removing pre-existing migrations is
 * NOT supported and will cause player configs to completely break, so avoid doing so.
 */
val migrations = Migrations.create {
	add(migration = ::`001_removeYaclVersion`)
	add(migration = ::`002_inventoryCategory`)
	add(migration = ::`003_renameGlaciteMineshaftShareCorpses`)
}

internal fun `001_removeYaclVersion`(json: JsonObject) {
	json.remove("version")
}

internal fun `002_inventoryCategory`(json: JsonObject) {
	val uiAndVisuals = json["uiAndVisuals"]?.asJsonObject ?: return
	val inventory = json["inventory"]?.asJsonObject ?: JsonObject().also { json.add("inventory", it) }

	uiAndVisuals.remove("slotInfo")?.asJsonObject?.let { slotInfo ->
		slotInfo.remove("enabled")
		inventory.add("slotInfo", slotInfo)
	}

	uiAndVisuals.remove("enchantments")?.asJsonObject?.let { enchantments ->
		enchantments.remove("parseItemEnchants")?.asBoolean?.let { parseItemEnchants ->
			enchantments.addProperty("modifyTooltips", parseItemEnchants)
		}

		inventory.add("enchantmentTooltips", enchantments)
	}
}

internal fun `003_renameGlaciteMineshaftShareCorpses`(json: JsonObject) {
	val glaciteMineshaft = json["mining"]?.asJsonObject["glaciteMineshaft"]?.asJsonObject ?: return
	glaciteMineshaft.add("autoShareCorpses", glaciteMineshaft.remove("autoShareCorpseCoords") ?: return)
}