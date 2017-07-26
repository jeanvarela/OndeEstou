package br.com.contatos.controlador.localizacao;

import java.util.List;

import br.com.contatos.modelo.entidade.localizacao.Localizacao;

/**
 * Created by jean on 26/07/2017.
 */

public interface IControladorLocalizacao {

    public long inserLocalizacao(Localizacao localizacao);

    public List<Localizacao> recuperaListaLocalizacoes(long idUsuario);
}
