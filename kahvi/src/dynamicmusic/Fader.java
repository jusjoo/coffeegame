package dynamicmusic;

import ec.Debug;

/**
 * Provides a way to fade a volume in or out. Keep updating with deltaTime
 * so the timer is running. Get the current fade volume with getFadedVolume() 
 */
public class Fader {

	// determines if fading in or out
	private boolean fadingIn;
	
	// start volume
	private float originalVolume;
	
	// target volume
	private float targetVolume;
	
	// fade time
	private float fadeTime;
	
	// fade timer
	private float timer;

	public Fader(float fadeTime, boolean fadingIn, float fromVolume) {
		this.fadeTime = fadeTime;
		this.fadingIn = fadingIn;
		this.originalVolume = fromVolume;
		
		if (fadingIn) {
			targetVolume = 1;
		} else {
			targetVolume = 0;
		}
		
		// set timer to starting value
		this.timer = fadeTime;
	}
	
	public void update(float deltaTime) {
		timer -= deltaTime;
	}
	
	public float getFadedVolume() {
		
		float timerStage = timer/fadeTime;
		float result = timerStage * (originalVolume - targetVolume) + targetVolume;		
		
		Debug.log(result);
		return result;
	}
	
	public boolean isDone() {
		return (timer <= 0);
	}
}
