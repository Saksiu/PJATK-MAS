package big.game;

import javax.swing.*;

public class LoginPage extends JPanel implements Displayable
{
    private JPanel mainPanel;
    private JTextField LoginTextField;
    private JButton SignInButton;
    private JButton SignUpButton;
    private JButton AdminButton;
    private JButton DeveloperButton;
    private JPasswordField PasswordField;

    public LoginPage()
    {
        SignUpButton.addActionListener(e -> onSignUp());
        SignInButton.addActionListener(e -> onSignIn());
        AdminButton.addActionListener(e -> MainFrame.Instance.switchToPanel("MainAdminPanel"));
        DeveloperButton.addActionListener(e -> MainFrame.Instance.switchToPanel("MainDeveloperPanel"));

        add(mainPanel);
    }

    private void onSignUp()
    {
        //Main.log("isEmailValid: "+isEmailValid()+ " isPasswordValid: "+isPasswordValid()+" is email used: "+isEmailUsed());

        if (!isEmailValid())
        {
            ResultDialog.Launch("Error", "Invalid Email: must include the '@' and '.' symbols and have total length of at least 4");
            return;
        }
        if (!isPasswordValid())
        {
            ResultDialog.Launch("Error", "Password invalid: must be between 8 and 24 characters");
            return;
        }
        if (isEmailUsed())
        {
            ResultDialog.Launch("Error", "Account with that Email already exists");
            return;
        }

        Account.Create(LoginTextField.getText(), String.valueOf(PasswordField.getPassword()));
        //MainFrame.Instance.switchToPanel("MainPlayerPanel");
        ResultDialog.Launch("Success", "Successfully signed up!");
    }

    private void onSignIn()
    {
        if (!isEmailValid())
        {
            ResultDialog.Launch("Error", "Invalid Email: must include the '@' and '.' symbols and have total length of at least 4");
            return;
        }
        if (!isPasswordValid())
        {
            ResultDialog.Launch("Error", "Password invalid: must be between 8 and 24 characters");
            return;
        }
        if (!isEmailUsed())
        {
            ResultDialog.Launch("Error", "No Account found with the Email");
            return;
        }
        if (!isPasswordCorrect())
        {
            ResultDialog.Launch("Error", "Password incorrect for the Account");
            return;
        }

        Account.LogInto(LoginTextField.getText());
        MainFrame.Instance.switchToPanel("MainPlayerPanel");
    }

    private boolean isEmailValid()
    {
        String login = LoginTextField.getText();

        return login.length() >= 4 && (login.contains("@")) && (login.contains("."));
    }

    private boolean isEmailUsed()
    {

        return Account.AccountWithEmailExists(LoginTextField.getText());
    }

    private boolean isPasswordValid()
    {
        String password = String.valueOf(PasswordField.getPassword());

        return password.length() >= 8 && password.length() <= 24;
    }

    private boolean isPasswordCorrect()
    {
        String Email = LoginTextField.getText();
        String password = String.valueOf(PasswordField.getPassword());

        Account account = Account.getAccountWithEmail(Email);

        return account != null && account.isPasswordCorrect(password);
    }

    public void onDisplayed(JFrame parent)
    {
        LoginTextField.setText("");
        PasswordField.setText("");
    }
}
