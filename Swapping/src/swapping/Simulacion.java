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
    ArrayList<Proceso> procesos = new ArrayList<Proceso>();

    public Simulacion() {
        this.memoriaPrincipal = new Memoria(4096);
        this.memoriaRespaldo = new Memoria (4096);
        
    }
    
    public void avanzarTiempo()
    {
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            if(!memoriaPrincipal.getCluster(i).isEmpty())
            {
                memoriaPrincipal.getCluster(i).getProceso(0).setTiempoLlevado(memoriaPrincipal.getCluster(i).getProceso(0).getTiempoLlevado()+1);
                if(memoriaPrincipal.getCluster(i).getProceso(0).getTiempo()==memoriaPrincipal.getCluster(i).getProceso(0).getTiempoLlevado())
                {
                    memoriaPrincipal.removerProceso(memoriaPrincipal.getCluster(i).getProceso(0));
                }
            }
        }
        this.tiempo++;
    }
    
    public void agregarProceso(Proceso proceso)
    {
        procesos.add(proceso);
        System.out.println(procesos.size());
    }
    
    public void limpiarLista()
    {
        
    }
    
    
    public int buscarInicioProceso()//Busca los procesos que deben iniciar en this.tiempo
    {
        for (int i = 0; i < procesos.size(); i++) {
            if(procesos.get(i).getTiempo() <= tiempo && !procesos.get(i).isIniciado())
                return i;
        }
        return -1;
    }
    
    public Proceso buscarMenorPrioridadSinFragmentacion(Proceso proceso)
    {
        Proceso procesoCandidato;
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            procesoCandidato = memoriaPrincipal.getCluster(i).getProceso(0);
            if (proceso.getPrioridad()<procesoCandidato.getPrioridad() && procesoCandidato.getTamaño() >= proceso.getTamaño())
            {
                return procesoCandidato;
            }
        }
        return null;
    }
    
    public Proceso buscarMenorPrioridadConFragmentacion(Proceso proceso)
    {
        int clusters = 0;
        Proceso procesoCandidato=null;
        Proceso siguienteProceso;
        System.out.println("Fragmentos proceso: "+proceso.getCantidadFragmentos());
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            System.out.println("TamañoMemoria: "+memoriaPrincipal.getTamanoMemoria()+" puntero: "+i);
            if(memoriaPrincipal.getCluster(i).isEmpty())
            {
                clusters++;
                System.out.println("Clusters: "+clusters);
            }                
            else
            {
                System.out.println("Clusters: "+clusters);
                siguienteProceso=memoriaPrincipal.getCluster(i).getProceso(0);
                System.out.println("Proceso 1: "+proceso.getNombrePrograma()+" Tamaño: "+proceso.getTamaño()+" Prioridad: "+proceso.getPrioridad());
                System.out.println("Proceso 2: "+siguienteProceso.getNombrePrograma()+" Tamaño: "+siguienteProceso.getTamaño()+" Prioridad: "+siguienteProceso.getPrioridad());
                
                if(siguienteProceso.getTamaño()>=proceso.getTamaño() && siguienteProceso.getPrioridad()>proceso.getPrioridad())//Ojo: tamaño estandar de un cluster 256mb
                {
                    System.out.println("xd");
                    if(procesoCandidato==null)
                    {
                        procesoCandidato=siguienteProceso;
                        clusters=1;
                    }
                    else if(siguienteProceso.getNombrePrograma().equals(procesoCandidato.getNombrePrograma()))
                        clusters+=1;
                    else
                        procesoCandidato=null;
                }
                else
                    procesoCandidato=null;
            }
            
            if(clusters==proceso.getCantidadFragmentos())
                {
                    return procesoCandidato;
                }
        }
        return null;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
    
    public void verificarPrioridad()
    {
        for (int i = 0; i < memoriaRespaldo.getTamanoMemoria(); i++) {
            if(!memoriaRespaldo.getCluster(i).isEmpty())
                buscarPrioridadConFragmentacion(memoriaRespaldo.getCluster(i).getProceso(0));
        }
    }
    
    public void swapInConFragmentacionExterna(Proceso proceso, boolean nuevo) //Busca N espacios contiguos para procesos con fragmentacion externa
    {                                       
        int primerCluster = -1;
        int clusters = 0;
       // System.out.println("Fragmentos proceso: "+proceso.getCantidadFragmentos());
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
         //       System.out.println("clusters: "+clusters +", primerCluster: "+ primerCluster);
                if(clusters==proceso.getCantidadFragmentos())
                {
                   // System.out.println(proceso.getCantidadFragmentos());
                    for (int j = 0; j < clusters; j++) {
                        Proceso procesoNuevo = new Proceso(j, proceso.getNombrePrograma(), proceso.getTamaño(), proceso.getTiempo(), proceso.getPrioridad());
                        memoriaPrincipal.getCluster(primerCluster+j).addProceso(procesoNuevo);
                      //  System.out.println(memoriaPrincipal.getCluster(primerCluster).getProceso(procesoNuevo.getNombrePrograma()).getNombrePrograma());
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
            else
                primerCluster = -1;
        }
        buscarPrioridadConFragmentacion(proceso);
    }
    
    public void swapInSinFragmentacionExterna(Proceso proceso, boolean nuevo)  //Hace swap in para procesos que no requieren espacios contiguos
    {
        int [] clustersDisponibles = new int [proceso.getCantidadFragmentos()];
        int puntero=0;
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            if(memoriaPrincipal.getCluster(i).getEspacioDisponible()==256)
            {
                clustersDisponibles[puntero]=i;
                puntero++;
            }
           // System.out.println(puntero);
            if(puntero==proceso.getCantidadFragmentos())
            {
                for (int j = 0; j < clustersDisponibles.length; j++) {
                    Proceso procesoNuevo = new Proceso(j, proceso.getNombrePrograma(), proceso.getTamaño(), proceso.getTiempo(), proceso.getPrioridad());
                    memoriaPrincipal.getCluster(clustersDisponibles[j]).addProceso(procesoNuevo);
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
        buscarPrioridadSinFragmentacion(proceso);
    }
    
    
    
    public void swapOutConFragmentacionExterna(Proceso proceso, boolean nuevo) //Busca N espacios contiguos para procesos con fragmentacion externa
    {  
        int primerCluster = -1;
        int clusters = 0;
    //    System.out.println("Fragmentos proceso: "+proceso.getCantidadFragmentos());
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
          //      System.out.println("clusters: "+clusters +", primerCluster: "+ primerCluster);
                if(clusters==proceso.getCantidadFragmentos())
                {
                    for (int j = 0; j < clusters; j++) {
                        Proceso procesoNuevo = new Proceso(j, proceso.getNombrePrograma(), proceso.getTamaño(), proceso.getTiempo(), proceso.getPrioridad());
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
        
        for (int i = 0; i < memoriaRespaldo.getTamanoMemoria(); i++) {
            if(memoriaRespaldo.getCluster(i).getEspacioDisponible()==256)
            {
                clustersDisponibles[puntero]=i;
                puntero++;
            }
            if(puntero==clustersDisponibles.length)
            {
                for (int j = 0; j < clustersDisponibles.length; j++) {
                    Proceso procesoNuevo = new Proceso(j, proceso.getNombrePrograma(), proceso.getTamaño(), proceso.getTiempo(), proceso.getPrioridad());
                    memoriaRespaldo.getCluster(clustersDisponibles[j]).addProceso(procesoNuevo);
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
    
    public void buscarPrioridadConFragmentacion(Proceso proceso)
    {
        Proceso procesoSwapOut = buscarMenorPrioridadConFragmentacion(proceso);
        if(procesoSwapOut!=null)
        {
            swapOutConFragmentacionExterna(procesoSwapOut, false);
            swapInConFragmentacionExterna(proceso, false);
            return;
        }
        return;
    }
    
    public void buscarPrioridadSinFragmentacion(Proceso proceso)
    {
        Proceso procesoSwapOut = buscarMenorPrioridadSinFragmentacion(proceso);
        if(procesoSwapOut!=null)
        {
            swapOutSinFragmentacionExterna(procesoSwapOut, false);
            swapInSinFragmentacionExterna(proceso, false);
            return;
        }
        return;
    }
    
}
