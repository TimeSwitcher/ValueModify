package me.timeswitcher.valuemodify;

import net.minecraft.util.text.StringTextComponent;

public class Util {

	public boolean isGameNull() {
		return ValueModify.mc.player == null || ValueModify.mc.world == null;
	}

	public void addMotionY(double motionY) {
		ValueModify.mc.player.setMotion(ValueModify.mc.player.getMotion().getX(), motionY, ValueModify.mc.player.getMotion().getZ());
	}

	public void sendClientMessage(String message) {
		ValueModify.mc.player.sendMessage(new StringTextComponent("[ValueModify] " + message));
	}

	public boolean isDebugInfo() {
		return ValueModify.mc.gameSettings.showDebugInfo;
	}

	public boolean ownServer() {
		return ValueModify.mc.getCurrentServerData().isOnLAN() || ValueModify.mc.isSingleplayer() || ValueModify.mc.getCurrentServerData().serverIP.equalsIgnoreCase("localhost") || ValueModify.mc.getCurrentServerData().serverIP.equalsIgnoreCase("127.0.0.1");
	}

	public boolean preventCheat() {
		return ValueModify.mc.getCurrentServerData() != null ? (ownServer() ? false : true) : false;
	}
}