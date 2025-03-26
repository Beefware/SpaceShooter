package io.github.some_example_name.lwjgl3.EntityManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

public class Player extends Entity {
    private Texture tex; // Texture of the player
    private Rectangle bounds; // Rectangle bounds for collision detection
    private boolean isHit = false; // Flag to check if the player is hit
    private boolean gameOver = false; // Flag to check if the game is over

    // Default constructor
    public Player() {
    }

    // Constructor to initialize player with texture, position, speed, and health
    public Player(String string, float x, float y, float speed, int health) {
        super(x, y, null, speed, health); // Call the parent Entity constructor
        tex = new Texture(string); // Load the texture
    }

    // Return the player's texture
    public Texture getTexture() {
        return tex;
    }

    // Set the player's texture
    public void setTexture(Texture tex) {
        this.tex = tex;
    }

    // Return the player's rectangle bounds for collision detection
    public Rectangle getBounds() {
        return bounds;
    }

    // Reduce player's health by 1 if damaged and change texture to indicate healing
    public void damage() {
        if (!this.getTexture().toString().equals("PlayerHealing.png")) { // Check if not already in healing state
            this.setHealth(getHealth() - 1); // Decrease health
            this.setTexture(new Texture("PlayerHealing.png")); // Change texture to healing
        }
    }
    
    // Provide temporary immunity by changing texture and reverting after 3 seconds
    public void immunity() {
        this.setTexture(new Texture("PlayerHealing.png")); // Set healing texture
        Timer.schedule(new Timer.Task() { // Schedule a task to revert texture
          @Override
          public void run() {
             setTexture(new Texture("Player.png")); // Revert to normal texture
          }
      }, 3); // Delay of 3 seconds
    }

    // Check if the game is over
    public boolean isGameOver() {
        return gameOver;
    }

    // Draw the player and health icons on the screen
    public void draw(SpriteBatch batch) {
        batch.begin();
        // Draw one health icon for each unit of health
        for (int i = 0; i < this.getHealth(); i++) {
            batch.draw(this.getTexture(), 20 + i * 50, 600, this.getTexture().getWidth() / 3, this.getTexture().getHeight() / 3);
        }
        // Draw the player
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getTexture().getWidth(), this.getTexture().getHeight());
        batch.end();

        // Update the rectangle bounds for collision detection
        bounds = new Rectangle(this.getX(), this.getY(), this.getTexture().getWidth(), this.getTexture().getHeight());
    }

    // Placeholder for player movement logic
    public void movement() {
        // Movement logic can be implemented here
    }

    // Update the player's state
    public void update() {
        // If health is less than 1, change texture to damage.png and set gameOver flag
        if (this.getHealth() < 1) {
            this.setTexture(new Texture("damage.png")); // Set damage texture
            gameOver = true; // Mark game as over
        }
    }

    // Dispose of the player's texture to free resources
    public void dispose() {
        this.getTexture().dispose();
    }
}
