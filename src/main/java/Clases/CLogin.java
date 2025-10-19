/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;


import com.mycompany.miposs.FormMenuPrincipal;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.ResultSet;


public class CLogin {
    
    // CREAMOS EL METODO DE VALIDACION DE CREDENCIALES DE ACCESO
    public void ValidarUsuario(JTextField usuario, JPasswordField contrasena){
    
        try {
            ResultSet rs = null;
            PreparedStatement ps = null;
            
            String consulta = "SELECT * FROM usuarios WHERE usuarios.Usuario =(?) AND usuarios.Contrasena=(?);";
            
            Clases.LoginConexion objetoConexion = new Clases.LoginConexion();
            ps = objetoConexion.establecerConexion().prepareStatement(consulta);
            
            String contra = String.valueOf(contrasena.getPassword());
            
            ps.setString(1, usuario.getText());
            ps.setString(2, contra);
            
            rs =  ps.executeQuery();
          
                if (rs.next()){
          
                    JOptionPane.showMessageDialog(null, "Acceso de usuario autorizado");
                    Formularios.MenuPrincipal objetoMenuPrincipal = new Formularios.MenuPrincipal();
                    objetoMenuPrincipal.setVisible(true);
                    
                    //FormMenuPrincipal objetoMenu = new  FormMenuPrincipal ();
                    //objetoMenu.setVisible(true);

                }
                else {
                         JOptionPane.showMessageDialog(null, "X Usuario incorrecto, vuelva a intentarlo");
                }
           
          
        } catch (Exception e) {
            
                    JOptionPane.showMessageDialog(null, "X Ocurrio un Error: "+e.toString());
        }
    }
    
}
