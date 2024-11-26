package big.game;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class MainPlayerPanel extends JPanel implements Displayable
{
    private final DefaultListModel<String> charactersListModel;
    private final DefaultListModel<String> achievementsListModel;
    private JPanel MainPanel;
    private JLabel AccountEmailLabel;
    private JList CharactersList;
    private JButton CharacterCreationButton;
    private JButton LogoutButton;
    private JPanel InventoryPanel;
    private JTextArea ItemDescriptionTextArea;
    private JLabel AchievementReceivingDateLabel;
    private JTextArea AchievementDescriptionTextArea;
    private JList AchievementsList;


    public MainPlayerPanel()
    {

        CharacterCreationButton.addActionListener(e -> onCharacterCreation());
        LogoutButton.addActionListener(e -> onLogout());

        charactersListModel = new DefaultListModel<>();
        CharactersList.setModel(charactersListModel);

        achievementsListModel = new DefaultListModel<>();
        AchievementsList.setModel(achievementsListModel);

        add(MainPanel);
        CharactersList.addListSelectionListener(e -> onSelectCharacter());
        AchievementsList.addListSelectionListener(e -> onAchievementSelectionChanged());
    }

    private void onAchievementSelectionChanged()
    {
        Character selectedCharacter = Account.LoggedAccount.getCharacter((String) CharactersList.getSelectedValue());
        if (selectedCharacter == null) return;
        String selectedAchievementName = (String) AchievementsList.getSelectedValue();

        CharacterAchievement selectedAchievement = null;
        for (CharacterAchievement achievement : selectedCharacter.getReceivedAchievements())
            if (Objects.equals(achievement.getReceivedAchievement().getName(), selectedAchievementName))
                selectedAchievement = achievement;

        if (selectedAchievement == null) return;
        AchievementDescriptionTextArea.setText(selectedAchievement.getReceivedAchievement().getDescription());
        AchievementReceivingDateLabel.setText(selectedAchievement.getReceivingDate().toString());
    }

    private void onCharacterCreation()
    {
        if (CharacterClass.getExtension().isEmpty())
        {
            ResultDialog.Launch("Error", "No Character Classes have been created! A Developer has to create one first!");
            return;
        }
        if(Account.LoggedAccount.hasMaxCharactersCreated()){
            ResultDialog.Launch("Error","Already created max amount of characters");
            return;
        }
        MainFrame.Instance.switchToPanel("CharacterCreationPage");

    }


    private void onSelectCharacter()
    {
        if (Account.LoggedAccount == null) return;
        //Main.log("Selected character changed");
        Character selectedCharacter = Account.LoggedAccount.getCharacter((String) CharactersList.getSelectedValue());
        if (selectedCharacter == null) return;

        ItemDescriptionTextArea.setText("");
        InventoryPanel.removeAll();
        Inventory inventory = selectedCharacter.getInventory();

        //ACHIEVEMENTS
        Main.log("onSelectCharacter, validated, adding achievements to list");
        achievementsListModel.clear();
        for (CharacterAchievement achievement : selectedCharacter.getReceivedAchievements())
            achievementsListModel.addElement(achievement.getReceivedAchievement().getName());

        if (!selectedCharacter.getReceivedAchievements().isEmpty())
            AchievementsList.setSelectedIndex(0);


        //INVENTORY

        MouseListener ml = new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                ItemStackSlotPanel pressedSlot = (ItemStackSlotPanel) e.getSource();
                //Main.log("pressed on item stack "+pressedSlot.getDisplayedItemStack().getContainsItem().getName());
                ItemDescriptionTextArea.setText(pressedSlot.getDisplayedItemStack().getContainsItem().getDescription());
            }
        };
        InventoryPanel.setLayout(new WrapLayout());

        int counter = 0;
        ItemStackSlotPanel itemStackSlotPanel;
        for (ItemStack itemStack : inventory.getOwnedItemStacks())
        {
            itemStackSlotPanel = new ItemStackSlotPanel(itemStack);
            itemStackSlotPanel.addMouseListener(ml);
            InventoryPanel.add(itemStackSlotPanel);
            counter++;
        }
        //Main.log("counter state after adding all item stacks: "+counter+" "+inventory.getSlots());
        while (counter < inventory.getSlots())
        {
            InventoryPanel.add(new ItemStackSlotPanel());
            //Main.log("adding empty slot "+counter);
            counter++;
        }

        revalidate();
        repaint();


    }

    private void onLogout()
    {
        Account.LogOut();
        MainFrame.Instance.switchToPanel("LoginPage");
    }

    public void onDisplayed(JFrame parent)
    {
        charactersListModel.clear();
        achievementsListModel.clear();

        ItemDescriptionTextArea.setText("");
        AchievementReceivingDateLabel.setText("");
        AchievementDescriptionTextArea.setText("");
        InventoryPanel.removeAll();
        for (Character character : Account.LoggedAccount.getOwnedCharacters())
            charactersListModel.addElement(character.getName());
        AccountEmailLabel.setText(Account.LoggedAccount.getEmail());

        if (!Account.LoggedAccount.getOwnedCharacters().isEmpty())
            CharactersList.setSelectedIndex(0);
    }
}
