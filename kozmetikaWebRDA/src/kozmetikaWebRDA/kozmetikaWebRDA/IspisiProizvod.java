package kozmetikaWebRDA.kozmetikaWebRDA;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IspisiProizvod extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JLabel lblNaziv;
    private JLabel lblMarka;
    private JLabel lblCijenaProizvoda;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            IspisiProizvod dialog = new IspisiProizvod();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public IspisiProizvod() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        {
            JLabel lblNewLabel = new JLabel("Ispiši proizvod");
            lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
            lblNewLabel.setBounds(140, 10, 139, 26);
            contentPanel.add(lblNewLabel);
        }
        {
            JLabel lblNewLabel_1 = new JLabel("Šifra proizvoda");
            lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lblNewLabel_1.setBounds(25, 61, 88, 13);
            contentPanel.add(lblNewLabel_1);
        }
        {
            textField = new JTextField();
            textField.setBounds(140, 59, 200, 19);
            contentPanel.add(textField);
            textField.setColumns(10);
        }

        lblNaziv = new JLabel("");
        lblNaziv.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNaziv.setBounds(25, 100, 350, 13);
        contentPanel.add(lblNaziv);

        lblMarka = new JLabel("");
        lblMarka.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblMarka.setBounds(25, 130, 350, 13);
        contentPanel.add(lblMarka);

        lblCijenaProizvoda = new JLabel("");
        lblCijenaProizvoda.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCijenaProizvoda.setBounds(25, 160, 350, 13);
        contentPanel.add(lblCijenaProizvoda);
        JButton btnNewButton = new JButton("Svi");
        btnNewButton.setBounds(275, 6, 105, 27);
        contentPanel.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IspisProizvodSvi dlg=new IspisProizvodSvi();
				dlg.setVisible(true);
			}
		});

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                okButton.addActionListener(e -> {
                    String sifraProizvoda = textField.getText();
                    if (!sifraProizvoda.isEmpty()) {
                        prikaziProizvod(sifraProizvoda);
                    } else {
                        System.out.println("Molimo unesite šifru proizvoda");
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

    private void prikaziProizvod(String sifraProizvoda) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";

        String query = "SELECT Naziv, Marka, Cijena_proizvoda FROM PROIZVOD WHERE Sifra_proizvoda = ?";

        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sifraProizvoda);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String naziv = rs.getString("Naziv");
                String marka = rs.getString("Marka");
                String cijenaProizvoda = rs.getString("Cijena_proizvoda");

                lblNaziv.setText("Naziv: " + naziv);
                lblMarka.setText("Marka: " + marka);
                lblCijenaProizvoda.setText("Cijena proizvoda: " + cijenaProizvoda);
            } else {
                lblNaziv.setText("Proizvod nije pronađen.");
                lblMarka.setText("");
                lblCijenaProizvoda.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

