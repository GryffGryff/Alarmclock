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

        //Alarm alarm = new Alarm();
        //alarm.setAlarmTime(13, 47, 0);
        //alarm.checkAlarm();
        

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        // final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.LOW);

        // set shutdown state for this pin
        //pin.setShutdownOptions(true, PinState.LOW);

        // provision gpio pin #01 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput select = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
        // provision gpio pin #04 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput up = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
        // provision gpio pin #05 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput down = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);
        
        // set shutdown state for this input pin
        select.setShutdownOptions(true);
        // set shutdown state for this input pin
        up.setShutdownOptions(true);
        // set shutdown state for this input pin
        down.setShutdownOptions(true);

        // create and register gpio pin listener
        select.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> SELECT BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
            }
        });
        up.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> UP BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
            }
        });
        down.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> DOWN BUTTON STATE CHANGE: " + event.getPin() + " = " + event.getState());
            }
        });

        System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)
        while(true) {
            try{
                Thread.sleep(500);
            } catch (java.lang.InterruptedException ie) {
            }
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        //gpio.shutdown();

        //System.out.println("Exiting ControlGpioExample");
        //Alarm alarm = new Alarm();
        //alarm.setAlarmTime(11, 28, 0, Alarm.DADDY_ALARM);
        //alarm.checkAlarm();
    }
}
