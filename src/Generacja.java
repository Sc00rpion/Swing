import java.awt.EventQueue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;





public class Generacja {
	private BazaDanych baza = null;
	private final int MIN_ILOSC_SLOW = 10;
	private final int MAX_ILOSC_SLOW = 30;
	private LinkedBlockingQueue<String> gKolejka = new LinkedBlockingQueue<String>();
	
	public Generacja(BazaDanych b) {
		baza = b;
	}

	public void start(LinkedBlockingQueue<KStat> sKolejka, GUI guii) {
		while (true) {
			try {
				String[] tWyrazow = gKolejka.take().split(" ");
				String t = null;
				int stopien = Czytaj.dajStopien();
				Random r = new Random();
				int iloscSlow = r.nextInt(MAX_ILOSC_SLOW - MIN_ILOSC_SLOW) + MIN_ILOSC_SLOW;
				for (int i = (tWyrazow.length - 1); i >= stopien - 1; i--) {
					t = dajSlowa(dajKlucz(tWyrazow, i - stopien + 1, i ), iloscSlow);
					if ( t != null){
						break;
					}
				}
				if(t == null){
					t = new String("Wysil siê troche i napisz do mnie coœ wiêcej");
				}
				String t2 = new String(t);
				sKolejka.put(new KStat(Kto.SYSTEM, t2));
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						guii.dodajWiadomoscSystemu(t2);
					}
				});
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public LinkedBlockingQueue<String> dajKolejke(){
		return gKolejka;
	}

	public String dajSlowa(String txt, int iloscSlow) {
		Ngram tmp = baza.get(txt);
		if (tmp == null) {
			return null;
		} else {
			StringBuilder builder = new StringBuilder("");
			for (int i = 0; i < iloscSlow; i++) {
				String sufiks = new String(tmp.getRandomSufiks());
				builder.append(sufiks).append(' ');
				tmp = baza.get(tmp.getKey(sufiks));

			}
			return builder.toString();
		}
	}

	private String dajKlucz(String[] tabs, int p, int k) {
		String wynik = new String(tabs[p]);
		for (int i = p + 1; i <= k; i++) {
			wynik = wynik + " " + tabs[i];
		}
		return wynik;
	}

}
