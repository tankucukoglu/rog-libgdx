package reignofgods.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import reignofgods.entity.Map;
import reignofgods.entity.Player;
import reignofgods.util.Constants;

public class WorldRenderer implements Disposable {

    private OrthographicCamera camera;
    private final WorldController worldController;
    
    private Map map;
    private Player player;
    
    private MapLayer collisionLayer;
    private MapObjects collisionObjects;
    
    private Rectangle rectangle;
    private boolean targetReached;
    
    private float x;
    private float y;
    
    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        camera.update();
        
        map = new Map("map3.tmx");
        player = new Player("warrior_m.png", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        collisionLayer = map.getMapLayers().get("Collision");
        collisionObjects = collisionLayer.getObjects();
        
        targetReached = true;
    }

    public void render() {
                    
        map.getTiledMapRenderer().setView(camera);
        map.getTiledMapRenderer().render();
        
//        if(worldController.getPlayerMovement()) {
//            movePlayer();
//        }
//        else {
//            movePlayerFree(worldController.getDirection());
//        }
        
        movePlayer();
        
        //detectCollision();
        
        moveCamera();
        
    }
    
    // tile by tile player movement
    
    private void movePlayer() {
        
        if(worldController.getDirection() == Constants.LEFT) {
            if(targetReached) {
                x = player.getNextX(Constants.LEFT);
                targetReached = false;
            }
            if(player.getX() > x) {
                player.move(player.getX() - Constants.TILE_SIZE * Constants.PLAYER_SPEED, player.getY(), Constants.LEFT);
                targetReached = false;
                worldController.setTargetReached(targetReached);
            }
            if(player.getX() < x) {
                
                player.setX(x);
                
                targetReached = true;
                worldController.setTargetReached(targetReached);
                if(!worldController.getMoving()) {
                    worldController.setDirection(Constants.IDLE);
                }
                player.setPrevDirection(Constants.LEFT);
            }
        }
        if(worldController.getDirection() == Constants.RIGHT) {
            if(targetReached) {
                x = player.getNextX(Constants.RIGHT);
                targetReached = false;
            }
            if(player.getX() < x) {
                player.move(player.getX() + Constants.TILE_SIZE * Constants.PLAYER_SPEED, player.getY(), Constants.RIGHT);
                targetReached = false;
                worldController.setTargetReached(targetReached);
            }
            if(player.getX() > x) {
                
                player.setX(x);
                
                targetReached = true;
                worldController.setTargetReached(targetReached);
                if(!worldController.getMoving()) {
                    worldController.setDirection(Constants.IDLE);
                }
                player.setPrevDirection(Constants.RIGHT);
            }
        }
        if(worldController.getDirection() == Constants.UP) {
            if(targetReached) {
                y = player.getNextY(Constants.UP);
                targetReached = false;
            }
            if(player.getY() < y) {
                player.move(player.getX(), player.getY() + Constants.TILE_SIZE * Constants.PLAYER_SPEED, Constants.UP);
                targetReached = false;
                worldController.setTargetReached(targetReached);
            }
            if(player.getY() > y) {
                
                player.setY(y);
                
                targetReached = true;
                worldController.setTargetReached(targetReached);
                if(!worldController.getMoving()) {
                    worldController.setDirection(Constants.IDLE);
                }
                player.setPrevDirection(Constants.UP);
            }
        }
        if(worldController.getDirection() == Constants.DOWN) {
            if(targetReached) {
                y = player.getNextY(Constants.DOWN);
                targetReached = false;
            }
            if(player.getY() > y) {
                player.move(player.getX(), player.getY() - Constants.TILE_SIZE * Constants.PLAYER_SPEED, Constants.DOWN);
                targetReached = false;
                worldController.setTargetReached(targetReached);
            }
            if(player.getY() < y) {
                
                player.setY(y);
                
                targetReached = true;
                worldController.setTargetReached(targetReached);
                if(!worldController.getMoving()) {
                    worldController.setDirection(Constants.IDLE);
                }
                player.setPrevDirection(Constants.DOWN);
            }
        }
        
        if(player.getX() > map.getWidth() - Constants.MAP_OFFSET) {
            player.move(map.getWidth() - Constants.MAP_OFFSET, player.getY(), player.getPrevDirection());
            worldController.setDirection(Constants.IDLE);
            player.setPrevDirection(Constants.RIGHT);
            worldController.setTargetReached(true);
        }
        if(player.getX() < 0) {
            player.move(0, player.getY(), player.getPrevDirection());
            worldController.setDirection(Constants.IDLE);
            player.setPrevDirection(Constants.LEFT);
            worldController.setTargetReached(true);
        }
        if(player.getY() > map.getHeight() - Constants.MAP_OFFSET) {
            player.move(player.getX(), map.getHeight() - Constants.MAP_OFFSET, player.getPrevDirection());
            worldController.setDirection(Constants.IDLE);
            player.setPrevDirection(Constants.UP);
            worldController.setTargetReached(true);
        }
        if(player.getY() < 0) {
            player.move(player.getX(), 0, player.getPrevDirection());
            worldController.setDirection(Constants.IDLE);
            player.setPrevDirection(Constants.DOWN);
            worldController.setTargetReached(true);
        }
        
        if(worldController.getDirection() == Constants.IDLE) {
            player.move(player.getX(), player.getY(), Constants.IDLE);
        }
    }
    
