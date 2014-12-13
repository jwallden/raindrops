package com.joakimwallden.raindrops.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.joakimwallden.raindrops.RaindropsGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(800, 400);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new RaindropsGame();
        }
}