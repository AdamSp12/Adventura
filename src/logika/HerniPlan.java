package logika;


/**
 * Class HerniPlan - třída představující mapu a stav adventury.
 * 
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny prostory, propojuje je vzájemně pomocí východů
 * a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Adam Spivák
 * @version    ZS 2016/2017
 */
public class HerniPlan {

    static final String CILOVY_PROSTOR = "predmesti";
    private Inventar inventar;
    private Prostor aktualniProstor;
    
    //vytvoření věcí, prostorů a postav, které nejsou ve hře hned od vytvoření hry.
    Vec mobil = new Vec("mobil", "Příkazem volej <číslo> zavoláš na zadané číslo", true);
    Vec papir = new Vec("papir", "Na papíru je napsáno 608748222", true);
    Vec cigarety = new Vec("cigarety", "ještě neotevřená krabička cigaret", true);
    Vec obraz = new Vec("obraz", "Jak někdo mohl vyhodit takový pěkný obraz, vypadá to na slušné umělecké dílo", true);
    Vec hodinky = new Vec("hodinky", "Staré zlaté hodinky", true);
    Prostor obchod = new Prostor("obchod","obchod s alkoholem\n" + 
                    "nic jiného, než alkohol si tu bohužel nekoupíš\n" + 
                    "flaška vodky stojí 200,-");
    Prostor doma = new Prostor("doma","tvůj domov");
    Prostor park = new Prostor("park", "park, ve kterém se nachází bezdomovec\n" +
                   "poblíž se nachází popelnice");
    Prostor bazar = new Prostor("bazar","bazar, ve kterém se dají prodat věci");
    Prostor kasino = new Prostor("kasino","kasino, ve kterém lze hrát ruletu");
    Prostor predmesti = new Prostor("predmesti", "předměstí \n" + "Gratuluji, vyhrál jsi!!!");
    Postava kamarad = new Postava("kamarad", "Ahoj, tady máš nějaký peníze a zlatý hodinky. \n" +
                                  "Doufám, že ti to trochu pomůže");
    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  
     */
    public HerniPlan() {
        zalozProstoryHry();
        inventar = new Inventar();
    }
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví doma.
     */
    private void zalozProstoryHry() {
        // přiřazují se průchody mezi prostory (sousedící prostory)
        doma.setVychod(park);
        doma.setVychod(kasino);
        doma.setVychod(predmesti);
        park.setVychod(doma);
        park.setVychod(kasino);
        park.setVychod(predmesti);
        kasino.setVychod(doma);
        kasino.setVychod(park);
        kasino.setVychod(predmesti);
        obchod.setVychod(doma);
        obchod.setVychod(kasino);
        obchod.setVychod(predmesti);
        obchod.setVychod(park);
        bazar.setVychod(doma);
        bazar.setVychod(park);
        bazar.setVychod(predmesti);
        bazar.setVychod(kasino);
        bazar.setVychod(obchod);

        // vytvoříme několik věcí
        String hlaska = "Bližší informace o tomto předmětu(ech) zjistíš pomocí příkazu 'prohledej'.";
        Vec stul = new Vec("stul", "Na stole jsi našel 2000,- , mobil a krabičku cigaret. \n" + hlaska, false, false);
        Vec postel = new Vec("postel", "Pod postelí jsi našel nějaký popsaný papír. \n" + hlaska, false, false);
        Vec popelnice = new Vec("popelnice", "V popelnici jsi našel vzácný obraz. \n" + hlaska, false, false);
        Vec alkohol = new Vec("alkohol", "alkohol 40%", true);

        // umístíme věci do prostorů
        doma.vlozVec(stul);
        doma.vlozVec(postel);
        park.vlozVec(popelnice);
        obchod.vlozVec(alkohol);
        
        // vytvoříme několik postav
        Postava bezdomovec = new Postava("bezdomovec", "Nebyla by cigaretka pane?");
        Postava prodavac = new Postava("prodavac", "Za 200,- si můžete vzít jednu flašku");
        Postava zamestnanec = new Postava("zamestnanec", "Zdravím, dejte mi něco, o co budu mít zájem, \n" + 
        "dám vám za to peníze");
        //umístíme postavy do prostorů
        park.vlozPostavu(bezdomovec);
        obchod.vlozPostavu(prodavac);
        bazar.vlozPostavu(zamestnanec);

        aktualniProstor = doma;  // hra začíná doma
    }
    
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }
    
    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
    }
    
    /**
     *  Metoda vrací true pokud hráč vyhrál oficiální cestou (tj. došel do cílového prostoru s
     *  alespoň 50000,-
     *  
     *  @return hráč vyhrál oficiální cestou
     */
    public boolean hracVyhral() {
        if (aktualniProstor.getNazev().equals(CILOVY_PROSTOR) && inventar.stavPenez() >= 50000) {
            return true;
        }
        
        return false;
    }
    
    /**
     *  Metoda vrací odkaz na inventář používaný v této hře
     *  
     *  @return Inventář
     */
    public Inventar getInventar()
    {
        return inventar;
    }
}
