/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
/**
 *  Instance třídy PrikazLokace vrací výpis pro příkaz lokace
 *  
 * @author     Adam Spivák
 * @version    16.01.2017
 */
class PrikazLokace implements IPrikaz {
    
    private static final String NAZEV = "lokace";
    private HerniPlan hPlan;
    
    /**
     *  Konstruktor třídy
     *  
     *  @param hPlan    herní plán, ve kterém se bude ve hře "chodit"
     */    
    public PrikazLokace(HerniPlan hPlan) {
        this.hPlan = hPlan;
    }
    
    /**
     *  Metoda vrací odpověď na příkaz lokace
     *  Vypíše název aktuálního prostoru, jeho popis, východy, věci a postavy
     */
    @Override
    public String proved(String... parametry) {
        Prostor prostor = hPlan.getAktualniProstor();
        return prostor.dlouhyPopis();
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