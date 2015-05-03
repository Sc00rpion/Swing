import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;

public class GUIStatystyka {

	public JFrame frmStatystyka;
	public JPanel pSystem;
	public JPanel pUzytkownik;
	public JTextPane txtUzytkownik;
	public JTextPane txtSystem;
	public JPanel pWykresUzytkownik;
	public JPanel pWykresSystem;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public GUIStatystyka() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStatystyka = new JFrame();
		frmStatystyka.setTitle("Statystyka");
		frmStatystyka.setBounds(100, 100, 679, 556);
		frmStatystyka.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		frmStatystyka.setVisible(false);

		pUzytkownik = new JPanel();
		frmStatystyka.getContentPane().add(pUzytkownik);
		pUzytkownik.setLayout(new BorderLayout(0, 0));
		
				JLabel lUzytkownik = new JLabel("U\u017Cytkownik");
				lUzytkownik.setFont(new Font("Times New Roman", Font.BOLD, 20));
				lUzytkownik.setHorizontalAlignment(SwingConstants.CENTER);
				pUzytkownik.add(lUzytkownik, BorderLayout.NORTH);

		txtUzytkownik = new JTextPane();
		txtUzytkownik.setEditable(false);
		pUzytkownik.add(txtUzytkownik, BorderLayout.SOUTH);
		
		pWykresUzytkownik = new JPanel();
		pUzytkownik.add(pWykresUzytkownik);
		pWykresUzytkownik.setLayout(new BorderLayout(0, 0));

		pSystem = new JPanel();
		frmStatystyka.getContentPane().add(pSystem);
		pSystem.setLayout(new BorderLayout(0, 0));

		JLabel lSystem = new JLabel("System");
		lSystem.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lSystem.setHorizontalAlignment(SwingConstants.CENTER);
		pSystem.add(lSystem, BorderLayout.NORTH);

		txtSystem = new JTextPane();
		txtSystem.setEditable(false);
		pSystem.add(txtSystem, BorderLayout.SOUTH);
		
		pWykresSystem = new JPanel();
		pSystem.add(pWykresSystem);
		pWykresSystem.setLayout(new BorderLayout(0, 0));
	}

	public void ustawWidocznosc() {
		frmStatystyka.setVisible(true);
	}
}
