package big.game;

import javax.swing.*;

public class AddClassPage extends JPanel implements Displayable
{

    private JPanel MainPanel;
    private JTextField ClassNameTextField;
    private JTextArea ClassDescriptionTextArea;
    private JButton CreateClassButton;
    private JButton CancelButton;

    public AddClassPage()
    {
        CancelButton.addActionListener(e ->
                MainFrame.Instance.switchToPanel("MainDeveloperPanel"));
        add(MainPanel);
        CreateClassButton.addActionListener(e -> onCreateClass());
    }


    private void onCreateClass()
    {
        //validate data
        String className = ClassNameTextField.getText();
        String classDesc = ClassDescriptionTextArea.getText();

        if (className.length() < 6 || className.length() > 20)
        {
            ResultDialog.Launch("Error", "Class name length has to be within 6-20 characters");
            return;
        }
        if (classDesc.length() < 20 || classDesc.length() > 100)
        {
            ResultDialog.Launch("Error", "Class description length has to be within 6-20 characters");
            return;
        }
        CharacterClass.Create(className, classDesc);
        MainFrame.Instance.switchToPanel("MainDeveloperPanel");
    }

    public void onDisplayed(JFrame parent)
    {
        ClassNameTextField.setText("");
        ClassDescriptionTextArea.setText("");
    }
}
