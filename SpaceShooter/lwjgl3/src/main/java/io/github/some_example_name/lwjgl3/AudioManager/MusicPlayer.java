package io.github.some_example_name.lwjgl3.AudioManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer implements IAudioPlayer {
    private Music music; // The music object to play audio
    private float volume; // Current volume level (0.0 to 1.0)
    private boolean isFadingOut; // Flag to indicate if a fade-out is in progress
    private boolean isFadingIn; // Flag to indicate if a fade-in is in progress

    // Constructor to initialize the MusicPlayer with a file path
    public MusicPlayer(String filePath) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal(filePath)); // Load music file
        this.music.setLooping(true); // Set music to loop by default
        this.volume = 0.0f; // Initialize volume to 0
        this.isFadingOut = false; // No fade-out in progress
        this.isFadingIn = false; // No fade-in in progress
    }

    @Override
    public void play() {
        // Play the music if it is not already playing
        if (!music.isPlaying()) {
            music.play();
        }
    }

    @Override
    public void stop() {
        // Stop the music if it is currently playing
        if (music.isPlaying()) {
            music.stop();
        }
    }

    @Override
    public void mute() {
        // Mute the music by setting the volume to 0
        this.volume = 0.0f;
        music.setVolume(this.volume);
    }

    @Override
    public void setVolume(float volume) {
        // Set the volume, clamping it between 0 and 1
        this.volume = Math.max(0, Math.min(volume, 1));
        music.setVolume(this.volume);
    }

    @Override
    public void fadeOut(float duration) {
        // Prevent multiple fade-outs from running simultaneously
        if (isFadingOut) return;

        isFadingOut = true; // Mark fade-out as in progress
        new Thread(() -> {
            float fadeTime = duration; // Total fade-out duration
            float stepTime = 0.1f; // Time interval for each volume reduction step
            float volumeStep = volume / (fadeTime / stepTime); // Volume reduction per step

            // Gradually reduce the volume to 0
            while (volume > 0) {
                volume -= volumeStep;
                if (volume < 0) volume = 0; // Ensure volume does not go below 0
                music.setVolume(volume);

                try {
                    Thread.sleep((long) (stepTime * 1000)); // Wait for the next step
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            music.stop(); // Stop the music after fade-out
            isFadingOut = false; // Mark fade-out as complete
        }).start();
    }

    @Override
    public void fadeIn(float duration) {
        // Prevent multiple fade-ins from running simultaneously
        if (isFadingIn) return;

        isFadingIn = true; // Mark fade-in as in progress
        new Thread(() -> {
            float fadeTime = duration; // Total fade-in duration
            float stepTime = 0.1f; // Time interval for each volume increase step
            float volumeStep = (1.0f - volume) / (fadeTime / stepTime); // Volume increase per step

            // Start playing the music if it is not already playing
            if (!music.isPlaying()) {
                music.play();
            }

            // Gradually increase the volume to 1.0
            while (volume < 1.0f) {
                volume += volumeStep;
                if (volume > 1.0f) volume = 1.0f; // Ensure volume does not exceed 1.0
                music.setVolume(volume);

                try {
                    Thread.sleep((long) (stepTime * 1000)); // Wait for the next step
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            isFadingIn = false; // Mark fade-in as complete
        }).start();
    }

    @Override
    public void dispose() {
        // Dispose of the music object to free resources
        music.dispose();
    }
}
