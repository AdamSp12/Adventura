/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
import java.util.Set;
import java.util.HashSet;
/*******************************************************************************
 * Instance třídy {@code PrikazDej} vrací výpis pro příkaz dej
 *
 * @author    Adam Spivák
 * @version   16.01.2017
 */
public class PrikazDej implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "dej";
    
    private HerniPlan hPlan;
    private Set<String> bezdomovecDostal;
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  
     *  @param hPlan    herní plán, ve kterém se bude ve hře "chodit"
     *  @param bezdomovecDostal kolekce, uchovává seznam věcí, které dostal bezdomovec
     */
    public PrikazDej(HerniPlan hPlan)
    {
        this.hPlan = hPlan;
        bezdomovecDostal = new HashSet<>();
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda vrací odpověď na příkaz dej.
     * Vrací výpis reakce postavy na dání předmětu, postava ho může a nemusí přijmout
     * Metoda vrací chybovou hlášku pokud nejsou zadané dva parametry, postava se nenacházi v prostoru,
     * dávaná věc se nenachází v inventáři.
     * 
     * @param   parametr1       jméno postavy
     * @param   parametr2       název věci
     * @return  odpověď hráči
     */
    public String proved(String... parametry) {
        if (parametry.length < 2) {
            return "nevím, co mám komu dát";
        }
        
        Prostor prostor = hPlan.getAktualniProstor();
        String jmenoPostavy = parametry[0];
        String nazevVeci = parametry[1];
        String vypis = "";
        if (!prostor.obsahujePostavu(jmenoPostavy))
        {
            return "postava '" + jmenoPostavy + "' tu není"; 
        }
        if (!hPlan.getInventar().obsahujeVec(nazevVeci))  {
            return "věc '" + nazevVeci + "' nemáš u sebe";
        }
        if (jmenoPostavy.equals("bezdomovec"))
        {
            switch(nazevVeci)
            {
                case "cigarety":
                if (!bezdomovecDostal.contains("cigarety"))
                {
                    getBezdomovecDostal().add("cigarety");
                    hPlan.getInventar().odeberZInventare("cigarety");
                    prostor.getPostava("bezdomovec").setRec("Nebylo by trochu alkoholu?");
                    hPlan.vyberProstor("obchod").setZna(true);
                    hPlan.park.setVychod(hPlan.obchod);
                    hPlan.doma.setVychod(hPlan.obchod);
                    hPlan.kasino.setVychod(hPlan.obchod);
                    updateHerniPlan();
                    vypis = "Díky moc chlape, za to ti řeknu, kde můžeš najít super obchod";
                }
                else
                {
                    vypis = "Seš hodnej ale cigaret mám ještě dost \n"
                    + prostor.getPostava("bezdomovec").getRec();
                }
                break;
                case "alkohol":
                if (!bezdomovecDostal.contains("alkohol"))
                {
                    getBezdomovecDostal().add("alkohol");
                    hPlan.getInventar().odeberZInventare("alkohol");
                    prostor.getPostava("bezdomovec").setRec("Zzzzzz");
                    hPlan.vyberProstor("bazar").setZna(true);
                    hPlan.park.setVychod(hPlan.bazar);
                    hPlan.doma.setVychod(hPlan.bazar);
                    hPlan.kasino.setVychod(hPlan.bazar);
                    hPlan.obchod.setVychod(hPlan.bazar);
                    updateHerniPlan();
                    vypis = "Ty seš vážně stědrej, za to ti řeknu kde můžeš najít super bazar";
                }
                else
                {
                    vypis = prostor.getPostava("bezdomovec").getRec();
                }
                break;
                default:
                vypis = "Co s tím mám jako dělat?";
            }
        }
        if (jmenoPostavy.equals("prodavac"))
        {
            if (nazevVeci.equals("alkohol"))
            {
               hPlan.getInventar().odeberZInventare("alkohol");
               vypis = "Vaši reklamaci přijímám \n" + 
               hPlan.getInventar().zvysStavPenez(200);
               updateHerniPlan();
            }
            else
            {
                vypis = "Nevím co bych s tím dělal pane";
            }
        }
        if (jmenoPostavy.equals("zamestnanec"))
        {
            switch (nazevVeci)
            {
            case "mobil":
            hPlan.getInventar().odeberZInventare("mobil");
            vypis = hPlan.getInventar().zvysStavPenez(3200);
            updateHerniPlan();
            break;
            case "hodinky":
            hPlan.getInventar().odeberZInventare("hodinky");
            vypis = hPlan.getInventar().zvysStavPenez(5000);
            updateHerniPlan();
            break;
            case "obraz":
            hPlan.getInventar().odeberZInventare("obraz");
            vypis = hPlan.getInventar().zvysStavPenez(10000);
            updateHerniPlan();
            break;
            default:
            vypis = "Tak za tohle vám vážně peníze nedám";
        }
        }
        if (jmenoPostavy.equals("kamarad"))
        {
            vypis = "Nic od tebe nechci, ty to potřebuješ víc";
        }
        return vypis;
    }
    
    public String getNazev() {
        return NAZEV;
    }
    @Override
    public void updateHerniPlan() {
        hPlan.notifyAllObservers();
    }

    /**
     * @return the bezdomovecDostal
     */
    public Set<String> getBezdomovecDostal() {
        return bezdomovecDostal;
    }

    /**
     * @param bezdomovecDostal the bezdomovecDostal to set
     */
    public void setBezdomovecDostal(Set<String> bezdomovecDostal) {
        this.bezdomovecDostal = bezdomovecDostal;
    }

    /**
     * @return the obchodNavstivil
     */


}
