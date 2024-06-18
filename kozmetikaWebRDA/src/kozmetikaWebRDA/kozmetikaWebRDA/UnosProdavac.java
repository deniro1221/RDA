package kozmetikaWebRDA.kozmetikaWebRDA;

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

public class UnosProdavac extends JDialog {

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
            UnosProdavac dialog = new UnosProdavac();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public UnosProdavac() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Unos prodavača");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(130, 27, 135, 21);
        contentPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Šifra prodavača");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(23, 84, 109, 13);
        contentPanel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Ime prodavača");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_2.setBounds(23, 120, 90, 13);
        contentPanel.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Adresa prodavača");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_3.setBounds(23, 158, 123, 13);
        contentPanel.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Email prodavača");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_4.setBounds(23, 194, 109, 13);
        contentPanel.add(lblNewLabel_4);
        
        textField = new JTextField();
        textField.setBounds(153, 82, 205, 19);
        contentPanel.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(153, 118, 205, 19);
        contentPanel.add(textField_1);
        
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(156, 156, 205, 19);
        contentPanel.add(textField_2);
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(153, 192, 205, 19);
        contentPanel.add(textField_3);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sifraProdavaca = textField.getText();
                String imeProdavaca = textField_1.getText();
                String adresaProdavaca = textField_2.getText();
                String emailProdavaca = textField_3.getText();  
                
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://ucka.veleri.hr:3306/dsubasic", "dsubasic", "11");

                    String sql = "INSERT INTO PRODAVAC (Sifra_prodavaca, Ime_prodavaca, Adresa_prodavaca, E_mail_prodavaca) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, sifraProdavaca);
                    stmt.setString(2, imeProdavaca);
                    stmt.setString(3, adresaProdavaca);
                    stmt.setString(4, emailProdavaca); 
                   
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
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(e -> dispose());

        buttonPane.add(cancelButton);
    }

}

