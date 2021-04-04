package com.affehund.undestroying.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.affehund.undestroying.ModUtils;
import com.affehund.undestroying.Undestroying;
import com.affehund.undestroying.UndestroyingConfig;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

/**
 * @author Affehund
 *
 */
@Mixin(ItemEntity.class)
public class ItemEntityMixin {

	@Inject(at = @At("TAIL"), method = "tick()V")
	private void tick(CallbackInfo callbackInfo) {
		ItemStack stack = this.getItem();
		if (ModUtils.isItemEnabledForUndestroying(stack)) {
			if (EnchantmentHelper.getEnchantments(stack).containsKey(Undestroying.UNDESTROYING_ENCHANTMENT.get())) {
				ItemEntity entity = (ItemEntity) (Object) this;
				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.DESPAWNING, stack)) {
					entity.age = 0;
				}
				if (ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.VOID, stack)) {
					BlockPos itemPos = new BlockPos(entity.position());
					if (itemPos.getY() < -32) {
						entity.setDeltaMovement(0, 0, 0);
						entity.teleportTo(itemPos.getX(), 0.0D, itemPos.getZ());
						PlayerEntity nearPlayer = entity.level.getNearestPlayer(entity,
								UndestroyingConfig.COMMON_CONFIG.VOID_TELEPORT_RANGE.get());
						if (nearPlayer != null) {
							Vector3d playerPos = nearPlayer.position();
							entity.teleportTo(playerPos.x, playerPos.y, playerPos.z);
						} else {
							entity.setNoGravity(true);
							entity.teleportTo(itemPos.getX(), 0.0D, itemPos.getZ());
						}
					}
				}
			}
		}
	}

	@Inject(at = @At("HEAD"), method = "hurt(Lnet/minecraft/util/DamageSource;F)Z", cancellable = true)
	private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {

		ItemStack stack = this.getItem();
		if (ModUtils.isItemEnabledForUndestroying(stack)) {
			if (EnchantmentHelper.getEnchantments(stack).containsKey(Undestroying.UNDESTROYING_ENCHANTMENT.get())) {

				if (source.equals(DamageSource.ANVIL)
						&& ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.ANVIL, stack)) {
					callback.setReturnValue(false);
				}

				if (source.equals(DamageSource.CACTUS)
						&& ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.ANVIL, stack)) {
					callback.setReturnValue(false);
				}

				if (source.isExplosion()
						&& ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.EXPLOSION, stack)) {
					callback.setReturnValue(false);
				}

				if ((source.equals(DamageSource.IN_FIRE) || source.equals(DamageSource.ON_FIRE))
						&& ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.FIRE, stack)) {
					callback.setReturnValue(false);
				}

				if (source.equals(DamageSource.LAVA)
						&& ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.LAVA, stack)) {
					callback.setReturnValue(false);
				}

				if (source.equals(DamageSource.LIGHTNING_BOLT)
						&& ModUtils.hasConfigLevel(UndestroyingConfig.COMMON_CONFIG.LIGHTNING_BOLT, stack)) {
					callback.setReturnValue(false);
				} else {
					callback.setReturnValue(true);
				}
			}
		}
	}

	@Shadow
	public ItemStack getItem() {
		throw new IllegalStateException("Couldn't shadow getItem()");
	}
}
