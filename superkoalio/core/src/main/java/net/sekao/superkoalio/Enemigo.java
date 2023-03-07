package net.sekao.superkoalio;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Enemigo extends Koala {
    static float xKoala = 0;
    static float yKoala = 0;
    public Enemigo() {
        super();
        this.xVelocity = -1*MAX_VELOCITY;
        final float width = 18;
        final float height = 26;
        this.setSize(1, height / width);

        Texture koalaTexture = new Texture("enemigo.png");
        TextureRegion[][] grid = TextureRegion.split(koalaTexture, (int) width, (int) height);

        stand = grid[0][0];
        jump = grid[0][1];
        walk = new Animation(0.15f, grid[0][2], grid[0][3], grid[0][4]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }
    
    public void act(float delta) {
        time = time + delta;
        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;
        
        if(y <= 10 ){
            if(xKoala - x > 5 && isFacingRight == false){
                xVelocity =  MAX_VELOCITY; 
                isFacingRight = true;
            }
            if(xKoala - x <= 10 &&xKoala - x >= -10){
                if(isFacingRight == true){
                    xVelocity =  MAX_VELOCITY;
                }else{
                    xVelocity =  -1*MAX_VELOCITY;
                }
            }
            if(xKoala - x < -5 && isFacingRight == true ){
                xVelocity = -1 *MAX_VELOCITY;
                isFacingRight = false;
            }     
            
        }
        yVelocity = yVelocity + GRAVITY;

        if (canMoveTo(x + xChange, y, false) == false) {
            xVelocity = xChange = 0;
        }

        if (canMoveTo(x, y + yChange, yVelocity > 0) == false) {
            canJump = yVelocity < 0;
            yVelocity = yChange = 0;
        }
        if(seTocan()){
            this.remove();
        }

        this.setPosition(x + xChange, y + yChange);

        xVelocity = xVelocity * DAMPING;
        if (Math.abs(xVelocity) < 0.5f) {
            xVelocity = 0;
        }
    }
    static void perseguir(float xKoala, float yKoala){
        Enemigo.xKoala = xKoala; 
        Enemigo.yKoala = yKoala;
    }
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        if (yVelocity != 0) {
            frame = jump;
        } else if (xVelocity != 0) {
            frame = (TextureRegion) walk.getKeyFrame(time);
        } else {
            frame = stand;
        }
        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }
    public boolean seTocan(){
        boolean choque= false;
        int x = (int) this.getX(); 
        int y = (int) this.getY();
        int altura = (int)this.getHeight();
        if(((int)xKoala == x)&&(int)yKoala == y+altura){
            choque = true;
        }
        return choque;
    }
   
    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                /*se que mi enemigo esta tocando algo del mapa comprobando si en la celda en
                la que estoy hay algo con el layer.getCell
                
                Y en el MainScreen tengo que comprobar si mi koala toca al enemigo*/
                if (layer.getCell(x, y) != null) {
                    return false;
                }
               
                y = y + 1;
            }
            x = x + 1;
        }

        return true;
    }
}
