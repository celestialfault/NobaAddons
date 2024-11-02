package me.nobaboy.nobaaddons.features.chat.chatcommands.impl

import me.nobaboy.nobaaddons.config.NobaConfigManager
import me.nobaboy.nobaaddons.features.chat.chatcommands.ChatCommandManager
import me.nobaboy.nobaaddons.features.chat.chatcommands.impl.dm.PartyMeCommand
import me.nobaboy.nobaaddons.features.chat.chatcommands.impl.dm.WarpUserCommand
import me.nobaboy.nobaaddons.features.chat.chatcommands.impl.shared.HelpCommand
import me.nobaboy.nobaaddons.features.chat.chatcommands.impl.shared.WarpOutCommand
import me.nobaboy.nobaaddons.features.chat.chatcommands.impl.shared.WarpPlayerHandler
import me.nobaboy.nobaaddons.utils.StringUtils.cleanFormatting
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import java.util.regex.Pattern

object DMCommands : ChatCommandManager() {
	private val config get() = NobaConfigManager.config.chat.chatCommands.dm

	override val enabled get() = config.enabled
	override val pattern =
		Pattern.compile("^From (?:\\[[A-Z+]+] )?(?<username>[A-z0-9_]+): [!?.](?<command>[A-z0-9_]+) ?(?<argument>[A-z0-9_ ]+)?")

	init {
		register(HelpCommand(this, "msg", config::help))
		register(WarpOutCommand("msg", config::warpOut))
		register(WarpUserCommand())
		register(PartyMeCommand())
	}

	fun init() {
		ClientReceiveMessageEvents.GAME.register { message, _ ->
			val cleanMessage = message.string.cleanFormatting()

			if(WarpPlayerHandler.isWarping) {
				WarpPlayerHandler.handleMessage(cleanMessage)
				return@register
			}

			processMessage(cleanMessage)
		}
	}
}