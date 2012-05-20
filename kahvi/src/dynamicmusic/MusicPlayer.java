package dynamicmusic;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import ec.Debug;

public class MusicPlayer {

	
	private ArrayList<Sound> soundfiles;
	
	private Sound addSnare;
	
	private int excitement;
	private Sound addHats;
	
	private AssetManager manager;
	
	private boolean isReady;
	
	private HashMap<Sound, Long> playingSoundIDs;
	
	
	public MusicPlayer() {
		soundfiles = new ArrayList<Sound>();
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
			for (Sound s : soundfiles) {
				long id = s.play();
				s.setVolume(id, 1);
				s.setLooping(id, true);
				playingSoundIDs.put(s, id);
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
		for (Sound s : soundfiles) {
			s.stop();
		}
	}
	
	public void setExcitement(int excitement) {

	}
	
	public int getExcitementLevel() {
		return excitement;
	}
	
	public void update(float deltaTime) {
		manager.update();
		if (!isReady) {
			// check if all resources are ready
			if (	   manager.isLoaded("assets/music/beat.ogg") 
					&& manager.isLoaded("assets/music/addsnare.ogg") 
					&& manager.isLoaded("assets/music/addhats.ogg") ) {
				
				soundfiles.add(manager.get("assets/music/beat.ogg", Sound.class));
				addSnare = manager.get("assets/music/addsnare.ogg", Sound.class);
				addHats = manager.get("assets/music/addhats.ogg", Sound.class);
				soundfiles.add(addSnare);
				soundfiles.add(addHats);
				
				isReady = true;
				
			} 
			
		} else {
			

		}
	}
	
}
