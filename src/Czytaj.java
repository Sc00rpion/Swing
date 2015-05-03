import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Czytaj {
	private static int stopienDopasowania= -1;

	public static synchronized void wybierzPlikBazowy(JFrame ramka, BazaDanych baza) {
//		-------------------------Ustalenie stopnia dopasowania przez u¿ytkownika---------------------------
		if(stopienDopasowania == -1){
			Integer opcja = (Integer) JOptionPane.showInputDialog(ramka, "Proszê wybraæ stopieñ dopasowania:", "Stopieñ dopasowania", JOptionPane.QUESTION_MESSAGE, null,new Integer[]{1,2,3,4,5}, 2 ); 
			if(opcja == null){
				return;
			}else{
				stopienDopasowania =opcja;
			}
		}
//---------------Pobranie nazw plików bazowych od u¿ytkownika--------------
		JFileChooser inFile = new JFileChooser();
		inFile.setCurrentDirectory(new File("."));
		inFile.setFileFilter(new FileNameExtensionFilter("Plik tekstowy (.txt)", "txt"));
		inFile.setMultiSelectionEnabled(true);
		int result = inFile.showOpenDialog(ramka);
		if (result == JFileChooser.APPROVE_OPTION) {
			odczytajPlik(baza, inFile.getSelectedFiles(), stopienDopasowania);
			System.out.println("Wszystko posz³o ok");
			//System.out.println(baza);
		}

	}

	public static void odczytajPlik(BazaDanych baza, File[] inFile, int stopien) {
		Scanner in = null;
		for (File plik : inFile) {
			try {
				in = new Scanner(plik, "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String tmp;
			String[] prefiksy = new String[stopien];
			for (int i = 0; i < stopien; i++) {
				if (!in.hasNext()) {
					return;
				}
				tmp = in.next();
				prefiksy[i] = tmp;
			}
			while (in.hasNext()) {
				tmp = in.next();
				Ngram value = new Ngram(prefiksy, tmp);
				String key = new String(value.getKey());
				baza.put(key, value);
				for (int i2 = 1; i2 < stopien; i2++) {
					prefiksy[i2 - 1] = prefiksy[i2];
				}
				prefiksy[stopien - 1] = tmp;
			}
			in.close();
		}

	}
	public static int dajStopien(){
		if(stopienDopasowania <= 0){
			return 1;
		}
		return stopienDopasowania;
	}

}
