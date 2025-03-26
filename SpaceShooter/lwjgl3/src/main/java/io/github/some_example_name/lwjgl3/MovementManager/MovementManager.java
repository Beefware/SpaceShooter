package io.github.some_example_name.lwjgl3.MovementManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import io.github.some_example_name.lwjgl3.EntityManager.Entity;
import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;
import io.github.some_example_name.lwjgl3.EntityManager.Player;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;

public class MovementManager {

    // Method to move an entity based on its type
    public void moveEntity(Entity entity) {
        if (entity instanceof Player) {
            moveTriangle((Player) entity); // Handle player movement
        } else if (entity instanceof MathOptions) {
            moveCircle((MathOptions) entity); // Handle circle movement
        } else if (entity instanceof TriangleProjectile) {
            moveTriangleProjectile((TriangleProjectile) entity); // Handle projectile movement
        }
    }

    // Method to handle player movement (triangle)
    private void moveTriangle(Player player) {
        if (!player.isGameOver()) { // Only move if the game is not over
            float moveSpeed = player.getSpeed() * Gdx.graphics.getDeltaTime(); // Calculate movement speed

            // Move left if LEFT key is pressed
            if (Gdx.input.isKeyPressed(Keys.LEFT)) {
                player.setX(player.getX() - moveSpeed);
            }
            // Move right if RIGHT key is pressed
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
                player.setX(player.getX() + moveSpeed);
            }
            // Prevent player from moving out of the left boundary
            if (player.getX() < 0) {
                player.setX(0);
            }
            // Prevent player from moving out of the right boundary
            if (player.getX() > Gdx.graphics.getWidth() - player.getTexture().getWidth()) {
                player.setX(Gdx.graphics.getWidth() - player.getTexture().getWidth());
            }
        }
    }

    // Method to handle circle movement
    private void moveCircle(MathOptions mOptions) {
        if (!mOptions.circleHit()) { // Only move if the circle hasn't been hit
            mOptions.setY(mOptions.getY() - mOptions.getSpeed()); // Move the circle downward

            // Check boundaries and toggle horizontal movement direction
            if (mOptions.getOption2X() + mOptions.getTexture().getWidth() > Gdx.graphics.getWidth()) {
                mOptions.setMoveRight(false); // Change direction to left
            } else if (mOptions.getOption1X() < 0) {
                mOptions.setMoveRight(true); // Change direction to right
            }

            if (mOptions.getMovement()) { // If horizontal movement is enabled
                // Move horizontally based on the direction
                if (mOptions.getMoveRight()) {
                    mOptions.setOption1x(mOptions.getOption1X() + 3); // Move right
                    mOptions.setOption2x(mOptions.getOption2X() + 3); // Move right
                } else {
                    mOptions.setOption1x(mOptions.getOption1X() - 3); // Move left
                    mOptions.setOption2x(mOptions.getOption2X() - 3); // Move left
                }
            }
        }
    }

    // Method to handle projectile movement
    private void moveTriangleProjectile(TriangleProjectile projectile) {
        // Fire the projectile when SPACE key is pressed and it's at the initial position
        if (Gdx.input.isKeyPressed(Keys.SPACE) && projectile.getY() == 5) {
            projectile.setX(projectile.getTriangle().getX() + 50); // Set initial X position
            projectile.setJustFired(true); // Mark the projectile as just fired
            projectile.setY(projectile.getY() + projectile.getSpeed()); // Move the projectile upward
        }
        // Continue moving the projectile upward if it has been fired
        if (projectile.getY() > 5) {
            projectile.setY(projectile.getY() + projectile.getSpeed());
        }
        // Reset the projectile if it goes out of bounds and the game is not over
        if (!projectile.getTriangle().isGameOver() && projectile.getY() > 600) {
            projectile.reset();
        }
    }
}
