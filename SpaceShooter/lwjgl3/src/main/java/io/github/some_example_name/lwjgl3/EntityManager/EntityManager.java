package io.github.some_example_name.lwjgl3.EntityManager;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.some_example_name.lwjgl3.MovementManager.MovementManager;
import io.github.some_example_name.lwjgl3.PowerupManager.TimeFreeze;

public class EntityManager {
	private List<Entity> entityList; // List to store all entities managed by the EntityManager

	// Constructor to initialize the EntityManager and its entity list
	public EntityManager() {
		entityList = new ArrayList<>();
	}

	// Add an entity to the EntityManager
	public void addEntities(Entity entity) {
		entityList.add(entity);
	}

	// Retrieve the list of all entities managed by the EntityManager
	public List<Entity> getAllEntities() {
		return entityList; // Return the list of all entities
	}

	// Remove an entity from the EntityManager
	public void removeEntities(Entity entity) {
		entityList.remove(entity);
	}

	// Draw all entities using the provided SpriteBatch and ShapeRenderer
	public void draw(SpriteBatch batch, ShapeRenderer shape) {
		for (Entity entity : entityList) {
			entity.draw(shape); // Draw the entity using the ShapeRenderer
			entity.draw(batch); // Draw the entity using the SpriteBatch
		}
	}

	// Handle the movement of all entities using the MovementManager
	public void movement(MovementManager movementManager) {
		for (Entity entity : entityList) {
			// Skip moving Circle entities if the TimeFreeze power-up is active
			if (entity instanceof Circle && TimeFreeze.isTimeFrozen()) {
				continue;
			}

			movementManager.moveEntity(entity); // Move the entity
		}
	}

	// Update all entities
	public void update() {
		for (Entity entity : entityList) {
			// Skip updating Circle entities if the TimeFreeze power-up is active
			if (entity instanceof Circle && TimeFreeze.isTimeFrozen()) {
				continue;
			}

			entity.update(); // Update the entity
		}
	}

	// Dispose of resources used by the EntityManager
	public void dispose() {
		this.dispose(); // Dispose of resources (potentially a bug: recursive call)
	}
}
