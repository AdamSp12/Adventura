package uiText;


import java.io.*;
import java.util.Scanner;
import logika.IHra;
/**
 * Class TextoveRozhrani
 * 
 * Toto je uživatelského rozhraní aplikace Adventura
 * Tato třída vytváří instanci třídy Hra, která představuje logiku aplikace.
 * Čte vstup zadaný uživatelem a předává tento řetězec logice a vypisuje odpověď logiky na konzoli.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha
 * @version    ZS 2016/2017
 */

public class TextoveRozhrani {
    private IHra hra;

    /**
     *  Vytváří hru.
     */
    public TextoveRozhrani(IHra hra) {
        this.hra = hra;
    }

    /**
     *  Hlavní metoda hry. Vypíše úvodní text a pak opakuje čtení a zpracování
     *  příkazu od hráče do konce hry (dokud metoda konecHry() z logiky nevrátí
     *  hodnotu true). Nakonec vypíše text epilogu.
     */
    public void hraj() {
        System.out.println(hra.vratUvitani());
        System.out.println();

        // základní cyklus programu - opakovaně se čtou příkazy a poté
        // se provádějí do konce hry.

        while (!hra.konecHry()) {
            String radek = prectiString();
            System.out.println(hra.zpracujPrikaz(radek));
            System.out.println();
        }

        System.out.println(hra.vratEpilog());
    }
    
    public void hrajZeSouboru(String soubor) {
        System.out.println("Nacitam prikazy ze souboru " + soubor);
        
        try (BufferedReader ctec = new BufferedReader(new FileReader(soubor))) {
            
            System.out.println(hra.vratUvitani());
            System.out.println();
            
            String radek = ctec.readLine();
            while (!hra.konecHry() && radek != null) {
                System.out.println("* " + radek + " *");
                System.out.println(hra.zpracujPrikaz(radek));
                System.out.println();
                
                radek = ctec.readLine();
            }

            System.out.println(hra.vratEpilog());
            
        } catch (FileNotFoundException e) {
            System.out.println("Soubor '" + soubor + "' nelze otevřít\n" + e);
        } catch (IOException e) {
            System.out.println("Chyba při čtení ze souboru\n" + e);
        }
    }
    
    /**
     *  Metoda přečte příkaz z příkazového řádku
     *
     *@return    Vrací přečtený příkaz jako instanci třídy String
     */
    private String prectiString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }

}
