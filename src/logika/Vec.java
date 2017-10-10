/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



/*******************************************************************************
 * Instance třídy Vec představují věci, které lze ve hře používat ...
 *
 * @author    Adam Spivak
 * @version   03.01.2017
 */
public class Vec
{
    //== Datové atributy (statické i instancí)======================================
    private String nazev;
    private String popis;
    private boolean jePrenositelna;
    private boolean bylaProhledana;    
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  
     *  @param nazev            název věci
     *  @param popis            popis věci (vypíše se příkazem prozkoumej)
     *  @param jePrenositelna   uchovává informaci o tom, zda se dá věc dát do inventáře 
     */
    public Vec(String nazev, String popis, boolean jePrenositelna)
    {
        this.nazev = nazev;
        this.popis = popis;
        this.jePrenositelna = jePrenositelna;
    }
    
    /***************************************************************************
     *  Konstruktor ....
     *  
     *  @param nazev            název věci
     *  @param popis            popis věci (vypíše se příkazem prozkoumej)
     *  @param jePrenositelna   uchovává informaci o tom, zda se dá věc dát do inventáře
     *  @param bylaProhledana   u předmětů, u kterých je významné zda již byly prohledány, se uchovává tato informace
     */
    public Vec(String nazev, String popis, boolean jePrenositelna, boolean bylaProhledana)
    {
        this.nazev = nazev;
        this.popis = popis;
        this.jePrenositelna = jePrenositelna;
        this.bylaProhledana = bylaProhledana;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda vrací název věci.
     * 
     * @return název věci
     */
    public String getNazev() {
        return nazev;
    }
    
    /**
     * Metoda vrací popis věci.
     * 
     * @return popis věci
     */
    public String getPopis() {
        return popis;
    }
    /**
     * Metoda nastaví popis věci
     * 
     * @param popis věci
     */
    public void setPopis(String popis)
    {
        this.popis = popis;
    }
    
    /**
     * Metoda vrací přenositelnost věci.
     * 
     * @return hodnota přenositelnosti
     */
    public boolean jePrenositelna() {
        return jePrenositelna;
    }
    
    /**
     * Metoda vrací informaci o tom, zda byla věc již prohledána
     * 
     * @return true pokud byla prohledána
     */
    public boolean bylaProhledana()
    {
        return bylaProhledana;
    }
    
    /**
     * Metoda nastaví, že věc již byla prohledána
     * 
     * @param true pokud byla prohledána
     */
    public void setProhledana(boolean hodnota)
    {
        bylaProhledana = hodnota;
    }

    //== Soukromé metody (instancí i třídy) ========================================

}
