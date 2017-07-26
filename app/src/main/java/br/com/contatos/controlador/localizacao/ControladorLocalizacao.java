package br.com.contatos.controlador.localizacao;

import android.content.Context;

import java.util.List;

import br.com.contatos.modelo.entidade.localizacao.Localizacao;
import br.com.contatos.modelo.repositorio.localizacao.IRepositorioLocalizacao;
import br.com.contatos.modelo.repositorio.localizacao.RepositorioLocalizacao;

/**
 * Created by jean on 26/07/2017.
 */

public class ControladorLocalizacao implements IControladorLocalizacao{

    private IRepositorioLocalizacao repositorioLocalizacao;

    public ControladorLocalizacao(Context context){
        this.repositorioLocalizacao = new RepositorioLocalizacao(context);
    }

   @Override
    public long inserLocalizacao(Localizacao localizacao) {
        return repositorioLocalizacao.adicionarLocalizacao(localizacao);
    }

    @Override
    public List<Localizacao> recuperaListaLocalizacoes(long idUsuario) {
        return repositorioLocalizacao.recuperaLocalizacoes(idUsuario);
    }
}
