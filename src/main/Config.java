package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	GamePanel gp;
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	
	public void saveConfig() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			
			// full screen
			if(gp.fullScreenOn == true) {
				bw.write("on");
			}
			if(gp.fullScreenOn == false) {
				bw.write("off");
			}
			bw.newLine();
			
			// music volume
			bw.write(String.valueOf(gp.music.volumeScale));
			bw.newLine();
			
			// sfx volume
			bw.write(String.valueOf(gp.se.volumeScale));
			bw.newLine();
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadConfig() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
			
			String s = br.readLine();
			
			// full screen
			if(s.equals("on")) {gp.fullScreenOn = true;}
			if(s.equals("off")) {gp.fullScreenOn = false;}
			
			// music
			s = br.readLine();
			gp.music.volumeScale = Integer.parseInt(s);
			
			// sfx
			s = br.readLine();
			gp.se.volumeScale = Integer.parseInt(s);
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
