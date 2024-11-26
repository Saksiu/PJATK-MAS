package big.game;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Character")
public class Character
{
    public static int startingExperienceLevel = 1;
    private static int maxExperienceLevel;
    @OneToMany(mappedBy = "receivedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<CharacterAchievement> receivedAchievements = new ArrayList<>();

    private String name;
    private int experienceLevel;
    @ManyToOne(fetch = FetchType.EAGER)
    private CharacterClass characterClass;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Inventory inventory;
    @ManyToOne(fetch = FetchType.EAGER)
    private Account ownedBy;

    public Character() {}
    public Character(String name, CharacterClass characterClass, Account ownedBy)
    {
        this.setName(name);
        this.setCharacterClass(characterClass);
        this.setOwnedBy(ownedBy);
    }
    public static Character Create(String name, CharacterClass characterClass)
    {
        Character newCharacter = new Character(name, characterClass, Account.LoggedAccount);

        newCharacter.setInventory(Inventory.Create(newCharacter));

        Main.executeSaveQuery(newCharacter);
        return newCharacter;
    }

    public static int getMaxExperienceLevel()
    {
        return maxExperienceLevel;
    }

    public static void setMaxExperienceLevel(int newValue)
    {
        Main.log("setting max exp level to " + newValue);
        maxExperienceLevel = newValue;
        StaticDataInjector.Instance.updateData();
    }


    public List<CharacterAchievement> getReceivedAchievements()
    {
        return receivedAchievements;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public void setInventory(Inventory inventory)
    {
        if (this.inventory != null || inventory == null) return;
        this.inventory = inventory;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public void setCharacterClass(CharacterClass characterClass)
    {
        this.characterClass = characterClass;
    }

    public void setOwnedBy(Account ownedBy)
    {
        if (ownedBy == null) return;

        if (this.ownedBy != null && ownedBy != this.ownedBy)
        {
            Main.log("Character " + getName() + " already owned by different Account");
            return;
        }

        this.ownedBy = ownedBy;
        ownedBy.addOwnedCharacter(this);
    }

    @Override
    public String toString()
    {
        return "Character{" +
                "name='" + name + '\'' +
                ", experienceLevel=" + experienceLevel +
                ", characterClass=" + characterClass +
                ", inventory=" + inventory +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
}
