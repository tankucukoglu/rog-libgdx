package reignofgods.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import reignofgods.util.Constants;

public class Player {
    
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 4;
    
    private final Sprite sprite;
    private final SpriteBatch batch;
    
    private final Animation walkAnimationUp;
    private final Animation walkAnimationRight;
    private final Animation walkAnimationDown;
    private final Animation walkAnimationLeft;
    private final Texture walkSheet;
    private final TextureRegion[] walkFramesUp;
    private final TextureRegion[] walkFramesRight;
    private final TextureRegion[] walkFramesDown;
    private final TextureRegion[] walkFramesLeft;
    private TextureRegion currentFrame;
    
    private float stateTime;
    
    private float x;
    private float y;
    
    private int prevDirection;
    
    private final Rectangle rectangle;
    
    public Player(String fileName, float x, float y) {
        
        walkSheet = new Texture(fileName);
        sprite = new Sprite(walkSheet);
        
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, 
                                                               walkSheet.getHeight() / FRAME_ROWS);
        
        walkFramesUp = new TextureRegion[FRAME_COLS];
        walkFramesRight = new TextureRegion[FRAME_COLS];
        walkFramesDown = new TextureRegion[FRAME_COLS];
        walkFramesLeft = new TextureRegion[FRAME_COLS];
        
        int index = 0;
        for(int i = 0; i < FRAME_COLS; i++) {
            walkFramesUp[index++] = tmp[0][i];
        }
        
        index = 0;
        for(int i = 0; i < FRAME_COLS; i++) {
            walkFramesRight[index++] = tmp[1][i];
        }
        
        index = 0;
        for(int i = 0; i < FRAME_COLS; i++) {
                walkFramesDown[index++] = tmp[2][i];
        }
        
        index = 0;
        for(int i = 0; i < FRAME_COLS; i++) {
            walkFramesLeft[index++] = tmp[3][i];
        }
        
        walkAnimationUp = new Animation(Constants.WALK_SPEED, walkFramesUp);
        walkAnimationRight = new Animation(Constants.WALK_SPEED, walkFramesRight);
        walkAnimationDown = new Animation(Constants.WALK_SPEED, walkFramesDown);
        walkAnimationLeft = new Animation(Constants.WALK_SPEED, walkFramesLeft);
        
        batch = new SpriteBatch();
        
        stateTime = 0f;
        
        this.x = x;
        this.y = y;
        
        prevDirection = Constants.DOWN;
        
        rectangle = new Rectangle();
        rectangle.height = Constants.TILE_SIZE;
        rectangle.width = Constants.TILE_SIZE;
        rectangle.x = x;
        rectangle.y = y;

    }
    public void move(float x, float y, int direction) {
        
        this.x = x;
        this.y = y;
        rectangle.x = x;
        rectangle.y = y;
        
        stateTime += Gdx.graphics.getDeltaTime();
        
        if(direction == Constants.LEFT) {
            currentFrame = walkAnimationLeft.getKeyFrame(stateTime, true);
        }
        if(direction == Constants.RIGHT) {
            currentFrame = walkAnimationRight.getKeyFrame(stateTime, true);    
        }
        if(direction == Constants.UP) {
            currentFrame = walkAnimationUp.getKeyFrame(stateTime, true);
        }
        if(direction == Constants.DOWN) {
            currentFrame = walkAnimationDown.getKeyFrame(stateTime, true);
        }
        if(direction == Constants.IDLE) {
            if(prevDirection == Constants.LEFT) {
                currentFrame = walkFramesLeft[1];
            }
            if(prevDirection == Constants.RIGHT) {
                currentFrame = walkFramesRight[1];
            }
            if(prevDirection == Constants.UP) {
                currentFrame = walkFramesUp[1];
            }
            if(prevDirection == Constants.DOWN) {
                currentFrame = walkFramesDown[1];
            }
        }
        
        batch.enableBlending();
        
        batch.begin();
        batch.draw(currentFrame, x, y);
        batch.end();
    }
    
    public Texture getTexture() {
        return walkSheet;
    }
    public Sprite getSprite() {
        return sprite;
    }
    public SpriteBatch getSpriteBatch() {
        return batch;
    }
    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }
    public float getStateTime() {
        return stateTime;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }    
    public int getPrevDirection() {
        return prevDirection;
    }
    public Rectangle getRectangle() {
        return rectangle;
    }
    public float getNextX(int direction) {
        if(direction == Constants.LEFT) {
            return x - Constants.TILE_SIZE;
        }
        if(direction == Constants.RIGHT) {
            return x + Constants.TILE_SIZE;
        }
        return x;
    }
    public float getNextY(int direction) {
        if(direction == Constants.UP) {
            return y + Constants.TILE_SIZE;
        }
        if(direction == Constants.DOWN) {
            return y - Constants.TILE_SIZE;
        }
        return y;
    }
    
    public void setPrevDirection(int prevDirection) {
        this.prevDirection = prevDirection;
    }
    public void setX(float x) {
        this.x  = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    
    public void dispose() {
        batch.dispose();
        walkSheet.dispose();
    }
}