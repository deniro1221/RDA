package kozmetikaWebRDA.kozmetikaWebRDA;
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

public class StornoKupca extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            StornoKupca dialog = new StornoKupca();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public StornoKupca() {
        setBounds(100, 100, 450, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Storniraj kupca");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(139, 10, 131, 21);
        contentPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Šifra kupca");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(21, 63, 75, 13);
        contentPanel.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(124, 61, 233, 19);
        contentPanel.add(textField);
        textField.setColumns(10);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String sifraKupca = textField.getText();
            if (!sifraKupca.isEmpty()) {
                stornirajKupca(sifraKupca);
                dispose();
            } else {
                
                System.out.println("Molimo unesite šifru kupca.");
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
        cancelButton.addActionListener(e -> dispose());

    }
    
    private void stornirajKupca(String sifraKupca) {
        String url = "jdbc:mysql://ucka.veleri.hr:3306/dsubasic";
        String korisnickoIme = "dsubasic";
        String lozinka = "11";
        
        String query = "DELETE FROM KUPAC WHERE Sifra_kupca = ?";
        
        try (Connection connection = DriverManager.getConnection(url, korisnickoIme, lozinka);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, sifraKupca);
            int rowsDeleted = preparedStatement.executeUpdate();
            
            if (rowsDeleted > 0) {
                System.out.println("Kupac uspješno storniran.");
            } else {
                System.out.println("Storniranje kupca nije uspjelo. Provjerite šifru kupca.");
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Greška pri radu s bazom podataka: " + ex.getMessage());
        }
    }
}

