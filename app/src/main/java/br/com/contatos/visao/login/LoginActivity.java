package br.com.contatos.visao.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import br.com.contatos.R;
import br.com.contatos.controlador.ControladorUsuario;
import br.com.contatos.controlador.IControladorUsuario;
import br.com.contatos.infraestrutura.util.Constantes;
import br.com.contatos.infraestrutura.util.MessageUtil;
import br.com.contatos.infraestrutura.util.Util;
import br.com.contatos.infraestrutura.util.UtilValidacao;
import br.com.contatos.infraestrutura.webservice.ServiceGenerator;
import br.com.contatos.infraestrutura.webservice.acao.AcaoLogar;
import br.com.contatos.infraestrutura.webservice.acao.AcaoRecuperaDadosUsuario;
import br.com.contatos.infraestrutura.webservice.bean.LoginBean;
import br.com.contatos.infraestrutura.webservice.interfaces.DadosUsuarioHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.ErroHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.LogarHandler;
import br.com.contatos.infraestrutura.webservice.retorno.AccessTokenRetorno;
import br.com.contatos.infraestrutura.webservice.retorno.DadosUsuarioRetorno;
import br.com.contatos.infraestrutura.webservice.resource.UsuarioService;
import br.com.contatos.modelo.entidade.usuario.Usuario;
import br.com.contatos.visao.principal.PrincipalActivity;
import br.com.contatos.visao.usuario.UsuarioActivity;
import retrofit2.Call;

/**
 * Activity responsavel por gerenciar a açoes relacionada ao login na aplicação *
 */
public class LoginActivity extends AppCompatActivity implements LogarHandler, ErroHandler,DadosUsuarioHandler {

    private TextInputLayout txtEmailLayout;
    private EditText        txtEmail;

    private TextInputLayout txtSenhaLayout;
    private EditText        txtSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmailLayout = (TextInputLayout) findViewById(R.id.txtEmailLayout);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        txtSenhaLayout = (TextInputLayout) findViewById(R.id.txtSenhaLayout);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
    }

    /**
     * Função: verificar se o e-mail e senha foram digitados corretamente
     *
     * @return
     */
    public boolean validaInformacoes(){
        boolean informacaoValida = true;

        if (UtilValidacao.verificaCampoTextoNaoVazio(txtEmail.getText().toString())){
            txtEmailLayout.setErrorEnabled(false);
        }else{
            informacaoValida = false;
            txtEmailLayout.setError(getString(R.string.emailNaoPreenchido));
        }

        if (UtilValidacao.verificaCampoTextoNaoVazio(txtSenha.getText().toString())){
            txtSenhaLayout.setErrorEnabled(false);
        }else{
            informacaoValida = false;
            txtSenhaLayout.setError(getString(R.string.senhaNaoPreenchida));
        }

        return informacaoValida;
    }

    /**
     * Funçao: Chama a tela para cadastrar usuário
     */
    public void cadastrarUsuario(View v){
        Intent intent = new Intent(this, UsuarioActivity.class);
        startActivity(intent);
    }

    public void realizarLogin(View v){
        if (Util.verificaConectado(getApplicationContext())) {
            if (validaInformacoes()) {
                realizarLogin();
            }
        }else{
            MessageUtil.exibeMensagem(getString(R.string.semConexao),getString(R.string.erro),this);
        }
    }

    private void realizarLogin(){
        LoginBean  bean = new LoginBean();
        bean.setUsername(txtEmail.getText().toString());
        bean.setPassword(txtSenha.getText().toString());
        bean.setClient_secret(Constantes.CLIENT_SECRET);
        bean.setGrant_type(Constantes.GRANT_TYPE);
        bean.setClient_id(Constantes.CLIENT_ID);

        UsuarioService service = ServiceGenerator.createService(UsuarioService.class);
        Call<AccessTokenRetorno> call = service.logar(bean);

        AcaoLogar callLogar = new AcaoLogar(this,this,txtEmail.getText().toString());
        callLogar.execute(call);
    }


    @Override
    public void erro(String mensagem) {
        MessageUtil.exibeMensagem(mensagem,getString(R.string.erro),LoginActivity.this);
    }

    @Override
    public void retornaDadosLogin(AccessTokenRetorno token, String email) {
        String tokenUsuario = token.getToken_type() + " " + token.getAccess_token();

        UsuarioService service = ServiceGenerator.createService(UsuarioService.class,tokenUsuario);
        Call<DadosUsuarioRetorno> call = service.retornaDadosUsuarioder(email);

        Usuario usuario = new Usuario();
        usuario.setPassword(txtSenha.getText().toString());
        usuario.setToken_type(token.getToken_type());
        usuario.setAccess_token(token .getAccess_token());

        AcaoRecuperaDadosUsuario callRecuperaDados = new AcaoRecuperaDadosUsuario(usuario,this,this);
        callRecuperaDados.execute(call);
    }

    @Override
    public void retornaUsuario(Usuario usuario) {
        String access_token = usuario.getToken_type() + " " + usuario.getAccess_token();

        long idUsuario = 0;
        IControladorUsuario controladorUsuario = new ControladorUsuario(this);

        if (controladorUsuario.verificaExitenciaUsuario(usuario.getId())){
            controladorUsuario.atualizaToken(usuario.getId(),usuario.getAccess_token());
            idUsuario = usuario.getId();
        }else{
           idUsuario =  controladorUsuario.adicionaUsuario(usuario);
        }

        chamaTelaPrincipal(idUsuario,access_token);
    }

    private void chamaTelaPrincipal(long idUsuario, String access_token){
        Intent intent = new Intent(this,PrincipalActivity.class);
        intent.putExtra(Constantes.ID_USUARIO_PARAMETRO,idUsuario);
        intent.putExtra(Constantes.ID_TOKEN_PARAMETRO,access_token);
        startActivity(intent);
    }
}
