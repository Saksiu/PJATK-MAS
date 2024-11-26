package big.game;

import javax.swing.*;
import java.util.InputMismatchException;

public class BaseItemCreationForm extends JPanel
{
    private JPanel BaseItemCreationPanel;
    private JTextField ItemNameTextField;
    private JTextArea ItemDescriptionTextArea;
    private JTextField ItemMaxStackSizeTextField;

    public BaseItemCreationForm()
    {
        ItemNameTextField.setText("");
        ItemDescriptionTextArea.setText("");
        ItemMaxStackSizeTextField.setText("");
        add(BaseItemCreationPanel);
    }

    public String getItemName(int minLength, int maxLength) throws InputMismatchException
    {
        String name = ItemNameTextField.getText();
        if (name.isEmpty())
            throw new InputMismatchException("item name field empty");

        //Main.log("item name length "+name.length()+" "+(name.length()<minLength||name.length()>maxLength));
        if (name.length() < minLength || name.length() > maxLength)
            throw new InputMismatchException("provided item name length is " + name.length() + " expected " + minLength + "-" + maxLength);
        return name;
    }

    public String getDescription(int minLength, int maxLength) throws InputMismatchException
    {
        String desc = ItemDescriptionTextArea.getText();
        if (desc.length() < minLength || desc.length() > maxLength)
            throw new InputMismatchException("provided item description length is " + desc.length() + " expected " + minLength + "-" + maxLength);

        return desc;
    }

    public int getMaxStackSize(int minValue, int maxValue) throws InputMismatchException
    {
        String maxStackSizeFieldValue = ItemMaxStackSizeTextField.getText();
        if (maxStackSizeFieldValue.isEmpty())
            throw new InputMismatchException("max stack size field empty");

        try
        {
            Integer.parseInt(maxStackSizeFieldValue);
        } catch (NumberFormatException nfe)
        {
            throw new InputMismatchException("non-integer value provided in the max stack size field");
        }

        if (Integer.parseInt(maxStackSizeFieldValue) < minValue || Integer.parseInt(maxStackSizeFieldValue) > maxValue)
            throw new InputMismatchException("provided max stack size value is " + maxStackSizeFieldValue + " expected " + minValue + "-" + maxValue);

        return Integer.parseInt(maxStackSizeFieldValue);
    }
}
