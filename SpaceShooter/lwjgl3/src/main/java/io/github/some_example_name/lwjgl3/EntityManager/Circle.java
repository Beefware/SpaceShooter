package io.github.some_example_name.lwjgl3.EntityManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.math.MathUtils;

public class Circle extends Entity{
	
	private float radius;	
	
	public Circle() {
		
	}
	
	//Create Circle Entity
	public Circle(Color color, float x,float y, float radius, float speed, int health) {
		super(x, y, color, speed, health);
		
		this.radius = radius;
	}
	
	//Return Circle Radius
	public float getRadius() {
		return radius;
	}

	//Set Circle Radius
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public Rectangle getBounds() {
	    return new Rectangle(getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
	}

	//Draw Circle Sprite and Rectangle
	public void draw(ShapeRenderer shape) {
		

    }
	
	//Draw Circle Sprite and Rectangle
	public void draw(SpriteBatch batch) {
		
    }
	
	
	//Circle Movement
	public void movement() {
		
	}
	

	//Print out the score and and new random respawn point if circle hit
	public void update() {		
		
		
	}
	
	public void dispose() {
		this.dispose();
	}
	
}
