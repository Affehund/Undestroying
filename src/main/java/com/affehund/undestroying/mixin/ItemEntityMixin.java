package com.affehund.undestroying.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.affehund.undestroying.UndestroyingFabric;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

/**
 * @author Affehund
 *
 */
@Mixin(ItemEntity.class)
public class ItemEntityMixin {
	@Inject(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", cancellable = true)
	private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
		if (UndestroyingFabric.isItemStackEnabledForUndestroying(this.getStack())) {
			if (EnchantmentHelper.get(this.getStack()).containsKey(UndestroyingFabric.UNDESTROYING_ENCHANTMENT)) {

				// TODO: tp item
				// disabled for OUT_OF_WORLD
				if (source.equals(DamageSource.OUT_OF_WORLD)) {
					callback.setReturnValue(true);
				}

				// Level 1: Cactus
				if (source.equals(DamageSource.CACTUS)
						&& UndestroyingFabric.hasMinUndestroyingLevel(1, this.getStack())) {
					callback.setReturnValue(false);
				}

				// Level 2: Fire
				if (source.isFire() && UndestroyingFabric.hasMinUndestroyingLevel(2, this.getStack())) {
					callback.setReturnValue(false);
				}

				// Level 3: Explosion and any other damage source
				if (UndestroyingFabric.hasMinUndestroyingLevel(3, this.getStack())) {
					callback.setReturnValue(false);
				}
			}
		}
	}

	@Shadow
	public ItemStack getStack() {
		throw new IllegalStateException("Couldn't shadow getItem()");
	}
}
