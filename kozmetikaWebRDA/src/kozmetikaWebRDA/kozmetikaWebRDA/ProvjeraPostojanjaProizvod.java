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
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProvjeraPostojanjaProizvod extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ProvjeraPostojanjaProizvod dialog = new ProvjeraPostojanjaProizvod();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ProvjeraPostojanjaProizvod() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("PROVJERA PROIZVOD");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(116, 10, 190, 21);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_2 = new JLabel("Kriterij");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_2.setBounds(10, 47, 45, 13);
        contentPanel.add(lblNewLabel_2);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Šifra proizvoda", "Marka proizvoda", "Naziv proizvoda"}));
        comboBox.setBounds(78, 44, 281, 21);
        contentPanel.add(comboBox);

        JLabel lblNewLabel_3 = new JLabel("Unos");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_3.setBounds(10, 83, 45, 13);
        contentPanel.add(lblNewLabel_3);

        textField = new JTextField();
        textField.setBounds(78, 75, 281, 19);
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
                String sql = "";

                if (unos.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Unos polje ne smije biti prazno.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                switch (kriterij) {
                    case "Šifra proizvoda":
                        sql = "SELECT * FROM PROIZVOD WHERE Sifra_proizvoda = ?";
                        break;
                    case "Marka proizvoda":
                        sql = "SELECT * FROM PROIZVOD WHERE Marka = ?";
                        break;
                    case "Naziv proizvoda":
                        sql = "SELECT * FROM PROIZVOD WHERE Naziv = ?";
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
                        JOptionPane.showMessageDialog(null, "Proizvod pronađen u bazi podataka.", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Proizvod nije pronađen u bazi podataka.", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
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
