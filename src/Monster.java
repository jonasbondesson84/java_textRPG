public class Monster {

    private String name;
    private int maxHealth;
    private int attackValue;
    private int defenceValue;
    private int xpGiven;
    private int difficulty;
    private int speed;

    //Constructor
    public Monster(String name, int maxHealth, int attackValue, int defenceValue, int difficulty, int xpGiven, int speed){
        this.name = name;
        this.maxHealth = maxHealth;
        this.attackValue = attackValue;
        this.defenceValue = defenceValue;
        this.difficulty = difficulty;
        this.xpGiven = xpGiven;
        this.speed = speed;
    }

    //Getters and setters

    public int getDefenceValue() {
        return defenceValue;
    }

    public void setDefenceValue(int defenceValue) {
        this.defenceValue = defenceValue;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttackValue() {
        return attackValue;
    }
    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
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
    public int getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getXpGiven() {
        return xpGiven;
    }

    public void setXpGiven(int xpGiven) {
        this.xpGiven = xpGiven;
    }
}
