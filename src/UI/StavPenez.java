package UI;

import javafx.scene.control.TextArea;
import logika.IHra;
import utils.Observer;

/**
 *
 * @author spia00
 */
public class StavPenez extends TextArea implements Observer{

    private IHra hra;

    /**
     * Konstruktor třídy
     * 
     * @param hra
     */
    public StavPenez(IHra hra){
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
        this.setPrefWidth(305);
        this.setEditable(false);
        this.setMouseTransparent(true);
        this.setFocusTraversable(false);
        update();
    }
    
    /**
     * Zjištění aktuálního stavu peněz
     * 
     */
    @Override
    public void update() {
        this.setText("Aktuální stav peněz: " + hra.getHerniPlan().getInventar().stavPenez());
        if(hra.konecHry()){
                    this.setDisabled(true);
                }
        else
        this.setDisabled(false); 
        
    }    
}
