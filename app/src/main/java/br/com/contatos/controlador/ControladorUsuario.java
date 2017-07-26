package br.com.contatos.controlador;

import android.content.Context;

import br.com.contatos.modelo.entidade.usuario.Usuario;
import br.com.contatos.modelo.repositorio.usuario.IRepositorioUsuario;
import br.com.contatos.modelo.repositorio.usuario.RepositorioUsuario;

/**
 * Created by jean on 24/07/2017.
 */

public class ControladorUsuario implements IControladorUsuario {

    private IRepositorioUsuario repositorioUsuario;

    public ControladorUsuario(Context context){
        this.repositorioUsuario = new RepositorioUsuario(context);
    }

    @Override
    public long adicionaUsuario(Usuario usuario) {
        return repositorioUsuario.inserirUsuario(usuario);
    }

    @Override
    public boolean verificaExitenciaUsuario(long idUsuario) {
        Usuario usuario = repositorioUsuario.recuperaUsuario(idUsuario);

        return usuario != null;
    }

    @Override
    public void atualizaToken(long idUsuario, String token) {
        repositorioUsuario.atualizaToken(idUsuario,token);
    }
}
