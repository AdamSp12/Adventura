package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import logika.IHra;
import main.Main;
import utils.Observer;

/**
 *
 * @author spia00
 */
public class VeciVProstoru extends ListView implements Observer {

    public IHra hra;
    public TextArea centralText;
    ObservableList<FlowPane> dataVeciProstor;
    private ImageView obrazek;
    private String cesta;

    /**
     * Konstruktor třídy
     * 
     * @param hra
     * @param centralText
     */
    public VeciVProstoru(IHra hra, TextArea centralText) {
        this.hra = hra;
        this.centralText = centralText;
        hra.getHerniPlan().registerObserver(this);
        init();
        update();
    }

    /**
     * Zajištění znovuvytvoření observeru při nové hře
     * 
     * @param hra
     */
    public void novaHra(IHra hra) {
        hra.getHerniPlan().deleteObserver(this);
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        update();
    }

    /**
     * Zjištění aktuálních věcí v aktuálním prostoru
     *
     */
    @Override
    public void update() {
        dataVeciProstor.clear();

        for (String text : hra.getHerniPlan().getAktualniProstor().getVeciVProstoru().keySet()) {
                FlowPane polozka = new FlowPane();
                polozka.setPrefWidth(100);
                polozka.setAlignment(Pos.CENTER);
                cesta = "/zdroje/" + text + ".jpg";
                obrazek = new ImageView(new Image(Main.class.getResourceAsStream(cesta),20,20,false,false));
                Button button = new Button(text, obrazek);
                button.setPrefSize(110, 20);
                button.setStyle(String.format("-fx-font-size: 10px; -fx-border-width: 2px; -fx-border-color: gray;"));
                button.setOnAction((ActionEvent event) -> {
                    String prikaz = "seber " + text;
                    centralText.appendText("\n" + prikaz);
                    String odpoved = hra.zpracujPrikaz(prikaz);
                    centralText.appendText("\n\n" + odpoved + "\n");
                });
                polozka.getChildren().add(button);              
                dataVeciProstor.add(polozka);              
            }
        if(hra.konecHry()){
                    this.setDisabled(true);
                }
        else
        this.setDisabled(false);    
        
    }
    
    /**
     * Nastavení při prvním spuštění
     * 
     */
    private void init() {
        dataVeciProstor = FXCollections.observableArrayList();
        this.setItems(dataVeciProstor);
        this.setPrefWidth(200);
        this.setPrefHeight(170);
        
    }
}

