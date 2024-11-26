package big.game;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.InputMismatchException;

@Entity(name = "Armor")
@PrimaryKeyJoinColumn(name = "id")
public class Armor extends Item
{
    private float defence;

    public Armor()
    {
    }

    private Armor(String name, String description, int maxStackSize, float defence)
    {
        super(name, description, maxStackSize);
        this.defence = defence;
    }

    public static Armor Create(String name, String description, int maxStackSize, float defence) throws InputMismatchException
    {
        Armor newArmor = new Armor(name, description, maxStackSize, defence);
        Item.Extension.add(newArmor);
        Main.executeSaveQuery(newArmor);
        Main.log("Created and saved new Armor");
        return newArmor;
    }
}
