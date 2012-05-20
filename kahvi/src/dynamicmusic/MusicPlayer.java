package dynamicmusic;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.IdentityMap.Entry;

import ec.Debug;

public class MusicPlayer {

	// all sound files with the excitement level they're triggered at
	private HashMap<Sound, Integer> soundfiles;
	
	private int excitement;
	
	private AssetManager manager;
	
	private boolean isReady;
	
	private HashMap<Sound, Long> playingSoundIDs;
	
	
	public MusicPlayer() {
		soundfiles = new HashMap<Sound, Integer>();
		playingSoundIDs = new HashMap<Sound, Long>();
		
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
			for (Sound s : soundfiles.keySet()) {
				long id = s.play(0f);
				s.setLooping(id, true);
				playingSoundIDs.put(s, id);
				Debug.log("sound playing");
			}
		}
	}
	
	/*
	 * Stops playing all sounds
	 */
	public void stop() {
		// clear all play ID's
		playingSoundIDs.clear();
		
		// stop all sounds
		for (Sound s : soundfiles.keySet()) {
			s.stop();
		}
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
			
			
			// playing music?
			if (!playingSoundIDs.isEmpty()) {
				updateSoundVolumes();
				
				// excitement over 10, set pitch up one note
				if (excitement >= 10) setPitch(1);
			}

		}
	}


	private void setPitch(int i) {
		
		float pitch = getPitch(i);
		
		for (Sound s :playingSoundIDs.keySet()) {
			s.setPitch(playingSoundIDs.get(s), pitch);
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
			
			soundfiles.put(manager.get("assets/music/beat.ogg", Sound.class), 0);
			
			soundfiles.put(manager.get("assets/music/addsnare.ogg", Sound.class), 3);
		
			soundfiles.put(manager.get("assets/music/addhats.ogg", Sound.class), 4);
			
			
			isReady = true;
			
		} 
	}



	private void updateSoundVolumes() {
		// go through all sounds
		for (Sound s : soundfiles.keySet()) {
			
			// if global excitement level is higher than the sounds trigger level, we up the volume
			if(excitement >= soundfiles.get(s)) {
				// sound should be already playing, because we have synchronized play on all sounds
				s.setVolume(playingSoundIDs.get(s), 1);
			} 
			// else we mute the sound
			else {
				s.setVolume(playingSoundIDs.get(s), 0);
			}
		}
	}
	
	private float getPitch(int pitchInNotes) {
		float pitch;
		
		pitch = (float) Math.pow(2.0, (pitchInNotes)/12.0);
		Debug.log("Note Pitch: " + pitch);
		return pitch;
	}
	
}
