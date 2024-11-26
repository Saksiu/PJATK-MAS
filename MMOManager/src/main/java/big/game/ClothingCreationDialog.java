package big.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.InputMismatchException;

public class ClothingCreationDialog extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private BaseItemCreationForm BaseItemCreationFormPanel;
    private JList ClothingBodyPartList;
    private final DefaultListModel<String> BodyPartsListModel;
    private JButton ClothingColorPickerButton;

    private Color selectedColor;


    public ClothingCreationDialog()
    {
        setTitle("Create Clothing Item");
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

        BodyPartsListModel = new DefaultListModel<>();
        ClothingBodyPartList.setModel(BodyPartsListModel);
        BodyPartsListModel.clear();

        for (Clothing.ClothingType clothingType : Clothing.ClothingType.values())
            BodyPartsListModel.addElement(String.valueOf(clothingType));

        ClothingBodyPartList.setSelectedIndex(0);

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ClothingColorPickerButton.addActionListener(e -> onColorSelection());


        selectedColor = Color.RED;
        ClothingColorPickerButton.setBackground(selectedColor);
        pack();
        setVisible(true);
        setResizable(false);
    }

    private void onColorSelection()
    {

        // color chooser Dialog Box
        selectedColor = JColorChooser.showDialog(this,
                "Select a color", selectedColor);

        ClothingColorPickerButton.setBackground(selectedColor);

    }

    private Clothing.ClothingType getSelectedBodyPart()
    {
        return Clothing.ClothingType.valueOf((String) ClothingBodyPartList.getSelectedValue());
    }

    private void onOK()
    {
        try
        {
            Clothing.Create(BaseItemCreationFormPanel.getItemName(1, 20), BaseItemCreationFormPanel.getDescription(1, 200),
                    BaseItemCreationFormPanel.getMaxStackSize(1, Integer.MAX_VALUE), getSelectedBodyPart(), selectedColor);

        } catch (InputMismatchException ime)
        {
            ResultDialog.Launch("Error", ime.getMessage());
            return;
        }
        ResultDialog.Launch("Success", "Successfully created Clothing item");
        //close only if successful, its annoying to re-input everything on initial failure
        dispose();
    }

    private void onCancel()
    {
        // add your code here if necessary
        dispose();
    }
}
