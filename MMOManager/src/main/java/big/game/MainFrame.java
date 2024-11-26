package big.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame
{
    public static MainFrame Instance;
    private final Map<String, Displayable> mappedPanels = new HashMap<>();
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MainFrame()
    {
        if (Instance != null)
        {
            Main.log("More than 1 MainFrame instances! This is horrible!");
            return;
        }
        Instance = this;
        setTitle("Login");

        setSize(100, 100);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                Main.onExit();
                dispose();
            }
        });

        cardLayout = (CardLayout) cardPanel.getLayout();

        setContentPane(cardPanel);
        addPanel(new LoginPage(), "LoginPage");
        addPanel(new MainPlayerPanel(), "MainPlayerPanel");
        addPanel(new CharacterCreationPage(), "CharacterCreationPage");
        addPanel(new MainAdminPanel(), "MainAdminPanel");
        addPanel(new MainDeveloperPanel(), "MainDeveloperPanel");
        addPanel(new AddClassPage(), "AddClassPage");

        switchToPanel("LoginPage");


        // Main.log(cardPanel.getLayout()+" "+cardPanel.getComponent(0)+" "+cardPanel.getComponent(0).isVisible());
    }

    public void switchToPanel(String panelName)
    {
        cardLayout.show(cardPanel, panelName);
        setTitle(panelName);
        mappedPanels.get(panelName).onDisplayed(this);

        setResizable(true);
        pack();
        validate();
        repaint();

        //setResizable(false);
        //setMinimumSize(new Dimension(500,500));
        //setSize(500,500);
    }

    private void addPanel(Displayable toAdd, String name)
    {
        cardPanel.add((JPanel) toAdd, name);
        Main.log("adding " + name);
        mappedPanels.put(name, toAdd);
    }
}
