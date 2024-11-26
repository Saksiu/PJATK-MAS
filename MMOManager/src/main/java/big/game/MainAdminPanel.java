package big.game;

import javax.swing.*;
import java.time.LocalDate;

public class MainAdminPanel extends JPanel implements Displayable
{
    private final DefaultListModel<String> allAccountsListModel;
    private final DefaultListModel<String> allItemsListModel;
    private final DefaultListModel<String> charactersListModel;
    private final DefaultListModel<String> achievementsListModel;
    private JPanel MainPanel;
    private JList AllAccountsList;
    private JButton LogoutButton;
    private JButton AddItemButton;
    private JButton AddAchievementButton;
    private JList CharactersList;
    private JList ItemList;
    private JTextField ItemQuantityTextField;
    private JTextArea SelectedItemDescriptionTextArea;
    private JLabel SelectedItemTypeLabel;
    private JList AllAchievementsList;
    private JTextArea AchievementDescriptionTextArea;
    private Account selectedAccount;

    public MainAdminPanel()
    {

        LogoutButton.addActionListener(e ->
                MainFrame.Instance.switchToPanel("LoginPage"));
        AllAccountsList.addListSelectionListener(e -> onAccountSelected());
        AddItemButton.addActionListener(e -> onAddItemToInventory());
        AddAchievementButton.addActionListener(e -> onAddAchievement());
        CharactersList.addListSelectionListener(e -> onCharacterSelected());
        ItemList.addListSelectionListener(e -> onItemSelectionChanged());

        charactersListModel = new DefaultListModel<>();
        CharactersList.setModel(charactersListModel);

        allAccountsListModel = new DefaultListModel<>();
        AllAccountsList.setModel(allAccountsListModel);

        allItemsListModel = new DefaultListModel<>();
        ItemList.setModel(allItemsListModel);

        achievementsListModel = new DefaultListModel<>();
        AllAchievementsList.setModel(achievementsListModel);

        add(MainPanel);
        AllAchievementsList.addListSelectionListener(e -> onAchievementSelectionChanged());
    }

    private void onAchievementSelectionChanged()
    {
        Achievement selectedAchievement = Achievement.getAchievement((String) AllAchievementsList.getSelectedValue());
        if (selectedAchievement == null) return;
        AchievementDescriptionTextArea.setText(selectedAchievement.getDescription());
    }

    private void onItemSelectionChanged()
    {
        Item selectedItem = Item.getItem((String) ItemList.getSelectedValue());
        if (selectedItem == null) return;
        SelectedItemDescriptionTextArea.setText(selectedItem.getDescription());
        SelectedItemTypeLabel.setText(selectedItem.getItemTypeName());
    }

    private void onAddItemToInventory()
    {
        Item selectedItem = Item.getItem((String) ItemList.getSelectedValue());
        String itemQuantity = ItemQuantityTextField.getText();
        if (itemQuantity.isEmpty())
        {
            ResultDialog.Launch("Error", "Provide item quantity value");
            return;
        }
        try
        {
            Integer.parseInt(itemQuantity);
        } catch (NumberFormatException nfe)
        {
            ResultDialog.Launch("Error", "Provide a number");
            return;
        }

        if (Integer.parseInt(itemQuantity) < 1)
        {
            ResultDialog.Launch("Error", "Item stack size has to be at least 1");
            return;
        }
        if (getSelectedCharacter() == null)
        {
            ResultDialog.Launch("Error", "Selected Account has no Characters");
            return;
        }

        if (Integer.parseInt(itemQuantity) < 1 || Integer.parseInt(itemQuantity) > selectedItem.getMaxStackAmount())
        {
            ResultDialog.Launch("Error", "Can only add a stack of size up to " + selectedItem.getMaxStackAmount()+" for this item");
            return;
        }
        if (getSelectedCharacter().getInventory().isFull())
        {
            ResultDialog.Launch("Error", "Target Inventory full ");
            return;
        }

        ItemStack.Create(getSelectedCharacter().getInventory(), selectedItem, Integer.parseInt(itemQuantity));
        ResultDialog.Launch("Success", "Added new Item stack");
    }


    private Character getSelectedCharacter()
    {
        Account selectedAccount = Account.getAccountWithEmail((String) AllAccountsList.getSelectedValue());
        return selectedAccount.getCharacter((String) CharactersList.getSelectedValue());
    }

    private void onAddAchievement()
    {
        Achievement selectedAchievement = Achievement.getAchievement((String) AllAchievementsList.getSelectedValue());

        if (getSelectedCharacter() == null)
        {
            ResultDialog.Launch("Error", "Selected Account has no Characters");
            return;
        }
        if (CharacterAchievement.CharacterHasAchievement(getSelectedCharacter(), selectedAchievement))
        {
            ResultDialog.Launch("Error", "Selected Character already gained this achievement");
            return;
        }
        CharacterAchievement.Create(selectedAchievement, getSelectedCharacter(), LocalDate.now());
        ResultDialog.Launch("Success", "Successfully added Achievement");
    }

    private void onAccountSelected()
    {
        selectedAccount = Account.getAccountWithEmail(
                (String) AllAccountsList.getSelectedValue());
        if (selectedAccount == null) return;
        charactersListModel.clear();

        for (Character character : selectedAccount.getOwnedCharacters())
            charactersListModel.addElement(character.getName());

        if (!selectedAccount.getOwnedCharacters().isEmpty())
            CharactersList.setSelectedIndex(0);
    }

    private void onCharacterSelected()
    {
        if (selectedAccount == null)
        {
            AddItemButton.setEnabled(false);
            AddAchievementButton.setEnabled(false);

            return;
        }

        AddItemButton.setEnabled(true);
        AddAchievementButton.setEnabled(true);

        if (selectedAccount.getOwnedCharacters().isEmpty())
        {
            AddItemButton.setEnabled(false);
            AddAchievementButton.setEnabled(false);
        } else
        {
            AddItemButton.setEnabled(true);
            AddAchievementButton.setEnabled(true);
        }
    }

    public void onDisplayed(JFrame parent)
    {
        selectedAccount = null;

        charactersListModel.clear();
        allAccountsListModel.clear();
        achievementsListModel.clear();

        AchievementDescriptionTextArea.setText("");
        SelectedItemTypeLabel.setText("");
        SelectedItemDescriptionTextArea.setText("");

        for (Account account : Account.getExtension())
            allAccountsListModel.addElement(account.getEmail());

        if (Account.getExtension().isEmpty())
        {
            AddItemButton.setEnabled(false);
            AddAchievementButton.setEnabled(false);
        } else
        {
            //AddItemButton.setEnabled(true);
            //AddAchievementButton.setEnabled(true);
            Main.log(Account.getExtension().get(0).getEmail() + " " + Account.getExtension().size());
            AllAccountsList.setSelectedIndex(0);
        }

        allItemsListModel.clear();
        if (Item.Extension.isEmpty())
        {
            AddItemButton.setEnabled(false);
        } else
        {
            AddItemButton.setEnabled(true);
            for (Item item : Item.Extension)
                allItemsListModel.addElement(item.getName());
            ItemList.setSelectedIndex(0);
        }

        if (Achievement.getExtension().isEmpty())
        {
            AddAchievementButton.setEnabled(false);
        } else
        {
            AddAchievementButton.setEnabled(true);
            for (Achievement achievement : Achievement.getExtension())
                achievementsListModel.addElement(achievement.getName());
            AllAchievementsList.setSelectedIndex(0);
        }
        ItemQuantityTextField.setText("1");
    }
}
