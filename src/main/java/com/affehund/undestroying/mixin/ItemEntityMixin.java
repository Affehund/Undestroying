package com.affehund.undestroying.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.affehund.undestroying.ModUtils;
import com.affehund.undestroying.UndestroyingFabric;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * @author Affehund
 *
 */
@Mixin(ItemEntity.class)
public class ItemEntityMixin {

	@Inject(at = @At("TAIL"), method = "tick()V")
	private void tick(CallbackInfo callbackInfo) {
		ItemStack stack = this.getStack();
		if (ModUtils.isItemEnabledForUndestroying(stack.getItem())) {
			if (EnchantmentHelper.get(stack).containsKey(UndestroyingFabric.UNDESTROYING_ENCHANTMENT)) {
				ItemEntity entity = (ItemEntity) (Object) this;
				if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.DESPAWNING, stack)) {
					entity.age = 0;
				}
				if (ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.VOID, stack)) {
					BlockPos itemPos = new BlockPos(entity.getPos());
					if (itemPos.getY() < -32) {
						entity.setNoGravity(true);
						entity.setVelocity(0, 0, 0);
						entity.teleport(itemPos.getX(), 0.0D, itemPos.getZ());
						entity.setGlowing(true);
						PlayerEntity nearPlayer = entity.world.getClosestPlayer(entity,
								UndestroyingFabric.CONFIG.VOID_TELEPORT_RANGE);
						if (nearPlayer != null) {
							Vec3d playerPos = nearPlayer.getPos();
							entity.teleport(playerPos.x, playerPos.y, playerPos.z);
						}
					}
				}
			}
		}
	}

	@Inject(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", cancellable = true)
	private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
		ItemStack stack = this.getStack();
		if (ModUtils.isItemEnabledForUndestroying(stack.getItem())) {
			if (EnchantmentHelper.get(stack).containsKey(UndestroyingFabric.UNDESTROYING_ENCHANTMENT)) {

				if (source.equals(DamageSource.ANVIL)
						&& ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.ANVIL, stack)) {
					callback.setReturnValue(false);
				}

				if (source.equals(DamageSource.CACTUS)
						&& ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.ANVIL, stack)) {
					callback.setReturnValue(false);
				}

				if (source.isExplosive() && ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.EXPLOSION, stack)) {
					callback.setReturnValue(false);
				}

				if ((source.equals(DamageSource.IN_FIRE) || source.equals(DamageSource.ON_FIRE))
						&& ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.FIRE, stack)) {
					callback.setReturnValue(false);
				}

				if (source.equals(DamageSource.LAVA)
						&& ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.LAVA, stack)) {
					callback.setReturnValue(false);
				}

				if (source.equals(DamageSource.LIGHTNING_BOLT)
						&& ModUtils.hasConfigLevel(UndestroyingFabric.CONFIG.LIGHTNING_BOLT, stack)) {
					callback.setReturnValue(false);
				} else {
					callback.setReturnValue(true);
				}
			}
		}
	}

	@Shadow
	public ItemStack getStack() {
		throw new IllegalStateException("Couldn't shadow getItem()");
	}
}
