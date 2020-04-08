package me.timeswitcher.valuemodify;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("valuemodify")
public class ValueModify
{
	public static final String VERSION = "1.0";

	public static Minecraft mc = Minecraft.getInstance();
	public static final Util UTIL = new Util();

	private static float kb = 1.0f;
	private static double jumpMotionY = 0.42d;

	public ValueModify() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onClientChat(ClientChatEvent e) {

		if (e.getMessage() != null) {

			if (e.getMessage().toLowerCase().startsWith("-kb ")) {

				e.setCanceled(true);

				String kbInput = e.getMessage().toLowerCase().replaceFirst("-kb ", "").toLowerCase();

				try {

					float kbValue = Float.parseFloat(kbInput);
					kb = kbValue;
					UTIL.sendClientMessage("Knockback strength set to " + kbValue);

				} catch (Exception exception) {

					UTIL.sendClientMessage("Invalid knockback input!");
				}

			} else if (e.getMessage().toLowerCase().startsWith("-jumpmotion ")) {

				e.setCanceled(true);

				String jumpMotionInput = e.getMessage().toLowerCase().replaceFirst("-jumpmotion ", "").toLowerCase();

				try {

					float motionValue = Float.parseFloat(jumpMotionInput);
					jumpMotionY = motionValue;
					UTIL.sendClientMessage("Jump Motion set to " + motionValue);

				} catch (Exception exception) {

					UTIL.sendClientMessage("Invalid motion input!");
				}
			}
		}
	}

	@SubscribeEvent
	public void onKnockBack(LivingKnockBackEvent e) {

		if (e.getEntity() != null) {

			if (e.getEntity() instanceof PlayerEntity) {

				e.setStrength(kb);
			}
		}
	}

	@SubscribeEvent
	public void onJump(LivingJumpEvent e) {

		if (e.getEntity() != null) {

			if (e.getEntity() instanceof PlayerEntity) {

				UTIL.addMotionY(jumpMotionY);
			}
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent e) {

		if (!UTIL.isGameNull()) {

			if (UTIL.preventCheat()) {

				if (kb < 1.0f) {
					kb = 1.0f;
					UTIL.sendClientMessage("Too low kb counts as a cheat on some servers. Therefor you are only allowed to use higher kb on public servers.");
				}
				if (jumpMotionY > 0.42d) {
					jumpMotionY = 0.42d;
					UTIL.sendClientMessage("Highjumps counts as a cheat on some servers. Therefor you are only allowed to use lower motion on public servers.");
				}
			}
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent e) {

		if (e.getType() == ElementType.TEXT && !UTIL.isDebugInfo()) {

			mc.fontRenderer.drawStringWithShadow("Knockback: " + kb, 1, 1, Color.WHITE.getRGB());
			mc.fontRenderer.drawStringWithShadow("Jumpmotion: " + jumpMotionY, 1, 10, Color.WHITE.getRGB());
		}
	}
}