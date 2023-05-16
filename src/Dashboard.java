import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Dashboard extends JFrame implements ActionListener {

    private JTable dataTable;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> data;
    private final String FILE_NAME = "data.txt";
    private JButton refreshButton;
    private JButton backButton;
	private JLabel background;

    public Dashboard() {
        setTitle("VOTE DAO-Dashboard");
		setLayout(null);
        setResizable(false);
		
        // Create a new JPanel with a BoxLayout and set it as the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        setContentPane(contentPane);
		
		// Create a JLabel for the background image and add it to the content pane
		ImageIcon bgImageIcon = new ImageIcon(ClassLoader.getSystemResource("bg10.jpg"));
		Image bgImage = bgImageIcon.getImage().getScaledInstance(800, 480, Image.SCALE_SMOOTH);
		background = new JLabel(new ImageIcon(bgImage));
		background.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(background);

        // Create the data table
        String[] columnNames = {"Name", "ID", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(50, 50, 700, 300);
        background.add(scrollPane);

        // Add the refresh button
        refreshButton = new JButton("Refresh");
		refreshButton.setFont(new Font("Arial", Font.BOLD, 15));
        refreshButton.setBounds(50, 380, 100, 30);
		refreshButton.setBackground(Color.BLACK);
		refreshButton.setForeground(Color.BLACK);
		refreshButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        refreshButton.addActionListener(this);
		refreshButton.setOpaque(false);
        background.add(refreshButton);

        // Add the back button
		backButton = new JButton("Back");
		backButton.setFont(new Font("Arial", Font.BOLD, 15));
		backButton.setBounds(600, 380, 100, 30);
		backButton.setBackground(Color.BLACK);
		backButton.setForeground(Color.BLACK);
		backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		backButton.addActionListener(this);
		backButton.setOpaque(false);
		background.add(backButton);  		
		

        // Load the data from file
        loadDataFromFile();

        setSize(800, 480);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadDataFromFile() {
        // Load the data from file into the table model and data array
        data = new ArrayList<>();
        try {
            FileReader reader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length == 3) { // check if the row has all three columns
                    String name = rowData[0];
                    String id = rowData[1];
                    String phone = rowData[2];
                    System.out.println("Adding row to table: " + name + ", " + id + ", " + phone);
                    tableModel.addRow(new Object[] {name, id, phone});
                    data.add(rowData);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading data from file.");
        }
        System.out.println("Data loaded: " + data.size() + " rows");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshButton) {
            // Reload the data from file
            tableModel.setRowCount(0);
            loadDataFromFile();
        } else if (e.getSource() == backButton) {
            new Admin();
            dispose(); // close the current window
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
