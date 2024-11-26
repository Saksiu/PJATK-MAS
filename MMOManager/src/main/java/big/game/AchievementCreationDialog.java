package big.game;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.InputMismatchException;

public class AchievementCreationDialog extends JDialog
{
    private JPanel contentPane;
    private JButton buttonCreate;
    private JButton buttonCancel;
    private JTextField AchievementNameTextField;
    private JTextArea AchievementDescriptionTextArea;

    public AchievementCreationDialog()
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCreate);

        buttonCreate.addActionListener(e -> onCreate());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(true);
    }

    private void onCreate()
    {
        // add your code here

        try
        {
            Achievement.Create(getName(1, 20), getDescription(1, 200));
        } catch (InputMismatchException ime)
        {
            ResultDialog.Launch("Error", ime.getMessage());
            return;
        }
        ResultDialog.Launch("Success", "Successfully created new Achievement");
        dispose();
    }

    private String getName(int minLength, int maxLength)
    {
        String name = AchievementNameTextField.getText();
        if (name.isEmpty())
            throw new InputMismatchException("item name field empty");

        //Main.log("item name length "+name.length()+" "+(name.length()<minLength||name.length()>maxLength));
        if (name.length() < minLength || name.length() > maxLength)
            throw new InputMismatchException("provided item name length is " + name.length() + " expected " + minLength + "-" + maxLength);
        return name;
    }

    private String getDescription(int minLength, int maxLength)
    {
        String desc = AchievementDescriptionTextArea.getText();
        if (desc.length() < minLength || desc.length() > maxLength)
            throw new InputMismatchException("provided item description length is " + desc.length() + " expected " + minLength + "-" + maxLength);

        return desc;
    }

    private void onCancel()
    {
        dispose();
    }
}
