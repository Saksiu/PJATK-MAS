package big.game;

import javax.swing.*;

public class ItemStackSlotPanel extends JPanel
{

    private JPanel ItemPanel;
    private JLabel ItemQuantityLabel;
    private JLabel ItemNameLabel;
    private JLabel ItemTypeLabel;
    private ItemStack displayedItemStack;

    public ItemStackSlotPanel(ItemStack itemStack)
    {
        displayedItemStack = itemStack;

        ItemNameLabel.setText(String.valueOf(itemStack.getContainsItem().getName()));
        ItemQuantityLabel.setText(String.valueOf(itemStack.getItemsAmount()));
        ItemTypeLabel.setText(itemStack.getContainsItem().getItemTypeName());
        add(ItemPanel);
    }


    public ItemStackSlotPanel()
    {
        ItemNameLabel.setText("");
        ItemQuantityLabel.setText("");
        ItemTypeLabel.setText("");
        add(ItemPanel);
    }

    public ItemStack getDisplayedItemStack()
    {
        return displayedItemStack;
    }
}
