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

import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Grupo 1
 */
public class Simulacion {
    
    Random random = new Random();
    boolean fragmentacionExterna;
    boolean fragmentacionInterna;
    Memoria memoriaPrincipal;
    Memoria memoriaRespaldo;
    int tiempo = 0;
    int procesosTerminados = 0;
    ArrayList<Proceso> procesos = new ArrayList<Proceso>();
    ArrayList<Proceso> listaDeProcesos = new ArrayList<Proceso>();

    public Simulacion() {
        this.memoriaPrincipal = new Memoria(2048);
        this.memoriaRespaldo = new Memoria (4096);
        
    }
    
    public void ordernarListaDeProcesos() {
             
        ArrayList<Proceso> desordenada = this.listaDeProcesos;
        ArrayList<Proceso> ordenada = new ArrayList<>();
        
        int prioridad = 9999;
        
        //obtenemos la prioridad mayor buscando el numero más pequeño
        for (Proceso p: desordenada) {
            if (prioridad>p.getPrioridad()) {
                prioridad = p.getPrioridad();   
            }     
        }
        
        //Ordenamos los elementos de la lista comparando su prioridad
        int cont=0;
        while (cont<desordenada.size()) {
            for (int i=0; i<desordenada.size(); i++) {
                if (prioridad==desordenada.get(i).getPrioridad()) {
                    ordenada.add(desordenada.get(i));
                }       
            }
            cont++;
            prioridad++;
        }
        System.out.println("--------");
        for (Proceso x: ordenada) {
            System.out.println(x.getUsos());
        }
        System.out.println("--------");
        
        this.listaDeProcesos = ordenada;
        
    }
    
    public void ordenarListaFIFO()
    {
        if(!listaDeProcesos.isEmpty())
        {
            Proceso proceso = listaDeProcesos.get(0);
            listaDeProcesos.remove(0);
            listaDeProcesos.add(proceso);
        }
        
    }
    
    public void ordernarListaDeProcesosLFU() {
             
        ArrayList<Proceso> desordenada = this.listaDeProcesos;
        ArrayList<Proceso> ordenada = new ArrayList<>();
        
        int prioridad = 9999;
        
        //obtenemos el proceso con mayor numero de usos buscando el numero más pequeño
        for (Proceso p: desordenada) {
            if (prioridad>p.getUsos()) {
                prioridad = p.getUsos();   
            }     
        }
        
        //Ordenamos los elementos de la lista comparando su cantidad de usos
        int cont=0;
        while (cont<desordenada.size()) {
            for (int i=0; i<desordenada.size(); i++) {
                if (prioridad==desordenada.get(i).getUsos()) {
                    ordenada.add(desordenada.get(i));
                }       
            }
            cont++;
            prioridad++;
        }
        System.out.println("--------");
        for (Proceso x: ordenada) {
            System.out.println(x.getUsos());
        }
        System.out.println("--------");
        
        this.listaDeProcesos = ordenada;
        
    }
    
    public void ordernarListaDeProcesosMFU() {
        
        
        ArrayList<Proceso> desordenada = this.listaDeProcesos;
        ArrayList<Proceso> ordenada = new ArrayList<>();
        
        int usos = -1;
        
        //obtenemos el proceso con menor numero de usos buscando el numero más pequeño
        for (Proceso p: desordenada) {
            if (usos<p.getUsos()) {
                usos = p.getUsos();   
            }     
        }
        
        //Ordenamos los elementos de la lista comparando su cantidad de usos
        int cont=0;
        while (cont<desordenada.size()) {
            for (int i=0; i<desordenada.size(); i++) {
                if (usos==desordenada.get(i).getUsos()) {
                    ordenada.add(desordenada.get(i));
                }       
            }
            cont++;
            usos--;
        }
        System.out.println("--------");
        for (Proceso x: ordenada) {
            System.out.println(x.getUsos());
        }
        System.out.println("--------");
        
        this.listaDeProcesos = ordenada;
  
    }
    
    
    
