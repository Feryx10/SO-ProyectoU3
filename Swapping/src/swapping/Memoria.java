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

/**
 *
 * @author Grupo 1
 */
public class Memoria {
    private Cluster [] memoria;
    private int tamanoMemoria;

    public Memoria(int tamanoMemoria) {
        this.memoria = new Cluster [tamanoMemoria/256];
        for (int i = 0; i < this.memoria.length; i++) {
            this.memoria[i] = new Cluster(256);
        }
        this.tamanoMemoria = tamanoMemoria/256;  
    }

    public Proceso getProceso(String nombre) {
        Proceso proceso = null;
        for (int i = 0; i < this.tamanoMemoria; i++) 
        {
            proceso = memoria[i].getProceso(nombre);
            if(proceso!=null)
                return proceso;        
        }
        System.out.println("Error: No existe el proceso '"+nombre+"'");
        return null;
        
    }
    
    public Cluster getCluster(int i)
    {
        return memoria[i];
    }
    
    public Cluster[] getClusters()
    {
        return memoria;
    }
    
    
/*
    public ArrayList <Proceso> getProcesos(String nombre) {
        ArrayList <Proceso> aux = new ArrayList <>();
        for (int i = 0; i < this.tamanoMemoria; i++) 
            if(this.memoria[i].getNombrePrograma().equals(nombre))
                aux.add(this.memoria[i]);
        if(aux.isEmpty())
            return null;         
        return aux;          
    }
*/
    
    public boolean insertProceso(Proceso proceso) {
        for (int i = 0; i < this.tamanoMemoria; i++) {
            if(this.memoria[i].getEspacioDisponible()>proceso.getTamaño()){ // get tamaño libre
                this.memoria[i].addProceso(proceso);
                return true;
            }
        }
        System.out.println("No hay espacio suficiente en la memoria");
        return false;
    }

    public int getTamanoMemoria() {
        return tamanoMemoria;
    }
    /*
    public int getMemoriaDisponible(){
        for (int i = 0; i < memoria.length; i++) {
            
        }
    }*/

    public boolean setTamanoMemoria(int tamanoMemoria, boolean reset) {
        if(reset){
            this.resetMemoria();
            this.memoria = new Cluster [tamanoMemoria];
            this.tamanoMemoria = tamanoMemoria;
            return true;
        }
        if(tamanoMemoria >= this.tamanoMemoria){
            if(reset)
                this.resetMemoria();                     
            Cluster [] aux = this.memoria;
            this.memoria = new Cluster [tamanoMemoria];
            System.arraycopy(aux, 0, this.memoria, 0, aux.length);
            this.tamanoMemoria = tamanoMemoria;
        }else{
            return false;
        }        
        return true;
    }
    
    public void resetMemoria() {
        this.memoria = new Cluster [this.tamanoMemoria];        
    }   
    
    public String mostrarMemoria() {
        String aux ="";
        for (int i = 0; i < this.memoria.length; i++) {
            aux="     | "+this.memoria[i].toString()+" |\n";                                 
        }
        return aux;     
    }   
    
    @Override
    public String toString() {
        return "Memoria{Tamano de Memoria=" + this.tamanoMemoria + '}'+"\n"+this.mostrarMemoria();
    }
    
    public void removerProceso(Proceso proceso)
    {
        Proceso procesoCandidato;
        for (int i = 0; i < this.tamanoMemoria; i++) {
            procesoCandidato = this.memoria[i].getProceso(0);
            if(procesoCandidato!= null && procesoCandidato.getNombrePrograma().equals(proceso.getNombrePrograma()))
                this.memoria[i].limpiarCluster();
        }
    }
    
}