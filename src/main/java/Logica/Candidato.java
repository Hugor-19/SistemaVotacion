package Logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "candidato")
public class Candidato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "cedula", nullable = false, unique = true)
    private String cedula;

    @Column(name = "partido_politico", nullable = false, length = 50)
    private String partidoPolitico;

    @Column(name = "imagen", length = 225)
    private String imagen;

    @Column(name = "propuesta", nullable = false, length = 255)
    private String propuesta;

    @Column(name = "estado", nullable = false, columnDefinition = "ENUM('activo', 'inactivo') DEFAULT 'activo'")
    private String estado;

    @Column(name = "fecha_creacion", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public Candidato() {
        this.estado = "activo";
    }

    // Constructor con imagen como String (ruta relativa)
    public Candidato(String nombre, String apellido, String cedula, String partidoPolitico, String imagen, String propuesta, String estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.partidoPolitico = partidoPolitico;
        this.imagen = imagen;
        this.propuesta = propuesta;
        this.estado = estado;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPartidoPolitico() {
        return partidoPolitico;
    }

    public void setPartidoPolitico(String partidoPolitico) {
        this.partidoPolitico = partidoPolitico;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(String propuesta) {
        this.propuesta = propuesta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}