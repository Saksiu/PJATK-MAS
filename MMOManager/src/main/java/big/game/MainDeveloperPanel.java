package big.game;

import javax.swing.*;

public class MainDeveloperPanel extends JPanel implements Displayable
{
    private JPanel MainPanel;
    private JButton CreateClassButton;
    private JButton LogoutButton;
    private JButton SetMaxExpLevelButton;
    private JTextField NewMaxLvlTextField;
    private JButton CreateArmorButton;
    private JButton CreateWeaponButton;
    private JButton CreateConsumableButton;
    private JButton CreateClothingButton;
    private JButton CreateAchievementButton;

    public MainDeveloperPanel()
    {

        CreateClassButton.addActionListener(e -> MainFrame.Instance.switchToPanel("AddClassPage"));
        LogoutButton.addActionListener(e ->
                MainFrame.Instance.switchToPanel("LoginPage"));

        add(MainPanel);
        SetMaxExpLevelButton.addActionListener(e -> onMaxLevelChange());

        CreateClothingButton.addActionListener(e -> new ClothingCreationDialog());
        CreateArmorButton.addActionListener(e -> new CommonItemCreationDialog("Armor"));
        CreateWeaponButton.addActionListener(e -> new CommonItemCreationDialog("Weapon"));
        CreateConsumableButton.addActionListener(e -> new CommonItemCreationDialog("Consumable"));
        CreateAchievementButton.addActionListener(e -> new AchievementCreationDialog());
    }

    private void onMaxLevelChange()
    {
        String newMaxLevel = NewMaxLvlTextField.getText();
        if (newMaxLevel.isEmpty())
        {
            ResultDialog.Launch("Error", "Provide a new value");
            return;
        }
        try
        {
            Integer.parseInt(newMaxLevel);
        } catch (NumberFormatException nfe)
        {
            ResultDialog.Launch("Error", "provide a number");
            return;
        }
        if (Integer.parseInt(newMaxLevel) <= Character.getMaxExperienceLevel())
        {
            ResultDialog.Launch("Error", "can only increase max experience level, current value is " + Character.getMaxExperienceLevel());
            return;
        }

        int temp = Character.getMaxExperienceLevel();
        Character.setMaxExperienceLevel(Integer.parseInt(newMaxLevel));
        ResultDialog.Launch("Success", "Increased new Max Experience Level from " + temp + " to " + Character.getMaxExperienceLevel());
    }

    public void onDisplayed(JFrame parent)
    {
        NewMaxLvlTextField.setText("");
    }

}
