package alarmclock;

import java.util.Vector;

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
        setIndex((getIndex() +1)% getCurrMenu().size());
        System.out.println("Index is "+index);
        display(currIndexName());
    }

    public void downButton() {
        setIndex((getIndex() + (getCurrMenu().size()-1))% getCurrMenu().size());
        System.out.println("Index is "+index);
        display(currIndexName());
    }

    public void display(String words) {
        System.out.println(words);
    }

    public void updateState(int button) {
        //System.out.println("In update state with button " + button + " and state " + currState);
        switch (currState) {
            case START:
                //System.out.println("Index number is " + getIndex());
                setIndex(0);
                setCurrState(MAIN_MENU);
                setCurrMenu(mainMenu);
                System.out.println("Length of main menu is " + mainMenu.size());
                System.out.println("Index number is " + getIndex());
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
                            System.out.println("index num is "+index);
                            setCurrState(SET_HOUR);
                            setCurrMenu(setHour);
                            display(currIndexName());
                            System.out.println("size of setHour is "+setHour.size());
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
                        setCurrState(SET_MINUTE);
                        setCurrMenu(setMinute);
                        display(currIndexName());
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
