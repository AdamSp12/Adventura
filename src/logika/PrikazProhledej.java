/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



/*******************************************************************************
 * Instance třídy PrikazProhledej vytváří odpovědi pro příkaz Prohledej
 *
 * @author    Adam Spivák
 * @version   13.01.2017
 * 
 */
public class PrikazProhledej implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "prohledej";
    private HerniPlan hPlan;

    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  
     *  @param hPlan    herní plán, ve kterém se bude ve hře "chodit"
     */
    public PrikazProhledej(HerniPlan hPlan)
    {
        this.hPlan = hPlan;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda vrací odpověď na příkaz prohledej.
     * Klasicky vrací informaci o věci, může ale i přidávat další věci do prostoru
     * např. při prohledání věci je nalezena nová věc, kterou je možné poté sebrat
     * Vypíše chybové hlášení v případě, že není zadán parametr, věc neobsahuje prostor ani inventář,
     * věc, ve které byla ukryta další věc, byla již prohledána
     * 
     * @param   parametry       název věci
     * @return  odpověď hráči
     */
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "nevím, co mám prohledat";
        }
        String nazevVeci = parametry[0];
        Vec vec = null;
        
        if ((!hPlan.getAktualniProstor().obsahujeVec(nazevVeci)) &&
            (!hPlan.getInventar().obsahujeVec(nazevVeci)))  {
            return "věc '" + nazevVeci + "' tu není";
        }
        
        if (!hPlan.getInventar().obsahujeVec(nazevVeci))
        {
            vec = hPlan.getAktualniProstor().getVec(nazevVeci);
        }
        else
        {
            vec = hPlan.getInventar().getVec(nazevVeci);
        }
        Prostor prostor = hPlan.getAktualniProstor();
        
        String vypis = vec.getPopis();
        
        switch(nazevVeci)
        {
            case "postel":  
            if (!prostor.getVec("postel").bylaProhledana())
            {
            prostor.vlozVec(hPlan.papir);
            prostor.getVec("postel").setPopis("Nic dalšího jsi zde nenašel");
            prostor.getVec("postel").setProhledana(true);
        }
            break;
            case "stul":
            if (!prostor.getVec("stul").bylaProhledana())
            {
            prostor.vlozVec(hPlan.mobil);
            prostor.vlozVec(hPlan.cigarety);
            vypis +=  "\n" + hPlan.getInventar().zvysStavPenez(2000);
            prostor.getVec("stul").setPopis("Na stole jsi nic dalšího nenašel");
            prostor.getVec("stul").setProhledana(true);
        }
            break;
            case "popelnice":
            if (!prostor.getVec("popelnice").bylaProhledana())
            {
            prostor.vlozVec(hPlan.obraz);
            prostor.getVec("popelnice").setPopis("V popelnici jsi kromě obrazu již nic dalšího nenašel");
            prostor.getVec("popelnice").setProhledana(true);
        }
            break;
            
        }
        updateHerniPlan();
        return vypis;
    }
    
    public String getNazev() {
        return NAZEV;
    }
    @Override
    public void updateHerniPlan() {
        hPlan.notifyAllObservers();
    }

    //== Soukromé metody (instancí i třídy) ========================================

}
