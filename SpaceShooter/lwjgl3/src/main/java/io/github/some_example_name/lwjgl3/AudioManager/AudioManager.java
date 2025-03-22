package io.github.some_example_name.lwjgl3.AudioManager;

// Add necessary imports
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
	 private static AudioManager instance;
	 private boolean isMuted = false;
	 private IAudioPlayer backgroundMusic;  // For background music
	 private Map<String, ISoundEffect> soundEffects;
	 private float musicVolume;      // Volume control for background music
	 private float soundVolume;      // Volume control for sound effects
	 
	 public static AudioManager getInstance() {
		 if (instance == null) {
			 instance = new AudioManager();
		 }
		 return instance;
	 }
	 // Audio Manager Constructor
	 public AudioManager() {
		 System.out.println("AudioManager created!");
		 this.musicVolume = 1.0f; // Default music volume
		 this.soundVolume = 1.0f; // Default sound volume
		 this.soundEffects = new HashMap<>();
		 loadDefaultSounds();
	 }
	 
	 public void loadDefaultSounds() {
		 loadSoundEffect("life", "lifepowerup.wav");
		 loadSoundEffect("power", "generalpowerup.wav");
		 loadSoundEffect("collision", "impacteffect.wav");
		 loadSoundEffect("correct", "correct.wav");
		 loadSoundEffect("wrong", "wrong.wav");
		 loadBackgroundMusic("background", "background_music.wav");
	 }
	 
	 public void loadSoundEffect(String name, String filePath) {
		    try {
		        ISoundEffect soundEffect = new SoundEffect(filePath);
		        addSoundEffect(name, soundEffect);
		        System.out.println("Loaded sound effect: " + name);
		    } catch (Exception e) {
		        System.err.println("Failed to load sound effect: " + name + " - " + e.getMessage());
		    }
	 }
	 
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
	 
	 public float getMusicVolume() {
		 return musicVolume;
	 }
		 
	 // Set Audio Volume
	 public void setMusicVolume(float volume) {
		 this.musicVolume = volume;
		 if (!isMuted) {
			 backgroundMusic.setVolume(volume); 
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
	
	public void muteAll(float musicvol, float soundvol) {
		isMuted = !isMuted;
		
		if(isMuted) {
			backgroundMusic.mute();
			for (ISoundEffect effect : soundEffects.values()) {
	            effect.mute();
	        }
		} else {
			if (backgroundMusic != null) {
				System.out.println(musicvol);
                backgroundMusic.setVolume(musicvol);
            }
            for (ISoundEffect effect : soundEffects.values()) {
                effect.setVolume(soundvol);
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
