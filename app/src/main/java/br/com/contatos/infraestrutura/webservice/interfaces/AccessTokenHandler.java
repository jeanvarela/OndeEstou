package br.com.contatos.infraestrutura.webservice.interfaces;

import br.com.contatos.infraestrutura.webservice.retorno.AccessTokenRetorno;

/**
 * Interface responsável por definir o método utilizado por retornar o token do usuário
 */

public interface AccessTokenHandler {

    public void retornaAccessToken(AccessTokenRetorno token);
}
