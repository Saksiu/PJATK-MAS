package big.game;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.InputMismatchException;

@Entity(name = "Weapon")
@PrimaryKeyJoinColumn(name = "id")
public class Weapon extends Item
{
    private float damage;

    public Weapon()
    {
    }

    private Weapon(String name, String description, int maxStackSize, float damage) throws InputMismatchException
    {
        super(name, description, maxStackSize);
        this.damage = damage;
    }

    public static Weapon Create(String name, String description, int maxStackSize, float damage) throws InputMismatchException
    {
        Weapon newWeapon = new Weapon(name, description, maxStackSize, damage);
        Item.Extension.add(newWeapon);
        Main.executeSaveQuery(newWeapon);
        Main.log("Created and saved new Weapon");
        return newWeapon;
    }

    public float getDamage()
    {
        return damage;
    }
}
