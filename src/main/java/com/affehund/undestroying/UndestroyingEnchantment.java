package com.affehund.undestroying;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
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
		return Undestroying.isItemEnabledForUndestroying(stack);
	}

	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return 3;
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
}
