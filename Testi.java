import java.io.IOException;
import java.util.Scanner;

/**
 * Luokka joka on Opiskelija, Kurssi, ja Suoritus luokkien testaamista varten.
 * @author Patrik Soininen
 */
public class Testi {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        paavalikko();
    }

    public static void paavalikko() throws IOException, ClassNotFoundException {

        //Luodaan tyhjät Opiskelija ja Kurssi luokan oliot jotta päästään käsiksi luokkien
        //metodeihin joilla haetaan muita olioita tiedostoista
        Opiskelija o = new Opiskelija();
        Kurssi k = new Kurssi();

        Scanner sc = new Scanner(System.in);

        //Tulostetaan vaihtoehdot käyttäjälle ja pyydetään valitsemaan.
        System.out.println("--------------\nPaavalikko");
        System.out.println("[1]Lisaa opiskelija\n[2]Lisaa kurssi\n[3]Lisaa suoritus" +
                "\n[4]Hae opiskelijan tietoja\n[5]Hae kurssin tietoja\n[6]Lopeta");

        System.out.print("--------------\nValitse: ");
        int valinta1 = sc.nextInt();

        //switch case valitun vaihtoehdon perusteella.
        switch (valinta1){

            case 1: //"Lisaa opiskelija"
                //Kysytään opiskelijan tiedot ja luodaan niillä uusi Opiskelija olio.
                System.out.print("Etunimi: ");
                String etunimi = sc.next();
                sc.nextLine();
                System.out.print("Sukunimi: ");
                String sukunimi = sc.next();
                sc.nextLine();
                System.out.print("Lahiosoite: ");
                String lahiosoite = sc.nextLine();
                System.out.print("Postitoimipaikka: ");
                String postitoimi = sc.nextLine();
                System.out.print("Postinumero: ");
                String postinumero = sc.next();
                sc.nextLine();
                System.out.print("Email: ");
                String m_email = sc.next();
                sc.nextLine();
                System.out.print("Puhelinnumero: ");
                String puhnumero = sc.next();
                new Opiskelija(etunimi, sukunimi, lahiosoite, postitoimi, postinumero, m_email, puhnumero);
                System.out.println("Opiskelijan id-numero on " + o.getOpiskelijat().get(o.getOpiskelijaLaskuri() - 1).getOpiskelija_id());
                //Palataan aloitukseen
                paavalikko();

            case 2: //"Lisaa kurssi"
                //Kysytään kurssin tiedot ja luodaan niillä uusi Kurssi olio.
                System.out.print("Kurssin nimi: ");
                String kurssinimi = sc.next();
                sc.nextLine();
                System.out.print("Kuvaus: ");
                String kuvaus = sc.next();
                sc.nextLine();
                System.out.print("Opintopisteet: ");
                int oppisteet = sc.nextInt();
                new Kurssi(oppisteet, kurssinimi, kuvaus);
                System.out.println("Kurssin id-numero on " + k.getKurssit().get(k.getKurssiLaskuri() - 1).getKurssi_id());
                //Palataan aloitukseen
                paavalikko();

            case 3: //"Lisaa suoritus"
                //Tarkistetaan onko yhtään Opiskelija luokan oliota olemassa.
                if(o.getOpiskelijat() == null){
                    paavalikko();
                    break;
                }
                System.out.print("Opiskelijan id-numero: ");
                int opiskelijaid = sc.nextInt();
                //Tarkistetaan onko kyseistä opiskelijaa olemassa.
                if(o.getOpiskelija(opiskelijaid) == null){
                    paavalikko();
                    break;
                }
                //Tarkistetaan onko yhtään Kurssi luokan oliota olemassa.
                if(k.getKurssit() == null){
                    paavalikko();
                    break;
                }
                System.out.print("Kurssin id: ");
                int kurssid = sc.nextInt();
                //Tarkistetaan onko kyseistä kurssia olemassa.
                if(k.getKurssi(kurssid) == null){
                    paavalikko();
                    break;
                }
                System.out.print("Arvosana: ");
                int arvosana = sc.nextInt();
                sc.nextLine();
                System.out.print("Suoritus pvm: ");
                String pvm = sc.nextLine();
                //Luodaan uusi suoritus jos kaikki meni putkeen.
                new Suoritus(k.getKurssi(kurssid), o.getOpiskelija(opiskelijaid), arvosana, pvm);
                System.out.println("Suoritus tallennettu.");
                paavalikko();
                break;

            case 4: //"Hae opiskelijan tietoja"
                int valinta2 = 0;
                //Varmistetaan että käyttäjä valitsee jonkun vaihtoehdoista
                while(valinta2 < 1 || valinta2 > 3) {
                    System.out.println("[1]Opiskelijan henkilokohtaiset tiedot.\n[2]Opiskelijan suoritukset.\n[3]Palaa alkuun.");
                    System.out.print("Valitse: ");
                    valinta2 = sc.nextInt();
                }
                //switch case valinnan perusteella
                switch (valinta2){
                    case 1: //"Opiskelijan henkilokohtaiset tiedot"
                        System.out.print("Opiskelijan id-numero: ");
                        int id = sc.nextInt();
                        //Tarkistetaan onko kyseistä opiskelijaa olemassa.
                        if(o.getOpiskelija(id) == null){
                            paavalikko();
                            break;
                        }
                        //Jos on tulostetaan opiskelijan henkkohtaiset tiedot.
                        else {
                            System.out.println(o.getOpiskelija(id));
                            paavalikko();
                        }
                    case 2: //"Opiskelijan suoritukset"
                        System.out.print("Opiskelijan id-numero: ");
                        id = sc.nextInt();
                        //Tarkistetaan onko kyseisellä opiskelijalla suorituksia.
                        if(o.getOpiskelijanSuoritukset(id) == null){
                            paavalikko();
                            break;
                        }
                        //Jos on, tulostetaan ne.
                        else {
                            System.out.println(o.getOpiskelijanSuoritukset(id));
                            paavalikko();
                        }
                    case 3: //"Palaa alkuun"
                        paavalikko();
                }

            case 5: //"Hae kurssin tietoja"
                int valinta3 = 0;
                //Varmistetaan että käyttäjä valitsee jonkun vaihtoehdoista
                while (valinta3 < 1 || valinta3 > 3) {
                    System.out.println("[1]Listaa kaikki kurssit.\n[2]Yksittaisen kurssin tiedot.\n[3]Palaa alkuun.");
                    System.out.print("Valitse: ");
                    valinta3 = sc.nextInt();
                }
                switch (valinta3){
                    case 1: //"Listaa kaikki kurssit"
                        //Testataan onko yhtään Kurssi oliota olemassa.
                        if(k.getKurssit() == null){
                            paavalikko();
                            break;
                        }
                        //Jos on, tulostetaan ne.
                        else {
                            for (int i = 0; i < k.getKurssiLaskuri(); i++) {
                                System.out.println(k.getKurssit().get(i));
                            }
                            paavalikko();
                            break;
                        }

                    case 2: //"Yksittaisen kurssin tiedot"
                        System.out.print("Kurssi-id: ");
                        int id = sc.nextInt();
                        //Tarkistetaan onko kyseistä kurssia olemassa.
                        if(k.getKurssi(id) == null){
                            paavalikko();
                            break;
                        }
                        int valinta4 = 0;
                        //Varmistetaan että käyttäjä valitsee jonkun vaihtoehdoista
                        while (valinta4 < 1 || valinta4 > 3) {
                            System.out.println("[1]Kurssin suoritukset.\n[2]Kurssin opiskelijat.\n[3]Palaa alkuun.");
                            System.out.print("Valitse: ");
                            valinta4 = sc.nextInt();
                        }
                        //switch case valinnan perusteella
                        switch (valinta4){

                            case 1: //"Kurssin suoritukset"
                                //Tarkistetaan onko kyseisellä kurssilla yhtään suoritusta.
                                if(k.getKurssinSuoritukset(id) == null){
                                    paavalikko();
                                    break;
                                }
                                //Jos on, tulostetaan ne.
                                else {
                                    System.out.println(k.getKurssinSuoritukset(id));
                                    paavalikko();
                                    break;
                                }

                            case 2: //"Kurssin opiskelijat"
                                //Tarkistetaan onko kurssilla yhtään opiskelijaa.
                                if(k.getKurssinOpiskelijat(id) == null){
                                    paavalikko();
                                    break;
                                }
                                //Jos on, tulostetaan ne.
                                else {
                                    System.out.println(k.getKurssinOpiskelijat(id));
                                    paavalikko();
                                    break;
                                }

                            case 3: //"Palaa alkuun"
                                paavalikko();
                                break;
                        }
                    case 3: //"Palaa alkuun"
                        paavalikko();
                }
            case 6: //"Lopeta"
                System.out.println("Lopetetaan...");
                System.exit(1);

            default: //Jos valinta on joku muu kuin annetuista vaihtoehdoista.
                paavalikko();
        }
    }
}
