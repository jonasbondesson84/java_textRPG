public class Item {

    private String name;
    private int attackValue;
    private int defenceValue;

    //Constructor
    public Item(String name, int attackValue, int defenceValue) {
        this.name = name;
        this.attackValue = attackValue;
        this.defenceValue = defenceValue;
    }

    //getters and setters

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
