import java.io.*;
import java.util.ArrayList;

/**
 * Luokka joka sisaltaa opiskelijan ominaisuudet.
 * @author Patrik Soininen
 */
public class Opiskelija implements Serializable {

    private int opiskelija_id;
    private String etunimi, sukunimi, lahiosoite, pstpaikka, postinumero, email, puhnumero;

    /**Lista johon tallennetaan Opiskelija luokan olioiden suoritukset*/
    ArrayList<Suoritus> suoritukset = new ArrayList<Suoritus>();

    /**Staattinen lista johon tallennetaan luodut Opiskelija luokan oliot*/
    static ArrayList<Opiskelija> opiskelijat = new ArrayList<Opiskelija>();

    /**Staattinen Laskuri luokan olio johon tallennetaan luotujen Opiskelija luokan olioiden maara*/
    static Laskuri opiskelijaLaskuri = new Laskuri(0);

    /**Parametriton konstruktori*/
    Opiskelija(){
    }

    /**
     * Konstruktori
     * @param enimi etunimi
     * @param snimi sukunimi
     * @param lhiosoite lahiosoite
     * @param paikka postitoimipaikka
     * @param pstnumero postinumero
     * @param m_email sahkopostiosoite
     * @param pnumero puhelinnumero
     */
    Opiskelija(String enimi, String snimi, String lhiosoite, String paikka, String pstnumero, String m_email, String pnumero) throws IOException, ClassNotFoundException {

        //Kaksi testi tiedostoa joille annetaan tiedostojen nimet joissa toisessa sijaitsee
        //Opiskelija olioiden määrää mittaava laskuri, ja toisessa kaikki Opiskelija oliot.
        File testi = new File("OpiskelijaLaskuri.dat");
        File testi1 = new File("Opiskelijat.dat");

        // Testataan onko laskurin sisältävää tiedostoa olemassa, jos on, haetaan tiedostossa olevan laskurin sisältö
        // olemassa olevaan laskuriin ja asetetaan opiskelijan id-numero yhden suuremmaksi kuin laskurin määrä.
        if(testi.exists()){
            this.opiskelija_id = getOpiskelijaLaskuri() + 1;
            opiskelijaLaskuri.setLukema(getOpiskelijaLaskuri());
        }
        //Jos tiedostoa ei ole olemassa eli tämä on ensimmäinen koskaan luotu olio, opiskelijan id-numero
        //on yhden suurempi kuin olemassaolevan laskurin määrä eli 1.
        else{
            this.opiskelija_id = opiskelijaLaskuri.getLukema() + 1;
        }
        //Testataan onko opiskelija oliot sisältävää tiedostoa olemassa eli, jos on, tuodaan siellä olevat
        //oliot opiskelija olioille tarkoitettuun listaan. Jos tiedostoa ei löydy, niin tämä on ensimmäinen
        //koskaan luotu olio.
        if(testi1.exists()) {
            opiskelijat = getOpiskelijat();
        }
        this.etunimi = enimi;
        this.sukunimi = snimi;
        this.lahiosoite = lhiosoite;
        this.pstpaikka = paikka;
        this.postinumero = pstnumero;
        this.email = m_email;
        this.puhnumero = pnumero;
        //Lisätään olio kaikki opiskelijat sisältävään listaan.
        opiskelijat.add(this);
        //Kasvatetaan opiskelija olioiden määrää mittaavaa laskuria yhdellä.
        opiskelijaLaskuri.kasvataMaaraa();
        //Tallennetaan sekä laskuri että opiskelijalista omiin tiedostoihinsa.
        opiskelijaLaskuriTallennus();
        opiskelijaTallennus();
    }

    /**@return opiskelija_id*/
    public int getOpiskelija_id() {
        return opiskelija_id;
    }

    /**@return  etunimi*/
    public String getEtunimi() {
        return etunimi;
    }

    /**@return sukunimi*/
    public String getSukunimi() {
        return sukunimi;
    }

    /**@return lahiosoite*/
    public String getLahiosoite() {
        return lahiosoite;
    }

    /**@return pstpaikka postitoimipaikka*/
    public String getPstpaikka() {
        return pstpaikka;
    }

    /**@return postinumero*/
    public String getPostinumero() {
        return postinumero;
    }

    /**@return email sahkopostiosoite*/
    public String getEmail() {
        return email;
    }

    /**@return puhnumero puhelinnumero*/
    public String getPuhnumero() {
        return puhnumero;
    }

    /**@param opiskelija_id id-numero*/
    public void setOpiskelija_id(int opiskelija_id) {
        this.opiskelija_id = opiskelija_id;
    }

