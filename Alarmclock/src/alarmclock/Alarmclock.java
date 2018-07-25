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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //TODO code application logic here
        //Sound sound = new Sound ();
        //sound.getFiles();
        //System.out.println(sound.getVolume());
        //sound.setVolume(5);
        //sound.playSound();
        //sound.stopSound();
        //Clock clock = new Clock ();
        //System.out.println(clock.getTime());
        //clock.update();

        Alarm alarm = new Alarm();
        alarm.setAlarmTime(13, 47, 0, Alarm.DADDY_ALARM);
        alarm.setAlarmTime(12, 54, 0, Alarm.MOMMY_ALARM)
        alarm.checkAlarm();
    }
}
