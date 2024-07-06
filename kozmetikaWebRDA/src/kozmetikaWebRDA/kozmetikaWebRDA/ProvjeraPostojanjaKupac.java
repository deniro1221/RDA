package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ProvjeraPostojanjaKupac extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ProvjeraPostojanjaKupac dialog = new ProvjeraPostojanjaKupac();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ProvjeraPostojanjaKupac() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("PROVJERA KUPCA");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel_1.setBounds(129, 10, 157, 21);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Kriterij");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_2.setBounds(10, 46, 45, 13);
        contentPanel.add(lblNewLabel_2);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Šifra kupca", "Ime kupca", "Prezime kupca"}));
        comboBox.setBounds(65, 43, 258, 21);
        contentPanel.add(comboBox);

        JLabel lblNewLabel_3 = new JLabel("Unos");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_3.setBounds(10, 90, 45, 13);
        contentPanel.add(lblNewLabel_3);

        textField = new JTextField();
        textField.setBounds(65, 87, 258, 19);
        contentPanel.add(textField);
        textField.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String kriterij = comboBox.getSelectedItem().toString();
                String unos = textField.getText();

                if (unos.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Unos ne smije biti prazan.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String sql = "";
                switch (kriterij) {
                    case "Šifra kupca":
                        sql = "SELECT * FROM KUPAC WHERE Sifra_kupca = ?";
                        break;
                    case "Ime kupca":
                        sql = "SELECT * FROM KUPAC WHERE Ime_kupca = ?";
                        break;
                    case "Prezime kupca":
                        sql = "SELECT * FROM KUPAC WHERE Prezime_kupca = ?";
                        break;
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://ucka.veleri.hr:3306/dsubasic", "dsubasic", "11");
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, unos);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Kupac pronađen u bazi podataka.", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Kupac nije pronađen u bazi podataka.", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
                    }

                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(cancelButton);
    }
}
