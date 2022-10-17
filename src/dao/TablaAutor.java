/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelos.Autor;

/**
 *
 * @author bradl
 */
public class TablaAutor {
    private ArrayList <Autor> lista;
    private final Conexion conexion  = new Conexion();
    private Connection conn; //Gestiona la conexión
    private PreparedStatement mostrarRegistros;
    private PreparedStatement modificarRegistro;
    private PreparedStatement insertarRegistros;
    private PreparedStatement eliminarRegistros;

    public TablaAutor() {
        try{
            conn = conexion.obtenerConexión();
            mostrarRegistros = conn.prepareStatement("Select * from Autor");
            insertarRegistros = conn.prepareStatement("Insert Into Autor (nombrePila, "+ "apellidoPaterno) Values(?, ?)");
            modificarRegistro = conn.prepareStatement("Update Autor set nombrePila = ?, " + " apellidoPaterno = ? where idAutor = ?");
            eliminarRegistros = conn.prepareStatement("Delete From Autor where idAutor = ? ");
            lista = new ArrayList<>();
            
            lista = listarRegistro();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
    
    private ArrayList<Autor> listarRegistro() {
        ArrayList<Autor> result = null;
        ResultSet rs = null;
        try{
            rs= mostrarRegistros.executeQuery();
            result = new ArrayList<>();
            while (rs.next()){
                result.add(new Autor(
                        rs.getInt("idAutor"),
                        rs.getString("nombrePila"),
                        rs.getString("apellidoPaterno"),
                        1//Estado Original que viene desde la BD
                ));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                rs.close();
            }catch(SQLException ex){
                conexion.close(conn);
            }
        }
        return result;
    }
    
    public int agregarALista(String nombrePila, String apellidoPaterno){
        try{
            lista.add(new Autor(0,
            nombrePila,
            apellidoPaterno,
            4));
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return 0;
    }
    
    public int editarLista(int idAutor, String nombrePila, String apellidoPaterno){
        try{
            Autor autor = new Autor(
            idAutor,
            nombrePila,
            apellidoPaterno,
            2
            );
            
            for(Autor a: lista){
                if(a.getIdAutor() == autor.getIdAutor()){
                    a.setNombrePila(autor.getNombrePila());
                    a.setApellidoPaterno(autor.getApellidoPaterno());
                    if(a.getEstado() != 0){
                        a.setEstado(autor.getEstado());
                    }
                }
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return 1;
    }
    
    public int eliminarEnLista(int idAutor) {
        try {
            for (Autor a : lista) {
                if (a.getIdAutor() == idAutor) {
                    a.setEstado(3);
                    return 1;
                } //estado Eliminado en la lista,
                //aun no eliminado en la base de datos
                
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public int agregarRegistroBD(Autor autor){
        int result = 0;
        try{
            insertarRegistros.setString(1, autor.getNombrePila());
            insertarRegistros.setString(2,autor.getApellidoPaterno());
            result = insertarRegistros.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
            conexion.close(conn);
        }
        return result;
    }
    
    public int modificarRegistroBD(Autor autor) {
        int result = 0;
        try {
            modificarRegistro.setString(1, autor.getNombrePila());
            modificarRegistro.setString(2, autor.getApellidoPaterno());
            modificarRegistro.setInt(3, autor.getIdAutor());
            result = modificarRegistro.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.close(conn);

        }
        return result;

    }

    
    public int eliminarRegistroBD(Autor autor) {
        int result = 0;
        try {
            eliminarRegistros.setInt(1, autor.getIdAutor());
            
            result = eliminarRegistros.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            conexion.close(conn);

        }
        return result;
    }

    public String actualizarBD()  {
        String msn = "";
        String msnError = "Errores en: ";
        int nuevos = 0, modificados = 0, eliminados = 0;
        int errorNuevos = 0, errorModificados = 0, errorEliminados = 0;

        for (Autor autor : lista) {
            switch (autor.getEstado()) {
                case 1:
                    //Si es original no hace nada
                    break;
                case 2:
                    //Estado notificacion
                    if (this.modificarRegistroBD(autor) != 0) {
                        modificados++;

                    } else {
                        errorModificados++;
                        msnError += "\n - Error al modificar: " + autor.getNombrePila()
                                + " " + autor.getApellidoPaterno();

                    }
                    break;
                case 3:
                    //estado eliminado
                    if (this.eliminarRegistroBD(autor) != 0) {
                        eliminados++;
                    } else {
                        errorEliminados++;
                        msnError += "\n - Error al eliminar: " + autor.getNombrePila()
                                + " " + autor.getApellidoPaterno();

                    }
                    break;
                case 4:
                    //Estado nuevo registro
                    if (this.agregarRegistroBD(autor) != 0) {
                        nuevos++;

                    } else {
                        errorNuevos++;
                        msnError += "\n -Error al agregar nuevo registro: "
                                + autor.getNombrePila() + " " + autor.getApellidoPaterno();

                    }
                    break;
                default:
                    msnError += "\n Revise el registro: " + autor.getNombrePila()
                            + " " + autor.getApellidoPaterno();
                    break;

            }
        }
        msn = "Registro guardados: "+ nuevos + "\nRegistros editados: "+ modificados +
                "\nRegistros eliminados: "+ eliminados;
        if(!msnError.equals("Entonces en: ")){
            msn += "\n"+ msnError;
        
        }
        lista = this.listarRegistro();
        return msn;

    }
    
    public ArrayList<Autor> getLista(){
        return lista;
    }


}
