import java.io.*;
import java.util.ArrayList;

/**
 * Luokka joka sisaltaa kurssin ominaisuudet
 * @author Patrik Soininen
 */
public class Kurssi implements Serializable {

    private int kurssi_id, opintopisteet;
    private String nimi, kuvaus;

    /**Staattinen Laskuri luokan olio johon tallennetaan luotujen Kurssi olioiden maara*/
    static Laskuri kurssiLaskuri = new Laskuri(0);

    /**Staattinen lista johon tallennetaan luodut kurssi oliot*/
    static ArrayList<Kurssi>kurssit = new ArrayList<Kurssi>();
    /**Lista johon tallennetaan yksittäisen kurssin suoritukset*/
    ArrayList<Suoritus> kurssisuoritukset = new ArrayList<Suoritus>();
    /**Lista johon tallennetaan yksittäisen kurssin opiskelijat*/
    ArrayList<Opiskelija> kurssiopiskelijat = new ArrayList<Opiskelija>();

    /**Parametriton konstruktori*/
    Kurssi(){
    }

    /**
     * Kontstruktori
     * @param opisteet opintopisteet
     * @param nim kurssin nimi
     * @param kuva kurssin kuvaus
     */
    Kurssi(int opisteet, String nim, String kuva) throws IOException, ClassNotFoundException {
        //Kaksi testi tiedostoa joille annetaan tiedostojen nimet joissa toisessa sijaitsee
        //Kurssi olioiden määrää mittaava laskuri, ja toisessa kaikki Kurssi oliot.
        File testi = new File("kurssiLaskuri.dat");
        File testi1 = new File("Kurssit.dat");

        // Testataan onko laskurin sisältävää tiedostoa olemassa, jos on, haetaan tiedostossa olevan laskurin sisältö
        // olemassa olevaan laskuriin ja asetetaan kurssin id-numero yhtä suuremmaksi kuin laskurin määrä.
        if(testi.exists()){
            this.kurssi_id = getKurssiLaskuri() + 1;
            kurssiLaskuri.setLukema(getKurssiLaskuri());
        }
        //Jos tiedostoa ei ole olemassa eli tämä on ensimmäinen koskaan luotu olio, kurssin id-numero
        //on yhden suurempi kuin olemassaolevan laskurin määrä eli 1.
        else{
            this.kurssi_id = kurssiLaskuri.getLukema() + 1;
        }
        //Testataan onko kurssi oliot sisältävää tiedostoa olemassa eli, jos on, tuodaan siellä olevat
        //oliot kurssi olioille tarkoitettuun listaan. Jos tiedostoa ei löydy, niin tämä on ensimmäinen
        //koskaan luotu olio.
        if(testi1.exists()) {
            kurssit = getKurssit();
        }
        this.opintopisteet = opisteet;
        this.nimi = nim;
        this.kuvaus = kuva;
        //Kasvatetaan kurssi olioiden määrää mittaavaa laskuria yhdellä ja tallennetetaan se omaan tiedostoonsa.
        kurssiLaskuri.kasvataMaaraa();
        kurssiLaskuriTallennus();
        //Lisätään olio kaikki kurssit sisältävään listaan ja tallennetaan se lista omaan tiedostoonsa.
        kurssit.add(this);
        kurssiTallennus();

    }

    /**@return kurssin id-numero*/
    public int getKurssi_id() {
        return kurssi_id;
    }

    /**@return opintopisteet*/
    public int getOpintopisteet() {
        return opintopisteet;
    }

    /**@return nimi*/
    public String getNimi() {
        return nimi;
    }

    /**@return kuvaus*/
    public String getKuvaus() {
        return kuvaus;
    }

    /**@param kurssi_id id-numero*/
    public void setKurssi_id(int kurssi_id) {
        this.kurssi_id = kurssi_id;
    }

    /**@param opintopisteet opintopisteet*/
    public void setOpintopisteet(int opintopisteet) {
        this.opintopisteet = opintopisteet;
    }

    /**@param nimi nimi*/
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**@param kuvaus kuvaus*/
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    @Override
    public String toString() {
        return "\nKurssi id: " + kurssi_id + "\nOpintopisteet: " + opintopisteet +
                "\nNimi: " + nimi + "\nKuvaus: " + kuvaus;
    }

