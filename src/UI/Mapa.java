package UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import logika.IHra;
import main.Main;
import utils.Observer;

/**
 *
 * @author spia00
 */
public class Mapa extends AnchorPane implements Observer{

    private IHra hra;
    private Circle tecka;
    private ImageView obrazek;
    
    /**
     * Konstruktor třídy
     * 
     * @param hra
     */
    public Mapa(IHra hra){
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        init();
    }
    
    /**
     * Zajištění znovuvytvoření observeru při nové hře
     * 
     * @param novaHra
     */
    public void novaHra(IHra novaHra){
        hra.getHerniPlan().deleteObserver(this);
        hra = novaHra;
        hra.getHerniPlan().registerObserver(this);
        update();
    }
    
    /**
     * Nastavení při prvním spuštění
     * 
     */
    private void init(){
        obrazek = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa.png"),450,305,false,false));
        tecka = new Circle(10, Paint.valueOf("red"));
        this.getChildren().addAll(obrazek, tecka);
        update();
    }
    
    /**
     * Zobrazení aktuálního obrázku mapy a umístění tečky na mapu do aktuálního prostoru
     * 
     */
    @Override
    public void update() {
        
        if(hra.getHerniPlan().vyberProstor("obchod").getZna() && hra.getHerniPlan().vyberProstor("bazar").getZna()){
            
            this.getChildren().clear();

            obrazek = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa_full.png"),450,305,false,false));
            this.getChildren().addAll(obrazek, tecka);
        }
        else
            
        if(hra.getHerniPlan().vyberProstor("obchod").getZna()){
            
            this.getChildren().clear();

            obrazek = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa_obchod.png"),450,305,false,false));
            this.getChildren().addAll(obrazek, tecka);
            }
            
        
        this.setTopAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosY());
        this.setLeftAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosX());
        
        if(hra.konecHry()){
                    this.setDisabled(true);
                }
        else
        this.setDisabled(false); 
    }   
}
