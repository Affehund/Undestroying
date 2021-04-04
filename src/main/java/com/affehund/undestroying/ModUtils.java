package com.affehund.undestroying;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author Affehund
 *
 */
public class ModUtils {
	public static boolean isItemEnabledForUndestroying(ItemStack stack) {
		return UndestroyingConfig.COMMON_CONFIG.INVERTED_BLACKLIST
				.get() == UndestroyingConfig.COMMON_CONFIG.BLACKLISTED_ITEMS.get()
						.contains(stack.getItem().getItem().getRegistryName().toString());
	}

	public static boolean hasMinUndestroyingLevel(float level, ItemStack stack) {
		return (EnchantmentHelper.getItemEnchantmentLevel(Undestroying.UNDESTROYING_ENCHANTMENT.get(), stack) >= level);
	}

	public static boolean hasConfigLevel(IntValue intValue, ItemStack stack) {
		int value = intValue.get();
		return value == 0 ? false : hasMinUndestroyingLevel(value, stack);
	}

	public static boolean isAntiBreakingEnabled() {
		return UndestroyingConfig.COMMON_CONFIG.TOOL_BREAKING.get() > 0 ? true : false;
	}

	public static boolean toolMatches(Item item) {
		return item instanceof ToolItem || item instanceof ShearsItem || item instanceof SwordItem
				|| item instanceof ShootableItem || item instanceof TridentItem;
	}

	public static boolean aboutToBreak(ItemStack stack) {
		return stack.getMaxDamage() > 2 && stack.getDamageValue() >= stack.getMaxDamage() - 2;
	}

	public static void cancleEvent(Event event, PlayerEntity player, ItemStack stack) {
		if (ModUtils.isAntiBreakingEnabled() && !player.abilities.instabuild && !stack.isEmpty()) {
			Item item = stack.getItem();
			if (stack.isDamageableItem()
					&& ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.TOOL_BREAKING, stack)
					&& ModUtils.toolMatches(item)) {
				if (ModUtils.aboutToBreak(stack)) {
					event.setCanceled(true);
				}
			}
		}
	}

	public static String intToRoman(int num) {
		int[] numbers = { 10, 9, 5, 4, 1 };
		String[] romanNumbers = { "X", "IX", "V", "IV", "I" };
		StringBuilder roman = new StringBuilder();

		for (int i = 0; i < numbers.length; i++) {
			while (num >= numbers[i]) {
				num -= numbers[i];
				roman.append(romanNumbers[i]);
			}
		}
		return roman.toString();
	}
}
