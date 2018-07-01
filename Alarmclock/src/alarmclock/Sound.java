/*
 * This class plays audio for the alarmclock
 */
package alarmclock;

import java.io.*;
import java.util.Vector;
import java.util.Random;
import sun.audio.*;
/**
 *
 * @author elenanisha
 */
public class Sound {
    protected int volume = 3;
    protected AudioStream audioStream = null;
    protected long playLength = 120000;
    protected Vector v = new Vector();
    protected String defDir = "/Users/Gryffin/IdeaProjects/Alarmclock/Data/";

    public Sound() {
        //adds all .wav files in data directory to vector v
        getFiles();
        // sets audioStream to random wav
        setAudioStream(pickWav());
    }

    public int getVolume () {
        //returns value of int volume
        return volume;
    }

    public void setVolume (int newVolume) {
        //set volume to newVolume
        this.volume = newVolume;
        //print out new volume
        System.out.println("The new volume is:" + this.volume);
    }

    public AudioStream getAudioStream() {
        //returns value of audioStream
        return this.audioStream;
    }

    public void setAudioStream(String filepath) {
        //makes filepath an AudioStream and set the field audioStream to that value
        this.audioStream = makeAudioStream(filepath);
    }

    public void setAudioStream() {
        if (!v.isEmpty()) {
            this.audioStream = makeAudioStream(pickWav());
        }
    }

    public long getPlayLength() {
        //returns the value of playLength
        return playLength;
    }

    public void setPlayLength(long playLength) {
        //sets the field playLength to the value of playLength
        this.playLength = playLength;
    }

    public String getDefDir() {
        //returns the value of defDir
        return this.defDir;
    }

    public void setDefDir(String defDir) {
        //sets the value of field defDir to the value of String defDir
        this.defDir = defDir;
    }

    public void getFiles(){
        File directory = new File(defDir);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
                addWav(file.getPath());
                System.out.println(file.getPath());
            }
        }
    }

    public void addWav(String filepath) {
        //add String filepath to field v
        v.add(filepath);
    }

    public String pickWav() {
        //make a random obj
        Random random = new Random();
        //return the string that is at a random index of field v
        return (String) v.get(random.nextInt(v.size()));
    }

    public AudioStream makeAudioStream(String filepath) {
        AudioStream audioStream = null;
        try {
            // open the sound file as a Java input stream
            InputStream in = new FileInputStream(filepath);

            // create an audiostream from the inputstream
            audioStream = new AudioStream(in);
        } catch (Exception e) {
        }
        //returns new AudioStream aStream
        return audioStream;
    }

    public void playSound () {
        //play specified sound
        try {
            // play the audio clip with the audioplayer class
            AudioPlayer.player.start(getAudioStream());
            //sleep for playLength
            Thread.sleep(playLength);
            //stop playing audio clip
            stopSound();
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
