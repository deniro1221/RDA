package kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AzurirajKupac extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldStaraSifra;
    private JTextField textFieldNovaSifra;
    private JTextField textFieldIme;
    private JTextField textFieldPrezime;
    private JTextField textFieldEmail;

    public static void main(String[] args) {
        try {
            AzurirajKupac dialog = new AzurirajKupac();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AzurirajKupac() {
        setBounds(100, 100, 450, 350);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNaslov = new JLabel("Ažuriranje kupca");
        lblNaslov.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNaslov.setBounds(126, 23, 179, 26);
        contentPanel.add(lblNaslov);

        JLabel lblStaraSifra = new JLabel("Stara Šifra kupca");
        lblStaraSifra.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblStaraSifra.setBounds(10, 60, 110, 13);
        contentPanel.add(lblStaraSifra);

        JLabel lblNovaSifra = new JLabel("Nova Šifra kupca");
        lblNovaSifra.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNovaSifra.setBounds(10, 90, 110, 13);
        contentPanel.add(lblNovaSifra);

        JLabel lblIme = new JLabel("Ime kupca");
        lblIme.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblIme.setBounds(10, 120, 89, 13);
        contentPanel.add(lblIme);

        JLabel lblPrezime = new JLabel("Prezime kupca");
        lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblPrezime.setBounds(10, 150, 89, 13);
        contentPanel.add(lblPrezime);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEmail.setBounds(10, 180, 89, 13);
        contentPanel.add(lblEmail);

        textFieldStaraSifra = new JTextField();
        textFieldStaraSifra.setBounds(130, 58, 179, 19);
        contentPanel.add(textFieldStaraSifra);
        textFieldStaraSifra.setColumns(10);

        textFieldNovaSifra = new JTextField();
        textFieldNovaSifra.setBounds(130, 88, 179, 19);
        contentPanel.add(textFieldNovaSifra);
        textFieldNovaSifra.setColumns(10);

        textFieldIme = new JTextField();
        textFieldIme.setBounds(130, 118, 179, 19);
        contentPanel.add(textFieldIme);
        textFieldIme.setColumns(10);

        textFieldPrezime = new JTextField();
        textFieldPrezime.setBounds(130, 148, 179, 19);
        contentPanel.add(textFieldPrezime);
        textFieldPrezime.setColumns(10);

        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(130, 178, 179, 19);
        contentPanel.add(textFieldEmail);
        textFieldEmail.setColumns(10);

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
                String ime = textFieldIme.getText();
                String prezime = textFieldPrezime.getText();
                String email = textFieldEmail.getText();

                if (ime.isEmpty() || prezime.isEmpty() || email.isEmpty() || novaSifraStr.isEmpty() || staraSifraStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Sva polja moraju uredno biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int staraSifra = Integer.parseInt(staraSifraStr);
                        int novaSifra = Integer.parseInt(novaSifraStr);

                        sacuvajUBazi(staraSifra, novaSifra, ime, prezime, email);
                        dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Molimo unesite važeće numeričke vrijednosti za šifre.", "Greška", JOptionPane.ERROR_MESSAGE);
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

    private void sacuvajUBazi(int staraSifra, int novaSifra, String ime, String prezime, String email) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";

        String checkquery = "SELECT COUNT(*) FROM KUPAC WHERE Sifra_kupca = ?";
        String updatequery = "UPDATE KUPAC SET Sifra_kupca = ?, Ime_kupca = ?, Prezime_kupca = ?, E_mail = ? WHERE Sifra_kupca = ?";

        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement checkStatement = connection.prepareStatement(checkquery);
             PreparedStatement updateStatement = connection.prepareStatement(updatequery)) {

            checkStatement.setInt(1, staraSifra);

            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                JOptionPane.showMessageDialog(this, "Šifra kupca ne postoji u bazi. Ažuriranje nije moguće.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            updateStatement.setInt(1, novaSifra);
            updateStatement.setString(2, ime);
            updateStatement.setString(3, prezime);
            updateStatement.setString(4, email);
            updateStatement.setInt(5, staraSifra);

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Kupac je uspješno ažuriran.");
            } else {
                JOptionPane.showMessageDialog(null, "Ažuriranje nije uspjelo. Provjerite staru šifru kupca.", "Greška", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Greška pri radu s bazom podataka: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
