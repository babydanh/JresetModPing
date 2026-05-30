package com.example.client.mixin;

import com.example.client.ExampleModClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {

	@Inject(method = "handleAnimate", at = @At("TAIL"))
	private void onEntityAnimation(ClientboundAnimatePacket packet, CallbackInfo ci) {
		if (packet.getAction() == 0) { // 0 = SWING_MAIN_HAND
			Minecraft client = Minecraft.getInstance();
			if (client.level != null) {
				Entity entity = client.level.getEntity(packet.getId());
				if (entity != null) {
					ExampleModClient.HIT_PREDICTION_HANDLER.onEntitySwingArm(entity);
				}
			}
		}
	}
}
