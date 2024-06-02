

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author PAUL BRYAN
 */
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class Salary extends javax.swing.JFrame {

    /**
     * Creates new form Salary
     */
    public Salary() {
        initComponents();
        try {
            Connection();
            populateComboBoxes();
            loadTableData();
            restrictDateChooser();
           
        } catch (SQLException ex) {
            Logger.getLogger(Salary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       Connection con;
    PreparedStatement pst;
    private final static String DbName = "FINAL"; // Updated database name
    private final static String DbDriver = "com.mysql.cj.jdbc.Driver";
    private final static String DbUrl = "jdbc:mysql://localhost:3306/" + DbName;
    private final static String DbUsername = "root";
    private final static String DbPassword = "";
    
  public void Connection() throws SQLException {
        try {
            Class.forName(DbDriver);
            con = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
            if (con != null) {
                System.out.println("Connection Successful");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(payroll.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(), "JDBC Driver not found", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(BIYAHE.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(), "Failed to connect to database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 private void populateComboBoxes() {
    try {
        // Populate the first JComboBox with data from the "details" table
        String driverQuery = "SELECT Drivername, Placeoftrip FROM `details]`";
        PreparedStatement driverPst = con.prepareStatement(driverQuery);
        ResultSet driverRs = driverPst.executeQuery();
        jComboBox1.removeAllItems();
        while (driverRs.next()) {
            String driverName = driverRs.getString("Drivername");
            String placeOfTrip = driverRs.getString("Placeoftrip");
            String fullName = driverName + "                         " + placeOfTrip;
            jComboBox1.addItem(fullName);
        }

        // Populate the second JComboBox with data from the "details" table
        String destinationQuery = "SELECT Rates, Allowance FROM `details]`";
        PreparedStatement destinationPst = con.prepareStatement(destinationQuery);
        ResultSet destinationRs = destinationPst.executeQuery();
        jComboBox2.removeAllItems();
        while (destinationRs.next()) {
            String rates = destinationRs.getString("Rates");
            String allowance = destinationRs.getString("Allowance"); 
            String tripDetails = rates + "                                              " + allowance ;
            jComboBox2.addItem(tripDetails);
        }
        
    // Populate the third JComboBox with data from the "cashadvance" table
        String cashAdvanceQuery = "SELECT DRIVERNAME, ADVANCE, DATE FROM cashadvance";
        PreparedStatement cashAdvancePst = con.prepareStatement(cashAdvanceQuery);
        ResultSet cashAdvanceRs = cashAdvancePst.executeQuery();
        jComboBox3.removeAllItems();
        while (cashAdvanceRs.next()) {
            String driverName = cashAdvanceRs.getString("DRIVERNAME");
            String advance = cashAdvanceRs.getString("ADVANCE");
            String date = cashAdvanceRs.getString("DATE"); // Assuming DATE is a string, adjust if it's a date type
            String advanceDetails = driverName + "                         " + advance + "                         " + date;
            jComboBox3.addItem(advanceDetails);
        }
    } catch (SQLException ex) {
        Logger.getLogger(BIYAHE.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Failed to populate combo boxes.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

  private void restrictDateChooser() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Set the minimum selectable date to the current date
        jDateChooser1.setMinSelectableDate(currentDate);

        // Set the maximum selectable date to December 31, 2025
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.set(2025, Calendar.DECEMBER, 31);
        jDateChooser1.setMaxSelectableDate(maxCalendar.getTime());
    }
 private void loadTableData() {
    try {
        String query = "SELECT Drivername, Finishedtrip, Rates, Allowance, Fuelconsumption, Driversalary, Comapanymoney, Date FROM salary";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows

        while (rs.next()) {
            String driverName = rs.getString("Drivername");
            String placeOfTrip = rs.getString("Finishedtrip");
            String rates = rs.getString("Rates");
            String distance = rs.getString("Allowance");
            String allowance = rs.getString("Fuelconsumption");
            String salary = rs.getString("Driversalary");
            String cmoney = rs.getString("Comapanymoney");

            // Retrieve the date as a string and then parse it into a java.sql.Date object
            String dateString = rs.getString("Date");
            if (dateString != null && !dateString.isEmpty()) {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                model.addRow(new Object[]{driverName, placeOfTrip, rates, distance, allowance, salary, cmoney, sqlDate});
            } else {
                // Handle the case where the date string is empty
                // You can skip adding this row to the table or handle it differently based on your requirements
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(BIYAHE.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Failed to load data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (ParseException ex) {
        Logger.getLogger(BIYAHE.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Failed to parse date from the database.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        FUEl = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Done = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        Delete = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        ADD = new javax.swing.JButton();
        ADDALLDRIVERSALARY = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        COMAPNYMONEY = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DirverName", "Finished Trip", "Rates", "Allowance", "Fuel Consumption", "Driver salary", "Company Money", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel1.setText("Driver Name / Finished trip");

        jLabel2.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel2.setText("Rates  ");

        jLabel3.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel3.setText("Allowance");

        jLabel4.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel4.setText("Fuel Consumption");

        Done.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        Done.setText("ADD TO TABLE");
        Done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoneActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel6.setText("Date");

        Delete.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel7.setText("Cash Advance List");

        ADD.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        ADD.setText("ADD ALL");
        ADD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ADDActionPerformed(evt);
            }
        });

        ADDALLDRIVERSALARY.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        ADDALLDRIVERSALARY.setText(" See Drivers Salary");
        ADDALLDRIVERSALARY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ADDALLDRIVERSALARYActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel8.setText("Driver name");

        jLabel9.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel9.setText("Cash Advance");

        COMAPNYMONEY.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        COMAPNYMONEY.setText(" COMPANY MONEY");
        COMAPNYMONEY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                COMAPNYMONEYActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(202, 202, 202)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(jLabel3)
                .addGap(114, 114, 114)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(ADD, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(COMAPNYMONEY))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(ADDALLDRIVERSALARY))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(FUEl, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(Done))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)
                        .addComponent(FUEl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Done)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ADDALLDRIVERSALARY)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ADD)
                            .addComponent(COMAPNYMONEY))
                        .addGap(30, 30, 30)
                        .addComponent(Delete)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoneActionPerformed
    try {
        // Retrieve data from UI components
        String driverNameFinishedTrip = (String) jComboBox1.getSelectedItem();
        String[] driverTripParts = driverNameFinishedTrip.split("                         ");
        String driverName = driverTripParts[0];
        String finishedTrip = driverTripParts[1];

        String ratesAllowance = (String) jComboBox2.getSelectedItem();
        String[] ratesAllowanceParts = ratesAllowance.split("                                              ");
        double rates = Double.parseDouble(ratesAllowanceParts[0]);
        double allowance = Double.parseDouble(ratesAllowanceParts[1]);

        // Validate fuel consumption input
        String fuelConsumptionStr = FUEl.getText();
        if (!fuelConsumptionStr.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for fuel consumption.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double fuelConsumption = Double.parseDouble(fuelConsumptionStr);

        // Check if date is selected
        if (jDateChooser1.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Date date = jDateChooser1.getDate();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        // Debugging print statements
        System.out.println("Driver Name: " + driverName);
        System.out.println("Finished Trip: " + finishedTrip);
        System.out.println("Rates: " + rates);
        System.out.println("Allowance: " + allowance);
        System.out.println("Fuel Consumption: " + fuelConsumption);

        // Calculate DriverSalary and CompanyMoney
        double driverSalary = rates * 0.1;
        double companyMoney = rates - (fuelConsumption + allowance + driverSalary);

        // Debugging print statements for calculations
        System.out.println("Driver Salary (10% of Rates): " + driverSalary);
        System.out.println("Company Money: " + companyMoney);

        // Insert data into database
        String query = "INSERT INTO salary (Drivername, Finishedtrip, Rates, Allowance, Fuelconsumption, Driversalary, Comapanymoney, Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        pst = con.prepareStatement(query);
        pst.setString(1, driverName);
        pst.setString(2, finishedTrip);
        pst.setDouble(3, rates);
        pst.setDouble(4, allowance);
        pst.setDouble(5, fuelConsumption);
        pst.setDouble(6, driverSalary);
        pst.setDouble(7, companyMoney);
        pst.setDate(8, sqlDate);
        pst.executeUpdate();

        // Update the table
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{driverName, finishedTrip, rates, allowance, fuelConsumption, driverSalary, companyMoney, sqlDate});

        // Show success message
        JOptionPane.showMessageDialog(this, "Data saved successfully.");

    } catch (SQLException ex) {
        Logger.getLogger(Salary.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Failed to save data.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Please enter valid numbers for rates, allowance, and fuel consumption.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_DoneActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
      // Get the selected row index
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Ask for confirmation
    int confirmDialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this data?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
    if (confirmDialogResult != JOptionPane.YES_OPTION) {
        return;
    }

    // Get the values from the selected row
    String driverName = jTable1.getValueAt(selectedRow, 0).toString();
    String finishedTrip = jTable1.getValueAt(selectedRow, 1).toString();
    java.sql.Date date = (java.sql.Date) jTable1.getValueAt(selectedRow, 7);

    try {
        // Construct the DELETE query
        String deleteQuery = "DELETE FROM salary WHERE Drivername = ? AND Finishedtrip = ? AND Date = ?";
        pst = con.prepareStatement(deleteQuery);
        pst.setString(1, driverName);
        pst.setString(2, finishedTrip);
        pst.setDate(3, date);
        
        // Execute the DELETE query
        int rowsAffected = pst.executeUpdate();
        
        if (rowsAffected > 0) {
            // Remove the selected row from the table
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Data deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Salary.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Failed to delete data.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_DeleteActionPerformed

    private void ADDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ADDActionPerformed
                                  
    // Get the selected rows from the table
    int[] selectedRows = jTable1.getSelectedRows();
    
    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(this, "Please select rows to sum up the driver salaries.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    double totalSalary = 0.0;
    
    // Iterate through the selected rows and sum up the driver salaries
    for (int rowIndex : selectedRows) {
        double driverSalary = Double.parseDouble(jTable1.getValueAt(rowIndex, 5).toString());
        totalSalary += driverSalary;
    }
    
    // Display the total salary in a message dialog
    JOptionPane.showMessageDialog(this, "Total Driver Salary: " + totalSalary);
    }//GEN-LAST:event_ADDActionPerformed

    private void ADDALLDRIVERSALARYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ADDALLDRIVERSALARYActionPerformed
String driverName = jTextField2.getText().trim();
    String cashAdvanceText = jTextField3.getText().trim();
    double cashAdvance = 0.0;

    if (driverName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a driver name.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    try {
        // If cash advance is provided, parse it
        if (!cashAdvanceText.isEmpty()) {
            try {
                cashAdvance = Double.parseDouble(cashAdvanceText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid cash advance amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Prepare the SQL query to fetch driver salaries based on the entered driver name
        String querySalary = "SELECT Driversalary FROM salary WHERE Drivername = ?";
        pst = con.prepareStatement(querySalary);
        pst.setString(1, driverName);
        
        // Execute the query
        ResultSet rsSalary = pst.executeQuery();
        
        // Calculate the total salary for the driver
        double totalSalary = 0.0;
        boolean driverFound = false;
        
        while (rsSalary.next()) {
            double driverSalary = rsSalary.getDouble("Driversalary");
            totalSalary += driverSalary;
            driverFound = true;
        }
        
        if (!driverFound) {
            JOptionPane.showMessageDialog(this, "Driver name not found in the salary database.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Fetch the cash advance if applicable
        double totalAdvance = 0.0;
        if (cashAdvanceText.isEmpty()) {
            String queryAdvance = "SELECT ADVANCE FROM cashadvance WHERE Drivername = ?";
            pst = con.prepareStatement(queryAdvance);
            pst.setString(1, driverName);
            ResultSet rsAdvance = pst.executeQuery();
            
            while (rsAdvance.next()) {
                totalAdvance += rsAdvance.getDouble("ADVANCE");
            }
        } else {
            totalAdvance = cashAdvance;
        }

        // Subtract the cash advance from the total salary
        double adjustedSalary = totalSalary - totalAdvance;
        
        // Show the adjusted salary in a message dialog
        JOptionPane.showMessageDialog(this, "Total salary for " + driverName + ": " + adjustedSalary);
    } catch (SQLException ex) {
        Logger.getLogger(Salary.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Failed to fetch data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_ADDALLDRIVERSALARYActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void COMAPNYMONEYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_COMAPNYMONEYActionPerformed
try {
        // Prepare the SQL query to fetch the sum of Companymoney from the salary table
        String query = "SELECT SUM(Comapanymoney) AS TotalCompanyMoney FROM salary";
        pst = con.prepareStatement(query);
        
        // Execute the query
        ResultSet rs = pst.executeQuery();
        
        // Check if there are any results
        if (rs.next()) {
            // Retrieve the total sum of Companymoney
            double totalCompanyMoney = rs.getDouble("TotalCompanyMoney");
            
            // Display the total sum in a message dialog
            JOptionPane.showMessageDialog(this, "Total Company Money: " + totalCompanyMoney);
        } else {
            // No records found in the table
            JOptionPane.showMessageDialog(this, "No records found in the salary table.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        // Error occurred while executing the SQL query
        Logger.getLogger(Salary.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Failed to fetch total Company Money from the database.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_COMAPNYMONEYActionPerformed




    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Salary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Salary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Salary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Salary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Salary().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ADD;
    private javax.swing.JButton ADDALLDRIVERSALARY;
    private javax.swing.JButton COMAPNYMONEY;
    private javax.swing.JButton Delete;
    private javax.swing.JButton Done;
    private javax.swing.JTextField FUEl;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
