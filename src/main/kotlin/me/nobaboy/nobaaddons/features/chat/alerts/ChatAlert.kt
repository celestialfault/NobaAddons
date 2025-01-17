package me.nobaboy.nobaaddons.features.chat.alerts

import net.minecraft.text.Text

abstract class ChatAlert(val id: String, val name: Text, val description: Text? = null) {
	var enabled: Boolean = false

	abstract fun processMessage(message: String)
}