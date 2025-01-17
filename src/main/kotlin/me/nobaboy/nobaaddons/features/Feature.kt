package me.nobaboy.nobaaddons.features

import com.google.gson.JsonObject
import dev.celestialfault.celestialconfig.Property
import dev.isxander.yacl3.api.OptionAddable
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.Option
import me.nobaboy.nobaaddons.config.FeatureConfig
import me.nobaboy.nobaaddons.config.NobaConfigUtils.boolean
import me.nobaboy.nobaaddons.utils.CommonText
import net.minecraft.text.Text
import org.jetbrains.annotations.UnmodifiableView
import java.util.Collections
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

typealias ConfigBuilder = OptionAddable.() -> Unit

abstract class Feature(
	val id: String,
	val name: Text,
	val description: Text? = null,
	val category: FeatureCategory,
	protected val enabledByDefault: Boolean = false,
) {
	var enabled by Property.of<Boolean>("enabled", enabledByDefault)

	protected val options: List<Property<*>> by lazy {
		this@Feature::class.memberProperties.mapNotNull { getProperty(it, this) }
	}

	/**
	 * Override this with your [config] (or [simpleConfig]) implementation to add your options to the YACL screen
	 */
	protected abstract val configBuilder: ConfigBuilder

	fun init() {
		check(id !in features) { "Feature ID $id has already been used" }
		features[id] = this
		FeatureConfig.json.get(id)?.let { load(it as? JsonObject ?: return@let) }
		initFeature()
	}

	/**
	 * Implement your feature init logic here
	 */
	protected abstract fun initFeature()

	open fun load(data: JsonObject) {
		for(option in options) {
			option.load(data.get(option.key) ?: continue)
		}
	}

	open fun save(): JsonObject = JsonObject().apply {
		for(option in options) {
			add(option.key, option.save() ?: continue)
		}
	}

	/**
	 * Builds an [OptionGroup] for the current feature
	 */
	fun buildConfig(): OptionGroup = OptionGroup.createBuilder().apply {
		name(name)
		description?.let { description(OptionDescription.of(it)) }
		collapsed(true)
		configBuilder(this)
	}.build()

	/**
	 * Utility method to cast [builder] to a [ConfigBuilder]
	 */
	protected inline fun simpleConfig(crossinline builder: OptionAddable.() -> Unit): ConfigBuilder = { builder(this) }

	/**
	 * Provides a [Option] with the value of [enabled] for use with [me.nobaboy.nobaaddons.config.NobaConfigUtils.requires]
	 */
	protected inline fun config(crossinline builder: OptionAddable.(Option<Boolean>) -> Unit): ConfigBuilder = {
		builder(this, boolean(CommonText.Config.ENABLED, null, enabledByDefault, ::enabled))
	}

	companion object {
		private val features = mutableMapOf<String, Feature>()

		fun features(): @UnmodifiableView Map<String, Feature> = Collections.unmodifiableMap(features)

		@Suppress("UNCHECKED_CAST")
		private fun <I : Any> getProperty(prop: KProperty1<I, *>, instance: Any): Property<*>? =
			prop.apply { isAccessible = true }.getDelegate(instance as I) as? Property<*>
	}
}