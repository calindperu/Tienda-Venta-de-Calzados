/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class LoginConexion {
    
        Connection conectar;
        
        String usuario="cgamarra";
        String clave="Calino$.2025";
        String bd="login";
        String ip="localhost";
        String puerto="3306";
        
        // creamos la cadena de conexion
        String cadena ="jdbc:mysql://"+ip+":"+puerto+"/"+bd;
        
        public Connection establecerConexion(){

        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,clave);
            
            //JOptionPane.showMessageDialog(null, "Se conecto correctamente a la BD");
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "X Ocurrio el siguiente error de conexion con la BD "+e.toString());
        }
            return conectar;  
    }           
}