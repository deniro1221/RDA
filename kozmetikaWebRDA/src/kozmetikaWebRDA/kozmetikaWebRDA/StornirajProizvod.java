package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StornirajProizvod extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            StornirajProizvod dialog = new StornirajProizvod();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public StornirajProizvod() {
        setBounds(100, 100, 450, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Storniraj proizvod");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(116, 10, 179, 21);
        contentPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Šifra proizvoda");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(10, 71, 107, 13);
        contentPanel.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(127, 69, 204, 19);
        contentPanel.add(textField);
        textField.setColumns(10);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String sifraProizvoda = textField.getText();
            if (!sifraProizvoda.isEmpty()) {
                stornirajProizvod(sifraProizvoda);
                dispose();
            } else {
                System.out.println("Molimo unesite šifru proizvoda.");
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);                cancelButton.addActionListener(e -> dispose());

        

    }
    
    private void stornirajProizvod(String sifraProizvoda) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";
        
        String query = "DELETE FROM PROIZVOD WHERE Sifra_proizvoda = ?";
        
        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, sifraProizvoda);
            int rowsDeleted = preparedStatement.executeUpdate();
            
            if (rowsDeleted > 0) {
                System.out.println("Proizvod uspješno storniran.");
            } else {
                System.out.println("Storniranje proizvoda nije uspjelo. Provjerite šifru proizvoda.");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Greška pri radu s bazom podataka: " + ex.getMessage());
        }
    }
}
