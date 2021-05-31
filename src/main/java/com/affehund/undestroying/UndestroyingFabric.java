package com.affehund.undestroying;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chocohead.mm.api.ClassTinkerers;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UndestroyingFabric implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger(ModConstants.MOD_NAME);

	public static final EnchantmentTarget ALL_ITEMS = ClassTinkerers.getEnum(EnchantmentTarget.class,
			ModConstants.ALL_ITEMS_STRING);

	public static Enchantment UNDESTROYING_ENCHANTMENT = Registry.register(Registry.ENCHANTMENT,
			new Identifier(ModConstants.MOD_ID, ModConstants.MOD_ID),
			new UndestroyingEnchantment(Rarity.UNCOMMON, ALL_ITEMS, EquipmentSlot.values()));

	public static UndestroyingConfig CONFIG;

	@Override
	public void onInitialize() {
		LOGGER.debug("Loading up " + ModConstants.MOD_NAME + "!");
		CONFIG = UndestroyingConfig.setup();
	}
}
