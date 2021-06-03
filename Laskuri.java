import java.io.Serializable;

/**
 * Luokka jonka olioita voidaan kayttaa laskurina ja tallentaa tiedostoihin
 * @author Patrik Soininen
 */
public class Laskuri implements Serializable {

    private int lukema;

    /**
     * Konstruktori
     * @param lukema laskurin lukema
     */
    Laskuri(int lukema){
        this.lukema = lukema;
    }

    /**@return lukema*/
    public int getLukema() {
        return lukema;
    }

    /**@param lukema laskurin lukema*/
    public void setLukema(int lukema) {
        this.lukema = lukema;
    }

    /**Kasvattaa lukemaa yhdella*/
    public void kasvataMaaraa() {
        this.lukema++;
    }

    /**Laskee lukemaa yhdella*/
    public void laskeMaaraa(){
        this.lukema--;
    }
}
