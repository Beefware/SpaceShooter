package io.github.some_example_name.lwjgl3.EntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;


public class EnemyBullet extends Bullet{

	private Player player;
	private Enemy enemy;
	
	public EnemyBullet() {
		
	}
	
	public EnemyBullet(Color color, float speed, int damage, Player player, Enemy enemy) {
		//Create EnemyBullet.
		super(enemy.getX(),enemy.getY(),color,speed,damage);
		
		//Player and Enemy to detect collisions and positions
		this.player = player;
		this.enemy = enemy;
		
		
	}
	
	public Enemy getEnemy() {
	    return this.enemy;
	}
	

	//Movement of EnemyBullet
	public void movement() {

	}
	
	public void update() {

	}
	
	public void dispose() {
		
	}
}
