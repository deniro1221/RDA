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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IspisKupac extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JLabel lblImeKupca;
    private JLabel lblPrezimeKupca;
    private JLabel lblEmailKupca;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            IspisKupac dialog = new IspisKupac();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public IspisKupac() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        {
            JLabel lblNewLabel = new JLabel("Ispiši kupca");
            lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
            lblNewLabel.setBounds(144, 10, 103, 15);
            contentPanel.add(lblNewLabel);
        }
        {
            JLabel lblNewLabel_1 = new JLabel("Šifra kupca");
            lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
            lblNewLabel_1.setBounds(29, 71, 74, 13);
            contentPanel.add(lblNewLabel_1);
        }
        {
            textField = new JTextField();
            textField.setBounds(132, 69, 192, 19);
            contentPanel.add(textField);
            textField.setColumns(10);
        }

        lblImeKupca = new JLabel("");
        lblImeKupca.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblImeKupca.setBounds(29, 100, 350, 13);
        contentPanel.add(lblImeKupca);

        lblPrezimeKupca = new JLabel("");
        lblPrezimeKupca.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblPrezimeKupca.setBounds(29, 130, 350, 13);
        contentPanel.add(lblPrezimeKupca);

        lblEmailKupca = new JLabel("");
        lblEmailKupca.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEmailKupca.setBounds(29, 160, 350, 13);
        contentPanel.add(lblEmailKupca);
        
        JButton btnNewButton = new JButton("Svi");
        btnNewButton.setBounds(275, 6, 105, 27);
        contentPanel.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IspisKupacSvi dlg=new IspisKupacSvi();
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
                    String sifraKupca = textField.getText();
                    if (!sifraKupca.isEmpty()) {
                        prikaziKupca(sifraKupca);
                    } else {
                        System.out.println("Molimo unesite šifru kupca");
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

    private void prikaziKupca(String sifraKupca) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";

        String query = "SELECT Ime_kupca, Prezime_kupca, E_mail FROM KUPAC WHERE Sifra_kupca = ?";

        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sifraKupca);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String imeKupca = rs.getString("Ime_kupca");
                String prezimeKupca = rs.getString("Prezime_kupca");
                String emailKupca = rs.getString("E_mail");

                lblImeKupca.setText("Ime kupca: " + imeKupca);
                lblPrezimeKupca.setText("Prezime kupca: " + prezimeKupca);
                lblEmailKupca.setText("E-mail kupca: " + emailKupca);
            } else {
                lblImeKupca.setText("Kupac nije pronađen.");
                lblPrezimeKupca.setText("");
                lblEmailKupca.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
