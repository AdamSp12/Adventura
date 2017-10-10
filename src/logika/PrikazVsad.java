/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
import java.util.Random;

/*******************************************************************************
 * Instance třídy {@code PrikazVsad} vrací výpis pro příkaz vsaď
 *
 * @author    Adam Spivák
 * @version   16.01.2017
 */
public class PrikazVsad implements IPrikaz
{
    private static final String NAZEV = "vsad";
    
    private HerniPlan hPlan;

    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  
     *  @param hPlan    herní plán, ve kterém se bude ve hře "chodit"
     */
    public PrikazVsad(HerniPlan hPlan)
    {
        this.hPlan = hPlan;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda vrací odpověď na příkaz vsaď.
     * Vrací informaci o zvýšení (hráč vyhrál) nebo o snížení (hráč prohrál) stavu peněz.
     * Vrací chybovou hlášku pokud se hráč nenachází v kasinu, nejsou zadány oba parametry, parametr
     * je zadaný ve špatném tvaru, je zadána neexistující barva, hráč nemá dostatek peněz na danou sázku
     * 
     * @param   parametr1       částka
     * @param   parametr2       barva
     * @return  odpověď hráči
     */
    public String proved(String... parametry) {
        if (hPlan.getAktualniProstor() != hPlan.kasino)
        {
            return "Musíte být v kasinu, abyste mohli sázet";
        }
        if (parametry.length < 2) {
            return "Nejdříve zadejte vsazenou častku, poté barvu";
        }
        String castkaString = parametry[0];
        if(!tryParseInt(castkaString))
        {
            return "Zadejte platnou částku";
        }
        int castka = Integer.parseInt(castkaString);
        if (castka < 1)
        {
            return "Musíte vsadit kladnou částku";
        }
        if (castka > hPlan.getInventar().stavPenez())
        {
            return "Nedostatek peněz";
        }
        String barva = parametry[1];
        if (!(barva.equals("cervená") || barva.equals("cerná")))
        {
            return "Tuto barvu neznám. \n" + "Lze sázet pouze na cervenou nebo cernou barvu";
        }
        Random random = new Random();
        boolean vyhral = random.nextBoolean();
        String vypis = "";
        if (vyhral)
        {
            vypis = hPlan.getInventar().zvysStavPenez(castka);
        }
        else
        {
            vypis = hPlan.getInventar().snizStavPenez(castka);
        }
        
        return vypis;
    }
    
    /**
     * Metoda vrací zda je zadané číslo možné převést na integer
     * 
     * @param   cislo   řetězec s číslem
     * @return  true pokud je možné převést
     */
    boolean tryParseInt(String cislo) {  
     try {  
         Integer.parseInt(cislo);  
         return true;  
      } catch (NumberFormatException e) {  
         return false;  
      }  
    }
    
    public String getNazev() {
        return NAZEV;
    }
}

