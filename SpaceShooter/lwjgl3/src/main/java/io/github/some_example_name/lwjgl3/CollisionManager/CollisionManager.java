package io.github.some_example_name.lwjgl3.CollisionManager;

import com.badlogic.gdx.utils.Timer;

import io.github.some_example_name.lwjgl3.EntityManager.Enemy;
import io.github.some_example_name.lwjgl3.EntityManager.EnemyBullet;
import io.github.some_example_name.lwjgl3.EntityManager.Player;
import io.github.some_example_name.lwjgl3.EntityManager.PlayerBullet;
public class CollisionManager {

	    public static boolean checkPlayerBulletCollision(PlayerBullet playerBullet, Enemy enemy) {
	        if (playerBullet.getBounds().overlaps(enemy.getBounds())) {
	            enemy.damage();

	            // Schedule a task to respawn enemy after a 3-second delay
	            Timer.schedule(new Timer.Task() {
	                @Override
	                public void run() {
	                    enemy.respawn();
	                }
	            }, 3);
	            
	            return true;
	        }
	        
	        return false;
	    }

	    public static boolean checkEnemyBulletCollision(EnemyBullet enemyBullet, Player player) {
	        if (enemyBullet.getBounds().overlaps(player.getBounds()) && !player.isDamaged()) {
	            player.damage(enemyBullet.getBulletDamage());

	            // Schedule a task to reset player's damage flag after a 3-second delay
	            Timer.schedule(new Timer.Task() {
	                @Override
	                public void run() {
	                    player.resetDamageFlag();
	                }
	            }, 3);
	            
	            return true;
	        }
	        return false;
	    }
	}


