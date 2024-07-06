package src.kozmetikaWebRDA.kozmetikaWebRDA;

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

public class MenuAdmin extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MenuAdmin dialog = new MenuAdmin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MenuAdmin() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(128, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Odabir iz izbornika");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblNewLabel.setBounds(142, 31, 210, 21);
			contentPanel.add(lblNewLabel);
		}
		{
			JButton btnNewButton = new JButton("Unos u bazu ");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UnosBaza dlg = new UnosBaza();
					dlg.setVisible(true);
				}
			});
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnNewButton.setBounds(38, 80, 119, 30);
			contentPanel.add(btnNewButton);
		}
		{
			JButton btnIspisRauna = new JButton("Ispis podatka");
			btnIspisRauna.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnIspisRauna.setBounds(38, 139, 119, 30);
			contentPanel.add(btnIspisRauna);
			  btnIspisRauna.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent e) {
			        	IspisIzbornik dlg= new IspisIzbornik();
			        	dlg.setVisible(true);
			            
			        }
			    });
		
		
		}
		
		JButton btnAurirajBazu = new JButton("Ažuriraj bazu");
		btnAurirajBazu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AzurirajBazu dlg=new AzurirajBazu();
				dlg.setVisible(true);
			}
		});
		btnAurirajBazu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAurirajBazu.setBounds(211, 80, 119, 30);
		contentPanel.add(btnAurirajBazu);
		
		JButton btnStornoUlaza = new JButton("Storno ulaza");
		btnStornoUlaza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StornoIzbornik dlg= new StornoIzbornik();
				dlg.setVisible(true);
			}
		});
		btnStornoUlaza.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnStornoUlaza.setBounds(211, 139, 119, 30);
		contentPanel.add(btnStornoUlaza);
		
		JButton btnApi = new JButton("API");
		btnApi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpajanjeAPI dlg= new SpajanjeAPI ();
				dlg.setVisible(true);
			}
		});
		btnApi.setBackground(new Color(128, 255, 0));
		btnApi.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApi.setBounds(133, 179, 119, 30);
		contentPanel.add(btnApi);
		
		JButton btnProvjera = new JButton("PROVJERA");
		btnProvjera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProvjeraIzbornik dlg = new ProvjeraIzbornik ();
				dlg.setVisible(true);
			}
		});
		btnProvjera.setBackground(new Color(255, 128, 255));
		btnProvjera.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnProvjera.setBounds(285, 179, 119, 30);
		contentPanel.add(btnProvjera);
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
				cancelButton.setActionCommand("Cancel");
                cancelButton.addActionListener(e -> dispose());
				buttonPane.add(cancelButton);
			}
		}
	}
}
