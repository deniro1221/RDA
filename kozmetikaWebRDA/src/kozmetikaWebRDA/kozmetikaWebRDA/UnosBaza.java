package src.kozmetikaWebRDA.kozmetikaWebRDA;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UnosBaza extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnosBaza frame = new UnosBaza();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UnosBaza() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Odabir željene akcije");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(124, 27, 168, 21);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("KUPAC");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnosKupac dlg = new UnosKupac();
				dlg.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(29, 81, 93, 30);
		contentPane.add(btnNewButton);
		
		JButton btnNarudba = new JButton("NARUDŽBA");
		btnNarudba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnosNarudzba dlg= new UnosNarudzba();
				dlg.setVisible(true);
				
			}
		});
		btnNarudba.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNarudba.setBounds(168, 81, 93, 30);
		contentPane.add(btnNarudba);
		
		JButton btnNewButton_1_1 = new JButton("PRODAVAČ");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnosProdavac dlg = new UnosProdavac();
				dlg.setVisible(true);
			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1_1.setBounds(320, 81, 93, 30);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnProizvod = new JButton("PROIZVOD");
		btnProizvod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnosProizvod dlg = new UnosProizvod();
				dlg.setVisible(true);
			}
		});
		btnProizvod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnProizvod.setBounds(98, 134, 93, 30);
		contentPane.add(btnProizvod);
		
		JButton btnProizvodNn = new JButton("PROIZVOD N-N");
		btnProizvodNn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnosProizvodNN dlg = new UnosProizvodNN();
				dlg.setVisible(true);
			}
		});
		btnProizvodNn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnProizvodNn.setBounds(248, 134, 132, 30);
		contentPane.add(btnProizvodNn);
		
		JButton btnIzlaz = new JButton("IZLAZ");
		btnIzlaz.setBackground(new Color(255, 0, 0));
        btnIzlaz.addActionListener(e -> dispose());

		btnIzlaz.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnIzlaz.setBounds(333, 223, 93, 30);
		contentPane.add(btnIzlaz);
		
		JButton btnKreirajTablicu = new JButton("KREIRAJ TABLICU");
		btnKreirajTablicu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KreirajTablicu dlg=new KreirajTablicu ();
				dlg.setVisible(true);
						
			}
		});
		btnKreirajTablicu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnKreirajTablicu.setBounds(160, 188, 132, 30);
		contentPane.add(btnKreirajTablicu);
	}
}
