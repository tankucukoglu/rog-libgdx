
package reignofgods.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import reignofgods.util.Constants;

public class WorldController extends InputAdapter{
    
    private boolean moving = false;
    
    private int direction = -1;
    

    public WorldController() {
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
    }

    public void update(float deltaTime) {
        
    }
    
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT) {
            direction = Constants.LEFT;
            moving = true;
        }
            
        if(keycode == Input.Keys.RIGHT) {
            direction = Constants.RIGHT;
            moving = true;
        }
            
        if(keycode == Input.Keys.UP) {
            direction = Constants.UP;
            moving = true;
        }
            
        if(keycode == Input.Keys.DOWN) {
            direction = Constants.DOWN;
            moving = true;
        }
            
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        
        if(keycode == Input.Keys.LEFT)
            moving = false;        
        if(keycode == Input.Keys.RIGHT)
            moving = false;
        if(keycode == Input.Keys.UP)
            moving = false;
        if(keycode == Input.Keys.DOWN)
            moving = false;
        
        return false;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    public int getDirection() {
        return direction;
    }
    public boolean getMoving() {
        return moving;
    }
}