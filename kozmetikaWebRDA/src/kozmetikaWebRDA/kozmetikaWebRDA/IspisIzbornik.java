package kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IspisIzbornik extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IspisIzbornik dialog = new IspisIzbornik();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public IspisIzbornik() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(128, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Izaberi za ispis");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(128, 10, 146, 21);
		contentPanel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("KUPAC");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IspisKupac dlg= new IspisKupac();
				dlg.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(34, 70, 106, 21);
		contentPanel.add(btnNewButton);
		
		JButton btnProdava = new JButton("PRODAVAČ");
		btnProdava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IspisiProdavac dlg=new IspisiProdavac();
				dlg.setVisible(true);
			}
		});
		btnProdava.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnProdava.setBounds(234, 71, 106, 21);
		contentPanel.add(btnProdava);
		
		JButton btnNarudba = new JButton("NARUDŽBA");
		btnNarudba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IspisiNarudzbu dlg=new IspisiNarudzbu();
				dlg.setVisible(true);
			}
		});
		btnNarudba.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNarudba.setBounds(34, 127, 106, 21);
		contentPanel.add(btnNarudba);
		
		JButton btnProizvod = new JButton("PROIZVOD");
		btnProizvod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IspisiProizvod dlg=new IspisiProizvod();
				dlg.setVisible(true);
			}
		});
		btnProizvod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnProizvod.setBounds(242, 128, 98, 21);
		contentPanel.add(btnProizvod);
		
		JButton btnIzlaz = new JButton("IZLAZ");
		btnIzlaz.setBackground(new Color(255, 0, 0));
		btnIzlaz.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnIzlaz.setBounds(143, 181, 106, 21);
	    btnIzlaz.addActionListener(e -> dispose());

		contentPanel.add(btnIzlaz);
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
				buttonPane.add(cancelButton);
			    cancelButton.addActionListener(e -> dispose());

			}
		}
	}
}
