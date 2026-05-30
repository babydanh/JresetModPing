package com.example.client;

import com.example.ExampleMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class ExampleModClient implements ClientModInitializer {
	public static final JumpResetHandler JUMP_RESET_HANDLER = new JumpResetHandler();
	public static final HitPredictionHandler HIT_PREDICTION_HANDLER = new HitPredictionHandler();

	@Override
	public void onInitializeClient() {
		ExampleMod.LOGGER.info("Jump Reset Ping Mod client initialized!");

		// Register tick event to monitor jump reset timing each game tick
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			JUMP_RESET_HANDLER.onClientTick(client);
		});

		// Register HUD render callback to display Jump Reset evaluation
		HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
			if (JUMP_RESET_HANDLER.resultDisplayTicks > 0) {
				Minecraft client = Minecraft.getInstance();
				if (client.font != null) {
					String text = JUMP_RESET_HANDLER.lastResult;
					int color = JUMP_RESET_HANDLER.resultColor;

					// Calculate position (centered, slightly below crosshair)
					int screenWidth = client.getWindow().getGuiScaledWidth();
					int screenHeight = client.getWindow().getGuiScaledHeight();
					int x = (screenWidth - client.font.width(text)) / 2;
					int y = (screenHeight / 2) + 20;

					// Draw text with shadow
					drawContext.drawString(client.font, text, x, y, color, true);
				}
			}
		});
	}
}