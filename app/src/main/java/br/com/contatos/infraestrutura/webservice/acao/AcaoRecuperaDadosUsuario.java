package br.com.contatos.infraestrutura.webservice.acao;

import android.os.AsyncTask;

import java.io.IOException;

import br.com.contatos.infraestrutura.webservice.interfaces.AccessTokenHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.DadosUsuarioHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.ErroHandler;
import br.com.contatos.infraestrutura.webservice.retorno.AccessTokenRetorno;
import br.com.contatos.infraestrutura.webservice.retorno.DadosUsuarioRetorno;
import br.com.contatos.modelo.entidade.usuario.Usuario;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Função: Classe responsavél por recuperar os dados do usuário na API rest
 */
public class AcaoRecuperaDadosUsuario extends AsyncTask<Call<DadosUsuarioRetorno>,Void,Response<DadosUsuarioRetorno>> {

    private Usuario usuario;
    private DadosUsuarioHandler dadosUsuarioHandler;
    private ErroHandler erroHandler;

    public AcaoRecuperaDadosUsuario(Usuario usuario, DadosUsuarioHandler dadosUsuarioHandler, ErroHandler erroHandler){
        this.usuario = usuario;
        this.dadosUsuarioHandler = dadosUsuarioHandler;
        this.erroHandler = erroHandler;
    }

    @Override
    protected Response<DadosUsuarioRetorno> doInBackground(Call<DadosUsuarioRetorno>... calls) {
        try {
            return calls[0].execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Response<DadosUsuarioRetorno> response) {
        if (response.isSuccessful()){
            DadosUsuarioRetorno dadosRetornados = response.body();
            usuario.setId(dadosRetornados.getData().getId());
            usuario.setNome(dadosRetornados.getData().getNome());
            usuario.setTipo(dadosRetornados.getData().getTipo());
            usuario.setEmail(dadosRetornados.getData().getEmail());
            usuario.setTelefone(dadosRetornados.getData().getTelefone());


            dadosUsuarioHandler.retornaUsuario(usuario);
        }else{
            try{
                erroHandler.erro(response.errorBody().string());
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
