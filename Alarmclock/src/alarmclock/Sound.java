/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock;

/**
 *
 * @author elenanisha
 */
public class Sound {
    protected int volume = 3;
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
    
    public void playSound () {
        //play specified sound
        //print out sound is playing
        System.out.println("sound is playing");
    }
    
    public void stopSound () {
        //stop playing sound
        //print out sound stopped playing
        System.out.println("sound stopped playing");
    }
}
