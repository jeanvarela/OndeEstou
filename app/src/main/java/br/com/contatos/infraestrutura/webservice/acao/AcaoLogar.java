package br.com.contatos.infraestrutura.webservice.acao;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import br.com.contatos.infraestrutura.util.ErrorUtils;
import br.com.contatos.infraestrutura.webservice.interfaces.AccessTokenHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.ErroHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.LogarHandler;
import br.com.contatos.infraestrutura.webservice.retorno.AccessTokenRetorno;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jean on 25/07/2017.
 */

public class AcaoLogar extends AsyncTask<Call<AccessTokenRetorno>,Void,Response<AccessTokenRetorno>> {

    private LogarHandler       logarHandler;
    private ErroHandler        erroHandler;
    private String             email;


    public AcaoLogar(LogarHandler logarHandler, ErroHandler erroHandler, String email){
        this.logarHandler = logarHandler;
        this.erroHandler = erroHandler;
        this.email = email;
    }

    @Override
    protected Response<AccessTokenRetorno> doInBackground(Call<AccessTokenRetorno>... callback) {
        Call<AccessTokenRetorno> call = callback[0];

        try {
            return call.execute();
        } catch (IOException e) {
            Log.e("Erro", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Response<AccessTokenRetorno> response) {
        if (response.isSuccessful()){
            logarHandler.retornaDadosLogin(response.body(),email);
        }else{
            try{
                erroHandler.erro(ErrorUtils.parseError(response.errorBody().string()));
            }  catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
