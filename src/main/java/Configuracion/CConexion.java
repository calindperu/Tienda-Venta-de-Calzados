/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

/*  import com.sun.source.tree.TryTree;  */
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class CConexion {
    
    Connection conectar = null;
    
    String usuario="cgamarra";
    String clave="Calino$.2025";
    String bd="calzados";
    String ip="localhost";
    String puerto="3306";
    
    // creamos la cadena de conexion
    String cadena ="jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    // creamos una funcion que nos devuelva una variable de tipo conexion
    // que nos permita abrir y cerrar la conexion a la BD
    
   public Connection estableceConexion(){
      
    try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario,clave);
            
            // Mostrar mensaje exitoso de conexion a BD
            //JOptionPane.showMessageDialog(null,"CONEXION EXITOSA A LA BD");
	} 

    catch (HeadlessException | ClassNotFoundException | SQLException e) {
            
            // Mostrar mensaje de error de conexion a BD
            JOptionPane.showMessageDialog(null,"NO SE CONECTO A LA BD: "+e.toString());
       }

            return conectar;
        }
    
    public void cerrarConexion(){
            
    try {
	if (conectar!=null && !conectar.isClosed()){
		conectar.close();
                //JOptionPane.showMessageDialog(null,"LA CONEXION FUE CERRADA");
            }
        } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null,"NO SE LOGRO CERRAR LA CONEXION: "+e.toString());
            }
    }
           
  }

    

