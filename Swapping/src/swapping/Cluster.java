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
public class Cluster {
    
    private int tamaño;
    private int espacioDisponible;
    private final ArrayList<Proceso> procesos = new ArrayList<>();
    private boolean ocupado;

    public Cluster(int tamaño) {
        this.tamaño = tamaño;
        this.espacioDisponible = tamaño;
        this.ocupado = false;
    }    
    
    public int getTamano()
    {
        return tamaño;
    }
    
    public void setTamano(int i)
    {
        this.tamaño = i;
    }
    
    public Proceso getProceso (String nombre)
    {
        for (int i = 0; i < procesos.size(); i++) {
            if(procesos.get(i).getNombrePrograma().equals(nombre))
                return procesos.get(i);
        }
        return null;
    }
    
    public int getEspacioDisponible()
    {
        return this.espacioDisponible;
    }
    
    public void addProceso (Proceso proceso){
        procesos.add(proceso);
        calcularEspacio();
    }    
    
    private void calcularEspacio()
    {
        int lleno = 0;
        for (int i = 0; i < procesos.size(); i++) {
            lleno += procesos.get(i).getTamaño();            
        }
        this.espacioDisponible = tamaño - lleno;
        if(this.espacioDisponible != tamaño)
            this.ocupado = true;
    }    

    public void limpiarCluster()
    {
        procesos.clear();
        this.espacioDisponible=this.tamaño;
    }
    
    @Override
    public String toString() {
        return "ED= " + this.espacioDisponible + ", P= " + this.procesos.toString() + ", O= " + this.ocupado;
    }   

    
}


