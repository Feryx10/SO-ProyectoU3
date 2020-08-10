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
package intercambio;

import java.util.ArrayList;

/**
 *
 * @author Grupo 1
 */
public class Simulacion {
    
    Memoria memoriaPrincipal;
    Memoria memoriaRespaldo;
    int tiempo = 0;
    int procesosTerminados = 0;
    ArrayList<Proceso> procesos = new ArrayList<>();

    public Simulacion() {
        this.memoriaPrincipal = new Memoria(4096);
        this.memoriaRespaldo = new Memoria (4096);
    }
    
    public void iniciarSimulacion()
    {
        int i;
        while(procesosTerminados-1!=procesos.size())
        {
            i=buscarInicioProceeso();
            if(i!=-1)
            {
                
            }
        }
    }
    
    public int buscarInicioProceeso()//Busca los procesos que deben iniciar en this.tiempo
    {
        for (int i = 0; i < procesos.size(); i++) {
            if(procesos.get(i).getTiempo() <= tiempo && !procesos.get(i).isCompletado())
                return i;
        }
        return -1;
    }
    
    public int satisfaceFragmentacionExterna(Proceso proceso) //Busca N espacios contiguos para procesos cuando la fragmentacion externa no aplica
    {                                                         //Si encuentra un grupo de clusters adecuado retorna el indice del primero, sino encuentra retorna -1
        int primerCluster = -1;
        int clusters = 0;
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            
            if(memoriaPrincipal.getCluster(i).getEspacioDisponible()==256)//Ojo: tamaño estandar de un cluster 256kb
            {
                if(primerCluster==-1)
                {
                    primerCluster=i;
                    clusters=1;
                }
                else
                    clusters+=1;
                if(clusters==proceso.getCantidadFragmentos())
                    return i;
            }
            primerCluster = -1;
            
        }
        return -1;
    }
    
    public void swapInSinFragmentacionExterna(Proceso proceso)  //Hace swap in para procesos que no requieren espacios contiguos
    {
        int [] clustersDisponibles = new int [proceso.getCantidadFragmentos()];
        int puntero=0;
        
        for (int i = 0; memoriaPrincipal.getTamanoMemoria() < 10; i++) {
            if(memoriaPrincipal.getCluster(i).getEspacioDisponible()==256)
            {
                clustersDisponibles[puntero]=i;
                puntero++;
            }
            if(puntero+1==clustersDisponibles.length)
            {
                for (int j = 0; clustersDisponibles.length < 10; j++) {
                    memoriaPrincipal.getCluster(i).addProceso(proceso);                    
                    return;
                }
            }
        }
        System.out.println("no hay espacio");
    }
    
}
