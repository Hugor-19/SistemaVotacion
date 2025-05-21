package Logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "ciudadano")
public class Ciudadano implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", columnDefinition = "ENUM('CC', 'CE', 'Pasaporte') DEFAULT 'CC'")
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento" , nullable = false, unique = true)
    private String documentoCedula;


    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(name = "estado", nullable = false, columnDefinition = "ENUM('activo', 'inactivo') DEFAULT 'activo'")
    private String estado;

    @Column(name = "fecha_creacion", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public enum TipoDocumento {
        CC, CE, Pasaporte
    }

    public Ciudadano() {
        this.estado = "activo"; // Establecer 'activo' como valor por defecto en el constructor vacío
    }

    public Ciudadano(String nombre, String apellido, TipoDocumento tipoDocumento,
            String documentoCedula, String telefono, Date fechaNacimiento, String estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documentoCedula = documentoCedula;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

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

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumentoCedula() {
        return documentoCedula;
    }

    public void setDocumentoCedula(String documentoCedula) {
        this.documentoCedula = documentoCedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}