package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

public class AzurirajNarudzba extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldStaraSifra;
    private JTextField textFieldNovaSifra;
    private JTextField textFieldDatum;
    private JTextField textFieldCijena;
    private JComboBox<String> comboBoxNacinPlacanja;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            AzurirajNarudzba dialog = new AzurirajNarudzba();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public AzurirajNarudzba() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Ažuriraj narudžbu");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(121, 10, 136, 21);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Stara šifra narudžbe");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(10, 40, 121, 13);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Nova šifra narudžbe");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_2.setBounds(10, 76, 121, 13);
        contentPanel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Datum narudžbe");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_3.setBounds(10, 111, 121, 13);
        contentPanel.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Cijena narudžbe");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_4.setBounds(10, 146, 121, 13);
        contentPanel.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Način plaćanja");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_5.setBounds(10, 180, 121, 13);
        contentPanel.add(lblNewLabel_5);

        textFieldStaraSifra = new JTextField();
        textFieldStaraSifra.setBounds(131, 38, 223, 19);
        contentPanel.add(textFieldStaraSifra);
        textFieldStaraSifra.setColumns(10);

        textFieldNovaSifra = new JTextField();
        textFieldNovaSifra.setColumns(10);
        textFieldNovaSifra.setBounds(131, 74, 223, 19);
        contentPanel.add(textFieldNovaSifra);

        textFieldDatum = new JTextField();
        textFieldDatum.setColumns(10);
        textFieldDatum.setBounds(131, 109, 223, 19);
        contentPanel.add(textFieldDatum);

        textFieldCijena = new JTextField();
        textFieldCijena.setColumns(10);
        textFieldCijena.setBounds(131, 144, 223, 19);
        contentPanel.add(textFieldCijena);

        comboBoxNacinPlacanja = new JComboBox<String>();
        comboBoxNacinPlacanja.setModel(new DefaultComboBoxModel<String>(new String[] {"Gotovina", "Kartica", "Vaucher", "Bitcoin"}));
        comboBoxNacinPlacanja.setBounds(131, 177, 223, 21);
        contentPanel.add(comboBoxNacinPlacanja);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                azurirajNarudzbu();
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

    private void azurirajNarudzbu() {
        try {
            int staraSifra = Integer.parseInt(textFieldStaraSifra.getText());
            int novaSifra = Integer.parseInt(textFieldNovaSifra.getText());
            String datumString = textFieldDatum.getText();
            String cijenaString = textFieldCijena.getText();
            String nacinPlacanja = comboBoxNacinPlacanja.getSelectedItem().toString();

            SimpleDateFormat dateFormatInput = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd");
            String datumBaza = dateFormatOutput.format(dateFormatInput.parse(datumString));
            
            double cijena = Double.parseDouble(cijenaString);
            
            sacuvajUBazi(staraSifra, novaSifra, datumBaza, cijena, nacinPlacanja);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Neispravan format šifre ili cijene, također provjerite jesu li sva polja unesena.", "Greška", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Neispravan format datuma. Očekivani format je ddMMyyyy.", "Greška", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Došlo je do greške: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sacuvajUBazi(int staraSifra, int novaSifra, String datum, double cijena, String nacinPlacanja) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";

        String checkquery = "SELECT COUNT(*) FROM NARUDZBA WHERE Sifra_narudzbe = ?";
        String updatequery = "UPDATE NARUDZBA SET Sifra_narudzbe = ?, Datum_narudzbe = ?, Nacin_placanja = ?, Cijena_narudzbe = ? WHERE Sifra_narudzbe = ?";

        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement checkStatement = connection.prepareStatement(checkquery);
             PreparedStatement updateStatement = connection.prepareStatement(updatequery)) {

            checkStatement.setInt(1, staraSifra);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                JOptionPane.showMessageDialog(this, "Šifra narudžbe ne postoji u bazi. Ažuriranje nije moguće.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            updateStatement.setInt(1, novaSifra);
            updateStatement.setString(2, datum);
            updateStatement.setString(3, nacinPlacanja); 
            updateStatement.setDouble(4, cijena); 
            updateStatement.setInt(5, staraSifra);

            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Podaci narudžbe su uspješno ažurirani.", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ažuriranje podataka nije uspjelo. Provjerite šifru narudžbe.", "Greška", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Greška pri radu s bazom podataka: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }}
