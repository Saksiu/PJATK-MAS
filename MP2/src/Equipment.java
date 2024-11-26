import java.util.Arrays;

public class Equipment
{
    private String name;
    private int quantity;
    private Condition condition;
    private Team owningTeam;

    private Equipment(String name, int quantity, Condition condition)
    {
        this.name = name;
        this.quantity = quantity;
        this.condition = condition;
    }

    public static Equipment Create(String[] rawData){
        Team teamRef=Team.tryGetByName(rawData[3]);
        Equipment created= new Equipment(
                rawData[0],
                Integer.parseInt(rawData[1]),
                Condition.valueOf(rawData[2].toUpperCase()));
        teamRef.addEquipment(created);
        created.owningTeam=teamRef;
        return created;
    }

    @Override
    public String toString()
    {
        return  "'" + name + '\'' +
                ", quantity: " + quantity +
                ", condition: " + condition +
                ", owned by: " + owningTeam.name;
    }
    public static String ConstructionFormat(){
        return "<Name> <Quantity> <Condition> <Owning Team>";
    }

    public enum Condition{
        FACTORYNEW,
        MINIMALWEAR,
        WELLWORN;

        public static String allConditionsToString(){
            return Arrays.toString(Condition.values());
        }
    }
}
