package big.game;


import javax.persistence.*;
import java.util.List;

@Entity(name = "StaticData")
public class StaticDataInjector
{

    @Transient
    public static StaticDataInjector Instance;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private int maxExpLevel = 100;

    public StaticDataInjector()
    {
    }

    public StaticDataInjector(int maxExpLevel)
    {
        this.maxExpLevel = maxExpLevel;
    }

    public static void Init()
    {
        if (Instance != null) return;

        List<StaticDataInjector> result = Main.executeGetQuery("FROM StaticData", StaticDataInjector.class);
        if (result.isEmpty())
        {
            Instance = new StaticDataInjector(1);
            Main.executeSaveQuery(Instance);
            Instance.injectData();
            Main.log("initialized first StaticDataInjector, you should not see this after first execution if database was wiped");
            return;
        }
        Instance = Main.executeGetQuery("FROM StaticData", StaticDataInjector.class).get(0);

        Instance.injectData();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void onExit()
    {
        maxExpLevel = Character.getMaxExperienceLevel();
        Main.executeSaveQuery(this);
    }

    public void updateData()
    {
        maxExpLevel = Character.getMaxExperienceLevel();
        Main.executeUpdateQuery(this);
    }

    public void injectData()
    {
        Character.setMaxExperienceLevel(maxExpLevel);
    }
}
