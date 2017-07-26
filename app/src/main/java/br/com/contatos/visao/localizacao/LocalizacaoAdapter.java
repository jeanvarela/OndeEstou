package br.com.contatos.visao.localizacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.contatos.R;
import br.com.contatos.modelo.entidade.localizacao.Localizacao;

/**
 * Created by jean on 26/07/2017.
 */

public class LocalizacaoAdapter extends BaseAdapter {

    private Context contexto;
    private List<Localizacao> localizacoes;

    public LocalizacaoAdapter(Context context, List<Localizacao> localizacoes){
        this.contexto = context;
        this.localizacoes = localizacoes;
    }

    @Override
    public int getCount() {
        return localizacoes.size();
    }

    @Override
    public Object getItem(int position) {
        return localizacoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Localizacao localizacao = localizacoes.get(position);

        View linha = LayoutInflater.from(contexto).inflate(R.layout.item,null);

        TextView txtClima = (TextView) linha.findViewById(R.id.txtClima);
        TextView txtLatitude = (TextView) linha.findViewById(R.id.txtLatitude);

        txtClima.setText(localizacao.getOcurrency());
        txtLatitude.setText(String.valueOf(localizacao.getLatitude()));

        return linha;
    }
}
