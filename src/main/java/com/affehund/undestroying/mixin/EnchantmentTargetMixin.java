package com.affehund.undestroying.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;

/**
 * @author Affehund
 *
 */
@Mixin(EnchantmentTarget.class)
public abstract class EnchantmentTargetMixin {

	@Shadow
	public abstract boolean isAcceptableItem(Item item);
}
