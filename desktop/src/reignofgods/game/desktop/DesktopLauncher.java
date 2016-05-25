package reignofgods.game.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import reignofgods.game.Game;
import reignofgods.util.Constants;

public class DesktopLauncher {
    public static void main (String[] arg) {
        
	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        
        config.width = Constants.WINDOW_WIDTH;
        config.height = Constants.WINDOW_HEIGHT;
        config.title = "Reign of Gods";
        
        LwjglApplication lwjglApplication = new LwjglApplication(new Game(), config);
        lwjglApplication.setLogLevel(Application.LOG_DEBUG);
    }
}