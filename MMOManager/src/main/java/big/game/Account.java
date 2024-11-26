package big.game;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Account")
public class Account
{
    private static final int DEFAULT_MAX_CHARACTERS = 5;
    @Transient
    public static Account LoggedAccount;
    private static List<Account> Extension;
    public static List<Account> getExtension()
    {
        return Extension;
    }
    public static void InitializeExtension()
    {
        Extension = Main.executeGetQuery("FROM Account", Account.class);
    }

    private String email;
    private String password;
    private int maxCharacters;
    private boolean isPremium;
    private float subscriptionCost;

    private LocalDate subscriptionStartDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownedBy", fetch = FetchType.EAGER)
    private List<Character> ownedCharacters = new LinkedList<>();

    public Account() {}

    public Account(String email, String password, int maxCharacters)
    {
        this.email = email;
        this.password = password;
        this.maxCharacters = maxCharacters;
    }

    public static Account Create(String email, String password)
    {
        Account account = new Account(email, password, DEFAULT_MAX_CHARACTERS);
        Extension.add(account);
        Main.executeSaveQuery(account);
        Main.log("Created new Account!");
        return account;
    }
    public static void LogInto(String Email)
    {
        LoggedAccount = getAccountWithEmail(Email);
    }

    public static void LogOut()
    {
        LoggedAccount = null;
    }

    public static Account getAccountWithEmail(String Email)
    {
        for (Account account : Extension)
            if (Objects.equals(account.email, Email))
            {
                Main.log("Found account " + account + " in Extension");
                return account;
            }
        Main.log("Could not find account with " + Email + " Email in Extension");
        return null;
    }

    public static boolean AccountWithEmailExists(String email)
    {
        for (Account a : Extension)
            if (Objects.equals(a.email, email))
            {
                Main.log("found account with mail " + a);
                return true;
            }
        Main.log("did not find account with email " + email);
        return false;
    }

    public static void verifySubscriptionStateAcrossAccounts(){
        for(Account account : Extension)
            if(account.subscriptionStartDate.isBefore(LocalDate.now().minusYears(1)))
                account.isPremium=false;
    }

    public Character getCharacter(String name)
    {
        for (Character character : getOwnedCharacters())
            if (Objects.equals(character.getName(), name))
                return character;
        return null;
    }
    public boolean hasMaxCharactersCreated(){
        return getOwnedCharacters().size()>=maxCharacters;
    }

    public boolean ownsCharacterWithName(String name)
    {
        for (Character ownedCharacter : ownedCharacters)
            if (Objects.equals(ownedCharacter.getName(), name))
                return true;
        return false;
    }

    public void purchaseSubscription(float yearlyCost){
        if(isPremium) return;
        subscriptionStartDate=LocalDate.now();

    }

    public List<Character> getOwnedCharacters()
    {
        return ownedCharacters;
    }

    public boolean isPasswordCorrect(String password)
    {
        return Objects.equals(this.password, password);
    }

    public String getEmail()
    {
        return email;
    }

    @Override
    public String toString()
    {
        return "Account{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isPremium=" + isPremium +
                ", ownedCharacters=" + ownedCharacters +
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

    public void addOwnedCharacter(Character character)
    {
        if (ownedCharacters.contains(character))
            return;

        ownedCharacters.add(character);
    }
}
