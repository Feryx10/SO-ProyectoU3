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
import java.util.Scanner;

/**
 *
 * @author Grupo 1
 */
public class Intercambio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        int seleccion = 10;
        String ingreso = "";
        Memoria memoriaPrincipal;
        Memoria memoriaSecundaria;        
        ArrayList <Proceso> procesos = new ArrayList <>();
        Scanner lector = new Scanner(System.in);
        while(true){
            switch(seleccion){         
                default:{
                    System.out.println("\f");
                    System.out.println(" Swapping Simulator");
                    System.out.println("-----------------------------------");
                    System.out.println("[1]- Configuraciones.");
                    System.out.println("[2]- Iniciar demostracion.");
                    System.out.println("");
                    System.out.println("[0]- Salir.");
                    ingreso = lector.next();
                    try{                        
                        seleccion = Integer.parseInt(ingreso);
                    }catch(NumberFormatException e){
                        System.out.println("\f");
                        System.out.println(" Swapping Simulator");
                        System.out.println("-----------------------------------");
                        System.out.println(" Ingreso no valido. Presione una tecla para continuar...");   
                        ingreso = lector.next();
                    }                    
                    break;
                }
                case 0:{     
                     System.exit(0);                                        
                }
                case 1:{
                    while(!ingreso.equals("S") && !ingreso.equals("N")){
                        System.out.println("\f");
                        System.out.println(" Swapping Simulator");
                        System.out.println("-----------------------------------");
                        System.out.println("[1]- Configuraciones");
                        System.out.println("");
                        System.out.print("  ¿ Desea una configuración predeterminada ? (S/N) : ");
                        ingreso = lector.next().toUpperCase();
                        System.out.println(ingreso);
                    } 
                    if(ingreso.equals("S")){
                        memoriaPrincipal = new Memoria(8);
                        memoriaSecundaria = new Memoria(8); 
                        char nombreAleatorio = 97;
                        for (int i = 0; i < 4; i++) {
                            Proceso aux = new Proceso (i, String.valueOf(nombreAleatorio++), 2, 2);
                            procesos.add(aux);
                        }
                        seleccion = 10;
                        break;                            
                    }                                           
                    System.out.print("  Ingrese tamaño para memorias : ");
                    ingreso = lector.next();
                    System.out.print("  Ingrese numero de procesos : ");
                    ingreso = lector.next();
                    seleccion = 10;
                    break;
                }
                case 2:{
                    System.out.println("\f");
                    System.out.println(" Swapping Simulator");
                    System.out.println("-----------------------------------");
                    System.out.println("[2]- Iniciar demostracion.");
                    System.out.println("");
                    ingreso = lector.next();
                    seleccion = 10;
                    break;
                }
            }
        }       
    }
    
    public boolean swapIn(int id){
        
        return true;        
    }
    
    public boolean swapOut (int id){
        
        return true;
    }
}
