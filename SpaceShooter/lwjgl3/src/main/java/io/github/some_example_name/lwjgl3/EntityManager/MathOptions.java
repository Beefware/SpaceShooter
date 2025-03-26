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

    // Texture for the math options
    private Texture tex;

    // Rectangles for collision detection of the two options
    private Rectangle option1Bounds;
    private Rectangle option2Bounds;

    // Flags for various states
    private boolean circleHit = false;
    private boolean movement = false;
    private boolean moveRight = false;

    // Static score variable
    private static int score;

    // Font for rendering text
    private BitmapFont Font;

    // Question and option-related variables
    private String questionText;
    private String option1Text;
    private float option1X;
    private boolean option1Correct;
    private String option2Text;
    private float option2X;
    private boolean option2Correct;

    // Topic for the type of math question
    private int topic;

    // Flag to reveal the correct answer
    private static boolean revealAnswer = false;

    // Default constructor
    public MathOptions() {
    }

    // Constructor with parameters
    public MathOptions(String string, float speed, int health, int topic) {
        super(null, Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() + 20, 0, speed, health);

        this.tex = new Texture(string);
        this.topic = topic;

        // Calculate positions for the options
        option1X = this.getX() - tex.getWidth() / 2;
        option2X = this.getX() * 3 - tex.getWidth() / 2;

        // Initialize font
        Font = new BitmapFont();
        Font.getData().setScale(3);
        Font.setColor(Color.YELLOW);

        // Generate a random question based on the topic
        randomQuestionGenerator(topic);
    }

    // Setter for revealAnswer flag
    public static void setRevealAnswer(boolean reveal) {
        revealAnswer = reveal;
    }

    // Getter for texture
    public Texture getTexture() {
        return tex;
    }

    // Setter for texture
    public void setTexture(Texture tex) {
        this.tex = tex;
    }

    // Getter and setter for option1X
    public float getOption1X() {
        return option1X;
    }

    public void setOption1x(float x) {
        this.option1X = x;
    }

    // Getter and setter for option2X
    public float getOption2X() {
        return option2X;
    }

    public void setOption2x(float x) {
        this.option2X = x;
    }

    // Draw method to render the math options and question
    public void draw(SpriteBatch batch) {
        batch.begin();

        // Draw the math options (textures)
        batch.draw(this.getTexture(), option1X,
                this.getY() - (this.getTexture().getWidth() / 2),
                this.getTexture().getWidth(), this.getTexture().getHeight());

        batch.draw(this.getTexture(), option2X,
                this.getY() - (this.getTexture().getWidth() / 2),
                this.getTexture().getWidth(), this.getTexture().getHeight());

        // Update option bounds for collision detection
        option1Bounds = new Rectangle(option1X,
                this.getY() - (this.getTexture().getWidth() / 2),
                this.getTexture().getWidth(),
                this.getTexture().getHeight());

        option2Bounds = new Rectangle(option2X,
                this.getY() - (this.getTexture().getWidth() / 2),
                this.getTexture().getWidth(),
                this.getTexture().getHeight());

        // Draw the math question in white
        GlyphLayout questionLayout = new GlyphLayout(Font, questionText);
        float questX = (Gdx.graphics.getWidth() - questionLayout.width) / 2;
        float questY = 550;
        Font.setColor(Color.WHITE); // Set default color for question
        Font.draw(batch, questionText, questX, questY);

        // Draw option 1 with conditional highlighting
        GlyphLayout option1Layout = new GlyphLayout(Font, option1Text);
        float x1 = option1X + tex.getWidth() / 2 - option1Layout.width / 2;
        float y1 = this.getY() + option1Layout.height / 2;

        if (revealAnswer) {
            if (option1Correct) {
                Font.setColor(Color.GREEN); // Highlight correct answer when power-up is active
            } else {
                Font.setColor(Color.YELLOW);
            }
        } else {
            Font.setColor(Color.YELLOW); // Default color when power-up is not active
        }
        Font.draw(batch, option1Text, x1, y1);

        // Draw option 2 with conditional highlighting
        GlyphLayout option2Layout = new GlyphLayout(Font, option2Text);
        float x2 = option2X + tex.getWidth() / 2 - option2Layout.width / 2;
        float y2 = this.getY() + option2Layout.height / 2;

        if (revealAnswer) {
            if (option2Correct) {
                Font.setColor(Color.GREEN); // Highlight correct answer when power-up is active
            } else {
                Font.setColor(Color.YELLOW);
            }
        } else {
            Font.setColor(Color.YELLOW); // Default color when power-up is not active
        }
        Font.draw(batch, option2Text, x2, y2);

        // Reset font color to default after drawing
        Font.setColor(Color.YELLOW);

        batch.end();
    }

    // Generate a random math question based on the topic
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

    // Generate an addition question
    private Object[] generateAdditionQuestion() {
        int a = MathUtils.random(10);
        int b = MathUtils.random(10);
        int answer = a + b;
        String question = a + " + " + b;
        return new Object[]{answer, question};
    }

    // Generate a subtraction question
    private Object[] generateSubtractionQuestion() {
        int a = MathUtils.random(10);
        int b = MathUtils.random(10);
        int answer = Math.max(a, b) - Math.min(a, b);
        String question = Math.max(a, b) + " - " + Math.min(a, b);
        return new Object[]{answer, question};
    }

    // Generate a multiplication question
    private Object[] generateMultiplicationQuestion() {
        int a = MathUtils.random(10);
        int b = MathUtils.random(10);
        int answer = a * b;
        String question = a + " x " + b;
        return new Object[]{answer, question};
    }

    // Generate a division question
    private Object[] generateDivisionQuestion() {
        int a = MathUtils.random(10) + 1;
        int b = MathUtils.random(10) + 1;
        int div = a * b;
        int answer = b;
        String question = div + " / " + a;
        return new Object[]{answer, question};
    }

    // Generate a random question from all types
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

    // Generate a wrong answer for the question
    private String generateWrongAnswer(int correctAnswer) {
        int wrongAnswer = correctAnswer + MathUtils.random(1, 7);
        return String.valueOf(wrongAnswer);
    }

    // Handle movement logic
    public void movement() {
    }

    // Getter and setter for movement flag
    public boolean getMovement() {
        return movement;
    }

    public void setMovement(boolean movement) {
        this.movement = movement;
    }

    // Getter and setter for moveRight flag
    public boolean getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    // Get bounds for option 1
    public Rectangle getBounds1() {
        return option1Bounds;
    }

    // Get bounds for option 2
    public Rectangle getBounds2() {
        return option2Bounds;
    }

    // Check if option 1 is correct
    public boolean isOption1() {
        return option1Correct;
    }

    // Check if option 2 is correct
    public boolean isOption2() {
        return option2Correct;
    }

    // Adjust difficulty based on score
    public void difficulty() {
        int speed = Math.floorDiv(score, 3) + 1;
        if (speed > 5) {
            speed = 5;
        }
        this.setSpeed(speed);
    }

    // Getter for score
    public static int getScore() {
        return score;
    }

    // Reset the score
    public static void resetScore() {
        score = 0;
    }

    // Handle correct option hit
    public void correctOptionHit() {
        this.setY(Gdx.graphics.getHeight() + 50);
        score += 1;
        difficulty();
        randomQuestionGenerator(topic);
        circleHit = true;
    }

    // Handle when the option hits the border
    public void hitBorder() {
        this.setY(Gdx.graphics.getHeight() + 50);
        randomQuestionGenerator(topic);
        circleHit = true;
    }

    // Check if the circle was hit
    public boolean circleHit() {
        return circleHit;
    }

    // Reset the circle hit state
    public void respawn() {
        circleHit = false;
    }

    // Update method for game logic
    public void update() {
        if (score > 9) {
            this.setMovement(true);
        }
    }

    // Dispose resources
    public void dispose() {
        this.dispose();
    }
}