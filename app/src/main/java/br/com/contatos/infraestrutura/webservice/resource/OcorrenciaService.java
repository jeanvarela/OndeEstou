package br.com.contatos.infraestrutura.webservice.resource;

import br.com.contatos.infraestrutura.webservice.retorno.OcorrenciaRetorno;
import br.com.contatos.modelo.entidade.localizacao.Localizacao;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by jean on 26/07/2017.
 */

public interface OcorrenciaService {

    @POST("createocurrency")
    Call<OcorrenciaRetorno> enviarOcorrencia(@Body Localizacao ocorrencia);
}