    // free player movement
    
    private void movePlayerFree(int direction) {
        
        worldController.setTargetReached(true);
        
        if(direction == Constants.LEFT) {
            player.move(player.getX() - Constants.TILE_SIZE * Constants.PLAYER_SPEED, player.getY(), Constants.LEFT);
            player.setPrevDirection(Constants.LEFT);
        }
        if(direction == Constants.RIGHT) {
            player.move(player.getX() + Constants.TILE_SIZE * Constants.PLAYER_SPEED, player.getY(), Constants.RIGHT);
            player.setPrevDirection(Constants.RIGHT);
        }
        if(direction == Constants.UP) {
            player.move(player.getX(), player.getY() + Constants.TILE_SIZE * Constants.PLAYER_SPEED, Constants.UP);
            player.setPrevDirection(Constants.UP);
        }
        if(direction == Constants.DOWN) {
            player.move(player.getX(), player.getY() - Constants.TILE_SIZE * Constants.PLAYER_SPEED, Constants.DOWN);
            player.setPrevDirection(Constants.DOWN);
        }
        if(direction == Constants.IDLE) {
            player.move(player.getX(), player.getY(), Constants.IDLE);
        }
        if(player.getX() > map.getWidth() - Constants.MAP_OFFSET) {
            player.move(map.getWidth() - Constants.MAP_OFFSET, player.getY(), player.getPrevDirection());
        }
        if(player.getX() < 0) {
            player.move(0f, player.getY(), player.getPrevDirection());
        }
        if(player.getY() > map.getHeight() - Constants.MAP_OFFSET) {
            player.move(player.getX(), map.getHeight() - Constants.MAP_OFFSET, player.getPrevDirection());
        }
        if(player.getY() < 0) {
            player.move(player.getX(), 0f, player.getPrevDirection());
        }
        
        if(!worldController.getMoving()) {
            worldController.setDirection(Constants.IDLE);
        }
    }
    
    private void moveCamera() {
        
        player.getSpriteBatch().setProjectionMatrix(camera.combined);
        
        camera.position.set(player.getX(), player.getY(), 0);       
        boundCamera();
        
        camera.update();
    }
    private void boundCamera() {
        
        if(camera.position.x + (camera.viewportWidth / 2) > map.getWidth()) {
            camera.position.x = map.getWidth() - (camera.viewportWidth / 2);
        }
        if(camera.position.x - (camera.viewportWidth / 2) < 0) {
            camera.position.x = camera.viewportWidth / 2;
        }
        if(camera.position.y + (camera.viewportHeight / 2) > map.getHeight()) {
            camera.position.y = map.getHeight() - (camera.viewportHeight / 2);
        }
        if(camera.position.y - (camera.viewportHeight / 2) < 0) {
            camera.position.y = camera.viewportHeight / 2;
        }
    }
    private void detectCollision() { // need to fix
        
        for(RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
            rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, player.getRectangle())) {
                if(rectangle.x + rectangle.width < player.getX() && player.getPrevDirection() == Constants.LEFT) {
                    System.out.println("left collide");
                    player.move(rectangle.x + rectangle.width - Constants.MAP_OFFSET + Constants.TILE_SIZE, player.getY(), Constants.LEFT);
                    worldController.setDirection(Constants.IDLE);
                    player.setPrevDirection(Constants.LEFT);
                }
                if(rectangle.x > player.getX() && player.getPrevDirection() == Constants.RIGHT) {
                    System.out.println("right collide");
                    player.move(rectangle.x - Constants.TILE_SIZE, player.getY(), Constants.RIGHT);
                    worldController.setDirection(Constants.IDLE);
                    player.setPrevDirection(Constants.RIGHT);
                }
                if(rectangle.y > player.getY() && player.getPrevDirection() == Constants.UP) {
                    System.out.println("up collide");
                    player.move(player.getX(), rectangle.y - Constants.TILE_SIZE, Constants.UP);
                    worldController.setDirection(Constants.IDLE);
                    player.setPrevDirection(Constants.UP);
                }
                if(rectangle.y + rectangle.height < player.getY() && player.getPrevDirection() == Constants.DOWN) {
                    System.out.println("down collide");
                    player.move(player.getX(), rectangle.y + rectangle.height - Constants.MAP_OFFSET + Constants.TILE_SIZE, Constants.DOWN);
                    worldController.setDirection(Constants.IDLE);
                    player.setPrevDirection(Constants.DOWN);
                }
            }
        }
    }
    
    @Override
    public void dispose() {
        player.dispose();
    }
}