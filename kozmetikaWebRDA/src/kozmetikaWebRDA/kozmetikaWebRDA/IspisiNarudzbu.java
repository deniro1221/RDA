package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;

public class IspisiNarudzbu extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JLabel lblSifraKupca;
    private JLabel lblSifraProdavaca;
    private JLabel lblDatumNarudzbe;
    private JLabel lblNacinPlacanja;
    private JLabel lblCijena;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            IspisiNarudzbu dialog = new IspisiNarudzbu();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public IspisiNarudzbu() {
        setBounds(100, 100, 450, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        {
            JLabel lblNewLabel = new JLabel("Ispis narudžbe");
            lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
            lblNewLabel.setBounds(132, 10, 135, 21);
            contentPanel.add(lblNewLabel);
        }
        {
            JLabel lblNewLabel_1 = new JLabel("Šifra narudžbe");
            lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lblNewLabel_1.setBounds(28, 69, 117, 13);
            contentPanel.add(lblNewLabel_1);
        }
        {
            textField = new JTextField();
            textField.setBounds(151, 67, 182, 19);
            contentPanel.add(textField);
            textField.setColumns(10);
        }

        lblSifraKupca = new JLabel("");
        lblSifraKupca.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblSifraKupca.setBounds(28, 100, 350, 13);
        contentPanel.add(lblSifraKupca);

        lblSifraProdavaca = new JLabel("");
        lblSifraProdavaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblSifraProdavaca.setBounds(28, 130, 350, 13);
        contentPanel.add(lblSifraProdavaca);

        lblDatumNarudzbe = new JLabel("");
        lblDatumNarudzbe.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDatumNarudzbe.setBounds(28, 160, 350, 13);
        contentPanel.add(lblDatumNarudzbe);

        lblNacinPlacanja = new JLabel("");
        lblNacinPlacanja.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNacinPlacanja.setBounds(28, 190, 350, 13);
        contentPanel.add(lblNacinPlacanja);

        lblCijena = new JLabel("");
        lblCijena.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCijena.setBounds(28, 220, 350, 13);
        contentPanel.add(lblCijena);

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                okButton.addActionListener(e -> {
                    String sifraNarudzbe = textField.getText();
                    if (!sifraNarudzbe.isEmpty()) {
                        prikaziNarudzbu(sifraNarudzbe);
                    } else {
                        System.out.println("Molimo unesite šifru narudžbe");
                    }
                });
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                // Add ActionListener to cancelButton
                cancelButton.addActionListener(e -> dispose());
            }
        }
    }

    private void prikaziNarudzbu(String sifraNarudzbe) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";

        String query = "SELECT Sifra_kupca, Sifra_prodavaca, Datum_narudzbe, Nacin_placanja, Cijena_narudzbe FROM NARUDZBA WHERE Sifra_narudzbe = ?";

        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sifraNarudzbe);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String sifraKupca = rs.getString("Sifra_kupca");
                String sifraProdavaca = rs.getString("Sifra_prodavaca");
                String datumNarudzbe = rs.getString("Datum_narudzbe");
                String nacinPlacanja = rs.getString("Nacin_placanja");
                String cijena = rs.getString("Cijena_narudzbe");

                lblSifraKupca.setText("Šifra kupca: " + sifraKupca);
                lblSifraProdavaca.setText("Šifra prodavača: " + sifraProdavaca);
                lblDatumNarudzbe.setText("Datum narudžbe: " + datumNarudzbe);
                lblNacinPlacanja.setText("Način plaćanja: " + nacinPlacanja);
                lblCijena.setText("Cijena narudžbe: " + cijena);
            } else {
                lblSifraKupca.setText("Narudžba nije pronađena.");
                lblSifraProdavaca.setText("");
                lblDatumNarudzbe.setText("");
                lblNacinPlacanja.setText("");
                lblCijena.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
