package Logica;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "resultados") // Nombre explícito de la tabla
public class Resultado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_eleccion", nullable = false)
    private int idEleccion;

    @Column(name = "id_candidato", nullable = false)
    private int idCandidato;

    @Column(name = "votos", nullable = false)
    private int votos;

    public Resultado() {
    }

    public Resultado(int id, int idEleccion, int idCandidato, int votos) {
        this.id = id;
        this.idEleccion = idEleccion;
        this.idCandidato = idCandidato;
        this.votos = votos;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEleccion() {
        return idEleccion;
    }

    public void setIdEleccion(int idEleccion) {
        this.idEleccion = idEleccion;
    }

    public int getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(int idCandidato) {
        this.idCandidato = idCandidato;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
}