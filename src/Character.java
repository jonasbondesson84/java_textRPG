import java.util.ArrayList;

public class Character {

    private String name;
    private int maxHealth;
    private int health;
    private int level;
    private int xp;
    private ArrayList<Item> inventory = new ArrayList<>();
    private ArrayList<Weapon> weaponsList = new ArrayList<>();
    private ArrayList<Misc> miscList = new ArrayList<>();
    private boolean monsterOutSideHouse;
    private int speed;
    private int lastLocation;  //Används för att komma ihåg var man var senast, om man springer ifrån ett monster.
    private boolean triedOpenChest;

    //Constructor
    public Character(String name) {
        this.name = name;
        this.maxHealth = 100;
        this.health=100;
        this.level = 1;
        this.xp = 0;
        this.monsterOutSideHouse = true;
        this.weaponsList.add(new Weapon("Knytnävar",5,"Weapon"));
        this.inventory.add(weaponsList.get(0));
        this.speed = 5;
        this.lastLocation = 100;
        this.triedOpenChest = false;
    }
    //Getters and setters

    public boolean isTriedOpenChest() {
        return triedOpenChest;
    }

    public void setTriedOpenChest(boolean triedOpenChest) {
        this.triedOpenChest = triedOpenChest;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(int lastLocation) {
        this.lastLocation = lastLocation;
    }

    public ArrayList<Weapon> getWeaponsList() {
        return weaponsList;
    }

    public void setWeaponsList(ArrayList<Weapon> weaponsList) {
        this.weaponsList = weaponsList;
    }

    public ArrayList<Misc> getMiscList() {
        return miscList;
    }

    public void setMiscList(ArrayList<Misc> miscList) {
        this.miscList = miscList;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getXp() {
        return xp;
    }
    public void setXp(int xp) {
        this.xp = xp;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }
    public boolean isMonsterOutSideHouse() {
        return monsterOutSideHouse;
    }
    public void setMonsterOutSideHouse(boolean monsterOutSideHouse) {
        this.monsterOutSideHouse = monsterOutSideHouse;
    }


}
