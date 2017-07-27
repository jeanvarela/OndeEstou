package br.com.contatos.infraestrutura.webservice.interfaces;

import br.com.contatos.infraestrutura.webservice.retorno.AccessTokenRetorno;

/**
 * Interface responsável por definir o método para retornar o token e o email após a finalização do login
 */

public interface LogarHandler {

    public void retornaDadosLogin(AccessTokenRetorno token,String email);
}
