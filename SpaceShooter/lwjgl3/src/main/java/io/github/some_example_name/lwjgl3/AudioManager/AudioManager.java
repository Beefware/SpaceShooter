package io.github.some_example_name.lwjgl3.AudioManager;

// Add necessary imports
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
	 private static AudioManager instance; // Singleton instance of AudioManager
	 private boolean isMuted = false; // Flag to check if audio is muted
	 private IAudioPlayer backgroundMusic;  // For background music
	 private Map<String, ISoundEffect> soundEffects; // Map to store sound effects
	 private float musicVolume;      // Volume control for background music
	 private float soundVolume;      // Volume control for sound effects
	 
	 // Singleton pattern to get the instance of AudioManager
	 public static AudioManager getInstance() {
		 if (instance == null) {
			 instance = new AudioManager();
		 }
		 return instance;
	 }
	 
	 // AudioManager Constructor
	 public AudioManager() {
		 System.out.println("AudioManager created!");
		 this.musicVolume = 1.0f; // Default music volume
		 this.soundVolume = 1.0f; // Default sound volume
		 this.soundEffects = new HashMap<>(); // Initialize sound effects map
		 loadDefaultSounds(); // Load default sounds
	 }
	 
	 // Load default sound effects and background music
	 public void loadDefaultSounds() {
		 loadSoundEffect("life", "lifepowerup.wav");
		 loadSoundEffect("power", "generalpowerup.wav");
		 loadSoundEffect("collision", "impacteffect.wav");
		 loadSoundEffect("correct", "correct.wav");
		 loadSoundEffect("wrong", "wrong.wav");
		 loadSoundEffect("fire", "lasershot.wav");
		 loadBackgroundMusic("background", "background_music.wav");
	 }
	 
	 // Load a sound effect and add it to the sound effects map
	 public void loadSoundEffect(String name, String filePath) {
			try {
				ISoundEffect soundEffect = new SoundEffect(filePath);
				addSoundEffect(name, soundEffect);
				System.out.println("Loaded sound effect: " + name);
			} catch (Exception e) {
				System.err.println("Failed to load sound effect: " + name + " - " + e.getMessage());
			}
	 }
	 
	 // Load background music and set it as the current background music
	 public void loadBackgroundMusic(String name, String filePath) {
			try {
				MusicPlayer backgroundMusic = new MusicPlayer(filePath);
				setBackgroundMusic(backgroundMusic);
				backgroundMusic.play();
				System.out.println("Loaded background music: " + name);
			} catch (Exception e) {
				System.err.println("Failed to load background music: " + name + " - " + e.getMessage());
			}
	 }
	 
	 // Get the current music volume
	 public float getMusicVolume() {
		 return musicVolume;
	 }
		 
	 // Set the volume for background music
	 public void setMusicVolume(float volume) {
		 this.musicVolume = volume;
		 if (!isMuted) {
			 backgroundMusic.setVolume(volume); 
		 }
	 }
	 
	 // Get the current sound effects volume
	 public float getSoundVolume() {
		 return soundVolume;
	 }
	 
	 // Set the volume for sound effects
	 public void setSoundVolume(float volume) {
			if (!isMuted) {
				this.soundVolume = volume;
				for (ISoundEffect effect : soundEffects.values()) {
					effect.setVolume(volume);
				}
			}
	 }
	 
	 // Set the background music player
	 public void setBackgroundMusic(IAudioPlayer musicPlayer) {
			if (this.backgroundMusic != null) {
				this.backgroundMusic.dispose(); // Dispose previous music if any
			}
			this.backgroundMusic = musicPlayer;
			this.backgroundMusic.setVolume(musicVolume); // Set volume for the new music
	 }
	 
	 // Play background music if it's not already playing
	 public void playBackgroundMusic() {
		 if (this.backgroundMusic != null) {
			 backgroundMusic.play();  // Play music
		 }
	 }
	 
	 // Stop the background music
	 public void stopBackgroundMusic() {
		if (backgroundMusic != null) {
			backgroundMusic.stop();  // Stop music
		}
	}
	
	// Mute or unmute all audio
	public void muteAll(float musicvol, float soundvol) {
		isMuted = !isMuted;
		
		if(isMuted) {
			backgroundMusic.mute(); // Mute background music
			for (ISoundEffect effect : soundEffects.values()) {
				effect.mute(); // Mute all sound effects
			}
		} else {
			if (backgroundMusic != null) {
				System.out.println(musicvol);
				backgroundMusic.setVolume(musicvol); // Restore background music volume
			}
			for (ISoundEffect effect : soundEffects.values()) {
				effect.setVolume(soundvol); // Restore sound effects volume
			}
		}
	}
	
	// Check if audio is muted
	public boolean isMuted() {
		return isMuted;
	}
	
	// Add a sound effect to the sound effects map
	public void addSoundEffect(String name, ISoundEffect soundEffect) {
		soundEffects.put(name, soundEffect);
	}
	
	// Play a specific sound effect by name
	public void playSoundEffect(String name) {
		ISoundEffect soundEffect = soundEffects.get(name);
		if (soundEffect != null) {
			System.out.println("Playing sound: " + name);
			soundEffect.play(soundVolume);
		} else {
			System.err.println("Sound effect not found: " + name);
		}
	}
	
	// Get all loaded sound effects
	public Map<String, ISoundEffect> getSoundEffects() {
		return soundEffects;
	}

	// Dispose all audio resources
	public void dispose() {
		if (backgroundMusic != null) {
			backgroundMusic.dispose(); // Dispose background music
		}
		for (ISoundEffect effect : soundEffects.values()) {
			effect.dispose(); // Dispose all sound effects
		}
		soundEffects.clear(); // Clear the sound effects map
	}
}
