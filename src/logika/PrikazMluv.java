/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Instance třídy {@code PrikazMluv} vrací výpis pro příkaz mluv
 *
 * @author    Adam Spivák
 * @version   15.01.2017
 */
public class PrikazMluv implements IPrikaz
{
    private static final String NAZEV = "mluv";
    
    private HerniPlan hPlan;

    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  
     *  @param hPlan    herní plán, ve kterém se bude ve hře "chodit" 
     */
    public PrikazMluv(HerniPlan hPlan)
    {
        this.hPlan = hPlan;
    }
    /**
     *  Metoda vypíše odpověď dané postavy.
     *  V jednom případě po příkazu mluv, vloží věc do prostoru a přidá peníze
     *  Vypíše chybové hlášení když není zadán parametr, prostor neobsahuje postavu
     *
     *  @param  parametr    jméno postavy
     *  @return     výpis pro příkaz inventář
     */ 
    //== Nesoukromé metody (instancí i třídy) ======================================
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím, s kým mám mluvit";
        }
        String nazevPostavy = parametry[0];
        Prostor prostor = hPlan.getAktualniProstor();
        if (!prostor.obsahujePostavu(nazevPostavy))  {
            return "Postava '" + nazevPostavy + "' tu není";
        }
        Postava postava = prostor.getPostava(nazevPostavy);
        if(nazevPostavy.equals("kamarad"))
        {
            prostor.vlozVec(hPlan.hodinky);
            prostor.odeberPostavu("kamarad");
            return postava.getRec() + "\n" + hPlan.getInventar().zvysStavPenez(5000);
        }
        return postava.getRec();
    }
    
    public String getNazev() {
        return NAZEV;
    }
}
