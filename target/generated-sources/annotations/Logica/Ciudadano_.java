package Logica;

import Logica.Ciudadano.TipoDocumento;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-06T15:37:24")
@StaticMetamodel(Ciudadano.class)
public class Ciudadano_ { 

    public static volatile SingularAttribute<Ciudadano, String> documentoCedula;
    public static volatile SingularAttribute<Ciudadano, TipoDocumento> tipoDocumento;
    public static volatile SingularAttribute<Ciudadano, String> estado;
    public static volatile SingularAttribute<Ciudadano, Date> fechaNacimiento;
    public static volatile SingularAttribute<Ciudadano, String> apellido;
    public static volatile SingularAttribute<Ciudadano, Date> fechaCreacion;
    public static volatile SingularAttribute<Ciudadano, Integer> id;
    public static volatile SingularAttribute<Ciudadano, String> telefono;
    public static volatile SingularAttribute<Ciudadano, String> nombre;

}