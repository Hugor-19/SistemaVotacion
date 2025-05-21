package Persistencia;

import Logica.Candidato;
import Logica.Ciudadano;
import Logica.Usuario;
import Logica.Voto;
import Logica.Resultado;

import java.util.List;

public class ControladoraPersistencia {

    UsuarioJpaController usuarioJPA = new UsuarioJpaController();
    CiudadanojpaController ciudadanoJPA = new CiudadanojpaController();
    CandidatojpaController candidatoJPA = new CandidatojpaController();
    VotoJpaController votoJPA = new VotoJpaController();
    ResultadoJpaController resultadoJPA = new ResultadoJpaController();


    public List<Usuario> getUsuarios() {
        List<Usuario> listaUsuarios;
        listaUsuarios = usuarioJPA.findUsuarioEntities();

        return listaUsuarios;
    }

    // crear usuario
    public void crearUsuario(Usuario usu) {
        usuarioJPA.create(usu);
    }

    public java.util.List<Usuario> traerUsuarios() {
        return usuarioJPA.findUsuarioEntities();
    }

    public void eliminarUsuario(int id) {
        try {
            usuarioJPA.destroy(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario buscarUsuarioPorId(int id) {
        return usuarioJPA.findUsuario(id);
    }

    public void editarUsuario(Usuario usu) {
        try {
            UsuarioJpaController usuarioJpa = new UsuarioJpaController();
            usuarioJpa.edit(usu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     // Ciudadano
    public void crearCiudadano(Usuario usuario) {
        usuarioJPA.create(usuario);
    }

    public java.util.List<Ciudadano> traerCiudadanos() {
        return ciudadanoJPA.findCiudadanoEntities();
    }

    // candidato
    public void crearCandidato(Candidato candidato) {
        candidatoJPA.create(candidato);
    }
    public java.util.List<Candidato> traerCandidatos() {
        return candidatoJPA.findCandidatoEntities();
    }

    public Candidato buscarCandidatoPorId(int id) {
        return candidatoJPA.findCandidato(id);
    }

    // votos 
    public java .util.List<Voto> traerVotos() {
        return votoJPA.findVotoEntities();
    }

    // Resultado 
    public java.util.List<Resultado> traerResultados() {
        return resultadoJPA.findResultadoEntities();
    }

}