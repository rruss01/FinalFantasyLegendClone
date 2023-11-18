package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	
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
	}

	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);	
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
	public void adujstVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-5.0f);
	}
}
