package com.example.client;

import com.example.ExampleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;

public class JumpResetHandler {
	private boolean wasOnGround = true;
	private int previousHurtTime = 0;
	private int ticksSinceDamage = -1;

	public String lastResult = "";
	public int resultDisplayTicks = 0;
	public int resultColor = 0xFFFFFF;

	public void onDamageReceived() {
		ticksSinceDamage = 0;
	}

	public void onClientTick(Minecraft client) {
		LocalPlayer player = client.player;
		if (player == null)
			return;

		if (resultDisplayTicks > 0) {
			resultDisplayTicks--;
		}

		if (player.hurtTime == 10 && previousHurtTime != 10) {
			onDamageReceived();
		}
		previousHurtTime = player.hurtTime;

		if (ticksSinceDamage >= 0) {
			ticksSinceDamage++;
			if (ticksSinceDamage > 20) { // Timeout after 20 ticks (1 second)
				ticksSinceDamage = -1;
			}
		}

		boolean isOnGround = player.onGround();
		if (wasOnGround && !isOnGround) {
			if (ticksSinceDamage >= 0) {
				int reactionTicks = ticksSinceDamage;

				net.minecraft.network.chat.Component titleText;
				if (reactionTicks <= 1) {
					lastResult = "Perfect!";
					titleText = net.minecraft.network.chat.Component.literal(lastResult)
							.withStyle(net.minecraft.ChatFormatting.GREEN);
				} else if (reactionTicks <= 3) {
					lastResult = "Good";
					titleText = net.minecraft.network.chat.Component.literal(lastResult)
							.withStyle(net.minecraft.ChatFormatting.YELLOW);
				} else {
					lastResult = "Fail";
					titleText = net.minecraft.network.chat.Component.literal(lastResult)
							.withStyle(net.minecraft.ChatFormatting.RED);
				}

				// Hiển thị dạng Title chính (Title to đùng giữa màn hình)
				client.gui.setTimes(0, 30, 10);
				client.gui.setTitle(titleText);
				client.gui.setSubtitle(net.minecraft.network.chat.Component.empty());

				ExampleMod.LOGGER.info("[JumpReset] {} (Reaction: {} ticks)", lastResult, reactionTicks);
				ticksSinceDamage = -1;
			}
		}
		wasOnGround = isOnGround;
	}
}
