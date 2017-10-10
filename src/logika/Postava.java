/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Instance třídy {@code Postava} představují postavy, se kterými lze ve hře něco dělat
 *
 * @author    Adam Spivak
 * @version   15.01.2017
 */
public class Postava
{
    private String jmeno;
    private String rec;
    /***************************************************************************
     *  Konstruktor ....
     *  
     *  @param jmeno    jméno postavy
     *  @param rec      co postava odpoví, při použití příkazu mluv
     */
    public Postava(String jmeno, String rec)
    {
        this.jmeno = jmeno;
        this.rec = rec;
    }
    /**
     * Metoda vrátí jméno postavy.
     * 
     * @return  jméno postavy
     */
    public String getJmeno()
    {
        return jmeno;
    }
    /**
     * Metoda vrátí řetězec, co postava odpoví
     * 
     * @return  odpověď postavy
     */
    public String getRec()
    {
        return rec;
    }
    /**
     * Metoda nastaví odpověď postavy
     * 
     * @param rec   odpověď postavy
     */
    public void setRec(String rec)
    {
        this.rec = rec;
    }
}
