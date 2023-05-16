import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.text.BadLocationException;


public class Resultin extends JFrame {

    private JTextPane textArea;
    private JButton backButton;
    private JLabel text;

    public Resultin() {
        setTitle("VOTE DAO-Result");
        setLayout(null);
        setResizable(false);

        // Create a JLabel for the background image and add it to the content pane
        ImageIcon bgImageIcon = new ImageIcon(ClassLoader.getSystemResource("bg00.jpg"));
        Image bgImage = bgImageIcon.getImage().getScaledInstance(800, 480, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setLayout(new BorderLayout());

        textArea = new JTextPane();
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20)); // Set the font size for the text

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        text = new JLabel("Here Is The Result !");
        text.setFont(new Font("Osward", Font.BOLD, 50));
        text.setHorizontalAlignment(SwingConstants.CENTER); // Center horizontally
        text.setVerticalAlignment(SwingConstants.CENTER); // Center vertically
        background.add(text, BorderLayout.NORTH);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 15));
        backButton.setBounds(20, 400, 100, 30); // Adjust the size and position of the button
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.BLACK); // Set the text color to black
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        setSize(800, 480);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setVisible(false);
                    new Admin();
                    dispose(); // Close the current window
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        backButton.setOpaque(false);
        background.add(backButton, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        background.add(centerPanel, BorderLayout.CENTER);

        setContentPane(background);

        // Read the file and populate the JTextPane
        readFile("votes.txt");
    }

    private void readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            textArea.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Resultin gui = new Resultin();
            gui.setVisible(true);
            gui.readFile("votes.txt");
        });
    }
}
