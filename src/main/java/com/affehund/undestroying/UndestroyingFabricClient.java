package com.affehund.undestroying;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

/**
 * @author Affehund
 *
 */
public class UndestroyingFabricClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ItemTooltipCallback.EVENT.register((stack, context, tooltip) -> {
			Item item = stack.getItem();
			if (UndestroyingFabric.CONFIG.SHOW_TOOLTIP && ModUtils.isItemEnabledForUndestroying(item)
					&& EnchantmentHelper.get(stack).containsKey(UndestroyingFabric.UNDESTROYING_ENCHANTMENT)
					&& !(item instanceof EnchantedBookItem)) {

				if (stack.isDamageable()) {
					if (ModUtils.toolMatches(item)) {
						if (ModUtils.aboutToBreak(stack)) {
							tooltip.add(new TranslatableText(ModConstants.TOOLTIP_TOOL_BROKEN)
									.formatted(Formatting.DARK_RED));
						}
					}
				}

				if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(),
						GLFW.GLFW_KEY_LEFT_SHIFT)) {
					tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING)
							.formatted(Formatting.GRAY));

					if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.ANVIL, stack)) {
						tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_ANVIL,
								ModUtils.intToRoman(UndestroyingFabric.CONFIG.ANVIL)).formatted(Formatting.GRAY));
					}
					if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.CACTUS, stack)) {
						tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_CACTUS,
								ModUtils.intToRoman(UndestroyingFabric.CONFIG.CACTUS)).formatted(Formatting.GRAY));
					}
					if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.DESPAWNING, stack)) {
						tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_DESPAWNING,
								ModUtils.intToRoman(UndestroyingFabric.CONFIG.DESPAWNING)).formatted(Formatting.GRAY));
					}
					if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.EXPLOSION, stack)) {
						tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_EXPLOSION,
								ModUtils.intToRoman(UndestroyingFabric.CONFIG.EXPLOSION)).formatted(Formatting.GRAY));
					}
					if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.FIRE, stack)) {
						tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_FIRE,
								ModUtils.intToRoman(UndestroyingFabric.CONFIG.FIRE)).formatted(Formatting.GRAY));
					}
					if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.LAVA, stack)) {
						tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_LAVA,
								ModUtils.intToRoman(UndestroyingFabric.CONFIG.LAVA)).formatted(Formatting.GRAY));
					}
					if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.LIGHTNING_BOLT, stack)) {
						tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_LIGHTNING_BOLT,
								ModUtils.intToRoman(UndestroyingFabric.CONFIG.LIGHTNING_BOLT))
										.formatted(Formatting.GRAY));
					}
					if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.TOOL_BREAKING, stack)
							&& ModUtils.toolMatches(item)) {
						tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_TOOL_BREAKING,
								ModUtils.intToRoman(UndestroyingFabric.CONFIG.TOOL_BREAKING))
										.formatted(Formatting.GRAY));
					}
					if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.VOID, stack)) {
						tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_VOID,
								ModUtils.intToRoman(UndestroyingFabric.CONFIG.VOID)).formatted(Formatting.GRAY));
					}
				}
			}
		});
	}
}
