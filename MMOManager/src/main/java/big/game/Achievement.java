package big.game;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity(name = "Achievement")
public class Achievement
{
    private static List<Achievement> Extension;
    public static List<Achievement> getExtension()
    {
        return Extension;
    }
    public static void InitializeExtension() {Extension = Main.executeGetQuery("FROM Achievement", Achievement.class);}

    private String name;
    private String description;
    public Achievement() {}

    private Achievement(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public static Achievement Create(String name, String description)
    {
        Achievement newAchievement = new Achievement(name, description);
        Extension.add(newAchievement);
        Main.executeSaveQuery(newAchievement);
        return newAchievement;
    }

    public static Achievement getAchievement(String name)
    {
        for (Achievement achievement : Extension)
            if (Objects.equals(achievement.getName(), name))
                return achievement;
        return null;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
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
