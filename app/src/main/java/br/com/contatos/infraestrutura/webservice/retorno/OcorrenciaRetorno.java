package br.com.contatos.infraestrutura.webservice.retorno;

/**
 * Created by jean on 26/07/2017.
 */

public class OcorrenciaRetorno {

    private int status;
    private boolean error;
    private Mensagem data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Mensagem getData() {
        return data;
    }

    public void setData(Mensagem data) {
        this.data = data;
    }
}
