package io.github.some_example_name.lwjgl3.CollisionManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;

import io.github.some_example_name.lwjgl3.EntityManager.Circle;
import io.github.some_example_name.lwjgl3.EntityManager.Triangle;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
public class CollisionManager {

	    public static boolean checkTriangleProjectileCollision(TriangleProjectile triangleProjectile, Circle circle) {
	        if (triangleProjectile.getBounds().overlaps(circle.getBounds1())) {
	        	if(circle.isOption1()) {
	        		 circle.damage();

	 	            // Schedule a task to respawn circle after a 3-second delay
	 	            Timer.schedule(new Timer.Task() {
	 	                @Override
	 	                public void run() {
	 	                    circle.respawn();
	 	                }
	 	            }, 2);
	 	            
	 	            return true;
	        	}else if(!circle.isOption1() && !triangleProjectile.getTriangle().isDamaged()) {
	        		triangleProjectile.getTriangle().damage();
	        		
	        		//Schedule a task to reset Triangle damage flag after a 3-second delay
		            Timer.schedule(new Timer.Task() {
		                @Override
		                public void run() {
			        		triangleProjectile.getTriangle().resetDamageFlag();
		                }
		            }, 1);

	        		return true;

	        	}
	        	
	        }
	        
	        if (triangleProjectile.getBounds().overlaps(circle.getBounds2())) {
	        	if (circle.isOption2()) {
		            circle.damage();
		
		            // Schedule a task to respawn circle after a 3-second delay
		            Timer.schedule(new Timer.Task() {
		                @Override
		                public void run() {
		                    circle.respawn();
		                }
		            }, 2);
		            
	            return true;
	        	}else if(!circle.isOption2() && !triangleProjectile.getTriangle().isDamaged()) {
	        		triangleProjectile.getTriangle().damage();
	        		//Schedule a task to reset Triangle damage flag after a 3-second delay
		            Timer.schedule(new Timer.Task() {
		                @Override
		                public void run() {
			        		triangleProjectile.getTriangle().resetDamageFlag();
		                }
		            }, 1);

	        		return true;

	        	}
	        }

	        
	        return false;
	    }
	    
	    
	    public static boolean checkCirclesBorderCollision(Circle circle, Triangle triangle) {
	    	if(circle.getY()-circle.getRadius()<1 && !triangle.isDamaged()) {
	    		triangle.damage();
	    		circle.hitBorder();
        		//Schedule a task to reset Triangle damage flag after a 3-second delay
	            Timer.schedule(new Timer.Task() {
	                @Override
	                public void run() {
		        		triangle.resetDamageFlag();
		    			circle.respawn();

	                }
	            }, 1);

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
	


