package io.github.some_example_name.lwjgl3.PowerupManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.some_example_name.lwjgl3.EntityManager.Triangle;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
import io.github.some_example_name.lwjgl3.CollisionManager.CollisionManager;

import com.badlogic.gdx.Gdx;

public class PowerupManager {
    
    private static PowerupManager instance; // Singleton instance

    private List<Powerup> powerups;
    private Random random;
    private static float powerupTimer = 0; // Time left for power-up
    private static float maxPowerupDuration = 0; // Total duration of power-up
    private static boolean powerupActive = false; // Is a power-up currently active?
    private ShapeRenderer shapeRenderer;

    public List<Powerup> getPowerups() {
        return powerups;
    }

    public static PowerupManager getInstance() {
        if (instance == null) {
            instance = new PowerupManager();
        }
        return instance;
    }

    private PowerupManager() {
        powerups = new ArrayList<>();
        random = new Random();
        shapeRenderer = new ShapeRenderer();

        scheduleNextSpawn();
    }

    public void startPowerupTimer(float duration) {
        powerupTimer = duration;
        maxPowerupDuration = duration;
        powerupActive = true;
    }

    // Schedule next spawn
    private void scheduleNextSpawn() {
        float delay = random.nextFloat() * (40 - 10) + 10;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                int screenWidth = Gdx.graphics.getWidth(); 
                float safeX = Math.max(50, Math.min(random.nextInt(screenWidth), screenWidth - 50));
                spawnPowerup(safeX, 600);
                scheduleNextSpawn(); 
            }
        }, delay);
    }

    // Spawn a power-up (ExtraLife, HintPowerup, TimeFreeze, or PlayerSpeed)
    public void spawnPowerup(float x, float y) {
        int screenWidth = Gdx.graphics.getWidth();

        float safeX = Math.max(50, Math.min(x, screenWidth - 50));
        float safeY = Math.min(Gdx.graphics.getHeight() - 50, 500); // ✅ Safer spawn Y

        Powerup newPowerup;

        // power-ups: 0 = ExtraLife, 1 = HintPowerup, 2 = TimeFreeze, 3 = PlayerSpeed
        int randomChoice = random.nextInt(4); // ➡️ Random integer: 0, 1, 2, or 3

        if (randomChoice == 0) {
            newPowerup = new ExtraLife(safeX, safeY, 10, 2);
            System.out.println("✅ Spawned ExtraLife at (" + safeX + ", " + safeY + ")");
//        } else if (randomChoice == 1) {
//            newPowerup = new HintPowerup(safeX, safeY, 10, 2);
//            System.out.println("💡 Spawned HintPowerup at (" + safeX + ", " + safeY + ")");
//        } else if (randomChoice == 2) {
//            newPowerup = new TimeFreeze(safeX, safeY, 10, 2);
//            System.out.println("❄️ Spawned TimeFreeze at (" + safeX + ", " + safeY + ")");
        } else {
            newPowerup = new PlayerSpeed(safeX, safeY, 10, 2);
            System.out.println("⚡ Spawned PlayerSpeed at (" + safeX + ", " + safeY + ")");
        }

        powerups.add(newPowerup);
    }

    public void checkCollision(Triangle player, TriangleProjectile projectile) {
        Iterator<Powerup> iterator = powerups.iterator();

        while (iterator.hasNext()) {
            Powerup p = iterator.next();
            String powerupName = p.getClass().getSimpleName();

            boolean playerCollected = CollisionManager.checkPowerupCollision(player, new ArrayList<>(List.of(p)));

            if (playerCollected) {
                System.out.println("⚡ Player collected power-up: " + powerupName);
            }

            if (playerCollected) {
                System.out.println("✅ Applying effect for " + powerupName);

                if (p instanceof ExtraLife) { 
                    // Only increase health for ExtraLife
                    if (player.getHealth() < 5) {  
                        player.setHealth(player.getHealth() + 1);
                        System.out.println("❤️ Extra Life! Health: " + player.getHealth());
                    } else {
                        System.out.println("⚠️ Player already at max health.");
                    }
                } else if (p instanceof PlayerSpeed) { 
                    System.out.println("⚡ PlayerSpeed power-up collected!");

                    float oldSpeed = player.getSpeed();
                    float newSpeed = oldSpeed * 2.0f;

                    player.setSpeed(newSpeed);

                    // Start power-up effect timer
                    PowerupManager.getInstance().startPowerupTimer(5.0f); 

                    // Restore speed after duration
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            System.out.println("⚡ Player speed power-up expired!");
                            player.setSpeed(oldSpeed);
                        }
                    }, 5.0f);
                } else if (p instanceof HintPowerup) { 
                    ((HintPowerup) p).applyEffect();
                    System.out.println("💡 Hint power-up activated!");
                } else if (p instanceof TimeFreeze) { 
                    ((TimeFreeze) p).applyEffect();
                    System.out.println("❄️ TimeFreeze power-up activated!");
                }

                iterator.remove(); // Remove power-up after collection
            }
        }
    }

    // Update movement & remove off-screen power-ups
    public void update(float deltaTime) {
        Iterator<Powerup> iterator = powerups.iterator();

        while (iterator.hasNext()) {
            Powerup p = iterator.next();
            p.update(); // Moves downward

            if (p.getY() < 0) {
                iterator.remove();
            }
        }

        // Update the power-up timer
        if (powerupActive) {
            powerupTimer -= deltaTime;
            if (powerupTimer <= 0) {
                powerupActive = false;
            }
        }
    }

    // Render power-ups
    public void render(SpriteBatch batch) {
        for (Powerup p : powerups) {
            p.draw(batch);
        }

        drawPowerupTimer(); // Draw the shrinking bar
    }

    private void drawPowerupTimer() {
        if (!powerupActive) return;

        float screenWidth = Gdx.graphics.getWidth();
        float barHeight = 15; // Slightly taller for visibility
        float barY = 5; // ✅ Position close to the bottom of the screen
        float progress = powerupTimer / maxPowerupDuration; // Shrinking width

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(0, barY, screenWidth * progress, barHeight);
        shapeRenderer.end();
    }
}
