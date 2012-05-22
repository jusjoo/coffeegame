package dynamicmusic;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.IdentityMap.Entry;

import ec.Debug;

public class MusicPlayer {


	
	private int excitement;
	
	private AssetManager manager;
	
	private boolean isReady;

	private DynamicSequencer sequencer;

	
	
	public MusicPlayer() {
		sequencer = new DynamicSequencer();
		manager = new AssetManager();
		manager.load("assets/music/beat.ogg", Sound.class);
		manager.load("assets/music/addsnare.ogg", Sound.class);
		manager.load("assets/music/addhats.ogg", Sound.class);
		
		//manager started loading resources, set state to not ready
		isReady = false;
	}


	
	/*
	 * Starts playing the whole sound bank
	 */
	public void play() {
		if (isReady) {
			sequencer.play();
		}
		
	}
	
	/*
	 * Stops playing all sounds
	 */
	public void stop() {
		sequencer.stop();
	}
	
	public void setExcitement(int excitement) {
		this.excitement = excitement;
		Debug.log("Excitement level set to: " + excitement);
	}
	
	public int getExcitementLevel() {
		return excitement;
	}
	
	public void update(float deltaTime) {
		manager.update();
		if (!isReady) {
			// check if all resources are ready
			checkResources();
			
			
		} else {
			// else our assets are ready
			sequencer.update(deltaTime, excitement);
		}
	}






	/*
	 * Checks if all assets are loaded and ready to play
	 * 
	 * sets this.isReady = true when everything is loaded
	 */
	private void checkResources() {
		
		if (	   manager.isLoaded("assets/music/beat.ogg") 
				&& manager.isLoaded("assets/music/addsnare.ogg") 
				&& manager.isLoaded("assets/music/addhats.ogg") ) {
			
			sequencer.addSound(manager.get("assets/music/beat.ogg", Sound.class), 0);	
			sequencer.addSound(manager.get("assets/music/addsnare.ogg", Sound.class), 3);
			sequencer.addSound(manager.get("assets/music/addhats.ogg", Sound.class), 4);
			
			isReady = true;
			
		} 
	}




	

	
}
