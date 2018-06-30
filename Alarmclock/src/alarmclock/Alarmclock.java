/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock;

import java.time.LocalTime;

/**
 *
 * @author elenanisha
 */
public class Alarmclock {
    private LocalTime alarmTime;
    
    public LocalTime getAlarmTime() {
        return this.alarmTime;
    }
    
    public void setAlarmTime(LocalTime time) {
        this.alarmTime = time;
    }
    
    public void setAlarmTime(int hour, int minute, int second) {
        this.alarmTime = LocalTime.of(hour, minute, second);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // commit 
        Sound sound = new Sound ();
        System.out.println(sound.getVolume());
        sound.setVolume(5);
        sound.playSound();
        sound.stopSound();
        Clock clock = new Clock ();
        System.out.println(clock.getTime());
        clock.update();
    }
    
}
