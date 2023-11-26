package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sound/overworld.wav");
		soundURL[1] = getClass().getResource("/sound/SFX_PURCHASE.wav");
		soundURL[2] = getClass().getResource("/sound/SFX_GET_ITEM_1.wav");
		soundURL[3] = getClass().getResource("/sound/SFX_GO_INSIDE.wav");
		soundURL[4] = getClass().getResource("/sound/SFX_HEAL_AILMENT.wav");
		soundURL[5] = getClass().getResource("/sound/title.wav");
		soundURL[6] = getClass().getResource("/sound/SFX_TAKE_DAMAGE.wav");
		soundURL[7] = getClass().getResource("/sound/slash.wav");
		soundURL[8] = getClass().getResource("/sound/DAMAGE_ENEMY.wav");
		soundURL[9] = getClass().getResource("/sound/SFX_SAVE.wav");
		soundURL[10] = getClass().getResource("/sound/SFX_LEVEL_UP.wav");
		soundURL[11] = getClass().getResource("/sound/SFX_CURSOR.wav");
		soundURL[12] = getClass().getResource("/sound/SFX_START_MENU.wav");
		soundURL[13] = getClass().getResource("/sound/SFX_INTRO_HIP.wav");
		soundURL[14] = getClass().getResource("/sound/SFX_INTRO_HOP.wav");
		soundURL[15] = getClass().getResource("/sound/fireball.wav");
	}

	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);	
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
	public void checkVolume() {
		switch(volumeScale) {
		case 0: volume = -80f; break;
		case 1: volume = -20f; break;
		case 2: volume = -12f; break;
		case 3: volume = -5f; break;
		case 4: volume = 1f; break;
		case 5: volume = 6f; break;
		}
		fc.setValue(volume);
	}
}
