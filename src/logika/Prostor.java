package logika;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import utils.Subject;
import utils.Observer;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Adam Spivak
 * @version ZS 2016/2017
 */
public class Prostor implements Subject {

    private String nazev;
    private String popis;
    private Set<Prostor> vychody;   // obsahuje sousední místnosti
    private Map<String, Vec> veci; //věci v prostoru
    private Map<String, Postava> postavy; //postavy v prostoru
    private boolean zna;
    private double posX;
    private double posY;
    private List<Observer> listObserveru = new ArrayList<Observer>();
    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     * @param popis Popis prostoru.
     */
    public Prostor(String nazev, String popis, boolean zna, double posX, double posY) {
        this.nazev = nazev;
        this.popis = popis;
        this.zna = zna;
        this.posX = posX;
        this.posY = posY;
        vychody = new HashSet<>();
        veci = new HashMap<>();
        postavy = new HashMap<>();
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
      @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.nazev, druhy.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }
      

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * lokaci vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Jsi v lokaci " + popis + ".\n"
                + "\n"
                + popisVychodu() + "\n"
                + popisVeci() + "\n"
                + popisPostav();
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    public String popisVychodu() {
        String vracenyText = "Východy:";
        for (Prostor sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
        }
            return vracenyText;
       }
        
    /**
     *  Metoda vrací řetězec názvů věci, které se v prostoru nacházejí
     *  
     *  @return seznam věci v prostoru oddělené mezerou
     */
    
    public String popisVeci() {
        String vracenyText = "Věci v prostoru:";
        for (String nazev : veci.keySet()) {
            vracenyText += " " + nazev;
        }
        
        return vracenyText;
    }

    /**
     *  Metoda vrací řetězec názvů postav, které se v prostoru nacházejí
     *  
     *  @return seznam postav v prostoru oddělené mezerou
     */
    
    public String popisPostav()
    {
        String vracenyText = "Postavy v prostoru:";
        for (String nazev : postavy.keySet())
        {
            vracenyText += " " + nazev;
        }
        return vracenyText;
    }
    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory = 
            vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                   .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }
    
    /**
     *  Metoda vloží zadanou věc do prostoru.
     *  
     *  @param vkládaná věc
     */
    public void vlozVec(Vec vec) {
        veci.put(vec.getNazev(), vec);
    }
    
    /**
     *  Metoda vloží zadanou postavu do prostoru.
     *  
     *  @param vkladaná postava
     */
    public void vlozPostavu(Postava postava)
    {
        postavy.put(postava.getJmeno(), postava);
    }
    
    /**
     *  Metoda odebere zadanou věc z prostoru v případě, že je přenositelná.
     *  
     *  @param název odebírané věci
     *  @return odebraná věc nebo null pokud je nepřenositelná
     */
    public Vec odeberVec(String nazev) {
       Vec vec;
       vec = getVec(nazev);
        if (vec.jePrenositelna())
        {
        veci.remove(nazev);  
        return vec;
    }
    return null;
    }
    
    /**
     *  Metoda odebere zadanou postavu z prostoru.
     *  
     *  @param jméno odebírané postavy
     */
    
    public void odeberPostavu(String nazev)
    {
        Postava postava;
        postava = getPostava(nazev);
        postavy.remove(nazev);
    }
   
    /**
     *  Metoda vrátí odkaz na věc v prostoru po zadaní jejího názvu
     *  
     *  @param název věci
     *  @return věc
     */
    
    public Vec getVec(String nazev){
        return veci.get(nazev);
    }
    
    /**
     *  Metoda vrátí odkaz na postavu v prostoru po zadaní jejího jména
     *  
     *  @param název postavy
     *  @return postava
     */
    
    public Postava getPostava(String nazev)
    {
        return postavy.get(nazev);
    }
    
    /**
     *  Metoda zjištuje zda se v prostoru nachází zadaná věc
     *  
     *  @param název věci
     *  @return true pokud se nachází, false pokud se nenachází
     */
    
    public boolean obsahujeVec(String nazev) {
        return veci.containsKey(nazev);
    }
    
    /**
     *  Metoda zjištuje zda se v prostoru nachází zadaná postava
     *  
     *  @param jméno postavy
     *  @return true pokud se nachází, false pokud se nenachází
     */
    
    public boolean obsahujePostavu(String nazev)
    {
        return postavy.containsKey(nazev);
    }
    
    
    public Map<String,Vec> getVeciVProstoru()
    {
        return veci;
    }
    
    public Map<String,Postava> getPostavyVProstoru()
    {
        return postavy;
    }
    

    /**
     * @return the posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * @param posX the posX to set
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * @return the posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * @param posY the posY to set
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }
    
        /**
     * @return the navstivil
     */
    public boolean getZna() {
        return zna;
    }

    /**
     * @param navstivil the navstivil to set
     */
    public void setZna (boolean zna) {
        this.zna = zna;
    }
    
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }
    /**
     * Zrušení observeru
     * @param observer Observer
     */
    @Override
    public void deleteObserver(Observer observer) {
        listObserveru.remove(observer);
    }
    /**
     * Oznámení observeru
     */   
    @Override
    public void notifyAllObservers() {
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
        }
    }


}
