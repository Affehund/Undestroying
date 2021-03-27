package com.affehund.undestroying;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;

/**
 * A class for our config values (allow totem of undying, blacklisted
 * dimensions, teleport height, etc...).
 * 
 * @author Affehund
 *
 */
@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID, bus = Bus.MOD)
public class UndestroyingConfig {
	public static class UndestroyingCommonConfig {
		public final ConfigValue<ArrayList<String>> BLACKLISTED_ITEMS;
		public final BooleanValue INVERTED_BLACKLIST;
		public final BooleanValue SHOW_TOOLTIP;

		public UndestroyingCommonConfig(ForgeConfigSpec.Builder builder) {
			builder.comment("Undestroying Common Config").push("general");

			BLACKLISTED_ITEMS = builder.comment(
					"This add items to a blacklist, which can't be enchanted with undestroying. Example: \"minecraft:dirt\"")
					.define("blacklisted_items", new ArrayList<String>());
			INVERTED_BLACKLIST = builder.comment(
					"This sets whether the blacklist is inverted. If true only items on the blacklist can be enchanted with undestroying.")
					.define("inverted_blacklist", false);
			SHOW_TOOLTIP = builder.comment(
					"This sets whether a tooltip is shown to explain the levels of the undestroying enchantment.")
					.define("show_tooltip", true);
			builder.pop();
		}
	}

	public static final ForgeConfigSpec COMMON_CONFIG_SPEC;
	public static final UndestroyingCommonConfig COMMON_CONFIG;
	static {
		final Pair<UndestroyingCommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder()
				.configure(UndestroyingCommonConfig::new);
		COMMON_CONFIG_SPEC = specPair.getRight();
		COMMON_CONFIG = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading event) {
		Undestroying.LOGGER.info("Loaded {} config file from {}", event.getConfig().getFileName(), ModConstants.MOD_ID);
	}
}
