package com.affehund.undestroying.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.affehund.undestroying.ModUtils;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * @author Affehund
 *
 */
@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
	@Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
	public void attackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> info) {
		if (ModUtils.cancleItemUsing()) {
			info.setReturnValue(false);
		}
	}

	@Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
	public void attackEntity(PlayerEntity player, Entity target, CallbackInfo info) {
		if (ModUtils.cancleItemUsing()) {
			info.cancel();
		}
	}

	@Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
	public void interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult,
			CallbackInfoReturnable<ActionResult> info) {
		if (ModUtils.cancleItemUsing()) {
			info.setReturnValue(ActionResult.FAIL);
		}
	}

	@Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
	public void interactItem(PlayerEntity player, World world, Hand hand, CallbackInfoReturnable<ActionResult> info) {
		if (ModUtils.cancleItemUsing()) {
			info.setReturnValue(ActionResult.FAIL);
		}
	}

	@Inject(method = "interactEntity", at = @At("HEAD"), cancellable = true)
	public void interactEntity(PlayerEntity player, Entity entity, Hand hand,
			CallbackInfoReturnable<ActionResult> info) {
		if (ModUtils.cancleItemUsing()) {
			info.setReturnValue(ActionResult.FAIL);
		}
	}
}
