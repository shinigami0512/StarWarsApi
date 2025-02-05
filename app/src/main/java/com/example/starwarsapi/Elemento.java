package com.example.starwarsapi;

/**
 * Clase generica que representa al elemento seleccionado a mostrar en el
 * recyclerview
 */
public class Elemento {

    private String dato1;
    private String dato2;
    private String dato3;

    public Elemento(String dato1, String dato2, String dato3) {
        this.dato1 = dato1;
        this.dato2 = dato2;
        this.dato3 = dato3;
    }

    public String getDato1() {
        return dato1;
    }

    public String getDato2() {
        return dato2;
    }

    public String getDato3() {
        return dato3;
    }
}
