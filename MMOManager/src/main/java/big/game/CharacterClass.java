package big.game;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity(name = "CharacterClass")
public class CharacterClass
{
    private static List<CharacterClass> Extension;
    public static List<CharacterClass> getExtension()
    {
        return Extension;
    }

    public static void InitializeExtension() {Extension = Main.executeGetQuery("FROM CharacterClass", CharacterClass.class);}

    private String name;
    private String description;

    public CharacterClass() {}

    private CharacterClass(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
    public static CharacterClass Create(String name, String description)
    {
        CharacterClass characterClass = new CharacterClass(name, description);
        Extension.add(characterClass);
        Main.executeSaveQuery(characterClass);
        Main.log("Created new CharacterClass!");
        return characterClass;
    }

    public static CharacterClass getCharacterClass(String name)
    {
        for (CharacterClass characterClass : Extension)
            if (Objects.equals(characterClass.getName(), name)) return characterClass;
        return null;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "CharacterClass{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
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
