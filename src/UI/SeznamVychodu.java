package UI;

import utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import logika.IHra;
import logika.Prostor;

/**
 *
 * @author spia00
 */
public class SeznamVychodu extends ListView implements Observer {

 
    public IHra hra;
    public TextArea centralText;
    ObservableList<FlowPane> dataVychodu;

    /**
     * Konstruktor třídy
     *
     * @param hra
     * @param centralText
     */
    public SeznamVychodu(IHra hra, TextArea centralText) {
        this.hra = hra;
        this.centralText = centralText;
        hra.getHerniPlan().registerObserver(this);
        init();
        update();
    }

    /**
     * Zajištění znovuvytvoření observeru při nové hře
     *
     * @param hra Nová hra
     */
    public void novaHra(IHra hra) {
        hra.getHerniPlan().deleteObserver(this);
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        update();
    }

    /**
     * Zjištění aktuálních východů z aktuálního prostoru
     *
     */
    @Override
    public void update() {
        dataVychodu.clear();

        for (Prostor prostor : hra.getHerniPlan().getAktualniProstor().getVychody()) {
            FlowPane polozka = new FlowPane();
            polozka.setPrefWidth(100);
            polozka.setAlignment(Pos.CENTER);

            String text = prostor.getNazev();
            Button button = new Button(text);
            button.setPrefSize(110, 20);
            button.setStyle(String.format("-fx-font-size: 10px; -fx-border-width: 2px; -fx-border-color: gray;"));
            button.setOnAction((ActionEvent event) -> {
                String prikaz = "jdi " + prostor.getNazev();
                centralText.appendText("\n" + prikaz);
                String odpoved = hra.zpracujPrikaz(prikaz);
                centralText.appendText("\n\n" + odpoved + "\n");
            });
            polozka.getChildren().add(button);

            dataVychodu.add(polozka);
            if (hra.konecHry()) {
                this.setDisabled(true);
            } else {
                this.setDisabled(false);
            }
        }
    }

    /**
     * Nastavení při prvním spuštění
     * 
     */
    private void init() {

        dataVychodu = FXCollections.observableArrayList();
        this.setItems(dataVychodu);
        this.setPrefHeight(180);
        this.setPrefWidth(120);
    }
}
