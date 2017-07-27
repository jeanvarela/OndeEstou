package br.com.contatos.infraestrutura.webservice.interfaces;

import br.com.contatos.modelo.entidade.usuario.Usuario;

/**
 * Interface responsável por definir o método utilizado para retornar os dados do usuário
 */

public interface DadosUsuarioHandler {

    public void retornaUsuario(Usuario usuario);
}
