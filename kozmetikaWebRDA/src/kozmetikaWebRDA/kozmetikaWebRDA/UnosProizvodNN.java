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
public class UnosProizvodNN extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	
	public static void main(String[] args) {
		try {
			UnosProizvodNN dialog = new UnosProizvodNN();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public UnosProizvodNN() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(128, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Unos Proizvod na narudžbi");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblNewLabel.setBounds(96, 33, 266, 21);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Šifra narudžbe");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_1.setBounds(32, 91, 99, 13);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Šifra proizvoda");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_1.setBounds(32, 137, 114, 13);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Količina naručenog proizvoda");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_1.setBounds(32, 185, 184, 13);
			contentPanel.add(lblNewLabel_1);
		}
		
		JTextField textField = new JTextField();
		textField.setBounds(234, 85, 128, 19);
		contentPanel.add(textField);
		
		JTextField textField_1 = new JTextField();
		textField_1.setBounds(234, 131, 128, 19);
		contentPanel.add(textField_1);
		
		JTextField textField_2 = new JTextField();
		textField_2.setBounds(234, 185, 128, 19);
		contentPanel.add(textField_2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						 String sifraNarudzbe = textField.getText();
			                String sifraProizvoda = textField_1.getText();
			                String kolicinaNarucenogProizvoda = textField_2.getText();
			                
			                
			                try {
			                    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			                    Connection conn = DriverManager.getConnection(
			                            "jdbc:mysql://ucka.veleri.hr:3306/dsubasic", "dsubasic", "11");

			                    String sql = "INSERT INTO PROIZVOD_NA_NARUDZBI (Sifra_narudzbe, Sifra_proizvoda, Kolicina_narucenog_proizvoda) VALUES (?, ?, ?)";
			                    PreparedStatement stmt = conn.prepareStatement(sql);
			                    stmt.setString(1, sifraNarudzbe);
			                    stmt.setString(2, sifraProizvoda);
			                    stmt.setString(3,kolicinaNarucenogProizvoda);
			                   
			                   
			                    stmt.executeUpdate();
			                    conn.close();

			                    JOptionPane.showMessageDialog(null, "Podaci su uspješno uneseni!", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);

			                    textField.setText("");
			                    textField_1.setText("");
			                    textField_2.setText("");
			                    
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
                cancelButton.addActionListener(e -> dispose());

			}
		}
	}
}

