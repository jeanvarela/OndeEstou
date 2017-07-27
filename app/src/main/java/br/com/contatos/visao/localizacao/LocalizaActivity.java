package br.com.contatos.visao.localizacao;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import br.com.contatos.R;
import br.com.contatos.controlador.localizacao.ControladorLocalizacao;
import br.com.contatos.controlador.localizacao.IControladorLocalizacao;
import br.com.contatos.infraestrutura.util.Constantes;
import br.com.contatos.infraestrutura.util.MessageUtil;
import br.com.contatos.infraestrutura.util.Util;
import br.com.contatos.infraestrutura.webservice.ServiceGenerator;
import br.com.contatos.infraestrutura.webservice.acao.AcaoGravarLocalizacao;
import br.com.contatos.infraestrutura.webservice.interfaces.ErroHandler;
import br.com.contatos.infraestrutura.webservice.interfaces.SucessoHandler;
import br.com.contatos.infraestrutura.webservice.retorno.OcorrenciaRetorno;
import br.com.contatos.infraestrutura.webservice.resource.OcorrenciaService;
import br.com.contatos.modelo.entidade.localizacao.Localizacao;
import br.com.contatos.visao.principal.PrincipalActivity;
import retrofit2.Call;

public class LocalizaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
                                                                   GoogleApiClient.ConnectionCallbacks,
                                                                   GoogleApiClient.OnConnectionFailedListener,
                                                                   LocationListener, SucessoHandler,ErroHandler{

    private Spinner spnClima;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    private EditText txtLatitude;
    private EditText txtLongitude;

    private long   idUsuario;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localiza);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spnClima = (Spinner) findViewById(R.id.spnClimaAtual);
        configuraSpinner();

        txtLatitude = (EditText) findViewById(R.id.txtLatitude);
        txtLongitude = (EditText) findViewById(R.id.txtLongitude);

        Intent intent = getIntent();
        idUsuario = intent.getLongExtra(Constantes.ID_USUARIO_PARAMETRO,-1);
        token = intent.getStringExtra(Constantes.ID_TOKEN_PARAMETRO);

        criarConexaoGoogle();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (googleApiClient != null && googleApiClient.isConnected()){
            inicializaLocationUpdate();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void criarConexaoGoogle(){
        googleApiClient = new GoogleApiClient.Builder(this)
                                             .addOnConnectionFailedListener(this)
                                             .addConnectionCallbacks(this)
                                             .addApi(LocationServices.API)
                                             .build();
        googleApiClient.connect();
    }

    private void inicializaLocationRequest(){
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void inicializaLocationUpdate(){
        inicializaLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);
    }

    private void stopLocationUpdate(){
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
    }

    private void configuraSpinner(){
        spnClima.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Sol");
        categories.add("Chuva");
        categories.add("Frio");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnClima.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null){
            txtLatitude.setText(String.valueOf(location.getLatitude()));
            txtLongitude.setText(String.valueOf(location.getLongitude()));
        }

        inicializaLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //TODO
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //TODO
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            txtLatitude.setText(String.valueOf(location.getLatitude()));
            txtLongitude.setText(String.valueOf(location.getLongitude()));
        }
    }

    public void enviarLocalizacao(View view){
        if (Util.verificaConectado(this)) {
            Localizacao ocorrencia = new Localizacao();
            ocorrencia.setId_menber(idUsuario);
            ocorrencia.setLatitude(Double.valueOf(txtLatitude.getText().toString()));
            ocorrencia.setLogitude(Double.valueOf(txtLongitude.getText().toString()));
            ocorrencia.setOcurrency(String.valueOf(spnClima.getSelectedItem()));

            OcorrenciaService service = ServiceGenerator.createService(OcorrenciaService.class);
            Call<OcorrenciaRetorno> call = service.enviarOcorrencia(ocorrencia);

            AcaoGravarLocalizacao callGravar = new AcaoGravarLocalizacao(this, this);
            callGravar.execute(call);
        } else{
           MessageUtil.exibeMensagem(getString(R.string.semConexao),getString(R.string.erro),this);
        }
    }

    @Override
    public void erro(String mensagem) {
        MessageUtil.exibeMensagem(mensagem,getString(R.string.erro),this);
    }

    @Override
    public void sucesso(String mensagem) {
        gravaLocalizacao();

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getString(R.string.sucesso));
        dialogo.setMessage(mensagem);
        dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                chamaTelaPrincipal();
                dialog.dismiss();
            }
        });
        dialogo.create();
        dialogo.show();
    }

    private void gravaLocalizacao(){
        Localizacao localizacao = new Localizacao();
        localizacao.setId_menber(idUsuario);
        localizacao.setLatitude(Double.valueOf(txtLatitude.getText().toString()));
        localizacao.setLogitude(Double.valueOf(txtLongitude.getText().toString()));
        localizacao.setOcurrency(String.valueOf(spnClima.getSelectedItem()));

        IControladorLocalizacao controladorLocalizacao = new ControladorLocalizacao(this);
        long idLocaliza = controladorLocalizacao.inserLocalizacao(localizacao);

    }

    private void chamaTelaPrincipal(){
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra(Constantes.ID_USUARIO_PARAMETRO,idUsuario);
        intent.putExtra(Constantes.ID_TOKEN_PARAMETRO,token);
        startActivity(intent);
    }
}
