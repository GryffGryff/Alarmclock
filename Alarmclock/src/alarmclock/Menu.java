package alarmclock;

import java.util.Vector;

public class Menu extends Vector {
    private String name;

    Menu(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public synchronized boolean add(Object o) {
        if (o instanceof MenuItem) {
            return super.add(o);
        }
        else {
            return false;
        }
    }

    public MenuItem get (int index) {
        return (MenuItem) super.get(index);
    }
}
