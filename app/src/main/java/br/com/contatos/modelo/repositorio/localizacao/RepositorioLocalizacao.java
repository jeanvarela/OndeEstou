package br.com.contatos.modelo.repositorio.localizacao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.contatos.modelo.entidade.localizacao.Localizacao;
import br.com.contatos.modelo.entidade.localizacao.LocalizacaoSQLHelper;
import br.com.contatos.modelo.entidade.usuario.Usuario;
import br.com.contatos.modelo.entidade.usuario.UsuarioSQLHelper;
import br.com.contatos.modelo.repositorio.RepositorioBasico;

/**
 * Created by jean on 26/07/2017.
 */

public class RepositorioLocalizacao extends RepositorioBasico implements IRepositorioLocalizacao {

    public RepositorioLocalizacao(Context ctx){
        super(ctx);
    }

    @Override
    public long adicionarLocalizacao(Localizacao localizacao) {
        long id = getWriteDatabase().insert(LocalizacaoSQLHelper.TABELA_LOCALIZACAO, null, criaContentValues(localizacao));
        getWriteDatabase().close();

        return id;
    }

    @Override
    public List<Localizacao> recuperaLocalizacoes(long idMember) {

        String sql = "SELECT * FROM " + LocalizacaoSQLHelper.TABELA_LOCALIZACAO +
                     " WHERE  " +  LocalizacaoSQLHelper.COLUNA_ID_MENBER + " = ?";
        String[] argumentos = new String[]{String.valueOf(idMember)};

        Cursor cursor = getWriteDatabase().rawQuery(sql,argumentos);

        List<Localizacao> localizacoes = new ArrayList<Localizacao>();
        while(cursor.moveToNext()){
            localizacoes.add(criaLocalizacao(cursor));
        }


        cursor.close();
        return localizacoes;
    }

    private ContentValues criaContentValues(Localizacao localizacao){
        ContentValues contentValues = new ContentValues();

        contentValues.put(LocalizacaoSQLHelper.COLUNA_ID_MENBER, localizacao.getId_menber());
        contentValues.put(LocalizacaoSQLHelper.COLUNA_OCORRENCY, localizacao.getOcurrency());
        contentValues.put(LocalizacaoSQLHelper.COLUNA_LATITUDE,localizacao.getLatitude());
        contentValues.put(LocalizacaoSQLHelper.COLUNA_LONGITUDE,localizacao.getLogitude());

        return contentValues;
    }

    private Localizacao criaLocalizacao(Cursor cursor){
        Localizacao localizacao = new Localizacao();
        localizacao.setId(cursor.getLong(cursor.getColumnIndex(LocalizacaoSQLHelper.COLUNA_ID)));
        localizacao.setId_menber(cursor.getLong(cursor.getColumnIndex(LocalizacaoSQLHelper.COLUNA_ID_MENBER)));
        localizacao.setOcurrency(cursor.getString(cursor.getColumnIndex(LocalizacaoSQLHelper.COLUNA_OCORRENCY)));
        localizacao.setLatitude(cursor.getDouble(cursor.getColumnIndex(LocalizacaoSQLHelper.COLUNA_LATITUDE)));
        localizacao.setLogitude(cursor.getDouble(cursor.getColumnIndex(LocalizacaoSQLHelper.COLUNA_LONGITUDE)));

        return  localizacao;
    }
}
