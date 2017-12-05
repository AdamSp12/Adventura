/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
/*******************************************************************************
 * Instance třídy {@code PrikazVyhod} vrací výpis pro příkaz vyhoď
 *
 * @author    Adam Spivák
 * @version   16.01.2017
 */
public class PrikazVyhod implements IPrikaz
{
     //==Datové atributy (statické a instancí)============================
    private static final String NAZEV = "vyhod";
    private HerniPlan hPlan;
    private Inventar inventar;
    private Vec vec;

    //== KONSTRUKTORY A TOVÁRNÍ METODY =========================================
    /***************************************************************************
     *  Konstruktor ....
     *  
     *   @param herniPlan herní plán, ve kterém se bude ve hře "chodit" 
     */
    public PrikazVyhod(HerniPlan hPlan) {
        this.hPlan = hPlan;
        this.inventar = hPlan.getInventar();
    }

    /**
     *  Vrací výpis pro příkaz vyhoď
     *  Vyhodí zadanou věc z inventáře
     *  Použije se hlavně v případě, že v inventáři již není místo
     *  Výpíše chybovou hlášku pokud není zadán parametr, zadaná věc není v inventáři
     *
     *@param parametry  věc, kterou chceme vyhodit
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím co mám vyhodit";
        }
        String jmenoVeci = parametry[0];
        if(inventar.getObsahInventare().containsKey(jmenoVeci))
        {
            vec = inventar.getVec(jmenoVeci);
            inventar.odeberZInventare(jmenoVeci);
            hPlan.getAktualniProstor().vlozVec(vec);
            updateHerniPlan();
            return "Věc '" + jmenoVeci + "' byla odebrána z inventáře a zůstala v aktuálním prostoru";
        }
        return "Tato věc se nenachází v inventáři";
        }
        @Override
    public String getNazev() {
        return NAZEV;
    }
    @Override
    public void updateHerniPlan() {
        hPlan.notifyAllObservers();
    }
}
