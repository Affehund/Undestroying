package com.affehund.undestroying;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UndestroyingFabric implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger(ModConstants.MOD_NAME);

	public static Enchantment UNDESTROYING_ENCHANTMENT = Registry.register(Registry.ENCHANTMENT,
			new Identifier(ModConstants.MOD_ID, ModConstants.MOD_ID),
			new UndestroyingEnchantment(Rarity.RARE, EnchantmentTarget.BREAKABLE, EquipmentSlot.values()));

	public static UndestroyingConfig CONFIG;

	@Override
	public void onInitialize() {
		LOGGER.debug("Loading up " + ModConstants.MOD_NAME + "!");
		CONFIG = UndestroyingConfig.setup();
	}

	public static boolean isItemStackEnabledForUndestroying(ItemStack stack) {
		return CONFIG.INVERTED_BLACKLIST == CONFIG.BLACKLISTED_ITEMS.contains(stack.getItem().toString());
	}

	public static boolean isItemEnabledForUndestroying(Item item) {
		return CONFIG.INVERTED_BLACKLIST == CONFIG.BLACKLISTED_ITEMS.contains(item.toString());
	}

	public static boolean hasMinUndestroyingLevel(float level, ItemStack stack) {
		return (EnchantmentHelper.getLevel(UNDESTROYING_ENCHANTMENT, stack) >= level);
	}
}
