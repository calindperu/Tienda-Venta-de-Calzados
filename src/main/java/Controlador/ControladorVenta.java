/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class ControladorVenta {
    
    /* CREAMOS EL METODO BUSCAR PRODUCTOS */
    public void BuscarProductos(JTextField nombreProducto, JTable tablaproductos){
    
    Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
    Modelos.ModeloProducto objetoProducto = new Modelos.ModeloProducto();
    
       DefaultTableModel modelo = new DefaultTableModel();
       modelo.addColumn("id");
       modelo.addColumn("NombreP");
       modelo.addColumn("PrecioProducto");       
       modelo.addColumn("Stock");
       
       tablaproductos.setModel(modelo);
       
        try {
            String consulta ="SELECT * FROM producto WHERE producto.nombre LIKE concat('%',?,'%')";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setString(1, nombreProducto.getText());
            
            ResultSet rs = ps.executeQuery();
            
            /* Mostramos los registros de la consulta en la tabla */
            
            while (rs.next()){
            
                objetoProducto.setIdproducto(rs.getInt("idproducto"));
                objetoProducto.setNombreProducto(rs.getString("nombre"));
                objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));
                objetoProducto.setStockProducto(rs.getInt("stock"));
                
                modelo.addRow(new Object[]{objetoProducto.getIdproducto(),objetoProducto.getNombreProducto(),objetoProducto.getPrecioProducto(),objetoProducto.getStockProducto()});
                
            }
            
                tablaproductos.setModel(modelo);
                    
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,"X Error al mostrar conexion"+e.toString());
            
        } finally {
            
            objetoConexion.cerrarConexion();
        }
        
        /* EVITA QUE LOR REGISTROS SEAN MODIFICADOS DESHABILITANDO LAS COLUMNAS */           
        
        for (int column = 0; column < tablaproductos.getColumnCount(); column++ ){
	Class <?> columnClass = tablaproductos.getColumnClass(column);
	tablaproductos.setDefaultEditor(columnClass,null);
        }

    }
    
    public void SeleccionarProductoVenta (JTable tablaProducto, JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock, JTextField precioFinal){
        
        int fila = tablaProducto.getSelectedRow();
        
        try {
            if (fila>=0) {
                id.setText(tablaProducto.getValueAt(fila, 0).toString());
                nombres.setText(tablaProducto.getValueAt(fila, 1).toString());
                precioProducto.setText(tablaProducto.getValueAt(fila, 2).toString());
                stock.setText(tablaProducto.getValueAt(fila, 3).toString());
                precioFinal.setText(tablaProducto.getValueAt(fila, 2).toString());
            }

        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "X ERROR DE SELECCION DE REGISTRO "+e.toString());
        }
    
       }
    
    
            /* CREAMOS EL METODO BUSCAR CLIENTES */
        public void BuscarCliente(JTextField nombreCliente, JTable tablaClientes){
    
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();
    
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("Nombres");
        modelo.addColumn("appaterno");       
        modelo.addColumn("apmaterno");
       
       tablaClientes.setModel(modelo);
       
        try {
            String consulta ="SELECT * FROM cliente WHERE cliente.nombres LIKE concat('%',?,'%');";            
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            
            ps.setString(1, nombreCliente.getText());
            
            ResultSet rs = ps.executeQuery();
            
            /* Mostramos los registros de la consulta en la tabla */
            
            while (rs.next()){
            
                objetoCliente.setIdCliente(rs.getInt("idcliente"));
                objetoCliente.setNombres(rs.getString("nombres"));
                objetoCliente.setapPaterno(rs.getString("appaterno"));
                objetoCliente.setApMaterno(rs.getString("apmaterno"));
                
                modelo.addRow(new Object[]{objetoCliente.getIdCliente(),objetoCliente.getNombres(),objetoCliente.getApPaterno(),objetoCliente.getApMaterno()});
                
            }
                tablaClientes.setModel(modelo);
                    
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,"X Error al mostrar clientes"+e.toString());
            
        } finally {
            
            objetoConexion.cerrarConexion();
        }
        
        /* EVITA QUE LOR REGISTROS SEAN MODIFICADOS DESHABILITANDO LAS COLUMNAS */           
        
        for (int column = 0; column < tablaClientes.getColumnCount(); column++ ){
	Class <?> columnClass = tablaClientes.getColumnClass(column);
	tablaClientes.setDefaultEditor(columnClass,null);
        }
    
        }
        
        
    /* CREAMOS EL METODO DE SELECCIONAR UN REGISTRO HACIENDO CLIC CON EL MOUSE */     
        
    public void SeleccionarClienteVenta (JTable tablaCliente, JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno){
        
        int fila = tablaCliente.getSelectedRow();
        
        try {
            if (fila>=0) {                
                id.setText(tablaCliente.getValueAt(fila, 0).toString());
                nombres.setText(tablaCliente.getValueAt(fila, 1).toString());
                appaterno.setText(tablaCliente.getValueAt(fila, 2).toString());
                apmaterno.setText(tablaCliente.getValueAt(fila, 3).toString());
            }

        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "X ERROR DE SELECCION DE REGISTRO "+e.toString());
        }
    
       }
    
     public void pasarProductosVenta (JTable tablaResumen, JTextField idproducto, JTextField nombreProducto, JTextField precioProducto, JTextField cantidadVenta, JTextField stock){
         
     
    DefaultTableModel modelo  = (DefaultTableModel) tablaResumen.getModel();
    int stockDisponible = Integer.parseInt(stock.getText());
    //String idProducto = idproducto.getText();
    
        String idProducto = idproducto.getText();
    
         for (int i = 0; i < modelo.getRowCount(); i++) {
           String idExistente = (String) modelo.getValueAt(i, 0);
           
             if (idExistente.equals(idProducto)) {
                 JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                 return;
             }
          }
          String nProducto = nombreProducto.getText();
          double precioUnitario = Double.parseDouble(precioProducto.getText());
          int cantidad = Integer.parseInt(cantidadVenta.getText());
          
          if (cantidad > stockDisponible) {
                
              JOptionPane.showMessageDialog(null, "La cantidad de venta no puede ser mayor al stock disponible");
              
              return;
          }
          
          double subtotal = precioUnitario * cantidad;
          
         //modelo.addRow(new Object[]{idProducto, nombreProducto, precioUnitario, cantidad, subtotal});
         modelo.addRow(new Object[]{idProducto, nProducto, precioUnitario, cantidad, subtotal});
    }
     
     // CREAMOS EL METODO: ELIMINAR REGISTROS SELECCIONADOS DEL RESUMEN VENTA
     
     public void EliminarProductosSeleccionadosResumenVentas(JTable tablaResumen){
     
         try {
             DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
             int IndiceSeleccionado = tablaResumen.getSelectedRow();
             
         if (IndiceSeleccionado !=- 1) {
             modelo.removeRow(IndiceSeleccionado);             
         }
         else{
             JOptionPane.showMessageDialog(null, "Seleccione una fila a eliminar");
         }
             
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "X Error al seleccionar un registro");
         }
    
     }
     
     
     // CREAMOS METODO: CALCULAR TOTAL A PAGAR (MAS EL 18%)
     
     public void calcularTotalPagar(JTable tablaResumen, JLabel IVA, JLabel totalPagar){
                        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
            
                        double totalSubtotal = 0;
                        double iva = 0.18;
                        double totaliva = 0;
            
            // CONVERTIMOS LOS TOTALES CON DOS DECIMALES
                        DecimalFormat formato = new DecimalFormat("#.##");
            
                        for (int i = 0; i < modelo.getRowCount(); i++) {
             
                        totalSubtotal = Double.parseDouble(formato.format(totalSubtotal+(double)modelo.getValueAt(i, 4)));
                        totaliva = Double.parseDouble(formato.format(iva*totalSubtotal));
                
            }
            
                        totalPagar.setText(String.valueOf(totalSubtotal));
                        IVA.setText(String.valueOf(totaliva));
     }
     
     // CREAMOS EL METODO: CREAR FACTURA
                public void crearFactura(JTextField codCliente){
                        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
                        Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();
                
                        String consulta = "INSERT INTO factura (fechafactura, fkcliente) VALUES (curdate(),?)";
                        
                    try {
                        objetoCliente.setIdCliente(Integer.parseInt(codCliente.getText()));
                        CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
                        cs.setInt(1, objetoCliente.getIdCliente());
                        
                        cs.execute();
                                
                        JOptionPane.showMessageDialog(null, "Factura creada");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "X Ocurrio un error al crear la factura "+e.toString());
                    } finally {
                        objetoConexion.cerrarConexion();
                    }
                }
                
        // CREAMOS EL METODO: REALIZAR VENTA                   
                
                public void realizarVenta(JTable tablaResumenVenta){
                    
                        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();     
                        String consultaDetalle ="INSERT INTO detalle (fkfactura,fkproducto,cantidad,precioVenta) VALUES ((SELECT MAX(idfactura) FROM factura),?,?,?);";
                        String consultaStock ="UPDATE producto SET producto.stock = stock - ? WHERE idproducto= ?;";
                        
                        try {
                                PreparedStatement psDetalle = objetoConexion.estableceConexion().prepareStatement(consultaDetalle);
                                PreparedStatement psStock = objetoConexion.estableceConexion().prepareStatement(consultaStock);
                                
                                int filas = tablaResumenVenta.getRowCount();
                                
                                for (int i = 0; i < filas; i++) {                                    

                               int idProducto = Integer.parseInt(tablaResumenVenta.getValueAt(i, 0).toString());
                               int cantidad = Integer.parseInt(tablaResumenVenta.getValueAt(i, 3).toString());
                               double precioVenta = Double.parseDouble(tablaResumenVenta.getValueAt(i, 2).toString());
                               
                               psDetalle.setInt(1, idProducto);
                               psDetalle.setInt(2, cantidad);
                               psDetalle.setDouble(3, precioVenta);
                               psDetalle.executeUpdate();
                               
                               // REDUCIMOS EL STOCK
                               psStock.setInt(1, cantidad);
                               psStock.setInt(2, idProducto);
                               psStock.executeUpdate();  
                            }
                                
                               JOptionPane.showMessageDialog(null, "Venta realizada");                                
                                        
                        } catch (Exception e) {
                               
                                JOptionPane.showMessageDialog(null, "X Error al realizar la venta "+e.toString());
                    } finally {
                                objetoConexion.cerrarConexion();
                    }
                
                }
                
                
                public void LimpiarCamposLuegoVenta(JTextField buscarCliente, JTable tablaCliente, JTextField buscarProducto, JTable tablaProducto, JTextField selectIdCliente, 
                                                    JTextField selectNombreCliente, JTextField selectAppaternoCliente, JTextField selectApmaternoCliente, JTextField selectIdProducto, 
                                                    JTextField selectNombreProducto, JTextField selectPrecioProducto, JTextField selectStockProducto, JTextField precioVenta,
                                                    JTextField cantidadVenta, JTable tablaResumen, JLabel IVA, JLabel total){
                                
                                buscarCliente.setText("");
                                buscarCliente.requestFocus();
                                DefaultTableModel modeloCliente = (DefaultTableModel) tablaCliente.getModel();
                                modeloCliente.setRowCount(0);
                                
                                buscarProducto.setText("");                                
                                DefaultTableModel modeloProducto = (DefaultTableModel) tablaProducto.getModel();
                                modeloProducto.setRowCount(0);
                                
                                selectIdCliente.setText("");
                                selectNombreCliente.setText("");
                                selectAppaternoCliente.setText("");
                                selectApmaternoCliente.setText("");
                                
                                selectIdProducto.setText("");
                                selectNombreProducto.setText("");
                                selectPrecioProducto.setText("");
                                selectStockProducto.setText("");   
                                
                                precioVenta.setText("");
                                precioVenta.setEnabled(false);
                                
                                cantidadVenta.setText("");
                                
                                // CREAMOS METODO PARA LIMPIAR LA TABLA DE RESUMEN.
                                DefaultTableModel modeloResumenVenta = (DefaultTableModel) tablaResumen.getModel();
                                modeloResumenVenta.setRowCount(0);
                                
                                IVA.setText("----");
                                total.setText("----");
                                
                }
                
                // CREAMOS METODO MOSTRAR ULTIMA FACTURA CREADA
                public void MostrarUltimaFactura(JLabel UltimaFactura){
                
                    Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
                    
                    try {
                        String consulta="SELECT MAX(idfactura) AS UltimaFactura FROM factura;";
                        
                        PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
                        
                        ResultSet rs = ps.executeQuery();
                        
                        if (rs.next()){
                            
                            UltimaFactura.setText(String.valueOf(rs.getInt("UltimaFactura")));
                       
                        }
                        
                    } catch (Exception e) {
                        
                        JOptionPane.showConfirmDialog(null,"X Error al mostrar la ultima factura"+e.toString());
                        
                    } finally {
                        
                        objetoConexion.cerrarConexion();
                    }
                
                }
                
}
