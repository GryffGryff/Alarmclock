package alarmclock;

import java.time.LocalTime;

public class Alarm {
    protected LocalTime alarmTime = null;
    protected boolean played = false;

    public LocalTime getAlarmTime() {
        //returns alarmTime
        return this.alarmTime;
    }

    public void setAlarmTime(LocalTime time) {
        this.alarmTime = time;
    }

    public void setAlarmTime(int hour, int minute, int second) {
        this.alarmTime = LocalTime.of(hour, minute, second);
    }

    public LocalTime getTime(){
        return LocalTime.now();
    }

    public void checkAlarm() {
        LocalTime time = getTime();
        int minute = time.getMinute();
        Sound sound = new Sound();
        while (true) {
            time = getTime();
            if (time.isBefore(alarmTime)) {
                played = false;
            }
            if (time.isAfter(alarmTime) && played == false) {
                sound.playSound();
                played = true;
            }
            if (minute != time.getMinute()) {
                System.out.println(time);
                minute = time.getMinute();
            }
        }
    }
}
