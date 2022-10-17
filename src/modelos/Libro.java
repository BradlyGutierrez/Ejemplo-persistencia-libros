/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author bradl
 */
public class Libro {
    private String isbn; 
    private String titulo;
    private int numeroEdicion;
    private String copyright;

    public Libro() {
    }

    public Libro(String isbn, String titulo, int numeroEdicion, String copyright) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.numeroEdicion = numeroEdicion;
        this.copyright = copyright;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public void setNumeroEdicion(int numeroEdicion) {
        this.numeroEdicion = numeroEdicion;
    }
    
    
}
