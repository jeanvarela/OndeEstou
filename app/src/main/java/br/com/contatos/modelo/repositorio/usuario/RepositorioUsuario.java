package br.com.contatos.modelo.repositorio.usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.contatos.infraestrutura.util.Constantes;
import br.com.contatos.modelo.entidade.usuario.Usuario;
import br.com.contatos.modelo.entidade.usuario.UsuarioSQLHelper;
import br.com.contatos.modelo.repositorio.RepositorioBasico;

public class RepositorioUsuario extends RepositorioBasico implements IRepositorioUsuario {


    public RepositorioUsuario(Context ctx) {
        super(ctx);
    }

    @Override
    public long inserirUsuario(Usuario usuario) {

        long id = getWriteDatabase().insert(UsuarioSQLHelper.TABELA_USUARIO, null, criaContentValues(usuario));
        getWriteDatabase().close();

        return id;
    }

    @Override
    public Usuario recuperaUsuario(long idUsuario) {
        String sql = "SELECT * FROM " + UsuarioSQLHelper.TABELA_USUARIO;
               sql += " WHERE  " +  UsuarioSQLHelper.COLUNA_ID + " = ?";

        String[] argumento = new String[]{String.valueOf(idUsuario)};
        Cursor cursor = getWriteDatabase().rawQuery(sql,argumento);

        Usuario usuario = null;

        if (cursor.moveToFirst()){
            usuario = criaUsuario(cursor);
        }

        return usuario;
    }

    @Override
    public void atualizaToken(long idUsuario, String token) {

        String sql = "UPDATE " + UsuarioSQLHelper.TABELA_USUARIO + " SET " +
                                 UsuarioSQLHelper.COLUNA_ACCESS_TOKEN + " = ? "  + " where " +
                                 UsuarioSQLHelper.COLUNA_ID  + " = ? ";
        String[] argumentos = new String[]{token,String.valueOf(idUsuario)};

        Cursor cursor = getWriteDatabase().rawQuery(sql,argumentos);
        cursor.moveToFirst();
    }

    private ContentValues criaContentValues(Usuario usuario){
        ContentValues contentValues = new ContentValues();

        contentValues.put(UsuarioSQLHelper.COLUNA_ID,usuario.getId());
        contentValues.put(UsuarioSQLHelper.COLUNA_NOME, usuario.getNome());
        contentValues.put(UsuarioSQLHelper.COLUNA_PASSWORD, usuario.getPassword());
        contentValues.put(UsuarioSQLHelper.COLUNA_EMAIL,usuario.getEmail());
        contentValues.put(UsuarioSQLHelper.COLUNA_TIPO,usuario.getTipo());
        contentValues.put(UsuarioSQLHelper.COLUNA_TELEFONE,usuario.getTelefone());
        contentValues.put(UsuarioSQLHelper.COLUNA_ACCESS_TOKEN,usuario.getAccess_token());
        contentValues.put(UsuarioSQLHelper.COLUNA_TOKEN_TYPE,usuario.getToken_type());

        return contentValues;
    }

    private Usuario criaUsuario(Cursor cursor){
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getLong(cursor.getColumnIndex(UsuarioSQLHelper.COLUNA_ID)));
        usuario.setNome(cursor.getString(cursor.getColumnIndex(UsuarioSQLHelper.COLUNA_NOME)));
        usuario.setPassword(cursor.getString(cursor.getColumnIndex(UsuarioSQLHelper.COLUNA_PASSWORD)));
        usuario.setEmail(cursor.getString(cursor.getColumnIndex(UsuarioSQLHelper.COLUNA_EMAIL)));
        usuario.setTipo(cursor.getString(cursor.getColumnIndex(UsuarioSQLHelper.COLUNA_TIPO)));
        usuario.setTelefone(cursor.getString(cursor.getColumnIndex(UsuarioSQLHelper.COLUNA_TELEFONE)));
        usuario.setAccess_token(cursor.getString(cursor.getColumnIndex(UsuarioSQLHelper.COLUNA_ACCESS_TOKEN)));
        usuario.setToken_type(cursor.getString(cursor.getColumnIndex(UsuarioSQLHelper.COLUNA_TOKEN_TYPE)));

        return  usuario;
    }
}