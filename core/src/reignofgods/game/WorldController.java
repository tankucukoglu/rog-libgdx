package reignofgods.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import reignofgods.util.Constants;

public class WorldController extends InputAdapter {
    
    private boolean moving;
    private boolean targetReached;
    private boolean playerMovement;
    
    private int direction = Constants.IDLE;
    
    public WorldController() {
        init();
    }
    private void init() {
        moving = false;
        targetReached = true;
        playerMovement = true;
        Gdx.input.setInputProcessor(this);
    }
    public void update(float deltaTime) {
        
    }
    
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT && targetReached) {
            direction = Constants.LEFT;
            moving = true;
        }
        if(keycode == Input.Keys.RIGHT && targetReached) {
            direction = Constants.RIGHT;
            moving = true;
        }
        if(keycode == Input.Keys.UP && targetReached) {
            direction = Constants.UP;
            moving = true;
        }
        if(keycode == Input.Keys.DOWN && targetReached) {
            direction = Constants.DOWN;
            moving = true;
        }
//        if(keycode == Input.Keys.SPACE) {
//            
//            if(playerMovement) {
//                playerMovement = false;
//            }
//            else {
//                playerMovement = true;
//            }
//        }
        
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
    public void setTargetReached(boolean targetReached) {
        this.targetReached = targetReached;
    }
    public int getDirection() {
        return direction;
    }
    public boolean getMoving() {
        return moving;
    }
    public boolean getPlayerMovement() {
        return playerMovement;
    }
}