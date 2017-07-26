package br.com.contatos.infraestrutura.webservice.acao;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.com.contatos.infraestrutura.util.ErrorUtils;
import br.com.contatos.infraestrutura.webservice.interfaces.ErroHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.SucessoHandler;
import br.com.contatos.infraestrutura.webservice.retorno.AccessTokenRetorno;
import br.com.contatos.infraestrutura.webservice.retorno.OcorrenciaRetorno;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jean on 26/07/2017.
 */

public class AcaoGravarOcorrencia extends AsyncTask<Call<OcorrenciaRetorno>,Void,Response<OcorrenciaRetorno>> {

    private SucessoHandler sucessoHandler;
    private ErroHandler    erroHandler;

    public AcaoGravarOcorrencia(SucessoHandler sucessoHandler, ErroHandler erroHandler) {
        this.sucessoHandler = sucessoHandler;
        this.erroHandler = erroHandler;
    }

    @Override
    protected Response<OcorrenciaRetorno> doInBackground(Call<OcorrenciaRetorno>... callback) {
        Call<OcorrenciaRetorno> call = callback[0];

        try {
            return call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Response<OcorrenciaRetorno> response) {
        if (response.isSuccessful()){
            sucessoHandler.sucesso("Localização enviada com sucesso");
        }else{
            erroHandler.erro("E-mail e/ou Senha inválidos");
        }
    }
}
