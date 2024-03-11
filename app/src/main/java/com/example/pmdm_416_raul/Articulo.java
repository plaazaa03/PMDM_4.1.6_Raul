package com.example.pmdm_416_raul;
public class Articulo {
    private String nombre;
    private boolean comprado;

    public Articulo(String nombre){
        this.setNombre(nombre);
        comprado = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }
}
