package io.github.some_example_name.lwjgl3.AudioManager;

// Add necessary imports
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
	private boolean isMuted = false;
	 private IAudioPlayer backgroundMusic;  // For background music
	 private Map<String, ISoundEffect> soundEffects;
	 private float musicVolume;      // Volume control for background music
	 private float soundVolume;      // Volume control for sound effects
	 
	 // Audio Manager Constructor
	 public AudioManager() {
		 System.out.println("AudioManager created!");
		 this.musicVolume = 1.0f; // Default music volume
		 this.soundVolume = 1.0f; // Default sound volume
		 this.soundEffects = new HashMap<>();	     
	 }
	 
	 public float getMusicVolume() {
		 return musicVolume;
	 }
	 
	 // Set Audio Volume
	 public void setMusicVolume(float volume) {
	        this.musicVolume = volume;
	        for (ISoundEffect effect : soundEffects.values()) {
	            effect.setVolume(volume);
	        }
	 }
	 
	 public float getSoundVolume() {
		 return soundVolume;
	 }
	 
	 // Set Audio Volume
	 public void setSoundVolume(float volume) {
		 	if (!isMuted) {
		 		this.soundVolume = volume;
		        for (ISoundEffect effect : soundEffects.values()) {
		            effect.setVolume(volume);
		        }
		 	}
	        
	 }
	 
	 
	// Set Background Music
	    public void setBackgroundMusic(IAudioPlayer musicPlayer) {
	        if (this.backgroundMusic != null) {
	            this.backgroundMusic.dispose(); // Dispose previous music if any
	        }
	        this.backgroundMusic = musicPlayer;
	        this.backgroundMusic.setVolume(musicVolume);
	    }
	 
	 // Play background music if its not already playing
	 public void playBackgroundMusic() {
		 if (backgroundMusic != null) {
			 backgroundMusic.play();  // Play music
	     }
	 }
	 
	// Stop background music
	public void stopBackgroundMusic() {
		if (backgroundMusic != null) {
			backgroundMusic.stop();  // Stop music
	    }
	}
	
	public void muteAll() {
		isMuted = !isMuted;
		
		if(isMuted) {
			backgroundMusic.mute();
			for (ISoundEffect effect : soundEffects.values()) {
	            effect.mute();
	        }
		} else {
			if (backgroundMusic != null) {
                backgroundMusic.setVolume(musicVolume);
            }
            for (ISoundEffect effect : soundEffects.values()) {
                effect.setVolume(soundVolume);
            }
		}
		
	}
	
	public boolean isMuted() {
		return isMuted;
	}
	
	// Add Sound Effect
    public void addSoundEffect(String name, ISoundEffect soundEffect) {
        soundEffects.put(name, soundEffect);
    }
    
    // Play a Sound Effect
    public void playSoundEffect(String name) {
        ISoundEffect soundEffect = soundEffects.get(name);
        if (soundEffect != null) {
        	System.out.println("Playing sound: " + name);
            soundEffect.play(soundVolume);
        } else {
        	System.err.println("Sound effect not found: " + name);
        }
    }
    
    // Get Loaded Sound Effects
    public Map<String, ISoundEffect> getSoundEffects() {
        return soundEffects;
    }

    // Dispose all audio resources
    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
        for (ISoundEffect effect : soundEffects.values()) {
            effect.dispose();
        }
        soundEffects.clear();
    }
	
}
