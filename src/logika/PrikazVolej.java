/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
/*******************************************************************************
 * Instance třídy {@code prikazVolej} vrací výpis pro příkaz volej
 *
 * @author    Adam Spivák
 * @version   16.01.2017
 */
public class PrikazVolej implements IPrikaz
{
   private static final String NAZEV = "volej";
    private HerniPlan hPlan;
    private Hra hra;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param hPlan herní plán, ve kterém se bude ve hře "chodit"
    *  @param hra hra, která se zrovna hraje
    */    
    public PrikazVolej(HerniPlan hPlan, Hra hra) {
        this.hPlan = hPlan;
        this.hra = hra;
    }

    /**
     *  Metoda vrací výpis pro příkaz volej
     *  Vratí chybovou hlášku v případě, že je parametr prázdný, postava u sebe nemá mobil, zadané číslo neexistuje
     *  
     *  @param parametry volané číslo
     */ 
    @Override
    public String proved(String... parametry) {
        if (!hPlan.getInventar().obsahujeVec("mobil"))
        {
            return "Nemáš u sebe mobil";
        }
        if (parametry.length == 0) {
            return "Nezadal jsi číslo";
        }
        
        String vypis = "";
        String volaneCislo = parametry[0];
        switch(volaneCislo)
        {
            case "608748222":
            hPlan.park.vlozPostavu(hPlan.kamarad);
            vypis = "Ahoj, myslím, že bych ti mohl alespoň trochu pomoct, sejdeme se v parku, \n"
            + "budu tam na tebe čekat.";
            break;
            case "158":
            vypis = "Na policii jsi ohlásil, že jsi vydírán, pachatel byl dopaden. Vyhrál jsi!";
            hra.setKonecHry(true);
            break;
            default: vypis = "Zadané číslo neexistuje";
        }
        return vypis;
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
}
