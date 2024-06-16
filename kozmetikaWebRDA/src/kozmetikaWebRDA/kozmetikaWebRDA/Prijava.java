package kozmetikaWebRDA.kozmetikaWebRDA;

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
import javax.swing.JTextField;
import javax.swing.JPasswordField; 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Prijava extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField korisnickoImeTextField; 
	private JPasswordField lozinkaPasswordField; 

	
	private static final String[] ADMIN_USERNAMES = {"admin1", "admin2", "admin3"};
	private static final String ADMIN_PASSWORD = "123";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Prijava dialog = new Prijava();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Prijava() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(128, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Prijava u sustav");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblNewLabel.setBounds(131, 42, 183, 26);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblKorisnickoIme = new JLabel("Korisničko ime");
			lblKorisnickoIme.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblKorisnickoIme.setBounds(48, 103, 80, 13);
			contentPanel.add(lblKorisnickoIme);
		}
		{
			JLabel lblLozinka = new JLabel("Lozinka");
			lblLozinka.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblLozinka.setBounds(48, 151, 45, 13);
			contentPanel.add(lblLozinka);
		}
		{
			korisnickoImeTextField = new JTextField();
			korisnickoImeTextField.setBounds(138, 101, 194, 19);
			contentPanel.add(korisnickoImeTextField);
			korisnickoImeTextField.setColumns(10);
		}
		{
			lozinkaPasswordField = new JPasswordField(); 
			lozinkaPasswordField.setBounds(138, 149, 194, 19);
			contentPanel.add(lozinkaPasswordField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String un = korisnickoImeTextField.getText(); 
						String pas = new String(lozinkaPasswordField.getPassword()); 
						
						boolean isAdmin = false;
						for (String adminUsername : ADMIN_USERNAMES) {
							if (un.equals(adminUsername) && pas.equals(ADMIN_PASSWORD)) {
								isAdmin = true;
								break;
							}
						}
						
						if (isAdmin) {
							JOptionPane.showMessageDialog(null,"Prijava uspješna");
							MenuAdmin dlg = new MenuAdmin();
							dlg.setVisible(true);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null,  "Pogrešna lozinka ili korisničko ime");
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

}
