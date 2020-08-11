/*
 * Copyright (C) 2020 Grupo 1
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package swapping;


import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Grupo 1
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    Button b_swapInWith;    
     @FXML
    Button b_swapInWithout;  
    @FXML
    Button b_swapOutWith;    
    @FXML
    Button b_swapOutWithout;    
    @FXML
    Button b_configSim;
    @FXML
    Label timer;    
    @FXML
    ListView colaDeProcesos;
    @FXML
    ListView listaMemoriaPrincipal;
    @FXML
    ListView listaMemoriaSecundaria; 
   
    Cluster auxiliarClusterSeleccionado = null;
    Simulacion aux1 = new Simulacion();
    Proceso aux2 = new Proceso(1,"Google.exe",1024,2);
    Proceso aux3 = new Proceso(1,"Firefox.exe",512,2);
    Proceso aux4 = new Proceso(1,"Minecraft.exe",256,2);  
    Thread hilo;       
        
    Runnable runnable = new Runnable() {
        String segundos;
        @Override
        public void run() {            
            while (true) {
                segundos = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));                
            }
        }
        
        public String getTime() {
            return segundos;
        }
    };
    
    public void runTimer(){      
        hilo = new Thread(runnable);
        hilo.start();      
       
    }

    @FXML
    private void handleButtonActionConfigSim(ActionEvent event) {        
        //aux1.procesos.add(aux2);
        aux1.swapInConFragmentacionExterna(aux2, true);
        aux1.swapInConFragmentacionExterna(aux3, true);
        aux1.swapInConFragmentacionExterna(aux4, true);
        this.runTimer();
        this.refrescar();         
    }
    
    @FXML
    private void handleButtonActionSwapIn(ActionEvent event) {
        
    }
    
    @FXML
    private void handleButtonActionSwapOut(ActionEvent event) {
        aux1.swapOutConFragmentacionExterna(aux2, false);        
        this.refrescar();        
    }
    
    @FXML
    public void seleccionTweet(MouseEvent event){        
        auxiliarClusterSeleccionado =  (Cluster) this.listaMemoriaPrincipal.getSelectionModel().getSelectedItem();       
        //Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        //cb.setContents(auxiliarSeleccionado.toString(), null);
    }
    
    
    private void refrescar(){      
        ObservableList<Cluster> listaObservableUno = FXCollections.observableList(Arrays.asList(this.aux1.memoriaPrincipal.getClusters()));            
        this.listaMemoriaPrincipal.setItems(listaObservableUno);
        this.listaMemoriaPrincipal.refresh();
 
        //ObservableList<Cluster> listaObservableUno = FXCollections.observableList(Arrays.asList(this.aux1.memoriaPrincipal.getClusters()));            
        this.listaMemoriaPrincipal.setItems(listaObservableUno);
        this.listaMemoriaPrincipal.refresh();
        
        ObservableList<Cluster> listaObservableDos = FXCollections.observableList(Arrays.asList(this.aux1.memoriaRespaldo.getClusters()));            
        this.listaMemoriaSecundaria.setItems(listaObservableDos);
        this.listaMemoriaSecundaria.refresh();
        
    }  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        this.listaMemoriaPrincipal.setCellFactory(param -> new ListCell<Proceso>() {
            private final ImageView imageView = new ImageView(new Image(this.getClass().getResource("color.png").toString()));
            @Override
            public void updateItem(Proceso aux, boolean empty) {
                super.updateItem(aux, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setEffect(new InnerShadow(100, aux.getColor()));
                    setGraphic(imageView);
                    setText(": " + aux.toString() + "\n");
                }
            }
        });
        
        this.listaMemoriaPrincipal.setCellFactory(param -> new ListCell<Cluster>() {
            private final ImageView imageView = new ImageView(new Image(this.getClass().getResource("color.png").toString()));
            @Override
            public void updateItem(Cluster aux, boolean empty) {
                super.updateItem(aux, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setEffect(new InnerShadow(100, aux.getColor()));
                    setGraphic(imageView);
                    setText(": " + aux.toString() + "\n");
                }
            }
        });
        
        this.listaMemoriaSecundaria.setCellFactory(param -> new ListCell<Cluster>() {
            private final ImageView imageView = new ImageView(new Image(this.getClass().getResource("color.png").toString()));
            @Override
            public void updateItem(Cluster aux, boolean empty) {
                super.updateItem(aux, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setEffect(new InnerShadow(100, aux.getColor()));
                    setGraphic(imageView);
                    setText(": " + aux.toString() + "\n");
                }
            }
        });             
        
        this.refrescar(); 
    }    
    
}