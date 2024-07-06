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

public class IspisiProdavac extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JLabel lblImeProdavaca;
    private JLabel lblAdresaProdavaca;
    private JLabel lblEmailProdavaca;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            IspisiProdavac dialog = new IspisiProdavac();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public IspisiProdavac() {
        setBounds(100, 100, 450, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        {
            JLabel lblNewLabel = new JLabel("Ispiši prodavača");
            lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
            lblNewLabel.setBounds(150, 10, 135, 21);
            contentPanel.add(lblNewLabel);
        }
        {
            JLabel lblNewLabel_1 = new JLabel("Šifra prodavača");
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

        lblImeProdavaca = new JLabel("");
        lblImeProdavaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblImeProdavaca.setBounds(28, 100, 350, 13);
        contentPanel.add(lblImeProdavaca);

        lblAdresaProdavaca = new JLabel("");
        lblAdresaProdavaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblAdresaProdavaca.setBounds(28, 130, 350, 13);
        contentPanel.add(lblAdresaProdavaca);

        lblEmailProdavaca = new JLabel("");
        lblEmailProdavaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEmailProdavaca.setBounds(28, 160, 350, 13);
        contentPanel.add(lblEmailProdavaca);

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                okButton.addActionListener(e -> {
                    String sifraProdavaca = textField.getText();
                    if (!sifraProdavaca.isEmpty()) {
                        prikaziProdavaca(sifraProdavaca);
                    } else {
                        System.out.println("Molimo unesite šifru prodavača");
                    }
                });
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                cancelButton.addActionListener(e -> dispose());

            }
        }
    }

    private void prikaziProdavaca(String sifraProdavaca) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";

        String query = "SELECT Ime_prodavaca, Adresa_prodavaca, E_mail_prodavaca FROM PRODAVAC WHERE Sifra_prodavaca = ?";

        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sifraProdavaca);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String imeProdavaca = rs.getString("Ime_prodavaca");
                String adresaProdavaca = rs.getString("Adresa_prodavaca");
                String emailProdavaca = rs.getString("E_mail_prodavaca");

                lblImeProdavaca.setText("Ime prodavača: " + imeProdavaca);
                lblAdresaProdavaca.setText("Adresa prodavača: " + adresaProdavaca);
                lblEmailProdavaca.setText("E-mail prodavača: " + emailProdavaca);
            } else {
                lblImeProdavaca.setText("Prodavač nije pronađen.");
                lblAdresaProdavaca.setText("");
                lblEmailProdavaca.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

