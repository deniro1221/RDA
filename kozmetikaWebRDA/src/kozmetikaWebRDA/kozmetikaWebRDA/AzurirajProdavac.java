package kozmetikaWebRDA.kozmetikaWebRDA;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AzurirajProdavac extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldStaraSifra;
    private JTextField textFieldNovaSifra;
    private JTextField textFieldIme;
    private JTextField textFieldAdresa;
    private JTextField textFieldEmail;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            AzurirajProdavac dialog = new AzurirajProdavac();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public AzurirajProdavac() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Ažuriraj prodavača");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(138, 29, 147, 21);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Stara šifra prodavača");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(24, 66, 137, 13);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Nova šifra prodavača");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1.setBounds(24, 94, 116, 13);
        contentPanel.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Ime prodavača");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_2.setBounds(24, 127, 104, 13);
        contentPanel.add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_3 = new JLabel("Adresa prodavača");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_3.setBounds(24, 161, 116, 13);
        contentPanel.add(lblNewLabel_1_3);

        JLabel lblNewLabel_1_4 = new JLabel("Email prodavača");
        lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_4.setBounds(24, 191, 110, 13);
        contentPanel.add(lblNewLabel_1_4);

        textFieldStaraSifra = new JTextField();
        textFieldStaraSifra.setBounds(189, 64, 181, 19);
        contentPanel.add(textFieldStaraSifra);
        textFieldStaraSifra.setColumns(10);

        textFieldNovaSifra = new JTextField();
        textFieldNovaSifra.setColumns(10);
        textFieldNovaSifra.setBounds(189, 92, 181, 19);
        contentPanel.add(textFieldNovaSifra);

        textFieldIme = new JTextField();
        textFieldIme.setColumns(10);
        textFieldIme.setBounds(189, 125, 181, 19);
        contentPanel.add(textFieldIme);

        textFieldAdresa = new JTextField();
        textFieldAdresa.setColumns(10);
        textFieldAdresa.setBounds(189, 159, 181, 19);
        contentPanel.add(textFieldAdresa);

        textFieldEmail = new JTextField();
        textFieldEmail.setColumns(10);
        textFieldEmail.setBounds(189, 189, 181, 19);
        contentPanel.add(textFieldEmail);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int staraSifra = Integer.parseInt(textFieldStaraSifra.getText());
                int novaSifra = Integer.parseInt(textFieldNovaSifra.getText());
                String ime = textFieldIme.getText();
                String adresa = textFieldAdresa.getText();
                String email = textFieldEmail.getText();
                
                sacuvajUBazi(staraSifra, novaSifra, ime, adresa, email);
                dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        // Add ActionListener to cancelButton
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void sacuvajUBazi(int staraSifra, int novaSifra, String ime, String adresa, String email) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";
        
        String query = "UPDATE PRODAVAC SET Sifra_prodavaca = ?, Ime_prodavaca = ?, Adresa_prodavaca = ?, E_mail_prodavaca = ? WHERE Sifra_prodavaca = ?";
        
        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            
            preparedStatement.setInt(1, novaSifra);
            preparedStatement.setString(2, ime);
            preparedStatement.setString(3, adresa);
            preparedStatement.setString(4, email);
            preparedStatement.setInt(5, staraSifra);
            
            int rowsUpdated = preparedStatement.executeUpdate();
            
 
            if (rowsUpdated > 0) {
                System.out.println("Podaci prodavača su uspješno ažurirani.");
            } else {
                System.out.println("Ažuriranje podataka nije uspjelo. Provjerite šifru prodavača.");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Greška pri radu s bazom podataka: " + ex.getMessage());
        }
    }
}
