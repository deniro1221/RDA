package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProvjeraKupac extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ProvjeraKupac dialog = new ProvjeraKupac();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ProvjeraKupac() {
        setBounds(100, 100, 450, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Storniraj kupca");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(139, 10, 131, 21);
        contentPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Šifra kupca");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(21, 63, 75, 13);
        contentPanel.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(124, 61, 233, 19);
        contentPanel.add(textField);
        textField.setColumns(10);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String sifraKupca = textField.getText();
            if (!sifraKupca.isEmpty()) {
                stornirajKupca(sifraKupca);
            } else {
                JOptionPane.showMessageDialog(this, "Molimo unesite šifru kupca.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        cancelButton.addActionListener(e -> dispose());
    }
    
    private void stornirajKupca(String sifraKupca) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";

        String provjeraQuery = "SELECT 1 FROM NARUDZBA WHERE Sifra_kupca = ?";
        String brisanjeQuery = "DELETE FROM KUPAC WHERE Sifra_kupca = ?";
        
        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka)) {
            connection.setAutoCommit(false); 

            try (PreparedStatement provjeraStmt = connection.prepareStatement(provjeraQuery)) {
                provjeraStmt.setString(1, sifraKupca);
                ResultSet resultSet = provjeraStmt.executeQuery();
                
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Kupca nije moguće obrisati jer postoje povezane narudžbe.", "Greška", JOptionPane.ERROR_MESSAGE);
                    connection.rollback(); 
                    return;
                }
            }

            // Check if the customer exists before attempting to delete
            String provjeraKupcaQuery = "SELECT * FROM KUPAC WHERE Sifra_kupca = ?";
            try (PreparedStatement provjeraKupcaStmt = connection.prepareStatement(provjeraKupcaQuery)) {
                provjeraKupcaStmt.setString(1, sifraKupca);
                ResultSet resultSet = provjeraKupcaStmt.executeQuery();
                
                if (!resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Kupac s ovom šifrom ne postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
                    connection.rollback();
                    return;
                }
            }

            // Confirm deletion
            int response = JOptionPane.showConfirmDialog(this, "Kupac je pronađen. Želite li ga obrisati?", "Potvrda", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION) {
                connection.rollback();
                return;
            }

            try (PreparedStatement brisanjeStmt = connection.prepareStatement(brisanjeQuery)) {
                brisanjeStmt.setString(1, sifraKupca);
                int rowsDeleted = brisanjeStmt.executeUpdate();
                
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Kupac uspješno storniran.", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Storniranje kupca nije uspjelo. Provjerite šifru kupca.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }

            connection.commit(); 

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška pri radu s bazom podataka: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
