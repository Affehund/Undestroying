package com.affehund.undestroying;

import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

/**
 * @author Affehund
 *
 */
public class ModUtils {
	public static boolean hasMinUndestroyingLevel(float level, ItemStack stack) {
		return (EnchantmentHelper.getLevel(UndestroyingFabric.UNDESTROYING_ENCHANTMENT, stack) >= level);
	}

	public static boolean isItemEnabledForUndestroying(Item item) {
		return UndestroyingFabric.CONFIG.INVERTED_BLACKLIST == UndestroyingFabric.CONFIG.BLACKLISTED_ITEMS
				.contains(item.toString());
	}

	public static boolean hasConfigLevel(int value, ItemStack stack) {
		return value == 0 ? false : hasMinUndestroyingLevel(value, stack);
	}

	public static boolean isAntiBreakingEnabled() {
		return UndestroyingFabric.CONFIG.TOOL_BREAKING > 0 ? true : false;
	}

	public static boolean toolMatches(Item item) {
		return item instanceof ToolItem || item instanceof ShearsItem || item instanceof SwordItem
				|| item instanceof RangedWeaponItem || item instanceof TridentItem;
	}

	public static boolean aboutToBreak(ItemStack stack) {
		return stack.getMaxDamage() > 2 && stack.getDamage() >= stack.getMaxDamage() - 2;
	}

	public static boolean cancleItemUsing() {
		MinecraftClient mc = MinecraftClient.getInstance();
		if (mc.player == null || mc.player.isCreative())
			return false;
		if (!ModUtils.isAntiBreakingEnabled())
			return false;
		for (Hand hand : Hand.values()) {
			ItemStack stack = mc.player.getStackInHand(hand);
			if (stack.isEmpty())
				return false;
			if (!stack.isDamageable())
				return false;
			if (!ModUtils.aboutToBreak(stack))
				return false;
			if (!ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.TOOL_BREAKING, stack))
				return false;
			if (!ModUtils.toolMatches(stack.getItem()))
				return false;
			else {
				mc.inGameHud.setOverlayMessage(
						new TranslatableText(ModConstants.TOOLTIP_TOOL_BROKEN).formatted(Formatting.DARK_RED), false);
				return true;
			}
		}
		return false;
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
