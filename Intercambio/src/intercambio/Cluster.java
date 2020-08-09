/*
 * Copyright (C) 2020 Luciano
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
 * @author Luciano
 */
public class Cluster {
    
    private int tamaño;
    private int espacioDisponible;
    private ArrayList<Proceso> procesos = new ArrayList<Proceso>();
    private boolean ocupado = false;

    public Cluster(int tamaño) {
        this.tamaño = tamaño;
        this.espacioDisponible = tamaño;
    }

    
    
    public int getTamano()
    {
        return tamaño;
    }
    
    public void setTamano(int i)
    {
        this.tamaño=i;
    }
    
    public Proceso getProceso (String nombre)
    {
        for (int i = 0; i < procesos.size(); i++) {
            if(procesos.get(i).getNombrePrograma().equals(nombre))
                return procesos.get(i);
        }
        return null;
    }
    
    public void addProceso (Proceso proceso){
        procesos.add(proceso);
        calcularEspacio();
    }
    
    public void calcularEspacio()
    {
        int ocupado=0;
        for (int i = 0; i < procesos.size(); i++) {
            ocupado+=procesos.get(i).getTamaño();            
        }
        this.espacioDisponible=tamaño-ocupado;
    }
    
    public int getEspacioDisponible()
    {
        return this.espacioDisponible;
    }
    
}


