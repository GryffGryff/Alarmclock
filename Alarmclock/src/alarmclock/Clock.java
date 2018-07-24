/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock;

import java.time.LocalTime;

/**
 *
 * @author Gryffin
 */
public class Clock {

    public LocalTime getTime(){
        return LocalTime.now();
    }

    public void update(){
        while(true) {
            System.out.println(getTime());
            try{
                Thread.sleep(60000);
            }
            catch(InterruptedException e){
                System.out.println("while loop interrupted");
            }
        }
    }
}
