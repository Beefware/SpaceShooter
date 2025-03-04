package io.github.some_example_name.lwjgl3.CollisionManager;

import com.badlogic.gdx.utils.Timer;

import io.github.some_example_name.lwjgl3.EntityManager.Circle;
import io.github.some_example_name.lwjgl3.EntityManager.Triangle;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
public class CollisionManager {

	    public static boolean checkTriangleProjectileCollision(TriangleProjectile triangleProjectile, Circle circle) {
	        if (triangleProjectile.getBounds().overlaps(circle.getBounds())) {
	            circle.damage();

	            // Schedule a task to respawn circle after a 3-second delay
	            Timer.schedule(new Timer.Task() {
	                @Override
	                public void run() {
	                    circle.respawn();
	                }
	            }, 3);
	            
	            return true;
	        }
	        
	        return false;
	    }
}

//	    public static boolean checkCircleProjectileCollision(CircleProjectile circleProjectile, Triangle triangle) {
//	        if (circleProjectile.getBounds().overlaps(triangle.getBounds()) && !triangle.isDamaged()) {
//	            triangle.damage(circleProjectile.getProjectileDamage());
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
	


