/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.sekao.superkoalio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author HUAWEI
 */
public class Monedas extends Koala{
    static float xKoala = 0;
    static float yKoala = 0;
    public static final float MONEDA_WIDTH = 20;          
    public static final float MONEDA_HEIGHT = 32;
    public static final int MONEDA_SCORE = 10;
    
    
     public Monedas(){
        this.xVelocity = 0;
        this.setSize(1, MONEDA_HEIGHT / MONEDA_WIDTH);
        Texture monedaTextura = new Texture("monedas.png");
        TextureRegion[][] grid = TextureRegion.split(monedaTextura, (int) MONEDA_WIDTH, (int) MONEDA_HEIGHT);

        stand = grid[0][0];
        jump = grid[0][1];
        walk = new Animation(0.15f, grid[0][2], grid[0][3], grid[0][4]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
     }
    
    
    public void act(float delta){
        time = time + delta;
        if(seTocan()){
            this.remove();
        }
    }
    
    public boolean seTocan(){
        boolean choque= false;
        int x = (int) this.getX(); 
        int y = (int) this.getY();
        if(((int)xKoala == x && (int)yKoala == y)){
            choque = true;
        }
        return choque;
    }
   
}
