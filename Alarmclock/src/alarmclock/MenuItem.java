package alarmclock;

public class MenuItem {
    //////FIELDS//////
    protected String name;
    protected Menu attachedMenu = null;

    //////CONSTRUCTORS//////
    public MenuItem(String name) {
        this.name = name;
    }

    public MenuItem(String name, Menu attachedMenu) {
        this.name = name;
        this.attachedMenu = attachedMenu;
    }

    //////METHODS//////

    public Menu getAttachedMenu() {
        return attachedMenu;
    }

    public void setAttachedMenu(Menu attachedMenu) {
        this.attachedMenu = attachedMenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
