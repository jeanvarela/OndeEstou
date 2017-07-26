package br.com.contatos.modelo.repositorio.localizacao;

import java.util.List;

import br.com.contatos.modelo.entidade.localizacao.Localizacao;

/**
 * Created by jean on 26/07/2017.
 */

public interface IRepositorioLocalizacao {

    public long adicionarLocalizacao(Localizacao localizacao);

    public List<Localizacao> recuperaLocalizacoes(long idMember);
}
