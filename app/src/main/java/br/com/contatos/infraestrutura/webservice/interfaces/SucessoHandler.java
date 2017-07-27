package br.com.contatos.infraestrutura.webservice.interfaces;

/**
 * Interface responsável por definir o método para retornar a mensagem de sucesso caso
 * a chamada ao método da API seja finalizada com sucesso.
 */

public interface SucessoHandler {

    public void sucesso(String mensagem);
}
