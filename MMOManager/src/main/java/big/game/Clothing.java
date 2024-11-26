package big.game;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import java.awt.*;
import java.util.InputMismatchException;

@Entity(name = "Clothing")
@PrimaryKeyJoinColumn(name = "id")
public class Clothing extends Item
{
    @Enumerated(EnumType.STRING)
    private ClothingType clothingType;
    private Color color;

    private Clothing(String name, String description, int maxStackSize, ClothingType clothingType, Color color)
    {
        super(name, description, maxStackSize);
        this.clothingType = clothingType;
        this.color = color;
    }

    public Clothing() {}

    public static Clothing Create(String name, String description, int maxStackSize, ClothingType clothingType, Color color) throws InputMismatchException
    {
        Clothing newClothing = new Clothing(name, description, maxStackSize, clothingType, color);
        Item.Extension.add(newClothing);
        Main.executeSaveQuery(newClothing);
        Main.log("Created and saved new Clothing");
        return newClothing;
    }

    enum ClothingType
    {
        HEAD,
        BODY,
        LEGS,
        FEET
    }
}


