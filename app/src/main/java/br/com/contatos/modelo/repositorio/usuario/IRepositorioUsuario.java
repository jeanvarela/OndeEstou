package br.com.contatos.modelo.repositorio.usuario;

import java.util.List;

import br.com.contatos.modelo.entidade.usuario.Usuario;

/**
 * Created by JEAN on 21/05/2017.
 */

public interface IRepositorioUsuario {

    public long inserirUsuario(Usuario usuario);

    public Usuario recuperaUsuario(long idUsuario);

    public void atualizaToken(long idUsuario, String token);
}
