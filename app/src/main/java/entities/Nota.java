package entities;

import java.io.Serializable;

/**
 * Created by David Rojo Martin on 11/12/2015.
 */
public class Nota implements Serializable {
    private String id;
    private String titulo;
    private String fecha;
    private String texto;

    public Nota(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", texto='" + texto + '\'' +
                '}';
    }

    public void relleno(){

    }

}
