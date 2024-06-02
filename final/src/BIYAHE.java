

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
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;
public class BIYAHE extends javax.swing.JFrame {

    /**
     * Creates new form BIYAHE
     */
    public BIYAHE() {
        initComponents();
        try {
            Connection();
             populateComboBoxes();
            restrictDateChooser();
            loadTableData();
            
        } catch (SQLException ex) {
            Logger.getLogger(BIYAHE.class.getName()).log(Level.SEVERE, null, ex);
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
            String driverQuery = "SELECT Firstname, Lastname FROM driver";
            PreparedStatement driverPst = con.prepareStatement(driverQuery);
            ResultSet driverRs = driverPst.executeQuery();
            jComboBox1.removeAllItems();
            while (driverRs.next()) {
                String firstname = driverRs.getString("Firstname");
                String lastname = driverRs.getString("Lastname");
                String fullName = firstname + " " + lastname;
                jComboBox1.addItem(fullName);
            }

            String destinationQuery = "SELECT PLACE, RATES, DISTANCE, ALLOWANCE FROM payroll";
            PreparedStatement destinationPst = con.prepareStatement(destinationQuery);
            ResultSet destinationRs = destinationPst.executeQuery();
            jComboBox2.removeAllItems();
            while (destinationRs.next()) {
                String DES = destinationRs.getString("PLACE");
                String rates = destinationRs.getString("RATES");
                String KM = destinationRs.getString("DISTANCE");
                String Pera = destinationRs.getString("ALLOWANCE");
                String tripDetails = DES + "                                    " + rates + "                                 " + KM + "                                      " + Pera;
                jComboBox2.addItem(tripDetails);
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
            String query = "SELECT Drivername, Placeoftrip, Rates, Distance, Allowance, Date FROM `details]`";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String driverName = rs.getString("Drivername");
                String placeOfTrip = rs.getString("Placeoftrip");
                String rates = rs.getString("Rates");
                String distance = rs.getString("Distance");
                String allowance = rs.getString("Allowance");
                java.sql.Date date = rs.getDate("Date");
                model.addRow(new Object[]{driverName, placeOfTrip, rates, distance, allowance, date});
            }
        } catch (SQLException ex) {
            Logger.getLogger(BIYAHE.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Failed to load data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 private void updateTripInDatabase(String driverName, String placeOfTrip, String rates, String distance, String allowance, java.sql.Date tripDate, int selectedRow) {
    try {
        // Prepare the SQL query to update the trip record
        String query = "UPDATE `details]` SET Drivername = ?, Placeoftrip = ?, Rates = ?, Distance = ?, Allowance = ?, Date = ? WHERE ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, driverName);
        pst.setString(2, placeOfTrip);
        pst.setString(3, rates);
        pst.setString(4, distance);
        pst.setString(5, allowance);
        pst.setDate(6, tripDate);
        pst.setInt(7, selectedRow + 1); // Assuming your primary key column is at index 1

        // Execute the update query
        pst.executeUpdate();

        // Inform the user about the successful update
        JOptionPane.showMessageDialog(this, "Trip record updated successfully!");

    } catch (SQLException ex) {
        Logger.getLogger(BIYAHE.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Failed to update the trip record.", "Error", JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        TAKETRIP = new javax.swing.JButton();
        UPDATETRIP = new javax.swing.JButton();
        BACK = new javax.swing.JButton();
        CANCELTRIP = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel1.setText("DRIVER NAME");

        jLabel2.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel2.setText("TRIP TO");

        jLabel3.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        jLabel3.setText("DATE");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Driver name", "Place of Trip", "Rates", "Distance/Km", "Allowance", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
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
        }

        TAKETRIP.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        TAKETRIP.setText("TAKE TRIP");
        TAKETRIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TAKETRIPActionPerformed(evt);
            }
        });

        UPDATETRIP.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        UPDATETRIP.setText("UPDATE TRIP");
        UPDATETRIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UPDATETRIPActionPerformed(evt);
            }
        });

        BACK.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        BACK.setText("BACK");

        CANCELTRIP.setFont(new java.awt.Font("STXihei", 1, 14)); // NOI18N
        CANCELTRIP.setText("CANCEL TRIP");
        CANCELTRIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CANCELTRIPActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("STXihei", 1, 18)); // NOI18N
        jLabel4.setText("5 - BROTHERS TRUCKING");

        jLabel5.setText("Destination");

        jLabel6.setText("Rates");

        jLabel7.setText("Distance");

        jLabel8.setText("Allowance");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(TAKETRIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(UPDATETRIP, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(BACK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(CANCELTRIP)
                            .addGap(18, 18, 18))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(119, 776, Short.MAX_VALUE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(83, 83, 83)
                                .addComponent(jLabel8)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(487, 487, 487))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(418, 418, 418)
                .addComponent(jLabel3)
                .addGap(107, 107, 107))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(237, 237, 237)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TAKETRIP)
                            .addComponent(UPDATETRIP))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CANCELTRIP)
                            .addComponent(BACK))
                        .addContainerGap(129, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

    private void TAKETRIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TAKETRIPActionPerformed
   // Check if all fields are filled
    if (jComboBox1.getSelectedItem() == null || jComboBox2.getSelectedItem() == null || jDateChooser1.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Please fill all fields before taking a trip.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String driverName = jComboBox1.getSelectedItem().toString();
    String placeOfTrip = jComboBox2.getSelectedItem().toString();
    Date tripDate = jDateChooser1.getDate();
    java.sql.Date sqlDate = new java.sql.Date(tripDate.getTime());

    // Extract the specific details from the selected destination (rates, distance, allowance)
    String[] tripDetails = placeOfTrip.split("\\s+");
    String rates = tripDetails[1];
    String distance = tripDetails[2];
    String allowance = tripDetails[3];

    int response = JOptionPane.showConfirmDialog(this, "Do you want to take this trip?\n" +
            "Driver Name: " + driverName + "\n" +
            "Place of Trip: " + tripDetails[0] + "\n" +
            "Rates: " + rates + "\n" +
            "Distance: " + distance + "\n" +
            "Allowance: " + allowance + "\n" +
            "Date: " + sqlDate, 
            "Confirm Trip", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

    if (response == JOptionPane.NO_OPTION) {
        return; // User chose not to proceed
    }

    try {
        String query = "INSERT INTO `details` (Drivername, Placeoftrip, Rates, Distance, Allowance, Date) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, driverName);
        pst.setString(2, tripDetails[0]); // Only the place name
        pst.setString(3, rates);
        pst.setString(4, distance);
        pst.setString(5, allowance);
        pst.setDate(6, sqlDate);

        pst.executeUpdate();
        JOptionPane.showMessageDialog(this, "Trip taken successfully!");

        loadTableData(); // Refresh the table after taking the trip

    } catch (SQLException ex) {
        Logger.getLogger(BIYAHE.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Failed to take the trip.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_TAKETRIPActionPerformed

    private void UPDATETRIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UPDATETRIPActionPerformed
 // Get the selected row index
    int selectedRow = jTable1.getSelectedRow();
    
    // Check if a row is selected
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Please select a row to update.");
        return;
    }
    
    // Get the updated values from your input fields or components
    String driverName = jComboBox1.getSelectedItem().toString();
    String placeOfTrip = jComboBox2.getSelectedItem().toString();
    Date tripDate = jDateChooser1.getDate();
    
    // Check if any field is empty
    if (driverName.isEmpty() || placeOfTrip.isEmpty() || tripDate == null) {
        JOptionPane.showMessageDialog(null, "Please fill all fields.");
        return;
    }
    
    java.sql.Date sqlDate = new java.sql.Date(tripDate.getTime());

    // Extract the specific details from the selected destination (rates, distance, allowance)
    String[] tripDetails = placeOfTrip.split("\\s+");
    String rates = tripDetails[1];
    String distance = tripDetails[2];
    String allowance = tripDetails[3];
    
    // Ask for confirmation
    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this trip record?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option != JOptionPane.YES_OPTION) {
        return;
    }
    
    // Update the data in the table model
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setValueAt(driverName, selectedRow, 0);
    model.setValueAt(placeOfTrip, selectedRow, 1);
    model.setValueAt(rates, selectedRow, 2);
    model.setValueAt(distance, selectedRow, 3);
    model.setValueAt(allowance, selectedRow, 4);
    model.setValueAt(sqlDate, selectedRow, 5);

    // Update the data in the database
    updateTripInDatabase(driverName, placeOfTrip, rates, distance, allowance, sqlDate, selectedRow);

      
    }//GEN-LAST:event_UPDATETRIPActionPerformed

    private void CANCELTRIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CANCELTRIPActionPerformed
     // Get the selected row index
    int selectedRow = jTable1.getSelectedRow();
    
    // Check if a row is selected
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Please select a row to cancel the trip.");
        return;
    }
    
    // Ask for confirmation
    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this trip?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (option != JOptionPane.YES_OPTION) {
        return;
    }
    
    try {
        // Prepare the SQL query to delete the trip record
        String query = "DELETE FROM `details]` WHERE ID = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, selectedRow + 1); // Assuming your primary key column is at index 1
        
        // Execute the delete query
        pst.executeUpdate();
        
        // Inform the user about the successful cancellation
        JOptionPane.showMessageDialog(this, "Trip canceled successfully!");
        
        // Reload the table data after cancellation
        loadTableData();
        
    } catch (SQLException ex) {
        Logger.getLogger(BIYAHE.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Failed to cancel the trip.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_CANCELTRIPActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
          // Get the selected row index
    int selectedRow = jTable1.getSelectedRow();
    
    // Check if a row is selected
    if (selectedRow != -1) {
        // Get data from the selected row
        String driverName = jTable1.getValueAt(selectedRow, 0).toString();
        String placeOfTrip = jTable1.getValueAt(selectedRow, 1).toString();
        String rates = jTable1.getValueAt(selectedRow, 2).toString();
        String distance = jTable1.getValueAt(selectedRow, 3).toString();
        String allowance = jTable1.getValueAt(selectedRow, 4).toString();
        Date date = (Date) jTable1.getValueAt(selectedRow, 5); // Assuming the 5th column contains date data

        // Set the data to your input fields or components
        jComboBox1.setSelectedItem(driverName);
        // Assuming placeOfTrip is in the format "PlaceName Rate Distance Allowance"
        String[] tripDetails = placeOfTrip.split("\\s+");
        jComboBox2.setSelectedItem(tripDetails[0]); // Set place of trip
        jDateChooser1.setDate(date);
    }
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(BIYAHE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BIYAHE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BIYAHE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BIYAHE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BIYAHE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BACK;
    private javax.swing.JButton CANCELTRIP;
    private javax.swing.JButton TAKETRIP;
    private javax.swing.JButton UPDATETRIP;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
