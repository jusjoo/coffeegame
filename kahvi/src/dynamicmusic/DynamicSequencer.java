package dynamicmusic;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.IdentityMap.Entry;

public class DynamicSequencer {

	// all sound files with the excitement level they're triggered at
	private HashMap<DynamicSound, Integer> soundfiles;
	private boolean isPlaying;
	
	private HashMap<DynamicSound, Fader> fadingSounds;
	
	public DynamicSequencer(){
		soundfiles = new HashMap<DynamicSound, Integer>();
		fadingSounds = new HashMap<DynamicSound, Fader>();
		
		// not playing anything
		isPlaying = false;
	}
	
	public void play() {
		for (DynamicSound s : soundfiles.keySet()) {
			s.loop();
		}
		isPlaying = true;
	}
	
	public void stop() {
		// stop all sounds
		for (DynamicSound s : soundfiles.keySet()) {
			s.stop();
		}
		
		isPlaying = false;
	}
	
	public void update(float deltaTime, int excitement) {
		// playing music?
		if (isPlaying) {
			updateSoundVolumes(excitement);
			updateFadingSounds(deltaTime);
			
			// excitement over 10, set pitch up one note
			if (excitement >= 10){
				setPitch(1);
			} else {
				setPitch(0);
			}
		}
	}
	
	private void updateFadingSounds(float deltaTime) {
		
		ArrayList<DynamicSound> removedKeys = null;
		
		for (java.util.Map.Entry<DynamicSound, Fader> e : fadingSounds.entrySet()) {
			// update the fader timers
			e.getValue().update(deltaTime);
			
			// update Sound volume
			e.getKey().setVolume(e.getValue().getFadedVolume());
			
			// tag the Sounds that are done fading
			if (e.getValue().isDone()) {
				// initialize if not already
				if (removedKeys == null) removedKeys = new ArrayList<DynamicSound>();
				removedKeys.add(e.getKey());
			}
		}
		
		// actually remove the faded items from fadingSounds
		if (removedKeys != null) {
			for (DynamicSound s: removedKeys) {
				fadingSounds.remove(s);
			}
		}
	}

	/* 
	 * sets pitch for all sounds
	 * 
	 * pitch i given in notes
	 */
	private void setPitch(int i) {
		
		float pitch = getPitch(i);
		
		for (DynamicSound s : soundfiles.keySet()) {
			s.setPitch(pitch);
		}
	}
	
	/*
	 * Converts the amount of pitch in notes to the sounds playing speed multiplier
	 */
	private float getPitch(int pitchInNotes) {
		float pitch;
		
		pitch = (float) Math.pow(2.0, (pitchInNotes)/12.0);
		return pitch;
	}
	
	private void updateSoundVolumes(int excitement) {
		// go through all sounds
		for (DynamicSound s : soundfiles.keySet()) {
			
			// if global excitement level is higher than the sounds trigger level, we up the volume
			if(excitement >= soundfiles.get(s)) {
				// sound should be already playing, because we have synchronized play on all sounds
				fadeIn(s);
			} 
			// else we mute the sound
			else {
				s.setVolume(0);
			}
		}
	}
	
	public void addSound(Sound s, int triggerLevel) {
		soundfiles.put(new DynamicSound(s), triggerLevel);
	}
	
	private void fadeIn(DynamicSound sound) {
		
		// check that the sound isn't already fading in or playing
		if (sound.getVolume() == 0 && !fadingSounds.containsKey(sound)) {
			fadingSounds.put(sound, new Fader(sound.getFadeTime(), true, 0));
		}
	}
	
}
