/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

public class ModeloCliente {    
    
    int idCliente;
    String nombres;
    String apPaterno;
    String apMaterno;
    
        
    /* insertamos los valores de la tabla: Cliente de la BD
    sombreamos todos los nombres y hacemos click derecho y damos clic en: Insert Code
    seguidamente damos clic en: Getter and Setter
    luego seleccionamos todas las columnas y ledamos al boton: Generate
    nos crea todos los Setter y Getter de nuestro modelo de tabla: Cliente
    */
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setapPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

}
