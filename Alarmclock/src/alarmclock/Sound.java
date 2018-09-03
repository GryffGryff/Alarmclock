/*
 * This class plays audio for the alarmclock
 */
package alarmclock;

import java.io.*;
import java.util.Iterator;
import java.util.Vector;
import java.util.Random;
import sun.audio.*;
/**
 *
 * @author elenanisha
 */
public class Sound {
    protected int volume = 3;
    protected Alarm alarm = null;
    protected AudioStream audioStream = null;
    protected long playLength = 120000;
    protected Vector jWav = new Vector();
    protected Vector mWav = new Vector();
    protected Vector dWav = new Vector();
    protected String defDir = "/home/pi/IdeaProjects/Alarmclock/Alarmclock/Data";
    protected String daddyDir = "/home/pi/IdeaProjects/Alarmclock/Alarmclock/Data/Daddy/";
    protected String mommyDir = "/home/pi/IdeaProjects/Alarmclock/Alarmclock/Data/Mommy/";

    public Sound(Alarm alarm) {
        //adds all .wav files in data directory to vector v
        getFiles();
        //set alarm to current instance of alarm
        this.alarm = alarm;
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

    public void setRandomAudioStream(Vector vector) {
        if (!vector.isEmpty()) {
            String s = pickWav(vector);
            System.out.println("playing "+s);
            this.audioStream = makeAudioStream(s);
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

    public void addFiles(File[] fList, Vector vector) {
        for (File file : fList){
            if (file.isFile() && !file.isHidden()){
                vector.add(file.getPath());
                addWav(file.getPath());
                System.out.println(file.getPath());
            }
        }
    }

    public void getFiles(){
        File directory = new File(defDir);
        File dDirectory = new File(daddyDir);
        File mDirectory = new File(mommyDir);
        //get all the files from a directory

        File[] dList = dDirectory.listFiles();
        File[] mList = mDirectory.listFiles();

        addFiles(dList, dWav);
        addFiles(mList, mWav);
    }

    public void addWav(String filepath) {
        //add String filepath to field v
        jWav.add(filepath);
    }

    public String pickWav(Vector vector) {
        //make a random obj
        Random random = new Random();
        String wav = (String) vector.get(random.nextInt(vector.size()));
        System.out.println("picked wav is " + wav);
        //return the string that is at a random index of field v
        return wav;
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
        System.out.println("we made an audiostream!");
        //returns new AudioStream aStream
        return audioStream;
    }

    public void playSound () {
        //play specified sound
        System.out.println("set vetor");
        try {
            //set audioStream
            Vector vector = new Vector();
            System.out.println("created vector");
            if(this.alarm.getWhichAlarm().equals(Alarm.DADDY_ALARM)) {
                vector = dWav;
                System.out.println("daddy's alarm");
            } else if(this.alarm.getWhichAlarm().equals(Alarm.MOMMY_ALARM)) {
                vector = mWav;
                System.out.println("mommy's alarm");
            } else if(this.alarm.getWhichAlarm().equals(Alarm.JOINT_ALARM)) {
                System.out.println("joint alarm");
                vector = jWav;
            }
            Iterator iterator = vector.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
            setRandomAudioStream(vector);
            // play the audio clip with the audioplayer class
            startSound(getAudioStream());
            //sleep for playLength
            Thread.sleep(playLength);
            //stop playing audio clip
            stopSound();
        }   catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public void startSound (AudioStream audioStream) {
        AudioPlayer.player.start(audioStream);
        System.out.println("Sound was started");
        System.out.println("audiostream = " + audioStream);
    }

    public void stopSound () {
        //stop playing sound
        AudioPlayer.player.stop(getAudioStream());
        //print out sound stopped playing
        System.out.println("sound stopped playing");
    }
}
