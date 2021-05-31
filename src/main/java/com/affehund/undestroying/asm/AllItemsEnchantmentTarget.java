package com.affehund.undestroying.asm;

import com.affehund.undestroying.ModUtils;
import com.affehund.undestroying.mixin.EnchantmentTargetMixin;

import net.minecraft.item.BookItem;
import net.minecraft.item.Item;

/**
 * @author Affehund
 *
 */
public class AllItemsEnchantmentTarget extends EnchantmentTargetMixin {

	@Override
	public boolean isAcceptableItem(Item item) {
		return item instanceof BookItem || ModUtils.isItemEnabledForUndestroying(item);
	}

}
