package io.github.some_example_name.lwjgl3.PowerupManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;

import io.github.some_example_name.lwjgl3.CollisionManager.CollisionManager;
import io.github.some_example_name.lwjgl3.EntityManager.Player;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;

public class PowerupManager {
    
    private static PowerupManager instance; // Singleton instance

    private List<Powerup> powerups; // List of active power-ups
    private Random random; // Random generator for spawning
    private static float powerupTimer = 0; // Time left for the active power-up
    private static float maxPowerupDuration = 0; // Total duration of the active power-up
    private static boolean powerupActive = false; // Is a power-up currently active?
    private List<PowerupCollectionListener> listeners = new ArrayList<>(); // Listeners for power-up collection events
    private ShapeRenderer shapeRenderer; // Renderer for drawing shapes (e.g., power-up timer bar)
    private boolean spawningStarted = false; // Flag to track if spawning has started
    private Timer.Task spawnTask; // Task for scheduling power-up spawns

    // Getter for the list of active power-ups
    public List<Powerup> getPowerups() {
        return powerups;
    }

    // Singleton pattern to get the single instance of PowerupManager
    public static PowerupManager getInstance() {
        if (instance == null) {
            instance = new PowerupManager();
        }
        return instance;
    }

    // Private constructor to enforce singleton pattern
    private PowerupManager() {
        powerups = new ArrayList<>();
        random = new Random();
        shapeRenderer = new ShapeRenderer();
    }

    // Start the timer for an active power-up
    public void startPowerupTimer(float duration) {
        powerupTimer = duration;
        maxPowerupDuration = duration;
        powerupActive = true;
    }
    
    // Add a listener for power-up collection events
    public void addCollectionListener(PowerupCollectionListener listener) {
        listeners.add(listener);
    }
    
    // Remove a listener for power-up collection events
    public void removeCollectionListener(PowerupCollectionListener listener) {
        listeners.remove(listener);
    }
    
    // Notify all listeners when a power-up is collected
    private void notifyPowerupCollected(Powerup powerup) {
        for (PowerupCollectionListener listener : listeners) {
            listener.onPowerupCollected(powerup);
        }
    }
    
    // Start spawning power-ups at random intervals
    public void startSpawning() {
        if (!spawningStarted) {
            spawningStarted = true;
            scheduleNextSpawn();
            System.out.println("Powerup spawning started.");
        }
    }

    // Stop spawning power-ups and clear the active list
    public void stopSpawning() {
        spawningStarted = false;
        powerups.clear(); // Clear all active power-ups
        if (spawnTask != null) {
            spawnTask.cancel(); // Cancel the current spawn task
            spawnTask = null; // Clear the reference
        }
        System.out.println("Powerup spawning stopped.");
    }

    // Schedule the next power-up spawn
    private void scheduleNextSpawn() {
        if (!spawningStarted) return; // Ensure spawning is still active

        float delay = random.nextFloat() * (40 - 10) + 10; // Random delay between 10 and 40 seconds

        spawnTask = new Timer.Task() { // Create a new spawn task
            @Override
            public void run() {
                if (!spawningStarted) return; // Ensure spawning is still active
                int screenWidth = Gdx.graphics.getWidth();
                float safeX = Math.max(50, Math.min(random.nextInt(screenWidth), screenWidth - 50)); // Ensure safe X position
                spawnPowerup(safeX, 600); // Spawn power-up at a fixed Y position
                scheduleNextSpawn(); // Schedule the next spawn
            }
        };
        Timer.schedule(spawnTask, delay); // Schedule the task with the calculated delay
    }

