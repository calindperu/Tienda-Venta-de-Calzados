/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import com.toedter.calendar.JDateChooser;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class ControladorReportes {
    
    public void BuscarFacturaMostrarDatosCliente(JTextField numeroFactura, JLabel numeroFacturaEncontrado, JLabel fechaFacturaEncontrado, JLabel nombreCliente, JLabel appaterno, JLabel apmaterno ){
    
           Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
           
           try {
                    String consulta = "SELECT factura.idfactura, factura.fechafactura, cliente.nombres, cliente.appaterno, cliente.apmaterno FROM factura INNER JOIN cliente ON cliente.idcliente = factura.fkcliente WHERE factura.idfactura= ?;";
                    
                    PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
                    ps.setInt(1, Integer.parseInt(numeroFactura.getText()));
                    
                   ResultSet rs = ps.executeQuery();
                   
                   if (rs.next()) {
                       numeroFacturaEncontrado.setText(String.valueOf(rs.getInt("idfactura")));
                       fechaFacturaEncontrado.setText(rs.getDate("fechaFactura").toString());
                       nombreCliente.setText(rs.getString("nombres"));
                       appaterno.setText(rs.getString("appaterno"));
                       apmaterno.setText(rs.getString("apmaterno"));
                       
                       //JOptionPane.showMessageDialog(null, "Factura encontrada");
                   }
                   else{
                       numeroFacturaEncontrado.setText("");
                       fechaFacturaEncontrado.setText("");
                       nombreCliente.setText("");
                       appaterno.setText("");
                       apmaterno.setText("");
                       
                        JOptionPane.showMessageDialog(null, "X No se encontro la factura");
                   }

        } catch (Exception e) {
            
                        JOptionPane.showMessageDialog(null, "X Error al buscar la factura"+e.toString());
        } finally {
                        objetoConexion.cerrarConexion();
        }
    
    }
    
    public void MostrarFacturaMostrarDatosProductos(JTextField numeroFactura, JTable tablaProductos, JLabel IVA, JLabel total ){
                     
                    Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
                    DefaultTableModel modelo = new DefaultTableModel();
                    
                    modelo.addColumn("N.Producto");
                    modelo.addColumn("Cantidad");        
                    modelo.addColumn("PrecioVenta");        
                    modelo.addColumn("SubTotal");
        
                    tablaProductos.setModel(modelo);
                    
                    try {
                    String consulta ="SELECT producto.nombre, detalle.cantidad, detalle.precioventa FROM detalle " +
                                    "INNER JOIN factura ON factura.idfactura = detalle.fkfactura " +
                                    "INNER JOIN producto ON producto.idproducto = detalle.fkproducto " +
                                    "WHERE factura.idfactura= ?;";
                    
                    PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
                    ps.setInt(1, Integer.parseInt(numeroFactura.getText()));
                    
                    ResultSet rs = ps.executeQuery();
                    
                    double totalFactura = 0.0;
                    double valorIVA = 0.18;
                    
                    DecimalFormat formato = new DecimalFormat("#.##");
                    
                    while (rs.next()) {
                        
                        String nombreProducto = rs.getString("nombre");
                        int cantidad = rs.getInt("cantidad");
                        double precioVenta = rs.getDouble("precioVenta");
                        double subTotal = cantidad * precioVenta;
                        
                        totalFactura = Double.parseDouble(formato.format(totalFactura+subTotal));                                                 
                        modelo.addRow(new Object[]{nombreProducto,cantidad,precioVenta,subTotal});  
                    }
                      
                        double totalIVA = Double.parseDouble(formato.format(totalFactura*valorIVA));                    
                        IVA.setText(String.valueOf(totalIVA));
                        total.setText(String.valueOf(totalFactura));
                 
                    
        } catch (Exception e) {
            
                        JOptionPane.showMessageDialog(null, "X Error al mostrar los productos de a factura "+e.toString());
            
        } finally {
                        objetoConexion.cerrarConexion();
        }
    }
                // CREAMOS EL METODO MOSTRAR TOTAL DE VENTAS POR FECHA
                // JDateChooser Control para manejar las fechas
                    public void mostrarTotalVentaPorFecha (JDateChooser desde, JDateChooser hasta, JTable tablaVentas, JLabel totalGeneral){
                     
                        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
                        
                        DefaultTableModel modelo = new DefaultTableModel();
                        modelo.addColumn("idFactura");
                        modelo.addColumn("fechaFactura");
                        modelo.addColumn("NProducto");
                        modelo.addColumn("cantidad");
                        modelo.addColumn("PrecioVenta");
                        modelo.addColumn("Subtotal");
                        
                        tablaVentas.setModel(modelo);
                        
                        try {
                            String consulta = "SELECT factura.idfactura, factura.fechafactura, producto.nombre, detalle.cantidad, detalle.precioventa FROM detalle INNER JOIN factura ON factura.idfactura = detalle.fkfactura INNER JOIN producto ON producto.idproducto = detalle.fkproducto WHERE factura.fechafactura Between ? AND ?;";
                            
                            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
                            java.util.Date fechaDesde = desde.getDate();
                            java.util.Date fechaHasta = hasta.getDate();
                            
                            java.sql.Date fechaDesdeSQL = new java.sql.Date(fechaDesde.getTime());
                            java.sql.Date fechaHastaSQL = new java.sql.Date(fechaHasta.getTime());
                            
                            ps.setDate(1, fechaDesdeSQL);
                            ps.setDate(2, fechaHastaSQL);
                            
                            ResultSet rs = ps.executeQuery();
                            
                            double totalFactura = 0.0;
                            DecimalFormat formato = new DecimalFormat("#.##");
                            
                            while (rs.next()) {                                
                                int idFactura = rs.getInt("idFactura");
                                Date fechaFactura = rs.getDate("fechaFactura");
                                String nombreProducto = rs.getString("nombre");
                                int cantidad = rs.getInt("cantidad");
                                double precioVenta = rs.getDouble("precioVenta");
                                
                                double subtotal = cantidad * precioVenta;
                                
                                totalFactura = Double.parseDouble(formato.format(totalFactura+subtotal));                               

                                modelo.addRow(new Object[]{idFactura,fechaFactura,nombreProducto,cantidad,precioVenta,subtotal});
                            }
                            
                            totalGeneral.setText(String.valueOf(totalFactura));
                       
                        } catch (Exception e) {
                                        JOptionPane.showMessageDialog(null, "Error al buscar los ingresos por fechas"+e.toString());
                        } finally {
                            
                                objetoConexion.cerrarConexion();
                        }
                        
                        for (int column = 0; column < tablaVentas.getColumnCount(); column++) {
                        
                            Class<?> columClass = tablaVentas.getColumnClass(column);
                        
                            tablaVentas.setDefaultEditor(columClass, null);
                        
                        }
                        
                    }
    
    
  }