package com.joakimwallden.raindrops.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.joakimwallden.raindrops.RaindropsGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Raindrops";
		config.width = 800;
		config.height = 400;
		new LwjglApplication(new RaindropsGame(), config);
	}
}
