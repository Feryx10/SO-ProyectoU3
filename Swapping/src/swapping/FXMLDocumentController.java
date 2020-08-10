/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swapping;


import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Asus-ROG
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    Button b_swapIn;    
    @FXML
    Button b_swapOut;    
    @FXML
    Button b_configSim;
    @FXML
    ListView memoriaPrincipal;
    @FXML
    ListView memoriaSecundaria;   
    
    Memoria memoriaUno = new Memoria(4096);
    Memoria memoriaDos = new Memoria(4096);
    
    @FXML
    private void handleButtonActionConfigSim(ActionEvent event) {
        
    }
    
    @FXML
    private void handleButtonActionSwapIn(ActionEvent event) {
        
    }
    
    @FXML
    private void handleButtonActionSwapOut(ActionEvent event) {
        
    }
    
    
    private void refrescar(){      
 
        ObservableList<Cluster> listaObservableUno = FXCollections.observableList(Arrays.asList(this.memoriaUno.getClusters()));            
        this.memoriaPrincipal.setItems(listaObservableUno);
        this.memoriaPrincipal.refresh();
        
        ObservableList<Cluster> listaObservableDos = FXCollections.observableList(Arrays.asList(this.memoriaDos.getClusters()));            
        this.memoriaSecundaria.setItems(listaObservableDos);
        this.memoriaSecundaria.refresh();
        
    }  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {           
        this.memoriaPrincipal.setCellFactory(param -> new ListCell<Cluster>() {
            private final ImageView imageView = new ImageView(new Image(this.getClass().getResource("color.png").toString()));
            @Override
            public void updateItem(Cluster aux, boolean empty) {
                super.updateItem(aux, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //imageView.setEffect(new InnerShadow(100, aux.getColor()));
                    setGraphic(imageView);
                    setText(" > " +  " | \n");
                }
            }
        });
        
        this.memoriaSecundaria.setCellFactory(param -> new ListCell<Cluster>() {
            private final ImageView imageView = new ImageView(new Image(this.getClass().getResource("color.png").toString()));
            @Override
            public void updateItem(Cluster aux, boolean empty) {
                super.updateItem(aux, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    //imageView.setEffect(new InnerShadow(100, aux.getColor()));
                    setGraphic(imageView);
                    setText(" > " + " | \n");
                }
            }
        });             
        
        this.refrescar(); 
    }    
    
}