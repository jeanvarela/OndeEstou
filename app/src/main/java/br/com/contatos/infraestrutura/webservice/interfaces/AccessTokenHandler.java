package br.com.contatos.infraestrutura.webservice.interfaces;

import br.com.contatos.infraestrutura.webservice.retorno.AccessTokenRetorno;

/**
 * Created by jean on 25/07/2017.
 */

public interface AccessTokenHandler {

    public void retornaAccessToken(AccessTokenRetorno token);
}
