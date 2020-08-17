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


import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Grupo 1
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    AnchorPane panel;
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
    ListView colaDePrioridad;
    @FXML
    ListView listaMemoriaPrincipal;
    @FXML
    ListView listaMemoriaSecundaria; 
   
    Cluster auxiliarClusterSeleccionado = null;
    Proceso auxiliarProcesoSeleccionado = null;
    Simulacion simulacion = new Simulacion();
    
    
    volatile int segundos = 1;
    volatile boolean ejecutar = true;
    Thread hilo;  

    public FXMLDocumentController() {
        cargarProgramas();
    }
    
    
        
    Runnable runnable = new Runnable() {  
        @Override
        public void run() {            
            while (ejecutar) {
                //segundos = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));                              
            }
        }        
    };
    
    public void cargarProgramas()
    {
        Proceso aux2 = new Proceso(1,"Google.exe",1024,2,1);
        Proceso aux3 = new Proceso(1,"Firefox.exe",512,2,2);
        Proceso aux4 = new Proceso(1,"Minecraft.exe",256,2,3);
        Proceso aux5 = new Proceso(1,"Premiere.exe",512,2,1);
        Proceso aux6 = new Proceso(1,"Steam.exe",1024,2,3);
        Proceso aux7 = new Proceso(1,"NetBeans.exe",1024,2,5);
        Proceso aux8 = new Proceso(1,"Discord.exe",768,2,2);
        Proceso aux9 = new Proceso(1,"CiscoPacketTracer.exe",512,2,4);
        Proceso aux10 = new Proceso(1,"Spotify.exe",256,2,4);
        Proceso aux11 = new Proceso(1,"GitKraken.exe",512,2,1);
        
        simulacion.agregarProceso(aux2);
        simulacion.agregarProceso(aux3);
        simulacion.agregarProceso(aux4);
        simulacion.agregarProceso(aux5);
        simulacion.agregarProceso(aux6);
        simulacion.agregarProceso(aux7);
        simulacion.agregarProceso(aux8);
        simulacion.agregarProceso(aux9);
        simulacion.agregarProceso(aux10);
        simulacion.agregarProceso(aux11);      
        



    }
    
    public void runTimer(){      
        hilo = new Thread(runnable);
        hilo.start();                      
    }   

    @FXML
    private void handleButtonActionTiempo(ActionEvent event) {
        simulacion.avanzarTiempoLFU();
        this.timer.setText("Timer: "+ simulacion.getTiempo() +" segundos.");
        simulacion.ordernarListaDeProcesosLFU();
        this.refrescar();
        simulacion.pedirFragmento();
     //   simulacion.verificarPrioridad();
        this.refrescar();
    }
    
    @FXML
    private void handleButtonActionSwapInWith(ActionEvent event) {
        if(auxiliarProcesoSeleccionado!=null)
        {
        //    simulacion.swapInConFragmentacionExterna(auxiliarProcesoSeleccionado, false); 
            simulacion.swapInFragmeto(auxiliarProcesoSeleccionado);
            simulacion.removerProceso(auxiliarProcesoSeleccionado); 
        }
    //    System.out.println("ASE");
        this.refrescar(); 
    }
    
    @FXML
    private void handleButtonActionSwapInWithout(ActionEvent event) {
        if(auxiliarProcesoSeleccionado!=null)
            simulacion.swapInSinFragmentacionExterna(auxiliarProcesoSeleccionado, false);
        simulacion.removerProceso(auxiliarProcesoSeleccionado);
        this.refrescar(); 
    }
    
    @FXML
    private void handleButtonActionSwapOutWith(ActionEvent event) {
        if(auxiliarProcesoSeleccionado!=null)
            simulacion.swapOutConFragmentacionExterna(auxiliarProcesoSeleccionado, false);       
        simulacion.removerProceso(auxiliarProcesoSeleccionado);
        this.refrescar();        
    }
    
    @FXML
    private void handleButtonActionSwapOutWithout(ActionEvent event) {
        if(auxiliarProcesoSeleccionado!=null)
            simulacion.swapOutSinFragmentacionExterna(auxiliarProcesoSeleccionado, false);  
        simulacion.removerProceso(auxiliarProcesoSeleccionado);
        this.refrescar();        
    }    
      
    @FXML
    public void seleccionClusterPrimaria(Event event){        
        auxiliarClusterSeleccionado = (Cluster) this.listaMemoriaPrincipal.getSelectionModel().getSelectedItem();     
        auxiliarProcesoSeleccionado = auxiliarClusterSeleccionado.getProceso(0);
        System.out.println(auxiliarClusterSeleccionado.getProceso(0));
        //Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        //cb.setContents(auxiliarSeleccionado.toString(), null);
    }    
    
    @FXML
    public void seleccionClusterSecundaria(Event event){        
        auxiliarClusterSeleccionado = (Cluster) this.listaMemoriaSecundaria.getSelectionModel().getSelectedItem();       
        auxiliarProcesoSeleccionado = auxiliarClusterSeleccionado.getProceso(0);
        System.out.println(auxiliarClusterSeleccionado.getProceso(0));
        //Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        //cb.setContents(auxiliarSeleccionado.toString(), null);
    }  
    
    @FXML
    public void seleccionClusterProcesos(Event event){        
        auxiliarProcesoSeleccionado = (Proceso) this.colaDeProcesos.getSelectionModel().getSelectedItem();       
        System.out.println(auxiliarProcesoSeleccionado);
        //Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        //cb.setContents(auxiliarSeleccionado.toString(), null);
    }  
    
    private void refrescar(){      
        this.timer.setText("Timer: "+ simulacion.getTiempo() +" segundos.");
        
        this.auxiliarClusterSeleccionado = null;
        this.auxiliarProcesoSeleccionado = null;
        
        
        ObservableList<Proceso> ocolaDePrioridad = FXCollections.observableArrayList(this.simulacion.listaDeProcesos);      //Edita aqui      
        this.colaDePrioridad.setItems(ocolaDePrioridad);
        this.colaDePrioridad.refresh();
        
        
        ObservableList<Proceso> ocolaDeProcesos = FXCollections.observableArrayList(this.simulacion.procesos);            
        this.colaDeProcesos.setItems(ocolaDeProcesos);
        this.colaDeProcesos.refresh();
 
        ObservableList<Cluster> listaObservableUno = FXCollections.observableList(Arrays.asList(this.simulacion.memoriaPrincipal.getClusters()));            
        this.listaMemoriaPrincipal.setItems(listaObservableUno);
        this.listaMemoriaPrincipal.refresh();
        
        ObservableList<Cluster> listaObservableDos = FXCollections.observableList(Arrays.asList(this.simulacion.memoriaRespaldo.getClusters()));            
        this.listaMemoriaSecundaria.setItems(listaObservableDos);
        this.listaMemoriaSecundaria.refresh();        
    }  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        this.colaDePrioridad.setCellFactory(param -> new ListCell<Proceso>() {
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
                    setText(": " + aux.toStringTerciario()+ "\n");
                }
            }
        });
        
        this.colaDeProcesos.setCellFactory(param -> new ListCell<Proceso>() {
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
                    setText(": " + aux.toStringTerciario()+ "\n");
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