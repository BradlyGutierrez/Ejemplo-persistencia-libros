/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.logging.*;


/**
 *
 * @author bradl
 */
public class Conexion {
    private static final String SERVIDOR = "localhost";
    private static final String USUARIO = "sa";
    private static final String PW = "1234";
    private static final String NOMBREBD = "Libros";
    private static final String PUERTO = "1433";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    public Connection obtenerConexión(){
        try{
            String conexionUrl = "jdbc:sqlserver://" + SERVIDOR + ": " + PUERTO +
                    "; Databasename= " + NOMBREBD + "; user = " + USUARIO + "; password = "
                    + PW + ";";
           Class.forName(DRIVER);
           JOptionPane.showMessageDialog(null,"Se conectó correctamente a la base de datos");

            return (DriverManager.getConnection(conexionUrl));
           
        }catch(ClassNotFoundException | SQLException ex){
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null,ex);

        }
        return null;
    }
    
    public void close(Connection con){
        try{
            con.close();
        }catch(SQLException ex){
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
