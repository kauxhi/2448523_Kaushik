/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javafinalproject;

/**
 *
 * @author kaushika
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnhancedGradingSystemJDBC extends JFrame {
    private JTextField nameField, mark1Field, mark2Field, mark3Field, totalField, averageField, classificationField;
    private JButton calculateButton, clearButton, addButton, deleteButton, updateButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private Connection conn;
    private PreparedStatement pst;

    public EnhancedGradingSystemJDBC() {
        initializeDatabase();
        initializeUI();
        loadTableData();
    }

    private void initializeDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentDB", "root", "");
            // Create table if it doesn't exist
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS student_grades (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    student_name VARCHAR(100),
                    mark1 DOUBLE,
                    mark2 DOUBLE,
                    mark3 DOUBLE,
                    total DOUBLE,
                    average DOUBLE,
                    classification VARCHAR(20)
                )
            """;
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EnhancedGradingSystemJDBC.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database Connection Error: " + ex.getMessage());
            System.exit(1);
        }
    }

    private void initializeUI() {
        setTitle("Enhanced Student Grading System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(60, 90, 150));
        JLabel titleLabel = new JLabel("Enhanced Student Grading System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize input fields
        nameField = createStyledTextField();
        mark1Field = createStyledTextField();
        mark2Field = createStyledTextField();
        mark3Field = createStyledTextField();
        totalField = createStyledTextField();
        averageField = createStyledTextField();
        classificationField = createStyledTextField();

        totalField.setEditable(false);
        averageField.setEditable(false);
        classificationField.setEditable(false);

        // Add components to input panel
        addLabelAndField(inputPanel, "Student Name:", nameField, gbc, 0);
        addLabelAndField(inputPanel, "Mark 1:", mark1Field, gbc, 1);
        addLabelAndField(inputPanel, "Mark 2:", mark2Field, gbc, 2);
        addLabelAndField(inputPanel, "Mark 3:", mark3Field, gbc, 3);
        addLabelAndField(inputPanel, "Total Mark:", totalField, gbc, 4);
        addLabelAndField(inputPanel, "Average Mark:", averageField, gbc, 5);
        addLabelAndField(inputPanel, "Classification:", classificationField, gbc, 6);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        calculateButton = createStyledButton("Calculate", new Color(50, 180, 70));
        clearButton = createStyledButton("Clear", new Color(220, 80, 60));
        addButton = createStyledButton("Add", new Color(30, 144, 255));
        updateButton = createStyledButton("Update", new Color(255, 165, 0));
        deleteButton = createStyledButton("Delete", new Color(220, 20, 60));

        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        add(inputPanel, BorderLayout.WEST);

        // Table
        String[] columns = {"ID", "Name", "Mark 1", "Mark 2", "Mark 3", "Total", "Average", "Classification"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        setupTable();
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Add event listeners
        setupEventListeners();
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(150, 25));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        return button;
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void setupTable() {
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentTable.setRowHeight(25);
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(60, 90, 150));
        studentTable.getTableHeader().setForeground(Color.WHITE);
    }

    private void setupEventListeners() {
        calculateButton.addActionListener(e -> calculateGrades());
        clearButton.addActionListener(e -> clearFields());
        addButton.addActionListener(e -> addRecord());
        updateButton.addActionListener(e -> updateRecord());
        deleteButton.addActionListener(e -> deleteRecord());

        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = studentTable.getSelectedRow();
                if (row != -1) {
                    populateFields(row);
                }
            }
        });
    }

    private void calculateGrades() {
        try {
            double mark1 = Double.parseDouble(mark1Field.getText());
            double mark2 = Double.parseDouble(mark2Field.getText());
            double mark3 = Double.parseDouble(mark3Field.getText());

            if (mark1 < 0 || mark1 > 100 || mark2 < 0 || mark2 > 100 || mark3 < 0 || mark3 > 100) {
                throw new IllegalArgumentException("Marks must be between 0 and 100");
            }

            double total = mark1 + mark2 + mark3;
            double average = total / 3;

            totalField.setText(String.format("%.2f", total));
            averageField.setText(String.format("%.2f", average));

            String classification;
            if (average >= 90) classification = "Excellent";
            else if (average >= 75) classification = "Good";
            else if (average >= 50) classification = "Pass";
            else classification = "Fail";

            classificationField.setText(classification);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric marks!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void addRecord() {
        if (!validateFields()) return;

        try {
            String sql = "INSERT INTO student_grades (student_name, mark1, mark2, mark3, total, average, classification) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);
            setStatementParameters(pst);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Record added successfully!");
            loadTableData();
            clearFields();
        } catch (SQLException ex) {
            Logger.getLogger(EnhancedGradingSystemJDBC.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error adding record: " + ex.getMessage());
        }
    }

    private void updateRecord() {
        int row = studentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to update!");
            return;
        }

        if (!validateFields()) return;

        try {
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            String sql = "UPDATE student_grades SET student_name=?, mark1=?, mark2=?, mark3=?, total=?, average=?, classification=? WHERE id=?";
            pst = conn.prepareStatement(sql);
            setStatementParameters(pst);
            pst.setInt(8, id);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Record updated successfully!");
            loadTableData();
            clearFields();
        } catch (SQLException ex) {
            Logger.getLogger(EnhancedGradingSystemJDBC.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error updating record: " + ex.getMessage());
        }
    }

    private void deleteRecord() {
        int row = studentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?");
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            String sql = "DELETE FROM student_grades WHERE id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Record deleted successfully!");
            loadTableData();
            clearFields();
        } catch (SQLException ex) {
            Logger.getLogger(EnhancedGradingSystemJDBC.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error deleting record: " + ex.getMessage());
        }
    }

    private void loadTableData() {
        try {
            String sql = "SELECT * FROM student_grades";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("student_name"),
                    rs.getDouble("mark1"),
                    rs.getDouble("mark2"),
                    rs.getDouble("mark3"),
                    rs.getDouble("total"),
                    rs.getDouble("average"),
                    rs.getString("classification")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EnhancedGradingSystemJDBC.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }

    private void setStatementParameters(PreparedStatement pst) throws SQLException {
        pst.setString(1, nameField.getText());
        pst.setDouble(2, Double.parseDouble(mark1Field.getText()));
        pst.setDouble(3, Double.parseDouble(mark2Field.getText()));
        pst.setDouble(4, Double.parseDouble(mark3Field.getText()));
        pst.setDouble(5, Double.parseDouble(totalField.getText()));
        pst.setDouble(6, Double.parseDouble(averageField.getText()));
        pst.setString(7, classificationField.getText());
    }

    private void populateFields(int row) {
        nameField.setText(tableModel.getValueAt(row, 1).toString());
        mark1Field.setText(tableModel.getValueAt(row, 2).toString());
        mark2Field.setText(tableModel.getValueAt(row, 3).toString());
        mark3Field.setText(tableModel.getValueAt(row, 4).toString());
        totalField.setText(tableModel.getValueAt(row, 5).toString());
        averageField.setText(tableModel.getValueAt(row, 6).toString());
        classificationField.setText(tableModel.getValueAt(row, 7).toString());
    }

    private void clearFields() {
        nameField.setText("");
        mark1Field.setText("");
        mark2Field.setText("");
        mark3Field.setText("");
        totalField.setText("");
        averageField.setText("");
        classificationField.setText("");
        studentTable.clearSelection();
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student name!");
            return false;
        }
        if (totalField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please calculate grades first!");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new EnhancedGradingSystemJDBC().setVisible(true);
        });
    }
}