    /**@param etunimi etunimi*/
    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    /**@param sukunimi sukunimi*/
    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    /**@param lahiosoite lahiosoite*/
    public void setLahiosoite(String lahiosoite) {
        this.lahiosoite = lahiosoite;
    }

    /**@param pstpaikka postitoimipaikka*/
    public void setPstpaikka(String pstpaikka) {
        this.pstpaikka = pstpaikka;
    }

    /**@param postinumero postinumero*/
    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
    }

    /**@param email sahkopostiosoite*/
    public void setEmail(String email) {
        this.email = email;
    }

    /**@param puhnumero puhelinnumero*/
    public void setPuhnumero(String puhnumero) {
        this.puhnumero = puhnumero;
    }

    @Override
    public String toString() {
        return "\nOpiskelija id: " + opiskelija_id + "\nEtunimi: " + etunimi +
                "\nSukunimi: " + sukunimi + "\nLahiosoite: " + lahiosoite +
                "\nPostitoimipaikka: " + pstpaikka + "\nPostinumero: " + postinumero +
                "\nEmail: " + email + "\nPuhnumero: " + puhnumero;
    }

    /**
     * Metodi opiskelijoiden suorittamien suoritusten tallentamiseen
     * @param s Suoritus luokan olio
     * */
    public void tallennaSuoritus(Suoritus s) throws IOException, ClassNotFoundException {
        //Testi tiedosto jolle annetaan nimeksi sama kuin minne kaikki yksittäisen opiskelijan suoritukset tallennetaan.
        File testi = new File(s.getOpiskelijaId() + "_Suoritukset.dat");
        //Jos yksittäisen opiskelijan suoritukset sisältävä tiedosto on olemassa, tuodaan sen sisältö olemassa
        //olevaan listaan.
        if(testi.exists()){
            suoritukset = getOpiskelijanSuoritukset(s.getOpiskelijaId());
        }
        //Lisätään suoritus suorituslistalle.
        suoritukset.add(s);
        //Tallennetaan suorituslista omaan tiedostoonsa.
        FileOutputStream suoritusVirta = new FileOutputStream(this.getOpiskelija_id() + "_Suoritukset.dat");
        ObjectOutputStream suoritusOlioVirta = new ObjectOutputStream(suoritusVirta);
        suoritusOlioVirta.writeObject(suoritukset);
    }

    /**
     * Metodi jolla tallennetaan opiskelijat sisaltava lista omaan tiedostoonsa.
     */
    public void opiskelijaTallennus() throws IOException {
        FileOutputStream opiskelijaVirta = new FileOutputStream("Opiskelijat.dat");
        ObjectOutputStream opiskelijaOlioVirta = new ObjectOutputStream(opiskelijaVirta);
        opiskelijaOlioVirta.writeObject(opiskelijat);
    }

    /**
     * Metodi jolla tuodaan kaikki Opiskelija oliot sisaltava tiedoston sisalto listaan, jos opiskelijoita on olemassa
     * @return lista jossa on on kaikki luodut opiskelija oliot
     */
    public ArrayList<Opiskelija> getOpiskelijat() throws IOException, ClassNotFoundException {
        //Testi tiedosto jolle annetaan nimeksi sama kuin minne kaikki opiskelijat tallennetaan
        File testi = new File("Opiskelijat.dat");
        //Jos sitä ei ole olemassa kerrotaan käyttäjälle että lista on tyhjä, ja palautetaan tyhjä arvo.
        if(!testi.exists()){
            System.out.println("Opiskelijalista on tyhja.");
            return null;
        }
        //Jos tiedosto on olemassa, otetaan sen sisältö talteen listaan ja palautetaan lista.
        else {
            FileInputStream opiskelijaVirta = new FileInputStream("Opiskelijat.dat");
            ObjectInputStream opiskelijaOlioVirta = new ObjectInputStream(opiskelijaVirta);
            @SuppressWarnings({"unchecked"})
            ArrayList<Opiskelija> opiskelijalista = (ArrayList<Opiskelija>) opiskelijaOlioVirta.readObject();
            return opiskelijalista;
        }
    }

    /**
     * Metodi jolla haetaan ja palautetaan yksittainen opiskelija olio tiedostosta jos se on olemassa
     * @param hakuOpiskelija_id haettavan opiskelijan id-numero
     * @return haettava opiskelija olio
     */
    public Opiskelija getOpiskelija(int hakuOpiskelija_id) throws IOException, ClassNotFoundException {
        //Testi tiedosto jolle annetaan nimeksi sama kuin minne kaikki opiskelijat tallennetaan
        File testi = new File("Opiskelijat.dat");
        //Jos sitä ei ole olemassa kerrotaan käyttäjälle että lista on tyhjä, ja palautetaan tyhjä arvo.
        if(!testi.exists()){
            System.out.println("Opiskelijalista on tyhja.");
            return null;
        }
        //Jos tiedosto on olemassa tuodaan sen sisältö listaan.
        else {
            FileInputStream opiskelijaVirta = new FileInputStream("Opiskelijat.dat");
            ObjectInputStream opiskelijaOlioVirta = new ObjectInputStream(opiskelijaVirta);
            @SuppressWarnings({"unchecked"})
            ArrayList<Opiskelija> haettavaOpiskelija = (ArrayList<Opiskelija>) opiskelijaOlioVirta.readObject();
            //Indeksi numero jota käytetään yksittäisen opiskelijan palautukseen. Se alustetaan -1 koska
            //lista alkaa 0.
            int hakuIndeksi = -1;
            //Etsitään listalta parametriksi annettua id-numeroa, jos täsmää, otetaan sen olion indeksi numero talteen
            //ja poistutaan silmukasta.
            for (int i = 0; i < haettavaOpiskelija.size(); i++) {
                if (haettavaOpiskelija.get(i).opiskelija_id == hakuOpiskelija_id) {
                    hakuIndeksi = i;
                    break;
                }
            }
            //Jos hakuindeksilla on edelleen sama arvo kuin alustaessa, ilmoitetaan että opiskelijaa ei ole listassa
            //ja palautetaan tyhjä arvo.
            if(hakuIndeksi == -1){
                System.out.println("Opiskelijaa ei ole listalla.");
                return null;
            }
            //Jos haettava opiskelija löytyi, palautetaan se.
            else {
                return haettavaOpiskelija.get(hakuIndeksi);
            }
        }
    }

    /**
     * Metodi jolla haetaan yksittaisen opiskelijan suorituksia ja palautetaan ne jos ovat olemassa
     * @param hakuOpiskelija_id haettavan opiskelija id-numero
     * @return lista jossa on opiskelijan suorittamat suoritukset
     */
    public ArrayList<Suoritus> getOpiskelijanSuoritukset(int hakuOpiskelija_id) throws IOException, ClassNotFoundException {
        //Testitiedosto jolle annetaan sama nimi kuin tiedostolla johon haettavana opiskelijan suoritukset tallennetaan.
        File testi = new File(hakuOpiskelija_id + "_Suoritukset.dat");
        //Jos tiedostoa ei ole olemassa, ilmoitetaan käyttäjälle ettei suorituksia ole, ja palautetaan tyhjä arvo.
        if(!testi.exists()){
            System.out.println("Opiskelijalla ei ole suorituksia.");
            return null;
        }
        //Jos tiedosto on olemassa, haetaan sen sisältö listaan ja palautetaan lista.
        else {
            FileInputStream suoritusVirta = new FileInputStream(hakuOpiskelija_id + "_Suoritukset.dat");
            ObjectInputStream suoritusOlioVirta = new ObjectInputStream(suoritusVirta);
            @SuppressWarnings({"unchecked"})
            ArrayList<Suoritus> haettavaSuoritus = (ArrayList<Suoritus>) suoritusOlioVirta.readObject();
            return haettavaSuoritus;
        }
    }

    /**
     * Metodi jolla tallennetaan opiskelija olioiden maaraa mittaava laskuri omaan tiedostoonsa
     */
    public void opiskelijaLaskuriTallennus() throws IOException {
        FileOutputStream laskuriVirta = new FileOutputStream("OpiskelijaLaskuri.dat");
        ObjectOutputStream laskuriOlioVirta = new ObjectOutputStream(laskuriVirta);
        laskuriOlioVirta.writeObject(opiskelijaLaskuri);
    }

    /**
     * Metodi jolla palautetaan opiskelija olioiden maaraa mittaava laskuri tiedostosta johon se on tallennettu.
     * @return laskurissa oleva maara kokonaislukuna
     */
    public int getOpiskelijaLaskuri() throws IOException, ClassNotFoundException {
        FileInputStream laskuriVirta = new FileInputStream("OpiskelijaLaskuri.dat");
        ObjectInputStream laskuriOlioVirta = new ObjectInputStream(laskuriVirta);
        Laskuri l = (Laskuri)laskuriOlioVirta.readObject();
        return l.getLukema();
    }
}
