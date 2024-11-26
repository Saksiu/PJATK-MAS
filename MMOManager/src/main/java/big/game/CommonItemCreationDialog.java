package big.game;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.InputMismatchException;

public class CommonItemCreationDialog extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField FloatValueTextField;
    private JLabel FloatValueLabel;
    private BaseItemCreationForm BaseItemCreationFormRef;

    private final String createdItemTypeName;
    private final String valueName;

    public CommonItemCreationDialog(String typeToCreate)
    {
        setTitle("Create " + typeToCreate);
        createdItemTypeName = typeToCreate;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

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

        switch (createdItemTypeName)
        {
            case "Armor":
                valueName = "Defence";
                break;
            case "Weapon":
                valueName = "Damage";
                break;
            default:
            case "Consumable":
                valueName = "Duration";
                break;
        }
        FloatValueLabel.setText(valueName + ':');

        pack();
        setVisible(true);
        setResizable(false);
    }

    private float getValue() throws InputMismatchException
    {
        String floatValue = FloatValueTextField.getText();

        if (floatValue.isEmpty())
            throw new InputMismatchException(valueName + " field empty");

        try
        {
            Float.parseFloat(floatValue);
        } catch (NumberFormatException nfe)
        {
            throw new InputMismatchException("non-float value provided in the " + valueName + " field");
        }

        return Float.parseFloat(floatValue);
    }

    private void onOK()
    {
        try
        {
            switch (createdItemTypeName)
            {
                case "Armor":
                    Armor.Create(BaseItemCreationFormRef.getItemName(1, 20), BaseItemCreationFormRef.getDescription(1, 200),
                            BaseItemCreationFormRef.getMaxStackSize(1, Integer.MAX_VALUE), getValue());
                    break;
                case "Weapon":
                    FloatValueLabel.setText("Damage:");
                    Weapon.Create(BaseItemCreationFormRef.getItemName(1, 20), BaseItemCreationFormRef.getDescription(1, 200),
                            BaseItemCreationFormRef.getMaxStackSize(1, Integer.MAX_VALUE), getValue());
                    break;
                default:
                case "Consumable":
                    FloatValueLabel.setText("Duration:");
                    Consumable.Create(BaseItemCreationFormRef.getItemName(1, 20), BaseItemCreationFormRef.getDescription(1, 200),
                            BaseItemCreationFormRef.getMaxStackSize(1, Integer.MAX_VALUE), getValue());
                    break;
            }
        } catch (InputMismatchException ime)
        {
            ResultDialog.Launch("Error", ime.getMessage());
            return;
        }
        ResultDialog.Launch("Success", "Successfully created " + createdItemTypeName + " item");
        dispose();
    }

    private void onCancel()
    {
        // add your code here if necessary
        dispose();
    }
}
