package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import javafx.scene.layout.FlowPane;
import logika.IHra;
import utils.Observer;

/**
 *
 * @author spia00
 */
public class PostavyVProstoru extends ListView implements Observer {

    public IHra hra;
    public TextArea centralText;
    ObservableList<FlowPane> dataPostavyProstor;

    /**
     * Konstruktor třídy
     * 
     * @param hra
     * @param centralText
     */
    public PostavyVProstoru(IHra hra, TextArea centralText) {
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
     * Zjištění postav, které se aktuálně nacházejí v aktuálním prostoru
     * 
     */
    @Override
    public void update() {
        dataPostavyProstor.clear();

        for (String text : hra.getHerniPlan().getAktualniProstor().getPostavyVProstoru().keySet()) {
                FlowPane polozka = new FlowPane();
                polozka.setPrefWidth(100);
                polozka.setAlignment(Pos.CENTER);
                Button button = new Button(text);
                button.setPrefSize(110, 20);
                button.setStyle(String.format("-fx-font-size: 10px; -fx-border-width: 2px; -fx-border-color: gray;"));
                button.setOnAction((ActionEvent event) -> {
                    String prikaz = "mluv " + text;
                    centralText.appendText("\n" + prikaz);
                    String odpoved = hra.zpracujPrikaz(prikaz);
                    centralText.appendText("\n\n" + odpoved + "\n");
                });
                polozka.getChildren().add(button);              
                dataPostavyProstor.add(polozka);              
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
        dataPostavyProstor = FXCollections.observableArrayList();
        this.setItems(dataPostavyProstor);
        this.setPrefWidth(200);
        this.setPrefHeight(120);
        
    }
}

