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
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;

public class IspisProizvodSvi extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            IspisProizvodSvi dialog = new IspisProizvodSvi();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public IspisProizvodSvi() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        {
            JLabel lblNewLabel = new JLabel("Ispiši sve proizvode");
            lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
            lblNewLabel.setBounds(140, 10, 160, 26);
            contentPanel.add(lblNewLabel);
        }

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                okButton.addActionListener(e -> dispose());
            }
        }

        // Call method to display all products
        prikaziSveProizvode();
    }

    private void prikaziSveProizvode() {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";

        String query = "SELECT Naziv, Marka, Cijena_proizvoda FROM PROIZVOD";

        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            
            int yOffset = 50; // Initial offset for labels
            while (rs.next()) {
                String naziv = rs.getString("Naziv");
                String marka = rs.getString("Marka");
                String cijenaProizvoda = rs.getString("Cijena_proizvoda");

                JLabel lblProizvod = new JLabel("Naziv: " + naziv + ", Marka: " + marka + ", Cijena: " + cijenaProizvoda);
                lblProizvod.setFont(new Font("Tahoma", Font.PLAIN, 12));
                lblProizvod.setBounds(25, yOffset, 400, 13);
                contentPanel.add(lblProizvod);
                yOffset += 20; // Adjust yOffset for the next label
            }
            
            contentPanel.revalidate();
            contentPanel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
