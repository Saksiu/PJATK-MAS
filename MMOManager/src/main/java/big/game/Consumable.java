package big.game;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.InputMismatchException;

@Entity(name = "Consumable")
@PrimaryKeyJoinColumn(name = "id")
public class Consumable extends Item
{
    private float duration;

    public Consumable()
    {
    }

    private Consumable(String name, String description, int maxStackSize, float duration)
    {
        super(name, description, maxStackSize);
        this.duration = duration;
    }

    public static Consumable Create(String name, String description, int maxStackSize, float duration) throws InputMismatchException
    {
        Consumable newConsumable = new Consumable(name, description, maxStackSize, duration);
        Item.Extension.add(newConsumable);
        Main.executeSaveQuery(newConsumable);
        Main.log("Created and saved new Consumable");
        return newConsumable;
    }

    public float getDuration()
    {
        return duration;
    }
}
