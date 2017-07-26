package br.com.contatos.modelo.entidade.localizacao;

/**
 * Created by JEAN on 21/05/2017.
 */

public class LocalizacaoSQLHelper {

    public static final String TABELA_LOCALIZACAO = "localizacao";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_ID_MENBER = "idMenber";
    public static final String COLUNA_OCORRENCY = "ocurrency";
    public static final String COLUNA_LATITUDE = "latitude";
    public static final String COLUNA_LONGITUDE = "longitude";


    public static String geraTabela() {
        String script =  "CREATE TABLE "+ TABELA_LOCALIZACAO +" (" +
                                          COLUNA_ID +" INTEGER PRIMARY KEY,"+
                                          COLUNA_ID_MENBER     +" INTEGER NOT NULL, "+
                                          COLUNA_OCORRENCY     +" TEXT NOT NULL, "+
                                          COLUNA_LATITUDE +" REAL NOT NULL, " +
                                          COLUNA_LONGITUDE +" REAL NOT NULL)";

        return script;
    }
}
