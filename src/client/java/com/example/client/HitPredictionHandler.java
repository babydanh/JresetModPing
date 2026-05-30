package com.example.client;

import com.example.ExampleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class HitPredictionHandler {
	private static final double MAX_REACH = 3.5;
	private static final double AIM_TOLERANCE = 0.5;
	private static final long PREDICTION_COOLDOWN_MS = 200;
	private long lastPredictionPing = 0;

	public void onEntitySwingArm(Entity attacker) {
		Minecraft client = Minecraft.getInstance();
		LocalPlayer localPlayer = client.player;
		if (localPlayer == null) return;
		if (attacker == localPlayer) return;

		double distance = attacker.distanceTo(localPlayer);
		if (distance > MAX_REACH) return;

		Vec3 lookVec = attacker.getLookAngle();
		Vec3 toPlayer = localPlayer.position().subtract(attacker.position()).normalize();
		double dot = lookVec.dot(toPlayer);

		if (dot < Math.cos(AIM_TOLERANCE)) return;

		long now = System.currentTimeMillis();
		if (now - lastPredictionPing < PREDICTION_COOLDOWN_MS) return;
		lastPredictionPing = now;

		playWarningPing(localPlayer);
		ExampleMod.LOGGER.info("[HitPredict] Incoming hit from {} at distance {:.1f}",
				attacker.getName().getString(), distance);
	}

	private void playWarningPing(LocalPlayer player) {
		player.playSound(SoundEvents.NOTE_BLOCK_BELL.value(), 1.0F, 1.5F);
	}
}
