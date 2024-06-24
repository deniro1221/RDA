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
import java.sql.ResultSet;

public class AzurirajProizvod extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldStaraSifra;
    private JTextField textFieldNovaSifra;
    private JTextField textFieldNaziv;
    private JTextField textFieldMarka;
    private JTextField textFieldCijena;

    public static void main(String[] args) {
        try {
            AzurirajProizvod dialog = new AzurirajProizvod();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AzurirajProizvod() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Ažuriraj proizvod");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(133, 21, 142, 21);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Stara Šifra proizvoda");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(10, 68, 142, 13);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Nova Šifra proizvoda");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1.setBounds(10, 91, 118, 13);
        contentPanel.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_1_1 = new JLabel("Naziv ");
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1_1.setBounds(10, 121, 118, 13);
        contentPanel.add(lblNewLabel_1_1_1);

        JLabel lblNewLabel_1_1_2 = new JLabel("Marka");
        lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1_2.setBounds(10, 147, 118, 13);
        contentPanel.add(lblNewLabel_1_1_2);

        JLabel lblNewLabel_1_1_2_1 = new JLabel("Cijena proizvoda");
        lblNewLabel_1_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1_2_1.setBounds(10, 170, 118, 13);
        contentPanel.add(lblNewLabel_1_1_2_1);

        textFieldStaraSifra = new JTextField();
        textFieldStaraSifra.setBounds(133, 66, 202, 19);
        contentPanel.add(textFieldStaraSifra);
        textFieldStaraSifra.setColumns(10);

        textFieldNovaSifra = new JTextField();
        textFieldNovaSifra.setColumns(10);
        textFieldNovaSifra.setBounds(133, 89, 202, 19);
        contentPanel.add(textFieldNovaSifra);

        textFieldNaziv = new JTextField();
        textFieldNaziv.setColumns(10);
        textFieldNaziv.setBounds(133, 119, 202, 19);
        contentPanel.add(textFieldNaziv);

        textFieldMarka = new JTextField();
        textFieldMarka.setColumns(10);
        textFieldMarka.setBounds(133, 145, 202, 19);
        contentPanel.add(textFieldMarka);

        textFieldCijena = new JTextField();
        textFieldCijena.setColumns(10);
        textFieldCijena.setBounds(133, 168, 202, 19);
        contentPanel.add(textFieldCijena);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String staraSifraStr = textFieldStaraSifra.getText();
                String novaSifraStr = textFieldNovaSifra.getText();
                String naziv = textFieldNaziv.getText();
                String marka = textFieldMarka.getText();
                String cijenaStr = textFieldCijena.getText();

                if (naziv.isEmpty() || marka.isEmpty() || cijenaStr.isEmpty() || staraSifraStr.isEmpty() || novaSifraStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Sva polja moraju uredno biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int staraSifra = Integer.parseInt(staraSifraStr);
                        int novaSifra = Integer.parseInt(novaSifraStr);
                        double cijena = Double.parseDouble(cijenaStr);

                        sacuvajUBazi(staraSifra, novaSifra, naziv, marka, cijena);
                        dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Molimo unesite važeće numeričke vrijednosti.", "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void sacuvajUBazi(int staraSifra, int novaSifra, String naziv, String marka, double cijena) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";

        String checkquery = "SELECT COUNT(*) FROM PROIZVOD WHERE Sifra_proizvoda = ?";
        String updatequery = "UPDATE PROIZVOD SET Sifra_proizvoda = ?, Naziv = ?, Marka = ?, Cijena_proizvoda = ? WHERE Sifra_proizvoda = ?";

        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement checkStatement = connection.prepareStatement(checkquery);
             PreparedStatement updateStatement = connection.prepareStatement(updatequery)) {

            checkStatement.setInt(1, staraSifra);

            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                JOptionPane.showMessageDialog(this, "Šifra proizvoda ne postoji u bazi. Ažuriranje nije moguće.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            updateStatement.setInt(1, novaSifra);
            updateStatement.setString(2, naziv);
            updateStatement.setString(3, marka);
            updateStatement.setDouble(4, cijena);
            updateStatement.setInt(5, staraSifra);

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Proizvod je uspješno ažuriran.");
            } else {
                JOptionPane.showMessageDialog(null, "Ažuriranje nije uspjelo. Provjerite staru šifru proizvoda.", "Greška", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Greška pri radu s bazom podataka: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}