    public void avanzarTiempo()
    {
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            if(!memoriaPrincipal.getCluster(i).isEmpty())
            {
                memoriaPrincipal.getCluster(i).getProceso(0).setUsos(memoriaPrincipal.getCluster(i).getProceso(0).getUsos()+1);
                swapOutFragmeto(memoriaPrincipal.getCluster(i).getProceso(0));
                this.tiempo++;
                return;
            }
            
        }
  /*      for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            if(!memoriaPrincipal.getCluster(i).isEmpty())
            {
                memoriaPrincipal.getCluster(i).getProceso(0).setTiempoLlevado(memoriaPrincipal.getCluster(i).getProceso(0).getTiempoLlevado()+1);
                if(memoriaPrincipal.getCluster(i).getProceso(0).getTiempo()==memoriaPrincipal.getCluster(i).getProceso(0).getTiempoLlevado())
                {
                    memoriaPrincipal.removerProceso(memoriaPrincipal.getCluster(i).getProceso(0));
                }
            }
        }*/
        this.tiempo++;
    }
    
    public void avanzarTiempoSegundaEntrega()
    {
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            if(!memoriaPrincipal.getCluster(i).isEmpty())
            {
                for (int j = 0; j < listaDeProcesos.size(); j++) {
                    if(memoriaPrincipal.getCluster(i).getProceso(0).getNombrePrograma().equals(listaDeProcesos.get(j).getNombrePrograma())) {
                        listaDeProcesos.get(j).setUsos(listaDeProcesos.get(j).getUsos()+1);
                        swapOutFragmeto(memoriaPrincipal.getCluster(i).getProceso(0));
                        this.tiempo++;
                        return;
                    }
                }
              //  memoriaPrincipal.getCluster(i).getProceso(0).setUsos(memoriaPrincipal.getCluster(i).getProceso(0).getUsos()+1);
                
            }
            
        }
  /*      for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            if(!memoriaPrincipal.getCluster(i).isEmpty())
            {
                memoriaPrincipal.getCluster(i).getProceso(0).setTiempoLlevado(memoriaPrincipal.getCluster(i).getProceso(0).getTiempoLlevado()+1);
                if(memoriaPrincipal.getCluster(i).getProceso(0).getTiempo()==memoriaPrincipal.getCluster(i).getProceso(0).getTiempoLlevado())
                {
                    memoriaPrincipal.removerProceso(memoriaPrincipal.getCluster(i).getProceso(0));
                }
            }
        }*/
        this.tiempo++;
    }
    
    public void agregarAListaDeProcesos(Proceso proceso)
    {
        if(!listaDeProcesos.contains(proceso))
        {
            listaDeProcesos.add(proceso);
        }
    }
    
    public void pedirFragmento() 
    {
        if(listaDeProcesos.size() >= 1)
        {
            ArrayList<Proceso> iDs = new ArrayList<Proceso>();
            for (int i = 0; i < memoriaRespaldo.getTamanoMemoria(); i++) 
            {
                if(!memoriaRespaldo.getCluster(i).isEmpty() && memoriaRespaldo.getCluster(i).getProceso(0).getNombrePrograma().equals(listaDeProcesos.get(0).getNombrePrograma()))
                {
                    iDs.add(memoriaRespaldo.getCluster(i).getProceso(0));
                }
            }
            if(iDs.size()>0)
            {
                int tamaño = iDs.size();
                int fragmento = random.nextInt(tamaño);
                swapInFragmeto(iDs.get(fragmento));
            }
        }
    }
        
    public void swapInFragmeto(Proceso proceso)
    {
        for (int i = 0; i < memoriaRespaldo.getTamanoMemoria(); i++) {
            if(!memoriaRespaldo.getCluster(i).isEmpty() && memoriaRespaldo.getCluster(i).getProceso(0).equals(proceso))
            {
                boolean addListo=false;
                boolean emptyListo=false;
                for (int j = 0; j < memoriaPrincipal.getTamanoMemoria(); j++) {
                    if(memoriaPrincipal.getCluster(j).isEmpty())
                    {
                        memoriaPrincipal.getCluster(j).addProceso(proceso);
                        break;
                    }
                }
                for (int j = 0; j < memoriaRespaldo.getTamanoMemoria(); j++) {
                    if(!memoriaRespaldo.getCluster(j).isEmpty() && memoriaRespaldo.getCluster(j).getProceso(0).equals(proceso))
                    {
                        memoriaRespaldo.getCluster(j).limpiarCluster();
                        break;
                    }
                }
                return;
            //    Proceso procesoNuevo = new Proceso (proceso.getID(), proceso.getNombrePrograma(), proceso.getTamañoFragmento(), proceso.getTiempo(), proceso.getPrioridad());
            }
        }
    }
    
    public void verificarCompleto(Proceso proceso)
    {
        for (int i = 0; i < memoriaRespaldo.getTamanoMemoria(); i++) {
            if(!memoriaRespaldo.getCluster(i).isEmpty() && memoriaRespaldo.getCluster(i).getProceso(0).getNombrePrograma().equals(proceso.getNombrePrograma()))
                return;
        }
        for (int i = 0; i < listaDeProcesos.size(); i++) {
            if(listaDeProcesos.get(i).getNombrePrograma().equals(proceso.getNombrePrograma()))
                listaDeProcesos.remove(i);
        }
        
    }
    
    public void swapOutFragmeto(Proceso proceso)
    {
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            if(!memoriaPrincipal.getCluster(i).isEmpty() && memoriaPrincipal.getCluster(i).getProceso(0).getID()==proceso.getID())
            {
         /*       for (int j = 0; j < memoriaRespaldo.getTamanoMemoria(); j++) {
                    if(memoriaRespaldo.getCluster(j).isEmpty())
                    {
                        memoriaRespaldo.getCluster(j).addProceso(proceso);
                        break;
                    }
                }*/
                for (int j = 0; j < memoriaPrincipal.getTamanoMemoria(); j++) {
                    if(!memoriaPrincipal.getCluster(j).isEmpty() && memoriaPrincipal.getCluster(j).getProceso(0).equals(proceso))
                    {
                        memoriaPrincipal.getCluster(j).limpiarCluster();
                        break;
                    } 
                } 

                verificarCompleto(proceso);
                return;
            //    Proceso procesoNuevo = new Proceso (proceso.getID(), proceso.getNombrePrograma(), proceso.getTamañoFragmento(), proceso.getTiempo(), proceso.getPrioridad());
            }
            
        }
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
        int vacios=0;
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            if(memoriaPrincipal.getCluster(i).isEmpty())
                vacios++;
        }
        Proceso procesoCandidato;
        for (int i = 0; i < memoriaPrincipal.getTamanoMemoria(); i++) {
            procesoCandidato = memoriaPrincipal.getCluster(i).getProceso(0);
            if (procesoCandidato!=null && proceso.getPrioridad()<procesoCandidato.getPrioridad() && procesoCandidato.getTamaño()+vacios >= proceso.getTamaño())
            {
                return procesoCandidato;
            }
        }
        return null;
    }
    
    public void removerProceso(Proceso proceso)
    {
        for (int i = 0; i < procesos.size(); i++) 
        {
            if(proceso.getNombrePrograma().equals(procesos.get(i).getNombrePrograma()))
                procesos.remove(i);
        }
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
            if(!memoriaRespaldo.getCluster(i).isEmpty()){
                swapInConFragmentacionExterna(memoriaRespaldo.getCluster(i).getProceso(0), false);
            }
            
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
                        agregarAListaDeProcesos(proceso);
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
