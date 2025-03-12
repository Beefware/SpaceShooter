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
    
    private List<Powerup> powerups;
    private Random random;
    private static float powerupTimer = 0; // Time left for power-up
    private static float maxPowerupDuration = 0; // Total duration of power-up
    private static boolean powerupActive = false; // Is a power-up currently active?
    private ShapeRenderer shapeRenderer;
    
    public List<Powerup> getPowerups() {
        return powerups;
    }

    public void startPowerupTimer(float duration) {
        powerupTimer = duration;
        maxPowerupDuration = duration;
        powerupActive = true;
    }
    
    public PowerupManager() {
        powerups = new ArrayList<>();
        random = new Random();
        shapeRenderer = new ShapeRenderer();
        
        // ✅ Schedule power-ups
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                spawnPowerup(random.nextInt(800), 600); 
            }
        }, 0);

        scheduleNextSpawn();
    }

    
    
    // ✅ 2️⃣ Schedule next spawn
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

    // ✅ 3️⃣ Spawn the ExtraLife power-up
    public void spawnPowerup(float x, float y) {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        float safeX = random.nextFloat() * (screenWidth - 50) + 25; 
        float safeY = Gdx.graphics.getHeight(); 

        Powerup newPowerup = new ExtraLife(safeX, safeY, 10, 2);
        powerups.add(newPowerup);

        System.out.println("✅ Spawned ExtraLife at (" + safeX + ", " + safeY + ")");
    }

    public void checkCollision(Triangle player, TriangleProjectile projectile) {
        Iterator<Powerup> iterator = powerups.iterator();

        while (iterator.hasNext()) {
            Powerup p = iterator.next();

            // ✅ If player collects the power-up
            if (CollisionManager.checkPowerupCollision(player, new ArrayList<>(List.of(p)))) {
                if (player.getHealth() < 5) { 
                    player.setHealth(player.getHealth() + 1);  
                    System.out.println("❤️ Extra Life Collected! Health: " + player.getHealth());
                } else {
                    System.out.println("⚠️ Power-up ignored: Player already at max health (5 hearts).");
                }
                iterator.remove(); // ✅ Remove power-up after collection
                continue; // ✅ Avoid checking again after removal
            }

            // ✅ If projectile hits the power-up (but does not remove projectile)
            if (CollisionManager.checkProjectilePowerupCollision(projectile, new ArrayList<>(List.of(p))))  {
                if (player.getHealth() < 5) { 
                    player.setHealth(player.getHealth() + 1);
                    System.out.println("❤️ Health increased: " + player.getHealth());
                } else {
                    System.out.println("⚠️ At max health (5)! No extra life given.");
                }
                iterator.remove(); // ✅ Remove power-up after being hit
            }
        }
    }


    // ✅ 5️⃣ Update movement & remove off-screen power-ups
    public void update(float deltaTime) {
        Iterator<Powerup> iterator = powerups.iterator();

        while (iterator.hasNext()) {
            Powerup p = iterator.next();
            p.update(); // Moves downward

            if (p.getY() < 0) {
                iterator.remove();
            }
        }

        // ✅ Update the power-up timer
        if (powerupActive) {
            powerupTimer -= deltaTime;
            if (powerupTimer <= 0) {
                powerupActive = false;
            }
        }
    }


    // ✅ 6️⃣ Render power-ups
    public void render(SpriteBatch batch) {
        for (Powerup p : powerups) {
            p.draw(batch);
        }

        drawPowerupTimer(); // ✅ Draw the shrinking bar
    }

    private void drawPowerupTimer() {
        if (!powerupActive) return;

        float screenWidth = Gdx.graphics.getWidth();
        float barHeight = 10;
        float barY = 10; // Bottom position
        float progress = powerupTimer / maxPowerupDuration; // Shrinking width

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(0, barY, screenWidth * progress, barHeight);
        shapeRenderer.end();
    }

}
