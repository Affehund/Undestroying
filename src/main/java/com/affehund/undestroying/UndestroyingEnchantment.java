package com.affehund.undestroying;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

/**
 * @author Affehund
 *
 */
public class UndestroyingEnchantment extends Enchantment {
	public UndestroyingEnchantment(Rarity rarity, EnchantmentType type, EquipmentSlotType[] slot) {
		super(rarity, type, slot);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return ModUtils.isItemEnabledForUndestroying(stack);
	}

	@Override
	public int getMaxLevel() {
		return UndestroyingConfig.COMMON_CONFIG.MAX_LEVEL.get();
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return enchantmentLevel * 30;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + 50;
	}

	public boolean isTreasureEnchantment() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return true;
	}

	@Override
	protected boolean checkCompatibility(Enchantment other) {
		return other == Enchantments.BINDING_CURSE
				&& !UndestroyingConfig.COMMON_CONFIG.COMPATIBLE_WITH_CURSE_OF_BINDING.get() ? false
						: super.checkCompatibility(other);
	}
}
