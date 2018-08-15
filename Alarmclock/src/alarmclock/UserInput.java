package alarmclock;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;

import java.util.Vector;

public class UserInput {

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

    protected final Vector alarm = new Vector();
    protected final Vector main = new Vector();
    protected final Vector setHour = new Vector();
    protected final Vector setMinute = new Vector();
    protected int menuItem = 0;
    protected Vector menu = main;
    protected int s = START;
    ////////////////////STATE_MACHINE////////////////////

    public UserInput() {
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
        alarm.add("set alarm time");
        alarm.add("cancel");
    }

    public UserInput() {
        // set shutdown state for select input pin
        this.select.setShutdownOptions(true);
        // set shutdown state for up input pin
        this.up.setShutdownOptions(true);
        // set shutdown state for down input pin
        this.down.setShutdownOptions(true);
    }

    // create and register select gpio pin listener
        select.addListener(new GpioPinListenerDigital() {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            // display pin state on console
            System.out.println(" --> SELECT BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
        }
    });
    // create and register up gpio pin listener
        up.addListener(new GpioPinListenerDigital() {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            // display pin state on console
            System.out.println(" --> UP BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
        }
    });
        //create and register down gpio pin listener
        down.addListener(new GpioPinListenerDigital() {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            // display pin state on console
            System.out.println(" --> DOWN BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
        }
    });

        public void display(String text) {
            System.out.println(text);
        }

        public int updateState(GpioPinDigitalInput button) {
            switch(s) {
                case START: menuItem = 0;
                            this.menu = this.main;
                            display(this.menu[menuItem]);
                    break;
                case SET_HOUR:  menuItem = 0;

                    break;
                case SET_MINUTE: menuItem = 0;
                    break;
                case MAIN: menuItem = 0;
                    break;

            }
        }

}
