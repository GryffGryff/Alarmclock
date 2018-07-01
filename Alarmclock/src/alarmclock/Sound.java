/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock;

import java.io.*;
import sun.audio.*;
/**
 *
 * @author elenanisha
 */
public class Sound {
    protected int volume = 3;
    protected AudioStream audioStream = null;
    protected long playLength = 120000;

    public Sound() {
        setAudioStream("/Users/Gryffin/IdeaProjects/Alarmclock/Data/pirate.wav");
    }

    public int getVolume () {
        //returns value of int volume
        return volume;
    }
    
    public void setVolume (int newVolume) {
        //set volume to newVolume
        volume = newVolume;
        //print out new volume
        System.out.println("The new volume is:" + volume);
    }

    public AudioStream getAudioStream() {
        return this.audioStream;
    }

    public void setAudioStream(AudioStream audioStream) {
        this.audioStream = audioStream;
    }

    public void setAudioStream(String filepath) {
        try {
            // open the sound file as a Java input stream
            InputStream in = new FileInputStream(filepath);

            // create an audiostream from the inputstream
            this.audioStream = new AudioStream(in);
        } catch (Exception e) {
        }
    }

    public void playSound () {
        //play specified sound
        try {
            // play the audio clip with the audioplayer class
            AudioPlayer.player.start(getAudioStream());
            Thread.sleep(playLength);
            AudioPlayer.player.stop(getAudioStream());
        }   catch (Exception e) {
            System.out.println("Exception");
        }
        //print out sound is playing
        System.out.println("sound is playing");
    }
    
    public void stopSound () {
        //stop playing sound
        AudioPlayer.player.stop(getAudioStream());
        //print out sound stopped playing
        System.out.println("sound stopped playing");
    }
}
