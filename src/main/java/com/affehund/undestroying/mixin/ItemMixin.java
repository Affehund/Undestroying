package com.affehund.undestroying.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.affehund.undestroying.ModConstants;
import com.affehund.undestroying.UndestroyingFabric;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

/**
 * @author Affehund
 *
 */
@Mixin(Item.class)
public class ItemMixin {
	@Inject(method = "appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/item/TooltipContext;)V", at = @At("HEAD"))
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext,
			CallbackInfo info) {
		if (UndestroyingFabric.CONFIG.SHOW_TOOLTIP) {
			Item item = (Item) (Object) this;

			if (UndestroyingFabric.isItemEnabledForUndestroying(item)
					&& EnchantmentHelper.get(itemStack).containsKey(UndestroyingFabric.UNDESTROYING_ENCHANTMENT)) {
				if (UndestroyingFabric.hasMinUndestroyingLevel(1, itemStack)) {
					tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING)
							.formatted(Formatting.GREEN));
					tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_1)
							.formatted(Formatting.GREEN));
				}
				if (UndestroyingFabric.hasMinUndestroyingLevel(2, itemStack)) {
					tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_2)
							.formatted(Formatting.GREEN));
				}
				if (UndestroyingFabric.hasMinUndestroyingLevel(3, itemStack)) {
					tooltip.add(new TranslatableText(ModConstants.TOOLTIP_ENCHANTMENT_UNDESTROYING_3)
							.formatted(Formatting.GREEN));
				}
			}
		}
	}
}
