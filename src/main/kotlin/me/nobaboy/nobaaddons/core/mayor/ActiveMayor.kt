package me.nobaboy.nobaaddons.core.mayor

data class ActiveMayor(val mayor: Mayor, val perks: List<MayorPerk>) {
	val name by mayor::mayorName
}
