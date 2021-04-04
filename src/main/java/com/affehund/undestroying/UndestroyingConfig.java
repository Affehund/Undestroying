package com.affehund.undestroying;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
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

		public final IntValue MAX_LEVEL;

		public final IntValue ANVIL;
		public final IntValue CACTUS;
		public final IntValue DESPAWNING;
		public final IntValue EXPLOSION;
		public final IntValue FIRE;
		public final IntValue LAVA;
		public final IntValue LIGHTNING_BOLT;
		public final IntValue TOOL_BREAKING;
		public final IntValue VOID;

		public final IntValue VOID_TELEPORT_RANGE;

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
			MAX_LEVEL = builder.comment("This sets the maximum enchantment level for the undestroying enchantment.")
					.defineInRange("max_level", 3, 1, 10);
			builder.pop();

			builder.push("enchantment_levels").comment(
					"Here you can set the required enchantment levels for the undestroying enchantment. If you set a value to 0, it will be disabled and won't work at all.");

			ANVIL = builder.comment("This sets the required enchantment level to prevent anvil destruction.")
					.defineInRange("anvil", 1, 0, 10);
			CACTUS = builder.comment("This sets the required enchantment level to prevent cactus destruction.")
					.defineInRange("cactus", 1, 0, 10);
			DESPAWNING = builder.comment("This sets the required enchantment level to prevent despawning destruction.")
					.defineInRange("despawning", 3, 0, 10);
			EXPLOSION = builder.comment("This sets the required enchantment level to prevent explosion destruction.")
					.defineInRange("explosion", 2, 0, 10);
			FIRE = builder.comment("This sets the required enchantment level to prevent fire destruction.")
					.defineInRange("fire", 2, 0, 10);
			LAVA = builder.comment("This sets the required enchantment level to prevent lava destruction.")
					.defineInRange("lava", 2, 0, 10);
			LIGHTNING_BOLT = builder
					.comment("This sets the required enchantment level to prevent lightning bolt destruction.")
					.defineInRange("lightning_bolt", 2, 0, 10);
			TOOL_BREAKING = builder.comment("This sets the required enchantment level to prevent tool breaking.")
					.defineInRange("tool_breaking", 3, 0, 10);
			VOID = builder.comment("This sets the required enchantment level to prevent void destruction.")
					.defineInRange("void", 3, 0, 10);

			VOID_TELEPORT_RANGE = builder.comment(
					"This sets the maximum range an item will be teleported to the next player. If there's no player near by the item will be float at y=0.")
					.defineInRange("void_teleport_range", 128, 0, 2048);

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
