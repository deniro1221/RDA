package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PrilagodiAzuriranje extends JDialog {
    private JTextField textFieldTablica;
    private JTextField textFieldPrimarniKljuc;
    private JTextField textFieldStupac;
    private JTextField textFieldNovaVrijednost;

    public static void main(String[] args) {
        try {
            PrilagodiAzuriranje dialog = new PrilagodiAzuriranje();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PrilagodiAzuriranje() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Prilagođeno ažuriranje");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(107, 21, 192, 24);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Unos tablice");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(10, 71, 106, 24);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Vrijednost primarnog ključa");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1.setBounds(10, 105, 150, 24);
        contentPanel.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_1_1 = new JLabel("Stupac koji se ažurira");
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1_1.setBounds(10, 139, 150, 24);
        contentPanel.add(lblNewLabel_1_1_1);

        JLabel lblNewLabel_1_1_2 = new JLabel("Nova vrijednost");
        lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1_2.setBounds(10, 173, 106, 24);
        contentPanel.add(lblNewLabel_1_1_2);

        textFieldTablica = new JTextField();
        textFieldTablica.setBounds(170, 75, 200, 19);
        contentPanel.add(textFieldTablica);
        textFieldTablica.setColumns(10);

        textFieldPrimarniKljuc = new JTextField();
        textFieldPrimarniKljuc.setColumns(10);
        textFieldPrimarniKljuc.setBounds(170, 109, 200, 19);
        contentPanel.add(textFieldPrimarniKljuc);

        textFieldStupac = new JTextField();
        textFieldStupac.setColumns(10);
        textFieldStupac.setBounds(170, 143, 200, 19);
        contentPanel.add(textFieldStupac);

        textFieldNovaVrijednost = new JTextField();
        textFieldNovaVrijednost.setColumns(10);
        textFieldNovaVrijednost.setBounds(170, 177, 200, 19);
        contentPanel.add(textFieldNovaVrijednost);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                azurirajVrijednost();
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        setTitle("Prilagođeno ažuriranje");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
    }

    private void azurirajVrijednost() {
        String tablica = textFieldTablica.getText().trim();
        String primarniKljuc = textFieldPrimarniKljuc.getText().trim();
        String stupac = textFieldStupac.getText().trim();
        String novaVrijednost = textFieldNovaVrijednost.getText().trim();

        String updateQuery = "";
        
        
        if (tablica.equalsIgnoreCase("KUPAC")) {
            updateQuery = "UPDATE KUPAC SET " + stupac + " = ? WHERE Sifra_kupca = ?";
        } else if (tablica.equalsIgnoreCase("PROIZVOD")) {
            updateQuery = "UPDATE PROIZVOD SET " + stupac + " = ? WHERE Sifra_proizvoda = ?";
        } else if (tablica.equalsIgnoreCase("NARUDZBA")) {
            updateQuery = "UPDATE NARUDZBA SET " + stupac + " = ? WHERE Sifra_narudzbe = ?";
        } else if (tablica.equalsIgnoreCase("PRODAVAC")) {
            updateQuery = "UPDATE PRODAVAC SET " + stupac + " = ? WHERE Sifra_prodavaca = ?";
        } else {
            
            JOptionPane.showMessageDialog(this, "Nepodržana tablica: " + tablica, "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr:3306/dsubasic", "dsubasic", "11");
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, novaVrijednost); 
            preparedStatement.setString(2, primarniKljuc); 

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Podaci su uspješno ažurirani.", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Nije pronađen redak za ažuriranje. Provjerite unos.", "Greška", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Greška pri radu s bazom podataka: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }}