    /**
     * Metodi Kurssin suoritusten tallentamiseen
     * @param s Suoritus luokan olio
     * */
    public void tallennaSuoritus(Suoritus s) throws IOException, ClassNotFoundException {
        //Testi tiedosto jolle annetaan nimeksi sama kuin minne kaikki yksittäisen kurssin suoritukset tallennetaan.
        File testi = new File(this.getKurssi_id() + "_KurssiSuoritukset.dat");
        //Jos yksittäisen kurssin suoritukset sisältävä tiedosto on olemassa, tuodaan sen sisältö olemassa
        //olevaan listaan.
        if(testi.exists()){
            kurssisuoritukset = getKurssinSuoritukset(this.getKurssi_id());
        }
        //Lisätään suoritus suorituslistaan.
        kurssisuoritukset.add(s);
        //Tallennetaan suorituslista omaan tiedostoonsa.
        FileOutputStream suoritusVirta = new FileOutputStream(this.getKurssi_id() + "_KurssiSuoritukset.dat");
        ObjectOutputStream suoritusOlioVirta = new ObjectOutputStream(suoritusVirta);
        suoritusOlioVirta.writeObject(kurssisuoritukset);
    }

    /**
     * Metodi jolla tallennetaan yksittaisen kurssin opiskelijat omaan tiedostoonsa
     * @param o Opiskelja luokan olio
     */
    public void tallennaOpiskelija(Opiskelija o) throws IOException, ClassNotFoundException {
        //Testi tiedosto jolle annetaan nimeksi sama kuin minne kaikki kurssin opiskelijat tallennetaan
        File testi = new File(this.getKurssi_id() + "_opiskelijat.dat");
        //Jos tiedosto on olemassa, tuodaan sen sisältämät opiskelijat olemassa olevaan listaan.
        if(testi.exists()){
            kurssiopiskelijat = getKurssinOpiskelijat(this.getKurssi_id());
        }
        //Lisätään opiskelija listaan
        kurssiopiskelijat.add(o);
        //Tallennetaan lista omaan tiedostoonsa.
        FileOutputStream opiskelijaVirta = new FileOutputStream( this.getKurssi_id() + "_opiskelijat.dat");
        ObjectOutputStream opiskelijaOlioVirta = new ObjectOutputStream(opiskelijaVirta);
        opiskelijaOlioVirta.writeObject(kurssiopiskelijat);
    }

    /**
     * Metodi jolla tallennetaan kurssit sisaltava lista omaan tiedostoonsa.
     */
    public void kurssiTallennus() throws IOException {
        FileOutputStream kurssiVirta = new FileOutputStream("Kurssit.dat");
        ObjectOutputStream kurssiOlioVirta = new ObjectOutputStream(kurssiVirta);
        kurssiOlioVirta.writeObject(kurssit);
    }

    /**
     * Metodi jolla tuodaan kaikki Kurssi oliot sisaltava tiedoston sisalto listaan, jos kursseja on olemassa
     * @return lista jossa on on kaikki luodut opiskelija oliot
     */
    public ArrayList<Kurssi> getKurssit() throws IOException, ClassNotFoundException {
        //Testi tiedosto jolle annetaan nimeksi sama kuin minne kaikki kurssit tallennetaan.
        File testi = new File("Kurssit.dat");
        //Jos sitä ei ole olemassa kerrotaan käyttäjälle että lista on tyhjä, ja palautetaan tyhjä arvo.
        if(!testi.exists()){
            System.out.println("Kurssilista on tyhja");
            return null;
        }
        //Jos tiedosto on olemassa, otetaan sen sisältö talteen listaan ja palautetaan lista.
        else {
            FileInputStream kurssiVirta = new FileInputStream("Kurssit.dat");
            ObjectInputStream kurssiOlioVirta = new ObjectInputStream(kurssiVirta);
            @SuppressWarnings({"unchecked"})
            ArrayList<Kurssi> kurssilista = (ArrayList<Kurssi>) kurssiOlioVirta.readObject();
            return kurssilista;
        }
    }

    /**
     * Metodi jolla haetaan ja palautetaan yksittainen Kurssi olio tiedostosta jos se on olemassa
     * @param kurssihakuId haettavan kurssin id-numero
     * @return haettava kurssi olio
     */
    public Kurssi getKurssi(int kurssihakuId) throws IOException, ClassNotFoundException {
        //Testi tiedosto jolle annetaan nimeksi sama kuin minne kaikki kurssit tallennetaan.
        File testi = new File("Kurssit.dat");
        //Jos sitä ei ole olemassa kerrotaan käyttäjälle että lista on tyhjä, ja palautetaan tyhjä arvo.
        if(!testi.exists()){
            System.out.println("Tiedostolista on tyhja.");
            return null;
        }
        //Jos tiedosto on olemassa tuodaan sen sisältö listaan.
        else {
            FileInputStream kurssiVirta = new FileInputStream("Kurssit.dat");
            ObjectInputStream kurssiOlioVirta = new ObjectInputStream(kurssiVirta);
            @SuppressWarnings({"unchecked"})
            ArrayList<Kurssi> kurssilista = (ArrayList<Kurssi>) kurssiOlioVirta.readObject();
            //Indeksi numero jota käytetään yksittäisen kurssin palautukseen. Se alustetaan -1 koska
            //lista alkaa 0.
            int hakuIndeksi = -1;
            //Etsitään listalta parametriksi annettua id-numeroa, jos täsmää, otetaan sen olion indeksi numero talteen
            //ja poistutaan silmukasta.
            for (int i = 0; i < kurssilista.size(); i++) {
                if (kurssilista.get(i).getKurssi_id() == kurssihakuId) {
                    hakuIndeksi = i;
                    break;
                }
            }
            //Jos hakuindeksilla on edelleen sama arvo kuin alustaessa, ilmoitetaan että kurssia ei ole listassa
            //ja palautetaan tyhjä arvo.
            if(hakuIndeksi == -1){
                System.out.println("Kurssia ei ole listalla.");
                return null;
            }
            //Jos haettava kurssi löytyi, palautetaan se.
            else {
                return kurssilista.get(hakuIndeksi);
            }
        }
    }

