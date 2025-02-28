package io.github.some_example_name.lwjgl3.AudioManager;

// Add necessary imports
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class AudioManager {
	 private Music backgroundMusic;  // For background music
	 private Sound impactSound;   // For impact sound upon collision
	 private float musicVolume;      // Volume control for background music
	 private float soundVolume;      // Volume control for sound effects
	 
	 // Audio Manager Constructor
	 public AudioManager() {
		 musicVolume = 0.5f; // Default music volume
		 soundVolume = 1.0f; // Default sound volume
		 
		 // Loading audio filess
	     backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("scifi-intro.mp3"));
	     impactSound = Gdx.audio.newSound(Gdx.files.internal("impacteffect.wav"));
	     
	     // Set background music properties
	     backgroundMusic.setLooping(true);  // Loop indefinitely
	     backgroundMusic.setVolume(musicVolume);  // Set default volume
	     
	 }
	 
	 // Play background music if its not already playing
	 public void playBackgroundMusic() {
		 if (!backgroundMusic.isPlaying()) {
			 backgroundMusic.play();  // Play music
	     }
	 }
	 
	// Stop background music
	public void stopBackgroundMusic() {
		if (backgroundMusic.isPlaying()) {
			backgroundMusic.stop();  // Stop music
	    }
	}
	
	// Play explosion sound
    public void playExplosionSound() {
        impactSound.play(soundVolume);  // Play impact sound with volume control upon collision detection
    }
    
    // Adjust music volume
    public void setMusicVolume(float volume) {
        musicVolume = volume;
        backgroundMusic.setVolume(musicVolume);  // Apply set volume to the background music
    }
    
 // Adjust sound effects volume
    public void setSoundVolume(float volume) {
        soundVolume = volume; // Apply set volume to the sound effect
    }

    // Dispose of the audio resources when no longer needed
    public void dispose() {
        backgroundMusic.dispose();  // Dispose of background music resource
        impactSound.dispose();   // Dispose of impact sound resource
    }
	
}
