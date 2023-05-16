import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Blocker extends JFrame implements ActionListener {
	
	JLabel NameLabel, IDLabel, PhoneLabel;
    private JTextField NameTextField, IDTextField, PhoneTextField;
    private JButton Add, Delete, Edit, Save, Back;
	private JLabel background;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JList dataList;
    private DefaultListModel listModel;
    private JLabel statusLabel;
    private ArrayList<String> data;
    private final String FILE_NAME = "User.txt";

    public Blocker() 
	{
		setTitle("VOTE DAO-USER List");
		setLayout(null);
        setResizable(false);
		
        // Create a new JPanel with a BoxLayout and set it as the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        setContentPane(contentPane);
		
		// Create a JLabel for the background image and add it to the content pane
		ImageIcon bgImageIcon = new ImageIcon(ClassLoader.getSystemResource("bg66.jpg"));
		Image bgImage = bgImageIcon.getImage().getScaledInstance(800, 480, Image.SCALE_SMOOTH);
		background = new JLabel(new ImageIcon(bgImage));
		background.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(background);
		
		// Create the data table
        String[] columnNames = {"Username", "ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(350, 30, 400, 350);
        background.add(scrollPane);

        // Create the input fields and buttons
       
	    NameLabel = new JLabel("ID: ");
        NameLabel.setBounds(40, 30, 150, 25);
		NameLabel.setFont(new Font("Raleway", Font.BOLD, 15));
        background.add(NameLabel);
		
        NameTextField = new JTextField();
        NameTextField.setBounds(100, 30, 200, 25);
		NameTextField.setFont(new Font("Arial", Font.BOLD, 14));
		NameTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		NameTextField.setOpaque(false);
        background.add(NameTextField);
		
		IDLabel = new JLabel("Pass: ");
        IDLabel.setBounds(40, 80, 150, 25);
		IDLabel.setFont(new Font("Raleway", Font.BOLD, 15));
        background.add(IDLabel);
		
        IDTextField = new JTextField();
        IDTextField.setBounds(100, 80, 200, 25);
		IDTextField.setFont(new Font("Arial", Font.BOLD, 14));
		IDTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		IDTextField.setOpaque(false);
        background.add(IDTextField);
		
		Add = new JButton("Add");
		Add.setFont(new Font("Arial", Font.BOLD, 15));
		Add.setBounds(50, 140, 100, 30);
		Add.setBackground(Color.BLACK);
		Add.setForeground(Color.BLACK);
		Add.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		Add.addActionListener(this);
		Add.setOpaque(false);
		background.add(Add);
		
		Delete = new JButton("Block");
		Delete.setFont(new Font("Arial", Font.BOLD, 15));
		Delete.setBounds(200, 140, 100, 30);
		Delete.setBackground(Color.BLACK);
		Delete.setForeground(Color.BLACK);
		Delete.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		Delete.addActionListener(this);
		Delete.setOpaque(false);
		background.add(Delete);
		
		Save = new JButton("Save");
		Save.setFont(new Font("Arial", Font.BOLD, 15));
		Save.setBounds(125, 190, 100, 30);
		Save.setBackground(Color.BLACK);
		Save.setForeground(Color.BLACK);
		Save.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		Save.addActionListener(this);
		Save.setOpaque(false);
		background.add(Save);
		
		Back = new JButton("Back");
		Back.setFont(new Font("Arial", Font.BOLD, 15));
		Back.setBounds(600, 400, 100, 30);
		Back.setBackground(Color.BLACK);
		Back.setForeground(Color.BLACK);
		Back.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		Back.addActionListener(this);
		Back.setOpaque(false);
		background.add(Back);  		
		
    
		// Create the data list and status label
        listModel = new DefaultListModel();
		dataList = new JList(listModel);
		add(new JScrollPane(dataList), BorderLayout.SOUTH);

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
            if (rowData.length == 2) { // check if the row has all three columns
                String name = rowData[0];
                String id = rowData[1];
                System.out.println("Adding row to table: " + name + ", " + id);
                tableModel.addRow(new Object[] {name, id});
                data.add(line); // add the original line to the data array
            }
        }
        bufferedReader.close();
        tableModel.fireTableDataChanged(); // update the table view
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error loading data from file.");
    }
    System.out.println("Data loaded: " + data.size() + " rows");
}

	public void actionPerformed(ActionEvent e) {
    if (e.getSource() == Back) {
        setVisible(false);
        new Official();
    } else if (e.getSource() == Add) {
		if(NameTextField.getText().trim().isEmpty() || IDTextField.getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(this,"Please Enter ID or Password");
			return;
		}
        // Add the data to the table model and data array
        String name = NameTextField.getText();
        String id = IDTextField.getText();
        String newData = name + "," + id;
        if (!data.contains(newData)) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to add this data?");
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.addRow(new Object[] {name, id});
                data.add(newData);
                statusLabel.setText("Data added successfully.");
                clearInputFields();
            }
        } else {
            JOptionPane.showMessageDialog(this, "This data already exists.");
        }
    } else if (e.getSource() == Delete) {
        // Remove the selected row from the table model and data array
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this data?");
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                data.remove(selectedRow);
                statusLabel.setText("Data deleted successfully.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    } 
	
     else if (e.getSource() == Save) {
        // Save the data to file
        try {
            FileWriter writer = new FileWriter(FILE_NAME);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            for (String line : data) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            statusLabel.setText("Data saved successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data to file.");
        }
    }
}

	private void clearInputFields() {
    // Clear the input fields
    NameTextField.setText("");
    IDTextField.setText("");
}
    public static void main(String[] args) {
        new Blocker();
    }
}

