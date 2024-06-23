package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

public class KreirajTablicu extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldImeTablice;
    private JTextField textFieldNazivStupca;
    private JComboBox<String> comboBoxTipPodatka;
    private JTextField textFieldDuljina;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            KreirajTablicu dialog = new KreirajTablicu();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public KreirajTablicu() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(128, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("KREIRANJE TABLICE U BAZI ");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(100, 10, 237, 29);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Ime tablice:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(20, 70, 74, 15);
        contentPanel.add(lblNewLabel_1);

        textFieldImeTablice = new JTextField();
        textFieldImeTablice.setBounds(138, 69, 230, 19);
        contentPanel.add(textFieldImeTablice);
        textFieldImeTablice.setColumns(10);

        JLabel lblNewLabel_1_1 = new JLabel("Stvori stupac:");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1.setBounds(20, 109, 94, 15);
        contentPanel.add(lblNewLabel_1_1);

        textFieldNazivStupca = new JTextField();
        textFieldNazivStupca.setColumns(10);
        textFieldNazivStupca.setBounds(138, 108, 230, 19);
        contentPanel.add(textFieldNazivStupca);

        JLabel lblNewLabel_1_1_1 = new JLabel("Tip podatka:");
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_1_1_1.setBounds(20, 156, 74, 15);
        contentPanel.add(lblNewLabel_1_1_1);

        comboBoxTipPodatka = new JComboBox<>();
        comboBoxTipPodatka.addItem("INT");
        comboBoxTipPodatka.addItem("DOUBLE");
        comboBoxTipPodatka.addItem("DATE");
        comboBoxTipPodatka.addItem("VARCHAR");
        comboBoxTipPodatka.setBounds(138, 154, 100, 21);
        contentPanel.add(comboBoxTipPodatka);

        textFieldDuljina = new JTextField();
        textFieldDuljina.setBounds(248, 154, 120, 19);
        contentPanel.add(textFieldDuljina);
        textFieldDuljina.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kreirajStupacUTablici();
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    private void kreirajStupacUTablici() {
        String imeTablice = textFieldImeTablice.getText().trim();
        String nazivStupca = textFieldNazivStupca.getText().trim();
        String tipPodatka = (String) comboBoxTipPodatka.getSelectedItem();
        String duljina = textFieldDuljina.getText().trim();

        if (imeTablice.isEmpty() || nazivStupca.isEmpty()) {
            return; 
        }

        
        String createColumnQuery = "ALTER TABLE `" + imeTablice + "` ADD COLUMN `" + nazivStupca + "` " + tipPodatka;
        
        
        if ("VARCHAR".equals(tipPodatka) && !duljina.isEmpty()) {
            createColumnQuery += "(" + duljina + ")";
        }

        try {
            
            Connection connection = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr:3306/dsubasic", "dsubasic", "11");

            
            Statement statement = connection.createStatement();
            statement.executeUpdate(createColumnQuery);

           
            statement.close();
            connection.close();

            
            JOptionPane.showMessageDialog(this, "Stupac uspješno kreiran.", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(this, "Greška prilikom kreiranja stupca: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
