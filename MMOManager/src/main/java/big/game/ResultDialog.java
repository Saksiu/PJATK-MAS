package big.game;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ResultDialog extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea MessageTextArea;

    public ResultDialog(String message)
    {
        MessageTextArea.setText(message);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onOK();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void Launch(String title, String message)
    {
        ResultDialog dialog = new ResultDialog(message);
        dialog.setTitle(title);
        dialog.pack();
        dialog.setSize(300, 200);
        dialog.setVisible(true);
    }

    private void onOK()
    {
        // add your code here
        dispose();
    }

}
