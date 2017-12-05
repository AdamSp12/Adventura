package main;

import UI.Mapa;
import UI.MenuPole;
import UI.ObsahInventare;
import UI.SeznamVychodu;
import UI.VeciVProstoru;
import UI.PostavyVProstoru;
import UI.StavPenez;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import uiText.TextoveRozhrani;

/**
 * @author spia00
 */
public class Main extends Application {

    private Mapa mapa;
    private MenuPole menu;
    private IHra hra;
    private Stage primaryStage;
    private SeznamVychodu seznamVychodu;
    private ObsahInventare obsahInventare;
    private VeciVProstoru veciVProstoru;
    private PostavyVProstoru postavyVProstoru;
    private TextArea centralText = new TextArea();
    private TextField zadejPrikazTextField = new TextField();
    private StavPenez stavPenez;
    
    /**
     * Inicializace GUI
     */
    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        hra = new Hra();
        mapa = new Mapa(hra);
        menu = new MenuPole(this);
        seznamVychodu = new SeznamVychodu(hra, centralText);
        obsahInventare = new ObsahInventare(hra, centralText);
        veciVProstoru = new VeciVProstoru(hra, centralText);
        postavyVProstoru = new PostavyVProstoru(hra, centralText);
        stavPenez = new StavPenez(hra);

        BorderPane borderPane = new BorderPane();

        centralText.setText(hra.vratUvitani());
        centralText.setEditable(false);

        Label zadejPrikazLabel = new Label("Zadej prikaz");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        getZadejPrikazTextField().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                String zadanyPrikaz = getZadejPrikazTextField().getText();
                String odpoved = hra.zpracujPrikaz(zadanyPrikaz);

                centralText.appendText("\n" + zadanyPrikaz + "\n");
                centralText.appendText("\n" + odpoved + "\n");

                getZadejPrikazTextField().setText("");

                if (hra.konecHry()) {
                    getZadejPrikazTextField().setEditable(false);
                }

            }
        });

        FlowPane dolniPanel = new FlowPane();
        dolniPanel.setAlignment(Pos.CENTER);
        dolniPanel.getChildren().addAll(zadejPrikazLabel, getZadejPrikazTextField());

        borderPane.setCenter(centralText);
        borderPane.setBottom(dolniPanel);
        borderPane.setLeft(levyPanel());
        borderPane.setRight(pravyPanel());

        borderPane.setTop(menu);
        //velikost sceny
        Scene scene = new Scene(borderPane, 1000, 800);

        primaryStage.setTitle("Moje Adventura");
        primaryStage.setScene(scene);
        primaryStage.show();

        getZadejPrikazTextField().requestFocus();
    }
    
    /**
     * Definuje obsah levého panelu
     * 
     * @return the levyPanel
     */
    private BorderPane levyPanel() {
        BorderPane levyPanel = new BorderPane();
        levyPanel.setTop(mapa);
        levyPanel.setCenter(getStavPenez());

        return levyPanel;
    }
    
    /**
     * Definuje obsah pravého panelu
     * 
     * @return the pravyPanel
     */
    private BorderPane pravyPanel() {

        BorderPane veci = new BorderPane();
        veci.setPrefHeight(400);
        BorderPane inventar = new BorderPane();
        Label textObsahInventare = new Label("\n Obsah inventáře:");
        textObsahInventare.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        inventar.setTop(textObsahInventare);
        inventar.setCenter(getObsahInventare());

        BorderPane veciProstor = new BorderPane();
        Label textVeciVProstoru = new Label("\n Věci v prostoru:");
        textVeciVProstoru.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        veciProstor.setTop(textVeciVProstoru);
        veciProstor.setCenter(getVeciVProstoru());

        veci.setTop(veciProstor);
        veci.setCenter(inventar);

        BorderPane center = new BorderPane();
        center.setPrefHeight(300);

        BorderPane vychody = new BorderPane();
        Label textSeznamVychodu = new Label("\n Seznam východů:");
        textSeznamVychodu.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        vychody.setTop(textSeznamVychodu);
        vychody.setCenter(seznamVychodu);

        BorderPane postavy = new BorderPane();
        Label textSeznamPostav = new Label("\n Seznam postav:");
        textSeznamPostav.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        postavy.setTop(textSeznamPostav);
        postavy.setCenter(getPostavyVProstoru());

        center.setTop(vychody);
        center.setCenter(postavy);

        BorderPane pravyPanel = new BorderPane();
        pravyPanel.setPrefWidth(130);
        pravyPanel.setTop(veci);
        pravyPanel.setCenter(center);

        return pravyPanel;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        } else {
            if (args[0].equals("-text")) {
                IHra hra = new Hra();
                TextoveRozhrani textoveRozhrani = new TextoveRozhrani(hra);
                textoveRozhrani.hraj();
            } else {
                System.out.println("Neplatny parametr");
                System.exit(1);
            }
        }
    }

    /**
     * @return the seznamVychodu
     */
    public SeznamVychodu getSeznamVychodu() {
        return seznamVychodu;
    }
    /**
     * @return the centralText
     */
    public TextArea getCentralText() {
        return centralText;
    }
    
    /**
     * Nastaví hra
     * 
     * @param hra
     */
    public void setHra(IHra hra) {
        this.hra = hra;
    }
    
    /**
     * @return the mapa
     */
    public Mapa getMapa() {
        return mapa;
    }

    /**
     * @return the primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @return the obsahInventare
     */
    public ObsahInventare getObsahInventare() {
        return obsahInventare;
    }

    /**
     * @return the veciVProstoru
     */
    public VeciVProstoru getVeciVProstoru() {
        return veciVProstoru;
    }

    /**
     * @return the postavyVProstoru
     */
    public PostavyVProstoru getPostavyVProstoru() {
        return postavyVProstoru;
    }

    /**
     * @return the stavPenez
     */
    public StavPenez getStavPenez() {
        return stavPenez;
    }

    /**
     * @return the zadejPrikazTextField
     */
    public TextField getZadejPrikazTextField() {
        return zadejPrikazTextField;
    }

}
