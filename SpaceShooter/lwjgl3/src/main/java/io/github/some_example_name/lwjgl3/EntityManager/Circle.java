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
	private Rectangle option1Bounds;
	private Rectangle option2Bounds;

	private boolean circleHit = false;
	
	private static int score;

		
	private BitmapFont Font;
	private String questionText;
	private String option1Text;
	private String  option2Text;
	private boolean option1Correct;
	private boolean option2Correct;


	
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
		//Rectangles for Collision Detection
		option1Bounds = new Rectangle(this.getX()-this.getRadius(),this.getY()-this.getRadius(),this.getRadius()*2, this.getRadius()*2);
		option2Bounds = new Rectangle(this.getX()+250-this.getRadius(),this.getY()-this.getRadius(),this.getRadius()*2, this.getRadius()*2);

    }
	
	//Draw Circle Sprite and Rectangle
	public void draw(SpriteBatch batch) {
		 batch.begin();
	     GlyphLayout questionLayout = new GlyphLayout(Font, questionText);
	     float questX = (Gdx.graphics.getWidth() - questionLayout.width) / 2;
	     float questY = 600;
	     Font.draw(batch,questionText,questX,questY);
	     
	     GlyphLayout option1Layout = new GlyphLayout(Font, option1Text);
	     float option1X = this.getX()-option1Layout.width/2;
	     float option1Y = this.getY()+option1Layout.height/2;
	     Font.draw(batch,option1Text,option1X,option1Y);
	     
	     GlyphLayout option2Layout = new GlyphLayout(Font, option2Text);
	     float option2X = this.getX()+250-option2Layout.width/2;
	     float option2Y = this.getY()+option2Layout.height/2;
	     Font.draw(batch,option2Text,option2X,option2Y);
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

		boolean randomise = MathUtils.randomBoolean();
		if(randomise) {
			option1Text = String.valueOf((int)correctArray[0]);
			option2Text = String.valueOf((int)wrongArray[0]);
			option1Correct = true;
			option2Correct = false;

		}else {
			option1Text = String.valueOf((int)wrongArray[0]);
			option2Text = String.valueOf((int)correctArray[0]);
			option1Correct = false;
			option2Correct = true;
		}

		System.out.println("Question: " + questionText);
	    System.out.println("Option1: " + option1Text);
	    System.out.println("Option2: " + option2Text);
	    
	}

	public Object[] randomCalculator() {
		int a = MathUtils.random(10);
		int b = MathUtils.random(10);
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
	
	
	//Return Rectangle
	public Rectangle getBounds1() {
		
        return option1Bounds;
        
	}
	
	//Return Rectangle
	public Rectangle getBounds2() {

        return option2Bounds;
	}
	
	public boolean isOption1() {
		return option1Correct;
	}
	
	public boolean isOption2() {
		return option2Correct;
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
	
	public void hitBorder() {
		 if (!circleHit) {
		        this.setColor(Color.SALMON);
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
