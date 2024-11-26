package big.game;

import javax.swing.*;

public class CharacterCreationPage extends JPanel implements Displayable
{
    private JPanel MainPanel;
    private JTextField CharacterNameTextField;
    private JLabel CharacterNameLabel;
    private JList CharacterClassesList;
    private final DefaultListModel<String> characterClassesListModel;
    private JTextArea CharacterClassDescriptionText;
    private JButton CreateCharacterButton;
    private JLabel ErrorLabel;
    private JButton CancelButton;


    private CharacterClass selectedCharacterClass;

    public CharacterCreationPage()
    {
        CreateCharacterButton.addActionListener(e -> onCreateButtonPressed());
        CharacterClassesList.addListSelectionListener(e -> onCharacterClassSelectionChanged());

        add(MainPanel);

        characterClassesListModel = new DefaultListModel<>();
        CharacterClassesList.setModel(characterClassesListModel);
        CancelButton.addActionListener(e ->
                MainFrame.Instance.switchToPanel("MainPlayerPanel"));
    }

    private void onCreateButtonPressed()
    {
        if (!isCharacterNameValid())
        {
            ResultDialog.Launch("Error", "Character name must be between 6 and 15 characters long");
            return;
        }
        if (Account.LoggedAccount.ownsCharacterWithName(CharacterNameTextField.getText()))
        {
            ResultDialog.Launch("Error", "Already created a character with that name");
            return;
        }

        Character.Create(CharacterNameTextField.getText(), selectedCharacterClass);
        //displayMessage("Created Character",Color.GREEN);
        MainFrame.Instance.switchToPanel("MainPlayerPanel");
    }

    private boolean isCharacterNameValid()
    {
        String name = CharacterNameTextField.getText();
        return name.length() >= 6 && name.length() <= 15;
    }

    private void onCharacterClassSelectionChanged()
    {
        String className = (String) CharacterClassesList.getSelectedValue();
        CharacterClass classRef = CharacterClass.getCharacterClass(className);

        if (classRef == null)
            return;
        selectedCharacterClass = classRef;
        CharacterClassDescriptionText.setText(selectedCharacterClass.getDescription());
    }

    public void onDisplayed(JFrame parent)
    {
        CharacterNameTextField.setText("");
        //setSize(300, 500); //why, just why

        characterClassesListModel.clear();
        if (CharacterClass.getExtension().isEmpty())
        {
            Main.log("CRITICAL ERROR: NO CHARACTERCLASSES FOUND IN EXTENSION, ABANDONING");
            return;
        }

        for (CharacterClass characterClass : CharacterClass.getExtension())
            characterClassesListModel.addElement(characterClass.getName());

        CharacterClassesList.setSelectedIndex(0);

    }
}
