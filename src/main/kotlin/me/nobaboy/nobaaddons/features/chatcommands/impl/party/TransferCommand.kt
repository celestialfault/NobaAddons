package me.nobaboy.nobaaddons.features.chatcommands.impl.party

import me.nobaboy.nobaaddons.api.PartyAPI
import me.nobaboy.nobaaddons.config.NobaConfigManager
import me.nobaboy.nobaaddons.features.chatcommands.ChatContext
import me.nobaboy.nobaaddons.features.chatcommands.IChatCommand
import me.nobaboy.nobaaddons.utils.HypixelCommands
import me.nobaboy.nobaaddons.utils.StringUtils.lowercaseEquals
import me.nobaboy.nobaaddons.utils.Utils

class TransferCommand : IChatCommand {
    override val name: String = "transfer"

    override val aliases: MutableList<String> = mutableListOf("ptme", "pt")

    override val usage: String = "(transfer|pt) [optional: username], ptme"

    override val isEnabled: Boolean
        get() = NobaConfigManager.get().chatCommands.party.transfer

    override fun run(ctx: ChatContext) {
        if (!PartyAPI.isLeader()) return

        if (!ctx.command().lowercaseEquals("ptme")) {
            val player = if (ctx.args().isEmpty()) ctx.user() else ctx.args()[0]
            HypixelCommands.partyTransfer(player)
            return
        }

        if (ctx.user() == Utils.getPlayerName()) return
        HypixelCommands.partyTransfer(ctx.user())
    }
}