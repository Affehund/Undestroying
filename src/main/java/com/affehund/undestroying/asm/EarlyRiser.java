package com.affehund.undestroying.asm;

import com.affehund.undestroying.ModConstants;
import com.chocohead.mm.api.ClassTinkerers;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

/**
 * @author Affehund
 *
 */
public class EarlyRiser implements Runnable {
	@Override
	public void run() {
		addTargets();
	}

	private void addTargets() {
		MappingResolver mapResolver = FabricLoader.getInstance().getMappingResolver();
		String enchantmentTarget = mapResolver.mapClassName("intermediary", "net.minecraft.class_1886");
		ClassTinkerers.enumBuilder(enchantmentTarget, new Class[0]).addEnumSubclass(ModConstants.ALL_ITEMS_STRING,
				"com.affehund.undestroying.asm.AllItemsEnchantmentTarget").build();
	}
}
