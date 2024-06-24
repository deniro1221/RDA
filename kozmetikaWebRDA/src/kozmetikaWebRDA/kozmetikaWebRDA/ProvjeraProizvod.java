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

public class ProvjeraProizvod extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            StornirajProizvod dialog = new StornirajProizvod();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ProvjeraProizvod() {
        setBounds(100, 100, 450, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Storniraj proizvod");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(116, 10, 179, 21);
        contentPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Šifra proizvoda");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(10, 71, 107, 13);
        contentPanel.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(127, 69, 204, 19);
        contentPanel.add(textField);
        textField.setColumns(10);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String sifraProizvoda = textField.getText();
            if (!sifraProizvoda.isEmpty()) {
                provjeriProizvod(sifraProizvoda);
            } else {
                JOptionPane.showMessageDialog(this, "Molimo unesite šifru proizvoda.", "Greška", JOptionPane.ERROR_MESSAGE);
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
    
    private void provjeriProizvod(String sifraProizvoda) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";
        
        String provjeraQuery = "SELECT 1 FROM PROIZVOD WHERE Sifra_proizvoda = ?";
        
        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement provjeraStmt = connection.prepareStatement(provjeraQuery)) {
            
            provjeraStmt.setString(1, sifraProizvoda);
            ResultSet resultSet = provjeraStmt.executeQuery();
            
            if (resultSet.next()) {
                int response = JOptionPane.showConfirmDialog(this, "Proizvod pronađen. Želite li ga obrisati?", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    stornirajProizvod(sifraProizvoda);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Proizvod nije pronađen. Provjerite šifru proizvoda.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška pri radu s bazom podataka: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stornirajProizvod(String sifraProizvoda) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";
        
        String brisanjeQuery = "DELETE FROM PROIZVOD WHERE Sifra_proizvoda = ?";
        
        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement brisanjeStmt = connection.prepareStatement(brisanjeQuery)) {
            
            brisanjeStmt.setString(1, sifraProizvoda);
            int rowsDeleted = brisanjeStmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Proizvod uspješno storniran.", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Storniranje proizvoda nije uspjelo. Provjerite šifru proizvoda.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška pri radu s bazom podataka: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
