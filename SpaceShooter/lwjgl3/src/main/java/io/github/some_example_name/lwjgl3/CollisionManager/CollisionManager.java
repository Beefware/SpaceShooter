package io.github.some_example_name.lwjgl3.CollisionManager;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Timer;

import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;
import io.github.some_example_name.lwjgl3.EntityManager.Player;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
import io.github.some_example_name.lwjgl3.PowerupManager.Powerup;
import io.github.some_example_name.lwjgl3.PowerupManager.TimeFreeze;

public class CollisionManager {

    public enum CollisionResult {
        CORRECT_OPTION, // Collision with the correct option
        WRONG_OPTION,   // Collision with the wrong option
        NO_COLLISION    // No collision occurred
    }

    // Check if a TriangleProjectile collides with MathOptions and handle the collision
    public static CollisionResult checkTriangleProjectileCollision(TriangleProjectile triangleProjectile, MathOptions mOptions) {

        // If the projectile overlaps with the left MathOptions
        if (triangleProjectile.getBounds().overlaps(mOptions.getBounds1())) {
            // If the left MathOptions is the correct option
            if (mOptions.isOption1()) {
                mOptions.correctOptionHit(); // Handle correct option hit
                triangleProjectile.reset(); // Reset the projectile
                // Schedule a task to respawn MathOptions after a 2-second delay
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        mOptions.respawn();
                    }
                }, 2);
                TimeFreeze.endTimeFreeze(); // End any active time freeze
                return CollisionResult.CORRECT_OPTION;

            } else if (!mOptions.isOption1()) { // If the left MathOptions is the wrong option
                triangleProjectile.reset(); // Reset the projectile
                triangleProjectile.getTriangle().damage(); // Damage the player
                triangleProjectile.getTriangle().immunity(); // Grant temporary immunity
                TimeFreeze.endTimeFreeze(); // End any active time freeze
                return CollisionResult.WRONG_OPTION;
            }
        }

        // If the projectile overlaps with the right MathOptions
        if (triangleProjectile.getBounds().overlaps(mOptions.getBounds2())) {
            // If the right MathOptions is the correct option
            if (mOptions.isOption2()) {
                mOptions.correctOptionHit(); // Handle correct option hit
                triangleProjectile.reset(); // Reset the projectile
                // Schedule a task to respawn MathOptions after a 2-second delay
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        mOptions.respawn();
                    }
                }, 2);
                TimeFreeze.endTimeFreeze(); // End any active time freeze
                return CollisionResult.CORRECT_OPTION;

            } else if (!mOptions.isOption2()) { // If the right MathOptions is the wrong option
                triangleProjectile.reset(); // Reset the projectile
                triangleProjectile.getTriangle().damage(); // Damage the player
                triangleProjectile.getTriangle().immunity(); // Grant temporary immunity
                TimeFreeze.endTimeFreeze(); // End any active time freeze
                return CollisionResult.WRONG_OPTION;
            }
        }
        
        return CollisionResult.NO_COLLISION; // No collision occurred
    }

    // Check if MathOptions hit the bottom border and handle the collision
    public static boolean checkCirclesBorderCollision(MathOptions mOptions, Player player) {
        // Check if MathOptions has crossed the bottom border
        if (mOptions.getY() - mOptions.getTexture().getHeight() / 2 < player.getTexture().getHeight()) {
        	player.damage(); // Damage the player
        	player.immunity(); // Grant temporary immunity
            mOptions.hitBorder(); // Handle border collision
            // Schedule a task to respawn MathOptions after a 0.5-second delay
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    mOptions.respawn();
                }
            }, 0.5f);
            System.out.println("Checked"); // Debug message
            return true;
        }
        return false; // No collision with the border
    }

    // Check if the player collides with any power-up and handle the collision
    public static boolean checkPowerupCollision(Player player, List<Powerup> powerups) {
        List<Powerup> toRemove = new ArrayList<>(); // List to store power-ups to be removed

        // Iterate through the power-ups
        for (Powerup powerup : new ArrayList<>(powerups)) {
            // Check if the player overlaps with the power-up
            if (player.getBounds().overlaps(powerup.getBounds())) {
                powerup.applyEffect(); // Apply the power-up effect
                toRemove.add(powerup); // Mark the power-up for removal
            }
        }

        powerups.removeAll(toRemove); // Remove the marked power-ups
        return !toRemove.isEmpty(); // Return true if any power-ups were removed
    }

    // Check if a projectile collides with any power-up and handle the collision
    public static boolean checkProjectilePowerupCollision(TriangleProjectile projectile, List<Powerup> powerups) {
        List<Powerup> toRemove = new ArrayList<>(); // List to store power-ups to be removed

        // Iterate through the power-ups
        for (Powerup powerup : new ArrayList<>(powerups)) {
            // Check if the projectile overlaps with the power-up
            if (projectile.getBounds().overlaps(powerup.getBounds())) {
                System.out.println("Projectile hit ExtraLife!"); // Debug message
                powerup.applyEffect(); // Apply the power-up effect
                toRemove.add(powerup); // Mark the power-up for removal
            }
        }

        powerups.removeAll(toRemove); // Remove the marked power-ups
        return !toRemove.isEmpty(); // Return true if any power-ups were removed
    }
}
