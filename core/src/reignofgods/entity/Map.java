package reignofgods.entity;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import reignofgods.util.Constants;

public class Map {
    
    private final TiledMap tiledMap;
    private final TiledMapRenderer tiledMapRenderer;
    private final MapLayers mapLayers;
    
    private final int mapWidth;
    private final int mapHeight;
    
    public Map(String fileName) {
        
        tiledMap = new TmxMapLoader().load(fileName);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        mapWidth = tiledMap.getProperties().get("width", Integer.class);
        mapHeight = tiledMap.getProperties().get("height", Integer.class);
        
        mapLayers = tiledMap.getLayers();
    }
    
    public int getWidth() {
        return mapWidth * Constants.TILE_SIZE;
    }
    public int getHeight() {
        return mapHeight * Constants.TILE_SIZE;
    }
    public TiledMap getTiledMap() {
        return tiledMap;
    }
    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }
    public MapLayers getMapLayers() {
        return mapLayers;
    }
}