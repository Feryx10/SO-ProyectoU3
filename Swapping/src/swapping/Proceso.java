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

import java.util.Random;
import javafx.scene.paint.Color;



/**
 *
 * @author Grupo 1
 */
public class Proceso {
    private final int ID;    
    private final String nombrePrograma;
    private boolean completado;
    private boolean iniciado;
    private int tamaño,tamañoFragmento, tiempo, cantidadFragmentos, prioridad;    
    private final Color color;
    private int colorHash;

    public Proceso(int ID, String nombrePrograma, int tamaño, int tiempo, int prioridad) {
        this.ID = ID;
        this.nombrePrograma = nombrePrograma;
        this.tamaño = tamaño;
        this.tiempo = tiempo;
        this.completado = false;
        this.iniciado = false;
        this.prioridad = prioridad;
        calcularCantidadFragmentos();
        calcularPesoFramgento();
        this.colorHash = (int) Math.sqrt(nombrePrograma.hashCode()*nombrePrograma.hashCode());
        while(this.colorHash>255)
            this.colorHash = this.colorHash/2;
        this.color = Color.rgb(this.colorHash, this.colorHash, this.colorHash);
    }
    
    public void calcularCantidadFragmentos()
    {
        int valor = (int) Math.ceil(tamaño/256);
        this.cantidadFragmentos = valor;
    }
    
    public void calcularPesoFramgento()
    {
        tamañoFragmento=256;
    }
    
    public int getID() {
        return ID;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }
    
    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }    

    @Override
    public String toString() {
        return "{" + "ID=" + ID + ", Nombre=" + nombrePrograma + ", Kb=" + tamaño + '}';
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public int getTamañoFragmento() {
        return tamañoFragmento;
    }

    public void setTamañoFragmento(int tamañoFragmento) {
        this.tamañoFragmento = tamañoFragmento;
    }

    public int getCantidadFragmentos() {
        return cantidadFragmentos;
    }

    public void setCantidadFragmentos(int cantidadFragmentos) {
        this.cantidadFragmentos = cantidadFragmentos;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
    
    public Color getColor(){
        return color;
    }   
           
}