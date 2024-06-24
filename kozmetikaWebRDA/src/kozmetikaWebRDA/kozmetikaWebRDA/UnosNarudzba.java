package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class UnosNarudzba extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UnosNarudzba dialog = new UnosNarudzba();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public UnosNarudzba() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Unos narudžbe");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(141, 10, 123, 21);
        contentPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Šifra narudžbe");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(10, 64, 107, 13);
        contentPanel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Šifra kupca");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_2.setBounds(10, 87, 92, 13);
        contentPanel.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Šifra prodavača");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_3.setBounds(10, 110, 92, 13);
        contentPanel.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Datum narudžbe");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_4.setBounds(10, 138, 107, 13);
        contentPanel.add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("Cijena");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_5.setBounds(10, 161, 92, 13);
        contentPanel.add(lblNewLabel_5);
        
        JLabel lblNewLabel_6 = new JLabel("Način plaćanja");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_6.setBounds(10, 189, 92, 13);
        contentPanel.add(lblNewLabel_6);
        
        textField = new JTextField();
        textField.setBounds(142, 62, 205, 19);
        contentPanel.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(141, 85, 205, 19);
        contentPanel.add(textField_1);
        
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(141, 108, 205, 19);
        contentPanel.add(textField_2);
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(141, 136, 205, 19);
        contentPanel.add(textField_3);
        
        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(141, 159, 205, 19);
        contentPanel.add(textField_4);
        
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Gotovina", "Kartica", "Vaucher", "Bitcoin"}));
        comboBox.setBounds(141, 186, 205, 21);
        contentPanel.add(comboBox);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sifraNarudzbe = textField.getText();
                String sifraKupca = textField_1.getText();
                String sifraProdavaca = textField_2.getText();
                String datumNarudzbe = textField_3.getText();  
                String nacinPlacanja = (String) comboBox.getSelectedItem();
                String cijena = textField_4.getText();

                try {
                   
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                    java.util.Date parsedDate = sdf.parse(datumNarudzbe);
                    sdf.applyPattern("yyyy-MM-dd");
                    String formatiraniDatum = sdf.format(parsedDate);

                   
                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://ucka.veleri.hr:3306/dsubasic", "dsubasic", "11");

                    String sql = "INSERT INTO NARUDZBA (Sifra_narudzbe, Sifra_kupca, Sifra_prodavaca, Datum_narudzbe, Nacin_placanja, Cijena_narudzbe) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, sifraNarudzbe);
                    stmt.setString(2, sifraKupca);
                    stmt.setString(3, sifraProdavaca);
                    stmt.setString(4, formatiraniDatum); 
                    stmt.setString(5, nacinPlacanja);
                    stmt.setString(6, cijena);

                    stmt.executeUpdate();
                    conn.close();

                    JOptionPane.showMessageDialog(null, "Podaci su uspješno uneseni!", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);

                    textField.setText("");
                    textField_1.setText("");
                    textField_2.setText("");
                    textField_3.setText("");
                    textField_4.setText("");
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
        buttonPane.add(cancelButton);
        cancelButton.addActionListener(e -> dispose());

    }
}

