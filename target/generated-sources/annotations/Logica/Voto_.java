package Logica;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-06T15:37:24")
@StaticMetamodel(Voto.class)
public class Voto_ { 

    public static volatile SingularAttribute<Voto, Integer> idCandidato;
    public static volatile SingularAttribute<Voto, Integer> idUsuario;
    public static volatile SingularAttribute<Voto, Date> fechaVotacion;
    public static volatile SingularAttribute<Voto, Integer> id;
    public static volatile SingularAttribute<Voto, Integer> idEleccion;

}