package reignofgods.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import reignofgods.entity.Map;
import reignofgods.entity.Player;
import reignofgods.util.Constants;

public class WorldRenderer implements Disposable {

    private OrthographicCamera camera;
    private final WorldController worldController;
    
    private Map map;
    private Player player;
                    
    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        camera.update();
        
        map = new Map("map3.tmx");
        player = new Player("warrior_m.png");
                                
    }

    public void render() {
        
        map.getTiledMapRenderer().setView(camera);
        map.getTiledMapRenderer().render();
                
        movePlayer(worldController.getDirection());        
        moveCamera();
        
        System.out.println(player.getY() + " " + map.getHeight());
                
    }

    @Override
    public void dispose() {
        player.dispose();
    }
    
    private void movePlayer(int direction) {
        
        if(direction == Constants.LEFT) {
            player.move(player.getX() - Constants.TILE_SIZE * Constants.CAMERA_SPEED, player.getY(), Constants.LEFT);
            player.setPrevDirection(Constants.LEFT);
        }
        if(direction == Constants.RIGHT) {
            player.move(player.getX() + Constants.TILE_SIZE * Constants.CAMERA_SPEED, player.getY(), Constants.RIGHT);
            player.setPrevDirection(Constants.RIGHT);
        }
        if(direction == Constants.UP) {
            player.move(player.getX(), player.getY() + Constants.TILE_SIZE * Constants.CAMERA_SPEED, Constants.UP);
            player.setPrevDirection(Constants.UP);
        }
        if(direction == Constants.DOWN) {
            player.move(player.getX(), player.getY() - Constants.TILE_SIZE * Constants.CAMERA_SPEED, Constants.DOWN);
            player.setPrevDirection(Constants.DOWN);
        }
        if(direction == Constants.IDLE) {
            player.move(player.getX(), player.getY(), Constants.IDLE);
        }
        if(player.getX() > map.getWidth() - 30) {
            player.move(map.getWidth() - 30, player.getY(), player.getPrevDirection());
        }
        if(player.getX() < 0) {
            player.move(0f, player.getY(), player.getPrevDirection());
        }
        if(player.getY() > map.getHeight() - 30) {
            player.move(player.getX(), map.getHeight() - 30, player.getPrevDirection());
        }
        if(player.getY() < 0) {
            player.move(player.getX(), 0f, player.getPrevDirection());
        }
        
        if(!worldController.getMoving()) {
            worldController.setDirection(Constants.IDLE);
        }
    }
    private void moveCamera() {
        
        player.getSpriteBatch().setProjectionMatrix(camera.combined); //***
        
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
}