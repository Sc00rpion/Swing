import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;

public class GUI {
	private JFrame fRamka;
	private JPanel wyswietl;
	private JTextArea wprowadzTekst;
	private JScrollPane scrollWprowadzTekst;
	private JButton bWyslij;
	private JButton bWyczysc;
	private JMenu mPlik;
	private JScrollPane scrollWyswietl;
	private JMenu mStatystyka;
	private JMenuItem miWyswietlStatystyki;
	private JMenuItem miWczytajPlikBazowy;
	private JMenu mPomoc;
	private JMenuItem miOProgramie;
	private LinkedBlockingQueue<String> kolejka;
	private BazaDanych baza;
	private Statystyka stat;
	private JMenuBar menuBar;
	private LinkedBlockingQueue<KStat> sKolejka;

	public GUI(LinkedBlockingQueue<String> k, BazaDanych baza, Statystyka s) {
		kolejka = k;
		this.baza = baza;
		stat = s;
		sKolejka = s.dajKolejke();
		
		fRamka = new JFrame();
		fRamka.getContentPane().setBackground(new Color(204, 204, 255));
		fRamka.setTitle("AutoCzat");
		fRamka.setBounds(100, 100, 454, 519);
		fRamka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fRamka.getContentPane().setLayout(null);

		setNimbus();

		wprowadzTekst = new JTextArea();
		wprowadzTekst.setLineWrap(true);
		wprowadzTekst.setWrapStyleWord(true);

		scrollWprowadzTekst = new JScrollPane(wprowadzTekst);
		scrollWprowadzTekst.setBounds(10, 321, 418, 102);
		fRamka.getContentPane().add(scrollWprowadzTekst);
		
		bWyslij = new JButton("Wy\u015Blij >>");
		bWyslij.addActionListener(akcjaWyslij);
		bWyslij.setBounds(339, 430, 89, 23);
		fRamka.getContentPane().add(bWyslij);

		bWyczysc = new JButton("Wyczy\u015B\u0107");
		bWyczysc.setBounds(10, 430, 89, 23);
		fRamka.getContentPane().add(bWyczysc);

		scrollWyswietl = new JScrollPane();
		scrollWyswietl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollWyswietl.setBounds(10, 11, 418, 298);
		scrollWyswietl.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		fRamka.getContentPane().add(scrollWyswietl);

		wyswietl = new JPanel();
		wyswietl.setMaximumSize(new Dimension(100, 32767));
		scrollWyswietl.setViewportView(wyswietl);
		wyswietl.setLayout(new BoxLayout(wyswietl, BoxLayout.Y_AXIS));

		menuBar = new JMenuBar();
		fRamka.setJMenuBar(menuBar);

		mPlik = new JMenu("Plik");
		menuBar.add(mPlik);

		miWczytajPlikBazowy = new JMenuItem("Wczytaj plik bazowy");
		miWczytajPlikBazowy.addActionListener(akcjaWczytaniePlikuBazowego);
		mPlik.add(miWczytajPlikBazowy);

		JMenuItem miZamknij = new JMenuItem("Zamknij");
		miZamknij.addActionListener(akcjaZamknij);
		mPlik.add(miZamknij);

		mStatystyka = new JMenu("Statystyka");
		menuBar.add(mStatystyka);

		miWyswietlStatystyki = new JMenuItem("Wy\u015Bwietl statystyki");
		miWyswietlStatystyki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stat.ustawWidocznosc();
			}
		});
		mStatystyka.add(miWyswietlStatystyki);

		mPomoc = new JMenu("Pomoc");
		menuBar.add(mPomoc);

		miOProgramie = new JMenuItem("O programie");
		miOProgramie.addActionListener(akcjaOProgramie);
		mPomoc.add(miOProgramie);

		this.fRamka.setVisible(true);
	}

	private void setNimbus() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(fRamka);
	}

	public void dodajWiadomoscSystemu(String wiadomosc) {
		JPanel panelTmp = new JPanel();
		wyswietl.add(panelTmp);
		panelTmp.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JTextArea atxt = new JTextArea();
		atxt.setFont(new Font("Monospaced", Font.PLAIN, 14));
		atxt.setAlignmentX(Component.RIGHT_ALIGNMENT);
		atxt.setWrapStyleWord(true);
		atxt.setLineWrap(true);
		atxt.setBounds(0, 0, 350, 20);
		atxt.setText(wiadomosc);
		atxt.setEditable(false);
		panelTmp.add(atxt);
		scrollWyswietl.validate();
	}

	private void dodajWiadomoscUzytkownika(String wiadomosc) {
		JPanel panelTmp = new JPanel();
		wyswietl.add(panelTmp);
		panelTmp.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		JTextArea atxt = new JTextArea();
		atxt.setFont(new Font("Monospaced", Font.PLAIN, 14));
		atxt.setAlignmentX(Component.RIGHT_ALIGNMENT);
		atxt.setWrapStyleWord(true);
		atxt.setLineWrap(true);
		atxt.setBounds(0, 0, 350, 20);
		atxt.setText(wiadomosc);
		atxt.setEditable(false);
		panelTmp.add(atxt);
		scrollWyswietl.validate();
	}

	ActionListener akcjaWyslij = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if ( wprowadzTekst.getText().trim().equals("")){
				return;
			}
			dodajWiadomoscUzytkownika(wprowadzTekst.getText());
			try {
				sKolejka.put(new KStat(Kto.UZYTKOWNIK, wprowadzTekst.getText()));
				kolejka.put(wprowadzTekst.getText());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wprowadzTekst.setText("");
		}
	};

	ActionListener akcjaWczytaniePlikuBazowego = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
