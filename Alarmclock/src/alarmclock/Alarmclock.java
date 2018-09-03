/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock;

import java.time.LocalTime;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;

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
        alarm.setAlarmTime(13, 47, Alarm.DADDY_ALARM);
        alarm.setAlarmTime(12, 54, Alarm.MOMMY_ALARM);
        alarm.setAlarmTime(16, 5, Alarm.JOINT_ALARM);
        UserInput ui = new UserInput(alarm);
        
        
        //create GpioController
        GpioController gpio = GpioFactory.getInstance();
        // provision gpio pin #01 as an input pin with its internal pull down resistor enabled
        GpioPinDigitalInput select = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
        // provision gpio pin #04 as an input pin with its internal pull down resistor enabled
        GpioPinDigitalInput up = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
        // provision gpio pin #05 as an input pin with its internal pull down resistor enabled
        GpioPinDigitalInput down = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);

        // set shutdown state for select input pin
        select.setShutdownOptions(true);
        // set shutdown state for up input pin
        up.setShutdownOptions(true);
        // set shutdown state for down input pin
        down.setShutdownOptions(true);
        
        // create and register select gpio pin listener
        select.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> SELECT BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
                if(event.getState() == PinState.LOW) {
                    ui.updateState(UserInput.SELECT_BUTTON);
                }
            }
        });
        // create and register up gpio pin listener
        up.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> UP BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
                if(event.getState() == PinState.LOW) {
                    ui.updateState(UserInput.UP_BUTTON);
                }
            }
        });
        //create and register down gpio pin listener
        down.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> DOWN BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
                if(event.getState() == PinState.LOW) {
                    ui.updateState(UserInput.DOWN_BUTTON);
                }
            }
        });
        alarm.checkAlarm();
    }
}
