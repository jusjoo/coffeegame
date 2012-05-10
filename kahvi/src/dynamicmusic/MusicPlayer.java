package dynamicmusic;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import ec.Debug;

public class MusicPlayer {

	
	private ArrayList<Sound> musicfiles;
	private Sound addSnare;
	private int excitement;
	private Sound addHats;
	private AssetManager manager;
	private boolean isReady;
	
	private float blockLength;
	

	
	
	public MusicPlayer() {
		musicfiles = new ArrayList<Sound>();
		
		manager = new AssetManager();
		manager.load("assets/music/beat.ogg", Sound.class);
		manager.load("assets/music/addsnare.ogg", Sound.class);
		manager.load("assets/music/addhats.ogg", Sound.class);
		
		//manager started loading resources, set state to not ready
		isReady = false;
		
	}

	private void playMuted() {
		for (Sound s : musicfiles) {
			s.setVolume(s.play(), 1);
			
			
		}
		
	}
	
	public void play() {

		if (isReady) {
			for (Sound m : musicfiles) {
				m.setVolume(m.play(), 1);
					
			}
		}
	}
	
	public void stop() {
		for (Sound m : musicfiles) {
			m.stop();
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
				
				musicfiles.add(manager.get("assets/music/beat.ogg", Sound.class));
				addSnare = manager.get("assets/music/addsnare.ogg", Sound.class);
				addHats = manager.get("assets/music/addhats.ogg", Sound.class);
				musicfiles.add(addSnare);
				musicfiles.add(addHats);
				
				isReady = true;
				
			} 
			
		} else {
			

		}
	}
	
}
