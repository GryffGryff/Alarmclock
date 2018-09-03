package alarmclock;

import java.time.LocalTime;

public class Alarm {
    

    public static final String  DADDY_ALARM = "Anind's Alarm";
    public static final String  MOMMY_ALARM = "Jen's Alarm";
    public static final String  JOINT_ALARM = "Joint Alarm";

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

    public void setAlarmTime(int hour, int minute, String s) {
        if(s.equals(DADDY_ALARM)) {
            this.daddyTime = LocalTime.of(hour, minute);
        }
        if(s.equals(MOMMY_ALARM)) {
            this.mommyTime = LocalTime.of(hour, minute);
        }
        if(s.equals(JOINT_ALARM)) {
            this.jointTime = LocalTime.of(hour, minute);
        }
    }

    public int getAlarmHour(String s) {
        if(s.equals(DADDY_ALARM)) {
            return this.daddyTime.getHour();
        } else if(s.equals(MOMMY_ALARM)) {
            return this.mommyTime.getHour();
        } else if(s.equals(JOINT_ALARM)) {
            return this.jointTime.getHour();
        }
        return 0;
    }

    public int getAlarmMinute(String s) {
        if(s.equals(DADDY_ALARM)) {
            return this.daddyTime.getMinute();
        } else if(s.equals(MOMMY_ALARM)) {
            return this.mommyTime.getMinute();
        } else if(s.equals(JOINT_ALARM)) {
            return this.jointTime.getMinute();
        }
        return 0;
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

    public void cancelAllAlarms () {
        this.jointTime = null;
        this.daddyTime = null;
        this.mommyTime = null;
        System.out.println("all alarms have been canceled");
    }

    public void cancelAlarm (String s) {
        if(s.equals(DADDY_ALARM)) {
            this.daddyTime = null;
            System.out.println("daddy's alarm was canceled");
        }
        if(s.equals(MOMMY_ALARM)) {
            this.mommyTime = null;
            System.out.println("mommy's alarm was canceled");
        }
        if(s.equals(JOINT_ALARM)) {
            this.jointTime = null;
            System.out.println("the joint alarm was canceled");
        }
    }
}
