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

public class UnosKupac extends JDialog {

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
            UnosKupac dialog = new UnosKupac();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public UnosKupac() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Unos kupca");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(148, 25, 123, 21);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Šifra kupca");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(10, 68, 77, 13);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Ime kupca");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1.setBounds(10, 101, 77, 13);
        contentPanel.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Prezime kupca");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_2.setBounds(10, 131, 77, 13);
        contentPanel.add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_3 = new JLabel("Adresa kupca");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_3.setBounds(10, 164, 77, 13);
        contentPanel.add(lblNewLabel_1_3);

        JLabel lblNewLabel_1_4 = new JLabel("Email");
        lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_4.setBounds(10, 198, 65, 13);
        contentPanel.add(lblNewLabel_1_4);

        textField = new JTextField();
        textField.setBounds(131, 66, 187, 19);
        contentPanel.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(131, 99, 187, 19);
        contentPanel.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(131, 129, 187, 19);
        contentPanel.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(131, 162, 187, 19);
        contentPanel.add(textField_3);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(131, 196, 187, 19);
        contentPanel.add(textField_4);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String sifraKupca = textField.getText();
                    String imeKupca = textField_1.getText();
                    String prezimeKupca = textField_2.getText();
                    String adresaKupca = textField_3.getText();
                    String emailKupca = textField_4.getText();

                    if (sifraKupca.isEmpty() || imeKupca.isEmpty() || prezimeKupca.isEmpty() || adresaKupca.isEmpty() || emailKupca.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Sva polja moraju biti popunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                        Connection conn = DriverManager.getConnection(
                                "jdbc:mysql://ucka.veleri.hr:3306/dsubasic", "dsubasic", "11");

                        String sql = "INSERT INTO KUPAC (Sifra_kupca, Ime_kupca, Prezime_kupca, Adresa_kupca, E_mail) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, sifraKupca);
                        stmt.setString(2, imeKupca);
                        stmt.setString(3, prezimeKupca);
                        stmt.setString(4, adresaKupca);
                        stmt.setString(5, emailKupca);

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
        }
        {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }
    }
}
