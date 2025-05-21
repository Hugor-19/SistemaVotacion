package Logica;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "resultados")
public class Resultado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Relación ManyToOne con Candidato: Sigue siendo importante para integridad y acceso
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_candidato", nullable = false)
    private Candidato candidato;

    // Campos desnormalizados para la tabla 'resultados'
    @Column(name = "cedula_candidato_snap", nullable = false) // 'snap' para snapshot, indica que es una copia
    private String cedulaCandidatoSnap;

    @Column(name = "nombre_completo_candidato_snap", nullable = false)
    private String nombreCompletoCandidatoSnap;

    @Column(name = "imagen_candidato_snap", length = 255)
    private String imagenCandidatoSnap; // Aumentar length si es necesario

    @Column(name = "votos", nullable = false)
    private int votos;

    public Resultado() {
    }

    // Constructor que acepta el Candidato y votos, y extrae los datos para los campos 'snap'
    public Resultado(Candidato candidato, int votos) {
        this.candidato = candidato;
        this.votos = votos;
        // Asignar los valores a los campos 'snap' al crear el objeto
        if (candidato != null) {
            this.cedulaCandidatoSnap = candidato.getCedula();
            this.nombreCompletoCandidatoSnap = candidato.getNombre() + " " + candidato.getApellido();
            this.imagenCandidatoSnap = candidato.getImagen();
        }
    }

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
        // Actualizar los campos 'snap' si el Candidato se reasigna
        if (candidato != null) {
            this.cedulaCandidatoSnap = candidato.getCedula();
            this.nombreCompletoCandidatoSnap = candidato.getNombre() + " " + candidato.getApellido();
            this.imagenCandidatoSnap = candidato.getImagen();
        } else {
            this.cedulaCandidatoSnap = null;
            this.nombreCompletoCandidatoSnap = null;
            this.imagenCandidatoSnap = null;
        }
    }

    public String getCedulaCandidatoSnap() {
        return cedulaCandidatoSnap;
    }

    // Si quieres que JPA los maneje directamente, necesitarías setters públicos
    public void setCedulaCandidatoSnap(String cedulaCandidatoSnap) {
        this.cedulaCandidatoSnap = cedulaCandidatoSnap;
    }

    public String getNombreCompletoCandidatoSnap() {
        return nombreCompletoCandidatoSnap;
    }

    public void setNombreCompletoCandidatoSnap(String nombreCompletoCandidatoSnap) {
        this.nombreCompletoCandidatoSnap = nombreCompletoCandidatoSnap;
    }

    public String getImagenCandidatoSnap() {
        return imagenCandidatoSnap;
    }

    public void setImagenCandidatoSnap(String imagenCandidatoSnap) {
        this.imagenCandidatoSnap = imagenCandidatoSnap;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
}