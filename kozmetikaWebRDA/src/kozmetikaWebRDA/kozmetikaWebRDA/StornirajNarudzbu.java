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

public class StornirajNarudzbu extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldSifraNarudzbe;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            StornirajNarudzbu dialog = new StornirajNarudzbu();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public StornirajNarudzbu() {
        setBounds(100, 100, 450, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Storniraj narudžbu");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(118, 10, 182, 21);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Šifra narudžbe");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(27, 53, 94, 13);
        contentPanel.add(lblNewLabel_1);

        textFieldSifraNarudzbe = new JTextField();
        textFieldSifraNarudzbe.setBounds(131, 51, 190, 19);
        contentPanel.add(textFieldSifraNarudzbe);
        textFieldSifraNarudzbe.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                obrisiNarudzbu();
                dispose();
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

    private void obrisiNarudzbu() {
        int sifraNarudzbe = Integer.parseInt(textFieldSifraNarudzbe.getText());

        try {
            obrisiIzBaze(sifraNarudzbe);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Neispravan format šifre narudžbe.");
        }
    }

    private void obrisiIzBaze(int sifraNarudzbe) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";

        String query = "DELETE FROM NARUDZBA WHERE Sifra_narudzbe = ?";

        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, sifraNarudzbe);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Narudžba je uspješno obrisana.");
            } else {
                System.out.println("Brisanje narudžbe nije uspjelo. Provjerite šifru narudžbe.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Greška pri radu s bazom podataka: " + ex.getMessage());
        }
    }
}

