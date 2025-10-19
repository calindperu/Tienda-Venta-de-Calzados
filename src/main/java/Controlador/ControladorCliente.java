/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class ControladorCliente {
    
    public void MostrarCliente(JTable tablaTotalClientes){

	Configuracion.CConexion objetoConexion = new Configuracion.CConexion();

	Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();
        
     	DefaultTableModel modelo = new DefaultTableModel();

        String sql ="";
	
	modelo.addColumn("id");
	modelo.addColumn("Nombres");
	modelo.addColumn("apPaterno");
	modelo.addColumn("apMaterno");
	
	tablaTotalClientes.setModel(modelo);
        
        sql = "SELECT cliente.idcliente, cliente.nombres, cliente.appaterno, cliente.apmaterno FROM cliente";

        try {
    // Código que podría lanzar una excepción
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){            
                objetoCliente.setIdCliente(rs.getInt("idcliente"));
                objetoCliente.setNombres(rs.getString("nombres"));   
                objetoCliente.setapPaterno(rs.getString("appaterno"));
                objetoCliente.setApMaterno(rs.getString("apmaterno"));
                    
                modelo.addRow(new Object[]{objetoCliente.getIdCliente(),objetoCliente.getNombres(),objetoCliente.getApPaterno(),objetoCliente.getApMaterno()});     
                }
            
            tablaTotalClientes.setModel(modelo);
                
        } catch (SQLException e) {
    // Código para manejar la excepción
        JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR CLIENTE "+e.toString());
        } 
        finally {
    // Código que se ejecuta siempre, ocurra o no una excepción
            objetoConexion.cerrarConexion();
        }       
        
	} 
    
  /* CREAMOS METODO PARA AGREGAR CLIENTES  */
    public void AgregarCliente(JTextField nombres,JTextField appaterno,JTextField apmaterno ){
        
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
	Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();

	String consulta = "insert into cliente (nombres, appaterno, apmaterno) values (?,?,?);";
        
    try {
            objetoCliente.setNombres(nombres.getText());
            objetoCliente.setapPaterno(appaterno.getText());
            objetoCliente.setApMaterno(apmaterno.getText());
            
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoCliente.getNombres());
            cs.setString(2, objetoCliente.getApPaterno());
            cs.setString(3, objetoCliente.getApMaterno());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null,"SE GUARDO CORRECTAMENTE");
            
        } catch (Exception e) {
            /* MOSTRAMOS UN MENSAJE DE ALERTA DE ERROR DE CONEXION DESCRIBIENDO EL TIPO DE ERROR EN VARIABLE: e */
            JOptionPane.showMessageDialog(null,"ERROR AL GUARDAR REGISTRO "+e.toString());
        }
        finally {
            /* CERRAMOS LA CONEXION PARA EVITAR VARIAS CONEXIONES ABIERTAS*/
            objetoConexion.cerrarConexion();
        }
    
    }
             /* CREAMOS METODO SELECCIONAR REGISTROS DE CLIENTE */
            public void Seleccionar(JTable totalcliente, JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno){
            
                int fila = totalcliente.getSelectedRow();
                
               try {    
                       if (fila >=0) {
                          id.setText(totalcliente.getValueAt(fila, 0).toString());
                          nombres.setText(totalcliente.getValueAt(fila, 1).toString());
                          appaterno.setText(totalcliente.getValueAt(fila, 2).toString());
                          apmaterno.setText(totalcliente.getValueAt(fila, 3).toString());                        
               
                       }      
                        
                } catch (Exception e) {                    
                    JOptionPane.showMessageDialog(null, "ERROR AL SELECCIONAL REGISTRO "+e.toString());
                }
               
            }
            
            /* CREAMOS METODO PARA MODIFICAR REGISTRO DEL CLIENTE SELECCIONADO */
            public void ModificarCliente(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) throws SQLException{
            
                Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
                Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();               
                           
             String consulta = "UPDATE cliente SET cliente.nombres = ?, cliente.appaterno = ?, cliente.apmaterno = ? WHERE cliente.idcliente = ?";
                
                try {
                  objetoCliente.setIdCliente(Integer.parseInt(id.getText()));
                  objetoCliente.setNombres(nombres.getText());   
                  objetoCliente.setapPaterno(appaterno.getText());
                  objetoCliente.setApMaterno(apmaterno.getText());
                  
                  CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);

                        cs.setString(1, objetoCliente.getNombres());
                        cs.setString(2, objetoCliente.getApPaterno());
                        cs.setString(3, objetoCliente.getApMaterno());
                        cs.setInt(4, objetoCliente.getIdCliente());
               
                  cs.execute();
                  
                  JOptionPane.showMessageDialog(null, "REGISTRO DE MODIFICO CORRECTAMENTE");
                    
                } catch (Exception e) {
                     
                  JOptionPane.showMessageDialog(null, "❌ Ocurrió un error al modificar el registro: " + e.toString());
                     
                } finally {
                    objetoConexion.cerrarConexion();
                }
             }
   
                /* CREAMOS EL METODO LIMPIAR CAMPOS DE REGISTROS PARA BLANQUEAR LOS TEXFIELDs */
                public void LimpiarCamposClientes(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno){
                
                    id.setText("");
                    nombres.setText("");
                    appaterno.setText("");
                    apmaterno.setText("");                   
                    
                }
                
                /* CREAMOS METODO: ELIMINAR CLIENTE DE LA BD. */
                public void EliminarClientes(JTextField id){
                    
                Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
                Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();  
                
                String consulta ="DELETE FROM cliente WHERE cliente.idcliente=?;";
                
                    try {
                        objetoCliente.setIdCliente(Integer.parseInt(id.getText()));
                        CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
                        cs.setInt(1, objetoCliente.getIdCliente());
                        
                        cs.execute();
                        
                        JOptionPane.showMessageDialog(null, "EL REGISTRO DE ELIMINO CORRECTAMENTE");
                        
                   
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "X ERROR! NO SE ELIMINO EL REGISTRO "+e.toString());
                    } finally {
                        objetoConexion.cerrarConexion();
                    }
                    
                }
                
              
  }               
