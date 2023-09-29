import java.util.ArrayList;

public class Weapon implements Item {

    private String name;
    private int attackValue;
    private int defenceValue;
    private String type;

    //Constructor
    public Weapon(String name, int attackValue, String type) {
        this.name = name;
        this.attackValue = attackValue;
        this.type = type;
    }

    //getters and setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDefenceValue() {
        return defenceValue;
    }

    public void setDefenceValue(int defenceValue) {
        this.defenceValue = defenceValue;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAttackValue() {
        return attackValue;
    }
    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }


}
