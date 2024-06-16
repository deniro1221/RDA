package kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AzurirajBazu extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AzurirajBazu dialog = new AzurirajBazu();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AzurirajBazu() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(128, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Izbornik");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(163, 30, 196, 21);
		contentPanel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("KUPAC");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AzurirajKupac dlg = new AzurirajKupac();
				dlg.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(39, 69, 113, 21);
		contentPanel.add(btnNewButton);
		
		JButton btnProizvod = new JButton("PROIZVOD");
		btnProizvod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AzurirajProizvod dlg = new AzurirajProizvod();
				dlg.setVisible(true);
				
			}
		});
		btnProizvod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnProizvod.setBounds(256, 69, 126, 21);
		contentPanel.add(btnProizvod);
		
		JButton btnProizvod_1 = new JButton("PRODAVAČ");
		btnProizvod_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AzurirajProdavac dlg = new AzurirajProdavac();
				dlg.setVisible(true);
				
			}
		});
		btnProizvod_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnProizvod_1.setBounds(39, 130, 113, 21);
		contentPanel.add(btnProizvod_1);
		
		JButton btnNarudba = new JButton("NARUDŽBA");
		btnNarudba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AzurirajNarudzba dlg = new AzurirajNarudzba();
				dlg.setVisible(true);
				
				
				
			}
		});
		btnNarudba.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNarudba.setBounds(256, 131, 126, 21);
		contentPanel.add(btnNarudba);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
