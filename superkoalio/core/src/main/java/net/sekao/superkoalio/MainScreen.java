package net.sekao.superkoalio;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;

public class MainScreen implements Screen {
    
    Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Koala koala;
    Enemigo enemigo;
    Monedas moneda;
    ArrayList<Enemigo> enemigos;
    ArrayList<Monedas> monedas;
    long ultimoEnemigoCreado;
    long ultimaMonedaCreada;
    int vidasKoala;
    SpriteBatch batch;
    Rectangle enemigoRectangulo;
    Rectangle koalaRectangulo;
    Rectangle monedaRectangulo;
      
    public void show() {
        vidasKoala = 3;
        ultimoEnemigoCreado = TimeUtils.nanoTime();
        ultimaMonedaCreada = TimeUtils.nanoTime();
        enemigos = new ArrayList<Enemigo>();
        monedas = new ArrayList<Monedas>();
        map = new TmxMapLoader().load("level1.tmx");
        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();
        
        stage = new Stage();
        stage.getViewport().setCamera(camera);
        
        koala = new Koala();
        koala.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        koala.setPosition(20, 10);
        stage.addActor(koala);
        
    }  
    public void spawnMonedas(float x, float y){
        moneda = new Monedas();
        monedas.add(moneda);
        moneda.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        moneda.setPosition(x, y);
        ultimaMonedaCreada = TimeUtils.nanoTime();
    }
    
    public void crearEnemigos(float x, float y){
        enemigo = new Enemigo();
        enemigos.add(enemigo);
        enemigo.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        enemigo.setPosition(x, y);
        ultimoEnemigoCreado = TimeUtils.nanoTime();
    }
    public boolean loMata(){
        boolean seToca = false;
        enemigoRectangulo = new Rectangle(enemigo.getX(), enemigo.getY(), enemigo.getWidth(),enemigo.getHeight());
        koalaRectangulo = new Rectangle(koala.getX(), koala.getY(), koala.getWidth(), koala.getHeight());
        if(koalaRectangulo.overlaps(enemigoRectangulo)){
            seToca = true;
        }
        return seToca;
    }
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.x = koala.getX();
        camera.update();

        renderer.setView(camera);
        
        renderer.render();
        if (TimeUtils.nanoTime() - ultimoEnemigoCreado > 2000000000){
            crearEnemigos(koala.getX()+10, 10);
                stage.addActor(enemigo);
        }
        if(TimeUtils.nanoTime() - ultimaMonedaCreada > 2000000000){
            spawnMonedas(koala.getX()+10, koala.getY());
            
            stage.addActor(moneda);
        }
        if(vidasKoala>4 && loMata()){
            vidasKoala -=1;
        }else if(vidasKoala == 0 && loMata()){
            koala.remove();            
        }
        
        stage.act(delta);
        stage.draw();
        enemigo.perseguir( koala.getX(), koala.getY());
    }

    public void dispose() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, 20 * width / height, 20);
    }

    public void resume() {
    }

    
}
