package UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import main.Main;

/**
 *
 * @author spia00
 */
public class MenuPole extends MenuBar{
    
    private Main main;
    private IHra hra;
    
    /**
     * Konstruktor třídy
     * 
     * @param main
     */
    public MenuPole(Main main){
        this.main = main;
        init();
    }
    
    /**
     * Nastavení obsahu horní lišty
     * 
     */
    private void init(){
        Menu menuSoubor = new Menu("Advenura");
        
        MenuItem itemNovaHra = new MenuItem("Nová hra");
        itemNovaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        
        MenuItem itemKonec = new MenuItem("Konec");
        
        
        Menu menuHelp = new Menu("Help");
        MenuItem itemOProgramu = new MenuItem("O programu");
        MenuItem itemNapoveda = new MenuItem("Napoveda");
        
        
        menuSoubor.getItems().addAll(itemNovaHra, itemKonec);
        menuHelp.getItems().addAll(itemOProgramu, itemNapoveda);
        
        this.getMenus().addAll(menuSoubor, menuHelp);
        
        // co se stane když klikneme na tlačítko o programu
        itemOProgramu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("O Adventure");
                alert.setHeaderText("Toto je ma adventura");
                alert.setContentText("Graficka verze adventury");
                alert.initOwner(main.getPrimaryStage());
                alert.showAndWait();
            }
        });
        
        // co se stane když klikneme na tlačítko napoveda
        itemNapoveda.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                stage.setTitle("Napoveda");
                WebView webview = new WebView();
                
                webview.getEngine().load(Main.class.getResource("/zdroje/napoveda.html").toExternalForm());
                
                stage.setScene(new Scene(webview, 500, 500));
                stage.show();
            }
        });
        
        // co se stane když klikneme na tlačítko konec
        itemKonec.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        // co se stane když klikneme na tlačítko nová hra
        itemNovaHra.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        hra = new Hra();
                        main.setHra(hra);
                        main.getMapa().novaHra(hra);
                        main.getSeznamVychodu().novaHra(hra);
                        main.getObsahInventare().novaHra(hra);
                        main.getPostavyVProstoru().novaHra(hra);
                        main.getVeciVProstoru().novaHra(hra);
                        main.getStavPenez().novaHra(hra);
                        main.getCentralText().setText(hra.vratUvitani());
                        main.getZadejPrikazTextField().setEditable(true);
                    }
                });
        
    }
    
}
