package dynamicmusic;

import com.badlogic.gdx.audio.Sound;


/**
 * Sound that has only one playID
 * 
 * @invariant sound != null && playID == -1 when not playing
 */
public class DynamicSound {

	private Sound sound;
	private long playID = -1;
	private float volume;
	
	// how long it takes to fade in/out the sound, default fadeTime set here
	private float fadeTime = 3f;
	
	public DynamicSound(Sound sound) {
		this.sound = sound;
	}
	
	public void loop() {
		playID = sound.loop();
	}

	public void loop(float vol) {
		playID = sound.loop(vol);
	}

	public void play() {
		playID = sound.play();
	}

	public void play(float vol) {
		playID = sound.play(vol);
		volume = vol;
	}

	/**
	 * 
	 * @pre this.isPlaying()
	 */
	public void setLooping(boolean looping) {
		sound.setLooping(playID, looping);
	}

	/**
	 * 
	 * @pre this.isPlaying()
	 */
	public void setPan(float arg1, float arg2) {
		sound.setPan(playID, arg1, arg2);
	}

	/**
	 * 
	 * @pre this.isPlaying()
	 */
	public void setPitch(float arg1) {
		sound.setPitch(playID, arg1);
	}

	/**
	 * 
	 * @pre this.isPlaying()
	 */
	public void setVolume(float arg1) {
		sound.setVolume(playID, arg1);
		volume = arg1;
	}
	
	public float getVolume() {
		return volume;
	}

	public void stop() {
		playID = -1;
		sound.stop();
	}

	
	public boolean isPlaying() {
		return !(playID == -1);
	}

	public float getFadeTime() {
		return fadeTime;
	}

	public void setFadeTime(float fadeTime) {
		this.fadeTime = fadeTime;
	}


}
