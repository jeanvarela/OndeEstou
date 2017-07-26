package br.com.contatos.infraestrutura.webservice.retorno;

/**
 * Created by jean on 24/07/2017.
 */

public class DadosUsuarioRetorno {

    private int status;
    private boolean erro;
    private UsuarioRetorno data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isErro() {
        return erro;
    }

    public void setErro(boolean erro) {
        this.erro = erro;
    }

    public UsuarioRetorno getData() {
        return data;
    }

    public void setData(UsuarioRetorno data) {
        this.data = data;
    }
}
