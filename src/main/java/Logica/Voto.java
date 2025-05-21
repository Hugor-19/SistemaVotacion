package Logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "votos")
public class Voto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Relación ManyToOne con Ciudadano
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ciudadano", nullable = false) // FK en la tabla votos
    private Ciudadano ciudadano;

    // Campo para guardar el nombre completo del ciudadano en el momento del voto
    @Column(name = "nombre_completo_ciudadano", nullable = false)
    private String nombreCompletoCiudadano;

    // Relación ManyToOne con Candidato
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_candidato", nullable = false) // FK en la tabla votos
    private Candidato candidato;

    // Campo para guardar el nombre completo del candidato en el momento del voto
    @Column(name = "nombre_completo_candidato", nullable = false)
    private String nombreCompletoCandidato;

    @Column(name = "fecha_votacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVotacion;

    // Opcional: Si tienes una entidad de Eleccion, también deberías relacionarla aquí
    // @ManyToOne
    // @JoinColumn(name = "id_eleccion", nullable = false)
    // private Eleccion eleccion;


    public Voto() {
    }

    // Constructor sin ID y con los objetos completos para las relaciones
    public Voto(Ciudadano ciudadano, Candidato candidato, Date fechaVotacion) {
        this.ciudadano = ciudadano;
        this.nombreCompletoCiudadano = ciudadano.getNombre() + " " + ciudadano.getApellido();
        this.candidato = candidato;
        this.nombreCompletoCandidato = candidato.getNombre() + " " + candidato.getApellido();
        this.fechaVotacion = fechaVotacion;
    }

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
        // Al asignar el ciudadano, actualiza también el nombre completo
        if (ciudadano != null) {
            this.nombreCompletoCiudadano = ciudadano.getNombre() + " " + ciudadano.getApellido();
        } else {
            this.nombreCompletoCiudadano = null;
        }
    }

    public String getNombreCompletoCiudadano() {
        return nombreCompletoCiudadano;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
        // Al asignar el candidato, actualiza también el nombre completo
        if (candidato != null) {
            this.nombreCompletoCandidato = candidato.getNombre() + " " + candidato.getApellido();
        } else {
            this.nombreCompletoCandidato = null;
        }
    }

    public String getNombreCompletoCandidato() {
        return nombreCompletoCandidato;
    }

    public Date getFechaVotacion() {
        return fechaVotacion;
    }

    public void setFechaVotacion(Date fechaVotacion) {
        this.fechaVotacion = fechaVotacion;
    }
}