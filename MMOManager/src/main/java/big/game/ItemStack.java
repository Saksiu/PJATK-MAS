package big.game;

import javax.persistence.*;

@Entity(name = "ItemStack")
public class ItemStack
{
    @ManyToOne
    private Inventory inInventory;

    @ManyToOne
    private Item containsItem;

    private int itemsAmount;

    public ItemStack() {}

    private ItemStack(Inventory inInventory, Item containsItem, int itemsAmount)
    {
        this.inInventory = inInventory;
        this.containsItem = containsItem;
        this.itemsAmount = itemsAmount;
    }

    public static ItemStack Create(Inventory inInventory, Item containsItem, int itemsAmount)
    {
        ItemStack newItemStack = new ItemStack(inInventory, containsItem, itemsAmount);
        inInventory.getOwnedItemStacks().add(newItemStack);
        Main.executeSaveQuery(newItemStack);
        return newItemStack;
    }

    public Item getContainsItem()
    {
        return containsItem;
    }

    public int getItemsAmount()
    {
        return itemsAmount;
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
