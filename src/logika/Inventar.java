/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import utils.Observer;
import utils.Subject;



/*******************************************************************************
 * Instance třídy {@code Inventar} představují seznam věci, které si hráč nese s sebou
 *
 * @author    Adam Spivák
 * @version   02.01.2017
 */
public class Inventar implements Subject
{
    private List<Observer> listObserveru = new ArrayList<Observer>();
    private Map<String, Vec> obsahInventare; //atribut pro uchování obsahu inventáře
    private int stavPenez;
    private static final int MAX_POCET = 4; // maximální počet věcí v inventáři
    //##########################################################################
    //== KONSTRUKTORY A TOVÁRNÍ METODY =========================================

    /***************************************************************************
     * Konstruktor ....
     * 
     */
    public Inventar()
    {
        obsahInventare = new HashMap<String, Vec>();
        stavPenez = 2000;
    }

    //PUBLIC METODY
    
    /**
     * Metoda dá zadanou věc do inventáře
     * 
     * @param   vec   vkládaná věc
     * @return  řetězec o úspěšnosti vložení
     */
    public String dejDoInventare(Vec vec)
    {
        if (obsahInventare.size() < MAX_POCET)
        {
        obsahInventare.put(vec.getNazev(), vec);
        return "Věc byla přidána do inventáře";
    }

    return "Věc nebyla přidána, v inventáři není místo";
    }
    
    /**
     * Metoda vrací iformaci o tom, zda je v inventáři místo
     * 
     * @return  true pokud je místo
     */
    public boolean jeMisto() {
        if (obsahInventare.size() < MAX_POCET) {
            return true;   
        }        
        return false;
    }
    /**
     * Metoda odebere zadanou věc z inventáře
     * 
     * @param   nazev   nazev odebírané věci
     */
    public void odeberZInventare(String nazev)
    {
        obsahInventare.remove(nazev);
    }
    
    /**
     * Metoda vrací informaci o tom, zda inventář obsahuje zadanou věc
     * 
     * @param   nazev   název věci
     * @return  true pokud obsahuje věc
     */
    public boolean obsahujeVec(String nazev) {
        return obsahInventare.containsKey(nazev);
    }
    
    /**
     * Metoda po zadání názvu věci vrací její odkaz
     * 
     * @param   nazev název věci
     * @return  věc
     */
    public Vec getVec(String nazev){
        return obsahInventare.get(nazev);
    }
    
    /**
     * Metoda vrací řetězec věcí v inventáři
     * 
     * @return  seznam věcí v invenáři, oddělené čárkou
     */
    public String getSeznam()
    {
        String seznam = "Věci v inventáři: ";
        for (String s : obsahInventare.keySet())
        {
            if (!seznam.equals("Věci v inventáři: "))
            {
                seznam += ", ";
            }
            seznam += s;
        }
        return seznam;
    }
    
    /**
     * Metoda sníží stav peněz, pokud by tím nedošlo k přečerpání peněz
     * 
     * @param castka    částka, o kterou se má snížit aktuální stav peněz
     */
    public String snizStavPenez(int castka)
    {
       if((stavPenez - castka) >= 0)
       {
        stavPenez = stavPenez - castka;
        return "Došlo ke snížení stavu peněz";
       }
       else
       {
        return "Nedostatek peněz"; 
       }
    }
    
    /**
     * Metoda zvýší stav peněz
     * 
     * @param castka    částka, o kterou se zvýší aktuální stav peněz
     */
    public String zvysStavPenez(int castka)
    {
        stavPenez = stavPenez + castka;
        return "Došlo ke zvýšení stavu peněz";
    }
    
    /**
     * Metoda vrací datovou kolekci inventáře
     * 
     * @return datová kolekce inventáře
     */
    public Map<String,Vec> getObsahInventare()
    {
        return obsahInventare;
    }
    
    
    /**
     * Metoda vrátí aktuální stav peněz
     * 
     * @return aktuální stav peněz
     */
    public int stavPenez()
    {
        return stavPenez;
    }
    /**
     * Metoda nastvaví aktuální stav peněz
     * 
     * @param   stav peněz
     */
    public void setStavPenez(int stav)
    {
        stavPenez = stav;
    }
    
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        listObserveru.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
        }
    }
}
