import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PharmaApp extends JFrame {

    private JLabel jsWelcome;
    private JTextField userField;
    private JPasswordField passwordPasswordField;
    private JButton loginButton;
    private JPanel LoginPage;

    public PharmaApp(){
        login();
    }
    private void login(){
        setContentPane(LoginPage);
        setTitle("PharmaApp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setDefaultLookAndFeelDecorated(true);
        setSize(700,500);
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PharmaApp.this, "test");
            }
        });
    }

    public static void main(String[] args){
        new PharmaApp();
    }
}
