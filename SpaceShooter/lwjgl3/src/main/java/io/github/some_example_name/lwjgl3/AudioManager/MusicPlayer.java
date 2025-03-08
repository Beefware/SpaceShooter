package io.github.some_example_name.lwjgl3.AudioManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class MusicPlayer implements IAudioPlayer {
    private Music music;
    private float volume;
    private boolean isFadingOut;
    private boolean isFadingIn;

    public MusicPlayer(String filePath) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        this.music.setLooping(true); // Loop by default
        this.volume = 0.0f;
        this.isFadingOut = false;
    }

    @Override
    public void play() {
        if (!music.isPlaying()) {
            music.play();
        }
    }

    @Override
    public void stop() {
        if (music.isPlaying()) {
            music.stop();
        }
    }

    @Override
    public void setVolume(float volume) {
    	this.volume = Math.max(0, Math.min(volume, 1)); // Clamp volume between 0 and 1
        music.setVolume(this.volume);
        music.setVolume(volume);
    }
    
    @Override
    public void fadeOut(float duration) {
        if (isFadingOut) return; // Prevent multiple fade-outs

        isFadingOut = true;
        new Thread(() -> {
            float fadeTime = duration;
            float stepTime = 0.1f;  // Reduce volume every 0.1 seconds
            float volumeStep = volume / (fadeTime / stepTime);

            while (volume > 0) {
                volume -= volumeStep;
                if (volume < 0) volume = 0;
                music.setVolume(volume);

                try {
                    Thread.sleep((long) (stepTime * 1000)); // Convert seconds to milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            music.stop(); // Stop the music after fade-out
            isFadingOut = false;
        }).start();
    }
    
    @Override
    public void fadeIn(float duration) {
        if (isFadingIn) return; // Prevent multiple fade-ins

        isFadingIn = true;
        new Thread(() -> {
            float fadeTime = duration;
            float stepTime = 0.1f;  // Increase volume every 0.1 seconds
            float volumeStep = (1.0f - volume) / (fadeTime / stepTime);
            
            if(!music.isPlaying()) {
            	music.play();
            }
            
            while (volume < 1.0f) {
                volume += volumeStep;
                if (volume > 1.0f) volume = 1.0f;
                music.setVolume(volume);

                try {
                    Thread.sleep((long) (stepTime * 1000)); // Convert seconds to milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            isFadingIn = false;
        }).start();
    }
    
    
    @Override
    public void dispose() {
        music.dispose();
    }
}
