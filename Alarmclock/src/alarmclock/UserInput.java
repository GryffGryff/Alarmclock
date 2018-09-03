package alarmclock;

import java.util.Vector;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;

public class UserInput {
    //////GLOBAL FIELDS//////
    //Buttons
    public static final int SELECT_BUTTON = 1;
    public static final int UP_BUTTON = 2;
    public static final int DOWN_BUTTON = 3;
    //States
    public static final int START = 4;
    public static final int MAIN_MENU = 5;
    public static final int ALARM_MENU = 6;
    public static final int SET_HOUR = 7;
    public static final int SET_MINUTE = 8;
    //MenuItems

    //////FIELDS//////
    //create GpioController
    protected final GpioController gpio = GpioFactory.getInstance();
    // provision gpio pin #01 as an input pin with its internal pull down resistor enabled
    protected final GpioPinDigitalInput select = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
    // provision gpio pin #04 as an input pin with its internal pull down resistor enabled
    protected final GpioPinDigitalInput up = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
    // provision gpio pin #05 as an input pin with its internal pull down resistor enabled
    protected final GpioPinDigitalInput down = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);

    //Pointer to main alarm class
    protected Alarm alarm;
    //Vector that holds all alarm menus
    protected Vector allAlarms = new Vector();
    //Menus
    protected Menu mainMenu = new Menu("Main Menu");
    protected Menu alarmMenu = new Menu("Alarm Menu");
    protected Menu setHour = new Menu("Set Alarm Hour");
    protected Menu setMinute = new Menu("Set Alarm Minute");
    //MenuItems
    protected MenuItem cancelAllAlarms = new MenuItem("Cancel All Alarms");
    protected MenuItem jointAlarm = new MenuItem(Alarm.JOINT_ALARM, alarmMenu);
    protected MenuItem mommyAlarm = new MenuItem(Alarm.MOMMY_ALARM, alarmMenu);
    protected MenuItem daddyAlarm = new MenuItem(Alarm.DADDY_ALARM, alarmMenu);
    protected MenuItem setAlarm = new MenuItem("Set Alarm", setHour);
    protected MenuItem cancelAlarm = new MenuItem("Cancel Alarm");
    //variables used to set alarms
    protected int hour;
    protected int minute;
    //index to access items at a particular index of a menu
    protected int index = 0;
    //current state
    protected int currState = START;
    //current menu
    protected Menu currMenu = mainMenu;
    //current alarm
    protected String whichAlarm;

    //////CONSTRUCTORS//////
    public UserInput(Alarm alarm) {
        //add alarms to all alarms
        allAlarms.add(Alarm.DADDY_ALARM);
        allAlarms.add(Alarm.JOINT_ALARM);
        allAlarms.add(Alarm.MOMMY_ALARM);
        this.alarm = alarm;
        //add items to setHour
        for (int hour = 0; hour <24; hour++) {
            setHour.add(new MenuItem("" + hour));
        }
        //add items to setMinute
        for (int minute = 0; minute <60; minute++) {
            setMinute.add(new MenuItem("" + minute));
        }
        //add menuItems to mainMenu
        mainMenu.add(cancelAllAlarms);
        mainMenu.add(jointAlarm);
        mainMenu.add(mommyAlarm);
        mainMenu.add(daddyAlarm);
        //add menuItems to alarmMenu
        alarmMenu.add(setAlarm);
        alarmMenu.add(cancelAlarm);

        // set shutdown state for select input pin
        this.select.setShutdownOptions(true);
        // set shutdown state for up input pin
        this.up.setShutdownOptions(true);
        // set shutdown state for down input pin
        this.down.setShutdownOptions(true);

        // create and register select gpio pin listener
        select.addListener(new UserInput() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> SELECT BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
                updateState(SELECT_BUTTON);
            }
        });
        // create and register up gpio pin listener
        up.addListener(new UserInput() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> UP BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
                updateState(UP_BUTTON);
            }
        });
        //create and register down gpio pin listener
        down.addListener(new UserInput() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> DOWN BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
                updateState(DOWN_BUTTON);
            }
        });
    }

    //////METHODS//////


    public int getCurrState() {
        return currState;
    }

    public void setCurrState(int currState) {
        this.currState = currState;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Menu getCurrMenu() {
        return currMenu;
    }

    public void setCurrMenu(Menu currMenu) {
        this.currMenu = currMenu;
    }

    public String getWhichAlarm() {
        return whichAlarm;
    }

    public void setWhichAlarm(String whichAlarm) {
        this.whichAlarm = whichAlarm;
    }

    public String currIndexName() {
        return currMenuItem().getName();
    }

    public MenuItem currMenuItem () {
        MenuItem currItem = getCurrMenu().get(getIndex());
        return currItem;
    }

    public void upButton() {
        setIndex(getIndex() +1% getCurrMenu().size());
        display(currIndexName());
    }

    public void downButton() {
        setIndex(getIndex() -1% getCurrMenu().size());
        display(currIndexName());
    }

    public void display(String words) {
        System.out.println(words);
    }

    public void updateState(int button) {
        switch (currState) {
            case START:
                setIndex(0);
                setCurrState(MAIN_MENU);
                setCurrMenu(mainMenu);
                display(getCurrMenu().getName());
                break;
            case MAIN_MENU:
                switch (button) {
                    case UP_BUTTON:
                        upButton();
                        break;
                    case DOWN_BUTTON:
                        downButton();
                        break;
                    case SELECT_BUTTON:
                        if (allAlarms.contains(currIndexName())) {
                            setWhichAlarm(currIndexName());
                            display(whichAlarm);
                            setCurrState(ALARM_MENU);
                            setCurrMenu(alarmMenu);
                            setIndex(0);
                        } else if (currMenuItem().equals(cancelAllAlarms)) {
                            alarm.cancelAllAlarms();
                            setCurrState(START);
                        }
                        break;
                }
                break;
            case ALARM_MENU:
                switch (button) {
                    case UP_BUTTON:
                        upButton();
                        break;
                    case DOWN_BUTTON:
                        downButton();
                        break;
                    case SELECT_BUTTON:
                        if (currMenuItem().equals(cancelAlarm)) {
                            alarm.cancelAlarm(whichAlarm);
                            setCurrState(START);
                        } else if (currMenuItem().equals(setAlarm)) {
                            setIndex(alarm.getAlarmHour(whichAlarm));
                            display(currIndexName());
                            setCurrState(SET_HOUR);
                            setCurrMenu(setHour);
                            break;
                        }
                }
                break;
            case SET_HOUR:
                switch (button) {
                    case UP_BUTTON:
                        upButton();
                        break;
                    case DOWN_BUTTON:
                        downButton();
                        break;
                    case SELECT_BUTTON:
                        this.hour = Integer.decode(currIndexName());
                        setIndex(alarm.getAlarmMinute(whichAlarm));
                        display(currIndexName());
                        setCurrState(SET_MINUTE);
                        setCurrMenu(setMinute);
                        break;
                }
                break;
            case SET_MINUTE:
                switch (button) {
                    case UP_BUTTON:
                        upButton();
                        break;
                    case DOWN_BUTTON:
                        downButton();
                        break;
                    case SELECT_BUTTON:
                        this.minute = Integer.decode(currIndexName());
                        alarm.setAlarmTime(this.hour,this.minute,whichAlarm);
                        setCurrState(START);
                }
        }
    }
}
