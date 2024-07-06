package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PrilagodiIspisPROIZVOD extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> comboBox;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PrilagodiIspisPROIZVOD dialog = new PrilagodiIspisPROIZVOD();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PrilagodiIspisPROIZVOD() {
		setBounds(100, 100, 600, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(128, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ispis proizvoda");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(139, 10, 160, 21);
		contentPanel.add(lblNewLabel);

		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] { 
		    "Cijena-manje prema većem",
		    "Cijena-veće prema manjem",
		    "Proizvod sa najvećom šifrom", 
		    "Proizvod sa najmanjom šifrom"
		}));
		comboBox.setBounds(57, 54, 234, 21);
		contentPanel.add(comboBox);

		JLabel lblNewLabel_1 = new JLabel("Uvjet");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 57, 52, 13);
		contentPanel.add(lblNewLabel_1);

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 100, 564, 200);
		contentPanel.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ispisProizvoda();
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

	
	private void ispisProizvoda() {
		String selectedCondition = (String) comboBox.getSelectedItem();
		String query = "";

		switch (selectedCondition) {
			case "Cijena-manje prema većem":
				query = "SELECT * FROM PROIZVOD ORDER BY Cijena_proizvoda ASC";
				break;
			case "Cijena-veće prema manjem":
				query = "SELECT * FROM PROIZVOD ORDER BY Cijena_proizvoda DESC";
				break;
			case "Proizvod sa najvećom šifrom":
				query = "SELECT * FROM PROIZVOD ORDER BY Sifra_proizvoda DESC LIMIT 1";
				break;
			case "Proizvod sa najmanjom šifrom":
				query = "SELECT * FROM PROIZVOD ORDER BY Sifra_proizvoda ASC LIMIT 1";
				break;
			default:
				JOptionPane.showMessageDialog(this, "Nepoznata opcija: " + selectedCondition, "Greška", JOptionPane.ERROR_MESSAGE);
				return;
		}

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr:3306/dsubasic", "dsubasic", "11");
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(query)) {

			
			textArea.setText("");

			
			if (!resultSet.isBeforeFirst()) {
				
				textArea.append("Nema proizvoda koji zadovoljavaju odabrani uvjet.");
			} else {
				
				while (resultSet.next()) {
					textArea.append("Šifra proizvoda: " + resultSet.getInt("Sifra_proizvoda") + "\n");
					textArea.append("Naziv: " + resultSet.getString("Naziv") + "\n");
					textArea.append("Marka: " + resultSet.getString("Marka") + "\n");

					textArea.append("Cijena: " + resultSet.getDouble("Cijena_proizvoda") + "\n");
					textArea.append("---------------------------------\n");
				}
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Greška pri radu s bazom podataka: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}
