package io.github.some_example_name.lwjgl3.MovementManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import io.github.some_example_name.lwjgl3.EntityManager.Circle;
import io.github.some_example_name.lwjgl3.EntityManager.Entity;
import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;
import io.github.some_example_name.lwjgl3.EntityManager.Triangle;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;

public class MovementManager {

    public void moveEntity(Entity entity) {
        if (entity instanceof Triangle) {
            moveTriangle((Triangle) entity);
        } else if (entity instanceof MathOptions) {
            moveCircle((MathOptions) entity);
        } else if (entity instanceof TriangleProjectile) {
        	moveTriangleProjectile((TriangleProjectile) entity);
        }
    }

    private void moveTriangle(Triangle triangle) {
        // Triangle movement logic
        if (!triangle.isGameOver()) {
            if (Gdx.input.isKeyPressed(Keys.LEFT)) {
                triangle.setX(triangle.getX() - 200 * Gdx.graphics.getDeltaTime());
            }
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
                triangle.setX(triangle.getX() + 200 * Gdx.graphics.getDeltaTime());
            }
            // Ensure the Triangle stays within the screen boundaries
            if (triangle.getX() < 0) {
                triangle.setX(0);
            }
            if (triangle.getX() > Gdx.graphics.getWidth() - triangle.getTexture().getWidth()) {
                triangle.setX(Gdx.graphics.getWidth() - triangle.getTexture().getWidth());
            }
        }
    }

    private void moveCircle(MathOptions mOptions) {
        // Circle movement logic
        if (!mOptions.circleHit()) {
        	mOptions.setY(mOptions.getY()-mOptions.getSpeed());
//            // Move Circle downwards if it's above the minimum Y position
//            if (circle.getY() > 400) { // 400 is used as a default for minY
//                circle.setY(circle.getY() - 10);
//            }
//
//         // Check boundaries and toggle direction
//            if (circle.getX() > Gdx.graphics.getWidth()) {
//                circle.setMoveRight(false); // Change direction to left
//            } else if (circle.getX() < 0) {
//                circle.setMoveRight(true); // Change direction to right
//            }
//
//            // Move horizontally based on the direction
//            if (circle.isMoveRight()) {
//                circle.setX(circle.getX() + 3); // Move right
//            } else {
//                circle.setX(circle.getX() - 3); // Move left
//            }
        }
    }

    private void moveTriangleProjectile(TriangleProjectile projectile) {
        // TriangleProjectile movement logic
        if (Gdx.input.isKeyPressed(Keys.SPACE) && projectile.getY()==5) {
        	projectile.setX(projectile.getTriangle().getX()+50);
            //projectile.setColor(Color.BLUE);

            projectile.setY(projectile.getY() + projectile.getSpeed());
        }
        if(projectile.getY() > 5) {
            projectile.setY(projectile.getY() + projectile.getSpeed());

        }
        if (!projectile.getTriangle().isGameOver() && projectile.getY() > 600) {
            projectile.setY(projectile.getTriangle().getY());
            projectile.setX(projectile.getTriangle().getX()+50);
        }
    }

//    private void moveCircleProjectile(CircleProjectile projectile) {
//        // CircleProjectile movement logic
//        projectile.setY(projectile.getY() - projectile.getSpeed());
//        if (!projectile.getCircle().circleHit() && projectile.getCircle().getY() < Gdx.graphics.getHeight() && projectile.getY() < 0) {
//            projectile.setY(projectile.getCircle().getY());
//            projectile.setX(projectile.getCircle().getX());
//        }
//    }
    
}
