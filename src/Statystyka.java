import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Statystyka {
	private BazaStatystyki uzytkownik = new BazaStatystyki();
	private BazaStatystyki system = new BazaStatystyki();
	private GUIStatystyka guiOkno;
	private LinkedBlockingQueue<KStat> sKolejka = new LinkedBlockingQueue<KStat>();

	public Statystyka() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiOkno = new GUIStatystyka();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LinkedBlockingQueue<KStat> dajKolejke() {
		return sKolejka;
	}

	public void dodajStatystykeSystemu(String tekst) {
		String[] tabS = tekst.split(" ");
		for (String x : tabS) {
			system.dodajSlowo(x);
		}
		String tekstStatystyki = system.generujStatystyke(9);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiOkno.txtSystem.setText(tekstStatystyki);
					JFreeChart chart = ChartFactory.createPieChart3D(null, system.wykres);
					ChartPanel cPanel = new ChartPanel(chart);
					guiOkno.pWykresSystem.removeAll();
					guiOkno.pWykresSystem.add(cPanel, BorderLayout.CENTER);
					guiOkno.pWykresSystem.validate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void dodajStatystykeUzytkownika(String tekst) {
		String[] tabS = tekst.split(" ");
		for (String x : tabS) {
			uzytkownik.dodajSlowo(x);
		}
		String tekstUzytkownika = uzytkownik.generujStatystyke(9);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiOkno.txtUzytkownik.setText(tekstUzytkownika);
					JFreeChart chart = ChartFactory.createPieChart3D(null, uzytkownik.wykres);
					ChartPanel cPanel = new ChartPanel(chart);
					guiOkno.pWykresUzytkownik.removeAll();
					guiOkno.pWykresUzytkownik.add(cPanel, BorderLayout.CENTER);
					guiOkno.pWykresUzytkownik.validate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void ustawWidocznosc() {
		guiOkno.frmStatystyka.setVisible(true);
	}

	public void start() {
		while (true) {
			try {
				KStat tmp = sKolejka.take();
				if (tmp.kto.equals(Kto.SYSTEM)) {
					dodajStatystykeSystemu(tmp.tekst);
				} else {
					dodajStatystykeUzytkownika(tmp.tekst);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class BazaStatystyki {

		public Slowo[] tab = new Slowo[100];
		public int unikalneSlowa = 0;
		public int wszystkieSlowa = 0;
		public DefaultPieDataset wykres;

		public void dodajWykres() {
			JFreeChart chart = ChartFactory.createPieChart3D(null, wykres);
			ChartPanel cPanel = new ChartPanel(chart);
			guiOkno.pWykresSystem.removeAll();
			guiOkno.pWykresSystem.add(cPanel, BorderLayout.CENTER);
			guiOkno.pWykresSystem.validate();
		}

		public void dodajSlowo(String s) {
			powieksz();
			Slowo tmp = szukajSlowa(s);
			if (tmp == null) {
				tab[unikalneSlowa] = new Slowo(s);
				unikalneSlowa++;
			} else {
				tmp.licznik++;
			}
			wszystkieSlowa++;
		}

		public void powieksz() {
			if (tab.length == unikalneSlowa) {
				Slowo[] tmp = new Slowo[unikalneSlowa * 2];
				for (int i = 0; i < unikalneSlowa; i++) {
					tmp[i] = tab[i];
				}
				tab = tmp;
			}
		}

		public Slowo szukajSlowa(String s) {
			int n = unikalneSlowa;
			for (int i = 0; i < n; i++) {
				if (tab[i].slowo.equals(s)) {
					return tab[i];
				}
			}
			return null;
		}

		public String generujStatystyke(int ilosc) {
			Arrays.sort(tab, 0, unikalneSlowa);
			StringBuilder builder = new StringBuilder();
			wykres = new DefaultPieDataset();
			int inne = wszystkieSlowa;
			if (unikalneSlowa < ilosc) {
				ilosc = unikalneSlowa;
			}
			for (int i = 0; i < ilosc; i++) {
				wykres.setValue(tab[i].slowo, tab[i].licznik);
				inne -= tab[i].licznik;
				builder.append(tab[i].slowo).append(" -> ").append(tab[i].licznik).append(" -> ")
						.append(tab[i].prawdopodobiensto()).append("\n");
			}
			wykres.setValue("Inne", inne);
			return builder.toString();
		}

		private class Slowo implements Comparable<Slowo> {
			public int licznik = 0;
			public String slowo;

			public Slowo(String s) {
				slowo = new String(s);
				licznik++;
			}

			public double prawdopodobiensto() {
				return (double) this.licznik / wszystkieSlowa;
			}

			@Override
			public int compareTo(Slowo o) {
				if (o == null)
					return -1;
				return o.licznik - this.licznik;
			}

		}
	}

}
