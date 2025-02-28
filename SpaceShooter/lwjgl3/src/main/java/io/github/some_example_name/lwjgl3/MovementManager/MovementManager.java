package io.github.some_example_name.lwjgl3.MovementManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import io.github.some_example_name.lwjgl3.EntityManager.Enemy;
import io.github.some_example_name.lwjgl3.EntityManager.EnemyBullet;
import io.github.some_example_name.lwjgl3.EntityManager.Entity;
import io.github.some_example_name.lwjgl3.EntityManager.Player;
import io.github.some_example_name.lwjgl3.EntityManager.PlayerBullet;

public class MovementManager {

    public void moveEntity(Entity entity) {
        if (entity instanceof Player) {
            movePlayer((Player) entity);
        } else if (entity instanceof Enemy) {
            moveEnemy((Enemy) entity);
        } else if (entity instanceof PlayerBullet) {
            movePlayerBullet((PlayerBullet) entity);
        } else if (entity instanceof EnemyBullet) {
            moveEnemyBullet((EnemyBullet) entity);
        }
    }

    private void movePlayer(Player player) {
        // Player movement logic
        if (!player.isGameOver()) {
            if (Gdx.input.isKeyPressed(Keys.LEFT)) {
                player.setX(player.getX() - 200 * Gdx.graphics.getDeltaTime());
            }
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
                player.setX(player.getX() + 200 * Gdx.graphics.getDeltaTime());
            }
            // Ensure the player stays within the screen boundaries
            if (player.getX() < 0) {
                player.setX(0);
            }
            if (player.getX() > Gdx.graphics.getWidth() - player.getTexture().getWidth()) {
                player.setX(Gdx.graphics.getWidth() - player.getTexture().getWidth());
            }
        }
    }

    private void moveEnemy(Enemy enemy) {
        // Enemy movement logic
        if (!enemy.enemyDestroyed()) {
            // Move enemy downwards if it's above the minimum Y position
            if (enemy.getY() > 400) { // 400 is used as a default for minY
                enemy.setY(enemy.getY() - 10);
            }

         // Check boundaries and toggle direction
            if (enemy.getX() > Gdx.graphics.getWidth() - enemy.getTexture().getWidth()) {
                enemy.setMoveRight(false); // Change direction to left
            } else if (enemy.getX() < 0) {
                enemy.setMoveRight(true); // Change direction to right
            }

            // Move horizontally based on the direction
            if (enemy.isMoveRight()) {
                enemy.setX(enemy.getX() + 3); // Move right
            } else {
                enemy.setX(enemy.getX() - 3); // Move left
            }
        }
    }

    private void movePlayerBullet(PlayerBullet bullet) {
        // PlayerBullet movement logic
        bullet.setY(bullet.getY() + bullet.getSpeed());
        if (!bullet.getPlayer().isGameOver() && bullet.getY() > 600) {
            bullet.setY(bullet.getPlayer().getY() + 70);
            bullet.setX(bullet.getPlayer().getX() + 50);
        }
    }

    private void moveEnemyBullet(EnemyBullet bullet) {
        // EnemyBullet movement logic
        bullet.setY(bullet.getY() - bullet.getSpeed());
        if (!bullet.getEnemy().enemyDestroyed() && bullet.getEnemy().getY() < Gdx.graphics.getHeight() && bullet.getY() < 0) {
            bullet.setY(bullet.getEnemy().getY());
            bullet.setX(bullet.getEnemy().getX() + 50);
        }
    }
}
