import java.io.IOException;
import java.io.Serializable;

/**
 * Luokka joka sisaltaa opiskelu suorituksen ominaisuudet
 * @author Patrik Soininen
 */
public class Suoritus implements Serializable {

    private int opiskelija_id, kurssi_id, arvosana;
    private String suoritus_pvm;

    /**
     * Konstruktori
     * @param k Kurssi luokan olio
     * @param o Opiskelija luokan olio
     * @param m_arvosana suorituksen arvosana
     * @param m_suoritus_pvm suorituksen pvm
     */
    Suoritus(Kurssi k, Opiskelija o, int m_arvosana, String m_suoritus_pvm) throws IOException, ClassNotFoundException {
        this.opiskelija_id = o.getOpiskelija_id();
        this.kurssi_id = k.getKurssi_id();
        this.arvosana = m_arvosana;
        this.suoritus_pvm = m_suoritus_pvm;
        //Tallennetaan suoritus Opiskelija oliolle ja Kurssi oliolle sekä tallennetaan Opiskelija olio Kurssi olion
        //opiskelija listaan.
        o.tallennaSuoritus(this);
        k.tallennaSuoritus(this);
        k.tallennaOpiskelija(o);
    }

    /**@return opiskelijan id-numero*/
    public int getOpiskelijaId() {
        return opiskelija_id;
    }

    /**@return kurssin id-numero */
    public int getKurssiId() {
        return kurssi_id;
    }

    /**@return arvosana*/
    public int getArvosana() {
        return arvosana;
    }

    /**@return suoritus pvm*/
    public String getSuoritus_pvm() {
        return suoritus_pvm;
    }

    /**@param o Opiskelija luokan olio*/
    public void setOpiskelija_id(Opiskelija o) {
        this.opiskelija_id = o.getOpiskelija_id();
    }

    /**@param k Kurssi luokan olio*/
    public void setKurssi_id(Kurssi k) {
        this.kurssi_id = k.getKurssi_id();
    }

    /**@param arvosana arvosana*/
    public void setArvosana(int arvosana) {
        this.arvosana = arvosana;
    }

    /**@param suoritus_pvm suoritus paivamaara*/
    public void setSuoritus_pvm(String suoritus_pvm) {
        this.suoritus_pvm = suoritus_pvm;
    }

    @Override
    public String toString() {
        return "\nOpiskelija id: " + opiskelija_id + "\nKurssi id: " + kurssi_id + "\nArvosana: " +
                arvosana + "\nSuoritus pvm: " + suoritus_pvm;
    }
}
