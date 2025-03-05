package io.github.some_example_name.lwjgl3.EntityManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Triangle extends Entity{
	private Texture tex;
	private Rectangle bounds;
	private boolean isHit = false;
	private boolean gameOver = false;
	
	public Triangle() {
		
	}
	
	public Triangle(String string, float x,float y, float speed, int health) {
		//Create Player Entity
		super(x, y, null, speed, health);
		tex = new Texture(string);
		
	}
	
	//Return Player Texture
	public Texture getTexture() {
		return tex;
	}
	
	//Set Player Texture
	public void setTexture(Texture tex) {
		this.tex = tex;
	}


	
	//Return Rectangle
	public Rectangle getBounds() {
        return bounds;
	}
	

	//Minus i health if triangle takes damage (Triangle and CircleProjectile collision)
	public void damage() {
		this.setHealth(getHealth()-1);
		this.setTexture(new Texture("PlayerHealing.png"));
		isHit = true;
	}
	
	//Return isHit if Collision 
	public boolean isDamaged() {
		return isHit;
	}
	
	//Ensure bullet collision is only detected once
	public void resetDamageFlag() {
		this.setTexture(new Texture("Player.png"));
		isHit = false;
	}
	
	//Set Game Over
	public boolean isGameOver() {
		return gameOver;
	}
	
	
	//Draw Textures
	public void draw(SpriteBatch batch) {
		batch.begin();
		//Draw one health icon for every player health.
		for(int i=0; i<this.getHealth();i++) {
			batch.draw(this.getTexture(), 20+i*50,600, this.getTexture().getWidth()/3, this.getTexture().getHeight()/3);
	
		}
		//Draw player
		batch.draw(this.getTexture(), this.getX(), this.getY(), this.getTexture().getWidth(), this.getTexture().getHeight());
		batch.end();
		
		//Rectangle of Player for Collision Detection
		bounds = new Rectangle(this.getX(),this.getY(),this.getTexture().getWidth(), this.getTexture().getHeight());


    }
	
	public void movement() {
	}
	

	public void update() {
		//If Player Health less than 0, damaged texture and GameOver.
		if (this.getHealth()<1) {
			this.setTexture(new Texture("damage.png"));
			gameOver = true;
		}
		
	}
	
	public void dispose() {
		this.dispose();
	}
	
}
