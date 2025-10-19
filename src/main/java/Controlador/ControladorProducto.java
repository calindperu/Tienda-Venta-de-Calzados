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


public class ControladorProducto {
    
        public void MostrarProductos(JTable tablaTotalProductos){

	Configuracion.CConexion objetoConexion = new Configuracion.CConexion();

	Modelos.ModeloProducto objetoProducto = new Modelos.ModeloProducto();
        
     	DefaultTableModel modelo = new DefaultTableModel();

        String sql ="";
	
	modelo.addColumn("id");
	modelo.addColumn("NombreProd");
	modelo.addColumn("Precio");
	modelo.addColumn("Stock");
	
	tablaTotalProductos.setModel(modelo);
        
        sql = "SELECT producto.idproducto, producto.nombre, producto.precioProducto, producto.stock FROM producto;";

        try {
    // Código que podría lanzar una excepción
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){            
                objetoProducto.setIdproducto(rs.getInt("idproducto"));
                objetoProducto.setNombreProducto(rs.getString("nombre"));                   
                objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));                  
                objetoProducto.setStockProducto(rs.getInt("stock"));
                    
                modelo.addRow(new Object[]{objetoProducto.getIdproducto(),objetoProducto.getNombreProducto(),objetoProducto.getPrecioProducto(),objetoProducto.getStockProducto()});     
                }
            
            tablaTotalProductos.setModel(modelo);
                
        } catch (SQLException e) {
    // Código para manejar la excepción
        JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR PRODUCTO "+e.toString());
        } 
        finally {
    // Código que se ejecuta siempre, ocurra o no una excepción
            objetoConexion.cerrarConexion();
        }       
        
	} 
    
        
          /* CREAMOS METODO PARA AGREGAR PRODUCTO */
    public void AgregarProducto(JTextField nombres,JTextField precioProducto,JTextField stock){
        
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
	Modelos.ModeloProducto objetoProducto = new Modelos.ModeloProducto();

	String consulta = "INSERT INTO producto (nombre, precioproducto, stock) values (?,?,?);";
        
    try {
            objetoProducto.setNombreProducto(nombres.getText());
            objetoProducto.setPrecioProducto(Double.valueOf(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));
            
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoProducto.getNombreProducto());
            cs.setDouble(2, objetoProducto.getPrecioProducto());
            cs.setInt(3, objetoProducto.getStockProducto());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null,"EL PRODUCTO SE GUARDO CORRECTAMENTE");
            
        } catch (Exception e) {
            /* MOSTRAMOS UN MENSAJE DE ALERTA DE ERROR DE CONEXION DESCRIBIENDO EL TIPO DE ERROR EN VARIABLE: e */
            JOptionPane.showMessageDialog(null,"ERROR AL GUARDAR PRODUCTO "+e.toString());
        }
        finally {
            /* CERRAMOS LA CONEXION PARA EVITAR VARIAS CONEXIONES ABIERTAS*/
            objetoConexion.cerrarConexion();
        }
    
    }
    
    
           /* CREAMOS METODO SELECCIONAR REGISTROS DE PRODUCTO */
            public void Seleccionar(JTable totalproducto, JTextField id, JTextField nombres, JTextField precioProducto, JTextField stockProducto){
            
                int fila = totalproducto.getSelectedRow();
                
               try {    
                       if (fila >=0) {
                          id.setText(totalproducto.getValueAt(fila, 0).toString());
                          nombres.setText(totalproducto.getValueAt(fila, 1).toString());
                          precioProducto.setText(totalproducto.getValueAt(fila, 2).toString());
                          stockProducto.setText(totalproducto.getValueAt(fila, 3).toString());                        
               
                       }      
                        
                } catch (Exception e) {                    
                    JOptionPane.showMessageDialog(null, "ERROR AL SELECCIONAL REGISTRO "+e.toString());
                }
             }
            
           /* CREAMOS METODO PARA MODIFICAR REGISTRO DEL CLIENTE SELECCIONADO */
            public void ModificarProducto(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock) throws SQLException{
            
                Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
                Modelos.ModeloProducto objetoProducto = new Modelos.ModeloProducto();               
                           
                String consulta = "UPDATE producto SET producto.nombre= ?, producto.precioProducto= ?, producto.stock= ? WHERE producto.idproducto= ?;";
                
                try {
                  objetoProducto.setIdproducto(Integer.parseInt(id.getText()));
                  objetoProducto.setNombreProducto(nombres.getText());   
                  objetoProducto.setPrecioProducto(Double.valueOf(precioProducto.getText()));
                  objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));
                  
                  CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);

                        cs.setString(1, objetoProducto.getNombreProducto());
                        cs.setDouble(2, objetoProducto.getPrecioProducto());
                        cs.setInt(3, objetoProducto.getStockProducto());
                        cs.setInt(4, objetoProducto.getIdproducto());
               
                  cs.execute();
                  
                  JOptionPane.showMessageDialog(null, "REGISTRO DE MODIFICO CORRECTAMENTE");
                    
                } catch (Exception e) {
                     
                  JOptionPane.showMessageDialog(null, "❌ OCURRIO UN ERROR AL MODIFICAR EL PRODUCTO: " + e.toString());
                     
                } finally {
                    objetoConexion.cerrarConexion();
                }
             }     
       
            /* CREAMOS EL METODO LIMPIAR CAMPOS DE REGISTROS PARA BLANQUEAR LOS TEXFIELDs */
                public void LimpiarCamposProducto(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock){
                
                    id.setText("");
                    nombres.setText("");
                    precioProducto.setText("");
                    stock.setText("");                   
                    
                }
                
                
                                /* CREAMOS METODO: ELIMINAR CLIENTE DE LA BD. */
                public void EliminarProducto(JTextField id){
                    
                Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
                Modelos.ModeloProducto objetoProducto = new Modelos.ModeloProducto();  
                
                String consulta ="DELETE FROM producto WHERE producto.idproducto=?;";
                
               
                    try {
                        objetoProducto.setIdproducto(Integer.parseInt(id.getText()));
                        CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
                        cs.setInt(1, objetoProducto.getIdproducto());
                        
                        cs.execute();
                        
                        JOptionPane.showMessageDialog(null, "EL PRODUCTO DE ELIMINO CORRECTAMENTE");
                        
                   
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "X OCURRIO UN ERROR, NO SE ELIMINO EL PRODUCTO "+e.toString());
                    } finally {
                        objetoConexion.cerrarConexion();
                    }
                    
                }
                
            
    
}
