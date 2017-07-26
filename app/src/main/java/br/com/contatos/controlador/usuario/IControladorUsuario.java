package br.com.contatos.controlador.usuario;

import br.com.contatos.modelo.entidade.usuario.Usuario;

/**
 * Created by jean on 24/07/2017.
 */

public interface IControladorUsuario {

    public long adicionaUsuario(Usuario usuario);

    public boolean verificaExitenciaUsuario(long idUsuario);

    public void atualizaToken(long idUsuario, String token);
}
