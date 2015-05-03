import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

public class Zarz�dzanie {
	private static BazaDanych baza = new BazaDanych();
	private static GUI window;
	private static Statystyka stat = new Statystyka();
	private static Generacja g = new Generacja(baza);

	public static void main(String[] args) {
		
// ---------------Tworzymy nowy w�tek dla interfejsu graficznego----------------
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					window = new GUI(g.dajKolejke(), baza, stat);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
// ----------------Tworzymy nowy w�tek kt�ry b�dzie nas�uchiwa� i generowa� odpowiedzi---------------
		Thread generuj = new Thread(new Runnable() {
			public void run() {
				g.start(stat.dajKolejke(), window);
			}
		});
		generuj.start();
		
// ----------------Tworzymy nowy w�tek kt�ry b�dzie generowa� statystyki---------------
		Thread statystyki = new Thread(new Runnable() {
			public void run() {
				stat.start();
			}
		});
		statystyki.start();

	}

}
