import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Official extends JFrame implements ActionListener {
    JButton Dashboard, Result, Exit;
    JLabel background;

    Official() {
    setTitle("VOTE DAO-Official");
    setLayout(null);
    setResizable(false);

    // Create a new JPanel with a BoxLayout and set it as the content pane
    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
    setContentPane(contentPane);

    // Create a JLabel for the background image and add it to the content pane
    ImageIcon bgImageIcon = new ImageIcon(ClassLoader.getSystemResource("bg13.jpg"));
    Image bgImage = bgImageIcon.getImage().getScaledInstance(800, 480, Image.SCALE_SMOOTH);
    background = new JLabel(new ImageIcon(bgImage));
    background.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPane.add(background);
	
    JLabel text = new JLabel("Welcome Back !");
    text.setFont(new Font("Osward", Font.BOLD, 50));
    text.setBounds(200, 40, 500, 40);
    background.add(text);

    //Dashboard Button
    Dashboard = new JButton("Control Panel");
    Dashboard.setFont(new Font("Arial", Font.BOLD, 25));
    Dashboard.setBounds(140, 120, 200, 80);
    Dashboard.setBackground(Color.BLACK);
    Dashboard.setForeground(Color.BLACK);
    Dashboard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    Dashboard.addActionListener(this);
    Dashboard.setOpaque(false);
    background.add(Dashboard);

    //Result Button
    Result = new JButton("Result");
    Result.setFont(new Font("Arial", Font.BOLD, 25));
    Result.setBounds(440, 120, 200, 80);
    Result.setBackground(Color.BLACK);
    Result.setForeground(Color.BLACK);
    Result.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    Result.addActionListener(this);
    Result.setOpaque(false);
    background.add(Result);

    Exit = new JButton("Exit");
    Exit.setFont(new Font("Arial", Font.BOLD, 25));
    Exit.setBounds(290, 250, 200, 80);
    Exit.setBackground(Color.BLACK);
    Exit.setForeground(Color.BLACK);
    Exit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    Exit.addActionListener(this);
    Exit.setOpaque(false);
    background.add(Exit);

    setSize(800, 480);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Dashboard) 
		{
            setVisible(false);
            new Blocker();
        } 
        
		else if (e.getSource() == Exit) 
		{
            setVisible(false);
            new Login();
        }
		
		else if (e.getSource() == Result) 
		{
			setVisible(false);
            new Resulting();
        } 
    }

    public static void main(String[] args) {
        new Official();
    }
}
