package br.com.contatos.visao.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.contatos.R;

import br.com.contatos.controlador.ControladorUsuario;
import br.com.contatos.controlador.IControladorUsuario;
import br.com.contatos.infraestrutura.util.Mask;
import br.com.contatos.infraestrutura.util.MessageUtil;
import br.com.contatos.infraestrutura.util.UtilValidacao;
import br.com.contatos.infraestrutura.webservice.ServiceGenerator;
import br.com.contatos.infraestrutura.webservice.acao.AcaoGravaUsuario;
import br.com.contatos.infraestrutura.webservice.acao.AcaoRecuperaDadosUsuario;
import br.com.contatos.infraestrutura.webservice.bean.UsuarioBean;
import br.com.contatos.infraestrutura.webservice.interfaces.AccessTokenHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.DadosUsuarioHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.ErroHandler;
import br.com.contatos.infraestrutura.webservice.resource.UsuarioService;
import br.com.contatos.infraestrutura.webservice.retorno.AccessTokenRetorno;
import br.com.contatos.infraestrutura.webservice.retorno.DadosUsuarioRetorno;
import br.com.contatos.modelo.entidade.usuario.Usuario;
import br.com.contatos.visao.principal.PrincipalActivity;
import retrofit2.Call;

public class UsuarioActivity extends AppCompatActivity implements AccessTokenHandler,ErroHandler,DadosUsuarioHandler {


    private TextInputLayout txtNomeLayout;
    private EditText        txtNome;

    private TextInputLayout txtEmailLayout;
    private EditText        txtEmail;

    private TextInputLayout txtSenhaLayout;
    private EditText        txtSenha;

    private TextInputLayout txtSenhaConfirmacaoLayout;
    private EditText txtSenhaConfirmacao;

    private TextInputLayout txtTelefoneLayout;
    private EditText txtTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNomeLayout = (TextInputLayout) findViewById(R.id.txtNomeLayout);
        txtNome = (EditText) findViewById(R.id.txtNome);

        txtEmailLayout = (TextInputLayout) findViewById(R.id.txtEmailLayout);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        txtSenhaLayout = (TextInputLayout) findViewById(R.id.txtSenhaLayout);
        txtSenha = (EditText) findViewById(R.id.txtSenha);

        txtSenhaConfirmacaoLayout = (TextInputLayout) findViewById(R.id.txtSenhaConfirmacaoLayout);
        txtSenhaConfirmacao = (EditText) findViewById(R.id.txtSenhaConfirmacao);

        txtTelefoneLayout = (TextInputLayout) findViewById(R.id.txtTelefoneLayout);
        txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        txtTelefone.addTextChangedListener(Mask.insert("(##)#####-####", txtTelefone));
    }

    /**
     * Função: realiza o cadastro do usuário
     * @param v
     */
    public void cadastrarUsuario(View v) {
        if (validaInformacoes()){
            UsuarioBean usuario = new UsuarioBean();
            usuario.setName(txtNome.getText().toString());
            usuario.setEmail(txtEmail.getText().toString());
            usuario.setTelefone(txtTelefone.getText().toString());
            usuario.setType("Cidadao");
            usuario.setPassword(txtSenha.getText().toString());

            UsuarioService service = ServiceGenerator.createService(UsuarioService.class);
            Call<AccessTokenRetorno> call = service.cadastrarUsuario(usuario);

            AcaoGravaUsuario gravaUsuario = new AcaoGravaUsuario(this,this);
            gravaUsuario.execute(call);
       }
    }


    /**
     *  Função: Metódo utilizado para validar as informações digitadas
     */
    public boolean validaInformacoes(){
        boolean informacaoValida = true;

        if (UtilValidacao.verificaCampoTextoNaoVazio(txtNome.getText().toString())){
            txtNomeLayout.setErrorEnabled(false);
        }else{
            informacaoValida = false;
            txtNomeLayout.setError(getString(R.string.nomeNaoPreenchido));
        }

        if (UtilValidacao.verificaCampoTextoNaoVazio(txtTelefone.getText().toString())){
            if (UtilValidacao.validaTelefone(txtTelefone.getText().toString())){
                txtTelefoneLayout.setErrorEnabled(false);
            }else{
                informacaoValida = false;
                txtTelefoneLayout.setError(getString(R.string.telefoneInvalido));
            }
        }else{
            informacaoValida = false;
            txtTelefoneLayout.setError(getString(R.string.telefoneNaoPreenchido));
        }

        if (UtilValidacao.verificaCampoTextoNaoVazio(txtEmail.getText().toString())){

            if (UtilValidacao.validaEmail(txtEmail.getText().toString())){
                txtEmailLayout.setErrorEnabled(false);
            }else{
                informacaoValida = false;
                txtEmailLayout.setError(getString(R.string.emailInvalido));
            }
        }else{
            informacaoValida = false;
            txtEmailLayout.setError(getString(R.string.emailNaoPreenchido));
        }

        String senha = txtSenha.getText().toString() ;
        if (UtilValidacao.verificaCampoTextoNaoVazio(senha)){
            txtSenhaLayout.setErrorEnabled(false);
        }else{
            informacaoValida = false;
            txtSenhaLayout.setError(getString(R.string.senhaNaoPreenchida));
        }

        String senhaConfirmacao = txtSenhaConfirmacao.getText().toString() ;
        if (UtilValidacao.verificaCampoTextoNaoVazio(senhaConfirmacao)){
            txtSenhaConfirmacaoLayout.setErrorEnabled(false);
        }else{
            informacaoValida = false;
            txtSenhaConfirmacaoLayout.setError(getString(R.string.senhaConfirmacaoNaoPreenchida));
        }

        if (senha.equals(senhaConfirmacao)){
            txtSenhaConfirmacaoLayout.setErrorEnabled(false);
        }else {
            informacaoValida = false;
            txtSenhaConfirmacaoLayout.setError(getString(R.string.senhaDiferenteSenhaConfirmacao));
        }

        return informacaoValida;
    }

    @Override
    public void erro(String mensagem) {
        MessageUtil.exibeMensagem(mensagem,getString(R.string.erro),UsuarioActivity.this);
    }

    @Override
    public void retornaAccessToken(AccessTokenRetorno token) {
        Usuario usuario = new Usuario();
        usuario.setEmail(txtEmail.getText().toString());
        usuario.setPassword(txtSenha.getText().toString());
        usuario.setNome(txtNome.getText().toString());
        usuario.setTipo("Cidadao");
        usuario.setTelefone(txtTelefone.getText().toString());
        usuario.setToken_type(token.getToken_type());
        usuario.setAccess_token(token.getAccess_token());

        String tokenUsuario = token.getToken_type() + " " + token.getAccess_token();

        UsuarioService service = ServiceGenerator.createService(UsuarioService.class,tokenUsuario);
        Call<DadosUsuarioRetorno> call = service.retornaDadosUsuarioder(txtEmail.getText().toString());

        AcaoRecuperaDadosUsuario callRecuperaDados = new AcaoRecuperaDadosUsuario(usuario,this,this);
        callRecuperaDados.execute(call);
    }

    @Override
    public void retornaUsuario(Usuario usuario) {
        IControladorUsuario controladorUsuario = new ControladorUsuario(this);
        long idUsuarioInserido = controladorUsuario.adicionaUsuario(usuario);

        if (idUsuarioInserido > 0){
            Toast.makeText(this,"Usuario inserido com sucesso",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, PrincipalActivity.class);
            startActivity(intent);
        }
    }
}
