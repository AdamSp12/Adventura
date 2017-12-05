/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Instance třídy {@code PrikazSeber} vrací výpis pro příkaz seber
 *
 * @author    Adam Spivák
 * @version   15.01.2017
 */
public class PrikazSeber implements IPrikaz
{
     //==Datové atributy (statické a instancí)============================
    private static final String NAZEV = "seber";
    private HerniPlan hPlan;
    private Inventar inventar;

    //== KONSTRUKTORY A TOVÁRNÍ METODY =========================================
    /***************************************************************************
     *  Konstruktor ....
     *  
     *   @param hPlan   herní plán, ve kterém se bude ve hře "chodit" 
     */
    public PrikazSeber(HerniPlan hPlan) {
        this.hPlan = hPlan;
        this.inventar = hPlan.getInventar();
    }

    /**
     *  Metoda sbírá věci, které se nacházejí v prostoru a které jdou sebrat, 
     *  vkládá je potom do invenáře
     *  Chybové hlášení se vypíše pokud není zadán parametr, inventář již danou věc obsahuje
     *  v invenáři není místo, pokud daná věc není přenositelná, pokud nemáme dostatek peněz
     *
     *@param parametr   název věci
     *@return výpis pro příkaz seber
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím co mám sebrat";
        }

        String jmenoVeci = parametry[0];
        Prostor prostor  = hPlan.getAktualniProstor();
        Vec vec;
        String vypis = "";
        if(!prostor.obsahujeVec(jmenoVeci))
                {
                    return jmenoVeci + " se zde nenachází";
                }        

        if(inventar.getObsahInventare().containsKey(jmenoVeci))
        {
            return "Vícekrát tuto věc v inventáři mít nemůžeš";
        }
        if(jmenoVeci.equals("alkohol"))
        {
            if (hPlan.getInventar().stavPenez() >= 200 && hPlan.getInventar().jeMisto())
            {
            vec = prostor.getVec(jmenoVeci);
            vypis = inventar.snizStavPenez(200);
        }
        else
        {
            vec = null;
            vypis = "Nemáš dostatek peněz nebo místo v inventáři.";
        }
        }
        else
        {
            
            if(hPlan.getInventar().jeMisto())
            {
                vec = prostor.odeberVec(jmenoVeci);
            }
            else
            {
                return "V inventáři není místo";
            }
        }
        if(vec == null)
        {
            return jmenoVeci + " se nedá sebrat \n" + vypis;
        }
        
        return
        inventar.dejDoInventare(vec) + update() + vypis;
    
        

        }
        @Override
    public String getNazev() {
        return NAZEV;
    }
    @Override
    public void updateHerniPlan() {
        hPlan.notifyAllObservers();
    }
    
    public String update()
            {
             hPlan.notifyAllObservers();
             return "";
            }
}

 
