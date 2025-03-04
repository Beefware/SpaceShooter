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
	private Rectangle bounds;
	private boolean moveRight=true;
	private boolean circleHit = false;
	
	private static int score;

		
	private BitmapFont Font;
	private String questionText;
	private String correctText;
	private String  wrongText;

	
	public Circle() {
		
	}
	
	//Create Circle Entity
	public Circle(Color color, float x,float y, float radius, float speed, int health) {
		super(x, y, color, speed, health);
		
		
		this.radius = radius;
		
		Font = new BitmapFont();
		Font.getData().setScale(3);
		Font.setColor(Color.YELLOW);
		
		randomQuestionGenerator();

		
	}
	
	//Return Circle Radius
	public float getRadius() {
		return radius;
	}

	//Set Circle Radius
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	//Draw Circle Sprite and Rectangle
	public void draw(ShapeRenderer shape) {
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(this.getColor());
		shape.circle(this.getX(), this.getY(), this.getRadius());
		shape.circle(this.getX()+250, this.getY(), this.getRadius());
		shape.end();
		//Rectangle for Collision Detection
		bounds = new Rectangle(this.getX()-this.getRadius(),this.getY()-this.getRadius(),this.getRadius()*2, this.getRadius()*2);
    }
	
	//Draw Circle Sprite and Rectangle
	public void draw(SpriteBatch batch) {
		 batch.begin();
	     GlyphLayout questionLayout = new GlyphLayout(Font, questionText);
	     float questX = (Gdx.graphics.getWidth() - questionLayout.width) / 2;
	     float questY = 600;
	     Font.draw(batch,questionText,questX,questY);
	     
	     GlyphLayout correctLayout = new GlyphLayout(Font, correctText);
	     float correctX = this.getX()-correctLayout.width/2;
	     float correctY = this.getY()+correctLayout.height/2;
	     Font.draw(batch,correctText,correctX,correctY);
	     
	     GlyphLayout wrongLayout = new GlyphLayout(Font, wrongText);
	     float wrongX = this.getX()+250-wrongLayout.width/2;
	     float wrongY = this.getY()+wrongLayout.height/2;
	     Font.draw(batch,wrongText,wrongX,wrongY);
	     batch.end();

    }
	
	public void randomQuestionGenerator() {
		Object[] correctArray = randomCalculator();
		//int correctOption = (int)correctArray[0];
		
		Object[] wrongArray = randomCalculator();
		//int wrongOption = (int)wrongArray[0];
		
		if (wrongArray[0] == correctArray[0]) {
			wrongArray = randomCalculator();
		}
		
		questionText = (String)correctArray[1];
		correctText = String.valueOf((int)correctArray[0]);
		wrongText = String.valueOf((int)wrongArray[0]);

		System.out.println("Question: " + questionText);

		System.out.println("CorrectOption: " + correctText);
		System.out.println("WrongOption: " + wrongText);

	}
	
	public int randomNumber() {
		int randomNumber = MathUtils.random(10);
		System.out.println("Random number: " + randomNumber);
		return randomNumber;
	}
	
	public Object[] randomCalculator() {
		int a = randomNumber();
		int b = randomNumber();
		int operator = MathUtils.random(2);
		int answer;
		String question;
		switch(operator) {
			case 0: 
				answer= a + b;
				question = a + " + " + b;
				break;
			case 1: 
				if(a>b) {
					answer = a - b;
					question = a + " - " + b;
				}else {
					answer = b - a;
					question = b + " - " + a;
				}
				break;
			case 2: 
				answer= a * b;
				question = a + " x " + b;
				break;
			default: 
				answer= a + b;
				question = a + " + " + b;
				break;
		}
		
		Object[] returnArray = new Object[2];
		returnArray[0] = answer;
		returnArray[1] = question;
		
		return returnArray;
	}
	
	
	//Circle Movement
	public void movement() {
		
	}
	
	//Returns if Circle is moving right
	public boolean isMoveRight() {
        return moveRight;
    }
	
	//Sets Circle to move right
	public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }
	
	//Return Rectangle
	public Rectangle getBounds() {
        return bounds;
	}
	
	//Returns Score
	public static int getScore() {
		return score;
	}
	
    public static void resetScore() {
        score = 0;
    }
	
	//If Circle is hit, set Circle colour to Salmon and increase score 
	public void damage() {
	    if (!circleHit) {
	        this.setColor(Color.SALMON);
	        score += 1;
	        randomQuestionGenerator();

	        circleHit = true;
	    }
	}
	
	//Returns if circle is hit or not
	public boolean circleHit() {
		return circleHit;
		
	}
	
	//Sets Circle color back to red and set enemy is not hit.
	public void respawn() {
		this.setColor(Color.RED);
		circleHit=false;

	}

	//Print out the score and and new random respawn point if circle hit
	public void update() {		
		
		
		
		if(this.getColor()==Color.SALMON) {
			setY(700);

//			 // Schedule a task to run after a 3 second delay
//		    Timer.schedule(new Timer.Task() {
//		        @Override
//		        public void run() {
//		        	setY(700);
//		        	
//		        }
//		    }, 1); // Delay in seconds

		}

	}
	
	public void dispose() {
		this.dispose();
	}
	
}
