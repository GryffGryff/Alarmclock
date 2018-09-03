package alarmclock;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;

import java.util.Vector;

public class OldUserInput {

    protected final GpioController gpio = GpioFactory.getInstance();

    // provision gpio pin #01 as an input pin with its internal pull down resistor enabled
    protected final GpioPinDigitalInput select = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
    // provision gpio pin #04 as an input pin with its internal pull down resistor enabled
    protected final GpioPinDigitalInput up = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
    // provision gpio pin #05 as an input pin with its internal pull down resistor enabled
    protected final GpioPinDigitalInput down = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);

    ////////////////////STATE_MACHINE////////////////////
    public static final int  START = 1;
    public static final int  MAIN = 2;
    public static final int  SET_HOUR = 3;
    public static final int  SET_MINUTE = 4;
    public static final int  SELECT_BUTTON = 5;
    public static final int  UP_BUTTON = 6;
    public static final int  DOWN_BUTTON = 7;

    protected final Menu daddyAlarm = new Menu("daddyAlarm");
    protected final Menu mommyAlarm = new Menu("mommyAlarm");
    protected final Menu jointAlarm = new Menu("jointAlarm");
    protected final Menu main = new Menu("Main menu");
    protected final Vector setHour = new Vector();
    protected final Vector setMinute = new Vector();
    protected Vector menu = (Vector) main;
    protected String whichAlarm = null;
    protected int menuItem = 0;
    protected int s = START;
    ////////////////////STATE_MACHINE////////////////////


    public int getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(int menuItem) {
        this.menuItem = menuItem;
    }

    public Vector getMenu() {
        return menu;
    }

    public void setMenu(Vector menu) {
        this.menu = menu;
    }

    public Object getCurrentMenuItem() {
        return getMenu().get(getMenuItem());
    }


    public OldUserInput() {
        main.add("Cancel all alarms");
        main.add("Joint Alarm");
        main.add("Daddy Alarm");
        main.add("Mommy Alarm");
        for (int hour = 0; hour <24; hour++) {
            setHour.add(hour);
        }

        for (int minute = 0; minute <60; minute++) {
            setMinute.add(minute);
        }
        daddyAlarm.add("set alarm time");
        daddyAlarm.add("cancel");
        mommyAlarm.add("set alarm time");
        mommyAlarm.add("cancel");
        jointAlarm.add("set alarm time");
        jointAlarm.add("cancel");
    }

    public OldUserInput() {
        // set shutdown state for select input pin
        this.select.setShutdownOptions(true);
        // set shutdown state for up input pin
        this.up.setShutdownOptions(true);
        // set shutdown state for down input pin
        this.down.setShutdownOptions(true);

        // create and register select gpio pin listener
        select.addListener(new OldUserInput() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> SELECT BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
                updateState(SELECT_BUTTON);
            }
        });
        // create and register up gpio pin listener
        up.addListener(new OldUserInput() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> UP BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
                updateState(UP_BUTTON);
            }
        });
        //create and register down gpio pin listener
        down.addListener(new OldUserInput() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> DOWN BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
                updateState(DOWN_BUTTON);
            }
        });
    }

        public void display(String text) {
            System.out.println(text);
        }

        public void updateState(int button) {
            Object menuItem = getCurrentMenuItem();
            switch(s) {
                case START: setMenuItem(0);
                            display((String) getCurrentMenuItem();
                            setMenu((Vector) this.main);
                            s = MAIN;
                    break;
                case SET_HOUR:  setMenuItem(0);

                    break;
                case SET_MINUTE: setMenuItem(0);
                    break;
                case MAIN:
                    switch (button) {
                        case UP_BUTTON:
                            setMenuItem(getMenuItem() -1% getMenu().size());
                            display((String) getCurrentMenuItem());
                            break;
                        case DOWN_BUTTON:
                            setMenuItem(getMenuItem() +1 % getMenu().size());
                            display((String) getCurrentMenuItem());
                            break;
                        case SELECT_BUTTON:
                            if(menuItem instanceof alarmclock.Menu) {
                                this.whichAlarm = ((Menu) menuItem).getName();
                                setMenu((Vector) menuItem);
                                setMenuItem(0);
                                display((String)getCurrentMenuItem());
                            } else if (isCancel(menuItem)) {
                                if (whichAlarm == null) {
                                    System.out.println("all alarms canceled");
                                } else {
                                    //need to go from name to alarm
                                    System.out.println(whichAlarm + " is canceled");
                                }
                            } else if (isSetTime(menuItem)) {
                                System.out.println("display time of " + whichAlarm);
                                setMenu((Vector) menuItem);
                                this.s =
                        }
                    }
                    break;

            }
        }

}
