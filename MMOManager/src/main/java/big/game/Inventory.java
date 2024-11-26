package big.game;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "Inventory")
public class Inventory
{
    @OneToOne(mappedBy = "inventory", optional = false)
    private Character ownedBy;
    private final int slots = 20;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inInventory", fetch = FetchType.EAGER)
    private final Set<ItemStack> ownedItemStacks = new HashSet<>();

    public Inventory() {}

    private Inventory(Character ownedBy)
    {
        this.ownedBy = ownedBy;
    }

    public static Inventory Create(Character ownedBy)
    {
        if (ownedBy == null)
        {
            Main.log("CRITICAL ERROR: ATTEMPTED TO CREATE INVENTORY FOR NULL CHARACTER");
            return null;
        }
        Inventory newInventory = new Inventory(ownedBy);
        //Main.executeSaveQuery(newInventory);
        return newInventory;
    }

    public int getSlots()
    {
        return slots;
    }

    public boolean isFull()
    {
        return getOwnedItemStacks().size() >= getSlots();
    }

    @Override
    public String toString()
    {
        return "Inventory{" +
                "id=" + id +
                ", slots=" + slots +
                '}';
    }

    public Set<ItemStack> getOwnedItemStacks()
    {
        return ownedItemStacks;
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
