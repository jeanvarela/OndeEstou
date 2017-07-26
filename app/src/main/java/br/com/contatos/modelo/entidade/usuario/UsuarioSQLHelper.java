package br.com.contatos.modelo.entidade.usuario;

/**
 * Created by JEAN on 21/05/2017.
 */

public class UsuarioSQLHelper  {

    public static final String TABELA_USUARIO = "usuario";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_PASSWORD = "password";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_TIPO = "tipo";
    public static final String COLUNA_TELEFONE = "telefone";
    public static final String COLUNA_ACCESS_TOKEN = "access_token";
    public static final String COLUNA_TOKEN_TYPE = "token_type";


    public static String geraTabela() {
        String script =  "CREATE TABLE "+ TABELA_USUARIO +" (" +
                                          COLUNA_ID +" INTEGER PRIMARY KEY,"+
                                          COLUNA_NOME     +" TEXT NOT NULL, "+
                                          COLUNA_EMAIL     +" TEXT NOT NULL, "+
                                          COLUNA_PASSWORD +" TEXT NOT NULL, " +
                                          COLUNA_TIPO +" TEXT NOT NULL, " +
                                          COLUNA_ACCESS_TOKEN +" TEXT NOT NULL, " +
                                          COLUNA_TOKEN_TYPE +" TEXT NOT NULL, " +
                                          COLUNA_TELEFONE +" TEXT NOT NULL)";

        return script;
    }
}
