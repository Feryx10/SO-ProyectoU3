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

import java.util.ArrayList;

/**
 *
 * @author Grupo 1
 */
public class Simulacion {
    
    boolean fragmentacionExterna;
    boolean fragmentacionInterna;
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
            i=buscarInicioProceso();
            if(i!=-1)
            {
                if(fragmentacionExterna)
                {
                    swapInConFragmentacionExterna(procesos.get(i), true);
                }
                else
                {
                    swapInSinFragmentacionExterna(procesos.get(i), true);
                }
            }
        }
    }
    
    public int buscarInicioProceso()//Busca los procesos que deben iniciar en this.tiempo
    {
        for (int i = 0; i < procesos.size(); i++) {
            if(procesos.get(i).getTiempo() <= tiempo && !procesos.get(i).isIniciado())
                return i;
        }
        return -1;
    }
    
    public void swapInConFragmentacionExterna(Proceso proceso, boolean nuevo) //Busca N espacios contiguos para procesos con fragmentacion externa
    {                                                         
        int primerCluster = -1;
        int clusters = 0;
        System.out.println("Fragmentos proceso: "+proceso.getCantidadFragmentos());
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            
            if(memoriaPrincipal.getCluster(i).getEspacioDisponible()==256)//Ojo: tamaño estandar de un cluster 256mb
            {
                
                if(primerCluster==-1)
                {
                    primerCluster=i;
                    clusters=1;
                }
                else
                    clusters+=1;
                System.out.println("clusters: "+clusters +", primerCluster: "+ primerCluster);
                if(clusters==proceso.getCantidadFragmentos())
                {
                    System.out.println(proceso.getCantidadFragmentos());
                    for (int j = 0; j < clusters; j++) {
                        Proceso procesoNuevo = new Proceso(j, proceso.getNombrePrograma(), 256, proceso.getTiempo());
                        memoriaPrincipal.getCluster(primerCluster+j).addProceso(procesoNuevo);
                        System.out.println(memoriaPrincipal.getCluster(primerCluster).getProceso(procesoNuevo.getNombrePrograma()).getNombrePrograma());
                    }
                    if(!nuevo)
                    {
                        for (int j = 0; j < memoriaRespaldo.getTamanoMemoria(); j++) {
                        if(memoriaRespaldo.getCluster(i).getProceso(proceso.getNombrePrograma()) != null)
                            memoriaRespaldo.getCluster(i).limpiarCluster();
                        }
                    }
                    return;
                }
            }
            else
                primerCluster = -1;
        }
    }
    
    public void swapInSinFragmentacionExterna(Proceso proceso, boolean nuevo)  //Hace swap in para procesos que no requieren espacios contiguos
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
                    Proceso procesoNuevo = new Proceso(j, proceso.getNombrePrograma(), proceso.getTamaño(), proceso.getTiempo());
                    memoriaPrincipal.getCluster(i).addProceso(procesoNuevo);
                }
                if(!nuevo)
                {
                    for (int j = 0; j < memoriaRespaldo.getTamanoMemoria(); j++) {
                    if(memoriaRespaldo.getCluster(j).getProceso(proceso.getNombrePrograma()) != null)
                        memoriaRespaldo.getCluster(j).limpiarCluster();
                    }
                }
                return;
            }
        }
        System.out.println("no hay espacio");
    }
    
    public void swapOutConFragmentacionExterna(Proceso proceso, boolean nuevo) //Busca N espacios contiguos para procesos con fragmentacion externa
    {                          
        int primerCluster = -1;
        int clusters = 0;
        System.out.println("Fragmentos proceso: "+proceso.getCantidadFragmentos());
        for (int i = 0; i < memoriaRespaldo.getTamanoMemoria(); i++) {
            
            if(memoriaRespaldo.getCluster(i).getEspacioDisponible()==256)//Ojo: tamaño estandar de un cluster 256mb
            {
                if(primerCluster==-1)
                {
                    primerCluster=i;
                    clusters=1;
                }
                else
                    clusters+=1;
                System.out.println("clusters: "+clusters +", primerCluster: "+ primerCluster);
                if(clusters==proceso.getCantidadFragmentos())
                {
                    for (int j = 0; j < clusters; j++) {
                        Proceso procesoNuevo = new Proceso(j, proceso.getNombrePrograma(), 256, proceso.getTiempo());
                        memoriaRespaldo.getCluster(primerCluster+j).addProceso(procesoNuevo);
                     //   System.out.println(memoriaRespaldo.getCluster(primerCluster).getProceso(procesoNuevo.getNombrePrograma()).getNombrePrograma());
                    }
                    if(!nuevo)
                    {
                        for (int j = 0; j < memoriaPrincipal.getTamanoMemoria(); j++) {
                        if(memoriaPrincipal.getCluster(j).getProceso(proceso.getNombrePrograma()) != null)
                            memoriaPrincipal.getCluster(j).limpiarCluster();
                        
                        }
                    }                    
                    return;
                }
            }
            else
                primerCluster = -1;
        }
    }
    
    public void swapOutSinFragmentacionExterna(Proceso proceso, boolean nuevo)  //Hace swap in para procesos que no requieren espacios contiguos
    {
        int [] clustersDisponibles = new int [proceso.getCantidadFragmentos()];
        int puntero=0;
        
        for (int i = 0; memoriaRespaldo.getTamanoMemoria() < 10; i++) {
            if(memoriaRespaldo.getCluster(i).getEspacioDisponible()==256)
            {
                clustersDisponibles[puntero]=i;
                puntero++;
            }
            if(puntero+1==clustersDisponibles.length)
            {
                for (int j = 0; clustersDisponibles.length < 10; j++) {
                    Proceso procesoNuevo = new Proceso(j, proceso.getNombrePrograma(), 256, proceso.getTiempo());
                    memoriaRespaldo.getCluster(i).addProceso(procesoNuevo);
                }
                if(!nuevo)
                {
                    for (int j = 0; j < memoriaPrincipal.getTamanoMemoria(); j++) {
                        if(memoriaPrincipal.getCluster(j).getProceso(proceso.getNombrePrograma()) != null)
                            memoriaPrincipal.getCluster(j).limpiarCluster();
                        }
                }
                return;
            }
        }
        System.out.println("no hay espacio");
    }
    
}
