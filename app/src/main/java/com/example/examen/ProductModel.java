package com.example.examen;

public class ProductModel {
    private int idProducto;
    private String nombre;
    private int existencias;
    private double precio;
    private String descripcion;

    // Constructores, getters y setters

    public ProductModel(int idProducto, String nombre, int existencias, double precio, String descripcion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.existencias = existencias;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
