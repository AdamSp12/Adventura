/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Instance třídy {@code PrikazInventar} vrací výpis pro příkaz inventář
 *
 * @author    Adam Spivák
 * @version   11.01.2017
 */
public class PrikazInventar implements IPrikaz
{
    private static final String NAZEV = "inventar";
    private HerniPlan hPlan;

    //== KONSTRUKTORY A TOVÁRNÍ METODY =========================================
    /***************************************************************************
     * Konsturktor
     *@param hPlan  herní plán, ve kterém se bude ve hře "chodit"
     */
    public PrikazInventar (HerniPlan hPlan) {
        this.hPlan = hPlan;
    }
    
    //==Nesoukromé metody==========================================
    /**
     *  Metoda vypíše všechny věci, které jsou v inventáři a stav peněz.
     *
     *  @return     výpis pro příkaz inventář
     */ 
    
    @Override
    public String proved(String... parametry) {
        return
        "Aktuální stav peněz: " + hPlan.getInventar().stavPenez() + "\n" +
        hPlan.getInventar().getSeznam();
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
    @Override
    public void updateHerniPlan() {
        hPlan.notifyAllObservers();
    }
}
