package br.com.contatos.infraestrutura.webservice.acao;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.com.contatos.infraestrutura.util.ErrorUtils;
import br.com.contatos.infraestrutura.webservice.interfaces.AccessTokenHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.ErroHandler;
import br.com.contatos.infraestrutura.webservice.retorno.AccessTokenRetorno;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Função: Classe responsavél grava o registro do usuário na API rest
 */
public class AcaoGravaUsuario extends AsyncTask<Call<AccessTokenRetorno>,Void,Response<AccessTokenRetorno>> {

    private AccessTokenHandler accessTokenHandler;
    private ErroHandler        erroHandler;

    public AcaoGravaUsuario(AccessTokenHandler accessTokenHandler, ErroHandler erroHandler){
        this.accessTokenHandler = accessTokenHandler;
        this.erroHandler = erroHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Response<AccessTokenRetorno> doInBackground(Call<AccessTokenRetorno>... callback) {
        Call<AccessTokenRetorno> call = callback[0];

        try {
            return call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Response<AccessTokenRetorno> response) {
        if (response.isSuccessful()){
            accessTokenHandler.retornaAccessToken(response.body());
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
