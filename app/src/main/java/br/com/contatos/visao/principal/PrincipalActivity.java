package br.com.contatos.visao.principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.contatos.R;
import br.com.contatos.controlador.localizacao.ControladorLocalizacao;
import br.com.contatos.controlador.localizacao.IControladorLocalizacao;
import br.com.contatos.infraestrutura.util.Constantes;
import br.com.contatos.modelo.entidade.localizacao.Localizacao;
import br.com.contatos.visao.localizacao.LocalizaActivity;
import br.com.contatos.visao.localizacao.LocalizacaoAdapter;
import br.com.contatos.visao.login.LoginActivity;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private long   idUsuario;
    private String token;

    private LocalizacaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        idUsuario = intent.getLongExtra(Constantes.ID_USUARIO_PARAMETRO,-1);
        token = intent.getStringExtra(Constantes.ID_TOKEN_PARAMETRO);

        ListView lista = (ListView)findViewById(R.id.lista);
        
        adapter = new LocalizacaoAdapter(this,atualizaLista());
        lista.setAdapter(adapter);
    }

    private List<Localizacao> atualizaLista(){
        IControladorLocalizacao controladorLocalizacao = new ControladorLocalizacao(this);
        return controladorLocalizacao.recuperaListaLocalizacoes(idUsuario);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_informar_localizacao) {
            chamaTelaInserirLocalizacao();
        }else
        if (id == R.id.nav_sair){
            chamaTelaLogin();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void chamaTelaInserirLocalizacao(){
        Intent intent = new Intent(this, LocalizaActivity.class);
        intent.putExtra(Constantes.ID_USUARIO_PARAMETRO,idUsuario);
        intent.putExtra(Constantes.ID_TOKEN_PARAMETRO,token);
        startActivity(intent);
    }

    private void chamaTelaLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
