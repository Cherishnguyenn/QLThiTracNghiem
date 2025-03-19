package quizmanagementsystem.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

//Button Controller for Login Button
public class LoginController implements ActionListener{
    private JButton loginButton;

    public LoginController(JButton loginButton){
        this.loginButton = loginButton;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == loginButton){
            System.out.println("Login Button Clicked");
        }
    }
    
}
