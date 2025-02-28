package io.github.some_example_name.lwjgl3.EntityManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

public class Enemy extends Entity{
	
	private Texture tex;
	private Rectangle bounds;
	private boolean moveRight=true;
	private boolean enemyDestroyed = false;
	private float minY;
	
	private int score;
	
	public Enemy() {
		
	}
	
	//Create Enemy Entity
	public Enemy(String string, float x,float y, float speed, int health) {
		super(x, y, null, speed, health);
		this.tex = new Texture(string);
		this.minY = 400;
		
	}
	
	//Return Texture
	public Texture getTexture() {
		return tex;
	}

	//Set New Texture
	public void setTexture(Texture tex) {
		this.tex = tex;
	}
	
	//Draw Enemy Sprite and Rectangle
	public void draw(SpriteBatch batch) {
    	batch.begin();
			batch.draw(this.getTexture(), this.getX(), this.getY(), this.getTexture().getWidth(), this.getTexture().getHeight());
		batch.end();
		//Rectangle for Collision Detection
		bounds = new Rectangle(this.getX(),this.getY(),this.getTexture().getWidth(), this.getTexture().getHeight());
    }
	
	//Enemy Movement
	public void movement() {
		
	}
	
	public boolean isMoveRight() {
        return moveRight;
    }
	
	public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }
	
	//Return Rectangle
	public Rectangle getBounds() {
        return bounds;
	}
	
	public int getScore() {
		return score;
	}
	
	//If Enemy is destroyed, set texture to damaged and increase score +1
	public void damage() {
	    if (!enemyDestroyed) {
	        this.setTexture(new Texture("damage.png"));
	        score += 1;
	        enemyDestroyed = true;
	    }
	}
	
	//Returns if enemy is destroyed or not
	public boolean enemyDestroyed() {
		return enemyDestroyed;
		
	}
	
	//Sets texture to enemy and enemy is not destroyed
	public void respawn() {
		this.setTexture(new Texture("Enemy.png"));
		enemyDestroyed=false;

	}

	//Print out the score and and new random respawn point if enemy destroyed
	public void update() {
		if(this.getTexture().toString()=="damage.png") {
			System.out.println("Score: " + score);
			 // Schedule a task to run after a 3 second delay
		    Timer.schedule(new Timer.Task() {
		        @Override
		        public void run() {
		        	setY(700);
		        	minY = (float) (400 + Math.random()*150);
		        	
		        }
		    }, 1); // Delay in seconds

		}

	}
	
	public void dispose() {
		
	}
	
}
