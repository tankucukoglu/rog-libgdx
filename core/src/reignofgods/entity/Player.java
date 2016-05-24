package reignofgods.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    
    public Player(String fileName) {
        
        walkSheet = new Texture(fileName);
        sprite = new Sprite(walkSheet);
        
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);
        
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
        
        this.x = Gdx.graphics.getWidth();
        this.y = Gdx.graphics.getHeight();
        
        prevDirection = Constants.DOWN;

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
    public void setPrevDirection(int prevDirection) {
        this.prevDirection = prevDirection;
    }
    
    public void dispose() {
        batch.dispose();
        walkSheet.dispose();
    }
    public void move(float x, float y, int direction) {
        
        this.x = x;
        this.y = y;
        
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
}