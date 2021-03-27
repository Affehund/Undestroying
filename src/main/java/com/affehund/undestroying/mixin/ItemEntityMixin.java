package com.affehund.undestroying.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.affehund.undestroying.Undestroying;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * @author Affehund
 *
 */
@Mixin(ItemEntity.class)
public class ItemEntityMixin {
	@Inject(at = @At("HEAD"), method = "hurt(Lnet/minecraft/util/DamageSource;F)Z", cancellable = true)
	private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {

		if (Undestroying.isItemEnabledForUndestroying(this.getItem())) {

			if (EnchantmentHelper.getEnchantments(this.getItem())
					.containsKey(Undestroying.UNDESTROYING_ENCHANTMENT.get())) {

				// TODO: tp item
				// disabled for OUT_OF_WORLD
				if (source.equals(DamageSource.OUT_OF_WORLD)) {
					callback.setReturnValue(true);
				}

				// Level 1: Cactus
				if (source.equals(DamageSource.CACTUS) && Undestroying.hasMinUndestroyingLevel(1, this.getItem())) {
					callback.setReturnValue(false);
				}

				// Level 2: Fire
				if (source.isFire() && Undestroying.hasMinUndestroyingLevel(2, this.getItem())) {
					callback.setReturnValue(false);
				}

				// Level 3: Explosion and any other damage source
				if (Undestroying.hasMinUndestroyingLevel(3, this.getItem())) {
					callback.setReturnValue(false);
				}
			}
		}
	}

	@Shadow
	public ItemStack getItem() {
		throw new IllegalStateException("Couldn't shadow getItem()");
	}
}