    /**
     * Metodi jolla haetaan yksittaisen kurssin suorituksia ja palautetaan ne jos ovat olemassa
     * @param hakuKurssi_id haettavan kurssin id-numero
     * @return lista jossa on kurssin suoritukset
     */
    public ArrayList<Suoritus> getKurssinSuoritukset(int hakuKurssi_id) throws IOException, ClassNotFoundException {
        //Testitiedosto jolle annetaan sama nimi kuin tiedostolla johon haettavan kurssin suoritukset tallennetaan.
        File testi = new File(hakuKurssi_id + "_KurssiSuoritukset.dat");
        //Jos tiedostoa ei ole olemassa, ilmoitetaan käyttäjälle ettei suorituksia ole, ja palautetaan tyhjä arvo.
        if(!testi.exists()){
            System.out.println("Kurssilla ei ole suorituksia.");
            return null;
        }
        //Jos tiedosto on olemassa, haetaan sen sisältö listaan ja palautetaan lista.
        else {
            FileInputStream opiskelijaVirta = new FileInputStream(hakuKurssi_id + "_KurssiSuoritukset.dat");
            ObjectInputStream opiskelijaOlioVirta = new ObjectInputStream(opiskelijaVirta);
            @SuppressWarnings({"unchecked"})
            ArrayList<Suoritus> haettavaSuoritus = (ArrayList<Suoritus>) opiskelijaOlioVirta.readObject();
            return haettavaSuoritus;
        }
    }

    /**
     * Metodi jolla haetaan yksittaisen kurssin opiskelijoita ja palautetaan ne jos ovat olemassa
     * @param hakuKurssi_id haettavan kurssin id-numero
     * @return lista jossa on kurssin opiskelijat
     */
    public ArrayList<Opiskelija> getKurssinOpiskelijat(int hakuKurssi_id) throws IOException, ClassNotFoundException {
        //Testitiedosto jolle annetaan sama nimi kuin tiedostolla johon haettavan kurssin opiskelijat tallennetaan.
        File testi = new File(hakuKurssi_id + "_opiskelijat.dat");
        //Jos tiedostoa ei ole olemassa, ilmoitetaan käyttäjälle ettei opiskelijoita ole, ja palautetaan tyhjä arvo.
        if(!testi.exists()){
            System.out.println("Kurssilla ei ole opiskelijoita.");
            return null;
        }
        //Jos tiedosto on olemassa, haetaan sen sisältö listaan ja palautetaan lista.
        else {
            FileInputStream opiskelijaVirta = new FileInputStream(hakuKurssi_id + "_opiskelijat.dat");
            ObjectInputStream opiskelijaOlioVirta = new ObjectInputStream(opiskelijaVirta);
            @SuppressWarnings({"unchecked"})
            ArrayList<Opiskelija> haettavatOpiskelijat = (ArrayList<Opiskelija>) opiskelijaOlioVirta.readObject();
            return haettavatOpiskelijat;
        }
    }

    /**
     * Metodi jolla tallennetaan Kurssi olioiden maaraa mittaava laskuri omaan tiedostoonsa
     */
    public void kurssiLaskuriTallennus() throws IOException {
        FileOutputStream laskuriTiedosto = new FileOutputStream("kurssiLaskuri.dat");
        ObjectOutputStream laskuriVirta = new ObjectOutputStream(laskuriTiedosto);
        laskuriVirta.writeObject(kurssiLaskuri);
    }

    /**
     * Metodi jolla palautetaan Kurssi olioiden maaraa mittaava laskuri tiedostosta johon se on tallennettu.
     * @return laskurissa oleva maara kokonaislukuna
     */
    public int getKurssiLaskuri() throws IOException, ClassNotFoundException {
        FileInputStream laskuriTiedosto = new FileInputStream("kurssiLaskuri.dat");
        ObjectInputStream laskuriVirta = new ObjectInputStream(laskuriTiedosto);
        Laskuri l = (Laskuri)laskuriVirta.readObject();
        return l.getLukema();
    }
}
