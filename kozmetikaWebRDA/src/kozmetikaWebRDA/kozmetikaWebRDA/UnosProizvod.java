package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UnosProizvod extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UnosProizvod dialog = new UnosProizvod();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public UnosProizvod() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Unos proizvoda");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(145, 21, 114, 21);
        contentPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Šifra proizvoda");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(10, 68, 94, 13);
        contentPanel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Naziv");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_2.setBounds(10, 105, 78, 13);
        contentPanel.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Marka");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_3.setBounds(10, 145, 78, 13);
        contentPanel.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Cijena proizvoda");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_4.setBounds(10, 184, 94, 13);
        contentPanel.add(lblNewLabel_4);
        
        textField = new JTextField();
        textField.setBounds(135, 66, 220, 19);
        contentPanel.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(135, 103, 220, 19);
        contentPanel.add(textField_1);
        
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(135, 143, 220, 19);
        contentPanel.add(textField_2);
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(135, 182, 220, 19);
        contentPanel.add(textField_3);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sifraProizvoda = textField.getText();
                String nazivProizvoda = textField_1.getText();
                String markaProizvoda = textField_2.getText();
                String cijenaProizvoda = textField_3.getText();  

                // Validate if any field is empty
                if (sifraProizvoda.isEmpty() || nazivProizvoda.isEmpty() || markaProizvoda.isEmpty() || cijenaProizvoda.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Sva polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return; // Exit method early if validation fails
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://ucka.veleri.hr:3306/dsubasic", "dsubasic", "11");

                    String sql = "INSERT INTO PROIZVOD (Sifra_proizvoda, Naziv, Marka, Cijena_proizvoda) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, sifraProizvoda);
                    stmt.setString(2, nazivProizvoda);
                    stmt.setString(3, markaProizvoda);
                    stmt.setString(4, cijenaProizvoda); 

                    stmt.executeUpdate();
                    conn.close();

                    JOptionPane.showMessageDialog(null, "Podaci su uspješno uneseni!", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);

                    textField.setText("");
                    textField_1.setText("");
                    textField_2.setText("");
                    textField_3.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Unos proizvoda");
        setSize(400, 300);
    }
}
