package big.game;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "CharacterAchievement")
public class CharacterAchievement
{
    private static List<CharacterAchievement> Extension;
    public static void InitializeExtension() {Extension = Main.executeGetQuery("FROM CharacterAchievement", CharacterAchievement.class);}

    @ManyToOne
    private Achievement receivedAchievement;
    @ManyToOne
    private Character receivedBy;
    private LocalDate receivingDate;

    public CharacterAchievement() {}

    private CharacterAchievement(Achievement receivedAchievement, Character receivedBy, LocalDate receivingDate)
    {
        this.receivedAchievement = receivedAchievement;
        this.receivedBy = receivedBy;
        this.receivingDate = receivingDate;
    }

    public static CharacterAchievement Create(Achievement receivedAchievement, Character receivedBy, LocalDate receivingDate)
    {
        CharacterAchievement newCharacterAchievement = new CharacterAchievement(receivedAchievement, receivedBy, receivingDate);
        receivedBy.getReceivedAchievements().add(newCharacterAchievement);
        Extension.add(newCharacterAchievement);
        Main.executeSaveQuery(newCharacterAchievement);
        return newCharacterAchievement;
    }

    public static boolean CharacterHasAchievement(Character character, Achievement achievement)
    {
        for (CharacterAchievement characterAchievement : Extension)
            if (characterAchievement.receivedBy == character && characterAchievement.receivedAchievement == achievement)
                return true;
        return false;
    }

    public Achievement getReceivedAchievement()
    {
        return receivedAchievement;
    }

    public LocalDate getReceivingDate()
    {
        return receivingDate;
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
