package br.com.contatos.infraestrutura.webservice.interfaces;

import okhttp3.ResponseBody;

/**
 * Interface responsável por definir o método para retornar a mensagem de erro caso ocorra algum erro
 */

public interface ErroHandler {

    public void erro(String mensagem);
}
