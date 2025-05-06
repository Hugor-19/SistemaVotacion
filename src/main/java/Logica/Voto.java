package Logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "votos") // Nombre explícito de la tabla
public class Voto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_ciudadano", nullable = false)
    private int idUsuario;

    @Column(name = "id_eleccion", nullable = false)
    private int idEleccion;

    @Column(name = "id_candidato", nullable = false)
    private int idCandidato;

    @Column(name = "fecha_votacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // Manejo de fechas con TIMESTAMP
    private Date fechaVotacion;

    public Voto() {
    }

    public Voto(int id, int idCiudadano, int idEleccion, int idCandidato, Date fechaVotacion) {
        this.id = id;
        this.idUsuario = idCiudadano;
        this.idEleccion = idEleccion;
        this.idCandidato = idCandidato;
        this.fechaVotacion = fechaVotacion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(int idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Date getFechaVotacion() {
        return fechaVotacion;
    }

    public void setFechaVotacion(Date fechaVotacion) {
        this.fechaVotacion = fechaVotacion;
    }
}