package br.com.contatos.infraestrutura.webservice.interfaces;

import okhttp3.ResponseBody;

/**
 * Created by jean on 25/07/2017.
 */

public interface ErroHandler {

    public void erro(String mensagem);
}
