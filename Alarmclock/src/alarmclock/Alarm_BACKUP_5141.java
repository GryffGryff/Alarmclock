package alarmclock;

import java.time.LocalTime;

public class Alarm {
<<<<<<< HEAD
=======
    
    protected LocalTime alarmTime = null;
    protected boolean played = false;
>>>>>>> e924bcc67d6da4a7bde3ae75cb5a950381ee1785

    public static final String  DADDY_ALARM = "daddy";
    public static final String  MOMMY_ALARM = "mommy";
    public static final String  JOINT_ALARM = "joint";

    protected String whichAlarm = "";
    protected LocalTime jointTime = null;
    protected LocalTime daddyTime = null;
    protected LocalTime mommyTime = null;

    public String getWhichAlarm() {
        return this.whichAlarm;
    }

    public void setWhichAlarm(String whichAlarm) {
        System.out.println(whichAlarm);
        this.whichAlarm = whichAlarm;
    }

    public LocalTime getAlarmTime(String s) {
        //returns an alarm time
        if(s.equals(DADDY_ALARM)) {
            return this.daddyTime;
        }
        if(s.equals(MOMMY_ALARM)) {
            return this.mommyTime;
        }
        if(s.equals(JOINT_ALARM)) {
            return this.jointTime;
        }
        return null; //does this work??
    }

    public void setAlarmTime(LocalTime time, String s) {
        if(s.equals(DADDY_ALARM)) {
            this.daddyTime = time;
        }
        if(s.equals(MOMMY_ALARM)) {
            this.mommyTime = time;
        }
        if(s.equals(JOINT_ALARM)) {
            this.jointTime = time;
        }
    }

    public void setAlarmTime(int hour, int minute, int second, String s) {
        if(s.equals(DADDY_ALARM)) {
            this.daddyTime = LocalTime.of(hour, minute, second);
        }
        if(s.equals(MOMMY_ALARM)) {
            this.mommyTime = LocalTime.of(hour, minute, second);
        }
        if(s.equals(JOINT_ALARM)) {
            this.jointTime = LocalTime.of(hour, minute, second);
        }
    }

    public LocalTime getTime(){
        return LocalTime.now();
    }

    public void checkAlarm() {
        LocalTime time = getTime();
        int minute = time.getMinute();
        Sound sound = new Sound(this);
        while (true) {
            time = getTime();
            if (minute != time.getMinute()) {
                System.out.println(time);
                minute = time.getMinute();
            }
            //System.out.println("alarmTime hour and minute: "+alarmTime.getHour()+", "+alarmTime.getMinute());
            //System.out.println("time hour and minute: "+time.getHour()+", "+time.getMinute());
            if (jointTime != null) {
                if (time.getMinute() == jointTime.getMinute() && time.getHour() == jointTime.getHour()) {
                    //System.out.println("playing");
                    setWhichAlarm(JOINT_ALARM);
                    sound.playSound();
                }
            }

            if (daddyTime != null) {
                if (time.getMinute() == daddyTime.getMinute() && time.getHour() == daddyTime.getHour()) {
                    System.out.println("playing");
                    setWhichAlarm(DADDY_ALARM);
                    sound.playSound();
                }
            }

            if (mommyTime != null) {
                if (time.getMinute() == mommyTime.getMinute() && time.getHour() == mommyTime.getHour()) {
                    //System.out.println("playing");
                    setWhichAlarm(MOMMY_ALARM);
                    sound.playSound();

                }
            }

            try {
                Thread.sleep(59998);
            }
            catch (InterruptedException ie) {
                System.out.println("InterruptedException");
            }
        }
    }
}
