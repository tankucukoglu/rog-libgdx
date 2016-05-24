package reignofgods.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Game extends ApplicationAdapter{
        
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    
    private boolean paused;
    
    @Override
    public void create() {
                
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);
        
        paused = false;
    }

    @Override
    public void render() {
        
        if(!paused) {
            worldController.update(Gdx.graphics.getDeltaTime());
        }
        
        Gdx.gl.glClearColor(100/255.0f, 149/255.0f, 237/255.0f, 255/255.0f);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
        
    }
    
    @Override 
    public void dispose() {
        worldRenderer.dispose();
    }
    @Override
    public void pause() {
        paused = true;
    }
    @Override
    public void resume() {
        paused = false;
    }
}