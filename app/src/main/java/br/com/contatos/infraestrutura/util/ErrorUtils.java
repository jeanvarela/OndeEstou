package br.com.contatos.infraestrutura.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jean on 23/07/2017.
 */

public class ErrorUtils {

    public static String parseError(String error) throws JSONException {
        String mensagemErro = null;
        JSONObject jsonError = new JSONObject(error);
        JSONArray erros = null;

        if (jsonError.has("erros")){
            erros = jsonError.getJSONArray("errors");
        }

        if (erros != null){
            mensagemErro = new String();

            for (int i = 0; i < erros.length(); i++){
                mensagemErro += erros.getJSONObject(i).getString("message") + "\n";
            }
        }

        return mensagemErro;
    }
}

