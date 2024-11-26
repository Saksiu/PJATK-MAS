package big.game;

import javax.persistence.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;

@Entity(name = "Item")
public abstract class Item
{
    protected static List<Item> Extension;
    public static void InitializeExtension()
    {
        Extension = Main.executeGetQuery("FROM Item", Item.class);
    }
    protected String name;
    protected int maxStackAmount;

    protected String description;

    public Item() {}

    protected Item(String name, String description, int maxStackAmount) throws InputMismatchException
    {
        if (getItem(name) != null)
            throw new InputMismatchException("Item with name " + name + " already exists");

        this.name = name;
        this.description = description;
        this.maxStackAmount = maxStackAmount;
    }

    public static Item getItem(String name)
    {
        for (Item item : Extension)
            if (Objects.equals(item.getName(), name))
                return item;
        return null;
    }

    public static boolean ItemWithNameExists(String name)
    {
        for (Item item : Extension)
            if (Objects.equals(item.getName(), name))
                return true;
        return false;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getMaxStackAmount()
    {
        return maxStackAmount;
    }


    //public abstract String getTypeName();

    //not proud of this, but works
    public String getItemTypeName()
    {
        String[] fullTypeNameSplit = getClass().getName().split("\\.");
        return fullTypeNameSplit[fullTypeNameSplit.length - 1];
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
