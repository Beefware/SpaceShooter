package io.github.some_example_name.lwjgl3.EntityManager;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.some_example_name.lwjgl3.MovementManager.MovementManager;
import io.github.some_example_name.lwjgl3.PowerupManager.TimeFreeze;

public class EntityManager {
private List<Entity> entityList;
	
	//Create EntityManager Array
	public EntityManager() {
		entityList = new ArrayList<>();
	}
	
	//Add entities to EntityManager
	public void addEntities(Entity entity) {
		entityList.add(entity);
	}
	
	public List<Entity> getAllEntities() {
	    return entityList; // Return the list of all entities
	}

	
	//Remove entity from EntityManager
	public void removeEntities(Entity entity) {
		entityList.remove(entity);
	}
	
	//Draw Entities 
	public void draw(SpriteBatch batch, ShapeRenderer shape) {
		for (Entity entity : entityList) {
			entity.draw(shape); 
			entity.draw(batch);
		}
	}
	
	//Run Entity movements 
	public void movement(MovementManager movementManager) {
	    for (Entity entity : entityList) {
	        // Skip moving Circles if TimeFreeze is active
	        if (entity instanceof Circle && TimeFreeze.isTimeFrozen()) {
	            continue;
	        }

	        movementManager.moveEntity(entity);
	    }
	}
	
	//Update Entities
	public void update() {
	    for (Entity entity : entityList) {
	        // ❄️ Skip updating Circles if TimeFreeze is active
	        if (entity instanceof Circle && TimeFreeze.isTimeFrozen()) {
	            continue;
	        }

	        entity.update(); 
	    }
	}

	
	public void dispose() {
		this.dispose();
	}
		
}
