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
    private LocalTime time;
    public LocalTime getTime(){
        return LocalTime.now();
    }
    public void update(){
        int repeat = 0;
        while(repeat <= 20) {
            System.out.println(time = LocalTime.now());
            try{
                Thread.sleep(10000);
            }
            catch(InterruptedException e){
                System.out.println("while loop interrupted");
            }
            repeat++;
        }
    }
}
