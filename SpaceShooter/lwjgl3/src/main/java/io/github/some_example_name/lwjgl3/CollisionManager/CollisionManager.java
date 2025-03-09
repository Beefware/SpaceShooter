package io.github.some_example_name.lwjgl3.CollisionManager;

import com.badlogic.gdx.utils.Timer;
import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;
import io.github.some_example_name.lwjgl3.EntityManager.Triangle;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
import io.github.some_example_name.lwjgl3.PowerupManager.Powerup;
import java.util.List;
import java.util.ArrayList;

public class CollisionManager {

    public enum CollisionResult {
        CORRECT_OPTION,
        WRONG_OPTION,
        NO_COLLISION
    }

 // ✅ Check if Projectile collides with MathOptions & remove it
    public static CollisionResult checkTriangleProjectileCollision(TriangleProjectile triangleProjectile, MathOptions mOptions) {

        // If projectile and left mOptions overlap
        if (triangleProjectile.getBounds().overlaps(mOptions.getBounds1())) {
            // If left mOptions is correct option, add a point and reset
            if (mOptions.isOption1()) {
                mOptions.damage();
                mOptions.setJustHitByProjectile(true);
                // Schedule a task to respawn mOptions after a 3-second delay
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        mOptions.respawn();
                    }
                }, 2);
                return CollisionResult.CORRECT_OPTION;

            } else if (!mOptions.isOption1() && !triangleProjectile.getTriangle().isDamaged()) {
                triangleProjectile.getTriangle().damage();
                mOptions.setJustHitByProjectile(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        triangleProjectile.getTriangle().resetDamageFlag();
                    }
                }, 1);
                return CollisionResult.WRONG_OPTION;
            }
        }

        // If projectile and right mOptions overlap
        if (triangleProjectile.getBounds().overlaps(mOptions.getBounds2())) {
            // If right mOptions is correct, add a point and reset
            if (mOptions.isOption2()) {
                mOptions.setJustHitByProjectile(true);
                mOptions.damage();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        mOptions.respawn();
                    }
                }, 2);
                return CollisionResult.CORRECT_OPTION;

            } else if (!mOptions.isOption2() && !triangleProjectile.getTriangle().isDamaged()) {
                triangleProjectile.getTriangle().damage();
                mOptions.setJustHitByProjectile(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        triangleProjectile.getTriangle().resetDamageFlag();
                    }
                }, 1);
                return CollisionResult.WRONG_OPTION;
            }
        }
        
        return CollisionResult.NO_COLLISION;
    }

    // ✅ Check if mOptions hit the bottom
    public static boolean checkCirclesBorderCollision(MathOptions mOptions, Triangle triangle) {
        if (mOptions.getY() < 60 && !triangle.isDamaged()) {
            triangle.damage();
            mOptions.hitBorder();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    triangle.resetDamageFlag();
                    mOptions.respawn();
                }
            }, 3);
            return true;
        }
        return false;
    }

    // ✅ Check if player collides with power-up
    public static boolean checkPowerupCollision(Triangle player, List<Powerup> powerups) {
        List<Powerup> toRemove = new ArrayList<>();

        for (Powerup powerup : new ArrayList<>(powerups)) {
            if (player.getBounds().overlaps(powerup.getBounds())) {
                powerup.applyEffect();
                toRemove.add(powerup); // ✅ Mark for removal instead of modifying the list directly
            }
        }

        powerups.removeAll(toRemove); // ✅ Remove power-ups after iteration
        return !toRemove.isEmpty();
    }

    // ✅ Check if projectile collides with power-up
    public static boolean checkProjectilePowerupCollision(TriangleProjectile projectile, List<Powerup> powerups) {
        List<Powerup> toRemove = new ArrayList<>();

        for (Powerup powerup : new ArrayList<>(powerups)) {
            if (projectile.getBounds().overlaps(powerup.getBounds())) {
                System.out.println("💥 Projectile hit ExtraLife! Removing both.");
                powerup.applyEffect();
                toRemove.add(powerup); // ✅ Mark for removal
            }
        }

        powerups.removeAll(toRemove); // ✅ Remove power-ups after iteration
        return !toRemove.isEmpty();
    }
}


//	    public static boolean checkmOptionsProjectileCollision(mOptionsProjectile mOptionsProjectile, Triangle triangle) {
//	        if (mOptionsProjectile.getBounds().overlaps(triangle.getBounds()) && !triangle.isDamaged()) {
//	            triangle.damage(mOptionsProjectile.getProjectileDamage());
//
//	            // Schedule a task to reset Triangle damage flag after a 3-second delay
//	            Timer.schedule(new Timer.Task() {
//	                @Override
//	                public void run() {
//	                    triangle.resetDamageFlag();
//	                }
//	            }, 3);
//	            
//	            return true;
//	        }
//	        return false;
//	    }
	


