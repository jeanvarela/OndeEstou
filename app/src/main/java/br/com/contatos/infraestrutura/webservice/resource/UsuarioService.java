package br.com.contatos.infraestrutura.webservice.resource;

import br.com.contatos.infraestrutura.webservice.bean.LoginBean;
import br.com.contatos.infraestrutura.webservice.bean.UsuarioBean;
import br.com.contatos.infraestrutura.webservice.retorno.AccessTokenRetorno;
import br.com.contatos.infraestrutura.webservice.retorno.DadosUsuarioRetorno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author JEAN.
 *
 * Função: essa interface tem como objetivo expor os recuros do webservice usuario
 */
public interface UsuarioService {



    @POST("store/user")
    Call<AccessTokenRetorno> cadastrarUsuario(@Body UsuarioBean usuario);

    @FormUrlEncoded
    @POST("user")
    Call<DadosUsuarioRetorno> retornaDadosUsuarioder(@Field("email") String email);

    @POST("oauth/access_token")
   /*Call<AccessTokenRetorno> logar(@Field("username") String username,@Field("password") String password,
                                    @Field("client_id") String client_id,@Field("grant_type") String grant_type,
                                    @Field("client_secret") String client_secret);*/
    Call<AccessTokenRetorno> logar(@Body LoginBean login);


}
