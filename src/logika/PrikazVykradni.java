/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
/*******************************************************************************
 * Instance třídy {@code PrikazVyhrad} vrací výpis pro příkaz vykradni
 *
 * @author    Adam Spivák
 * @version   16.01.2017
 */
public class PrikazVykradni implements IPrikaz
{
     //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "vykradni";
    
    private HerniPlan hPlan;
    private Hra hra;
    private boolean vykradlObchod = false;

    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  
     *  @param hPlan    herní plán, ve kterém se bude ve hře "chodit"
     *  @param hra  hra, ve které hráč právě hraje
     */
    public PrikazVykradni(HerniPlan hPlan, Hra hra)
    {
        this.hPlan = hPlan;
        this.hra = hra;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     *  Metoda vrací výpis pro příkaz vykradni
     *  pokusí se vykrást daný prostor, pokus může skončit úspěšně nebo prohrou,
     *  záleží na vykrádaném prostoru.
     *  Metoda vypíše chybovou hlášku pokud je parametr prázdný, hráč se nenachází v daném prostoru,
     *  daný prostor nelze vykrást.
     *
     *@param parametr   název věci
     *@return výpis pro příkaz seber
     */ 
    public String proved(String... parametry) {
        String vypis = "";
        if (parametry.length < 1) {
            return "nevím, jaký prostor se mám pokusit vykrást";
        }
        String nazevProstoru = parametry[0];
        if (!nazevProstoru.equals(hPlan.getAktualniProstor().getNazev()))
        {
            return "Zadaný prostor nemůžeš vykrást, \n" + "protože se v něm nenacházíš";
        }
        
        switch(nazevProstoru)
        {
            case "obchod":
            if (vykradlObchod)
            {
                vypis = "Podruhé se ti to už nepovedlo \n" +
                "Prodavač byl připravený a zavolal policii \n" +
                "Prohrál jsi!";
                hra.setKonecHry(true);
            }
            else
            {
                vykradlObchod = true;
                vypis = "Povedlo se ti vykrást obchod...\n" +
                "Jen tak tak jsi utekl domů \n" + 
                hPlan.getInventar().zvysStavPenez(23000);
                hPlan.setAktualniProstor(hPlan.doma);
            }
            break;
            case "kasino":
            vypis = "Byl jsi dopaden \n" + 
            "Prohrál jsi!";
            hra.setKonecHry(true);
            break;
            case "bazar":
            vypis = "Byl jsi dopaden \n" + 
            "Prohrál jsi!";
            hra.setKonecHry(true);
            break;
            default:
            vypis = "Tuto lokaci nelze vykrást";
        }
        return vypis;
    }
    
    public String getNazev() {
        return NAZEV;
    }
    
}
