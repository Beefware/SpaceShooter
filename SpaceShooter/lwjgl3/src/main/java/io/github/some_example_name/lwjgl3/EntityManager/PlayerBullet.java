package io.github.some_example_name.lwjgl3.EntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;


public class PlayerBullet extends Bullet{

	private Player player;
	private Enemy enemy;
	
	public PlayerBullet() {
		
	}
	
	public PlayerBullet(Color color, float speed, int damage, Player player, Enemy enemy) {
		//Create PlayerBullet
		super(player.getX()+50,player.getY()+70,color,speed,damage);
		this.player = player;
		this.enemy = enemy;
		
	}
	
	public Player getPlayer() {
	    return this.player;
	}
	
	
	//Movement of PlayerBullet
	public void movement() {
//		this.setY(this.getY()+ this.getSpeed());
//		//Reset Bullet if out of frame and alive
//		if(player.isGameOver()==false) {
//			if (this.getY()>600) {
//				this.setY(player.getY()+70);
//				this.setX(player.getX()+50);
//				
//			}
//		}
	}
	
	public void update() {
//		//If bullet rectangle collides with enemy rectangle, enemy takes damage
//		if (this.getBounds().overlaps(enemy.getBounds())) {
//		    enemy.damage();
//
//		    // Schedule a task to run enemy respawn after a 3 second delay
//		    Timer.schedule(new Timer.Task() {
//		        @Override
//		        public void run() {
//		        	//Enemy respawns 3s after being destroyed
//		            enemy.respawn();
//		        }
//		    }, 3); // Delay in seconds
//		}

	}
	
	public void dispose() {
		
	}
}
