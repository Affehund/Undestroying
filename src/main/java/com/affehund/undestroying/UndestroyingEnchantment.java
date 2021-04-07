package com.affehund.undestroying;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * @author Affehund
 *
 */
public class UndestroyingEnchantment extends Enchantment {
	public UndestroyingEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
		super(weight, type, slotTypes);
	}

	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return ModUtils.isItemEnabledForUndestroying(stack.getItem());
	}

	@Override
	public int getMaxLevel() {
		return UndestroyingFabric.CONFIG.MAX_LEVEL;
	}

	@Override
	public int getMinPower(int enchantmentLevel) {
		return enchantmentLevel * 30;
	}

	@Override
	public int getMaxPower(int enchantmentLevel) {
		return this.getMinPower(enchantmentLevel) + 50;
	}

	public boolean isTreasureEnchantment() {
		return true;
	}

	@Override
	protected boolean canAccept(Enchantment other) {
		return super.canAccept(other)
				&& UndestroyingFabric.CONFIG.COMPATIBLE_WITH_CURSE_OF_BINDING == (other == Enchantments.BINDING_CURSE);
	}
}
