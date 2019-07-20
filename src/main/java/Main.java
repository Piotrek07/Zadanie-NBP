import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
@Getter
@Setter
public class Main {

    private static final String GBP = "http://api.nbp.pl/api/exchangerates/rates/C/GBP";
    private static final String USD = "http://api.nbp.pl/api/exchangerates/rates/C/USD";
    private static final String EUR = "http://api.nbp.pl/api/exchangerates/rates/C/EUR";
    private static final String CHF = "http://api.nbp.pl/api/exchangerates/rates/C/CHF";


    public static void main(String[] args) throws IOException {


        java.text.DecimalFormat df = new java.text.DecimalFormat("0.000");



        String[] Tablica = {GBP, USD, EUR, CHF};

        for (String Url : Tablica) {
            double kursyWalutObecne = polaczenie(Url).getRates().get(0).getAsk();
            double kursyWalutMiesiacWczesnieej = polaczenie(urlMiesiacWczesniej(Url, dataMiesiacWczesniej()))
                    .getRates().get(0).getAsk();
            System.out.println("Waluta: " + polaczenie(Url).getCurrency());
            System.out.println("Kurs walut miesiac wczesniej, z daty: "+ dataMiesiacWczesniejDoDruku()
                    +  " to: " + df.format(kursyWalutMiesiacWczesnieej));
            System.out.println("Kursy walut obecnie, za daty "+ obecnaData() + " to: "+ kursyWalutObecne);


            double miesiacPrzed = przelicznik(100, kursyWalutMiesiacWczesnieej);
            double obecnie = przelicznik(100, kursyWalutObecne);

            double wyniczek = roznicaMiedzyMiesiacami(miesiacPrzed, obecnie);
            System.out.println("Roznica kursow w obcej walucie" +  " to:"  + df.format(wyniczek));
            double przelicznikNaPLNFinal = przelicznikNaPLN(wyniczek, obecnie);

            double jakiZyskLubStrara = zyskLubStrata(przelicznikNaPLNFinal);
            System.out.println(df.format(jakiZyskLubStrara) + "\n");
        }
    }



    private static Waluty polaczenie(String url) throws IOException {
        URL funt = new URL(url);
        URLConnection connection = funt.openConnection();
        Scanner scanfunt = new Scanner(connection.getInputStream());
        String pobraneDane = scanfunt.nextLine();
        Gson gson = new Gson();
        return gson.fromJson(pobraneDane, Waluty.class);
    }

    private static double przelicznik(double iloscWalutydoPrzeliczenia, double kurs) {
        double poPrzeliczeniu = iloscWalutydoPrzeliczenia / kurs;
        return poPrzeliczeniu;
    }


    private static double przelicznikNaPLN(double iloscWalutydoPrzeliczenia, double kurs) {
        double poPrzeliczeniu = iloscWalutydoPrzeliczenia * kurs;
        return poPrzeliczeniu;

    }

    public static String urlMiesiacWczesniej(String urlMiesicMinusJeden, String ustawDate) {
        String miesiacWczesniej = urlMiesicMinusJeden + ustawDate;
        return miesiacWczesniej;
    }


    public static double roznicaMiedzyMiesiacami(double iloscKupionychMiesiecPrzed, double iloscKupionychObecnie) {
        double wynikRoznicy = iloscKupionychMiesiecPrzed - iloscKupionychObecnie;
        return wynikRoznicy;
    }

    public static double zyskLubStrata(double zyskLubStrata) {
        if (zyskLubStrata > 0) {
            System.out.println("Zysk w zlotowkach: ");
        } else if (zyskLubStrata == 0) {
            System.out.println("Wyszedles na zero");
        } else if (zyskLubStrata < 0) {
            System.out.println("Strata w zlotowkach:  ");
        }
        return zyskLubStrata;
    }



    static String dataMiesiacWczesniej() {
        Calendar kalendarz = Calendar.getInstance();
        kalendarz.add(Calendar.MONTH, -1);
        Date miesiacwczesniej = kalendarz.getTime();
        SimpleDateFormat format = new SimpleDateFormat("/yyyy-MM-dd");
        String ustawienieFormatu = format.format(miesiacwczesniej);
        return ustawienieFormatu;
    }

    static String obecnaData(){
        String obecnaData;
        java.util.Date date = new java.util.Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        obecnaData = dateFormat.format(date);
        return obecnaData;
    }

    static String dataMiesiacWczesniejDoDruku() {
        Calendar kalendarz = Calendar.getInstance();
        kalendarz.add(Calendar.MONTH, -1);
        Date miesiacwczesniej = kalendarz.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String ustawienieFormatu = format.format(miesiacwczesniej);
        return ustawienieFormatu;
    }

}
