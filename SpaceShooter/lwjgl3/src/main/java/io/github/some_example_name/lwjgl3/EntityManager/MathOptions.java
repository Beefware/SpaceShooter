package io.github.some_example_name.lwjgl3.EntityManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class MathOptions extends Circle {

    private Texture tex;
    private Rectangle option1Bounds;
    private Rectangle option2Bounds;

    private boolean circleHit = false;
    private boolean moveRight = true;
    private boolean justHitByProjectile = false;

    private static int score;

    private BitmapFont Font;
    private String questionText;
    private String option1Text;
    private String option2Text;
    private boolean option1Correct;
    private boolean option2Correct;
    private int topic;
    
    private static boolean revealAnswer = false; // ✅ New flag

    public MathOptions() {
    }

    public MathOptions(String string, float x, float y, float speed, int health, int topic) {
        super(null, x / 4, y, 0, speed, health);

        this.tex = new Texture(string);
        this.topic = topic;

        Font = new BitmapFont();
        Font.getData().setScale(3);
        Font.setColor(Color.YELLOW);

        randomQuestionGenerator(topic);
        
    }

    public static void setRevealAnswer(boolean reveal) {
        revealAnswer = reveal;
    }

    public Texture getTexture() {
        return tex;
    }

    public void setTexture(Texture tex) {
        this.tex = tex;
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        
        // Draw the math options (textures)
        batch.draw(this.getTexture(), this.getX() - (this.getTexture().getWidth() / 2), 
                   this.getY() - (this.getTexture().getWidth() / 2), 
                   this.getTexture().getWidth(), this.getTexture().getHeight());

        batch.draw(this.getTexture(), this.getX() * 3 - (this.getTexture().getWidth() / 2), 
                   this.getY() - (this.getTexture().getWidth() / 2), 
                   this.getTexture().getWidth(), this.getTexture().getHeight());

        // ✅ Update option bounds for collision detection
        option1Bounds = new Rectangle(this.getX() - (this.getTexture().getWidth() / 2), 
                                      this.getY() - (this.getTexture().getWidth() / 2), 
                                      this.getTexture().getWidth(), 
                                      this.getTexture().getHeight());

        option2Bounds = new Rectangle(this.getX() * 3 - (this.getTexture().getWidth() / 2), 
                                      this.getY() - (this.getTexture().getWidth() / 2), 
                                      this.getTexture().getWidth(), 
                                      this.getTexture().getHeight());

        // ✅ Draw the math question in white
        GlyphLayout questionLayout = new GlyphLayout(Font, questionText);
        float questX = (Gdx.graphics.getWidth() - questionLayout.width) / 2;
        float questY = 600;
        Font.setColor(Color.WHITE); // Set default color for question
        Font.draw(batch, questionText, questX, questY);

        // ✅ Draw option 1 with conditional highlighting
        GlyphLayout option1Layout = new GlyphLayout(Font, option1Text);
        float option1X = this.getX() - option1Layout.width / 2;
        float option1Y = this.getY() + option1Layout.height / 2;

        if (revealAnswer) { 
            if (option1Correct) {
                Font.setColor(Color.GREEN); // ✅ Highlight correct answer when power-up is active
            } else {
                Font.setColor(Color.YELLOW);
            }
        } else {
            Font.setColor(Color.YELLOW); // ✅ Default color when power-up is not active
        }
        Font.draw(batch, option1Text, option1X, option1Y);

        // ✅ Draw option 2 with conditional highlighting
        GlyphLayout option2Layout = new GlyphLayout(Font, option2Text);
        float option2X = this.getX() * 3 - option2Layout.width / 2;
        float option2Y = this.getY() + option2Layout.height / 2;

        if (revealAnswer) {
            if (option2Correct) {
                Font.setColor(Color.GREEN); // ✅ Highlight correct answer when power-up is active
            } else {
                Font.setColor(Color.YELLOW);
            }
        } else {
            Font.setColor(Color.YELLOW); // ✅ Default color when power-up is not active
        }
        Font.draw(batch, option2Text, option2X, option2Y);

        // ✅ Reset font color to default after drawing
        Font.setColor(Color.YELLOW);

        batch.end();
    }


    public void randomQuestionGenerator(int topic) {
        Object[] questionData = null;
        switch (topic) {
            case 1: // Addition
                questionData = generateAdditionQuestion();
                break;
            case 2: // Subtraction
                questionData = generateSubtractionQuestion();
                break;
            case 3: // Multiplication
                questionData = generateMultiplicationQuestion();
                break;
            case 4: // Division
                questionData = generateDivisionQuestion();
                break;
            case 5: // All
                questionData = generateRandomQuestion();
                break;
            default:
                System.err.println("Invalid topic selected.");
                break;
        }

        if (questionData != null) {
            questionText = (String) questionData[1];
            boolean randomize = MathUtils.randomBoolean();
            if (randomize) {
                option1Text = String.valueOf((int) questionData[0]);
                option2Text = generateWrongAnswer((int) questionData[0]);
                option1Correct = true;
                option2Correct = false;
            } else {
                option2Text = String.valueOf((int) questionData[0]);
                option1Text = generateWrongAnswer((int) questionData[0]);
                option1Correct = false;
                option2Correct = true;
            }
        }
    }

    private Object[] generateAdditionQuestion() {
        int a = MathUtils.random(10);
        int b = MathUtils.random(10);
        int answer = a + b;
        String question = a + " + " + b;
        return new Object[]{answer, question};
    }

    private Object[] generateSubtractionQuestion() {
        int a = MathUtils.random(10);
        int b = MathUtils.random(10);
        int answer = Math.max(a, b) - Math.min(a, b);
        String question = Math.max(a, b) + " - " + Math.min(a, b);
        return new Object[]{answer, question};
    }

    private Object[] generateMultiplicationQuestion() {
        int a = MathUtils.random(10);
        int b = MathUtils.random(10);
        int answer = a * b;
        String question = a + " x " + b;
        return new Object[]{answer, question};
    }

    private Object[] generateDivisionQuestion() {
        int a = MathUtils.random(10) + 1;
        int b = MathUtils.random(10) + 1;
        int div = a * b;
        int answer = b;
        String question = div + " / " + a;
        return new Object[]{answer, question};
    }

    private Object[] generateRandomQuestion() {
        int operator = MathUtils.random(3);
        switch (operator) {
            case 0:
                return generateAdditionQuestion();
            case 1:
                return generateSubtractionQuestion();
            case 2:
                return generateMultiplicationQuestion();
            case 3:
                return generateDivisionQuestion();
            default:
                return generateAdditionQuestion();
        }
    }

    private String generateWrongAnswer(int correctAnswer) {
        int wrongAnswer = MathUtils.random(100);
        while (wrongAnswer == correctAnswer) {
            wrongAnswer = MathUtils.random(100);
        }
        return String.valueOf(wrongAnswer);
    }

    public void movement() {
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public Rectangle getBounds1() {
        return option1Bounds;
    }

    public Rectangle getBounds2() {
        return option2Bounds;
    }

    public boolean isOption1() {
        return option1Correct;
    }

    public boolean isOption2() {
        return option2Correct;
    }

    public void difficulty() {
        int speed = Math.floorDiv(score, 3) + 1;
        if (speed > 5) {
            speed = 5;
        }
        this.setSpeed(speed);
    }

    public static int getScore() {
        return score;
    }

    public static void resetScore() {
        score = 0;
    }

    public void damage() {
        if (!circleHit) {
            this.setColor(Color.SALMON);
            score += 1;
            difficulty();
            randomQuestionGenerator(topic);
            circleHit = true;
        }
    }

    public void hitBorder() {
        if (!circleHit) {
            this.setColor(Color.SALMON);
            randomQuestionGenerator(topic);
            circleHit = true;
        }
    }

    public boolean circleHit() {
        return circleHit;
    }
		
		//Sets Circle color back to red and set enemy is not hit.
		public void respawn() {
			this.setColor(Color.RED);
			circleHit=false;

		}
		
		public void setJustHitByProjectile(boolean value) {
		    this.justHitByProjectile = value;
		}

		public boolean isJustHitByProjectile() {
		    return justHitByProjectile;
		}

		
		//Print out the score and and new random respawn point if circle hit
		public void update() {		
			
			
			
			if(this.getColor()==Color.SALMON) {
				setY(700);

//				 // Schedule a task to run after a 3 second delay
//			    Timer.schedule(new Timer.Task() {
//			        @Override
//			        public void run() {
//			        	setY(700);
//			        	
//			        }
//			    }, 1); // Delay in seconds

			}

		}
		
		public void dispose() {
			this.dispose();
		}
	
}
