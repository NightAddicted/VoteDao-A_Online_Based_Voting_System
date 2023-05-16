import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Login extends JFrame implements ActionListener 
{

    JButton login, clear;
    JTextField idText;
    JPasswordField passText;
    JLabel background;

    Login() 
	{
        setTitle("VOTE DAO-Login");
        setLayout(null);
        setResizable(false);

        // Create a new JPanel with a BoxLayout and set it as the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        setContentPane(contentPane);
		
		// Create a JLabel for the background image and add it to the content pane
		ImageIcon bgImageIcon = new ImageIcon(ClassLoader.getSystemResource("bg3.jpg"));
		Image bgImage = bgImageIcon.getImage().getScaledInstance(800, 480, Image.SCALE_SMOOTH);
		background = new JLabel(new ImageIcon(bgImage));
		background.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(background);


        // Add your existing components to the content pane, positioned over the background image
        JLabel text = new JLabel("Welcome To VOTE DAO");
        text.setFont(new Font("Osward", Font.BOLD, 38));
        text.setBounds(200, 25, 500, 40);
        background.add(text);

        JLabel id = new JLabel("Enter User ID");
        id.setFont(new Font("Raleway", Font.BOLD, 15));
        id.setBounds(340, 140, 150, 40);
        background.add(id);

        idText = new JTextField();
        idText.setBounds(270, 175, 250, 30);
        idText.setFont(new Font("Arial", Font.BOLD, 14));
		idText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		idText.setBackground(new Color(219, 223, 234));
		idText.setOpaque(false);
        background.add(idText);

        JLabel pass = new JLabel("Enter Password");
        pass.setFont(new Font("Raleway", Font.BOLD, 15));
        pass.setBounds(340, 200, 500, 40);
        background.add(pass);

        passText = new JPasswordField();
        passText.setBounds(270, 235, 250, 30);
        passText.setFont(new Font("Arial", Font.BOLD, 14));
		passText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		passText.setBackground(new Color(219, 223, 234));
		passText.setOpaque(false);
        background.add(passText);
		

        login = new JButton("LOG IN");
		login.setFont(new Font("Arial", Font.BOLD, 13));
        login.setBounds(270, 285, 100, 30);
		login.setBackground(Color.BLACK);
        login.setForeground(Color.BLACK);
		login.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        login.addActionListener(this);
		login.setOpaque(false);
        background.add(login);

        clear = new JButton("CLEAR");
		clear.setFont(new Font("Arial", Font.BOLD, 13));
        clear.setBounds(420, 285, 100, 30);
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.BLACK);
		clear.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        clear.addActionListener(this);
		clear.setOpaque(false);
        background.add(clear);

        setSize(800, 480);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    }
	private void redirectToPage(String id) 
		{
			String prefix = id.substring(0, 2);
			switch (prefix) 
			{
				case "11":
					// Redirect to Admin page
					Admin adminPage = new Admin();
					adminPage.setVisible(true); // Make the Admin page visible
					setVisible(false); // Hide the Login page
					break;
				case "22":
					// Redirect to Voter page
					VoterLogin voterPage = new VoterLogin();
					voterPage.setVisible(true); // Make the Admin page visible
					setVisible(false); // Hide the Login page
					break;
				case "33":
					Official officialPage = new Official();
					officialPage.setVisible(true); // Make the Admin page visible
					setVisible(false); // Hide the Login page
					break;
				case "44":
					CandidateLogin CandidateLoginPage = new CandidateLogin();
					CandidateLoginPage.setVisible(true); // Make the Admin page visible
					setVisible(false); // Hide the Login page
					break;
				default:
					JOptionPane.showMessageDialog(this, "Invalid ID");
			}
		}
		
    public void actionPerformed(ActionEvent e) 
	{
        if (e.getSource() == clear) 
		{
            idText.setText("");
            passText.setText("");
        } 
		else if (e.getSource() == login) 
		{
            String id = idText.getText();
            String password = new String(passText.getPassword());
            if (id.trim().isEmpty() || password.trim().isEmpty()) 
			{
                JOptionPane.showMessageDialog(this, "Please Enter ID or Password");
                return;
            }
            if (validateUser(id, password)) 
			{
				redirectToPage(id);
            } else 
			{
                JOptionPane.showMessageDialog(this, "Invalid ID or Password");
            }
        }
    }
	private boolean validateUser(String id, String password) {
		try {
			File file = new File("User.txt");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");
				if(parts[0].equals(id) && parts[1].equals(password)) {
					scanner.close();
					return true;
				}
			}
			scanner.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	public static void main(String []args)
	{
		new Login();
	}
}
   