//	--------------------Tworzymy nowy w¹tek który bêdzie odczytywa³, analizowa³ i zapisywa³ pliki bazowe-------------		
			Thread r = new Thread(new Runnable() {
				public void run() {
					Czytaj.wybierzPlikBazowy(fRamka, baza);
				}
			});
			r.start();
		}
	};
	
	ActionListener akcjaZamknij = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	};
	
	ActionListener akcjaOProgramie = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			JPanel panel_1 = new JPanel();
			panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
			
					JLabel label = new JLabel("Autor: Maciej Skarbek");
					panel_1.add(label);
					label.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 20));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setAlignmentX(Component.CENTER_ALIGNMENT);
					
					JLabel label_5 = new JLabel(" ");
					label_5.setHorizontalAlignment(SwingConstants.CENTER);
					label_5.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 20));
					label_5.setAlignmentX(0.5f);
					panel_1.add(label_5);
					
					JLabel label_4 = new JLabel("Program AutoCzat powsta³ w ramach przedmiotu");
					label_4.setHorizontalAlignment(SwingConstants.CENTER);
					label_4.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 20));
					label_4.setAlignmentX(0.5f);
					panel_1.add(label_4);
					
					JLabel label_3 = new JLabel("Jêzyki i metody programowania 2 prowadzonego na");
					label_3.setHorizontalAlignment(SwingConstants.CENTER);
					label_3.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 20));
					label_3.setAlignmentX(0.5f);
					panel_1.add(label_3);
					
					JLabel label_2 = new JLabel("Politechnice Warszawskiej");
					label_2.setHorizontalAlignment(SwingConstants.CENTER);
					label_2.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 20));
					label_2.setAlignmentX(0.5f);
					panel_1.add(label_2);
					
					JLabel label_1 = new JLabel("w celu poznania jêzyka programowania JAVA");
					label_1.setHorizontalAlignment(SwingConstants.CENTER);
					label_1.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 20));
					label_1.setAlignmentX(0.5f);
					panel_1.add(label_1);
			JOptionPane.showMessageDialog(fRamka,panel_1,"O Programie", JOptionPane.INFORMATION_MESSAGE);
		}
	};
}
