package big.game;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class Main
{
    private static final boolean LOG_INFO = false;

    private static SessionFactory sessionFactory;

    public static void main(String[] args)
    {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        log("Initialized and configured DB successfully");

        InitializeExtensions();
        StaticDataInjector.Init();
        log("Initialized Extensions successfully");

        for (Account account : Account.getExtension())
            log(account);

        for (CharacterClass characterClass : CharacterClass.getExtension())
            log(characterClass);


        log("Initializing LoginPage");
        //LoginPage loginPage=new LoginPage();
        MainFrame mainFrame = new MainFrame();
    }

    public static void onExit()
    {
        //StaticDataInjector.Instance.onExit();
        sessionFactory.close();
    }

    public static <T> List<T> executeGetQuery(String query, Class<T> aClass)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<T> sessionQuery = session.createQuery(query, aClass);
        List<T> toReturn = sessionQuery.list();
        session.getTransaction().commit();
        session.close();
        return toReturn;
    }

    public static void executeSaveQuery(Object toSave)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(toSave);
        session.getTransaction().commit();
        session.close();
        log("saved " + toSave.toString());
    }

    public static void executeUpdateQuery(Object toUpdate)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(toUpdate);
        session.getTransaction().commit();
        session.close();
        log("updated " + toUpdate.toString());
    }

    private static void InitializeExtensions()
    {
        Account.InitializeExtension();
        CharacterClass.InitializeExtension();
        Item.InitializeExtension();
        Achievement.InitializeExtension();
        CharacterAchievement.InitializeExtension();
    }

    public static void log(Object o)
    {
        if (LOG_INFO)
            System.out.println(o);
    }

}