    // Spawn a power-up at the specified position
    public void spawnPowerup(float x, float y) {
        int screenWidth = Gdx.graphics.getWidth();

        float safeX = Math.max(50, Math.min(x, screenWidth - 50)); // Ensure safe X position
        float safeY = Math.min(Gdx.graphics.getHeight() - 50, 500); // Ensure safe Y position

        Powerup newPowerup;

        // Randomly select a power-up type to spawn
        int randomChoice = random.nextInt(4); // Random integer: 0, 1, 2, or 3

        if (randomChoice == 0) {
            newPowerup = new ExtraLife(safeX, safeY, 10, 2);
            System.out.println("Spawned ExtraLife at (" + safeX + ", " + safeY + ")");
        } else if (randomChoice == 1) {
            newPowerup = new HintPowerup(safeX, safeY, 10, 2);
            System.out.println("Spawned HintPowerup at (" + safeX + ", " + safeY + ")");
        } else if (randomChoice == 2) {
            newPowerup = new TimeFreeze(safeX, safeY, 10, 2);
            System.out.println("Spawned TimeFreeze at (" + safeX + ", " + safeY + ")");
        } else {
            newPowerup = new PlayerSpeed(safeX, safeY, 10, 2);
            System.out.println("Spawned PlayerSpeed at (" + safeX + ", " + safeY + ")");
        }

        powerups.add(newPowerup); // Add the new power-up to the active list
    }

    // Check for collisions between the player/projectile and power-ups
    public void checkCollision(Player player, TriangleProjectile projectile) {
        Iterator<Powerup> iterator = powerups.iterator();

        while (iterator.hasNext()) {
            Powerup p = iterator.next();
            String powerupName = p.getClass().getSimpleName();

            boolean playerCollected = CollisionManager.checkPowerupCollision(player, new ArrayList<>(Arrays.asList(p)));

            if (playerCollected) {
                System.out.println("Player collected power-up: " + powerupName);
            }

            if (playerCollected) {
                System.out.println("Applying effect for " + powerupName);

                // Apply effects based on the type of power-up
                if (p instanceof ExtraLife) { 
                    // Increase health for ExtraLife
                    if (player.getHealth() < 5) {  
                        player.setHealth(player.getHealth() + 1);
                        System.out.println("Extra Life! Health: " + player.getHealth());
                    } else {
                        System.out.println("Player already at max health.");
                    }
                } else if (p instanceof PlayerSpeed) { 
                    System.out.println("PlayerSpeed power-up collected!");

                    float oldSpeed = player.getSpeed();
                    float newSpeed = oldSpeed * 2.0f;

                    player.setSpeed(newSpeed);

                    // Start power-up effect timer
                    PowerupManager.getInstance().startPowerupTimer(5.0f); 

                    // Restore speed after duration
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            System.out.println("Player speed power-up expired!");
                            player.setSpeed(oldSpeed);
                        }
                    }, 5.0f);
                } else if (p instanceof HintPowerup) { 
                    ((HintPowerup) p).applyEffect();
                    System.out.println("Hint power-up activated!");
                } else if (p instanceof TimeFreeze) { 
                    ((TimeFreeze) p).applyEffect();
                    System.out.println("TimeFreeze power-up activated!");
                }
                
                notifyPowerupCollected(p); // Notify listeners of the collection

                iterator.remove(); // Remove the collected power-up
            }
        }
    }

    // Update the state of power-ups and remove off-screen ones
    public void update(float deltaTime) {
        Iterator<Powerup> iterator = powerups.iterator();

        while (iterator.hasNext()) {
            Powerup p = iterator.next();
            p.update(); // Update power-up position (e.g., move downward)

            if (p.getY() < 0) { // Remove power-ups that move off-screen
                iterator.remove();
            }
        }

        // Update the power-up timer
        if (powerupActive) {
            powerupTimer -= deltaTime;
            if (powerupTimer <= 0) {
                powerupActive = false; // Deactivate power-up when timer expires
            }
        }
    }

    // Render all active power-ups
    public void render(SpriteBatch batch) {
        for (Powerup p : powerups) {
            p.draw(batch); // Draw each power-up
        }

        drawPowerupTimer(); // Draw the shrinking power-up timer bar
    }

    // Draw the shrinking power-up timer bar
    private void drawPowerupTimer() {
        if (!powerupActive) return; // Only draw if a power-up is active

        float screenWidth = Gdx.graphics.getWidth();
        float barHeight = 15; // Height of the timer bar
        float barY = 5; // Position of the timer bar near the bottom of the screen
        float progress = powerupTimer / maxPowerupDuration; // Calculate progress as a fraction

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN); // Set the color of the bar
        shapeRenderer.rect(0, barY, screenWidth * progress, barHeight); // Draw the bar
        shapeRenderer.end();
    }
}
