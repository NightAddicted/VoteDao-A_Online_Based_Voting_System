import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VotingSystem extends JFrame {
    private JPanel candidatePanel;
    private JButton voteButton;
    private Map<String, Integer> voteCounts;

    public VotingSystem() {
        setTitle("VOTE DAO-Voter");
        setLayout(null);
        setResizable(false);
        setSize(800, 480);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        // Create a new JPanel with a BoxLayout and set it as the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        setContentPane(contentPane);

        // Create a JLabel for the background image and add it to the content pane
        ImageIcon bgImageIcon = new ImageIcon(ClassLoader.getSystemResource("bg00.jpg"));
        Image bgImage = bgImageIcon.getImage().getScaledInstance(800, 480, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(background);

        JLabel text = new JLabel("Please vote your candidate !");
        text.setFont(new Font("Osward", Font.BOLD, 50));
        text.setBounds(50, 30, 700, 80);
        background.add(text);

        candidatePanel = new JPanel();
        voteButton = new JButton("VOTE");

        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCandidate = getSelectedCandidate();
                if (selectedCandidate != null) {
                    updateResult(selectedCandidate);
                    new VoterLogin(); // Navigate to the login page
                    dispose(); // Close the current window
                }
            }
        });

        voteCounts = new HashMap<>();
        loadCandidatesFromFile("candidates.txt");
        setupCandidatePanel();

        candidatePanel.setBounds(280, 150, 500, 150);
        
        voteButton.setFont(new Font("Arial", Font.BOLD, 35));
        voteButton.setBounds(280, 350, 200, 80);
        voteButton.setBackground(Color.BLACK);
        voteButton.setForeground(Color.BLACK);
        voteButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        voteButton.setOpaque(false);
        background.add(voteButton);

        background.add(candidatePanel);
		
		// Set the transparency of the candidate list
        candidatePanel.setOpaque(false);
        for (Component component : candidatePanel.getComponents()) {
            if (component instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) component;
                radioButton.setOpaque(false);
            }
        }
    
    }

    private void loadCandidatesFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String candidate = line.trim();
                voteCounts.put(candidate, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupCandidatePanel() {
    candidatePanel.setLayout(new GridLayout(0, 1));
    ButtonGroup buttonGroup = new ButtonGroup();

    Font candidateFont = new Font("Arial", Font.PLAIN, 30); // Set the desired font size

    for (String candidate : voteCounts.keySet()) {
        JRadioButton radioButton = new JRadioButton(candidate);
        radioButton.setActionCommand(candidate);
        radioButton.setFont(candidateFont); // Apply the font to the radio button's label
        candidatePanel.add(radioButton);
        buttonGroup.add(radioButton);
    }
}


    private String getSelectedCandidate() {
        for (Component component : candidatePanel.getComponents()) {
            if (component instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) component;
                if (radioButton.isSelected()) {
                                        return radioButton.getActionCommand();
                }
            }
        }
        return null;
    }

    private void updateResult(String candidate) {
        int currentVotes = voteCounts.getOrDefault(candidate, 0);
        int updatedVotes = currentVotes + 1;
        voteCounts.put(candidate, updatedVotes);
        writeResultToFile(candidate, updatedVotes);
        JOptionPane.showMessageDialog(this, "Vote recorded!");
    }

    private void writeResultToFile(String candidate, int votes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("votes.txt"))) {
            for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + " votes");
                writer.newLine();
            }
            writer.write("Total Votes: " + getTotalVotes());
            writer.newLine();
            writer.write("Winner: " + getWinner());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getTotalVotes() {
        int totalVotes = 0;
        for (int votes : voteCounts.values()) {
            totalVotes += votes;
        }
        return totalVotes;
    }

    private String getWinner() {
        String winner = "";
        int maxVotes = -1;

        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            String candidate = entry.getKey();
            int votes = entry.getValue();

            if (votes > maxVotes) {
                winner = candidate;
                maxVotes = votes;
            } else if (votes == maxVotes) {
                winner = "Tie";
            }
        }

        return winner;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VotingSystem votingSystem = new VotingSystem();
                votingSystem.setVisible(true);
            }
        });
    }
}

